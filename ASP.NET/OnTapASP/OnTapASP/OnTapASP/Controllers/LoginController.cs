using OnTapASP.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace OnTapASP.Controllers
{
    public class LoginController : Controller
    {
        Model1 db = new Model1();
        // GET: Login
        public ActionResult Index()
        {
            return View();
        }
        [HttpGet]
        public ActionResult Login()
        {
            return View();
        }
        [HttpPost]
        public ActionResult Login(int MaTV, string MatKhau)
        {
            var tv = db.TiepViens.SingleOrDefault(x => x.MaTV == MaTV && x.MatKhau == MatKhau);
            if( tv == null)
            {
                ViewBag.err = "Sai ten dang nhap hoac mat khau";
                return View("Login");
            }
            else
            {
                Session["MaTV"] = MaTV;
                return RedirectToAction("Index", "TiepVienHangKhong");
            }
                
        }
        public ActionResult Logout()
        {
            Session["MaTV"] = null;
            return RedirectToAction("Index", "TiepVienHangKhong");
        }
    }
}