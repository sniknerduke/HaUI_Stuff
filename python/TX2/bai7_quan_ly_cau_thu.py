class Nguoi:
    def __init__(self, ho_ten, tuoi, quoc_tich):
        self.ho_ten = ho_ten
        self.tuoi = tuoi
        self.quoc_tich = quoc_tich
    def __str__(self):
        return f"{self.ho_ten:<20} {self.tuoi:<5} {self.quoc_tich:<15}"
class CauLacBo:
    def __init__(self, ten_clb, ma_clb, hlv, nam_tl):
        self.ten_clb = ten_clb
        self.ma_clb = ma_clb
        self.hlv = hlv
        self.nam_tl = nam_tl
    def __str__(self):
        return self.ten_clb
class CauThu(Nguoi):
    def __init__(self, ho_ten, tuoi, quoc_tich, ma_ct, vi_tri, so_ao, ten_clb, ma_clb, hlv, nam_tl):
        super().__init__(ho_ten, tuoi, quoc_tich)
        self.ma_ct = ma_ct
        self.vi_tri = vi_tri
        self.so_ao = so_ao
        # Hợp thành (Composition): CauThu tự tạo và quản lý CauLacBo
        self.cau_lac_bo = CauLacBo(ten_clb, ma_clb, hlv, nam_tl)
        # Hợp thành (Composition): Danh sách các CLB cũ quản lý bên trong
        self.ds_clb_cu = []

    def them_clb_cu(self, ten_clb, ma_clb, hlv, nam_tl):
        """Tạo đối tượng CauLacBo và thêm vào lịch sử (Hợp thành)"""
        clb = CauLacBo(ten_clb, ma_clb, hlv, nam_tl)
        self.ds_clb_cu.append(clb)

    def __str__(self):
        lich_su = ", ".join([c.ten_clb for c in self.ds_clb_cu]) if self.ds_clb_cu else "Không"
        return f"{super().__str__()} | {self.ma_ct:<10} {self.vi_tri:<10} {self.so_ao:<5} | CLB: {self.cau_lac_bo.ten_clb:<10} | Cũ: {lich_su}"
def nhap_cau_lac_bo():
    print("\n--- Nhập thông tin Câu lạc bộ ---")
    ten = input("Tên CLB: ")
    ma = input("Mã CLB: ")
    hlv = input("Tên HLV: ")
    nam = int(input("Năm thành lập: "))
    return CauLacBo(ten, ma, hlv, nam)
def nhap_cau_thu(clb):
    print("\n--- Nhập thông tin Cầu thủ ---")
    ten = input("  Họ và tên: ")
    tuoi = int(input("  Tuổi: "))
    qt = input("  Quốc tịch: ")
    ma = input("  Mã cầu thủ: ")
    vt = input("  Vị trí: ")
    so = int(input("  Số áo: "))
    return CauThu(ten, tuoi, qt, ma, vt, so, clb.ten_clb, clb.ma_clb, clb.hlv, clb.nam_tl)
def nhap_danh_sach_cau_thu(clb):
    ds = []
    while True:
        try:
            n = int(input("Nhập số lượng cầu thủ (n > 3): "))
            if n > 3: break
            print("Yêu cầu nhập n > 3")
        except ValueError:
            print("Lỗi nhập số.")
    for i in range(n):
        print(f"\nNhập cầu thủ thứ {i+1}:")
        ds.append(nhap_cau_thu(clb))
    return ds
def in_dong_ke():
    print("+" + "-"*22 + "+" + "-"*6 + "+" + "-"*15 + "+" + "-"*10 + "+" + "-"*12 + "+" + "-"*7 + "+" + "-"*12 + "+" + "-"*20 + "+")
def in_header():
    print(f"|{'Họ tên':^22}|{'Tuổi':^6}|{'Quốc tịch':^15}|{'Mã CT':^10}|{'Vị trí':^12}|{'Số áo':^7}|{'CLB':^12}|{'CLB Cũ':^20}|")
def in_cau_thu(ct):
    lich_su = ", ".join([c.ten_clb for c in ct.ds_clb_cu]) if ct.ds_clb_cu else ""
    print(f"|{ct.ho_ten:^22}|{ct.tuoi:^6}|{ct.quoc_tich:^15}|{ct.ma_ct:^10}|{ct.vi_tri:^12}|{ct.so_ao:^7}|{ct.cau_lac_bo.ten_clb:^12}|{lich_su:^20}|")
