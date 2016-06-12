package com.autosite.tool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class AccessData {

    public static void main(String[] args) {

        {

            try {
                Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                // set this to a MS Access DB you have on your machine
                String filename = "c:/juyang/juyang-2010.mdb";
                String database = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
                database += filename.trim() + ";DriverID=22;READONLY=true}"; 
                Connection con = DriverManager.getConnection(database, "", "");
                
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select * from income_people");

                if (rs != null) {
                    while (rs.next()) {
                        String name = rs.getString(2);
                        double sip = rs.getDouble(3);
                        double juil = rs.getDouble(4);
                        double gam = rs.getDouble(5);
                        double sun = rs.getDouble(6);
                        double gun = rs.getDouble(7);
                        double cell = rs.getDouble(8);
                        double gi = rs.getDouble(9);

                        System.out.println(name + " " + sip);
                        
                    }
                }
                
            }

            catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
