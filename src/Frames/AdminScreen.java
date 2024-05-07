package Frames;

import Clases.Usuario;
import Network.Usuarios.UserConnections;
import SessionManager.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class AdminScreen extends JFrame {

    private final SessionManager sessionManager = SessionManager.getInstance();
    private JPanel adminPanel;
    private JPanel headerPanel;
    private JLabel usuarioLabel;
    private JPanel contenidoPrincipal;
    private JPanel footerPanel;
    private JLabel labelVolver;
    private JLabel labelFichajes;
    private JLabel labelNominas;

    public AdminScreen() {
        setContentPane(adminPanel);
        setTitle("Nóminas y Fichajes DEP");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(680, 460));
        setMaximumSize(new Dimension(680, 460));
        setLocationRelativeTo(null);
        setVisible(true);

        usuarioLabel.setText(sessionManager.getUsuario().getName());
        usuarioLabel.setForeground(Color.WHITE);
        usuarioLabel.setToolTipText("Ver perfil");
        usuarioLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        labelFichajes.setForeground(Color.WHITE);
        labelFichajes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        labelNominas.setForeground(Color.WHITE);
        labelNominas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        labelVolver.setForeground(Color.WHITE);
        labelVolver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        headerPanel.setMinimumSize(new Dimension(640, 40));
        headerPanel.setMaximumSize(new Dimension(640, 40));
        footerPanel.setMinimumSize(new Dimension(640, 40));
        footerPanel.setMaximumSize(new Dimension(640, 40));

        usuarioLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                PerfilScreen perfilScreen = new PerfilScreen();
                perfilScreen.setVisible(true);
                AdminScreen.super.dispose();
            }
        });

        labelVolver.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MainScreen mainScreen = new MainScreen();
                mainScreen.setVisible(true);
                AdminScreen.super.dispose();
            }
        });

        labelVolver.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MainScreen mainScreen = new MainScreen();
                mainScreen.setVisible(true);
                AdminScreen.super.dispose();
            }
        });

        labelNominas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                NominasScreen nominasScreen = new NominasScreen();
                nominasScreen.setVisible(true);
                AdminScreen.super.dispose();
            }
        });
    }

    private void createUIComponents() {
        UserConnections userConnections = new UserConnections();
        userConnections.getAllUsers();

        contenidoPrincipal = new JPanel();
        contenidoPrincipal.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        SessionManager usuariosManager = SessionManager.getInstance();

        ArrayList<Usuario> usuarios = usuariosManager.getUsuariosDisponibles();
        for(Usuario usuario: usuarios) {
            JPanel panelNomina = new JPanel(new FlowLayout());

            JLabel label = new JLabel(usuario.getName());
            JButton button = new JButton("Ver Perfil Usuario");

            button.addActionListener(e -> {
                FichajesUsuarioScreen fichajesUsuario = new FichajesUsuarioScreen(usuario);
                fichajesUsuario.setVisible(true);
                AdminScreen.super.dispose();
            });

            panelNomina.add(label);
            panelNomina.add(button);
            panelNomina.setVisible(true);

            gbc.gridy++;
            contenidoPrincipal.add(panelNomina, gbc);
        }
    }
}
