
package interfaccia;

import java.util.Random;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class StampaThread extends Thread {
    
    private final String testo;
    private final JTextArea textArea;
    private final Random random = new Random();
    private final JTextField textField;

    public StampaThread(final String testo, final JTextArea textArea, final JTextField textField) {
        this.testo = testo;
        this.textArea = textArea;
        this.textField = textField;
    }

    @Override
    public void run() {
        try {
            this.textField.setEditable(false);
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
            this.textField.setEditable(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void stampa() {
        this.start();
    }
}
