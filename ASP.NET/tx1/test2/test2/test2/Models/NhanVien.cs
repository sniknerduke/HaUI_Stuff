using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace test2.Models
{
    public class NhanVien
    {
        public string manv { get; set; }
        public string diachi { get; set; }
        public int songaylam { get; set; }
        public int luongngay { get; set; }
        public string hoten { get; set; }
        public int tienluong { get { return songaylam * luongngay; } }
    }
}