using Helpers.HelperClasses;
using Microsoft.AspNetCore.Mvc;

namespace API.Controllers
{
	[ApiController]
	[Route("api/[controller]")]

	public class PasswordController : ControllerBase
	{

		[HttpGet]
		public ActionResult GetHashedPassword(string password)
		{
			string hashedPassword = PasswordHasher.HashPassword(password);

			return Ok(hashedPassword);
		}

	}
}
