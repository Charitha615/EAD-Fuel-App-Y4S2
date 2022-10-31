namespace MongoDBTestProject.Auth
{
    /**
     * Hahing servis interface
     * **/
    public interface IAuthHashService
    {
        // for user password hashing
        void PasswordHashing(String password, out byte[] passwordHash, out byte[] passwordKey);

        // for verify the user password in login
        bool VerifyPassword(String password, byte[]? passwordHash, byte[]? passwordKey);


    }
}
