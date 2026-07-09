def kiem_tra_va_cap_nhat_sp(san_pham, ma_sp, so_luong_moi=100):
    if ma_sp in san_pham:
        print(f"\nSan pham {ma_sp} da co. Cap nhat so luong thanh {so_luong_moi}.")
        san_pham[ma_sp] = so_luong_moi
    else:
        print(f"\nSan pham {ma_sp} chua co. Nhap moi.")
        sl_moi = int(input(f"Nhap so luong cho {ma_sp}: "))
        san_pham[ma_sp] = sl_moi
    return san_pham

def xoa_sp_so_luong_0(san_pham):
    keys_to_remove = [ma_sp for ma_sp, sl in san_pham.items() if sl == 0]
    for k in keys_to_remove:
        del san_pham[k]
    return san_pham

def chuyen_dict_sang_list(san_pham):
    list_ma_sp = list(san_pham.keys())
    list_so_luong = list(san_pham.values())
    return list_ma_sp, list_so_luong

def lay_n_phan_tu_dau(lst, n=3):
    return lst[:n]

def lay_n_phan_tu_cuoi(lst, n=3):
    return lst[-n:]

def in_dong_ke_loai():
    print("+" + "-"*12 + "+" + "-"*25 + "+")

def in_dong_ke_sp():
    print("+" + "-"*12 + "+" + "-"*15 + "+")

def in_bang_loai_san_pham(loai_san_pham):
    print("\nDANH MUC LOAI SAN PHAM:")
    in_dong_ke_loai()
    print(f"|{'Ma Loai':^12}|{'Ten Loai':^25}|")
    in_dong_ke_loai()
    for ma, ten in loai_san_pham.items():
        print(f"|{ma:^12}|{ten:^25}|")
    in_dong_ke_loai()

def in_bang_san_pham(san_pham, tieu_de="DANH SACH SAN PHAM"):
    print(f"\n{tieu_de}:")
    in_dong_ke_sp()
    print(f"|{'Ma SP':^12}|{'So Luong':^15}|")
    in_dong_ke_sp()
    for ma, sl in san_pham.items():
        print(f"|{ma:^12}|{sl:>14,} |")
    in_dong_ke_sp()

def nhap_san_pham():
    n = int(input("Nhập số lượng sản phẩm (n): ").strip())
    
    san_pham = {}
    for i in range(n):
        ma_sp = input(f"Nhập mã SP thứ {i+1}: ").strip()
        so_luong = int(input(f"Nhập số lượng tồn kho cho {ma_sp}: ").strip())
        san_pham[ma_sp] = so_luong
    return san_pham

def nhap_loai_san_pham():
    m = int(input("\nNhập số lượng loại sản phẩm (m): ").strip())
    
    loai_san_pham = {}
    for i in range(m):
        ma_loai = input(f"Nhập mã loại SP thứ {i+1}: ").strip()
        ten_loai = input(f"Nhập tên loại SP cho {ma_loai}: ").strip()
        loai_san_pham[ma_loai] = ten_loai
    return loai_san_pham

def main():
    print("\n" + "="*30 + "\n--- BÀI 2: QUẢN LÝ TỪ ĐIỂN SẢN PHẨM ---")
    
    san_pham = nhap_san_pham()
    loai_san_pham = nhap_loai_san_pham()
    
    in_bang_loai_san_pham(loai_san_pham)
    
    san_pham = kiem_tra_va_cap_nhat_sp(san_pham, "SP01", 100)
    in_bang_san_pham(san_pham, "DANH SACH SP SAU KHI CAP NHAT/THEM SP01")
    
    san_pham = xoa_sp_so_luong_0(san_pham)
    in_bang_san_pham(san_pham, "DANH SACH SP SAU KHI XOA SP CO SO LUONG 0")
    
    list_ma_sp, list_so_luong = chuyen_dict_sang_list(san_pham)
    
    print(f"\n3 phan tu DAU TIEN cua danh sach ma SP: {lay_n_phan_tu_dau(list_ma_sp)}")
    print(f"3 phan tu CUOI CUNG cua danh sach so luong: {lay_n_phan_tu_cuoi(list_so_luong)}")

if __name__ == "__main__":
    main()