def in_bang_cau_thu(ds_cau_thu, tieu_de="DANH SÁCH CẦU THỦ"):
    print("\n" + "="*112)
    print(f"{tieu_de:^112}")
    in_dong_ke()
    in_header()
    in_dong_ke()
    for ct in ds_cau_thu:
        in_cau_thu(ct)
    in_dong_ke()
def main_nhap_tu_ban_phim():
    """Hàm nhập từ bàn phím"""
    clb = nhap_cau_lac_bo()
    ds_cau_thu = nhap_danh_sach_cau_thu(clb)
    in_bang_cau_thu(ds_cau_thu, "DANH SÁCH VỪA NHẬP")
    for ct in ds_cau_thu:
        if ct.ho_ten.strip() == "Cristiano Ronaldo":
            ct.so_ao = 7
            print(f"| -> Đã cập nhật số áo của {ct.ho_ten} thành 7.")
    dem_tre = len([ct for ct in ds_cau_thu if ct.tuoi < 20])
    print(f"\nSố cầu thủ < 20 tuổi: {dem_tre}")
    ds_cau_thu.sort(key=lambda x: x.so_ao)
    in_bang_cau_thu(ds_cau_thu, "DANH SÁCH SAU KHI SẮP XẾP")
def main():
    """Hàm chạy với dữ liệu mẫu"""
    clb_mu = CauLacBo("Man Utd", "MU01", "Erik ten Hag", 1878)
    print("\n" + "="*50)
    print(f"{'1. THÔNG TIN CÂU LẠC BỘ':^50}")
    print("="*50)
    print("+" + "-"*20 + "+" + "-"*25 + "+")
    print(f"|{'Tên CLB':^20}|{clb_mu.ten_clb:^25}|")
    print(f"|{'Mã CLB':^20}|{clb_mu.ma_clb:^25}|")
    print(f"|{'HLV':^20}|{clb_mu.hlv:^25}|")
    print(f"|{'Năm thành lập':^20}|{clb_mu.nam_tl:^25}|")
    print("+" + "-"*20 + "+" + "-"*25 + "+")
    
    # Lấy thông tin từ clb_mu để truyền vào (Composition)
    t, m, h, n = clb_mu.ten_clb, clb_mu.ma_clb, clb_mu.hlv, clb_mu.nam_tl
    ds_cau_thu = [
        CauThu("Cristiano Ronaldo", 39, "Bồ Đào Nha", "CR7", "Tiền đạo", 9, t, m, h, n),
        CauThu("Marcus Rashford", 26, "Anh", "MR10", "Tiền đạo", 10, t, m, h, n),
        CauThu("Bruno Fernandes", 29, "Bồ Đào Nha", "BF8", "Tiền vệ", 8, t, m, h, n),
        CauThu("Kobbie Mainoo", 19, "Anh", "KM37", "Tiền vệ", 37, t, m, h, n)
    ]
    # Thêm lịch sử CLB (Hợp thành) - Chỉ tạo dữ liệu, object CLB được sinh ra bên trong CT
    ds_cau_thu[0].them_clb_cu("Real Madrid", "RMA", "Ancelotti", 1902)
    ds_cau_thu[0].them_clb_cu("Juventus", "JUV", "Allegri", 1897)

    in_bang_cau_thu(ds_cau_thu, "2. DANH SÁCH CẦU THỦ MẪU")
    print("\n" + "="*50)
    print(f"{'3. CẬP NHẬT SỐ ÁO RONALDO':^50}")
    print("="*50)
    tim_thay = False
    for ct in ds_cau_thu:
        if ct.ho_ten.strip() == "Cristiano Ronaldo":
            ct.so_ao = 7
            print(f"| -> Đã cập nhật số áo của {ct.ho_ten} thành 7.")
            tim_thay = True
    if not tim_thay:
        print("| -> Không tìm thấy cầu thủ Cristiano Ronaldo.")
    dem_tre = len([ct for ct in ds_cau_thu if ct.tuoi < 20])
    print("\n" + "="*50)
    print(f"{'4. THỐNG KÊ TUỔI':^50}")
    print("="*50)
    print("+" + "-"*35 + "+" + "-"*12 + "+")
    print(f"|{'Số cầu thủ nhỏ hơn 20 tuổi':^35}|{dem_tre:^12}|")
    print("+" + "-"*35 + "+" + "-"*12 + "+")
    ds_cau_thu.sort(key=lambda x: x.so_ao)
    in_bang_cau_thu(ds_cau_thu, "5. DANH SÁCH SAU KHI SẮP XẾP (Theo số áo tăng dần)")
if __name__ == "__main__":
    main()
