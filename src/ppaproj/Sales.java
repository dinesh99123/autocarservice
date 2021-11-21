/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ppaproj;

import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

/**
 *
 * @author Hp
 */
public class Sales extends javax.swing.JFrame {

    /**
     * Creates new form Sales
     */
    public Sales() {
        initComponents();
        Connect();
    }

    Connection con;
    PreparedStatement pst;
    PreparedStatement pst1;
    PreparedStatement pst2;
    DefaultTableModel df;
    ResultSet rs;

    public void Connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/autocarservice", "root", "");

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Sales.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void barcode() {
        String barcode = txtbarcode.getText();
        try {
            pst = con.prepareStatement("select * from spareparts where Barcode =?");
            pst.setString(1, barcode);
            rs = pst.executeQuery();

            if (rs.next() == false) {
                JOptionPane.showMessageDialog(this, "Barcode Not Found");
                txtbarcode.setText("");

            } else {
                String sparepart = rs.getString("Spare_Part");
                String price = rs.getString("Rental_Price");

                txtsparepart.setText(sparepart.trim());
                txtprice.setText(price.trim());
                txtqty.requestFocus();

            }
        } catch (SQLException ex) {
            Logger.getLogger(Sales.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void sales() {
        String barcode = txtbarcode.getText();
        try {
            pst = con.prepareStatement("select * from spareparts where Barcode =?");
            pst.setString(1, barcode);
            rs = pst.executeQuery();

            while (rs.next()) {
                int current_qty;
                current_qty = rs.getInt("qty");
                int price = Integer.parseInt(txtprice.getText());
                int qty = Integer.parseInt(txtqty.getText());
                int tot = price * qty;

                if (qty >= current_qty) {
                    JOptionPane.showMessageDialog(this, "Quantity is not enough");
                } else {
                    df = (DefaultTableModel) jTable2.getModel();
                    df.addRow(new Object[]{
                        txtbarcode.getText(),
                        txtsparepart.getText(),
                        txtprice.getText(),
                        txtqty.getText(),
                        tot});
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Sales.class.getName()).log(Level.SEVERE, null, ex);
        }

        int sum = 0;
        for (int i = 0; i < jTable2.getRowCount(); i++) {
            sum = sum + Integer.parseInt(jTable2.getValueAt(i, 4).toString());

        }
        txttotal_cost.setText(String.valueOf(sum));
        txtbarcode.setText("");
        txtsparepart.setText("");
        txtprice.setText("");
        txtqty.setText("");
    }

    public void add() {
        try {
            DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDateTime now = LocalDateTime.now();
            String date = dt.format(now);

            String total_cost = txttotal_cost.getText();
            String payment = txtpayment.getText();
            String balance = txtbalance.getText();

            int lastid = 0;

            String query1 = "insert into sales (date, TotalCost, Payment, Balance) values(?, ?, ?, ?)";

            pst = con.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, date);
            pst.setString(2, total_cost);
            pst.setString(3, payment);
            pst.setString(4, balance);
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();

            if (rs.next()) {
                lastid = rs.getInt(1);
            }

            String query2 = "insert into salesitem(SalesID, Barcode, Rental_Price, Qty, TotalCost) values(?, ?, ?, ?, ?)";
            pst1 = con.prepareStatement(query2);
            String barcode;
            String rental_price;
            String qty;
            int tot_cost;

            for (int i = 0; i < jTable2.getRowCount(); i++) {
                barcode = (String) jTable2.getValueAt(i, 0);
                rental_price = (String) jTable2.getValueAt(i, 2);
                qty = (String) jTable2.getValueAt(i, 3);
                tot_cost = (int) jTable2.getValueAt(i, 4);

                pst1.setInt(1, lastid);
                pst1.setString(2, barcode);
                pst1.setString(3, rental_price);
                pst1.setString(4, qty);
                pst1.setInt(5, tot_cost);
                pst1.executeUpdate();

            }

            String query3 = "update spareparts set Qty = Qty-? where Barcode =?";
            pst2 = con.prepareStatement(query3);
            for (int i = 0; i < jTable2.getRowCount(); i++) {
                barcode = (String) jTable2.getValueAt(i, 0);
                qty = (String) jTable2.getValueAt(i, 3);

                pst2.setString(1, qty);
                pst2.setString(2, barcode);
                pst2.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Sales Completed");

        } catch (SQLException ex) {
            Logger.getLogger(Purchase.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txtprice = new javax.swing.JTextField();
        txtsparepart = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        txtqty = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txttotal_cost = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtpayment = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtbalance = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        txtbarcode = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 153, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Sales");

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setPreferredSize(new java.awt.Dimension(720, 550));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Bar Code");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 26, 100, -1));

        jLabel3.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Spare Part");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(164, 26, 100, -1));

        jLabel4.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Price");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(307, 26, 100, -1));

        jButton1.setBackground(new java.awt.Color(204, 204, 204));
        jButton1.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jButton1.setText("Add");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(568, 61, -1, -1));

        txtprice.setMinimumSize(new java.awt.Dimension(7, 120));
        jPanel1.add(txtprice, new org.netbeans.lib.awtextra.AbsoluteConstraints(307, 62, 100, -1));

        txtsparepart.setMinimumSize(new java.awt.Dimension(7, 120));
        jPanel1.add(txtsparepart, new org.netbeans.lib.awtextra.AbsoluteConstraints(164, 62, 100, -1));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Bar Code", "Spare Part", "Price", "Qty", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable2);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 104, 505, 331));

        txtqty.setMinimumSize(new java.awt.Dimension(7, 120));
        jPanel1.add(txtqty, new org.netbeans.lib.awtextra.AbsoluteConstraints(449, 62, 100, -1));

        jLabel7.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Qty");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(449, 26, 100, -1));

        jLabel8.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Total Cost");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(568, 133, 113, -1));

        txttotal_cost.setMinimumSize(new java.awt.Dimension(7, 120));
        jPanel1.add(txttotal_cost, new org.netbeans.lib.awtextra.AbsoluteConstraints(568, 168, 113, -1));

        jLabel9.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Payment");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(572, 233, 112, -1));

        txtpayment.setMinimumSize(new java.awt.Dimension(7, 120));
        jPanel1.add(txtpayment, new org.netbeans.lib.awtextra.AbsoluteConstraints(569, 268, 112, -1));

        jLabel10.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Balance");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(569, 332, 115, -1));

        txtbalance.setMinimumSize(new java.awt.Dimension(7, 120));
        jPanel1.add(txtbalance, new org.netbeans.lib.awtextra.AbsoluteConstraints(569, 367, 115, -1));

        jButton2.setBackground(new java.awt.Color(204, 204, 204));
        jButton2.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jButton2.setText("Close");
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(457, 446, -1, -1));

        jButton3.setBackground(new java.awt.Color(204, 204, 204));
        jButton3.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jButton3.setText("Add");
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(317, 446, -1, -1));

        txtbarcode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbarcodeKeyPressed(evt);
            }
        });
        jPanel1.add(txtbarcode, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 62, 100, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
            .addGroup(layout.createSequentialGroup()
                .addGap(286, 286, 286)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        sales();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        int payment = Integer.parseInt(txtpayment.getText());
        int total_cost = Integer.parseInt(txttotal_cost.getText());
        int balance = payment - total_cost;

        txtbalance.setText(String.valueOf(balance));

        add();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtbarcodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbarcodeKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            barcode();

        }
    }//GEN-LAST:event_txtbarcodeKeyPressed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sales().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField txtbalance;
    private javax.swing.JTextField txtbarcode;
    private javax.swing.JTextField txtpayment;
    private javax.swing.JTextField txtprice;
    private javax.swing.JTextField txtqty;
    private javax.swing.JTextField txtsparepart;
    private javax.swing.JTextField txttotal_cost;
    // End of variables declaration//GEN-END:variables
}
