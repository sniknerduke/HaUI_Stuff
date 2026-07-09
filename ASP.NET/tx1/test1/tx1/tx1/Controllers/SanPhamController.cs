using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using tx1.Models;

namespace tx1.Controllers
{
    
    public class SanPhamController : Controller
    {
        // GET: SanPham
        List<SanPham> danhsach = new List<SanPham>();

        public SanPhamController()
        {
            danhsach.Add(new SanPham { masp = "sp1", hangsx = "Apple", tensp = "Iphone X", soluong = 20, giatien = 200 });
            danhsach.Add(new SanPham { masp = "sp2", hangsx = "Xiaomi", tensp = "Xiaomi Ultra 15", soluong = 10, giatien = 500 });
            danhsach.Add(new SanPham { masp = "sp3", hangsx = "Apple", tensp = "Apple", soluong = 30, giatien = 320 });
            danhsach.Add(new SanPham { masp = "sp4", hangsx = "Apple", tensp = "Iphone 15", soluong = 40, giatien = 300 });
            danhsach.Add(new SanPham { masp = "sp5", hangsx = "Xiaomi", tensp = "Xiaomi 14 pro", soluong = 10, giatien = 200 });
            danhsach.Add(new SanPham { masp = "sp6", hangsx = "Xiaomi", tensp = "Xiaomi 15 pro", soluong = 15, giatien = 250 });
        }

        public ActionResult HienThiDS()
        {
            ViewBag.List1 = danhsach.Where(s => s.giatien >= 250).ToList();
            ViewBag.List2 = danhsach.Where(s => s.hangsx == "Xiaomi").ToList();
            return View();
        }

        public ActionResult NhapDL()
        {
            return View(new SanPham { hangsx = "Apple" });
        }
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult KetQua(SanPham sp)
        {
            if (!ModelState.IsValid)
            {
                return View("NhapDL", sp);
            }

            return View(sp);
        } 
        public ActionResult Index()
        {
            return View();
        }
    }
}