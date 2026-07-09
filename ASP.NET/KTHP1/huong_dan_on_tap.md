# CẨM NANG ÔN TẬP ASP.NET MVC (DÀNH CHO NGƯỜI MỚI BẮT ĐẦU)

Chào bạn! Tài liệu này giải thích chi tiết từng dòng code và các khái niệm cốt lõi theo cách đơn giản nhất (giống như giải thích cho một em bé), giúp bạn hiểu sâu sắc bản chất để khi đi thi có gặp đề bài xoay chuyển thế nào vẫn tự tin làm được bài.

---

## 🍳 PHẦN 0: MÔ HÌNH NHÀ HÀNG (GIẢI THÍCH MVC LÀ GÌ?)

Hãy tưởng tượng ASP.NET MVC hoạt động giống hệt như một **Nhà hàng**:
* **Database / Models (Nguyên liệu trong kho)**: Nơi chứa dữ liệu thô (tiếp viên, bộ phận).
* **View (Thực đơn / Đĩa ăn dọn lên bàn)**: Những gì khách hàng nhìn thấy bằng mắt (HTML/CSS).
* **Controller (Đầu bếp)**: Người nhận yêu cầu từ khách (URL), đi vào kho (Model) lấy nguyên liệu chế biến, bày lên đĩa (View) rồi mang ra cho khách.

---

---

## 🛠️ PHẦN CHUẨN BỊ: TẠO CƠ SỞ DỮ LIỆU & KHỞI TẠO DỰ ÁN (THEO ĐÚNG FLOW ĐỀ BÀI)

### Bước A: Chạy file `.sql` để tạo cơ sở dữ liệu `QuanLyTiepVien`
1. Mở phần mềm **SQL Server Management Studio (SSMS)** lên.
2. Tại bảng kết nối, điền **Server name**: `.\SQLEXPRESS` (hoặc tên máy tính của bạn) và nhấn **Connect**.
3. Vào **File** -> **Open** -> **File...** -> Tìm chọn tệp tin `.sql` đề bài phát.
4. Nhấn nút **Execute** (hoặc phím **F5**) để chạy toàn bộ file SQL.
5. Kiểm tra bên trái mục **Databases**, nhấp chuột phải chọn **Refresh**, bạn sẽ thấy CSDL `QuanLyTiepVien` đã được tạo kèm theo các bảng `TiepVien` và `BoPhan`.

---

