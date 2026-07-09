from abc import ABC, abstractmethod
class CanBo(ABC):
    def __init__(self, ma_cb, ho_ten, nam_sinh):
        self.ma_cb = ma_cb
        self.ho_ten = ho_ten
        self.nam_sinh = nam_sinh
    @abstractmethod
    def tinh_luong(self):
        pass
    def __str__(self):
        return f"[{self.ma_cb}] {self.ho_ten} ({self.nam_sinh})"
class CongNhan(CanBo):
    def __init__(self, ma_cb, ho_ten, nam_sinh, ngay_cong):
        super().__init__(ma_cb, ho_ten, nam_sinh)
        self.ngay_cong = ngay_cong
    def tinh_luong(self):
        return self.ngay_cong * 500000
    def __str__(self):
        return f"Công Nhân: {super().__str__()} | Lương: {self.tinh_luong():,.0f} VNĐ"
class KySu(CanBo):
    def __init__(self, ma_cb, ho_ten, nam_sinh, he_so_luong):
        super().__init__(ma_cb, ho_ten, nam_sinh)
        self.he_so_luong = he_so_luong
    def tinh_luong(self):
        return self.he_so_luong * 2000000
    def __str__(self):
        return f"Kỹ Sư:   {super().__str__()} | Lương: {self.tinh_luong():,.0f} VNĐ"
def nhap_cong_nhan():
    print("\n--- Nhập thông tin Công Nhân ---")
    ma = input("Mã CB: ")
    ten = input("Họ tên: ")
    nam_sinh = int(input("Năm sinh: "))
    ngay_cong = int(input("Số ngày công: "))
    return CongNhan(ma, ten, nam_sinh, ngay_cong)
def nhap_ky_su():
    print("\n--- Nhập thông tin Kỹ Sư ---")
    ma = input("Mã CB: ")
    ten = input("Họ tên: ")
    nam_sinh = int(input("Năm sinh: "))
    he_so = float(input("Hệ số lương: "))
    return KySu(ma, ten, nam_sinh, he_so)
def nhap_danh_sach():
    ds = []
    while True:
        try:
            n = int(input("Nhập số lượng nhân viên: "))
            if n > 0: break
            print("Số lượng phải > 0")
        except ValueError:
            print("Lỗi nhập số.")
    for i in range(n):
        print(f"\nNhập nhân viên thứ {i+1}:")
        loai = input("Loại (1: Công nhân, 2: Kỹ sư): ")
        if loai == "1":
            ds.append(nhap_cong_nhan())
        else:
            ds.append(nhap_ky_su())
    return ds
def in_dong_ke():
    print("+" + "-"*12 + "+" + "-"*22 + "+" + "-"*8 + "+" + "-"*15 + "+" + "-"*18 + "+")
def in_header():
    print(f"|{'Loại':^12}|{'Họ tên':^22}|{'Năm sinh':^8}|{'Hệ số/Công':^15}|{'Lương':^18}|")
def in_mot_nhan_vien(nv):
    if isinstance(nv, CongNhan):
        loai = "Công Nhân"
        he_so = f"{nv.ngay_cong} công"
    else:
        loai = "Kỹ Sư"
        he_so = f"HS: {nv.he_so_luong}"
    print(f"|{loai:^12}|{nv.ho_ten:^22}|{nv.nam_sinh:^8}|{he_so:^15}|{nv.tinh_luong():>15,} VNĐ|")
def main():
    ds_nhan_vien = [
        CongNhan("CN01", "Nguyen Van A", 1990, 26),      # Làm 26 công
        CongNhan("CN02", "Tran Thi B", 1995, 24),        # Làm 24 công
        KySu("KS01", "Le Van C", 1988, 5.0),             # Hệ số 5.0
        KySu("KS02", "Pham Thi D", 1992, 3.5)            # Hệ số 3.5
    ]
    print("\n" + "="*79)
    print(f"{'BẢNG LƯƠNG TOÀN CÔNG TY':^79}")
    in_dong_ke()
    in_header()
    in_dong_ke()
    for nv in ds_nhan_vien:
        in_mot_nhan_vien(nv)
    tong_luong = sum(nv.tinh_luong() for nv in ds_nhan_vien)
    in_dong_ke()
    print(f"|{'TỔNG TIỀN LƯƠNG PHẢI TRẢ:':^58}|{tong_luong:>15,} VNĐ|")
    in_dong_ke()
    ds_ky_su = [nv for nv in ds_nhan_vien if isinstance(nv, KySu)]
    print("\n" + "+" + "-"*30 + "+" + "-"*10 + "+")
    print(f"|{'DANH SÁCH KỸ SƯ':^30}|{'Hệ số':^10}|")
    print("+" + "-"*30 + "+" + "-"*10 + "+")
    for nv in ds_ky_su:
        print(f"|{nv.ho_ten:^30}|{nv.he_so_luong:^10}|")
    print("+" + "-"*30 + "+" + "-"*10 + "+")
def main_nhap_tu_ban_phim():
    """Hàm nhập từ bàn phím"""
    ds_nhan_vien = nhap_danh_sach()
    print("\n" + "="*79)
    print(f"{'BẢNG LƯƠNG TOÀN CÔNG TY':^79}")
    in_dong_ke()
    in_header()
    in_dong_ke()
    for nv in ds_nhan_vien:
        in_mot_nhan_vien(nv)
    tong_luong = sum(nv.tinh_luong() for nv in ds_nhan_vien)
    in_dong_ke()
    print(f"|{'TỔNG TIỀN LƯƠNG PHẢI TRẢ:':^58}|{tong_luong:>15,} VNĐ|")
    in_dong_ke()
def main():
    """Hàm chạy với dữ liệu mẫu"""
    ds_nhan_vien = [
        CongNhan("CN01", "Nguyen Van A", 1990, 26),
        CongNhan("CN02", "Tran Thi B", 1995, 24),
        KySu("KS01", "Le Van C", 1988, 5.0),
        KySu("KS02", "Pham Thi D", 1992, 3.5)
    ]
    print("\n" + "="*79)
    print(f"{'BẢNG LƯƠNG TOÀN CÔNG TY':^79}")
    in_dong_ke()
    in_header()
    in_dong_ke()
    for nv in ds_nhan_vien:
        in_mot_nhan_vien(nv)
    tong_luong = sum(nv.tinh_luong() for nv in ds_nhan_vien)
    in_dong_ke()
    print(f"|{'TỔNG TIỀN LƯƠNG PHẢI TRẢ:':^58}|{tong_luong:>15,} VNĐ|")
    in_dong_ke()
    ds_ky_su = [nv for nv in ds_nhan_vien if isinstance(nv, KySu)]
    print("\n" + "+" + "-"*30 + "+" + "-"*10 + "+")
    print(f"|{'DANH SÁCH KỸ SƯ':^30}|{'Hệ số':^10}|")
    print("+" + "-"*30 + "+" + "-"*10 + "+")
    for nv in ds_ky_su:
        print(f"|{nv.ho_ten:^30}|{nv.he_so_luong:^10}|")
    print("+" + "-"*30 + "+" + "-"*10 + "+")
if __name__ == "__main__":
    main()  # Chạy với dữ liệu mẫu
    main()
