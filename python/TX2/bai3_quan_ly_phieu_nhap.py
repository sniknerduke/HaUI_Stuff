from abc import ABC, abstractmethod
class QuanLyNhapXuat(ABC):
    @abstractmethod
    def nhap_thong_tin(self):
        pass
    @abstractmethod
    def xuat_thong_tin(self):
        pass
class NhaCungCap(QuanLyNhapXuat):
    def __init__(self, ma_so="", ten="", dia_chi=""):
        self.ma_so = ma_so
        self.ten = ten
        self.dia_chi = dia_chi
    def nhap_thong_tin(self):
        print("--- Nhập thông tin Nhà cung cấp ---")
        self.ma_so = input("Mã số NCC: ")
        self.ten = input("Tên NCC: ")
        self.dia_chi = input("Địa chỉ: ")
    def xuat_thong_tin(self):
        print(f"Mã số: {self.ma_so:<15} Tên NCC: {self.ten}")
        print(f"Địa chỉ: {self.dia_chi}")
class MatHang(QuanLyNhapXuat):
    def __init__(self, ten_hang="", don_gia=0.0, so_luong=0):
        self.ten_hang = ten_hang
        self.don_gia = don_gia
        self.so_luong = so_luong
    @property
    def thanh_tien(self):
        return self.don_gia * self.so_luong
    def nhap_thong_tin(self):
        self.ten_hang = input("Tên hàng: ")
        self.don_gia = float(input("Đơn giá: "))
        self.so_luong = int(input("Số lượng: "))
    def xuat_thong_tin(self):
        print(f"{self.ten_hang:<20} {self.don_gia:<10,.0f} {self.so_luong:<10} {self.thanh_tien:<15,.0f}")
class PhieuNhap(QuanLyNhapXuat):
    def __init__(self):
        self.ma_phieu = ""
        self.ngay_lap = ""
        self.ncc = NhaCungCap()
        self.ds_hang = []
    def nhap_thong_tin(self):
        print("\n=== NHẬP PHIẾU ===")
        self.ma_phieu = input("Mã phiếu: ")
        self.ngay_lap = input("Ngày lập: ")
        self.ncc.nhap_thong_tin()
        n = int(input("Nhập số lượng mặt hàng: "))
        for i in range(n):
            print(f"-> Nhập hàng thứ {i+1}:")
            mh = MatHang()
            mh.nhap_thong_tin()
            self.ds_hang.append(mh)
    def xuat_thong_tin(self):
        dong_ke = "+" + "-"*22 + "+" + "-"*12 + "+" + "-"*12 + "+" + "-"*17 + "+"
        print("\n" + "="*67)
        print(f"{'PHIẾU NHẬP HÀNG':^67}")
        print("="*67)
        print(f"| Mã phiếu: {self.ma_phieu:<20} | Ngày lập: {self.ngay_lap:<20} |")
        print(f"| Mã NCC: {self.ncc.ma_so:<22} | Tên NCC: {self.ncc.ten:<20} |")
        print(f"| Địa chỉ: {self.ncc.dia_chi:<53} |")
        print(dong_ke)
        print(f"|{'Tên hàng':^22}|{'Đơn giá':^12}|{'Số lượng':^12}|{'Thành tiền':^17}|")
        print(dong_ke)
        for mh in self.ds_hang:
            print(f"|{mh.ten_hang:^22}|{mh.don_gia:>11,} |{mh.so_luong:>11,} |{mh.thanh_tien:>16,} |")
        tong_tien = sum(mh.thanh_tien for mh in self.ds_hang)
        print(dong_ke)
        print(f"|{'TỔNG CỘNG:':^48}|{tong_tien:>16,} |")
        print(dong_ke)
def main_nhap_tu_ban_phim():
    """Hàm nhập từ bàn phím"""
    phieu = PhieuNhap()
    phieu.nhap_thong_tin()
    phieu.xuat_thong_tin()
def main():
    """Hàm chạy với dữ liệu mẫu"""
    phieu = PhieuNhap()
    phieu.ma_phieu = "PN001"
    phieu.ngay_lap = "01/02/2026"
    phieu.ncc = NhaCungCap("NCC01", "Công ty ABC", "123 Nguyễn Trãi, HN")
    phieu.ds_hang = [
        MatHang("Laptop Dell", 15000000, 5),
        MatHang("Chuột Logitech", 500000, 20),
        MatHang("Bàn phím cơ", 2000000, 10),
        MatHang("Màn hình LG", 8000000, 3)
    ]
    phieu.xuat_thong_tin()
if __name__ == "__main__":
    main()  # Chạy với dữ liệu mẫu
