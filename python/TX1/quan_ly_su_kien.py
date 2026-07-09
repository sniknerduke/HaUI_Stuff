def khoi_tao_danh_sach():
    dssk = []
    while True:
        try:
            n = int(input("Nhập số lượng sự kiện (n >= 5): "))
            if n >= 5:
                break
            print("Yêu cầu nhập n >= 5!")
        except ValueError:
            print("Vui lòng nhập số nguyên.")
    print("\n--- NHẬP THÔNG TIN SỰ KIỆN ---")
    for i in range(n):
        print(f"Sự kiện thứ {i+1}:")
        sk = {
            "ma": input("  Mã sự kiện: "),
            "ten": input("  Tên sự kiện: "),
            "dia_diem": input("  Địa điểm: "),
            "ngay": input("  Ngày tổ chức: "),
            "so_nguoi": int(input("  Số người tham gia: "))
        }
        dssk.append(sk)
    return dssk
def bo_sung_chi_phi(dssk):
    print("\n--- BỔ SUNG CHI PHÍ TỔ CHỨC ---")
    for sk in dssk:
        if "chi_phi" not in sk:
            print(f"Nhập chi phí cho sự kiện '{sk['ten']}': ")
            while True:
                try:
                    cp = float(input("  > Chi phí: "))
                    if cp >= 0:
                        sk["chi_phi"] = cp
                        break
                    print("  Chi phí phải >= 0.")
                except ValueError:
                    print("  Lỗi nhập số.")
def tinh_chi_phi_tb(dssk):
    if not dssk: return 0
    tong_chi_phi = sum([sk.get("chi_phi", 0) for sk in dssk])
    return tong_chi_phi / len(dssk)
def sap_xep_tang_dan(dssk):
    dssk.sort(key=lambda x: x["so_nguoi"])
def tim_su_kien_dong_nhat(dssk):
    if not dssk: return None
    return max(dssk, key=lambda x: x["so_nguoi"])
def hien_thi(dssk):
    print(f"{'Mã':<10} {'Tên sự kiện':<20} {'Số người':<10} {'Chi phí':<15}")
    print("-" * 60)
    if isinstance(dssk, dict):
        dssk = [dssk]
    for sk in dssk:
        cp = sk.get("chi_phi", 0)
        print(f"{sk['ma']:<10} {sk['ten']:<20} {sk['so_nguoi']:<10} {cp:<15,.0f}")
def main():
    ds_su_kien = khoi_tao_danh_sach()
    bo_sung_chi_phi(ds_su_kien)
    tb = tinh_chi_phi_tb(ds_su_kien)
    print(f"\n=> CHI PHÍ TRUNG BÌNH CÁC SỰ KIỆN: {tb:,.0f} VNĐ")
    print("\n--- DANH SÁCH SAU KHI SẮP XẾP (Người tham gia tăng dần) ---")
    sap_xep_tang_dan(ds_su_kien)
    hien_thi(ds_su_kien)
    print("\n--- SỰ KIỆN CÓ SỐ NGƯỜI ĐÔNG NHẤT ---")
    sk_max = tim_su_kien_dong_nhat(ds_su_kien)
    hien_thi(sk_max)
if __name__ == "__main__":
    main()
