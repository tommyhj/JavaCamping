package javacamping;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/** Class for handling economic reports
 *
 * @author Tommy Hjertberg
 * @version 2017-08-13
 */
public class Economy extends javax.swing.JFrame {

    /**
     * Creates new form Economy
     */
    public Economy() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        economyReport1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        reportType = new javax.swing.JComboBox<>();
        economyReport = new javax.swing.JButton();
        fromDate = new javax.swing.JTextField();
        untilDate = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        mainMenu = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        economyReport1.setText("Skapa rapport");
        economyReport1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                economyReport1ActionPerformed(evt);
            }
        });

        jButton2.setText("Spara text till fil");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        reportType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Alla boenden", "Stugor", "Parkeringsplatser för husvagn/husbil", "Tältplatser", " " }));
        reportType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportTypeActionPerformed(evt);
            }
        });

        economyReport.setText("Skapa rapport");
        economyReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                economyReportActionPerformed(evt);
            }
        });

        fromDate.setText(LocalDate.now().minusMonths(1).toString());
        fromDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fromDateActionPerformed(evt);
            }
        });

        untilDate.setText(LocalDate.now().plusDays(1).toString());
        untilDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                untilDateActionPerformed(evt);
            }
        });

        jLabel1.setText("Filtrera på typ av boende");

        jLabel2.setText("Från:");

        jLabel3.setText("Till:");

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jScrollPane2.setViewportView(jTextArea1);

        mainMenu.setText("Tillbaka till menyn");
        mainMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainMenuActionPerformed(evt);
            }
        });

        jButton1.setText("Skriv text till fil");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(30, 30, 30)
                                .addComponent(jLabel2)
                                .addGap(52, 52, 52)
                                .addComponent(jLabel3))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(reportType, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fromDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(untilDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(economyReport)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mainMenu))
                            .addComponent(jButton1))
                        .addGap(0, 108, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(reportType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fromDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(untilDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(economyReport)
                    .addComponent(mainMenu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void reportTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportTypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_reportTypeActionPerformed

    private void fromDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fromDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fromDateActionPerformed

    private void economyReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_economyReportActionPerformed
        try {
            if (LocalDate.parse(fromDate.getText()).isAfter(LocalDate.parse(
                    untilDate.getText()))){
                jTextArea1.setText("Till-datumet kan inte vara tidigare än från-datumet");
            } else{
            jTextArea1.setText(
                    JavaCamping.generateEconomyReports(
                            LocalDate.parse(fromDate.getText()), 
                            LocalDate.parse(untilDate.getText()), 
                            reportType.getSelectedItem().toString()));
            }
        } catch (DateTimeParseException e){
            jTextArea1.setText("Datum måste anges i formatet ÅÅÅÅ-MM-DD");
        } 
    }//GEN-LAST:event_economyReportActionPerformed

    private void untilDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_untilDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_untilDateActionPerformed

    private void economyReport1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_economyReport1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_economyReport1ActionPerformed

    private void mainMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainMenuActionPerformed
        this.setVisible(false);
        GUI main_window = new GUI();
        main_window.setTitle("JavaCamping");
        main_window.setVisible(true);
    }//GEN-LAST:event_mainMenuActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        saveText(jTextArea1.getText().replace("\n", "\r\n"), "print.txt");
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Economy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Economy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Economy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Economy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Economy().setVisible(true);
            }
        });
    }

    /**
     *
     * @param text
     * @param file
     */
    public void saveText(String text, String file){
        try {
            try {
                PrintWriter outStream = new PrintWriter
                                                    (new BufferedWriter
                                                    (new OutputStreamWriter
                                                    (new FileOutputStream(file),
                                                            "UTF-8")));
                outStream.print(text);
                outStream.close();
                JOptionPane.showMessageDialog(null, "Texten sparades i " + file, "Utskrift klar", 
                JOptionPane.INFORMATION_MESSAGE);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Economy.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Economy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton economyReport;
    private javax.swing.JButton economyReport1;
    private javax.swing.JTextField fromDate;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JButton mainMenu;
    private javax.swing.JComboBox<String> reportType;
    private javax.swing.JTextField untilDate;
    // End of variables declaration//GEN-END:variables
}
