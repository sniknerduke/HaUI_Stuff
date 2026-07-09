package cms.user;

import java.sql.*;
import java.util.ArrayList;
import util.*;
import objects.UserObject;

public class UserFunctionImpl implements UserFunction {
    private Connection con; 

    public UserFunctionImpl(ConnectionPool cp) {
        if (cp == null) cp = new ConnectionPoolImpl(); 
        try {
            this.con = cp.getConnection("User"); 
            if (this.con.getAutoCommit()) this.con.setAutoCommit(false); 
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

    @Override
    public boolean addUser(UserObject item) {
        StringBuilder sql = new StringBuilder(); 
        sql.append("INSERT INTO tbluser(user_name, user_pass, user_fullname, user_email, user_created_date, user_parent_id, user_deleted) "); 
        sql.append("VALUES(?, md5(?), ?, ?, ?, ?, ?)"); 
        
        try {
            PreparedStatement pre = this.con.prepareStatement(sql.toString()); 
            pre.setString(1, item.getUser_name()); 
            pre.setString(2, item.getUser_pass()); 
            pre.setString(3, item.getUser_fullname()); 
            pre.setString(4, item.getUser_email()); 
            pre.setString(5, item.getUser_created_date()); 
            pre.setInt(6, item.getUser_parent_id());
            pre.setBoolean(7, item.isUser_deleted()); 
            
            int result = pre.executeUpdate(); 
            if (result > 0) {
                this.con.commit();
                return true;
            }
        } catch (SQLException e) {
            try { this.con.rollback(); } catch (SQLException e1) { e1.printStackTrace(); } 
        }
        return false;
    }

    @Override
    public ArrayList<UserObject> getUsers(UserObject similar, int at, byte total) {
        ArrayList<UserObject> list = new ArrayList<>(); 
        String sql = "SELECT * FROM tbluser ORDER BY user_id DESC LIMIT " + at + "," + total; 
        try {
            PreparedStatement pre = this.con.prepareStatement(sql); 
            ResultSet rs = pre.executeQuery(); 
            while (rs != null && rs.next()) {
                UserObject item = new UserObject(); 
                item.setUser_id(rs.getInt("user_id")); 
                item.setUser_name(rs.getString("user_name")); 
                item.setUser_email(rs.getString("user_email"));
                item.setUser_fullname(rs.getString("user_fullname")); 
                item.setUser_created_date(rs.getString("user_created_date"));
                item.setUser_parent_id(rs.getInt("user_parent_id"));
                list.add(item); 
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return list; 
    }
}
