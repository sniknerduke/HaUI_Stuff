namespace tx2._1.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("Product")]
    public partial class Product
    {
        [Key]
        [DisplayName("Mã r??u")]
        public int ProductID { get; set; }

        [Required(ErrorMessage = "Tên r??u không ???c ?? tr?ng!")]
        [StringLength(50)]
        [DisplayName("Tên r??u")]
        public string ProductName { get; set; }

        [DisplayName("Mô t?")]
        public string Description { get; set; }

        [Required(ErrorMessage = "Giá nh?p không ???c ?? tr?ng!")]
        [DisplayName("Giá nh?p")]
        public decimal PurchasePrice { get; set; }

        [Required(ErrorMessage = "Giá bán không ???c ?? tr?ng!")]
        [DisplayName("Giá bán")]
        public decimal Price { get; set; }

        [Required(ErrorMessage = "S? l??ng không ???c ?? tr?ng!")]
        [DisplayName("S? l??ng")]
        public int? Quantity { get; set; }

        [StringLength(20)]
        [DisplayName("N?m s?n xu?t")]
        public string Vintage { get; set; }

        [Required(ErrorMessage = "Danh m?c không ???c ?? tr?ng!")]
        public string CatalogyID { get; set; }

        [DisplayName("Hình ?nh")]
        public string Image { get; set; }

        [Required(ErrorMessage = "Vùng không ???c ?? tr?ng!")]
        [StringLength(100)]
        [DisplayName("Vùng")]
        public string Region { get; set; }

        public virtual Catalogy Catalogy { get; set; }
    }
}
