using System;
using System.ComponentModel.DataAnnotations.Schema;
using System.Data.Entity;
using System.Linq;

namespace KTHP1.Models
{
    public partial class Model1 : DbContext
    {
        public Model1()
            : base("name=shit")
        {
        }

        public virtual DbSet<BoPhan> BoPhans { get; set; }
        public virtual DbSet<TiepVien> TiepViens { get; set; }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
        }
    }
}
