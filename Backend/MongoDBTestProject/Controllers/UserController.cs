using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using MongoDBTestProject.Auth;
using MongoDBTestProject.Model;
using MongoDBTestProject.Service;
using System.Security;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace MongoDBTestProject.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    /**
     * user controller class
     * **/
    public class UserController : ControllerBase
    {
        // variable for hold servies interfaces
        private readonly IUserService userService;
        private readonly IAuthHashService authService;
        // constructor 
        public UserController(IUserService userService, IAuthHashService authService)
        {
            this.userService = userService;
            this.authService = authService;
        }

        // GET: api/<UserController>
        [HttpGet]
        public ActionResult<List<User>> Get()
        {
            return userService.GetStudents();
        }

        // GET api/<UserController>/5
        [HttpGet("{id}")]
        public ActionResult<User> Get(String id)
        {
            var user = userService.Get(id);
            if (user == null)
            {
                return NotFound($"user with id = {id} not found");
            }
            
            return user;
        }

        // POST api/<UserController>
        [HttpPost("registration")]
        public ActionResult<User> Registration([FromBody] UserRequest request)
        {
            if (request.Username == null || request.Email == null || request.Password == null || request.VehicleType == null)
            {
                return BadRequest("Fail to registre");
            }
            authService.PasswordHashing(request.Password, out byte[] passwordHash, out byte[] passwordKey);

            User user = new User();
            user.Username = request.Username;
            user.Email = request.Email;
            user.Password = passwordHash;
            user.PasswordKey = passwordKey;
            user.Role = request.Role;
            user.VehicleType = request.VehicleType;

            userService.Create(user);
            return  CreatedAtAction(nameof(Get), new { id = user.Id }, user);
        }

        //POST login api 
        [HttpPost("login")]
        public ActionResult<User> Login([FromBody] UserRequest request)
        {
            if (request.Username == null || request.Password == null)
            {
                return BadRequest("Fail to login");
            }
            var existingUser = userService.GetUserByUsername(request.Username);

            if (existingUser == null)
            {
                return NotFound($"user with username = {request.Username} not found");
            }
            bool verification = authService.VerifyPassword(request.Password, existingUser.Password, existingUser.PasswordKey);

            if (!verification)
            {
                return NotFound("Your username or password is wrong");
            }
            
            return Ok(existingUser);
        }

        // PUT api/<UserController>/5
        [HttpPut("{id}")]
        public ActionResult Put(String id, [FromBody] User user)
        {
            var existingUser = userService.Get(id);

            if (existingUser == null)
            {
                return NotFound($"Student with id = {id} not found");
            }

            userService.Update(id, user);

            return NoContent();
        }

        // DELETE api/<UserController>/5
        [HttpDelete("{id}")]
        public ActionResult Delete(String id)
        {
            var user = userService.Get(id);

            if (user == null)
            {
                return NotFound($"Student with id = {id} not found");
            }

            userService.Remove(user.Id);

            return Ok($"Student with id = {id} deleted");
        }
    }
}
