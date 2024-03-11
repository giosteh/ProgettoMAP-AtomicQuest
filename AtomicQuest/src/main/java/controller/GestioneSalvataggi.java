
package controller;

import entita.Giocatore;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class GestioneSalvataggi {
    
    private static final String CREAZIONETABELLA = "CREATE TABLE IF NOT EXISTS salvataggi"
            + "(id INT AUTO_INCREMENT PRIMARY KEY, nome VARCHAR(1024), obj BLOB);";
    
    private static Connection connettiAlDB() {
        String dbpath = "jdbc:h2:";
        Properties props = new Properties();
        props.setProperty("user", "atom");
        props.setProperty("password", "1234");
        try {
            Connection conn = DriverManager.getConnection(dbpath, props);
            return conn;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
    
    public static void creaTabellaInDB() {
        try {
            Connection conn = GestioneSalvataggi.connettiAlDB();
            Statement stm = conn.createStatement();
            stm.executeUpdate(CREAZIONETABELLA);
            stm.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public static void eseguiInsertInDB(final String nome, final Giocatore obj) {
        
    }
    
    public static Giocatore eseguiCaricamentoDaDB(final int id) {
        
    }
    
    public static Giocatore eseguiCaricamentoDaDB(final String nome) {
        
    }
}
