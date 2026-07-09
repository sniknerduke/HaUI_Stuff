using KTHP_final1.Models;
using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Helpers;
using System.Web.Mvc;
using System.Web.UI.WebControls;

namespace KTHP_final1.Controllers
{
    public class TiepVienHangKhongController : Controller
    {
        private Model1 db = new Model1();
        public ActionResult Luongcao()
        {
            var ds = db.TiepViens.Where(t => t.Luong >= 1600);
            ViewBag.TL = ds.Sum(t => t.Luong);
            return View(ds);
        }
        public ActionResult MenuBP() => PartialView(db.BoPhans.ToList());
        // GET: TiepVienHangKhong
        public ActionResult Index()
        {
            var tiepViens = db.TiepViens.Include(t => t.BoPhan);
            return View(tiepViens.ToList());
        }

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
        public ActionResult Create(TiepVien tiepVien)
        {
            try
            {
                db.TiepViens.Add(tiepVien);
                db.SaveChanges();
                return Json(new { msg = "Them thanh cong!" });
            }
            catch (Exception ex)
            {
                return Json(new { msg = "co loi: " + ex.Message });
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
        public ActionResult Edit(TiepVien tiepVien)
        {
            try
            {
                db.Entry(tiepVien).State = EntityState.Modified;
                db.SaveChanges();
                return Json(new { msg = "Cap nhat thanh cong!" });
            }
            catch (Exception ex)
            {
                return Json(new { msg = "co loi: " + ex.Message });
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
