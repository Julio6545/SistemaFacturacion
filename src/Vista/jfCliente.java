/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

/**
 *
 * @author JFAA
 */
public class jfCliente extends javax.swing.JFrame {

    /**
     * Creates new form jfCliente
     */
    public jfCliente() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpFondo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtNroCedula = new javax.swing.JTextField();
        txtRuc = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        txtDireccion = new javax.swing.JTextField();
        jcbCiudad = new javax.swing.JComboBox<>();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jcbTipoCliente = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Clientes");

        jpFondo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Lucida Sans", 1, 15)); // NOI18N
        jLabel1.setText("Nro. Cédula :");
        jpFondo.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, -1, -1));

        jLabel2.setFont(new java.awt.Font("Lucida Sans", 1, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 1, 59));
        jLabel2.setText("R.U.C. :");
        jpFondo.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, -1, -1));

        jLabel3.setFont(new java.awt.Font("Lucida Sans", 1, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 1, 59));
        jLabel3.setText("Nombre :");
        jpFondo.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 180, -1, -1));

        jLabel4.setFont(new java.awt.Font("Lucida Sans", 1, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 1, 59));
        jLabel4.setText("Apellido :");
        jpFondo.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 220, -1, -1));

        jLabel5.setFont(new java.awt.Font("Lucida Sans", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 1, 59));
        jLabel5.setText("Teléfono :");
        jpFondo.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 260, -1, -1));

        jLabel6.setFont(new java.awt.Font("Lucida Sans", 1, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 1, 59));
        jLabel6.setText("Dirección :");
        jpFondo.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 300, -1, -1));

        jLabel7.setFont(new java.awt.Font("Lucida Sans", 1, 15)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 1, 59));
        jLabel7.setText("Ciudad :");
        jpFondo.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 350, -1, -1));

        txtNroCedula.setBackground(new java.awt.Color(0, 1, 59));
        txtNroCedula.setFont(new java.awt.Font("Lucida Sans", 1, 15)); // NOI18N
        txtNroCedula.setForeground(new java.awt.Color(255, 255, 255));
        txtNroCedula.setBorder(null);
        txtNroCedula.setCaretColor(new java.awt.Color(255, 255, 255));
        txtNroCedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNroCedulaActionPerformed(evt);
            }
        });
        jpFondo.add(txtNroCedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, 231, 23));

        txtRuc.setBackground(new java.awt.Color(0, 1, 59));
        txtRuc.setFont(new java.awt.Font("Lucida Sans", 1, 15)); // NOI18N
        txtRuc.setForeground(new java.awt.Color(255, 255, 255));
        txtRuc.setBorder(null);
        txtRuc.setCaretColor(new java.awt.Color(255, 255, 255));
        jpFondo.add(txtRuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 140, 231, 23));

        txtNombre.setBackground(new java.awt.Color(0, 1, 59));
        txtNombre.setFont(new java.awt.Font("Lucida Sans", 1, 15)); // NOI18N
        txtNombre.setForeground(new java.awt.Color(255, 255, 255));
        txtNombre.setBorder(null);
        txtNombre.setCaretColor(new java.awt.Color(255, 255, 255));
        jpFondo.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 180, 231, 23));

        txtApellido.setBackground(new java.awt.Color(0, 1, 59));
        txtApellido.setFont(new java.awt.Font("Lucida Sans", 1, 15)); // NOI18N
        txtApellido.setForeground(new java.awt.Color(255, 255, 255));
        txtApellido.setBorder(null);
        txtApellido.setCaretColor(new java.awt.Color(255, 255, 255));
        jpFondo.add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 220, 231, 23));

        txtTelefono.setBackground(new java.awt.Color(0, 1, 59));
        txtTelefono.setFont(new java.awt.Font("Lucida Sans", 1, 15)); // NOI18N
        txtTelefono.setForeground(new java.awt.Color(255, 255, 255));
        txtTelefono.setBorder(null);
        txtTelefono.setCaretColor(new java.awt.Color(255, 255, 255));
        jpFondo.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 260, 231, 23));

        jLabel8.setBackground(new java.awt.Color(153, 204, 255));
        jLabel8.setFont(new java.awt.Font("Lucida Sans", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 1, 59));
        jLabel8.setText("Registro de Clientes");
        jLabel8.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(0, 0, 0)));
        jLabel8.setOpaque(true);
        jpFondo.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 30, -1, -1));

        btnBuscar.setBackground(new java.awt.Color(153, 204, 255));
        btnBuscar.setFont(new java.awt.Font("Lucida Sans", 1, 15)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(0, 1, 59));
        btnBuscar.setText("Buscar");
        btnBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jpFondo.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 90, -1, -1));

        txtDireccion.setBackground(new java.awt.Color(0, 1, 59));
        txtDireccion.setFont(new java.awt.Font("Lucida Sans", 1, 15)); // NOI18N
        txtDireccion.setForeground(new java.awt.Color(255, 255, 255));
        txtDireccion.setBorder(null);
        txtDireccion.setCaretColor(new java.awt.Color(255, 255, 255));
        jpFondo.add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 300, 302, 23));

        jcbCiudad.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jcbCiudad.setBorder(null);
        jpFondo.add(jcbCiudad, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 350, 132, -1));

        btnGuardar.setBackground(new java.awt.Color(153, 204, 255));
        btnGuardar.setFont(new java.awt.Font("Lucida Sans", 1, 15)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(0, 102, 51));
        btnGuardar.setText("Guardar");
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jpFondo.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 440, 100, -1));

        btnModificar.setBackground(new java.awt.Color(153, 204, 255));
        btnModificar.setFont(new java.awt.Font("Lucida Sans", 1, 15)); // NOI18N
        btnModificar.setForeground(new java.awt.Color(0, 1, 59));
        btnModificar.setText("Modificar");
        btnModificar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jpFondo.add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 440, 120, -1));

        btnBorrar.setBackground(new java.awt.Color(153, 204, 255));
        btnBorrar.setFont(new java.awt.Font("Lucida Sans", 1, 15)); // NOI18N
        btnBorrar.setForeground(new java.awt.Color(255, 0, 0));
        btnBorrar.setText("Eliminar");
        btnBorrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jpFondo.add(btnBorrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 440, 100, -1));

        jLabel9.setFont(new java.awt.Font("Lucida Sans", 1, 15)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 1, 59));
        jLabel9.setText("Tipo Cliente:");
        jpFondo.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 390, -1, -1));

        jcbTipoCliente.setFont(new java.awt.Font("Lucida Sans", 1, 15)); // NOI18N
        jcbTipoCliente.setForeground(new java.awt.Color(0, 1, 59));
        jcbTipoCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Motos Repuestos", "Lubricantes" }));
        jcbTipoCliente.setBorder(null);
        jpFondo.add(jcbTipoCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 390, 130, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpFondo, javax.swing.GroupLayout.DEFAULT_SIZE, 603, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpFondo, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNroCedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNroCedulaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNroCedulaActionPerformed

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
            java.util.logging.Logger.getLogger(jfCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jfCliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnBorrar;
    public javax.swing.JButton btnBuscar;
    public javax.swing.JButton btnGuardar;
    public javax.swing.JButton btnModificar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    public javax.swing.JComboBox<String> jcbCiudad;
    public javax.swing.JComboBox<String> jcbTipoCliente;
    public javax.swing.JPanel jpFondo;
    public javax.swing.JTextField txtApellido;
    public javax.swing.JTextField txtDireccion;
    public javax.swing.JTextField txtNombre;
    public javax.swing.JTextField txtNroCedula;
    public javax.swing.JTextField txtRuc;
    public javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
