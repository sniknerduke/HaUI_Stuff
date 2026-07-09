package main;

import util.*;
import cms.user.*;
import objects.UserObject;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ConnectionPool cp = new ConnectionPoolImpl(); 
        UserFunction uf = new UserFunctionImpl(cp); 

        // Tao user moi
        UserObject nItem = new UserObject();
        nItem.setUser_name("JavaNangcao");
        nItem.setUser_email("huyhq@haui.edu.vn");
        nItem.setUser_created_date("10/05/24");
        nItem.setUser_fullname("Hoang Quang Huy");
        nItem.setUser_parent_id(20);
        nItem.setUser_pass("123456");

        boolean results = uf.addUser(nItem);
        if (!results) {
            System.out.print("------KHONG THANH CONG------\n");
        }

        // Danh sach doi tuong
        ArrayList<UserObject> list = uf.getUsers(null, 0, (byte) 10);
        list.forEach(u -> System.out.println(u));
    }
}
