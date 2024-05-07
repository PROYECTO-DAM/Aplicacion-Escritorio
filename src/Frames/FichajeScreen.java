package Frames;

import Clases.Fichaje;
import Clases.Proyecto;
import Network.Fichajes.FichajeConnections;
import SessionManager.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FichajeScreen extends JFrame {

    private JPanel fichajePanel;
    private JPanel headerPanel;
    private JLabel usuarioLabel;
    private JPanel contenidoPrincipal;
    private JPanel footerPanel;
    private JButton botonVolver;
    private JButton modificarBoton;
    private JButton borrarBoton;
    private JLabel proyectoLabel;
    private JLabel proyectoValue;
    private JLabel horasLabel;
    private JLabel horasValue;
    private JComboBox<String> comboBox1;
    private JTextField horasField;

    private String proyecto = "";

    private String horas = "";

    public FichajeScreen(Fichaje fichaje) {
        setContentPane(fichajePanel);
        setTitle("Nóminas y Fichajes DEP");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(680, 460));
        setMaximumSize(new Dimension(680, 460));
        setLocationRelativeTo(null);
        setVisible(true);

        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        modificarBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FichajeConnections fichajeConnections = new FichajeConnections();
                fichajeConnections.ActualizarFichaje(String.valueOf(fichaje.getId()), proyecto, horas, outputFormat.format(fichaje.getFecha()));
                MainScreen mainScreen = new MainScreen();
                mainScreen.setVisible(true);
                FichajeScreen.super.dispose();
            }
        });

        borrarBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FichajeConnections fichajeConnections = new FichajeConnections();
                fichajeConnections.BorrarFichaje(String.valueOf(fichaje.getId()));
                MainScreen mainScreen = new MainScreen();
                mainScreen.setVisible(true);
                FichajeScreen.super.dispose();
            }
        });

        botonVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainScreen mainScreen = new MainScreen();
                mainScreen.setVisible(true);
                FichajeScreen.super.dispose();
            }
        });

        SessionManager sessionManager = SessionManager.getInstance();
        ArrayList<Proyecto> proyectos = sessionManager.getProyectos();



        for(Proyecto proyecto1: proyectos) {
            if(proyecto1.getCodigo() == fichaje.getProyecto()) {
                proyecto = proyecto1.getNombre();
            }
        }

        proyectoValue.setText(proyecto);
        horasValue.setText(String.valueOf(fichaje.getHoras()));


        for(Proyecto proy: proyectos) comboBox1.addItem(proy.getNombre() + " - " + proy.getCodigo());

        comboBox1.addActionListener(e1 -> {
            String proyectoSeleccionado = (String) comboBox1.getSelectedItem();
            String[] partes = proyectoSeleccionado.split(" - ");
            proyecto = partes[1];
        });

        horasField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                horas = horasField.getText();
            }
        });
    }
}
