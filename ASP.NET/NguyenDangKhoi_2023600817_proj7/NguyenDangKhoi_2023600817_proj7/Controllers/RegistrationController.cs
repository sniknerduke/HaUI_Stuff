using System.Web.Mvc;
using NguyenDangKhoi_2023600817_proj7.Models;

namespace NguyenDangKhoi_2023600817_proj7.Controllers
{
    public class RegistrationController : Controller
    {
        [HttpGet]
        public ActionResult Index()
        {
            return View();
        }

        [HttpPost]
        public ActionResult Index(RegistrationModel model)
        {
            if (ModelState.IsValid)
            {
                ViewBag.UserInfo = model;
            }

            return View(model);
        }
    }
}
