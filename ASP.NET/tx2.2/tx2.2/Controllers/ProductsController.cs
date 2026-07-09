using System;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using tx2._2.Models;

namespace tx2._2.Controllers
{
    public class ProductsController : Controller
    {
        private Ec db = new Ec();

        // 1. HIỂN THỊ TOÀN BỘ DANH SÁCH + TÌM KIẾM THEO TÊN HOẶC GIÁ
        public ActionResult Index(string searchString, string searchtype)
        {
            var products = db.Products.Select(p => p);

            // Thực hiện xử lý bộ lọc tìm kiếm dữ liệu nếu có giá trị truyền lên
            if (!String.IsNullOrEmpty(searchString))
            {
                if (searchtype == "pname") // Tìm theo tên sản phẩm
                {
                    products = products.Where(p => p.ProdName.Contains(searchString));
                }
                else if (searchtype == "price") // Tìm sản phẩm có giá nhỏ hơn hoặc bằng giá trị nhập
                {
                    if (decimal.TryParse(searchString, out decimal searchPrice))
                    {
                        products = products.Where(p => p.Price <= searchPrice);
                    }
                }
            }

            products = products.OrderBy(p => p.Pid);

            // Lưu giữ lại giá trị filter phục vụ gán ngược vào Form giao diện hiển thị
            ViewBag.CurrentSearch = searchString;
            ViewBag.CurrentSearchType = searchtype;

            return View(products.ToList());
        }

        // 2. XEM CHI TIẾT SẢN PHẨM
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

        // 3. HIỂN THỊ SẢN PHẨM THEO TỪNG DANH MỤC TRÊN MENU
        [Route("shop/danhmuc/{Categoryid?}")] // Kích hoạt định tuyến URL theo thuộc tính
        public ActionResult ProductByCategoryID(int? Categoryid)
        {
            if (Categoryid == null) return RedirectToAction("Index");

            var category = db.Categories.Find(Categoryid);
            if (category != null)
            {
                ViewBag.CategoryName = category.CategoryName; // Lấy tên danh mục hiển thị ra giao diện
            }

            var listProducts = db.Products.Where(p => p.Categoryid == Categoryid).OrderBy(p => p.Pid).ToList();
            return View(listProducts);
        }

        // GET: Products/Create
        public ActionResult Create()
        {
            ViewBag.Categoryid = new SelectList(db.Categories, "Categoryid", "CategoryName");
            return View();
        }

        // POST: Products/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "Pid,Categoryid,ProdName,MetaTitle,Description,Price")] Product product, HttpPostedFileBase ImageFile)
        {
            try
            {
                if (ModelState.IsValid)
                {
                    if (ImageFile != null && ImageFile.ContentLength > 0)
                    {
                        string fileName = System.IO.Path.GetFileName(ImageFile.FileName);
                        string uploadPath = Server.MapPath("~/Content/Images/" + fileName);
                        ImageFile.SaveAs(uploadPath); // Copy và lưu file ảnh vào server vật lý
                        product.ImagePath = fileName; // Lưu tên file ảnh vào cơ sở dữ liệu
                    }
                    else
                    {
                        product.ImagePath = "no-image-news.png"; // Gán ảnh mặc định
                    }

                    db.Products.Add(product);
                    db.SaveChanges();
                    return RedirectToAction("Index");
                }
            }
            catch (Exception ex)
            {
                ViewBag.Error = "Xảy ra lỗi hệ thống khi lưu dữ liệu mới: " + ex.Message;
            }

            ViewBag.Categoryid = new SelectList(db.Categories, "Categoryid", "CategoryName", product.Categoryid);
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

            ViewBag.Categoryid = new SelectList(db.Categories, "Categoryid", "CategoryName", product.Categoryid);
            return View(product);
        }

        // POST: Products/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "Pid,Categoryid,ProdName,MetaTitle,Description,Price,ImagePath")] Product product, HttpPostedFileBase ImageFile)
        {
            try
            {
                if (ModelState.IsValid)
                {
                    if (ImageFile != null && ImageFile.ContentLength > 0)
                    {
                        string fileName = System.IO.Path.GetFileName(ImageFile.FileName);
                        string uploadPath = Server.MapPath("~/Content/Images/" + fileName);
                        ImageFile.SaveAs(uploadPath);
                        product.ImagePath = fileName; // Ghi đè cập nhật ảnh mới
                    }

                    db.Entry(product).State = EntityState.Modified;
                    db.SaveChanges();
                    return RedirectToAction("Index");
                }
            }
            catch (Exception ex)
            {
                ViewBag.Error = "Xảy ra lỗi hệ thống khi chỉnh sửa dữ liệu: " + ex.Message;
            }

            ViewBag.Categoryid = new SelectList(db.Categories, "Categoryid", "CategoryName", product.Categoryid);
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
