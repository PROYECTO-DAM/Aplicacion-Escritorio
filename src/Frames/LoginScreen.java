package Frames;

import Network.Proyectos.ProyectoConnections;
import Network.Usuarios.UserConnections;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginScreen extends JFrame {
    private JPanel LoginPanel;
    private JTextField correoField;
    private JPasswordField passwordField;
    private JLabel correoLabel;
    private JLabel contraseñaLabel;
    private JButton loginButton;
    private JLabel registerLabel;
    private String correo = "";
    private String contraseña = "";
    private final UserConnections userConnections = new UserConnections();

    private final ProyectoConnections proyectoConnections = new ProyectoConnections();
    public LoginScreen() {

        setContentPane(LoginPanel);
        setTitle("Nóminas y Fichajes DEP");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(640, 360));
        setMaximumSize(new Dimension(640, 360));
        setLocationRelativeTo(null);
        setVisible(true);

        loginButton.setEnabled(validarUsuario(correo, contraseña));

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Boolean isLoginOk = userConnections.login("", correo, contraseña, "");
                if(isLoginOk) {
                    Boolean userFound = userConnections.getUserByID("");
                    if(userFound) {
                        MainScreen mainScreen = new MainScreen();
                        mainScreen.setVisible(true);
                        obtenerProyectos();
                        LoginScreen.super.dispose();
                    }
                }
            }
        });
        correoField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                correo = correoField.getText();
                loginButton.setEnabled(validarUsuario(correo, contraseña));
            }
        });
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                contraseña = String.valueOf(passwordField.getPassword());
                loginButton.setEnabled(validarUsuario(correo, contraseña));
            }
        });

        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RegisterScreen registerScreen = new RegisterScreen();
                registerScreen.setVisible(true);
                LoginScreen.super.dispose();
            }
        });

        registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static void main(String[] args) {
        new LoginScreen();
    }

    public static Boolean validarUsuario(String email, String password) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,3}$";

        Pattern regex = Pattern.compile(emailRegex);
        Matcher matcher = regex.matcher(email);

        return password.length() > 6 && matcher.matches();
    }

    public void obtenerProyectos() {
        proyectoConnections.getAllProyectos();
    }
}
