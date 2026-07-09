using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using test2.Models;

namespace test2.Controllers
{
    public class QuanLyNVController : Controller
    {
        // GET: QuanLyNV
        List<NhanVien> danhsach = new List<NhanVien>();


        public QuanLyNVController()
        {
            danhsach.Add(new NhanVien { manv = "Nv01", hoten = "Nguyễn Vân Anh", diachi = "Hà Nội", songaylam = 15, luongngay = 200000 });
            danhsach.Add(new NhanVien { manv = "Nv02", hoten = "Lê Thu Hà", diachi = "Hải Phòng", songaylam = 27, luongngay = 250000 });
            danhsach.Add(new NhanVien { manv = "Nv03", hoten = "Nguyễn Văn Hoàng", diachi = "Hà Nội", songaylam = 18, luongngay = 250000 });
            danhsach.Add(new NhanVien { manv = "Nv04", hoten = "Trần Thu Hương", diachi = "Hải Phòng", songaylam = 25, luongngay = 190000 });
            danhsach.Add(new NhanVien { manv = "Nv05", hoten = "Ngô Phương Thảo", diachi = "Quảng Ninh", songaylam = 20, luongngay = 180000 });
        }

        public ActionResult HienThiDS()
        {
            ViewBag.List1 = danhsach.Where(s => s.songaylam < 20).ToList();
            ViewBag.List2 = danhsach.Where(s => s.luongngay > 90000).ToList();
            return View();
        }

        public ActionResult NhapDL()
        {
            return View();
        }

        [HttpPost]
        public ActionResult KetQua(NhanVien nv)
        {
            return View(nv);
        }
        public ActionResult Index()
        {
            return View();
        }
    }
}