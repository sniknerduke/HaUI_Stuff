using KTHP4.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace KTHP4.Controllers
{
    public class LoginController : Controller
    {
        private Model1 db = new Model1();

        // GET: Login
        public ActionResult Login()
        {
            return View();
        }
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Login(string username , string pass)
        {
            var user = db.TiepViens.FirstOrDefault(t => t.HoTen == username && t.MatKhau == pass);
            if (user !=null)
            {
                Session["TV"] = user.HoTen;
                return RedirectToAction("Index", "TiepVienHangKhong");
            }
            ViewBag.error = "Sai tk or mk";
            return View();
        }

        public ActionResult Logout()
        {
            Session.Remove("TV");
            return RedirectToAction("Login");
        }
    }
}