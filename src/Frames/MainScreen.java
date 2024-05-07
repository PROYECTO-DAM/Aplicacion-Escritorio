package Frames;

import Clases.CalendarLabel;
import Clases.Proyecto;
import Network.Fichajes.FichajeConnections;
import SessionManager.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainScreen extends JFrame {

    private final FichajeConnections fichajeConnections = new FichajeConnections();

    private final SessionManager sessionManager = SessionManager.getInstance();
    private JPanel mainPanel;
    private JPanel panelCalendario;
    private JLabel labelFichajes;
    private JLabel labelAdmin;
    private JLabel labelNominas;
    private JLabel usuarioLabel;
    private JPanel footerPanel;
    private JPanel headerPanel;
    private Calendar calendar;
    private Calendar calendarioOriginal;

    private String proyecto;

    public MainScreen() {
        setContentPane(mainPanel);
        setTitle("Nóminas y Fichajes DEP");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(680, 460));
        setMaximumSize(new Dimension(680, 460));
        setLocationRelativeTo(null);
        setVisible(true);

        usuarioLabel.setText("Bienvenido/a, " +sessionManager.getUsuario().getName());
        usuarioLabel.setForeground(Color.WHITE);
        usuarioLabel.setToolTipText("Ver perfil");
        usuarioLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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

        usuarioLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                PerfilScreen perfilScreen = new PerfilScreen();
                perfilScreen.setVisible(true);
                MainScreen.super.dispose();
            }
        });

        labelFichajes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                FichajesScreen fichajesScreen = new FichajesScreen();
                fichajesScreen.setVisible(true);
                MainScreen.super.dispose();
            }
        });

        labelAdmin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(sessionManager.getUsuario().getRole().equals("Recursos Humanos")) {
                    AdminScreen adminScreen = new AdminScreen();
                    adminScreen.setVisible(true);
                    MainScreen.super.dispose();
                } else {
                    JOptionPane.showMessageDialog(panelCalendario,"No tienes permiso para acceder a esta parte de la aplicación");
                }
            }
        });

        labelNominas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                NominasScreen nominasScreen = new NominasScreen();
                nominasScreen.setVisible(true);
                MainScreen.super.dispose();
            }
        });
    }

    //Mazacote de generacion de calendario
    private void createUIComponents() {
        panelCalendario = new JPanel(new BorderLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(640, 50));
        JButton prevButton = new JButton("<<");
        JButton nextButton = new JButton(">>");
        JLabel monthLabel = new JLabel("", SwingConstants.CENTER);
        headerPanel.add(prevButton, BorderLayout.WEST);
        headerPanel.add(monthLabel, BorderLayout.CENTER);
        headerPanel.add(nextButton, BorderLayout.EAST);


        this.calendar = Calendar.getInstance();
        this.calendar.set(Calendar.DAY_OF_MONTH, 1);

        JPanel calendarPanel = new JPanel(new GridLayout(0, 7));
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM yyyy");
        monthLabel.setText(monthFormat.format(this.calendar.getTime()));

        this.calendarioOriginal = (Calendar) this.calendar.clone();

        updateCalendar(calendar, monthLabel, calendarPanel);

        prevButton.addActionListener(e -> previousMonth(this.calendarioOriginal, monthLabel, calendarPanel));
        nextButton.addActionListener(e -> nextMonth(this.calendarioOriginal, monthLabel, calendarPanel));

        panelCalendario.add(headerPanel, BorderLayout.NORTH);
        panelCalendario.add(calendarPanel, BorderLayout.CENTER);
    }

    private void previousMonth(Calendar calendar, JLabel monthLabel, JPanel calendarPanel) {
        Calendar previousMonthCalendar = (Calendar) calendar.clone();
        previousMonthCalendar.set(Calendar.DAY_OF_MONTH, 1);
        previousMonthCalendar.add(Calendar.MONTH, -1);
        updateCalendar(previousMonthCalendar, monthLabel, calendarPanel);
    }

    private void nextMonth(Calendar calendar, JLabel monthLabel, JPanel calendarPanel) {
        Calendar nextMonthCalendar = (Calendar) calendar.clone();
        nextMonthCalendar.set(Calendar.DAY_OF_MONTH, 1);
        nextMonthCalendar.add(Calendar.MONTH, 1);
        updateCalendar(nextMonthCalendar, monthLabel, calendarPanel);
    }

    private void updateCalendar(Calendar calendar, JLabel monthLabel, JPanel calendarPanel) {
        Calendar clonedCalendar = (Calendar) calendar.clone();
        clonedCalendar.set(Calendar.DAY_OF_MONTH, 1);
        int maxDay = clonedCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int startDay = clonedCalendar.get(Calendar.DAY_OF_WEEK);
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM yyyy");
        monthLabel.setText(monthFormat.format(clonedCalendar.getTime()));

        calendarPanel.removeAll();


        String[] dayNames = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado","Domingo"};

        for (String dayName : dayNames) {
            JLabel dayLabel = new JLabel(dayName, SwingConstants.CENTER);
            calendarPanel.add(dayLabel);
        }

        //i = 2 por que el calendario empieza en domingo
        for (int i = 2; i < startDay; i++) {
            calendarPanel.add(new JLabel(""));
        }

        Calendar clonedCalendarForLabels = (Calendar) clonedCalendar.clone();

        int diffMonths = clonedCalendar.get(Calendar.MONTH) - calendar.get(Calendar.MONTH);
        if (diffMonths > 0) {
            for (int i = 0; i < diffMonths; i++) {
                clonedCalendarForLabels.add(Calendar.MONTH, -1);
            }
        } else if (diffMonths < 0) {
            for (int i = 0; i < Math.abs(diffMonths); i++) {
                clonedCalendarForLabels.add(Calendar.MONTH, 1);
            }
        }

        for (int day = 1; day <= maxDay; day++) {
            CalendarLabel label = new CalendarLabel(String.valueOf(day), SwingConstants.CENTER, clonedCalendarForLabels.getTime());
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            calendarPanel.add(label);
            label.setForeground((clonedCalendarForLabels.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || clonedCalendarForLabels.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) ? Color.RED : Color.BLACK);
            clonedCalendarForLabels.add(Calendar.DAY_OF_MONTH, 1);
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    JDialog dialog = new JDialog();
                    dialog.setTitle("Fichar");
                    dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    dialog.setLayout(new GridBagLayout());

                    ArrayList<Proyecto> proyectos = sessionManager.getProyectos();

                    DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();

                    comboBoxModel.addElement("Elija un proyecto:");

                    for (Proyecto proyecto : proyectos) {
                        comboBoxModel.addElement(proyecto.getNombre() + " - " + proyecto.getCodigo());
                    }

                    JComboBox<String> comboBox = new JComboBox<>(comboBoxModel);

                    comboBox.addActionListener(e1 -> {
                        String proyectoSeleccionado = (String) comboBox.getSelectedItem();
                        String[] partes = proyectoSeleccionado.split(" - ");
                        proyecto = partes[1];
                    });

                    JLabel proyectoLabel = new JLabel("Proyecto:");
                    JLabel horasLabel = new JLabel("Horas:");
                    JTextField horasField = new JTextField(20);
                    JButton button = new JButton("Fichar");

                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    gbc.anchor = GridBagConstraints.WEST;
                    gbc.insets = new Insets(5, 5, 5, 5);

                    dialog.add(proyectoLabel, gbc);
                    gbc.gridy++;
                    dialog.add(horasLabel, gbc);
                    gbc.gridx++;
                    gbc.gridy = 0;
                    dialog.add(comboBox, gbc);
                    gbc.gridy++;
                    dialog.add(horasField, gbc);
                    gbc.gridy++;
                    dialog.add(button, gbc);

                    String labelText = label.getText();
                    int selectedDay = Integer.parseInt(labelText);
                    Calendar selectedDate = (Calendar) clonedCalendarForLabels.clone();
                    selectedDate.set(Calendar.DAY_OF_MONTH, selectedDay);

                    int year = selectedDate.get(Calendar.YEAR);
                    int month = selectedDate.get(Calendar.MONTH) + 1;
                    int day = selectedDate.get(Calendar.DAY_OF_MONTH);

                    String fecha = String.format("%04d-%02d-%02d", year, month, day);

                    button.addActionListener(e1 -> {
                        String horas = horasField.getText();

                        Boolean isFichajeOk = validarCamposFichaje(proyecto, horas, fecha);
                        if(isFichajeOk) {
                            fichajeConnections.Fichar(proyecto, horas, fecha);
                            dialog.dispose();
                        }
                    });


                    dialog.pack();
                    dialog.setLocationRelativeTo(null);
                    dialog.setVisible(true);
                }
            });
        }
        calendarPanel.revalidate();
        calendarPanel.repaint();

        actualizarCalendarioOriginal(calendar);
    }

    private void actualizarCalendarioOriginal(Calendar calendario) {
        this.calendar = calendario;
        this.calendarioOriginal = (Calendar) calendar.clone();
    }

    public static Boolean validarCamposFichaje(String proyecto, String horas, String fecha) {
        return proyecto.length() > 0 && horas.length() > 0 && fecha.length() == 10;
    }
}

