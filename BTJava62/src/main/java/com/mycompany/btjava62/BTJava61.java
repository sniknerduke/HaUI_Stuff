package com.mycompany.btjava61;

import java.util.*;

abstract class CanBo {
    String hoTen, gioiTinh, diaChi;
    int namSinh;

    public CanBo(String hoTen, int namSinh, String gioiTinh, String diaChi) {
        this.hoTen = hoTen;
        this.namSinh = namSinh;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
    }

    public abstract void hienThi();
}

class CongNhan extends CanBo {
    int bac;
    public CongNhan(String hoTen, int namSinh, String gioiTinh, String diaChi, int bac) {
        super(hoTen, namSinh, gioiTinh, diaChi);
        this.bac = bac;
    }
    public void hienThi() {
        System.out.println("CongNhan: " + hoTen + ", Bac: " + bac);
    }
}

class KySu extends CanBo {
    String nganhDaoTao;
    public KySu(String hoTen, int namSinh, String gioiTinh, String diaChi, String nganh) {
        super(hoTen, namSinh, gioiTinh, diaChi);
        this.nganhDaoTao = nganh;
    }
    public void hienThi() {
        System.out.println("KySu: " + hoTen + ", Nganh: " + nganhDaoTao);
    }
}

class NhanVien extends CanBo {
    String congViec;
    public NhanVien(String hoTen, int namSinh, String gioiTinh, String diaChi, String congViec) {
        super(hoTen, namSinh, gioiTinh, diaChi);
        this.congViec = congViec;
    }
    public void hienThi() {
        System.out.println("NhanVien: " + hoTen + ", CV: " + congViec);
    }
}

class QLCB {
    List<CanBo> danhSach = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    void nhapCanBo() {
        System.out.print("Nhap loai (1: CN, 2: KS, 3: NV): ");
        int chon = sc.nextInt(); sc.nextLine();
        System.out.print("Ho ten: "); String ht = sc.nextLine();
        System.out.print("Nam sinh: "); int ns = sc.nextInt(); sc.nextLine();
        System.out.print("Gioi tinh: "); String gt = sc.nextLine();
        System.out.print("Dia chi: "); String dc = sc.nextLine();
        if (chon == 1) {
            System.out.print("Bac (1-7): "); int bac = sc.nextInt();
            danhSach.add(new CongNhan(ht, ns, gt, dc, bac));
        } else if (chon == 2) {
            System.out.print("Nganh dao tao: "); String nganh = sc.nextLine();
            danhSach.add(new KySu(ht, ns, gt, dc, nganh));
        } else {
            System.out.print("Cong viec: "); String cv = sc.nextLine();
            danhSach.add(new NhanVien(ht, ns, gt, dc, cv));
        }
    }

    void timKiemTheoTen(String ten) {
        for (CanBo cb : danhSach) if (cb.hoTen.equalsIgnoreCase(ten)) cb.hienThi();
    }

    void hienThiDanhSach() {
        for (CanBo cb : danhSach) cb.hienThi();
    }
}
