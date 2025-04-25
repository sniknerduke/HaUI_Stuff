/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.btjava62;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class TaiLieu {
    String maTL, tenNXB;
    int soBan;

    public TaiLieu(String maTL, String tenNXB, int soBan) {
        this.maTL = maTL;
        this.tenNXB = tenNXB;
        this.soBan = soBan;
    }

    public abstract void hienThi();
}

class Sach extends TaiLieu {
    String tacGia;
    int soTrang;
    public Sach(String ma, String nxb, int soBan, String tg, int soTrang) {
        super(ma, nxb, soBan);
        this.tacGia = tg;
        this.soTrang = soTrang;
    }
    public void hienThi() {
        System.out.println("Sach: " + maTL + ", TG: " + tacGia);
    }
}

class TapChi extends TaiLieu {
    int soPH, thangPH;
    public TapChi(String ma, String nxb, int soBan, int soPH, int thangPH) {
        super(ma, nxb, soBan);
        this.soPH = soPH;
        this.thangPH = thangPH;
    }
    public void hienThi() {
        System.out.println("TapChi: " + maTL + ", So PH: " + soPH);
    }
}

class Bao extends TaiLieu {
    String ngayPH;
    public Bao(String ma, String nxb, int soBan, String ngayPH) {
        super(ma, nxb, soBan);
        this.ngayPH = ngayPH;
    }
    public void hienThi() {
        System.out.println("Bao: " + maTL + ", Ngay PH: " + ngayPH);
    }
}

class QuanLySach {
    List<TaiLieu> ds = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    void nhap() {
        System.out.print("Loai (1:S, 2:TC, 3:B): ");
        int chon = sc.nextInt(); sc.nextLine();
        System.out.print("Ma: "); String ma = sc.nextLine();
        System.out.print("NXB: "); String nxb = sc.nextLine();
        System.out.print("So ban: "); int sb = sc.nextInt(); sc.nextLine();
        if (chon == 1) {
            System.out.print("Tac gia: "); String tg = sc.nextLine();
            System.out.print("So trang: "); int st = sc.nextInt();
            ds.add(new Sach(ma, nxb, sb, tg, st));
        } else if (chon == 2) {
            System.out.print("So PH: "); int sph = sc.nextInt();
            System.out.print("Thang PH: "); int tph = sc.nextInt();
            ds.add(new TapChi(ma, nxb, sb, sph, tph));
        } else {
            System.out.print("Ngay PH: "); String nph = sc.nextLine();
            ds.add(new Bao(ma, nxb, sb, nph));
        }
    }

    void hienThi() {
        for (TaiLieu t : ds) t.hienThi();
    }

    void timKiemTheoLoai(String loai) {
        for (TaiLieu t : ds) if (t.getClass().getSimpleName().equalsIgnoreCase(loai)) t.hienThi();
    }
}
