package util;

import java.sql.*;

public interface ConnectionPool {
    public Connection getConnection(String objectName) throws SQLException;
    public void releaseConnection(Connection con, String objectName) throws SQLException;
}
