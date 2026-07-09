using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using tx2._1.Models;

namespace tx2._1.Controllers
{
    public class ProductsController : Controller
    {
        private Model1 db = new Model1();

        // GET: Products
        public ActionResult Index(string searchString, decimal? minPrice, decimal? maxPrice)
        {
            var products = db.Products.Include(p => p.Catalogy);

            // Lọc theo tên rượu
            if (!String.IsNullOrEmpty(searchString))
            {
                products = products.Where(p => p.ProductName.Contains(searchString));
            }

            // Lọc theo khoảng giá bán
            if (minPrice.HasValue)
            {
                products = products.Where(p => p.Price >= minPrice.Value);
            }
            if (maxPrice.HasValue)
            {
                products = products.Where(p => p.Price <= maxPrice.Value);
            }

            // Sắp xếp mặc định
            products = products.OrderBy(p => p.ProductName);

            // Truyền dữ liệu tìm kiếm hiện tại sang View để giữ trạng thái trên form
            ViewBag.CurrentSearch = searchString;
            ViewBag.MinPrice = minPrice;
            ViewBag.MaxPrice = maxPrice;

            return View(products.ToList());
        }

        // GET: Products/Details/5
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Product product = db.Products.Find(id);
            if (product == null)
            {
                return HttpNotFound();
            }
            return View(product);
        }

        // GET: Products/Create
        public ActionResult Create()
        {
            ViewBag.CatalogyID = new SelectList(db.Catalogies, "CatalogyID", "CatalogyName");
            return View();
        }

        // POST: Products/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "ProductID,ProductName,Description,PurchasePrice,Price,Quantity,Vintage,CatalogyID,Region")] Product product, HttpPostedFileBase ImageFile)
        {
            try
            {
                if (ModelState.IsValid)
                {
                    // Xử lý upload file ảnh nếu có lựa chọn file
                    if (ImageFile != null && ImageFile.ContentLength > 0)
                    {
                        string fileName = System.IO.Path.GetFileName(ImageFile.FileName);
                        string path = Server.MapPath("~/wwwroot/WineImages/" + fileName);
                        ImageFile.SaveAs(path);
                        product.Image = fileName; // Lưu tên file vào database
                    }
                    else
                    {
                        product.Image = "no-image-news.png"; // Ảnh mặc định khi không upload
                    }

                    db.Products.Add(product);
                    db.SaveChanges();
                    return RedirectToAction("Index");
                }
            }
            catch (Exception ex)
            {
                ViewBag.Error = "Lỗi hệ thống khi thêm dữ liệu: " + ex.Message;
            }

            ViewBag.CatalogyID = new SelectList(db.Catalogies, "CatalogyID", "CatalogyName", product.CatalogyID);
            return View(product);
        }

        // GET: Products/Edit/5
        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Product product = db.Products.Find(id);
            if (product == null)
            {
                return HttpNotFound();
            }
            ViewBag.CatalogyID = new SelectList(db.Catalogies, "CatalogyID", "CatalogyName", product.CatalogyID);
            return View(product);
        }

        // POST: Products/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "ProductID,ProductName,Description,PurchasePrice,Price,Quantity,Vintage,CatalogyID,Image,Region")] Product product, HttpPostedFileBase ImageFile)
        {
            try
            {
                if (ModelState.IsValid)
                {
                    if (ImageFile != null && ImageFile.ContentLength > 0)
                    {
                        string fileName = System.IO.Path.GetFileName(ImageFile.FileName);
                        string path = Server.MapPath("~/wwwroot/WineImages/" + fileName);
                        ImageFile.SaveAs(path);
                        product.Image = fileName; // Ghi đè tên ảnh mới
                    }

                    db.Entry(product).State = EntityState.Modified;
                    db.SaveChanges();
                    return RedirectToAction("Index");
                }
            }
            catch (Exception ex)
            {
                ViewBag.Error = "Lỗi hệ thống khi cập nhật dữ liệu: " + ex.Message;
            }

            ViewBag.CatalogyID = new SelectList(db.Catalogies, "CatalogyID", "CatalogyName", product.CatalogyID);
            return View(product);
        }

        // GET: Products/Delete/5
        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Product product = db.Products.Find(id);
            if (product == null)
            {
                return HttpNotFound();
            }
            return View(product);
        }

        // POST: Products/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            Product product = db.Products.Find(id);
            db.Products.Remove(product);
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
