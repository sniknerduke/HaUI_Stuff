using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace test3.Models
{
    public class Sanpham
    {
        public string masp { get; set; }
        public string tensanpham { get; set; }
        public int soluong { get; set; }
        public double giatien { get; set; }
        public bool giamgia { get; set; } // Đổi sang kiểu bool

        public double thanhtien
        {
            get
            {
                // Kiểm tra trực tiếp biến bool
                if (giamgia)
                    return soluong * giatien * 0.9;
                else
                    return soluong * giatien;
            }
        }
    }
    }