using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using KTHP1.Models;

namespace KTHP1.Controllers
{
    public class TiepVienHangKhongController : Controller
    {
        private Model1 db = new Model1();

        public ActionResult Index(int? maBP, int? soChuyenBayMin, string hoTen)
        {
            var query = db.TiepViens.Include(t => t.BoPhan).AsQueryable();

            if (maBP.HasValue)
            {
                query = query.Where(tv => tv.MaBoPhan == maBP.Value);
            }
            if (soChuyenBayMin.HasValue)
            {
                query = query.Where(tv => tv.SoLuongChuyenBay >= soChuyenBayMin.Value);
            }
            if (!string.IsNullOrEmpty(hoTen))
            {
                query = query.Where(tv => tv.HoTen.Contains(hoTen));
            }

            ViewBag.MaBoPhan = new SelectList(db.BoPhans, "MaBoPhan", "TenBoPhan", maBP);
            ViewBag.CurrentMaBP = maBP;
            ViewBag.CurrentSoChuyenBayMin = soChuyenBayMin;
            ViewBag.CurrentHoTen = hoTen;

            return View(query.ToList());
        }

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

        public ActionResult ThemTiepVien()
        {
            ViewBag.MaBoPhan = new SelectList(db.BoPhans, "MaBoPhan", "TenBoPhan");
            return View("Create");
        }

        [HttpPost]
        public JsonResult Create(TiepVien tv, HttpPostedFileBase ImageFile)
        {
            if (!ModelState.IsValid)
            {
                return Json(new { success = false, msg = "Dữ liệu không hợp lệ!" });
            }
            try
            {
                if (ImageFile != null && ImageFile.ContentLength > 0)
                {
                    string folderPath = Server.MapPath("~/Content/Images/");
                    if (!System.IO.Directory.Exists(folderPath))
                    {
                        System.IO.Directory.CreateDirectory(folderPath);
                    }
                    string fileName = System.IO.Path.GetFileName(ImageFile.FileName);
                    string uploadPath = Server.MapPath("~/Content/Images/" + fileName);
                    ImageFile.SaveAs(uploadPath);
                    tv.Anh = fileName;
                }
                else
                {
                    tv.Anh = "no-image-news.png";
                }
                db.TiepViens.Add(tv);
                db.SaveChanges();
                return Json(new { success = true, msg = "Thêm thành công!" });
            }
            catch (Exception ex)
            {
                return Json(new { success = false, msg = "Lỗi khi lưu vào cơ sở dữ liệu: " + ex.Message });
            }
        }

        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            var tv = db.TiepViens.Find(id);
            if (tv == null)
            {
                return HttpNotFound();
            }
            ViewBag.MaBoPhan = new SelectList(db.BoPhans, "MaBoPhan", "TenBoPhan", tv.MaBoPhan);
            return View(tv);
        }

        [HttpPost]
        public JsonResult Edit(TiepVien tv, HttpPostedFileBase ImageFile)
        {
            if (!ModelState.IsValid)
            {
                return Json(new { success = false, msg = "Dữ liệu không hợp lệ!" });
            }
            try
            {
                var existing = db.TiepViens.Find(tv.MaTV);
                if (existing == null)
                {
                    return Json(new { success = false, msg = "Không tìm thấy tiếp viên!" });
                }

                if (ImageFile != null && ImageFile.ContentLength > 0)
                {
                    string folderPath = Server.MapPath("~/Content/Images/");
                    if (!System.IO.Directory.Exists(folderPath))
                    {
                        System.IO.Directory.CreateDirectory(folderPath);
                    }
                    string fileName = System.IO.Path.GetFileName(ImageFile.FileName);
                    string uploadPath = Server.MapPath("~/Content/Images/" + fileName);
                    ImageFile.SaveAs(uploadPath);
                    tv.Anh = fileName;
                }
                else
                {
                    tv.Anh = existing.Anh;
                }

                db.Entry(existing).CurrentValues.SetValues(tv);
                db.SaveChanges();
                return Json(new { success = true, msg = "Sửa thành công!", fileName = tv.Anh });
            }
            catch (Exception ex)
            {
                return Json(new { success = false, msg = "Lỗi khi lưu vào cơ sở dữ liệu: " + ex.Message });
            }
        }

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

        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            TiepVien tiepVien = db.TiepViens.Find(id);
            db.TiepViens.Remove(tiepVien);
            db.SaveChanges();
            return RedirectToAction("Index");
        }

        public ActionResult DanhSachLuongCao()
        {
            var ds = db.TiepViens.Where(tv => tv.Luong >= 1600).ToList();
            ViewBag.TongLuong = ds.Sum(tv => tv.Luong);
            return View(ds);
        }

        [ChildActionOnly]
        public ActionResult MenuBoPhan() => PartialView("_MenuBoPhan", db.BoPhans.ToList());

        public ActionResult DanhSachTheoBoPhan(int maBP) => View("Index", db.TiepViens.Where(tv => tv.MaBoPhan == maBP).ToList());

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
