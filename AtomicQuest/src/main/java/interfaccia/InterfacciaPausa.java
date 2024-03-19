/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package interfaccia;

import java.awt.Dimension;
import java.awt.Toolkit;
import controller.GestioneSalvataggi;
import entita.Giocatore;

/**
 *
 * @author giuse
 */
public class InterfacciaPausa extends javax.swing.JFrame {

    private InterfacciaGioco interfacciaGioco;
    private Giocatore giocatore;
    
    public InterfacciaPausa(InterfacciaGioco interfacciaGioco, Giocatore giocatore) {
        initComponents();
        centraFrame();
        this.interfacciaGioco = interfacciaGioco;
        this.giocatore = giocatore;
        interfacciaGioco.setVisible(false);
    }
    
     private void centraFrame() {
        // Ottenere la dimensione dello schermo
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Ottenere la dimensione del frame
        Dimension frameSize = getSize();

        // Calcolare la posizione del frame per centrarlo
        int x = (screenSize.width - frameSize.width) / 2;
        int y = (screenSize.height - frameSize.height) / 2;

        // Impostare la posizione del frame
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

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        buttonRiprendi.setText("RIPRENDI");
        buttonRiprendi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRiprendiActionPerformed(evt);
            }
        });

        buttonSalva.setText("SALVA");
        buttonSalva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSalvaActionPerformed(evt);
            }
        });

        buttonEsci.setText("ESCI");
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
                .addGap(210, 210, 210)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonRiprendi, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonSalva, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonEsci, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(210, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(buttonRiprendi, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(buttonSalva, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addComponent(buttonEsci, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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