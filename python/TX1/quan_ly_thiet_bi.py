def khoi_tao_thiet_bi():
    danh_sach = {}
    while True:
        try:
            n = int(input("Nhập số lượng thiết bị (n >= 5): "))
            if n >= 5: break
            print("Cần nhập số lượng >= 5!")
        except ValueError:
            print("Vui lòng nhập số nguyên.")
    print("\n--- NHẬP THÔNG TIN BAN ĐẦU ---")
    for i in range(n):
        print(f"Thiết bị thứ {i+1}:")
        while True:
            ma = input("  Mã thiết bị: ")
            if ma not in danh_sach: break
            print("  Mã này đã tồn tại, vui lòng nhập khác.")
        ten = input("  Tên thiết bị: ")
        loai = input("  Loại thiết bị: ")
        nam = input("  Năm mua: ")
        phong = input("  Phòng sử dụng: ")
        danh_sach[ma] = [ten, loai, nam, phong]
    return danh_sach
def kiem_tra_co_gia(ma_tb, db_thiet_bi):
    """Kiểm tra list thông tin đã có phần tử thứ 5 (Giá trị) chưa"""
    if ma_tb in db_thiet_bi:
        thong_tin = db_thiet_bi[ma_tb]
        return len(thong_tin) >= 5
    return False
def nhap_them_gia_tri(ma_tb, db_thiet_bi):
    if ma_tb in db_thiet_bi:
        ten_tb = db_thiet_bi[ma_tb][0]
        print(f"Nhập giá cho '{ten_tb}' ({ma_tb}):")
        while True:
            try:
                gia = float(input("  > Giá trị: "))
                if gia >= 0:
                    db_thiet_bi[ma_tb].append(gia)
                    break
                print("  Giá trị phải >= 0.")
            except ValueError:
                print("  Lỗi nhập số.")
    else:
        print("Mã thiết bị không tồn tại!")
def main():
    db = khoi_tao_thiet_bi()
    print("\n--- BỔ SUNG GIÁ TRỊ THIẾT BỊ ---")
    for ma in db:
        if not kiem_tra_co_gia(ma, db):
            nhap_them_gia_tri(ma, db)
    tong_gia_tri = 0
    print("\n" + "="*65)
    print(f"{'Mã':<10} {'Tên TB':<20} {'Phòng':<10} {'Giá trị':<15}")
    print("-" * 65)
    for ma, info in db.items():
        if len(info) >= 5:
            gia_tri = info[4] # Phần tử thứ 5 nằm ở index 4
            tong_gia_tri += gia_tri
            print(f"{ma:<10} {info[0]:<20} {info[3]:<10} {gia_tri:,.0f}")
    print("-" * 65)
    print(f"TỔNG GIÁ TRỊ TOÀN BỘ THIẾT BỊ: {tong_gia_tri:,.0f} VNĐ")
    print("="*65)
if __name__ == "__main__":
    main()
