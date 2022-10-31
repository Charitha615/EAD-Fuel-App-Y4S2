using MongoDB.Driver;
using MongoDBTestProject.Model;

namespace MongoDBTestProject.Service
{
    /** 
     * user service class
     * **/
    public class UserService : IUserService
    {
        // veriable for hold mongo colllection
        private readonly IMongoCollection<User> _user;

        //constructor 
        public UserService(IDatabaseSettings settings, IMongoClient mongoClient)
        {
            var database = mongoClient.GetDatabase(settings.DatabaseName);
            _user = database.GetCollection<User>(settings.UserCollectionName);
        }

        // create user 
        public User Create(User user)
        {
            _user.InsertOne(user);
            return user;
        }

        // get user using id
        public User Get(string id)
        {
            return _user.Find(user => user.Id == id).FirstOrDefault();
        }

        // get user using username
        public User GetUserByUsername(string username)
        {
            return _user.Find(user => user.Username == username).FirstOrDefault();
        }

        // get all user in the collection
        public List<User> GetStudents()
        {
            return _user.Find(user => true).ToList();
        }

        // remove user using id
        public void Remove(string id)
        {
            _user.DeleteOne(user => user.Id == id);
        }

        // update user using id
        public void Update(string id, User user)
        {
            _user.ReplaceOne(user => user.Id == id, user);
        }
    }

}
