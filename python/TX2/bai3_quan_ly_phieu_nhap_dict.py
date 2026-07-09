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
        # Ép kiểu về số để tránh lỗi nếu vô tình giá trị là string
        return float(self.don_gia) * float(self.so_luong)
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
        self.ds_hang = {}
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
            self.ds_hang[mh.ten_hang] = mh
    def xuat_thong_tin(self):
        print("\n" + "="*60)
        print(f"{'PHIẾU NHẬP HÀNG':^60}")
        print(f"Mã phiếu: {self.ma_phieu:<20} Ngày lập: {self.ngay_lap}")
        self.ncc.xuat_thong_tin()
        print("-" * 60)
        print(f"{'Tên hàng':<20} {'Đơn giá':<10} {'Số lượng':<10} {'Thành tiền':<15}")
        print("-" * 60)
        [mh.xuat_thong_tin() for mh in self.ds_hang.values()]
        tong_tien = sum(mh.thanh_tien for mh in self.ds_hang.values())
        print("-" * 60)
        print(f"{'Cộng thành tiền:':<42} {tong_tien:,.0f} VNĐ")
        print("="*60)
    def tim_hang(self, ten):
        """Tìm hàng theo tên (key)"""
        return self.ds_hang.get(ten, None)
    def xoa_hang(self, ten):
        """Xóa hàng theo tên"""
        if ten in self.ds_hang:
            del self.ds_hang[ten]
            print(f"Đã xóa: {ten}")
        else:
            print("Không tìm thấy!")
    def loc_hang_gia_cao(self, muc_gia):
        """Lọc hàng có đơn giá >= mức giá (dict comprehension)"""
        return {k: v for k, v in self.ds_hang.items() if v.don_gia >= muc_gia}
def main():
    phieu = PhieuNhap()
    phieu.nhap_thong_tin()
    phieu.xuat_thong_tin()
    print("\n--- TÌM KIẾM ---")
    ten_tim = input("Nhập tên hàng cần tìm: ")
    ket_qua = phieu.tim_hang(ten_tim)
    if ket_qua:
        print(f"Tìm thấy: {ket_qua.ten_hang} - Giá: {ket_qua.don_gia:,.0f}")
    else:
        print("Không tìm thấy!")
if __name__ == "__main__":
    main()
