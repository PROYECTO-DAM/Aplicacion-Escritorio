package Frames;

import Network.Usuarios.UserConnections;
import SessionManager.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PerfilScreen extends JFrame {

    private final SessionManager sessionManager = SessionManager.getInstance();
    private JPanel perfilPanel;
    private JPanel headerPanel;
    private JLabel usuarioLabel;
    private JPanel contenidoPrincipal;
    private JPanel footerPanel;
    private JLabel labelAdmin;
    private JLabel labelFichajes;
    private JLabel labelNominas;
    private JPanel panelBotones;
    private JPanel panelDatos;
    private JTextField textFieldNombre;
    private JTextField textFieldCorreo;
    private JPasswordField textFieldPassword;
    private JButton modificarUsuarioButton;
    private JButton eliminarUsuarioButton;
    private JLabel labelNombre;
    private JLabel labelCorreo;
    private JLabel labelContraseña;
    private JButton logoutButton;

    private String nombre;
    private String correo;
    private String contraseña;

    public PerfilScreen() {
        setContentPane(perfilPanel);
        setTitle("Nóminas y Fichajes DEP");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(680, 460));
        setMaximumSize(new Dimension(680, 460));
        setLocationRelativeTo(null);
        setVisible(true);

        usuarioLabel.setText(sessionManager.getUsuario().getName());
        usuarioLabel.setForeground(Color.WHITE);

        labelFichajes.setForeground(Color.WHITE);
        labelFichajes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        labelNominas.setForeground(Color.WHITE);
        labelNominas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        labelAdmin.setForeground(Color.WHITE);
        labelAdmin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        headerPanel.setMinimumSize(new Dimension(640, 40));
        headerPanel.setMaximumSize(new Dimension(640, 40));
        footerPanel.setMinimumSize(new Dimension(640, 40));
        footerPanel.setMaximumSize(new Dimension(640, 40));

        labelFichajes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                FichajesScreen fichajesScreen = new FichajesScreen();
                fichajesScreen.setVisible(true);
                PerfilScreen.super.dispose();
            }
        });

        labelAdmin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(sessionManager.getUsuario().getRole().equals("Recursos Humanos")) {
                    AdminScreen adminScreen = new AdminScreen();
                    adminScreen.setVisible(true);
                    PerfilScreen.super.dispose();
                } else {
                    JOptionPane.showMessageDialog(perfilPanel,"No tienes permiso para acceder a esta parte de la aplicación");
                }
            }
        });

        labelNominas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                NominasScreen nominasScreen = new NominasScreen();
                nominasScreen.setVisible(true);
                PerfilScreen.super.dispose();
            }
        });

        labelNombre.setText(sessionManager.getUsuario().getName());
        labelCorreo.setText(sessionManager.getUsuario().getEmail());
        labelContraseña.setText(sessionManager.getPassword());

        modificarUsuarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(validarCampos(nombre, correo, contraseña)) {
                    UserConnections userConnections = new UserConnections();
                    userConnections.updateUser(sessionManager.getUsuario().getId(), nombre, sessionManager.getUsuario().getRole(), correo, contraseña);
                    MainScreen mainScreen = new MainScreen();
                    mainScreen.setVisible(true);
                    PerfilScreen.super.dispose();
                }
            }
        });

        eliminarUsuarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opcion = JOptionPane.showConfirmDialog(panelDatos, "¿Seguro que quieres borrar tu usuario? Esta accion no se puede deshacer", "Elimiar Usuario", JOptionPane.YES_NO_CANCEL_OPTION);
                if(opcion == JOptionPane.YES_OPTION) {
                    UserConnections userConnections = new UserConnections();
                    userConnections.deleteUser(sessionManager.getUsuario().getId());
                    LoginScreen loginScreen = new LoginScreen();
                    loginScreen.setVisible(true);
                    PerfilScreen.super.dispose();
                }
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sessionManager.cleanUsuario();
                LoginScreen loginScreen = new LoginScreen();
                loginScreen.setVisible(true);
                PerfilScreen.super.dispose();
            }
        });

        textFieldNombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                nombre = textFieldNombre.getText();
            }
        });

        textFieldCorreo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                correo = textFieldCorreo.getText();
            }
        });

        textFieldPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                contraseña = String.valueOf(textFieldPassword.getPassword());
            }
        });
    }

    private Boolean validarCampos(String nombre, String correo, String password) {
        return nombre.length() > 0 && correo.length() > 0 && password.length() > 8;
    }
}
