using KTHP4.Models;
using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using System.Web.UI.WebControls;

namespace KTHP4.Controllers
{
    public class TiepVienHangKhongController : Controller
    {
        private Model1 db = new Model1();

        // GET: TiepVienHangKhong
        public ActionResult Index(int? id)
        {
            var tiepViens = db.TiepViens.Include(t => t.BoPhan);
            if (id != null)
            {
                tiepViens = tiepViens.Where(t => t.MaBoPhan == id);
            }
            return View(tiepViens.ToList());
        }
        public ActionResult LuongCao()
        {
            var tiepViens = db.TiepViens.Where(t => t.Luong >=1600);
            ViewBag.TL = tiepViens.Sum(t => t.Luong);
            return View(tiepViens);
        }
        public ActionResult MenuBP() => PartialView(db.BoPhans.ToList());

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
        public ActionResult Create( TiepVien tiepVien , HttpPostedFileBase ImageFile)
        {
            try
            {
                var path = System.IO.Path.GetFileName(ImageFile.FileName);
                ImageFile.SaveAs(Server.MapPath("~/Content/Images/" + path));
                tiepVien.Anh = path;
                db.TiepViens.Add(tiepVien);
                db.SaveChanges();
                return Json(new { msg = "Thêm thành công" });
            }
            catch (Exception ex)
            {
                return Json(new { msg = "Có lỗi " + ex});
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
        public ActionResult Edit(TiepVien tiepVien , HttpPostedFileBase ImageFile)
        {
            try
            {
                    var path = System.IO.Path.GetFileName(ImageFile.FileName);
                    ImageFile.SaveAs(Server.MapPath("~/Content/Images/" + path));
                    tiepVien.Anh = path;
                db.Entry(tiepVien).State = EntityState.Modified;
                db.SaveChanges();
                return Json(new { msg = "Sửa thành công" });
            }
            catch (Exception ex)
            {
                return Json(new { msg = "Có lỗi " + ex });
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
