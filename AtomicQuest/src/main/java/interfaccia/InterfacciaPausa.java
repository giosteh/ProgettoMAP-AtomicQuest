
package interfaccia;

import java.awt.Dimension;
import java.awt.Toolkit;
import controller.GestioneSalvataggi;
import entita.Giocatore;

/**
 * Classe che rappresenta l'interfaccia di pausa.
 */
public class InterfacciaPausa extends javax.swing.JFrame {

    private final InterfacciaGioco interfacciaGioco;
    private final Giocatore giocatore;
    
    /**
     * Costruttore di default.
     * @param interfacciaGioco l'interfaccia del gioco
     * @param giocatore il giocatore
     */
    public InterfacciaPausa(InterfacciaGioco interfacciaGioco, Giocatore giocatore) {
        initComponents();
        centraFrame();
        this.interfacciaGioco = interfacciaGioco;
        this.giocatore = giocatore;
        interfacciaGioco.setVisible(false);
    }
    
    /**
     * Metodo che centra il frame.
     */
    private void centraFrame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        int x = (screenSize.width - frameSize.width) / 2;
        int y = (screenSize.height - frameSize.height) / 2;
        setLocation(x, y);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        buttonRiprendi = new javax.swing.JButton();
        buttonSalva = new javax.swing.JButton();
        buttonEsci = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        buttonRiprendi.setBackground(new java.awt.Color(43, 147, 72));
        buttonRiprendi.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        buttonRiprendi.setForeground(new java.awt.Color(255, 255, 255));
        buttonRiprendi.setText("RIPRENDI");
        buttonRiprendi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        buttonRiprendi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRiprendiActionPerformed(evt);
            }
        });

        buttonSalva.setBackground(new java.awt.Color(43, 147, 72));
        buttonSalva.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        buttonSalva.setForeground(new java.awt.Color(255, 255, 255));
        buttonSalva.setText("SALVA");
        buttonSalva.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        buttonSalva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSalvaActionPerformed(evt);
            }
        });

        buttonEsci.setBackground(new java.awt.Color(43, 147, 72));
        buttonEsci.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        buttonEsci.setForeground(new java.awt.Color(255, 255, 255));
        buttonEsci.setText("ESCI");
        buttonEsci.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        buttonEsci.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEsciActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(buttonRiprendi, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buttonSalva, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buttonEsci, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonRiprendi, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonSalva, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonEsci, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(60, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonRiprendiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRiprendiActionPerformed
        this.dispose();
        this.interfacciaGioco.setVisible(true);
    }//GEN-LAST:event_buttonRiprendiActionPerformed

    private void buttonEsciActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEsciActionPerformed
        System.exit(0);
    }//GEN-LAST:event_buttonEsciActionPerformed

    private void buttonSalvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSalvaActionPerformed
        GestioneSalvataggi.creaTabellaInDB();
        GestioneSalvataggi.rimuoviDaDB("Utente");
        GestioneSalvataggi.inserisciInDB("Utente", this.giocatore);
        this.interfacciaGioco.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_buttonSalvaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonEsci;
    private javax.swing.JButton buttonRiprendi;
    private javax.swing.JButton buttonSalva;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
