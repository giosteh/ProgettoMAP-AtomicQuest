
package interfaccia;

import java.util.Random;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;


public class StampaThread extends Thread {
    
    private final String testo;
    private final JTextArea textArea;
    private final Random random = new Random();

    public StampaThread(final String testo, final JTextArea textArea) {
        this.testo = testo;
        this.textArea = textArea;
    }

    @Override
    public void run() {
        try {
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

                Thread.sleep(random.nextInt(131) + 20);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void stampa() {
        this.start();
    }
}
