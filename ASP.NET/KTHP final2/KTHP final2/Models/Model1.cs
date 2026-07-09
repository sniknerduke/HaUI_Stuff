using System;
using System.ComponentModel.DataAnnotations.Schema;
using System.Data.Entity;
using System.Linq;

namespace KTHP_final2.Models
{
    public partial class Model1 : DbContext
    {
        public Model1()
            : base("name=Model2")
        {
        }

        public virtual DbSet<BoPhan> BoPhans { get; set; }
        public virtual DbSet<TiepVien> TiepViens { get; set; }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
        }
    }
}
