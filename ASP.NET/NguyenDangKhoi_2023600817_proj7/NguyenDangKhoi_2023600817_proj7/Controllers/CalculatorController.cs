using System.Web.Mvc;

namespace NguyenDangKhoi_2023600817_proj7.Controllers
{
    public class CalculatorController : Controller
    {
        [HttpGet]
        public ActionResult Index()
        {
            return View();
        }

        [HttpPost]
        public ActionResult Index(double? num1, double? num2, string operation)
        {
            if (num1.HasValue && num2.HasValue)
            {
                double result = 0;

                switch (operation)
                {
                    case "C?ng":
                        result = num1.Value + num2.Value;
                        break;
                    case "Tr?":
                        result = num1.Value - num2.Value;
                        break;
                    case "Nhân":
                        result = num1.Value * num2.Value;
                        break;
                    case "Chia":
                        if (num2.Value != 0)
                        {
                            result = num1.Value / num2.Value;
                        }
                        else
                        {
                            ViewBag.Error = "Không th? chia cho 0";
                        }
                        break;
                }

                ViewBag.Result = result;
                ViewBag.Num1 = num1;
                ViewBag.Num2 = num2;
            }

            return View();
        }
    }
}
