using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace tx1.Models
{
    public class SanPham
    {
        [Required(ErrorMessage = "Vui lòng nhập mã sản phẩm")]
        public string masp { get; set; }

        [Required(ErrorMessage = "Vui lòng chọn hãng sản xuất")]
        [RegularExpression("Apple|Xiaomi", ErrorMessage = "Hãng sản xuất không hợp lệ")]
        public string hangsx { get; set; }

        [Required(ErrorMessage = "Vui lòng nhập tên sản phẩm")]
        public string tensp { get; set; }

        [Required(ErrorMessage = "Vui lòng nhập số lượng")]
        [Range(1, int.MaxValue, ErrorMessage = "Số lượng phải lớn hơn 0")]
        public int? soluong { get; set; }

        [Required(ErrorMessage = "Vui lòng nhập giá tiền")]
        [Range(1, int.MaxValue, ErrorMessage = "Giá tiền phải lớn hơn 0")]
        public int? giatien { get; set; }

        public int tongtien
        {
            get { return (soluong ?? 0) * (giatien ?? 0); }
        }
    }
}