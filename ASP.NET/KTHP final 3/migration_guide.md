# ASP.NET MVC Migration & Rebuild Guide

This guide describes how to migrate/rebuild your project [KTHP final 3](file:///d:/College%20stuff/ASP.NET/KTHP%20final%203/KTHP%20final%203) from a scratch template in Visual Studio 2022 or VS Code.

---

## 1. Technology Comparison

| Feature / Concept | ASP.NET MVC 5 (Current Project) | ASP.NET Core MVC (Modern VS Code/VS 2022) |
| :--- | :--- | :--- |
| **Project System** | Standard `.csproj` (Non-SDK Style) | SDK-style `.csproj` (Compact XML format) |
| **Configuration** | [Web.config](file:///d:/College%20stuff/ASP.NET/KTHP%20final%203/KTHP%20final%203/Web.config) | `appsettings.json` |
| **App Startup** | [Global.asax.cs](file:///d:/College%20stuff/ASP.NET/KTHP%20final%203/KTHP%20final%203/Global.asax.cs) & `App_Start/` folder | `Program.cs` |
| **Static Files** | Scattered in `/Content` and `/Scripts` | Kept in the `/wwwroot` folder |
| **ORM / Database** | Entity Framework 6 (`System.Data.Entity`) | Entity Framework Core (`Microsoft.EntityFrameworkCore`) |
| **Session State** | `Session["TV"]` | `HttpContext.Session.SetString("TV", ...)` (Requires middleware setup) |
| **Controller Base** | Inherits `System.Web.Mvc.Controller` | Inherits `Microsoft.AspNetCore.Mvc.Controller` |

---

## 2. Scenario A: Rebuilding inside a clean ASP.NET MVC 5 (.NET Framework 4.7.2) Template
*Use this if you want to keep the exact same code structure, but start fresh in VS 2022 to clear build issues.*

### Phase 1: Project Creation
1. Open Visual Studio 2022, click **Create a new project**.
2. Search and select **ASP.NET Web Application (.NET Framework)**.
3. Choose **.NET Framework 4.7.2** (or 4.8) as the Target Framework.
4. Select **Empty** and check the **MVC** folder checkbox (or select the **MVC** template).

### Phase 2: Restoring Dependencies
1. Open **NuGet Package Manager Console** (or right-click Project -> Manage NuGet Packages).
2. Install Entity Framework 6 (same as your current project):
   ```powershell
   Install-Package EntityFramework -Version 6.5.1
   ```
3. Install standard compiler platform tools if they aren't pre-configured:
   ```powershell
   Install-Package Microsoft.CodeDom.Providers.DotNetCompilerPlatform
   ```

### Phase 3: Moving Configuration
1. Open the [Web.config](file:///d:/College%20stuff/ASP.NET/KTHP%20final%203/KTHP%20final%203/Web.config) file of the current project.
2. Copy the `<connectionStrings>` block (line 69-71) and paste it inside the `<configuration>` block of the new `Web.config`:
   ```xml
   <connectionStrings>
     <add name="Model1" connectionString="data source=SniknerDuke\SQLEXPRESS;initial catalog=QuanLyTiepVien;integrated security=True;trustservercertificate=True;MultipleActiveResultSets=True;App=EntityFramework" providerName="System.Data.SqlClient" />
   </connectionStrings>
   ```

### Phase 4: Porting Database Models
1. Copy the files from your old `Models/` folder:
   - [Model1.cs](file:///d:/College%20stuff/ASP.NET/KTHP%20final%203/KTHP%20final%203/Models/Model1.cs) (Contains the DbContext wrapper)
   - [TiepVien.cs](file:///d:/College%20stuff/ASP.NET/KTHP%20final%203/KTHP%20final%203/Models/TiepVien.cs)
   - [BoPhan.cs](file:///d:/College%20stuff/ASP.NET/KTHP%20final%203/KTHP%20final%203/Models/BoPhan.cs)
2. Paste them into the new `Models` folder. Update the namespaces (e.g. `namespace MyNewProject.Models`) inside each file if you named your new project differently.

### Phase 5: Porting Controllers and Views
1. Copy controllers:
   - [TiepVienHangKhongController.cs](file:///d:/College%20stuff/ASP.NET/KTHP%20final%203/KTHP%20final%203/Controllers/TiepVienHangKhongController.cs)
   - [LoginController.cs](file:///d:/College%20stuff/ASP.NET/KTHP%20final%203/KTHP%20final%203/Controllers/LoginController.cs)
2. Copy views:
   - Create directories `Views/TiepVienHangKhong` and `Views/Login` inside your new project's structure.
   - Move all corresponding `.cshtml` view files into those folders.
   - Replace your `Views/Shared/_Layout.cshtml` with the layout file from the old project.

### Phase 6: Adjusting Routing & Testing
1. Open `App_Start/RouteConfig.cs` in the new project.
2. Change the default action to match [RouteConfig.cs:L16-L21](file:///d:/College%20stuff/ASP.NET/KTHP%20final%203/KTHP%20final%203/App_Start/RouteConfig.cs#L16-L21):
   ```csharp
   defaults: new { controller = "TiepVienHangKhong", action = "Index", id = UrlParameter.Optional }
   ```
3. Run/Build the application.

---

## 3. Scenario B: Migrating to a modern ASP.NET Core MVC project (VS Code / .NET 8)
*Use this if you want to modernize the app to run cross-platform on macOS/Linux/Windows, or run natively in VS Code using the modern command-line toolchain.*

### Phase 1: Create a modern MVC template
1. Create a new directory and run:
   ```bash
   dotnet new mvc -n MyMvcApp
   ```

### Phase 2: Add modern Entity Framework Core packages
1. Add SqlServer Support:
   ```bash
   dotnet add package Microsoft.EntityFrameworkCore.SqlServer
   dotnet add package Microsoft.EntityFrameworkCore.Design
   ```

### Phase 3: Setup Configuration & DbContext
1. In `appsettings.json`, add the connection string from your old `Web.config`:
   ```json
   "ConnectionStrings": {
     "DefaultConnection": "Server=SniknerDuke\\SQLEXPRESS;Database=QuanLyTiepVien;Trusted_Connection=True;TrustServerCertificate=True;MultipleActiveResultSets=True"
   }
   ```
2. Rewrite [Model1.cs](file:///d:/College%20stuff/ASP.NET/KTHP%20final%203/KTHP%20final%203/Models/Model1.cs) to use EF Core DbContext instead of EF6:
   ```csharp
   using Microsoft.EntityFrameworkCore;
   
   namespace MyMvcApp.Models
   {
       public partial class Model1 : DbContext
       {
           public Model1(DbContextOptions<Model1> options) : base(options) { }
           public virtual DbSet<BoPhan> BoPhans { get; set; }
           public virtual DbSet<TiepVien> TiepViens { get; set; }
       }
   }
   ```
3. Register the DbContext and Enable Session support in `Program.cs`:
   ```csharp
   builder.Services.AddDbContext<Model1>(options =>
       options.UseSqlServer(builder.Configuration.GetConnectionString("DefaultConnection")));
   
   builder.Services.AddSession(options => {
       options.IdleTimeout = TimeSpan.FromMinutes(20);
   });
   
   // In the app pipeline config (before app.MapControllerRoute):
   app.UseSession();
   ```

### Phase 4: Porting Controllers (Handling Dependency Injection)
In ASP.NET Core, you do not write `private Model1 db = new Model1()`. Instead, inject it via the controller's constructor:
```csharp
public class TiepVienHangKhongController : Controller
{
    private readonly Model1 _db;
    public TiepVienHangKhongController(Model1 db)
    {
        _db = db;
    }
}
```
*Modify all references of `db` to `_db` and change return types to `IActionResult` instead of `ActionResult`.*

### Phase 5: Handling Session Code Changes
For authentication in [LoginController.cs](file:///d:/College%20stuff/ASP.NET/KTHP%20final%203/KTHP%20final%203/Controllers/LoginController.cs):
- Replace `Session["TV"] = user.HoTen;` with `HttpContext.Session.SetString("TV", user.HoTen);`.
- Replace `Session.Remove("TV");` with `HttpContext.Session.Remove("TV");`.

### Phase 6: Move Static Assets & Layout
1. Place Bootstrap and custom CSS/JS assets from your old project into the `/wwwroot` folder.
2. Move View pages (`Views/TiepVienHangKhong/*`, `Views/Login/*`, etc.) to the new Views directory.
3. Update namespace declarations at the top of your `.cshtml` views (or write `@using MyMvcApp.Models` globally inside your new project's `Views/_ViewImports.cshtml`).
