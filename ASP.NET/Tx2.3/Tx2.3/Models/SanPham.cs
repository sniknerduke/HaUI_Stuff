namespace Tx2._3.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("SanPham")]
    public partial class SanPham
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.None)]
        [Required(ErrorMessage ="Khong de trong !")]
        [DisplayName("Ma sp")]
        public int MaSP { get; set; }

        [StringLength(100)]
        [Required(ErrorMessage = "Khong de trong !")]
        [DisplayName("Ten sp")]
        public string TenSP { get; set; }
        [Required(ErrorMessage = "Khong de trong !")]
        [DisplayName("SL sp")]
        public int? SoLuong { get; set; }
        [Required(ErrorMessage = "Khong de trong !")]
        [DisplayName("Don gia sp")]
        public decimal? DonGia { get; set; }

        [StringLength(50)]
        [DisplayName("Ha sp")]
        public string HinhAnh { get; set; }
        [DisplayName("Ma hang")]
        public int? MaHang { get; set; }

        public virtual HangSanXuat HangSanXuat { get; set; }
    }
}
