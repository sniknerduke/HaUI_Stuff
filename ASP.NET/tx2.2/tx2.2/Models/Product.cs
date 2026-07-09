namespace tx2._2.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("Product")]
    public partial class Product
    {
        [Key]
        [DisplayName("Mã sản phẩm")]
        public int Pid { get; set; }

        [DisplayName("Mã danh mục")]
        [Required(ErrorMessage = "Danh mục sản phẩm bắt buộc phải chọn!")]
        public int Categoryid { get; set; }

        [Required(ErrorMessage = "Tên sản phẩm không được để trống!")]
        [StringLength(250)]
        [DisplayName("Tên sản phẩm")]
        public string ProdName { get; set; }

        [StringLength(50)]
        public string MetaTitle { get; set; }

        [StringLength(250)]
        [DisplayName("Mô tả sản phẩm")]
        public string Description { get; set; }

        [StringLength(550)]
        [DisplayName("Hình ảnh")]
        public string ImagePath { get; set; }

        [Required(ErrorMessage = "Giá bán không được để trống!")]
        [Range(0, double.MaxValue, ErrorMessage = "Giá bán phải lớn hơn hoặc bằng 0!")]
        [DisplayName("Giá bán tiền")]
        public decimal Price { get; set; }

        public virtual Category Category { get; set; }
    }
}
