/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.btjava63;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class ThiSinh {
    String soBD, hoTen, diaChi, uuTien;
    public ThiSinh(String soBD, String hoTen, String diaChi, String uuTien) {
        this.soBD = soBD;
        this.hoTen = hoTen;
        this.diaChi = diaChi;
        this.uuTien = uuTien;
    }
    public abstract void hienThi();
}

class ThiSinhKhoiA extends ThiSinh {
    public ThiSinhKhoiA(String sbd, String ht, String dc, String ut) {
        super(sbd, ht, dc, ut);
    }
    public void hienThi() {
        System.out.println("Khoi A: " + hoTen + " - SBD: " + soBD);
    }
}

class ThiSinhKhoiB extends ThiSinh {
    public ThiSinhKhoiB(String sbd, String ht, String dc, String ut) {
        super(sbd, ht, dc, ut);
    }
    public void hienThi() {
        System.out.println("Khoi B: " + hoTen + " - SBD: " + soBD);
    }
}

class ThiSinhKhoiC extends ThiSinh {
    public ThiSinhKhoiC(String sbd, String ht, String dc, String ut) {
        super(sbd, ht, dc, ut);
    }
    public void hienThi() {
        System.out.println("Khoi C: " + hoTen + " - SBD: " + soBD);
    }
}

class TuyenSinh {
    List<ThiSinh> ts = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    void nhap() {
        System.out.print("Khoi (A/B/C): "); String khoi = sc.nextLine();
        System.out.print("SBD: "); String sbd = sc.nextLine();
        System.out.print("Ho ten: "); String ht = sc.nextLine();
        System.out.print("Dia chi: "); String dc = sc.nextLine();
        System.out.print("Uu tien: "); String ut = sc.nextLine();

        switch (khoi.toUpperCase()) {
            case "A": ts.add(new ThiSinhKhoiA(sbd, ht, dc, ut)); break;
            case "B": ts.add(new ThiSinhKhoiB(sbd, ht, dc, ut)); break;
            case "C": ts.add(new ThiSinhKhoiC(sbd, ht, dc, ut)); break;
        }
    }

    void hienThi() {
        for (ThiSinh t : ts) t.hienThi();
    }

    void timKiem(String sbd) {
        for (ThiSinh t : ts) if (t.soBD.equalsIgnoreCase(sbd)) t.hienThi();
    }
}
