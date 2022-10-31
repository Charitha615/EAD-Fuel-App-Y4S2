namespace MongoDBTestProject.Model
{
    /**
     * database settings class
     * **/
    public class DatabaseSettings : IDatabaseSettings
    {
        // get the user collection name
        public string UserCollectionName { get; set; } = String.Empty;
        // get the database name
        public string DatabaseName { get; set; } = String.Empty;
        // get database connected url
        public string ConnectionString { get; set; } = String.Empty;
        // get the fuel station collection name
        public string FuelStationCollectionName { get; set; } = String.Empty;
        // get the fuel queue collection name
        public string FuelQueueHistoryCollectionName { get; set; } = String.Empty;
        // get the fuel queue history collection name
        public string FuelQueueCollectionName { get; set; } = String.Empty;
        // get the fuel queue request collection name
        public string FuelQueueRequestCollectionName { get; set; } = String.Empty;
    }

    }
