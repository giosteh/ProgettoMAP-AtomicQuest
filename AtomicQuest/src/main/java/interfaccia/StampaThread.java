
package interfaccia;


import controller.OutputParser;
import java.util.Random;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * Classe che rappresenta un thread per la stampa di un testo.
 */
public class StampaThread extends Thread {
    
    private final String testo;
    private final JTextArea textArea;
    private final Random random = new Random();
    private JTextField textField;
    private OutputParser output;
    
    /**
     * Costruttore con parametri.
     * @param testo il testo da stampare
     * @param textArea l'area di testo in cui stampare il testo
     * @param textField il campo di testo da disabilitare
     */
    public StampaThread(final String testo, final JTextArea textArea, final JTextField textField) {
        this.testo = testo;
        this.textArea = textArea;
        this.textField = textField;
    }
    
    /**
     * Costruttore con parametri.
     * @param testo il testo da stampare
     * @param textArea l'area di testo in cui stampare il testo
     */
    public StampaThread(final String testo, final JTextArea textArea) {
        this.testo = testo;
        this.textArea = textArea;
    }
    
    /**
     * Costruttore con parametri.
     * @param testo il testo da stampare
     * @param textArea l'area di testo in cui stampare il testo
     * @param textField il campo di testo da disabilitare
     * @param output l'output da stampare
     */
    public StampaThread(final String testo, final JTextArea textArea, final JTextField textField, OutputParser output) {
        this.testo = testo;
        this.textArea = textArea;
        this.textField = textField;
        this.output = output;
    }

    @Override
    public void run() {
        try {
            if (this.textField != null) {
                this.textField.setEditable(false);
            }
            int lunghezzaTesto = testo.length();
            int indiceCorrente = 0;

            while (indiceCorrente < lunghezzaTesto) {
                int numCaratteri = random.nextInt(20) + 5;
                StringBuilder sb = new StringBuilder();

                for (int i = 0; i < numCaratteri && indiceCorrente < lunghezzaTesto; i++) {
                    sb.append(testo.charAt(indiceCorrente));
                    indiceCorrente++;
                }
                final String testoParziale = sb.toString();
                SwingUtilities.invokeLater(() -> this.textArea.append(testoParziale));

                Thread.sleep(random.nextInt(181) + 20);
            }
            if (this.textField != null) {
                this.textField.setEditable(true);
            }
            
            if (this.output != null) {
                this.textField.setEditable(false);
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Metodo che avvia il thread.
     */
    public void stampa() {
        this.start();
    }
}
