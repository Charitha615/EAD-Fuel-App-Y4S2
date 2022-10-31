namespace MongoDBTestProject.Model
{
    /** 
     * fuel queue remove request class for geth the request
     * **/
    public class FuelQueueRemoveRequest
    {
        public String EndDateTime { get; set; } = String.Empty;
        public String FuelAmount { get; set; } = String.Empty;
    }
}
