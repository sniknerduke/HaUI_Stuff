class SanPham:
    def __init__(self, ma_sp, ten_sp, don_gia, so_luong):
        self.ma_sp = ma_sp
        self.ten_sp = ten_sp
        self.don_gia = don_gia
        self.so_luong = so_luong

    def thanh_tien(self):
        return self.don_gia * self.so_luong

    def __str__(self):
        # In định dạng bảng: Mã (10 ký tự), Tên (20), Giá (15), SL (5), Thành tiền (15)
        return f"{self.ma_sp:<10} {self.ten_sp:<20} {self.don_gia:<15,.0f} {self.so_luong:<5} {self.thanh_tien():<15,.0f}"

class PhieuNhap:
    def __init__(self, ma_phieu, ngay_lap):
        self.ma_phieu = ma_phieu
        self.ngay_lap = ngay_lap
        self.danh_sach_sp = []  # <--- List chứa các đối tượng SanPham

    def them_san_pham(self):
        n = int(input(f"Nhập số lượng mặt hàng cho phiếu {self.ma_phieu}: "))
        for i in range(n):
            print(f"  > Nhập hàng thứ {i + 1}:")
            ma = input("    Mã SP: ")
            ten = input("    Tên SP: ")
            gia = float(input("    Đơn giá: "))
            sl = int(input("    Số lượng: "))
            
            # Tạo đối tượng SanPham và thêm vào list của Phiếu
            sp = SanPham(ma, ten, gia, sl)
            self.danh_sach_sp.append(sp)

    def sap_xep_giam_dan(self):
        # Sắp xếp list danh_sach_sp dựa trên don_gia
        # reverse=True để giảm dần (cao xuống thấp)
        self.danh_sach_sp.sort(key=lambda x: x.don_gia, reverse=True)

    def tinh_tong_tien(self):
        # Dùng comprehension thay vì vòng for
        return sum(sp.thanh_tien() for sp in self.danh_sach_sp)

    def in_phieu(self):
        dong_ke = "+" + "-"*12 + "+" + "-"*22 + "+" + "-"*15 + "+" + "-"*8 + "+" + "-"*17 + "+"
        
        print("\n" + "="*78)
        print(f"{'PHIẾU NHẬP KHO':^78}")
        print("="*78)
        print(f"| Mã phiếu: {self.ma_phieu:<30} | Ngày lập: {self.ngay_lap:<26} |")
        print(dong_ke)
        print(f"|{'Mã SP':^12}|{'Tên Hàng':^22}|{'Đơn giá':^15}|{'SL':^8}|{'Thành tiền':^17}|")
        print(dong_ke)
        
        # In danh sách sản phẩm
        for sp in self.danh_sach_sp:
            print(f"|{sp.ma_sp:^12}|{sp.ten_sp:^22}|{sp.don_gia:>14,} |{sp.so_luong:>7,} |{sp.thanh_tien():>16,} |")
            
        dong_ke_tong = "+" + "-"*60 + "+" + "-"*17 + "+"
        print(dong_ke_tong)
        print(f"|{'TỔNG CỘNG:':^60}|{self.tinh_tong_tien():>14,} VNĐ|")
        print(dong_ke_tong)

# --- CHƯƠNG TRÌNH CHÍNH ---
def main_nhap_tu_ban_phim():
    """Hàm nhập từ bàn phím"""
    print("--- KHỞI TẠO PHIẾU ---")
    ma = input("Nhập mã phiếu: ")
    ngay = input("Nhập ngày lập: ")
    phieu = PhieuNhap(ma, ngay)
    
    phieu.them_san_pham()
    phieu.sap_xep_giam_dan()
    
    print("\n--- KẾT QUẢ SAU KHI SẮP XẾP ---")
    phieu.in_phieu()

def main():
    """Hàm chạy với dữ liệu mẫu"""
    # Tạo phiếu nhập kho
    phieu = PhieuNhap("PNK001", "01/02/2026")
    
    # Thêm sản phẩm mẫu (không qua input)
    phieu.danh_sach_sp = [
        SanPham("SP01", "Laptop Dell", 15000000, 5),
        SanPham("SP02", "Chuột Logitech", 500000, 20),
        SanPham("SP03", "Bàn phím cơ", 2000000, 10),
        SanPham("SP04", "Màn hình LG", 8000000, 3)
    ]
    
    # Sắp xếp giảm dần theo đơn giá
    phieu.sap_xep_giam_dan()
    
    # In kết quả
    print("\n--- KẾT QUẢ SAU KHI SẮP XẾP (Đơn giá giảm dần) ---")
    phieu.in_phieu()

if __name__ == "__main__":
    main()  # Chạy với dữ liệu mẫu
    # main_nhap_tu_ban_phim()  # Bỏ comment để nhập từ bàn phím
