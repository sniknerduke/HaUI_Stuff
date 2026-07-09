package util;

import java.sql.*;
import java.util.*;

public class ConnectionPoolImpl implements ConnectionPool {
    private String driver = "com.mysql.cj.jdbc.Driver"; 
    private String username = "it6020_huyhq"; 
    private String userpass = "(@135780*)"; 
    private String url = "jdbc:mysql://localhost:3306/it6020_data"; 
    private Stack<Connection> pool; 

    public ConnectionPoolImpl() {
        this.pool = new Stack<>(); 
        try {
            Class.forName(this.driver); 
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); 
        }
    }

    @Override
    public Connection getConnection(String objectName) throws SQLException {
        if (this.pool.isEmpty()) {
            System.out.println(objectName + " đã khởi tạo một kết nối mới."); 
            return DriverManager.getConnection(this.url, this.username, this.userpass); 
        } else {
            System.out.println(objectName + " đã lấy ra một kết nối."); 
            return this.pool.pop(); 
        }
    }

    @Override
    public void releaseConnection(Connection con, String objectName) throws SQLException {
        System.out.println(objectName + " đã trả về một kết nối."); 
        this.pool.push(con); 
    }
}
