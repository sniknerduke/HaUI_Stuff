def khoi_tao_thu_vien():
    thu_vien = {}
    while True:
        try:
            n = int(input("Nhập số lượng đầu sách (n >= 5): "))
            if n >= 5: break
            print("Cần nhập n >= 5!")
        except ValueError:
            print("Vui lòng nhập số nguyên.")
    for i in range(n):
        print(f"\n--- Nhập sách thứ {i+1} ---")
        while True:
            ma = input("Mã sách: ")
            if ma not in thu_vien: break
            print("Mã đã tồn tại, vui lòng nhập mã khác.")
        ten = input("Tên sách: ")
        the_loai = input("Thể loại: ")
        while True:
            try:
                nam = int(input("Năm xuất bản: "))
                gia = float(input("Giá sách: "))
                sl = int(input("Số lượng còn lại: "))
                muon = int(input("Số lượt mượn: "))
                if nam >= 0 and gia >= 0 and sl >= 0 and muon >= 0:
                    break
                print("Các chỉ số phải >= 0.")
            except ValueError:
                print("Lỗi nhập số.")
        while True:
            try:
                str_dg = input("Nhập các điểm đánh giá (cách nhau bởi dấu cách): ")
                ds_danh_gia = [float(x) for x in str_dg.split()]
                if all(1.0 <= x <= 5.0 for x in ds_danh_gia):
                    break
                print("Điểm đánh giá phải từ 1.0 đến 5.0")
            except ValueError:
                print("Lỗi định dạng số.")
        thu_vien[ma] = {
            "ten": ten,
            "the_loai": the_loai,
            "nam_xuat_ban": nam,
            "gia": gia,
            "so_luong": sl,
            "so_luot_muon": muon,
            "danh_gia": ds_danh_gia
        }
    return thu_vien
def tim_sach_ban_chay(thu_vien):
    if not thu_vien:
        return []
    tong_muon = sum(s["so_luot_muon"] for s in thu_vien.values())
    trung_binh = tong_muon / len(thu_vien)
    print(f"\n(Trung bình lượt mượn toàn thư viện: {trung_binh:.2f})")
    ket_qua = []
    for ma, info in thu_vien.items():
        if info["so_luot_muon"] > trung_binh:
            item = info.copy()
            item["ma_sach"] = ma
            ket_qua.append(item)
    ket_qua.sort(key=lambda x: x["so_luot_muon"], reverse=True)
    return ket_qua
def xoa_sach(thu_vien, nam_x):
    cac_ma_xoa = []
    for ma, info in thu_vien.items():
        if info["nam_xuat_ban"] < nam_x and info["so_luong"] == 0:
            cac_ma_xoa.append(ma)
    for ma in cac_ma_xoa:
        del thu_vien[ma]
    return len(cac_ma_xoa)
def hien_thi(data):
    print(f"{'Mã':<10} {'Tên sách':<20} {'Năm':<6} {'SL':<5} {'Mượn':<5}")
    print("-" * 60)
    if isinstance(data, dict):
        for ma, info in data.items():
            print(f"{ma:<10} {info['ten']:<20} {info['nam_xuat_ban']:<6} {info['so_luong']:<5} {info['so_luot_muon']:<5}")
    elif isinstance(data, list):
        for info in data:
            print(f"{info['ma_sach']:<10} {info['ten']:<20} {info['nam_xuat_ban']:<6} {info['so_luong']:<5} {info['so_luot_muon']:<5}")
def main():
    db_sach = khoi_tao_thu_vien()
    print("\n--- DANH SÁCH BAN ĐẦU ---")
    hien_thi(db_sach)
    print("\n--- DANH SÁCH SÁCH BÁN CHẠY (Mượn > TB) ---")
    ds_hot = tim_sach_ban_chay(db_sach)
    hien_thi(ds_hot)
    try:
        nam_x = int(input("\nNhập năm mốc (X) để xóa sách cũ & hết hàng: "))
        sl_xoa = xoa_sach(db_sach, nam_x)
        print(f"-> Đã xóa thành công {sl_xoa} cuốn sách.")
    except ValueError:
        print("Lỗi nhập năm.")
    print("\n--- THƯ VIỆN SAU KHI XÓA ---")
    hien_thi(db_sach)
if __name__ == "__main__":
    main()
