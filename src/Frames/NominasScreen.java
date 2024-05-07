package Frames;

import Clases.Nomina;
import Network.Nominas.NominaConnections;
import SessionManager.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class NominasScreen extends JFrame {

    private final SessionManager sessionManager = SessionManager.getInstance();
    private JPanel nominasPanel;
    private JPanel headerPanel;
    private JLabel usuarioLabel;
    private JPanel contenidoPrincipal;
    private JPanel footerPanel;
    private JLabel labelAdmin;
    private JLabel labelFichajes;
    private JLabel labelVolver;

    public NominasScreen() {
        setContentPane(nominasPanel);
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
        labelFichajes.setForeground(Color.WHITE);
        labelFichajes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        labelAdmin.setForeground(Color.WHITE);
        labelAdmin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        headerPanel.setMinimumSize(new Dimension(640, 40));
        headerPanel.setMaximumSize(new Dimension(640, 40));
        footerPanel.setMinimumSize(new Dimension(640, 40));
        footerPanel.setMaximumSize(new Dimension(640, 40));

        usuarioLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                PerfilScreen perfilScreen = new PerfilScreen();
                perfilScreen.setVisible(true);
                NominasScreen.super.dispose();
            }
        });

        labelVolver.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MainScreen mainScreen = new MainScreen();
                mainScreen.setVisible(true);
                NominasScreen.super.dispose();
            }
        });

        labelAdmin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(sessionManager.getUsuario().getRole().equals("Recursos Humanos")) {
                    AdminScreen adminScreen = new AdminScreen();
                    adminScreen.setVisible(true);
                    NominasScreen.super.dispose();
                } else {
                    JOptionPane.showMessageDialog(nominasPanel,"No tienes permiso para acceder a esta parte de la aplicación");
                }
            }
        });

        labelFichajes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                FichajesScreen fichajesScreen = new FichajesScreen();
                fichajesScreen.setVisible(true);
                NominasScreen.super.dispose();
            }
        });
    }

    private void createUIComponents() {
        NominaConnections nominaConnections = new NominaConnections();
        nominaConnections.getNominasByUser("");
        contenidoPrincipal = new JPanel();
        contenidoPrincipal.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        SessionManager nominasManager = SessionManager.getInstance();

        ArrayList<Nomina> nominas = nominasManager.getNominas();

        for(Nomina nomina: nominas) {
            JPanel panelNomina = new JPanel(new FlowLayout());

            JLabel label = new JLabel("Nómina del mes: " +nomina.getMes() + "-" + nomina.getAno());
            JButton button = new JButton("Ver nómina");

            button.addActionListener(e -> {
                JDialog dialog = new JDialog();
                dialog.setTitle("Nómina de " +nomina.getMes() + "-" + nomina.getAno());
                dialog.setLayout(new GridBagLayout());
                dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                dialog.setSize(400,300);
                GridBagConstraints constraints  = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.anchor = GridBagConstraints.WEST;
                gbc.insets = new Insets(10, 5, 10, 5);

                Font boldFont = new Font(Font.DIALOG, Font.BOLD, 14);

                JLabel empleadoLabel = new JLabel("Empleado:");
                empleadoLabel.setFont(boldFont);
                constraints.gridx = 0;
                constraints.gridy = 0;
                dialog.add(empleadoLabel, constraints);

                JLabel empleadoValue = new JLabel(sessionManager.getUsuario().getName());
                empleadoValue.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
                constraints.gridx = 1;
                constraints.gridy = 0;
                dialog.add(empleadoValue, constraints);

                JLabel mesLabel = new JLabel("Mes:");
                mesLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                mesLabel.setFont(boldFont);
                constraints.gridx = 0;
                constraints.gridy = 1;
                dialog.add(mesLabel, constraints);

                JLabel mesValue = new JLabel(nomina.getMes());
                mesValue.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
                constraints.gridx = 1;
                constraints.gridy = 1;
                dialog.add(mesValue, constraints);

                JLabel pagoLabel = new JLabel("Horas fichadas este mes:");
                pagoLabel.setFont(boldFont);
                constraints.gridx = 0;
                constraints.gridy = 2;
                dialog.add(pagoLabel, constraints);

                JLabel pagoValue = new JLabel(String.valueOf(nomina.getHoras()));
                pagoValue.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
                constraints.gridx = 1;
                constraints.gridy = 2;
                dialog.add(pagoValue, constraints);

                JLabel horasLabel = new JLabel("Año:");
                horasLabel.setFont(boldFont);
                constraints.gridx = 0;
                constraints.gridy = 3;
                dialog.add(horasLabel, constraints);

                JLabel horasValue = new JLabel(String.valueOf(nomina.getAno()));
                horasValue.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
                constraints.gridx = 1;
                constraints.gridy = 3;
                dialog.add(horasValue, constraints);

                JLabel añoLabel = new JLabel("Dinero pagado:");
                añoLabel.setFont(boldFont);
                constraints.gridx = 0;
                constraints.gridy = 4;
                dialog.add(añoLabel, constraints);

                JLabel añoValue = new JLabel(nomina.getPago() + "€");
                añoValue.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
                constraints.gridx = 1;
                constraints.gridy = 4;
                dialog.add(añoValue, constraints);

                JButton botonOK = new JButton("OK");

                botonOK.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dialog.dispose();
                    }
                });

                constraints.gridx = 0;
                constraints.gridy = 5;
                constraints.gridwidth = 2;
                constraints.anchor = GridBagConstraints.CENTER;
                dialog.add(botonOK, constraints);

                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
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
