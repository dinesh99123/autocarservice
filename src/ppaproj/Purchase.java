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
public class Purchase extends javax.swing.JFrame {

    /**
     * Creates new form Purchase
     */
    public Purchase() {
        initComponents();
        Connect();
        Supplier();
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
            Logger.getLogger(Details.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Supplier() {
        try {
            pst = con.prepareStatement("select Distinct SupplierName from supplier");
            rs = pst.executeQuery();

            txtsupplier.removeAllItems();

            while (rs.next()) {
                txtsupplier.addItem(rs.getString("SupplierName"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Purchase.class.getName()).log(Level.SEVERE, null, ex);
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
                String price = rs.getString("Cost_Price");

                txtsparepart.setText(sparepart.trim());
                txtprice.setText(price.trim());
                txtqty.requestFocus();

            }
        } catch (SQLException ex) {
            Logger.getLogger(Purchase.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void purchase() {
        int price = Integer.parseInt(txtprice.getText());
        int qty = Integer.parseInt(txtqty.getText());

        int tot = price * qty;

        df = (DefaultTableModel) jTable1.getModel();
        df.addRow(new Object[]{
            txtbarcode.getText(),
            txtsparepart.getText(),
            txtprice.getText(),
            txtqty.getText(),
            tot});
        int sum = 0;
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            sum = sum + Integer.parseInt(jTable1.getValueAt(i, 4).toString());

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
            String supplier = txtsupplier.getSelectedItem().toString();
            String total_cost = txttotal_cost.getText();
            String payment = txtpayment.getText();
            String balance = txtbalance.getText();

            int lastid = 0;

            String query1 = "insert into purchase(date, SupplierName, TotalCost, Payment, Balance) values(?, ?, ?, ?, ?)";

            pst = con.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, date);
            pst.setString(2, supplier);
            pst.setString(3, total_cost);
            pst.setString(4, payment);
            pst.setString(5, balance);
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();

            if (rs.next()) {
                lastid = rs.getInt(1);
            }

            String query2 = "insert into purchaseitem(PurchaseID, Barcode, Cost_Price, Qty, TotalCost) values(?, ?, ?, ?, ?)";
            pst1 = con.prepareStatement(query2);
            String barcode;
            String cost_price;
            String qty;
            int tot_cost = 0;

            for (int i = 0; i < jTable1.getRowCount(); i++) {
                barcode = (String) jTable1.getValueAt(i, 0);
                cost_price = (String) jTable1.getValueAt(i, 2);
                qty = (String) jTable1.getValueAt(i, 3);
                tot_cost = (int) jTable1.getValueAt(i, 4);

                pst1.setInt(1, lastid);
                pst1.setString(2, barcode);
                pst1.setString(3, cost_price);
                pst1.setString(4, qty);
                pst1.setInt(5, tot_cost);
                pst1.executeUpdate();

            }

            String query3 = "update spareparts set Qty = Qty+? where Barcode =?";
            pst2 = con.prepareStatement(query3);
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                barcode = (String) jTable1.getValueAt(i, 0);
                qty = (String) jTable1.getValueAt(i, 3);

                pst2.setString(1, qty);
                pst2.setString(2, barcode);
                pst2.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Purchase Completed");

        } catch (SQLException ex) {
            Logger.getLogger(Purchase.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtsupplier = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txtprice = new javax.swing.JTextField();
        txtsparepart = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
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
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jLabel6.setText("Supplier");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(583, 13, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel2.setText("Purchase");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(256, 0, -1, -1));

        txtsupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtsupplierActionPerformed(evt);
            }
        });
        getContentPane().add(txtsupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(651, 10, 79, -1));

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setPreferredSize(new java.awt.Dimension(720, 550));

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Barcode");

        jLabel3.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Spare Part");

        jLabel4.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Price");

        jButton1.setBackground(new java.awt.Color(204, 204, 204));
        jButton1.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jButton1.setText("Add");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtprice.setMinimumSize(new java.awt.Dimension(7, 120));

        txtsparepart.setMinimumSize(new java.awt.Dimension(7, 120));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Barcode", "Spare Part", "Price", "Qty", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        txtqty.setMinimumSize(new java.awt.Dimension(7, 120));

        jLabel7.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Qty");

        jLabel8.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Total Cost");

        txttotal_cost.setMinimumSize(new java.awt.Dimension(7, 120));

        jLabel9.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Payment");

        txtpayment.setMinimumSize(new java.awt.Dimension(7, 120));

        jLabel10.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Balance");

        txtbalance.setMinimumSize(new java.awt.Dimension(7, 120));

        jButton2.setBackground(new java.awt.Color(204, 204, 204));
        jButton2.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jButton2.setText("Close");
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(204, 204, 204));
        jButton3.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        jButton3.setText("Add");
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        txtbarcode.setMinimumSize(new java.awt.Dimension(7, 120));
        txtbarcode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbarcodeKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtbarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtsparepart, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtprice, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtqty, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                        .addGap(168, 168, 168))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(29, 29, 29)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtpayment, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtbalance, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(28, 28, 28)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jButton1)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                                            .addComponent(txttotal_cost, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                        .addContainerGap(33, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(89, 89, 89)
                .addComponent(jButton2)
                .addGap(201, 201, 201))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(txtprice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtsparepart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtqty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtbarcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(txttotal_cost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(txtpayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(txtbalance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap(76, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 48, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        purchase();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        int payment = Integer.parseInt(txtpayment.getText());
        int total_cost = Integer.parseInt(txttotal_cost.getText());
        int balance = total_cost - payment;

        txtbalance.setText(String.valueOf(balance));
        add();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtsupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtsupplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsupplierActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Purchase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Purchase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Purchase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Purchase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Purchase().setVisible(true);
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
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtbalance;
    private javax.swing.JTextField txtbarcode;
    private javax.swing.JTextField txtpayment;
    private javax.swing.JTextField txtprice;
    private javax.swing.JTextField txtqty;
    private javax.swing.JTextField txtsparepart;
    private javax.swing.JComboBox<String> txtsupplier;
    private javax.swing.JTextField txttotal_cost;
    // End of variables declaration//GEN-END:variables
}
