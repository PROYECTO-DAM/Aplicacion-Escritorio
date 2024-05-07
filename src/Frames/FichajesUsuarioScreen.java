package Frames;

import Clases.Fichaje;
import Clases.Usuario;
import Network.Fichajes.FichajeConnections;
import SessionManager.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FichajesUsuarioScreen extends JFrame {

    private JPanel fichajesPanel;
    private JPanel headerPanel;
    private JLabel usuarioLabel;
    private JPanel contenidoPrincipal;
    private JPanel footerPanel;
    private JLabel labelVolver;
    private JLabel labelNominas;

    public FichajesUsuarioScreen(Usuario user) {
        SessionManager sessionManager = SessionManager.getInstance();
        setContentPane(fichajesPanel);
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
        labelVolver.setForeground(Color.WHITE);
        labelVolver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        labelNominas.setForeground(Color.WHITE);
        labelNominas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        headerPanel.setMinimumSize(new Dimension(640, 40));
        headerPanel.setMaximumSize(new Dimension(640, 40));
        footerPanel.setMinimumSize(new Dimension(640, 40));
        footerPanel.setMaximumSize(new Dimension(640, 40));

        usuarioLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                PerfilScreen perfilScreen = new PerfilScreen();
                perfilScreen.setVisible(true);
                FichajesUsuarioScreen.super.dispose();
            }
        });

        labelVolver.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MainScreen mainScreen = new MainScreen();
                mainScreen.setVisible(true);
                FichajesUsuarioScreen.super.dispose();
            }
        });


        labelNominas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                NominasScreen nominasScreen = new NominasScreen();
                nominasScreen.setVisible(true);
                FichajesUsuarioScreen.super.dispose();
            }
        });
    }

    private void createUIComponents() {
        FichajeConnections fichajeConnections = new FichajeConnections();
        fichajeConnections.getFichajesByUsuario("");
        contenidoPrincipal = new JPanel();
        contenidoPrincipal.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        SessionManager fichajesManager = SessionManager.getInstance();

        ArrayList<Fichaje> fichajes = fichajesManager.getFichajes();

        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (Fichaje fichaje: fichajes) {
            JPanel panelNomina = new JPanel(new FlowLayout());

            JLabel label = new JLabel("Fecha: " + outputFormat.format(fichaje.getFecha()));
            JButton button = new JButton("Ver Fichaje");

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    FichajeScreen fichajeScreen = new FichajeScreen(fichaje);
                    fichajeScreen.setVisible(true);
                    FichajesUsuarioScreen.super.dispose();
                }
            });

            panelNomina.add(label);
            panelNomina.add(button);
            panelNomina.setVisible(true);

            gbc.gridy++;
            contenidoPrincipal.add(panelNomina, gbc);

        }
        JScrollPane scrollPane = new JScrollPane(contenidoPrincipal);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    }
}
