package com.p2lp2;

import java.sql.Connection;
import java.sql.DriverManager;

public class TesteConexao {
    public static void main(String[] args) {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=P2_LP2;encrypt=true;trustServerCertificate=true";
        String user = "sa";
        String password = "123";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ CONEXÃO OK!");
            conn.close();
        } catch (Exception e) {
            System.out.println("❌ ERRO: " + e.getMessage());
        }
    }
}