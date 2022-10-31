using Microsoft.AspNetCore.Mvc;
using MongoDBTestProject.Model;
using MongoDBTestProject.Service;

namespace MongoDBTestProject.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    /** 
     * fuel station controller class 
     ***/
    public class FuelStationController : Controller
    {
        // variable for hold servies interfaces
        private readonly IFuelStationService fuelStationService;
        // constructor 
        public FuelStationController(IFuelStationService fuelStationService)
        {
            this.fuelStationService = fuelStationService;
        }

        // POST api/<FuelSationController>/addFuelStation
        [HttpPost("addFuelStation")]
        public ActionResult<FuelStation> AddFuelStation([FromBody] FuelStation request)
        {
            if (request.Location == null || request.NoOfPumps < 0 || request.Availability == null || request.FuelType == null)
            {
                return BadRequest("Missing Fuel Station Details!");
            }
            FuelStation station = new FuelStation();
            station.Location = request.Location;
            station.NoOfPumps = request.NoOfPumps;
            station.Availability = request.Availability;
            station.FuelType = request.FuelType;
            station.StartingTime = request.StartingTime;
            station.EndingTime = request.EndingTime;
            fuelStationService.CreateFuelStation(station);
            return CreatedAtAction(nameof(GetFuelStation), new { id = station.Id }, station);
        }

        // GET api/<FuelStationController>/<id>
        [HttpGet("get/{id}")]
        public ActionResult<FuelStationDetailResponse> GetFuelStation(String id)
        {
            var station = fuelStationService.GetFuelStation(id);
            if (station == null)
            {
                return NotFound($"Fuel Station with id = {id} not found");
            }
            int count = fuelStationService.GetVehicleCount(id);

            FuelStationDetailResponse response = new();
            response.vehicleCount = count;
            response.id = station.Id;
            response.location = station.Location;
            response.noOfPumps = station.NoOfPumps;
            response.availability = station.Availability;
            response.fuelType = station.FuelType;
            response.startingTime = station.StartingTime;
            response.endingTime = station.EndingTime;
            
            return response;
        }

        [HttpGet("{id}")]
        public ActionResult<FuelStation> GetFuelStationWithCount(String id)
        {
            var station = fuelStationService.GetFuelStation(id);
            if (station == null)
            {
                return NotFound($"Fuel Station with id = {id} not found");
            }

            return station;
        }

        //GET api/<FuelStationController>/getFuelStations
        [HttpGet("getFuelStations")]
        public ActionResult<List<FuelStation>> GetAllFuelStations()
        {
            return fuelStationService.GetFuelStations();
        }

        //PUT api/<FuelStationController>/updateStartEndTime
        [HttpPut("updateStartEndTime/{id}")]
        public ActionResult updateStartEndTime(String id, [FromBody] FuelStation station)
        {
            fuelStationService.UpdateStartTimeAndEndTime(id, station);
            return NoContent();
        }

        // POST api/<FuelSationController>/addFuelQueueRequest
        [HttpPost("AddFuelRequest")]
        public ActionResult<FuelQueueRequest> AddFuelRequest([FromBody] FuelQueueRequest request)
        {
            if (request.UserId == null || request.NoOfLiters < 0 || request.PumpId == null || request.ApprovalStatus == null)
            {
                return BadRequest("Missing Fuel Station Details!");
            }
            FuelQueueRequest station = new FuelQueueRequest();
            station.UserId = request.UserId;
            station.NoOfLiters = request.NoOfLiters;
            station.PumpId = request.PumpId;
            station.ApprovalStatus = request.ApprovalStatus;
            fuelStationService.CreateFuelRequest(station);
            return CreatedAtAction(nameof(GetFuelStation), new { id = station.Id }, station);
        }

        //PUT api/<FuelStationController>/updateApprovalStatus
        [HttpPut("updateApprovalState/{id}")]
        public ActionResult updateApprovalStatus(String id, [FromBody] FuelQueueRequest station)
        {
            String status = "pending";
            String approvalStatus = station.ApprovalStatus;
            if(String.Equals(approvalStatus, "approve"))
            {
                status = "approve";
            }
            fuelStationService.UpdateApprovalStatusFuelRequest(status, id);
            return NoContent();
        }
        //GET api/<FuelStationController>/getFuelRequests
        [HttpGet("getFuelRequests")]
        public ActionResult<List<FuelQueueRequest>> GetFuelRequests()
        {
            return fuelStationService.GetFuelQueueRequests();
        }


        //Queue

        //Create Queue
        [HttpPost("addFuelStationQueue")]
        public ActionResult<FuelQueue> AddQueue([FromBody] FuelQueue request)
        {
            if (request.VehicleNumber == null || request.UserId == null || request.StationId == null || request.PumpId == null)
            {
                return BadRequest("Missing Fuel Station Details!");
            }
            FuelQueue queue = new FuelQueue();
            queue.VehicleNumber = request.VehicleNumber;
            queue.StationId = request.StationId;
            queue.UserId = request.UserId;
            queue.PumpId = request.PumpId;
            queue.StartingDateTime = request.StartingDateTime;
           // queue.Status = request.Status;
            queue.Status = "IN";
            fuelStationService.CreateQueue(queue);
            return CreatedAtAction(nameof(GetFuelStation), new { id = queue.Id }, queue);
        }

        //Get All Queue 
        [HttpGet("getFuelQueue")]
        public ActionResult<List<FuelQueue>> GetAllFuelQueue()
        {
            return fuelStationService.GetAllQueue();
        }

        //Get One Queue
        [HttpGet("getqueue/{id}")]
        public ActionResult<FuelQueue> GetQueue(String id)
        {
            var station = fuelStationService.GetQueueone(id);
            if (station == null)
            {
                return NotFound($"Fuel Queue with id = {id} not found");
            }

            return station;
        }


        //PUT update queue status
        [HttpPut("updateQueueState/{id}")]
        public ActionResult updateQueueStatus(String id, [FromBody] FuelQueue station)
        {
            String status = "OUT";

            fuelStationService.UpdateQueueStatus(status, id);
            return Content(id+" Status Updated!");
        }

        //DELETE delete fuel queue from the collection
        [HttpDelete("removeFuelQueue/{id}")]
        public ActionResult RemoveFuelQueue(String id, [FromBody] FuelQueueRemoveRequest request)
        {
            var queue = fuelStationService.GetQueueone(id);

            if (queue == null)
            {
                return NotFound($"Queue with id = {id} not found");
            }
            var station = fuelStationService.GetFuelStation(queue.StationId);
            fuelStationService.RemoveFuelQueue(queue.Id);

            FuelQueueHistory queueHistory = new();
            queueHistory.StationId = queue.StationId;
            queueHistory.StartDateTime = queue.StartingDateTime;
            queueHistory.UserId = queue.UserId;
            queueHistory.EndDateTime = request.EndDateTime;
            queueHistory.Location = station.Location;
            if (request.FuelAmount == "")
            {
                queueHistory.FuelAmount = "Early queue exit";
            }
            else
            {
                queueHistory.FuelAmount = request.FuelAmount;
            }

            fuelStationService.InsertQueueHistory(queueHistory);

            return Ok($"Queue with id = {id} deleted");
        }

        //GET get the all fuel history base on user
        [HttpGet("getUserHistory/{id}")]
        public ActionResult<List<FuelQueueHistory>> GetUserHistory(String id)
        {
            return fuelStationService.GetQueueHistory(id);
        }

    }
}
