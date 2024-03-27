
package controller;

import entita.Giocatore;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Classe che si occupa della gestione dei salvataggi.
 */
public class GestioneSalvataggi {
    
    private static final String CREAZIONETABELLA = "CREATE TABLE IF NOT EXISTS salvataggi"
            + "(id INT AUTO_INCREMENT PRIMARY KEY, nome VARCHAR(1024), obj BLOB);";

    /**
     * Metodo che si connette al database.
     */
    private static Connection connettiAlDB() {
        String dbpath = "jdbc:h2:./risorse/db/salvataggio";
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

    /**
     * Metodo che crea la tabella nel database.
     */
    public static void creaTabellaInDB() {
        try (Connection conn = GestioneSalvataggi.connettiAlDB();
                Statement stm = conn.createStatement()) {
            stm.executeUpdate(CREAZIONETABELLA);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Metodo che inserisce un oggetto nel database.
     * @param nome il nome dell'oggetto da inserire
     * @param obj l'oggetto da inserire
     */
    public static boolean inserisciInDB(final String nome, final Giocatore obj) {
        try (Connection conn = GestioneSalvataggi.connettiAlDB();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream outStream = new ObjectOutputStream(baos);
                PreparedStatement pstm = conn.prepareStatement("INSERT INTO salvataggi (nome, obj) VALUES (?, ?)")) {

            outStream.writeObject(obj);
            byte[] serializedObj = baos.toByteArray();
            pstm.setString(1, nome);
            pstm.setBytes(2, serializedObj);
            pstm.executeUpdate();
            return true;
        } catch (SQLException | IOException ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }

    /**
     * Metodo che seleziona un oggetto dal database.
     * @param nome il nome dell'oggetto da selezionare
     */
    public static Giocatore selezionaDaDB(final String nome) {
        try (Connection conn = GestioneSalvataggi.connettiAlDB();
                PreparedStatement pstm = conn.prepareStatement("SELECT obj FROM salvataggi WHERE (nome = ?)")) {

            pstm.setString(1, nome);
            try (ResultSet res = pstm.executeQuery()) {
                if (res.next()) {
                    byte[] serializedObj = res.getBytes(1);
                    try (ByteArrayInputStream bais = new ByteArrayInputStream(serializedObj);
                            ObjectInputStream inStream = new ObjectInputStream(bais)) {

                        return (Giocatore) inStream.readObject();
                    } catch (ClassNotFoundException | IOException ex) {
                        System.err.println(ex.getMessage());
                    }
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    /**
     * Metodo che rimuove un oggetto dal database.
     * @param nome il nome dell'oggetto da rimuovere
     */
    public static void rimuoviDaDB(final String nome) {
        try (Connection conn = GestioneSalvataggi.connettiAlDB();
                PreparedStatement pstm = conn.prepareStatement("DELETE FROM salvataggi WHERE (nome = ?)")) {

            pstm.setString(1, nome);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    /**
     * Metodo che verifica se il database è vuoto.
     */
    public static boolean isDBVuoto() {
        try (Connection conn = GestioneSalvataggi.connettiAlDB();
                Statement stm = conn.createStatement();
                ResultSet res = stm.executeQuery("SELECT COUNT(*) FROM salvataggi")) {
            res.next();
            return res.getInt(1) == 0;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return true;
    }
    
}
