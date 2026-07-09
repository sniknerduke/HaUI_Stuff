class SanPham:
    def __init__(self, ma_sp, ten_sp, gia_ban):
        self.ma_sp = ma_sp
        self.ten_sp = ten_sp
        self.gia_ban = gia_ban
    def __str__(self):
        return f"[{self.ma_sp}] {self.ten_sp:<20} | Giá: {self.gia_ban:,.0f}"
class DonHang:
    def __init__(self, ma_don, ngay_lap):
        self.ma_don = ma_don
        self.ngay_lap = ngay_lap
        self.danh_sach_sp = []
    def them_san_pham(self, sp):
        self.danh_sach_sp.append(sp)
    def tinh_tong_tien(self):
        return sum(sp.gia_ban for sp in self.danh_sach_sp)
    def __str__(self):
        s = f"\n--- ĐƠN HÀNG {self.ma_don} ({self.ngay_lap}) ---\n"
        for sp in self.danh_sach_sp:
            s += str(sp) + "\n"
        s += f"-> TỔNG TIỀN HÀNG: {self.tinh_tong_tien():,.0f} VNĐ"
        return s
    def __add__(self, other):
        ma_moi = f"GOP-{self.ma_don}-{other.ma_don}"
        don_moi = DonHang(ma_moi, self.ngay_lap)
        don_moi.danh_sach_sp = self.danh_sach_sp + other.danh_sach_sp
        return don_moi
class DonHangOnline(DonHang):
    def __init__(self, ma_don, ngay_lap, dia_chi, cuoc_phi):
        super().__init__(ma_don, ngay_lap)
        self.dia_chi = dia_chi
        self.cuoc_phi = cuoc_phi
    def tinh_tong_tien(self):
        tong_tien_hang = super().tinh_tong_tien()
        return tong_tien_hang + self.cuoc_phi
    def __str__(self):
        base_str = super().__str__()
        them_thong_tin = f"\n   + Phí vận chuyển: {self.cuoc_phi:,.0f}" \
                         f"\n   + Địa chỉ: {self.dia_chi}" \
                         f"\n=> TỔNG THANH TOÁN: {self.tinh_tong_tien():,.0f} VNĐ"
        return base_str + them_thong_tin
def nhap_san_pham():
    print("\n--- Nhập thông tin Sản phẩm ---")
    ma = input("Mã SP: ")
    ten = input("Tên SP: ")
    gia = float(input("Giá bán: "))
    return SanPham(ma, ten, gia)
def nhap_don_hang_online():
    print("\n--- Nhập thông tin Đơn hàng Online ---")
    ma = input("Mã đơn: ")
    ngay = input("Ngày lập: ")
    dc = input("Địa chỉ giao: ")
    phi = float(input("Phí vận chuyển: "))
    return DonHangOnline(ma, ngay, dc, phi)
def nhap_san_pham_vao_don(don_hang, ds_sp_co_san):
    print(f"\n--- Thêm sản phẩm vào đơn {don_hang.ma_don} ---")
    print("Danh sách sản phẩm có sẵn:")
    for i, sp in enumerate(ds_sp_co_san, 1):
        print(f"  {i}. {sp.ten_sp} - {sp.gia_ban:,.0f} VNĐ")
    while True:
        chon = input("Chọn SP (nhập số, 0 để dừng): ")
        if chon == "0":
            break
        try:
            idx = int(chon) - 1
            if 0 <= idx < len(ds_sp_co_san):
                don_hang.them_san_pham(ds_sp_co_san[idx])
                print(f"  -> Đã thêm {ds_sp_co_san[idx].ten_sp}")
        except:
            print("Lựa chọn không hợp lệ.")
def in_dong_ke_sp():
    print("+" + "-"*10 + "+" + "-"*22 + "+" + "-"*18 + "+")
def in_bang_san_pham(ds_sp, tieu_de="DANH SÁCH SẢN PHẨM"):
    print("\n" + "="*54)
    print(f"{tieu_de:^54}")
    in_dong_ke_sp()
    print(f"|{'Mã SP':^10}|{'Tên sản phẩm':^22}|{'Giá bán':^18}|")
    in_dong_ke_sp()
    for sp in ds_sp:
        print(f"|{sp.ma_sp:^10}|{sp.ten_sp:^22}|{sp.gia_ban:>15,} VNĐ|")
    in_dong_ke_sp()
