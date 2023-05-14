package com.nhom7.foodg;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {

        System.out.println("Aaaaaaaaaaaaaaa");
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser("sa");
        ds.setPassword("12");
        ds.setServerName("THINKPAD-T480S\\SQLEXPRESS01");
        ds.setPortNumber(1433);
        ds.setDatabaseName("foodg");
        ds.setEncrypt(false);

        try(Connection conn = ds.getConnection()){
            System.out.println("Connection success");
            System.out.println(conn.getMetaData());
        }
        catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
