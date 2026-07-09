using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using KTHP3.Models;

namespace KTHP3.Controllers
{
    public class TiepVienHangKhongController : Controller
    {
        private Model1 db = new Model1();

        // GET: TiepVienHangKhong
        public ActionResult Index()
        {
            var tiepViens = db.TiepViens.Include(t => t.BoPhan);
            return View(tiepViens.ToList());
        }
        public ActionResult LuongCao()
        {
            var ds = db.TiepViens.Where(t => t.Luong >= 1600);
            ViewBag.TL = ds.Sum(t => t.Luong);
            return View(ds);
        }
        public ActionResult MenuBP() => PartialView("MenuBP", db.BoPhans.ToList());
        // public ActionResult DanhSachTheoBoPhan(int maBP) => View("Index", db.TiepViens.Include(t => t.BoPhan).Where(tv => tv.MaBoPhan == maBP).ToList());
        // GET: TiepVienHangKhong/Details/5
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            TiepVien tiepVien = db.TiepViens.Find(id);
            if (tiepVien == null)
            {
                return HttpNotFound();
            }
            return View(tiepVien);
        }

        // GET: TiepVienHangKhong/Create
        public ActionResult Create()
        {
            ViewBag.MaBoPhan = new SelectList(db.BoPhans, "MaBoPhan", "TenBoPhan");
            return View();
        }

        // POST: TiepVienHangKhong/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        public ActionResult Create(TiepVien tiepVien, HttpPostedFileBase ImageFile)
        {
            try
            {
                if (ImageFile != null && ImageFile.ContentLength > 0)
                {
                    string filename = System.IO.Path.GetFileName(ImageFile.FileName);
                    ImageFile.SaveAs(Server.MapPath("~/Content/Images/" + filename));
                    tiepVien.Anh = filename;
                }
                db.TiepViens.Add(tiepVien);
                db.SaveChanges();
                return Json(new { msg = "Thêm thành công" });
            }
            catch (Exception ex)
            {
                return Json(new { msg = "Lỗi: " + ex.Message });
            }
        }

        // GET: TiepVienHangKhong/Edit/5
        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            TiepVien tiepVien = db.TiepViens.Find(id);
            if (tiepVien == null)
            {
                return HttpNotFound();
            }
            ViewBag.MaBoPhan = new SelectList(db.BoPhans, "MaBoPhan", "TenBoPhan", tiepVien.MaBoPhan);
            return View(tiepVien);
        }

        // POST: TiepVienHangKhong/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        public ActionResult Edit(TiepVien tiepVien, HttpPostedFileBase ImageFile)
        {
            try
            {
                if (ImageFile != null && ImageFile.ContentLength > 0)
                {
                    string filename = System.IO.Path.GetFileName(ImageFile.FileName);
                    ImageFile.SaveAs(Server.MapPath("~/Content/Images/" + filename));
                    tiepVien.Anh = filename;
                }
                else
                {
                    tiepVien.Anh = db.TiepViens.AsNoTracking().FirstOrDefault(t => t.MaTV == tiepVien.MaTV)?.Anh;
                }

                db.Entry(tiepVien).State = EntityState.Modified;
                db.SaveChanges();
                return Json(new { msg = "Sửa thành công!", newImageName = tiepVien.Anh });
            }
            catch (Exception ex)
            {
                return Json(new { msg = "Lỗi: " + ex.Message });
            }
        }

        // GET: TiepVienHangKhong/Delete/5
        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            TiepVien tiepVien = db.TiepViens.Find(id);
            if (tiepVien == null)
            {
                return HttpNotFound();
            }
            return View(tiepVien);
        }

        // POST: TiepVienHangKhong/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            TiepVien tiepVien = db.TiepViens.Find(id);
            db.TiepViens.Remove(tiepVien);
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
