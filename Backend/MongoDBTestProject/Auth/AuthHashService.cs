using Microsoft.AspNetCore.Identity;
using System.Security.Cryptography;

namespace MongoDBTestProject.Auth
{
    /** 
     * Hahing class
     * **/
    public class AuthHashService : IAuthHashService
    {
        // Hash the user password
        public void PasswordHashing(String password, out byte[] passwordHash, out byte[] passwordKey)
        {
            using (var hmac = new HMACSHA512())
            {
                passwordKey = hmac.Key;
                passwordHash = hmac.ComputeHash(System.Text.Encoding.UTF8.GetBytes(password));
            }
        }

        // verify the password for login
        public bool VerifyPassword(String password, byte[] passwordHash, byte[] passwordKey)
        {
            using (var hmac = new HMACSHA512(passwordKey)) 
            {
                var computedHash = hmac.ComputeHash(System.Text.Encoding.UTF8.GetBytes(password));
                return computedHash.SequenceEqual(passwordHash);
            }
        }
    }
}
