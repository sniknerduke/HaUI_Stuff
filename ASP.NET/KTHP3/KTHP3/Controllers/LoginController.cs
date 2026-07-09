using KTHP3.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace KTHP3.Controllers
{
    public class LoginController : Controller
    {
        private Model1 db = new Model1();
        // GET: Login
        public ActionResult Login()
        {
            return View();
        }
        public ActionResult Logout()
        {
            Session["UserTV"] = null;
            return RedirectToAction("Index", "TiepVienHangKhong");
        }
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Login(string hoTen , string matkhau)
        {
            var user = db.TiepViens.FirstOrDefault(tv => tv.HoTen == hoTen && tv.MatKhau == matkhau);
            if (user != null)
            {
                Session["UserTV"] = user.HoTen;
                return RedirectToAction("Index", "TiepVienHangKhong");
            }
            ViewBag.Error = "Sai tài khoản hoặc mật khẩu";
            return View();
        }

    }
}