using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using KTHP1.Models;

namespace KTHP1.Controllers
{
    public class LoginController : Controller
    {
        private Model1 db = new Model1();

        public ActionResult Login() => View();

        [HttpPost]
        public ActionResult Login(string hoTen, string matKhau)
        {
            var user = db.TiepViens.FirstOrDefault(tv => tv.HoTen == hoTen && tv.MatKhau == matKhau);
            if (user != null)
            {
                Session["UserTV"] = user.HoTen;
                return RedirectToAction("Index", "TiepVienHangKhong");
            }
            ViewBag.Error = "Sai tài khoản hoặc mật khẩu!";
            return View();
        }

        public ActionResult Logout()
        {
            Session["UserTV"] = null;
            return RedirectToAction("Index", "TiepVienHangKhong");
        }
    }
}
