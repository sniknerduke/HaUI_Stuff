using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using Tx2._3.Models;

namespace Tx2._3.Controllers
{
    public class SanPhamsController : Controller
    {
        private Model1 db = new Model1();
        public ActionResult Search(string search)
        {
            var sanPham = db.SanPhams.Include(x => x.HangSanXuat);
            if (!string.IsNullOrEmpty(search))
            {
                string f = search.Trim();
                decimal price;
                if (decimal.TryParse(search , out price))
                {
                    sanPham = sanPham.Where(x => x.DonGia >= price || x.TenSP.Contains(f));
                }
                sanPham = sanPham.Where(x => x.TenSP.Contains(f));
            }
            return View(sanPham.ToList());
        }

        // GET: SanPhams
        public ActionResult Index()
        {
            var sanPhams = db.SanPhams.Include(s => s.HangSanXuat);
            return View(sanPhams.ToList());
        }

        // GET: SanPhams/Details/5
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            SanPham sanPham = db.SanPhams.Find(id);
            if (sanPham == null)
            {
                return HttpNotFound();
            }
            return View(sanPham);
        }

        // GET: SanPhams/Create
        public ActionResult Create()
        {
            ViewBag.MaHang = new SelectList(db.HangSanXuats, "MaHang", "TenHang");
            return View();
        }

        // POST: SanPhams/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "MaSP,TenSP,SoLuong,DonGia,HinhAnh,MaHang")] SanPham sanPham)
        {
            try 
            {
                
                if (ModelState.IsValid)
                {
                    sanPham.HinhAnh = "";
                    var f = Request.Files["ImageFile"];
                    if (f != null && f.ContentLength > 0)
                    {
                        string FileName = System.IO.Path.GetFileName(f.FileName);
                        string uploadPath = Server.MapPath("~/images/" + FileName);
                        f.SaveAs(uploadPath);
                        sanPham.HinhAnh = FileName;

                    }
                    db.SanPhams.Add(sanPham);
                    db.SaveChanges();
                    
                }
                return RedirectToAction("Index");
            }
            catch (Exception ex)
            {
                ViewBag.Error = "Loi them dl !" + ex.Message;
                ViewBag.MaHang = new SelectList(db.HangSanXuats, "MaHang", "TenHang", sanPham.MaHang);
                return View(sanPham);
            }
            

        }

        // GET: SanPhams/Edit/5
        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            SanPham sanPham = db.SanPhams.Find(id);
            if (sanPham == null)
            {
                return HttpNotFound();
            }
            ViewBag.MaHang = new SelectList(db.HangSanXuats, "MaHang", "TenHang", sanPham.MaHang);
            return View(sanPham);
        }

        // POST: SanPhams/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "MaSP,TenSP,SoLuong,DonGia,HinhAnh,MaHang")] SanPham sanPham)
        {
            try
            {

                if (ModelState.IsValid)
                {
                    sanPham.HinhAnh = "";
                    var f = Request.Files["ImagePath"];
                    if (f != null && f.ContentLength > 0)
                    {
                        string FileName = System.IO.Path.GetFileName(f.FileName);
                        string uploadPath = Server.MapPath("~/images/" + FileName);
                        f.SaveAs(uploadPath);
                        sanPham.HinhAnh = FileName;

                    }
                    db.Entry(sanPham).State = EntityState.Modified;
                    db.SaveChanges();

                }
                return RedirectToAction("Index");
            }
            catch (Exception ex)
            {
                ViewBag.Error = "Loi them dl !" + ex.Message;
                return View(sanPham);
            }
        }

        // GET: SanPhams/Delete/5
        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            SanPham sanPham = db.SanPhams.Find(id);
            if (sanPham == null)
            {
                return HttpNotFound();
            }
            return View(sanPham);
        }

        // POST: SanPhams/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            SanPham sanPham = db.SanPhams.Find(id);
            db.SanPhams.Remove(sanPham);
            db.SaveChanges();
            return RedirectToAction("Index");
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }
    }
}
