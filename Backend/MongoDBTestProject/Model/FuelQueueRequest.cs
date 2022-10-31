using MongoDB.Bson.Serialization.Attributes;
using MongoDB.Bson;

namespace MongoDBTestProject.Model
{
    /** 
     * fuel queue request model class
     * **/
    public class FuelQueueRequest
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        public String Id { get; set; } = String.Empty;
        [BsonElement("userId")]
        public String UserId { get; set; } = String.Empty;
        [BsonElement("noOfLiters")]
        public float NoOfLiters { get; set; }
        [BsonElement("pumpId")]
        public String PumpId { get; set; } = String.Empty;
        [BsonElement("approvalStatus")]
        public String ApprovalStatus { get; set; } = String.Empty;
    }
}
