using KTHP_final2.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace KTHP_final2.Controllers
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
        public ActionResult Login(string username, string pass)
        {
            if (int.TryParse(username, out int maTV))
            {
                var user = db.TiepViens.FirstOrDefault(t => t.MaTV == username && t.MatKhau == pass);
                if (user != null)
                {
                    Session["TV"] = user.HoTen;
                    return RedirectToAction("Index", "TiepVienHangKhong");
                }
                else
                {
                    ViewBag.error = "Có lỗi ";
                    return View();
                }
            }
        }
        public ActionResult Logout()
        {
            Session.Remove("TV");
            return RedirectToAction("Login");
        }
    }
}