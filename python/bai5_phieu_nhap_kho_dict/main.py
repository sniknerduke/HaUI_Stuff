class SanPham:
    def __init__(self, ma_sp, ten_sp, don_gia, so_luong):
        self.ma_sp = ma_sp
        self.ten_sp = ten_sp
        self.don_gia = don_gia
        self.so_luong = so_luong

    def thanh_tien(self):
        return self.don_gia * self.so_luong

    def __str__(self):
        return f"{self.ma_sp:<10} {self.ten_sp:<20} {self.don_gia:<15,.0f} {self.so_luong:<5} {self.thanh_tien():<15,.0f}"

class PhieuNhap:
    def __init__(self, ma_phieu, ngay_lap):
        self.ma_phieu = ma_phieu
        self.ngay_lap = ngay_lap
        # DICT: key = mã SP, value = đối tượng SanPham
        self.danh_sach_sp = {}

    def them_san_pham(self):
        n = int(input(f"Nhập số lượng mặt hàng cho phiếu {self.ma_phieu}: "))
        for i in range(n):
            print(f"  > Nhập hàng thứ {i + 1}:")
            ma = input("    Mã SP: ")
            ten = input("    Tên SP: ")
            gia = float(input("    Đơn giá: "))
            sl = int(input("    Số lượng: "))
            
            sp = SanPham(ma, ten, gia, sl)
            # Thêm vào dict với key = mã SP
            self.danh_sach_sp[ma] = sp

    def sap_xep_giam_dan(self):
        # Sắp xếp dict theo đơn giá giảm dần (dict comprehension)
        sorted_items = sorted(self.danh_sach_sp.items(), key=lambda x: x[1].don_gia, reverse=True)
        self.danh_sach_sp = {k: v for k, v in sorted_items}

    def tinh_tong_tien(self):
        # Dùng comprehension thay vì vòng for
        return sum(sp.thanh_tien() for sp in self.danh_sach_sp.values())

    def in_phieu(self):
        print("\n" + "="*70)
        print(f"{'PHIẾU NHẬP KHO':^70}")
        print(f"Mã phiếu: {self.ma_phieu:<30} Ngày lập: {self.ngay_lap}")
        print("-" * 70)
        print(f"{'Mã SP':<10} {'Tên Hàng':<20} {'Đơn giá':<15} {'SL':<5} {'Thành tiền':<15}")
        print("-" * 70)
        
        # In bằng comprehension
        [print(sp) for sp in self.danh_sach_sp.values()]
            
        print("-" * 70)
        print(f"{'TỔNG CỘNG:':<51} {self.tinh_tong_tien():,.0f} VNĐ")
        print("="*70)

    # Các thao tác với DICT
    def tim_san_pham(self, ma_sp):
        """Tìm sản phẩm theo mã (key lookup - O(1))"""
        return self.danh_sach_sp.get(ma_sp)

    def xoa_san_pham(self, ma_sp):
        """Xóa sản phẩm theo mã"""
        if ma_sp in self.danh_sach_sp:
            del self.danh_sach_sp[ma_sp]
            print(f"Đã xóa sản phẩm: {ma_sp}")
        else:
            print("Không tìm thấy!")

    def loc_gia_cao(self, muc_gia):
        """Lọc sản phẩm có đơn giá >= mức giá (dict comprehension)"""
        return {k: v for k, v in self.danh_sach_sp.items() if v.don_gia >= muc_gia}

    def loc_ton_kho_thap(self, muc_sl):
        """Lọc sản phẩm có số lượng <= mức (dict comprehension)"""
        return {k: v for k, v in self.danh_sach_sp.items() if v.so_luong <= muc_sl}

    def thong_ke(self):
        """Thống kê nhanh bằng comprehension"""
        print("\n--- THỐNG KÊ ---")
        # Tổng số mặt hàng
        print(f"Tổng số mặt hàng: {len(self.danh_sach_sp)}")
        # Tổng số lượng
        tong_sl = sum(sp.so_luong for sp in self.danh_sach_sp.values())
        print(f"Tổng số lượng: {tong_sl}")
        # Giá cao nhất
        gia_max = max(sp.don_gia for sp in self.danh_sach_sp.values()) if self.danh_sach_sp else 0
        print(f"Đơn giá cao nhất: {gia_max:,.0f} VNĐ")
        # Giá thấp nhất
        gia_min = min(sp.don_gia for sp in self.danh_sach_sp.values()) if self.danh_sach_sp else 0
        print(f"Đơn giá thấp nhất: {gia_min:,.0f} VNĐ")

# --- CHƯƠNG TRÌNH CHÍNH ---
def main():
    # 1. Tạo phiếu
    print("--- KHỞI TẠO PHIẾU ---")
    ma = input("Nhập mã phiếu: ")
    ngay = input("Nhập ngày lập: ")
    phieu = PhieuNhap(ma, ngay)
    
    # 2. Nhập hàng vào phiếu
    phieu.them_san_pham()
    
    # 3. Sắp xếp
    phieu.sap_xep_giam_dan()
    
    # 4. In kết quả
    print("\n--- KẾT QUẢ SAU KHI SẮP XẾP ---")
    phieu.in_phieu()
    
    # 5. Thống kê
    phieu.thong_ke()
    
    # 6. Demo tìm kiếm
    print("\n--- TÌM KIẾM SẢN PHẨM ---")
    ma_tim = input("Nhập mã SP cần tìm: ")
    sp = phieu.tim_san_pham(ma_tim)
    if sp:
        print(f"Tìm thấy: {sp.ten_sp} - Giá: {sp.don_gia:,.0f} - SL: {sp.so_luong}")
    else:
        print("Không tìm thấy!")

if __name__ == "__main__":
    main()
