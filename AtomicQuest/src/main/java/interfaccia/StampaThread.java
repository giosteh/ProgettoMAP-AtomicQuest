
package interfaccia;


import controller.OutputParser;
import java.util.Random;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class StampaThread extends Thread {
    
    private final String testo;
    private final JTextArea textArea;
    private final Random random = new Random();
    private JTextField textField;
    private OutputParser output;
    
    
    public StampaThread(final String testo, final JTextArea textArea, final JTextField textField) {
        this.testo = testo;
        this.textArea = textArea;
        this.textField = textField;
    }
    
    public StampaThread(final String testo, final JTextArea textArea) {
        this.testo = testo;
        this.textArea = textArea;
    }
    
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
    
    public void stampa() {
        this.start();
    }
}
