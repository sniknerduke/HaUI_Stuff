def khoi_tao_mon_hoc():
    """Hàm khởi tạo và trả về từ điển lưu trữ n môn học (n >= 5)"""
    ds_mon = {}
    while True:
        try:
            n = int(input("Nhập số lượng môn học (n >= 5): "))
            if n >= 5:
                break
            print("Vui lòng nhập n >= 5.")
        except ValueError:
            print("Lỗi: Vui lòng nhập số nguyên.")
    print("\n--- NHẬP THÔNG TIN MÔN HỌC ---")
    for i in range(n):
        print(f"Môn thứ {i+1}:")
        while True:
            ma_mon = input("  Mã môn: ")
            if ma_mon not in ds_mon:
                break
            print("  Mã môn này đã tồn tại, vui lòng nhập mã khác!")
        ten_mon = input("  Tên môn: ")
        tin_chi = int(input("  Số tín chỉ: "))
        hoc_ky = input("  Học kỳ: ")
        giang_vien = input("  Giảng viên: ")
        ds_mon[ma_mon] = [ten_mon, tin_chi, hoc_ky, giang_vien]
    return ds_mon
def kiem_tra_dang_ky(ma_mon, ds_mon):
    """Kiểm tra môn học đã có số lượng đăng ký chưa (phần tử thứ 5)"""
    if ma_mon in ds_mon:
        thong_tin = ds_mon[ma_mon]
        if len(thong_tin) >= 5:
            return True
    return False
def nhap_so_dang_ky(ma_mon, ds_mon):
    """Nhập số lượng SV đăng ký vào phần tử thứ 5 của danh sách"""
    if ma_mon in ds_mon:
        if not kiem_tra_dang_ky(ma_mon, ds_mon):
            while True:
                try:
                    sl = int(input(f"Nhập số lượng ĐK cho môn '{ds_mon[ma_mon][0]}' ({ma_mon}): "))
                    if sl >= 0:
                        ds_mon[ma_mon].append(sl)
                        break
                    print("Số lượng phải >= 0.")
                except ValueError:
                    print("Vui lòng nhập số nguyên.")
        else:
            print(f"Môn {ma_mon} đã có thông tin đăng ký rồi.")
    else:
        print("Mã môn không tồn tại.")
def main():
    print("=== QUẢN LÝ ĐĂNG KÝ MÔN HỌC ===")
    db_mon_hoc = khoi_tao_mon_hoc()
    print("\n--- BỔ SUNG SỐ LƯỢNG ĐĂNG KÝ ---")
    for ma in db_mon_hoc:
        if not kiem_tra_dang_ky(ma, db_mon_hoc):
            nhap_so_dang_ky(ma, db_mon_hoc)
    tong_luot_dk = 0
    print("\n--- KẾT QUẢ ---")
    print(f"{'Mã':<10} {'Tên môn':<20} {'Số ĐK':<10}")
    print("-" * 40)
    for ma, info in db_mon_hoc.items():
        if len(info) >= 5:
            so_luong = info[4] # Phần tử thứ 5 nằm ở index 4
            tong_luot_dk += so_luong
            print(f"{ma:<10} {info[0]:<20} {so_luong:<10}")
    print("-" * 40)
    print(f"TỔNG SỐ LƯỢT ĐĂNG KÝ CỦA TẤT CẢ CÁC MÔN: {tong_luot_dk}")
if __name__ == "__main__":
    main()
