namespace KTHP4.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("TiepVien")]
    public partial class TiepVien
    {
        [Key]
        public int MaTV { get; set; }

        [Required]
        [StringLength(100)]
        public string MatKhau { get; set; }

        [Required]
        [StringLength(30)]
        public string HoTen { get; set; }

        [Column(TypeName = "date")]
        [DataType(DataType.Date)]
        [DisplayFormat(DataFormatString = "{0:yyyy-MM-dd}", ApplyInEditMode = true)]
        public DateTime? NgaySinh { get; set; }

        public int Luong { get; set; }

        public int? SoLuongChuyenBay { get; set; }

        public int? MaBoPhan { get; set; }

        [StringLength(255)]
        public string Anh { get; set; }

        public virtual BoPhan BoPhan { get; set; }
    }
}
