package Frames;

import Clases.Usuario;
import Network.Proyectos.ProyectoConnections;
import Network.Usuarios.UserConnections;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterScreen extends JFrame {

    private String nombre = "";
    private String role = "";
    private String correo = "";
    private String contraseña = "";

    private final UserConnections userConnections = new UserConnections();

    private final ProyectoConnections proyectoConnections = new ProyectoConnections();
    private JPanel registerPanel;
    private JLabel nameLabel;
    private JTextField nombreField;
    private JLabel roleLabel;
    private JTextField roleField;
    private JLabel emailLabel;
    private JTextField emailField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton registrarseButton;
    private JLabel loginLabel;

    public RegisterScreen() {
        setContentPane(registerPanel);
        setTitle("Nóminas y Fichajes DEP");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(640, 360));
        setMaximumSize(new Dimension(640, 360));
        setLocationRelativeTo(null);
        setVisible(true);

        registrarseButton.setEnabled(validarUsuario(nombre, role, correo, contraseña));

        nombreField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                nombre = nombreField.getText();
                registrarseButton.setEnabled(validarUsuario(nombre, role, correo, contraseña));
            }
        });
        roleField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                role = roleField.getText();
                registrarseButton.setEnabled(validarUsuario(nombre, role, correo, contraseña));
            }
        });
        emailField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                correo = emailField.getText();
                registrarseButton.setEnabled(validarUsuario(nombre, role, correo, contraseña));
            }
        });
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                contraseña = String.valueOf(passwordField.getPassword());
                registrarseButton.setEnabled(validarUsuario(nombre, role, correo, contraseña));
            }
        });

        registrarseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Usuario user = new Usuario(nombre, role, correo, contraseña);
                Boolean isRegistroOk = userConnections.register(user);
                if(isRegistroOk) {
                    Boolean userFound = userConnections.getUserByID("");
                    if(userFound) {
                        MainScreen mainScreen = new MainScreen();
                        mainScreen.setVisible(true);
                        obtenerProyectos();
                        RegisterScreen.super.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "No se ha encontrado el usuario");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error al rellenar los datos, revisa bien todos los campos");
                }
            }
        });

        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LoginScreen loginScreen = new LoginScreen();
                loginScreen.setVisible(true);
                RegisterScreen.super.dispose();
            }
        });

        loginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static Boolean validarUsuario(String nombre, String rol, String email, String password) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,3}$";

        Pattern regex = Pattern.compile(emailRegex);
        Matcher matcher = regex.matcher(email);

        return nombre.length() > 10 && rol.length() > 0 && password.length() > 6 && matcher.matches();
    }

    public void obtenerProyectos() {
        proyectoConnections.getAllProyectos();
    }
}
