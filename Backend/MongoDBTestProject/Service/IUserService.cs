using MongoDBTestProject.Model;

namespace MongoDBTestProject.Service
{
    /**
     * user service interface
     * **/
    public interface IUserService
    {
        // get all user in the collection
        List<User> GetStudents();
        // get user using id
        User Get(String id);
        // create user
        User Create(User user);
        // get user using username
        User GetUserByUsername(string username);
        // update user using id
        void Update(String id, User user);
        // remove user using id
        void Remove(String id);
    }
}
