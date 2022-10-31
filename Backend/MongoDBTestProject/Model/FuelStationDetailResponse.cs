namespace MongoDBTestProject.Model
{
    /**
     * fuel station detail response class for send reponse
     * **/
    public class FuelStationDetailResponse
    {
        public string id { get; set; } = string.Empty;
        public string location { get; set; } = string.Empty;
        public int noOfPumps { get; set; }
        public string availability { get; set; } = string.Empty;
        public String startingTime { get; set; } = string.Empty;
        public String endingTime { get; set; } = string.Empty;
        public string fuelType { get; set; } = string.Empty;
        public int vehicleCount { get; set; }
    }
}
