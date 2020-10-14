package org.leplus.apps;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

class DBGrep {
    
    private final static String LOGIN_FLAG = "-l";
    private final static String PASSWORD_FLAG = "-p";
    private final static String URL_FLAG = "-u";
    private final static String DRIVER_FLAG = "-d";
    private final static String SCHEMA_FLAG = "-s";
    private final static String TABLE_FLAG = "-t";
    private final static String COLUMN_FLAG = "-c";
    private final static String VALUE_FLAG = "-v";
    private final static String HELP_FLAG = "-h";

    public static void main(String[] args) {
        String login = null;
        String password = null;
        String url = null;
        String driver = null;
        String schema = null;
        String table = null;
        String column = null;
        String value = null;
        int i = 0;
        while (i < args.length) {
            String flag = args[i++];
            if (flag.equals(LOGIN_FLAG)) {
                login = args[i++];  
            } else if (flag.equals(PASSWORD_FLAG)) {
                password = args[i++];
            } else if (flag.equals(URL_FLAG)) {
                url = args[i++];
            } else if (flag.equals(DRIVER_FLAG)) {
                driver = args[i++];
            } else if (flag.equals(SCHEMA_FLAG)) {
                schema = args[i++];
            } else if (flag.equals(TABLE_FLAG)) {
                table = args[i++];
            } else if (flag.equals(COLUMN_FLAG)) {
                column = args[i++];
            } else if (flag.equals(VALUE_FLAG)) {
                value = args[i++];
            } else if (flag.equals(HELP_FLAG)) {
                System.out.println("options:");
                System.out.println("\t" + LOGIN_FLAG + "\tlogin");
                System.out.println("\t" + PASSWORD_FLAG + "\tpassword");
                System.out.println("\t" + URL_FLAG + "\tURL");
                System.out.println("\t" + DRIVER_FLAG + "\tdriver");
                System.out.println("\t" + SCHEMA_FLAG + "\tschema pattern");
                System.out.println("\t" + TABLE_FLAG + "\ttable pattern");
                System.out.println("\t" + COLUMN_FLAG + "\tcolumn pattern");
                System.out.println("\t" + VALUE_FLAG + "\tvalue");
                return;
            }
        }
        boolean error = false;
        if (login == null) {
            System.err.println("error: login is missing");
            error = true;
        }
        if (password == null) {
            System.err.println("error: password is missing");
            error = true;
        }
        if (url == null) {
            System.err.println("error: URL is missing");
            error = true;
        }
        if (driver == null) {
            System.err.println("error: driver is missing");
            error = true;
        }
        if (error)
            return;
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, login, password);
            ResultSet rs1;
            ResultSet rs2;
            String sch;
            String tbl;
            String col;
            int type;
            String res;
            DatabaseMetaData dmd = con.getMetaData();
            rs1 = dmd.getColumns(null,
                                 schema == null ? "%" : schema,
                                 table == null ? "%" : table,
                                 column == null ? "%" : column);
            while (rs1.next()) {
                sch = rs1.getString(2);
                tbl = rs1.getString(3);
                col = rs1.getString(4);
                type = rs1.getInt(5);
                try {
                    if (type == Types.VARCHAR) {
                        stmt = con.createPreparedStatement("SELECT " + col + " FROM " + sch + '.' + tbl + " WHERE " + col + " LIKE ?");
                        stmt.setString(1, value);
                        rs2 = stmt.executeQuery();
                        while (rs2.next()) {
                            res = rs2.getString(1);
                            System.out.println(sch + '.' + tbl + '.' + col + ':'  + res);
                        }
                    }
                } catch (SQLException f) {
                    f.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception f) {
                f.printStackTrace();
            }
        }
        return;
    }
    
}
