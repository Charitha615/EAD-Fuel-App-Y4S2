using MongoDB.Bson.Serialization.Attributes;
using MongoDB.Bson;
using System.Xml.Linq;
using System.Numerics;

namespace MongoDBTestProject.Model
{
    /**
     * fuel station model class
     * **/
    public class FuelStation
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        public String Id { get; set; } = String.Empty;
        [BsonElement("location")]
        public String Location { get; set; } = String.Empty;
        [BsonElement("noPumps")]
        public int NoOfPumps { get; set; }
        [BsonElement("availability")]
        public String Availability { get; set; } = String.Empty;
        [BsonElement("startingTime")]
        public String StartingTime { get; set; } = String.Empty;

        [BsonElement("endingTime")]
        public String EndingTime { get; set; } = String.Empty;
        [BsonElement("fuelType")]
        public String FuelType { get; set; } = String.Empty;

    }
}