def in_don_hang(don_hang, loai="online"):
    dong_ke = "+" + "-"*10 + "+" + "-"*22 + "+" + "-"*18 + "+"
    dong_ke_info = "+" + "-"*20 + "+" + "-"*31 + "+"
    print("\n" + "="*54)
    print(f"{'ĐƠN HÀNG: ' + don_hang.ma_don:^54}")
    print("="*54)
    print(dong_ke_info)
    print(f"|{'Mã đơn':^20}|{don_hang.ma_don:^31}|")
    print(f"|{'Ngày lập':^20}|{don_hang.ngay_lap:^31}|")
    if loai == "online" and hasattr(don_hang, 'dia_chi'):
        print(f"|{'Địa chỉ':^20}|{don_hang.dia_chi:^31}|")
        print(f"|{'Phí vận chuyển':^20}|{don_hang.cuoc_phi:>28,} VNĐ|")
    print(dong_ke_info)
    print(f"\n{'--- Chi tiết sản phẩm ---':^54}")
    print(dong_ke)
    print(f"|{'Mã SP':^10}|{'Tên sản phẩm':^22}|{'Giá bán':^18}|")
    print(dong_ke)
    for sp in don_hang.danh_sach_sp:
        print(f"|{sp.ma_sp:^10}|{sp.ten_sp:^22}|{sp.gia_ban:>15,} VNĐ|")
    print(dong_ke)
    tong_tien_hang = sum(sp.gia_ban for sp in don_hang.danh_sach_sp)
    print(f"|{'Tổng tiền hàng:':^34}|{tong_tien_hang:>15,} VNĐ|")
    if loai == "online" and hasattr(don_hang, 'cuoc_phi'):
        print(f"|{'Phí vận chuyển:':^34}|{don_hang.cuoc_phi:>15,} VNĐ|")
        print(dong_ke)
        print(f"|{'TỔNG THANH TOÁN:':^34}|{don_hang.tinh_tong_tien():>15,} VNĐ|")
    print(dong_ke)
def in_don_hang_gop(don_gop):
    dong_ke = "+" + "-"*10 + "+" + "-"*22 + "+" + "-"*18 + "+"
    print("\n" + "="*54)
    print(f"{'ĐƠN HÀNG GỘP':^54}")
    print("="*54)
    print(f"|{'Mã đơn gộp:':^20} {don_gop.ma_don:^31}|")
    print(f"|{'Ngày lập:':^20} {don_gop.ngay_lap:^31}|")
    print("\n" + dong_ke)
    print(f"|{'Mã SP':^10}|{'Tên sản phẩm':^22}|{'Giá bán':^18}|")
    print(dong_ke)
    for sp in don_gop.danh_sach_sp:
        print(f"|{sp.ma_sp:^10}|{sp.ten_sp:^22}|{sp.gia_ban:>15,} VNĐ|")
    print(dong_ke)
    print(f"|{'TỔNG TIỀN HÀNG:':^34}|{don_gop.tinh_tong_tien():>15,} VNĐ|")
    print(dong_ke)
def main_nhap_tu_ban_phim():
    """Hàm nhập từ bàn phím"""
    ds_sp = []
    n = int(input("Nhập số lượng sản phẩm: "))
    for i in range(n):
        print(f"\nSản phẩm thứ {i+1}:")
        ds_sp.append(nhap_san_pham())
    in_bang_san_pham(ds_sp)
    dh1 = nhap_don_hang_online()
    nhap_san_pham_vao_don(dh1, ds_sp)
    dh2 = nhap_don_hang_online()
    nhap_san_pham_vao_don(dh2, ds_sp)
    in_don_hang(dh1, "online")
    in_don_hang(dh2, "online")
    dh_gop = dh1 + dh2
    in_don_hang_gop(dh_gop)
def main():
    """Hàm chạy với dữ liệu mẫu"""
    sp1 = SanPham("SP01", "Laptop Dell", 15000000)
    sp2 = SanPham("SP02", "Chuột Logitech", 500000)
    sp3 = SanPham("SP03", "Bàn phím Cơ", 2000000)
    in_bang_san_pham([sp1, sp2, sp3])
    print("\n" + "="*54)
    print(f"{'KHỞI TẠO 2 ĐƠN HÀNG ONLINE':^54}")
    print("="*54)
    dh1 = DonHangOnline("DH01", "01/02/2026", "Hà Nội", 30000)
    dh1.them_san_pham(sp1)
    dh1.them_san_pham(sp2)
    dh2 = DonHangOnline("DH02", "01/02/2026", "Hồ Chí Minh", 50000)
    dh2.them_san_pham(sp3)
    in_don_hang(dh1, "online")
    in_don_hang(dh2, "online")
    print("\n" + "="*54)
    print(f"{'SAU KHI GỘP 2 ĐƠN HÀNG (dùng toán tử +)':^54}")
    print("="*54)
    dh_gop = dh1 + dh2
    in_don_hang_gop(dh_gop)
if __name__ == "__main__":
    main()
