from abc import ABC, abstractmethod

# 1. Lớp Trừu tượng (Cha)
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

# 2. Lớp Công Nhân (Con)
class CongNhan(CanBo):
    def __init__(self, ma_cb, ho_ten, nam_sinh, ngay_cong):
        super().__init__(ma_cb, ho_ten, nam_sinh)
        self.ngay_cong = ngay_cong

    def tinh_luong(self):
        return self.ngay_cong * 500000

    def __str__(self):
        return f"Công Nhân: {super().__str__()} | Lương: {self.tinh_luong():,.0f} VNĐ"

# 3. Lớp Kỹ Sư (Con)
class KySu(CanBo):
    def __init__(self, ma_cb, ho_ten, nam_sinh, he_so_luong):
        super().__init__(ma_cb, ho_ten, nam_sinh)
        self.he_so_luong = he_so_luong

    def tinh_luong(self):
        return self.he_so_luong * 2000000

    def __str__(self):
        return f"Kỹ Sư:   {super().__str__()} | Lương: {self.tinh_luong():,.0f} VNĐ"

# --- CHƯƠNG TRÌNH CHÍNH (DÙNG DICT) ---
def main():
    # DICT: key = mã cán bộ, value = đối tượng CanBo
    ds_nhan_vien = {
        "CN01": CongNhan("CN01", "Nguyen Van A", 1990, 26),
        "CN02": CongNhan("CN02", "Tran Thi B", 1995, 24),
        "KS01": KySu("KS01", "Le Van C", 1988, 5.0),
        "KS02": KySu("KS02", "Pham Thi D", 1992, 3.5)
    }

    print("--- BẢNG LƯƠNG TOÀN CÔNG TY ---")
    # In tất cả bằng comprehension
    [print(nv) for nv in ds_nhan_vien.values()]
    
    # Tính tổng lương bằng comprehension
    tong_luong = sum(nv.tinh_luong() for nv in ds_nhan_vien.values())
    print(f"\n=> TỔNG TIỀN LƯƠNG PHẢI TRẢ: {tong_luong:,.0f} VNĐ")

    # Lọc Kỹ sư bằng dict comprehension
    print("\n--- DANH SÁCH KỸ SƯ (Lọc bằng dict comprehension) ---")
    ds_ky_su = {k: v for k, v in ds_nhan_vien.items() if isinstance(v, KySu)}
    [print(f"- {nv.ho_ten} (Hệ số: {nv.he_so_luong})") for nv in ds_ky_su.values()]

    # Lọc Công nhân bằng dict comprehension
    print("\n--- DANH SÁCH CÔNG NHÂN ---")
    ds_cong_nhan = {k: v for k, v in ds_nhan_vien.items() if isinstance(v, CongNhan)}
    [print(f"- {nv.ho_ten} (Ngày công: {nv.ngay_cong})") for nv in ds_cong_nhan.values()]

    # Tìm kiếm theo mã
    print("\n--- TÌM KIẾM THEO MÃ ---")
    ma_tim = input("Nhập mã cán bộ: ")
    ket_qua = ds_nhan_vien.get(ma_tim)
    print(ket_qua if ket_qua else "Không tìm thấy!")

    # Lọc lương cao (>= 10 triệu) bằng dict comprehension
    print("\n--- NHÂN VIÊN LƯƠNG >= 10 TRIỆU ---")
    luong_cao = {k: v for k, v in ds_nhan_vien.items() if v.tinh_luong() >= 10000000}
    [print(f"- {nv.ho_ten}: {nv.tinh_luong():,.0f} VNĐ") for nv in luong_cao.values()]

if __name__ == "__main__":
    main()
