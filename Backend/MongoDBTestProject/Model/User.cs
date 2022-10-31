using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
namespace MongoDBTestProject.Model
{
    /**
     * user model class
     * **/
    public class User
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        public String Id { get; set; } = String.Empty;
        [BsonElement("username")]
        public String Username { get; set; } = String.Empty;
        [BsonElement("email")]
        public String Email { get; set; } = String.Empty;
        [BsonElement("password")]
        public byte[]? Password { get; set; }
        [BsonElement("passwordKey")]
        public byte[]? PasswordKey { get; set; }
        [BsonElement("role")]
        public String Role { get; set; } = String.Empty;
        [BsonElement("vehicleType")]
        public String VehicleType { get; set; } = String.Empty;
    }
}
