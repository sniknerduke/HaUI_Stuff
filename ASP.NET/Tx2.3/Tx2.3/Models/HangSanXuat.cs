namespace Tx2._3.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("HangSanXuat")]
    public partial class HangSanXuat
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public HangSanXuat()
        {
            SanPhams = new HashSet<SanPham>();
        }

        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.None)]
        public int MaHang { get; set; }

        [StringLength(100)]
        public string TenHang { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<SanPham> SanPhams { get; set; }
    }
}
