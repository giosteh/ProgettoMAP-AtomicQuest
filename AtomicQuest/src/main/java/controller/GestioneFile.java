
package controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.List;


public class GestioneFile {

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
