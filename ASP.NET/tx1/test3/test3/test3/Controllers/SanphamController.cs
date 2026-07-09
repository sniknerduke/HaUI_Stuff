using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using test3.Models;

namespace test3.Controllers
{
    public class SanphamController : Controller
    {
        // GET: Sanpham
        List<Sanpham> danhsach = new List<Sanpham>();
        public SanphamController()
        {
            // Truyền true cho sản phẩm có giảm giá, false cho không giảm giá
            danhsach.Add(new Sanpham { masp = "S01", tensanpham = "Sản phẩm 1", soluong = 10, giatien = 100, giamgia = false });
            danhsach.Add(new Sanpham { masp = "S02", tensanpham = "Sản phẩm 2", soluong = 20, giatien = 120, giamgia = true });
            danhsach.Add(new Sanpham { masp = "S03", tensanpham = "Sản phẩm 3", soluong = 15, giatien = 200, giamgia = true });
            danhsach.Add(new Sanpham { masp = "S04", tensanpham = "Sản phẩm 4", soluong = 30, giatien = 150, giamgia = false });
            danhsach.Add(new Sanpham { masp = "S05", tensanpham = "Sản phẩm 5", soluong = 20, giatien = 50, giamgia = true });
        }
        public ActionResult HienThiDanhSach()
        {
            ViewBag.List1 = danhsach.Where(s => s.giatien > 100).ToList();
            ViewBag.List2 = danhsach.Where(s => s.giamgia == true).ToList(); // Lọc theo biến bool
            return View();
        }

        public ActionResult ThemSanPham()
        {
            return View();
        }

        [HttpPost]
        public ActionResult KetQua(Sanpham sp)
        {
            return View(sp);
        }
        public ActionResult Index()
        {
            return View();
        }
    }
}