def tao_cong_nhan(ma_cn, ho_ten, luong_ngay, so_ngay_cong, phu_cap):
    luong_co_ban = luong_ngay * so_ngay_cong
    tong_thu_nhap = luong_co_ban + phu_cap
    return [ma_cn, ho_ten, luong_ngay, so_ngay_cong, phu_cap, luong_co_ban, tong_thu_nhap]
def tim_cong_nhan_thu_nhap_cao_nhat(ds_cong_nhan):
    if not ds_cong_nhan:
        return []
    max_thu_nhap = max(cn[6] for cn in ds_cong_nhan)
    return [cn for cn in ds_cong_nhan if cn[6] == max_thu_nhap]
def loc_cong_nhan_thoa_dieu_kien(ds_cong_nhan, muc_thu_nhap_yeu_cau=15200000):
    ket_qua = []
    for cn in ds_cong_nhan:
        dk_khong_bang_0 = (cn[5] > 0) and (cn[4] > 0)
        dk_tong_thu_nhap = (cn[6] > muc_thu_nhap_yeu_cau)
        if dk_khong_bang_0 and dk_tong_thu_nhap:
            ket_qua.append(cn)
    return ket_qua
def in_dong_ke():
    print("+" + "-"*10 + "+" + "-"*20 + "+" + "-"*15 + "+" + "-"*15 + "+" + "-"*15 + "+")
def in_header():
    print(f"|{'Ma CN':^10}|{'Ho Ten':^20}|{'Luong CB':^15}|{'Phu Cap':^15}|{'Tong TN':^15}|")
def in_mot_cong_nhan(cn):
    print(f"|{cn[0]:^10}|{cn[1]:^20}|{cn[5]:>14,} |{cn[4]:>14,} |{cn[6]:>14,} |")
def in_bang_cong_nhan(ds_cong_nhan):
    print("\nDANH SÁCH CÔNG NHÂN:")
    in_dong_ke()
    in_header()
    in_dong_ke()
    for cn in ds_cong_nhan:
        in_mot_cong_nhan(cn)
    in_dong_ke()
def in_cong_nhan_thu_nhap_cao_nhat(ds_cong_nhan, max_thu_nhap):
    print("Các công nhân có thu nhập cao nhất:")
    for cn in ds_cong_nhan:
        if cn[6] == max_thu_nhap:
            print(f"- {cn[1]} (Mã: {cn[0]})")
def nhap_mot_cong_nhan(stt):
    print(f"\nNhập thông tin công nhân thứ {stt}:")
    ma_cn = input("Mã công nhân: ").strip()
    ho_ten = input("Họ tên: ").strip()
    luong_ngay = int(input("Lương ngày: ").strip())
    so_ngay_cong = int(input("Số ngày công: ").strip())
    phu_cap = int(input("Phụ cấp: ").strip())
    return tao_cong_nhan(ma_cn, ho_ten, luong_ngay, so_ngay_cong, phu_cap)
def nhap_danh_sach_cong_nhan():
    n = int(input("Nhập số lượng công nhân: ").strip())
    ds_cong_nhan = []
    for i in range(n):
        cn = nhap_mot_cong_nhan(i + 1)
        ds_cong_nhan.append(cn)
    return ds_cong_nhan
def main():
    print("--- BÀI 1: QUẢN LÝ CÔNG NHÂN ---")
    ds_cong_nhan = nhap_danh_sach_cong_nhan()
    in_bang_cong_nhan(ds_cong_nhan)
    ds_thu_nhap_cao_nhat = tim_cong_nhan_thu_nhap_cao_nhat(ds_cong_nhan)
    print("\nCÔNG NHÂN CÓ THU NHẬP CAO NHẤT:")
    in_bang_cong_nhan(ds_thu_nhap_cao_nhat)
    ds_thoa_dk = loc_cong_nhan_thoa_dieu_kien(ds_cong_nhan)
    print(f"\nCÔNG NHÂN THỎA ĐIỀU KIỆN (Thu nhập > 15.2tr & không khoản nào bằng 0): {len(ds_thoa_dk)} người")
    in_bang_cong_nhan(ds_thoa_dk)
if __name__ == "__main__":
    main()
