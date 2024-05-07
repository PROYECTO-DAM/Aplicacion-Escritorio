package Clases;

import javax.swing.*;
import java.util.Date;

public class CalendarLabel extends JLabel {

    private Date date;

    public CalendarLabel(String text, int horizontalAlignment, Date date) {
        super(text, horizontalAlignment);
        this.date = date;
    }

    public Date getDate() {
        return date;
    }
}
