import os

# --- CÂU 2 ---

class Nguoi:
    def __init__(self, ho_ten, ngay_sinh, dia_chi):
        self.ho_ten = ho_ten
        self.ngay_sinh = ngay_sinh
        self.dia_chi = dia_chi

    def __str__(self):
        return f"{self.ho_ten:<20} {self.ngay_sinh:<12} {self.dia_chi:<15}"

class GiaoVien(Nguoi):
    def __init__(self, ho_ten, ngay_sinh, dia_chi, mon_day, trinh_do, so_nam_ct):
        # Gọi hàm khởi tạo của lớp cha
        super().__init__(ho_ten, ngay_sinh, dia_chi)
        self.mon_day = mon_day
        self.trinh_do = trinh_do
        self.so_nam_ct = so_nam_ct

    # Cài đặt toán tử < (less than) để so sánh 2 đối tượng
    def __lt__(self, other):
        return self.so_nam_ct < other.so_nam_ct

    def __str__(self):
        # Kế thừa chuỗi in của lớp cha và nối thêm thông tin riêng
        base_str = super().__str__()
        return f"{base_str} {self.mon_day:<15} {self.trinh_do:<10} {self.so_nam_ct:<5}"

# --- HÀM IN BẢNG ---
def in_dong_ke():
    print("+" + "-"*22 + "+" + "-"*14 + "+" + "-"*17 + "+" + "-"*17 + "+" + "-"*12 + "+" + "-"*8 + "+")

def in_header():
    print(f"|{'Họ tên':^22}|{'Ngày sinh':^14}|{'Địa chỉ':^17}|{'Môn dạy':^17}|{'Trình độ':^12}|{'Năm CT':^8}|")

def in_giao_vien(gv):
    print(f"|{gv.ho_ten:^22}|{gv.ngay_sinh:^14}|{gv.dia_chi:^17}|{gv.mon_day:^17}|{gv.trinh_do:^12}|{gv.so_nam_ct:^8}|")

def in_bang_giao_vien(ds_gv, tieu_de="DANH SÁCH GIÁO VIÊN"):
    print("\n" + "="*96)
    print(f"{tieu_de:^96}")
    in_dong_ke()
    in_header()
    in_dong_ke()
    for gv in ds_gv:
        in_giao_vien(gv)
    in_dong_ke()

def ghi_file(ds_gv):
    """Ghi danh sách giáo viên ra file"""
    thu_muc_hien_tai = os.path.dirname(os.path.abspath(__file__))
    duong_dan_file = os.path.join(thu_muc_hien_tai, "GIAOVIEN.TXT")
    
    try:
        with open(duong_dan_file, "w", encoding="utf-8") as f:
            dong_ke_str = "+" + "-"*22 + "+" + "-"*14 + "+" + "-"*17 + "+" + "-"*17 + "+" + "-"*12 + "+" + "-"*8 + "+"
            header_str = f"|{'Họ tên':^22}|{'Ngày sinh':^14}|{'Địa chỉ':^17}|{'Môn dạy':^17}|{'Trình độ':^12}|{'Năm CT':^8}|"
            
            f.write(dong_ke_str + "\n")
            f.write(header_str + "\n")
            f.write(dong_ke_str + "\n")
            
            for gv in ds_gv:
                gv_str = f"|{gv.ho_ten:^22}|{gv.ngay_sinh:^14}|{gv.dia_chi:^17}|{gv.mon_day:^17}|{gv.trinh_do:^12}|{gv.so_nam_ct:^8}|"
                f.write(gv_str + "\n")
            
            f.write(dong_ke_str + "\n")
        print(f"\n=> Đã ghi kết quả vào file '{duong_dan_file}' thành công.")
    except Exception as e:
        print(f"Lỗi khi ghi file: {e}")

# --- CHƯƠNG TRÌNH CHÍNH ---
def main_nhap_tu_ban_phim():
    """Hàm nhập từ bàn phím"""
    print("\n=== NHẬP DANH SÁCH GIÁO VIÊN ===")
    ds_giao_vien = []
    
    while True:
        try:
            n = int(input("Nhập số lượng giáo viên (n > 3): "))
            if n > 3: break
            print("Yêu cầu nhập n > 3.")
        except ValueError:
            print("Lỗi nhập số.")

    for i in range(n):
        print(f"\nNhập thông tin GV thứ {i+1}:")
        ht = input("  Họ tên: ")
        ns = input("  Ngày sinh: ")
        dc = input("  Địa chỉ: ")
        mon = input("  Môn dạy: ")
        td = input("  Trình độ (Cử nhân/Thạc sĩ/Tiến sĩ): ")
        
        while True:
            try:
                sn = int(input("  Số năm công tác: "))
                if sn >= 0: break
                print("Số năm phải >= 0")
            except ValueError:
                print("Nhập số nguyên.")
        
        gv = GiaoVien(ht, ns, dc, mon, td, sn)
        ds_giao_vien.append(gv)

    ds_giao_vien.sort()
    in_bang_giao_vien(ds_giao_vien, "DANH SÁCH GIÁO VIÊN (Đã sắp xếp tăng dần thâm niên)")
    ghi_file(ds_giao_vien)

def main():
    """Hàm chạy với dữ liệu mẫu"""
    # Tạo danh sách giáo viên mẫu
    ds_giao_vien = [
        GiaoVien("Nguyen Van An", "15/05/1985", "Hà Nội", "Toán", "Thạc sĩ", 10),
        GiaoVien("Tran Thi Binh", "20/08/1990", "Hải Phòng", "Văn", "Cử nhân", 5),
        GiaoVien("Le Van Cuong", "10/03/1980", "Đà Nẵng", "Lý", "Tiến sĩ", 15),
        GiaoVien("Pham Thi Dung", "25/12/1988", "HCM", "Hóa", "Thạc sĩ", 8)
    ]
    
    # Sắp xếp theo thâm niên
    ds_giao_vien.sort()
    
    # In bảng
    in_bang_giao_vien(ds_giao_vien, "DANH SÁCH GIÁO VIÊN (Đã sắp xếp tăng dần thâm niên)")
    
    # Ghi file
    ghi_file(ds_giao_vien)

if __name__ == "__main__":
    main()  # Chạy với dữ liệu mẫu
    # main_nhap_tu_ban_phim()  # Bỏ comment để nhập từ bàn phím
