namespace MongoDBTestProject.Model
{
    /**
     * detabase settings interface
     * **/
    public interface IDatabaseSettings
    {
        // get the fuel station collection name
        string FuelStationCollectionName { get; set; }
        // get the fuel queue history collection name
        string FuelQueueHistoryCollectionName { get; set; }
        // get the fuel queue collection name
        string FuelQueueCollectionName { get; set; }
        // get the fuel queue request collection name
        string FuelQueueRequestCollectionName { get; set; }
        // get the user collection name
        string UserCollectionName { get; set; }
        // get the database name
        string DatabaseName { get; set; }
        // get database connected url
        string ConnectionString { get; set; }   


    }
}