### Bước B: Tạo Project ASP.NET MVC trong Visual Studio
1. Mở phần mềm **Visual Studio** lên, chọn **Create a new project**.
2. Tìm kiếm cụm từ **"ASP.NET Web Application"** -> Chọn dòng **ASP.NET Web Application (.NET Framework)** (có biểu tượng chữ C#) -> Nhấn **Next**.
3. **Configure your new project**:
   - **Project name**: Đặt tên theo đúng cú pháp đề yêu cầu: `HoTen_MaSinhVien` (Ví dụ: `NguyenVanA_2023600123`).
   - **Framework**: Chọn bản ổn định nhất (mặc định thường là `.NET Framework 4.7.2`).
   - Nhấn **Create**.
4. **Create a new ASP.NET Web Application**:
   - Chọn template **MVC**.
   - Phía bên phải, phần **Authentication**, nên chọn **No Authentication** (vì chúng ta sẽ tự code Login Controller theo ý đề bài).
   - Nhấn **Create** để Visual Studio khởi tạo toàn bộ khung xương của dự án.

---

### Bước C: Cài đặt Entity Framework (NuGet Packages)
Để dự án C# kết nối được với Database SQL Server dễ dàng, ta cần cài đặt thư viện Entity Framework:
* **Cách 1 (Qua giao diện chuột)**:
  1. Nhấp chuột phải vào tên Project trong cột **Solution Explorer** -> Chọn **Manage NuGet Packages...**
  2. Chọn tab **Browse**, gõ tìm từ khóa `EntityFramework`.
  3. Chọn bản cài đặt mới nhất, nhấn nút **Install**, rồi chọn **OK** và **Accept** các điều khoản.
* **Cách 2 (Qua Console - Rất nhanh)**:
  1. Vào menu **Tools** -> **NuGet Package Manager** -> **Package Manager Console**.
  2. Gõ lệnh sau vào bảng console ở dưới màn hình và nhấn **Enter**:
     ```shell
     Install-Package EntityFramework
     ```

---

### Bước D: Tạo Model tự động từ Cơ sở dữ liệu (Database First)
Sau khi có Database và Project, ta cần kéo các bảng từ SQL Server vào C# để code:
1. Nhấp chuột phải vào thư mục **Models** trong Solution Explorer -> Chọn **Add** -> **New Item...**
2. Phía bên trái chọn mục **Data** -> Phía giữa chọn **ADO.NET Entity Data Model** -> Đặt tên cho model (ví dụ: `Model1` hoặc `QuanLyTiepVienModel`) -> Nhấn **Add**.
3. Bảng tùy chọn **Entity Data Model Wizard** xuất hiện:
   - Chọn **EF Designer from database** (nghĩa là kéo bảng có sẵn từ DB vào) -> Nhấn **Next**.
   - Nhấn nút **New Connection...** để kết nối cơ sở dữ liệu:
     - **Server name**: Điền `.\SQLEXPRESS` (hoặc tên máy SQL Server của bạn).
     - **Select or enter a database name**: Chọn CSDL `QuanLyTiepVien` từ danh sách xổ xuống.
     - Nhấn **Test Connection** để kiểm tra, nếu báo thành công thì nhấn **OK**.
   - Tích chọn **"Save connection settings in Web.config as"** (để tự động lưu Connection String vào Web.config) -> Nhấn **Next**.
   - Chọn phiên bản **Entity Framework 6.x** -> Nhấn **Next**.
   - Tại màn hình chọn bảng, mở rộng mục **Tables** và tích chọn 2 bảng: `TiepVien` và `BoPhan`.
   - Nhấn **Finish**. Dự án sẽ tự động sinh ra các file Model C# tương ứng (`TiepVien.cs`, `BoPhan.cs`) và file Context điều khiển cơ sở dữ liệu (`Model1.cs`).

---

### Bước E: Tạo MVC Controller và Views tự động (Scaffolding)
Để có sẵn các trang CRUD (Thêm, sửa, xóa, xem danh sách) nền tảng từ đầu:
1. Nhấp chuột phải vào thư mục **Controllers** -> Chọn **Add** -> **Controller...**
2. Chọn dòng **MVC 5 Controller with views, using Entity Framework** -> Nhấn **Add**.
3. **Add Controller**:
   - **Model class**: Chọn lớp `TiepVien` trong danh sách xổ xuống.
   - **Data context class**: Chọn lớp Context vừa sinh ra ở Bước D (thường là `Model1` hoặc `QuanLyTiepVienEntities`).
   - Tích chọn các mục: *Generate views*, *Reference script libraries*, *Use a layout page* (để trống ô layout để dùng layout mặc định).
   - **Controller name**: Đổi tên thành `TiepVienHangKhongController` -> Nhấn **Add**.
4. Visual Studio sẽ tự động sinh ra file `TiepVienHangKhongController.cs` và 5 file View giao diện nền tảng (`Index`, `Create`, `Edit`, `Details`, `Delete`) trong thư mục `Views/TiepVienHangKhong`.

---

## 🧭 BƯỚC 1: ĐẶT TRANG CHỦ MẶC ĐỊNH (ROUTE CONFIG)
* **Tệp tin**: [RouteConfig.cs](file:///d:/College%20stuff/ASP.NET/KTHP1/KTHP1/App_Start/RouteConfig.cs)

### 👶 Giải thích kiểu "Baby":
Khi bạn gõ địa chỉ trang web (ví dụ `localhost:1234/`), máy tính sẽ không biết nên mở trang nào đầu tiên. File này giống như một biển chỉ dẫn nói với máy tính: *"Khi khách vừa vào nhà hàng mà chưa gọi món, hãy tự động làm món **Index** của đầu bếp **TiepVienHangKhong** nhé!"*

### 💻 Đoạn code cấu hình:
```csharp
routes.MapRoute(
    name: "Default",
    url: "{controller}/{action}/{id}",
    // Đổi controller từ "Home" thành "TiepVienHangKhong"
    defaults: new { controller = "TiepVienHangKhong", action = "Index", id = UrlParameter.Optional }
);
```

---

## 📁 BƯỚC 2: THÊM CỘT ẢNH VÀO CƠ SỞ DỮ LIỆU & MODEL
* **Tệp tin Model**: [TiepVien.cs](file:///d:/College%20stuff/ASP.NET/KTHP1/KTHP1/Models/TiepVien.cs)

### 👶 Giải thích kiểu "Baby":
Ban đầu trong danh sách thông tin tiếp viên không có mục lưu trữ ảnh. Chúng ta làm 2 việc:
1. Vào SQL chạy lệnh `ALTER TABLE` để đục một chiếc hộp mới tên là `Anh` trong bảng Tiếp viên ở Database.
2. Mở file C# [TiepVien.cs](file:///d:/College%20stuff/ASP.NET/KTHP1/KTHP1/Models/TiepVien.cs) và khai báo thêm thuộc tính `Anh` để C# nhận diện chiếc hộp đó.

> [!NOTE]
> Ta **không lưu trực tiếp file ảnh nặng nề** vào trong database. Ta chỉ lưu **tên của bức ảnh** (ví dụ: `myphoto.png`) dưới dạng chữ (string). Còn file ảnh thật thì ta cất vào một thư mục trên máy tính.

### 💻 Đoạn code khai báo thuộc tính:
```csharp
[StringLength(255)] // Giới hạn tên file ảnh tối đa 255 ký tự
public string Anh { get; set; }
```

---

## 🔑 BƯỚC 3: ĐĂNG NHẬP / ĐĂNG XUẤT (QUẢN LÝ SESSION)
* **Tệp tin Controller**: [LoginController.cs](file:///d:/College%20stuff/ASP.NET/KTHP1/KTHP1/Controllers/LoginController.cs)

### 👶 Giải thích kiểu "Baby":
* **Session là gì?** Nó giống như chiếc **vòng đóng dấu đỏ vào tay** khi bạn mua vé vào khu vui chơi. Mỗi lần bạn đi qua cổng bảo vệ (như chuyển trang), bảo vệ chỉ cần nhìn xem tay bạn có dấu đỏ (Session) hay không. Nếu có thì cho qua và chào bạn, nếu không thì bắt quay lại phòng vé (Form Login).
* **Đăng xuất (`Logout`)**: Đơn giản là xóa vết dấu đỏ trên tay (`Session = null`), bạn sẽ trở lại trạng thái khách vãng lai.

### 💻 Giải thích code Controller chi tiết:
```csharp
public class LoginController : Controller
{
    private Model1 db = new Model1(); // Lấy đầu mối kết nối database

    // 1. Khi khách truy cập link /Login/Login thì hiện Form nhập
    public ActionResult Login() => View();

    // 2. Khi khách điền thông tin và ấn nút đăng nhập (POST gửi dữ liệu lên)
    [HttpPost]
    public ActionResult Login(string hoTen, string matKhau)
    {
        // Tìm trong bảng Tiếp viên xem có ai tên giống thế và mật khẩu giống thế không
        var user = db.TiepViens.FirstOrDefault(tv => tv.HoTen == hoTen && tv.MatKhau == matKhau);
        
        if (user != null) // Nếu tìm thấy (Đăng nhập đúng)
        {
            // Đóng dấu đỏ lên tay: lưu tên tiếp viên vào bộ nhớ Session
            Session["UserTV"] = user.HoTen; 
            
            // Đưa khách về trang chủ Danh sách tiếp viên
            return RedirectToAction("Index", "TiepVienHangKhong"); 
        }
        
        // Nếu sai: Tạo một dòng thông báo lỗi
        ViewBag.Error = "Sai tài khoản hoặc mật khẩu!";
        return View(); // Hiện lại form đăng nhập kèm dòng lỗi
    }

    // 3. Khi khách ấn Đăng xuất
    public ActionResult Logout() 
    { 
        Session["UserTV"] = null; // Xóa dấu đỏ trên tay (xóa Session)
        return RedirectToAction("Index", "TiepVienHangKhong"); // Trở về trang chủ
    }
}
```

---

## 🖼️ BƯỚC 4: THÊM/SỬA TIẾP VIÊN BẰNG AJAX & UPLOAD ẢNH

### 👶 Giải thích kiểu "Baby" về "FormData":
Bình thường khi gửi tin nhắn Ajax, ta gửi dạng văn bản chữ (JSON). Nhưng **ảnh là vật thể vật lý**, chữ không thể mô tả được nó. Do đó ta phải dùng một chiếc hộp đặc biệt trong JavaScript gọi là `FormData` để bỏ cả chữ lẫn tệp tin ảnh thật vào đó gửi đi.

---

### 💻 Code xử lý ở Controller ([TiepVienHangKhongController.cs](file:///d:/College%20stuff/ASP.NET/KTHP1/KTHP1/Controllers/TiepVienHangKhongController.cs)):

#### A. Hàm Thêm mới (`CreateAjax`):
```csharp
[HttpPost]
public JsonResult CreateAjax(TiepVien tv, HttpPostedFileBase ImageFile)
{
    // BƯỚC 1: Kiểm tra xem các trường bắt buộc (nhập tên, mật khẩu...) đã đầy đủ chưa
    if (!ModelState.IsValid)
    {
        return Json(new { success = false, msg = "Dữ liệu không hợp lệ!" });
    }
    try
    {
        // BƯỚC 2: Nếu người dùng có chọn ảnh và gửi lên
        if (ImageFile != null && ImageFile.ContentLength > 0)
        {
            // Tìm đường dẫn thư mục vật lý chứa ảnh trên máy chủ (Server)
            string folderPath = Server.MapPath("~/Content/Images/");
            
            // Nếu thư mục này chưa có thì tự động tạo mới
            if (!System.IO.Directory.Exists(folderPath))
            {
                System.IO.Directory.CreateDirectory(folderPath);
            }
            
            // Lấy tên của file ảnh (Ví dụ: "vietnam_airlines.jpg")
            string fileName = System.IO.Path.GetFileName(ImageFile.FileName);
            
            // Kết hợp thư mục và tên file để có đường dẫn lưu file
            string uploadPath = Server.MapPath("~/Content/Images/" + fileName);
            
            // Lưu file ảnh thật sự vào ổ cứng máy chủ
            ImageFile.SaveAs(uploadPath);
            
            // Ghi tên file ảnh vào đối tượng tiếp viên để lưu vào DB
            tv.Anh = fileName;
        }
        else
        {
            // Nếu không chọn ảnh, gán ảnh mặc định
            tv.Anh = "no-image-news.png";
        }
        
        // BƯỚC 3: Lưu tiếp viên vào database
        db.TiepViens.Add(tv);
        db.SaveChanges();
        return Json(new { success = true, msg = "Thêm thành công!" });
    }
    catch (Exception ex)
    {
        return Json(new { success = false, msg = "Lỗi: " + ex.Message });
    }
}
```

#### B. Hàm Sửa thông tin (`EditAjax`):
Hàm sửa khó hơn hàm thêm vì chúng ta phải xử lý: **"Giữ lại ảnh cũ nếu khách không chọn ảnh mới"**.
```csharp
[HttpPost]
public JsonResult EditAjax(TiepVien tv, HttpPostedFileBase ImageFile)
{
    if (!ModelState.IsValid)
    {
        return Json(new { success = false, msg = "Dữ liệu không hợp lệ!" });
    }
    try
    {
        // 1. Tìm thông tin tiếp viên cũ đang lưu trong database
        var existing = db.TiepViens.Find(tv.MaTV);
        if (existing == null)
        {
            return Json(new { success = false, msg = "Không tìm thấy tiếp viên!" });
        }

        // 2. Nếu khách chọn tải lên ảnh mới
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
            
            // Cập nhật tên ảnh mới
            tv.Anh = fileName;
        }
        else
        {
            // NẾU KHÔNG CHỌN ẢNH MỚI: Lấy tên ảnh cũ gán lại để không bị mất ảnh cũ
            tv.Anh = existing.Anh;
        }

        // 3. Đè các giá trị mới nhập từ client vào dữ liệu cũ đang được theo dõi
        db.Entry(existing).CurrentValues.SetValues(tv);
        db.SaveChanges(); // Lưu thay đổi vào DB
        
        // Trả về kèm fileName mới để javascript cập nhật ảnh demo
        return Json(new { success = true, msg = "Sửa thành công!", fileName = tv.Anh });
    }
    catch (Exception ex)
    {
        return Json(new { success = false, msg = "Lỗi: " + ex.Message });
    }
}
```

---

### 💻 Code xử lý ở Views giao diện ([Edit.cshtml](file:///d:/College%20stuff/ASP.NET/KTHP1/KTHP1/Views/TiepVienHangKhong/Edit.cshtml)):

#### Form và các thẻ hiển thị ảnh:
```html
<!-- BẮT BUỘC: enctype="multipart/form-data" để form hỗ trợ gửi tệp tin -->
@using (Html.BeginForm(null, null, FormMethod.Post, new { enctype = "multipart/form-data", id = "formEdit" }))
{
    <!-- Khung hiển thị ảnh demo -->
    <div class="form-group">
        <label class="control-label col-md-2">Ảnh hiện tại</label>
        <div class="col-md-10">
            <!-- Thẻ <img> có id="imgPreview" để dễ dàng điều khiển bằng Javascript -->
            <img id="imgPreview" src="@(string.IsNullOrEmpty(Model.Anh) ? "" : Url.Content("~/Content/Images/" + Model.Anh))" 
                 alt="Ảnh tiếp viên" 
                 style="width: 100px; height: auto; border: 1px solid #ccc; padding: 5px; border-radius: 5px; @(string.IsNullOrEmpty(Model.Anh) ? "display: none;" : "")" />
        </div>
    </div>

    <!-- Nơi chọn file ảnh -->
    <div class="form-group">
        <label class="control-label col-md-2" for="ImageFile">Ảnh mới</label>
        <div class="col-md-10">
            <input type="file" id="ImageFile" name="ImageFile" class="form-control" accept="image/*" />
        </div>
    </div>
    
    <!-- Nút lưu dữ liệu (Loại button thường để điều khiển bằng Ajax) -->
    <input type="button" id="btnSubmitEdit" value="Save" class="btn btn-default" />
}
```

#### Javascript xử lý Preview ảnh & gửi Ajax:
```javascript
@section Scripts {
    <script>
        // 1. Kỹ thuật xem trước ảnh thời gian thực (Local Preview)
        // Khi người dùng chọn file ảnh mới ở nút input file
        $("#ImageFile").change(function () {
            var input = this;
            if (input.files && input.files[0]) {
                var reader = new FileReader(); // Bộ đọc file của trình duyệt
                reader.onload = function (e) {
                    // Đọc file thành công thì thay đổi đường dẫn src của thẻ img để hiển thị luôn
                    $("#imgPreview").attr("src", e.target.result).show();
                };
                reader.readAsDataURL(input.files[0]);
            }
        });

        // 2. Gửi Ajax bằng FormData
        $("#btnSubmitEdit").click(function () {
            var formData = new FormData(); // Tạo chiếc hộp rỗng đựng dữ liệu gửi đi
            
            // Cho chữ vào hộp
            formData.append("MaTV", $("#MaTV").val());
            formData.append("MatKhau", $("#MatKhau").val());
            formData.append("HoTen", $("#HoTen").val());
            formData.append("NgaySinh", $("#NgaySinh").val());
            formData.append("Luong", $("#Luong").val());
            formData.append("SoLuongChuyenBay", $("#SoLuongChuyenBay").val());
            formData.append("MaBoPhan", $("#MaBoPhan").val());
            
            // Cho file ảnh thật vào hộp
            var fileInput = document.getElementById("ImageFile");
            if (fileInput.files.length > 0) {
                formData.append("ImageFile", fileInput.files[0]);
            }

            $.ajax({
                url: '@Url.Action("EditAjax", "TiepVienHangKhong")',
                type: 'POST',
                data: formData, // Truyền chiếc hộp FormData chứa file đi
                contentType: false, // BẮT BUỘC: Không cho jQuery tự ý đặt ContentType
                processData: false, // BẮT BUỘC: Không cho jQuery chuyển đổi FormData thành chuỗi
                success: function(res) {
                    alert(res.msg);
                    if (res.success && res.fileName) {
                        // Cập nhật lại đĩa ảnh Demo bằng file ảnh chính thức mà Server vừa lưu
                        $("#imgPreview").attr("src", "/Content/Images/" + res.fileName).show();
                    }
                }
            });
        });
    </script>
}
```

---

## 🔍 BƯỚC 5: TÌM KIẾM ĐA NĂNG ĐỘNG (THEO TÊN, BỘ PHẬN, SỐ CHUYẾN BAY)
* **Tệp tin Controller**: [TiepVienHangKhongController.cs](file:///d:/College%20stuff/ASP.NET/KTHP1/KTHP1/Controllers/TiepVienHangKhongController.cs)

### 👶 Giải thích kiểu "Baby":
Khi khách vào trang danh sách tiếp viên, họ có thể muốn lọc kết quả (Ví dụ: *"Chỉ hiển thị người tên Nam thuộc bộ phận Kỹ thuật và bay nhiều hơn 5 chuyến"*).
* Ta chuẩn bị 1 danh sách Tiếp viên đầy đủ ban đầu (`AsQueryable()`).
* Khách điền bộ lọc nào thì ta **cộng dồn** điều kiện đó vào câu lệnh SQL (`Where`).
* Cuối cùng mới lấy kết quả đã lọc gửi ra giao diện.

### 💻 Code xử lý ở Controller:
```csharp
public ActionResult Index(int? maBP, int? soChuyenBayMin, string hoTen)
{
    // 1. Tạo truy vấn gốc ban đầu (Chưa tải dữ liệu thực tế về)
    var query = db.TiepViens.Include(t => t.BoPhan).AsQueryable();

    // 2. Nếu khách có lọc theo bộ phận
    if (maBP.HasValue)
    {
        query = query.Where(tv => tv.MaBoPhan == maBP.Value);
    }
    
    // 3. Nếu khách có lọc theo số chuyến bay tối thiểu
    if (soChuyenBayMin.HasValue)
    {
        query = query.Where(tv => tv.SoLuongChuyenBay >= soChuyenBayMin.Value);
    }
    
    // 4. Nếu khách có nhập tên tiếp viên
    if (!string.IsNullOrEmpty(hoTen))
    {
        query = query.Where(tv => tv.HoTen.Contains(hoTen)); // Lọc tên có chứa từ khóa
    }

    // 5. Nạp danh sách bộ phận vào DropdownList và đánh dấu bộ phận đang chọn
    ViewBag.MaBoPhan = new SelectList(db.BoPhans, "MaBoPhan", "TenBoPhan", maBP);
    
    // 6. Lưu lại các từ khóa khách vừa gõ để hiển thị lại trên các ô input
    ViewBag.CurrentMaBP = maBP;
    ViewBag.CurrentSoChuyenBayMin = soChuyenBayMin;
    ViewBag.CurrentHoTen = hoTen;

    // 7. Chạy câu truy vấn đã cộng dồn điều kiện và trả về View
    return View(query.ToList());
}
```

---

## 💡 CÁC MẸO "VÀNG" KHI ĐI THI KHÔNG ĐƯỢC QUÊN

1. **Lỗi 404 khi tạo File mới**: 
   Khi thi, nếu bạn tạo file bằng cách nhấp chuột phải chọn New File ngoài thư mục mà không dùng Visual Studio, dự án sẽ không chạy. Nhớ mở file cấu hình `.csproj` của dự án và khai báo đúng đường dẫn tệp tin để Visual Studio biên dịch được nó.
2. **Luôn sử dụng `Url.Content` khi hiện ảnh**:
   Vì tên ảnh lưu trong cơ sở dữ liệu chỉ là `myphoto.png` chứ không có đường dẫn đầy đủ, nên khi hiển thị ảnh ở thẻ `<img>` trên View, ta phải viết:
   ```html
   src="~/Content/Images/@item.Anh"
   ```
   hoặc dùng helper:
   ```html
   src="@Url.Content("~/Content/Images/" + item.Anh)"
   ```
   để trình duyệt hiểu đúng thư mục lưu trữ.
3. **Dropdown list trong MVC**:
   Khi làm form tìm kiếm, để render ra thẻ `<select>` chứa danh sách các bộ phận, ta dùng cú pháp đơn giản:
   ```html
   @Html.DropDownList("maBP", (SelectList)ViewBag.MaBoPhan, "--- Tất cả bộ phận ---", new { @class = "form-select" })
   ```
   - `"maBP"`: Trùng khớp với tên của tham số `maBP` nhận vào ở Controller.
   - `ViewBag.MaBoPhan`: Chứa danh sách dữ liệu bộ phận được đóng gói dưới dạng `SelectList` gửi từ Controller qua.
4. **Hiển thị ảnh trong trang Details và Delete**:
   Để trang Chi tiết và trang Xóa hiện được ảnh tiếp viên, ta thêm cụm thẻ `<dt>` và `<dd>` này vào trong thẻ `<dl class="dl-horizontal">` của các file [Details.cshtml](file:///d:/College%20stuff/ASP.NET/KTHP1/KTHP1/Views/TiepVienHangKhong/Details.cshtml) và [Delete.cshtml](file:///d:/College%20stuff/ASP.NET/KTHP1/KTHP1/Views/TiepVienHangKhong/Delete.cshtml):
   ```html
   <dt>
       @Html.DisplayNameFor(model => model.Anh)
   </dt>
   <dd>
       @if (!string.IsNullOrEmpty(Model.Anh))
       {
           <img src="~/Content/Images/@Model.Anh" alt="Ảnh tiếp viên" style="width: 150px; height: auto; border: 1px solid #ccc; padding: 5px; border-radius: 5px;" />
       }
       else
       {
           <span class="text-muted">Không có ảnh</span>
       }
   </dd>
   ```
