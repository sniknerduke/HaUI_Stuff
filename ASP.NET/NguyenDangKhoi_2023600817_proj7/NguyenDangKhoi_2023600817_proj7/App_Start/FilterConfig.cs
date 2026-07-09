using System.Web;
using System.Web.Mvc;

namespace NguyenDangKhoi_2023600817_proj7
{
    public class FilterConfig
    {
        public static void RegisterGlobalFilters(GlobalFilterCollection filters)
        {
            filters.Add(new HandleErrorAttribute());
        }
    }
}
