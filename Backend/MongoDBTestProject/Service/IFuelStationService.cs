using MongoDBTestProject.Model;

namespace MongoDBTestProject.Service
{
    /**
     * feule station service interface 
     * **/
    public interface IFuelStationService
    {
        // List of fuel stations for the queue users references.
        List<FuelStation> GetFuelStations();
        // Single fuel station details for the Station Owner.
        FuelStation GetFuelStation(String id);
        // create fusel station 
        FuelStation CreateFuelStation(FuelStation station);
        // Update fuel station queue starting time and ending time.
        void UpdateFuelStation(String stationId, FuelStation station);
        // remove fuel station using id
        void RemoveFuelStation(String id);
        // update fuel station details using id
        void UpdateStartTimeAndEndTime(String id, FuelStation station);



        // update the fuel requeat status
        void UpdateApprovalStatusFuelRequest(String approaval, String id);
        // get the all fuel requeat in the database
        List<FuelQueueRequest> GetFuelQueueRequests();
        // create new fuel request
        FuelQueueRequest CreateFuelRequest(FuelQueueRequest request);


        // Insert Queue
        FuelQueue CreateQueue(FuelQueue queue);

        // Get all Queue
        List<FuelQueue> GetAllQueue();

        // Specific get Queue
        FuelQueue GetQueueone(String id);


        // update Queue status
        void UpdateQueueStatus(String approaval, String id);



        // get vehicle count
        int GetVehicleCount(String stationId);
        // remove the fuel queue using id
        public void RemoveFuelQueue(String id);
        // insert new fuel queue history 
        public FuelQueueHistory InsertQueueHistory(FuelQueueHistory queueHistory);
        // get the list of fuel queue histories
        public List<FuelQueueHistory> GetQueueHistory(String id);


    }
}
