class MonHoc:
    def __init__(self, ma_mon, ten_mon, so_tin_chi):
        self.ma_mon = ma_mon
        self.ten_mon = ten_mon
        self.so_tin_chi = so_tin_chi
    def __str__(self):
        return f"[{self.ma_mon}] {self.ten_mon} ({self.so_tin_chi} tín)"
class SinhVien:
    def __init__(self, ma_sv, ho_ten):
        self.ma_sv = ma_sv
        self.ho_ten = ho_ten
        self.bang_diem = {}
    def them_diem(self, mon_hoc, diem):
        self.bang_diem[mon_hoc] = diem
    def diem_trung_binh(self):
        tong_diem_tin = 0
        tong_tin_chi = 0
        for mon, diem in self.bang_diem.items():
            tong_diem_tin += diem * mon.so_tin_chi
            tong_tin_chi += mon.so_tin_chi
        if tong_tin_chi == 0:
            return 0
        return tong_diem_tin / tong_tin_chi
    def __str__(self):
        dtb = self.diem_trung_binh()
        return f"SV: {self.ho_ten} ({self.ma_sv}) - ĐTB: {dtb:.2f}"
    def __eq__(self, other):
        return self.diem_trung_binh() == other.diem_trung_binh()
    def __lt__(self, other):
        return self.diem_trung_binh() < other.diem_trung_binh()
class SinhVienChinhQuy(SinhVien):
    def __init__(self, ma_sv, ho_ten, lop_hoc):
        super().__init__(ma_sv, ho_ten)
        self.lop_hoc = lop_hoc
    def __str__(self):
        return f"{super().__str__()} - Lớp: {self.lop_hoc}"
def nhap_mon_hoc():
    print("\n--- Nhập thông tin Môn học ---")
    ma = input("Mã môn: ")
    ten = input("Tên môn: ")
    tc = int(input("Số tín chỉ: "))
    return MonHoc(ma, ten, tc)
def nhap_sinh_vien():
    print("\n--- Nhập thông tin Sinh viên ---")
    ma = input("Mã SV: ")
    ten = input("Họ tên: ")
    lop = input("Lớp học: ")
    return SinhVienChinhQuy(ma, ten, lop)
def nhap_diem(sv, ds_mon):
    print(f"\n--- Nhập điểm cho {sv.ho_ten} ---")
    for mon in ds_mon:
        diem = float(input(f"  Điểm môn {mon.ten_mon}: "))
        sv.them_diem(mon, diem)
def in_dong_ke_mon():
    print("+" + "-"*12 + "+" + "-"*25 + "+" + "-"*10 + "+")
def in_bang_mon_hoc(ds_mon):
    print("\n" + "="*51)
    print(f"{'DANH SÁCH MÔN HỌC':^51}")
    in_dong_ke_mon()
    print(f"|{'Mã môn':^12}|{'Tên môn':^25}|{'Tín chỉ':^10}|")
    in_dong_ke_mon()
    for mon in ds_mon:
        print(f"|{mon.ma_mon:^12}|{mon.ten_mon:^25}|{mon.so_tin_chi:^10}|")
    in_dong_ke_mon()
def in_dong_ke_sv():
    print("+" + "-"*10 + "+" + "-"*20 + "+" + "-"*10 + "+" + "-"*10 + "+")
def in_bang_sinh_vien(ds_sv):
    print("\n" + "="*54)
    print(f"{'KẾT QUẢ HỌC TẬP':^54}")
    in_dong_ke_sv()
    print(f"|{'Mã SV':^10}|{'Họ tên':^20}|{'Lớp':^10}|{'ĐTB':^10}|")
    in_dong_ke_sv()
    for sv in ds_sv:
        print(f"|{sv.ma_sv:^10}|{sv.ho_ten:^20}|{sv.lop_hoc:^10}|{sv.diem_trung_binh():^10.2f}|")
    in_dong_ke_sv()
def in_bang_diem_chi_tiet(sv):
    print(f"\n--- Bảng điểm chi tiết: {sv.ho_ten} ({sv.ma_sv}) ---")
    dong_ke = "+" + "-"*12 + "+" + "-"*25 + "+" + "-"*10 + "+" + "-"*8 + "+"
    print(dong_ke)
    print(f"|{'Mã môn':^12}|{'Tên môn':^25}|{'Tín chỉ':^10}|{'Điểm':^8}|")
    print(dong_ke)
    for mon, diem in sv.bang_diem.items():
        print(f"|{mon.ma_mon:^12}|{mon.ten_mon:^25}|{mon.so_tin_chi:^10}|{diem:^8.1f}|")
    print(dong_ke)
    print(f"|{'ĐTB tích lũy:':^49}|{sv.diem_trung_binh():^8.2f}|")
    print(dong_ke)
def main_nhap_tu_ban_phim():
    """Hàm nhập từ bàn phím"""
    ds_mon = []
    n_mon = int(input("Nhập số lượng môn học: "))
    for i in range(n_mon):
        print(f"\nMôn thứ {i+1}:")
        ds_mon.append(nhap_mon_hoc())
    in_bang_mon_hoc(ds_mon)
    ds_sv = []
    n_sv = int(input("\nNhập số lượng sinh viên: "))
    for i in range(n_sv):
        print(f"\nSinh viên thứ {i+1}:")
        sv = nhap_sinh_vien()
        nhap_diem(sv, ds_mon)
        ds_sv.append(sv)
    in_bang_sinh_vien(ds_sv)
    if len(ds_sv) >= 2:
        if ds_sv[0] == ds_sv[1]:
            print(f"{ds_sv[0].ho_ten} có kết quả BẰNG {ds_sv[1].ho_ten}")
        elif ds_sv[0] > ds_sv[1]:
            print(f"{ds_sv[0].ho_ten} có kết quả TỐT HƠN {ds_sv[1].ho_ten}")
        else:
            print(f"{ds_sv[1].ho_ten} có kết quả TỐT HƠN {ds_sv[0].ho_ten}")
def main():
    """Hàm chạy với dữ liệu mẫu"""
    python = MonHoc("IT6130", "Lập trình Python", 3)
    toan = MonHoc("MA101", "Toán cao cấp", 4)
    in_bang_mon_hoc([python, toan])
    sv1 = SinhVienChinhQuy("SV001", "Nguyen Van An", "CNTT1")
    sv2 = SinhVienChinhQuy("SV002", "Tran Thi Binh", "CNTT2")
    sv1.them_diem(python, 8.0)
    sv1.them_diem(toan, 9.0)
    sv2.them_diem(python, 6.0)
    sv2.them_diem(toan, 7.0)
    in_bang_sinh_vien([sv1, sv2])
    in_bang_diem_chi_tiet(sv1)
    in_bang_diem_chi_tiet(sv2)
    print("\n" + "="*50)
    print(f"{'SO SÁNH KẾT QUẢ':^50}")
    print("="*50)
    dong_ke_ss = "+" + "-"*48 + "+"
    print(dong_ke_ss)
    if sv1 == sv2:
        ket_qua = f"{sv1.ho_ten} có kết quả BẰNG {sv2.ho_ten}"
    elif sv1 > sv2:
        ket_qua = f"{sv1.ho_ten} có kết quả TỐT HƠN {sv2.ho_ten}"
    else:
        ket_qua = f"{sv2.ho_ten} có kết quả TỐT HƠN {sv1.ho_ten}"
    print(f"|{ket_qua:^48}|")
    print(dong_ke_ss)
if __name__ == "__main__":
    main()
