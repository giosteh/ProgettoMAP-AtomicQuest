
package controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.List;

/**
 * Classe che si occupa della gestione dei file.
 */
public class GestioneFile {

    /**
     * Metodo che carica una mappa da file.
     * @param nomeFile il nome del file
     * @return la mappa caricata
     */
    public static <E, F> Map<E, F> caricaMap(final String nomeFile) {
        FileInputStream inFile = null;
        try {
            inFile = new FileInputStream(nomeFile);
            ObjectInputStream inStream = new ObjectInputStream(inFile);
            return (Map<E, F>) inStream.readObject();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        } finally {
            try {
                if (inFile != null) {
                    inFile.close();
                }
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
        return null;
    }

    /**
     * Metodo che salva una mappa su file.
     * @param mappa la mappa da salvare
     * @param nomeFile il nome del file
     */
    public static <E, F> void salvaMap(Map<E, F> mappa, final String nomeFile) {
        FileOutputStream outFile = null;
        try {
            outFile = new FileOutputStream(nomeFile);
            ObjectOutputStream outStream = new ObjectOutputStream(outFile);
            outStream.writeObject(mappa);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } finally {
            try {
                if (outFile != null) {
                    outFile.close();
                }
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    /**
     * Metodo che carica una lista da file.
     * @param nomeFile il nome del file
     * @return la lista caricata
     */
    public static <E> List<E> caricaList(final String nomeFile) {
        FileInputStream inFile = null;
        try {
            inFile = new FileInputStream(nomeFile);
            ObjectInputStream inStream = new ObjectInputStream(inFile);
            return (List<E>) inStream.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        } finally {
            try {
                if (inFile != null) {
                    inFile.close();
                }
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
        return null;
    }

    /**
     * Metodo che salva una lista su file.
     * @param lista la lista da salvare
     * @param nomeFile il nome del file
     */
    public static <E> void salvaList(List<E> lista, final String nomeFile) {
        FileOutputStream outFile = null;
        try {
            outFile = new FileOutputStream(nomeFile);
            ObjectOutputStream outStream = new ObjectOutputStream(outFile);
            outStream.writeObject(lista);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } finally {
            try {
                if (outFile != null) {
                    outFile.close();
                }
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
}
