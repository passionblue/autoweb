package com.autosite.lab;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class DataObjectRetrieve {

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://192.168.0.3/autosite", "root", "");
            DatabaseMetaData metaData = con.getMetaData();

            ResultSet columns = metaData.getColumns(null, "%", "site", "%");
            
            ResultSetMetaData rsMeta = columns.getMetaData();
            
            int columnCount = rsMeta.getColumnCount();
            
            for (int i = 1; i <= columnCount; i++) {
                System.out.println("-------------------");
                System.out.println(rsMeta.getColumnLabel(i));
                System.out.println(rsMeta.getColumnClassName(i));
                System.out.println(rsMeta.getColumnName(i));
                System.out.println(rsMeta.getColumnType(i));
                System.out.println(rsMeta.getColumnTypeName(i));
            }
            
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
            while (columns.next()) {

                System.out.println(columns.getString("TABLE_CAT"));
                System.out.println(columns.getString("SQL_DATA_TYPE"));
                System.out.println(columns.getString("COLUMN_DEF"));
                System.out.println(columns.getString("TABLE_NAME"));
                String columnName = columns.getString("COLUMN_NAME");
                String datatype = columns.getString("TYPE_NAME");
                int datasize = columns.getInt("COLUMN_SIZE");
                int digits = columns.getInt("DECIMAL_DIGITS");
                int nullable = columns.getInt("NULLABLE");
                boolean isNull = (nullable == 1);
                
                System.out.println(columnName + ",type=" + datatype);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void a(String input) {
        
    }
    
    
    
    
}

class DbColumn{
    
    String name;
    String type;
    int size;
    boolean nullable;
    
}