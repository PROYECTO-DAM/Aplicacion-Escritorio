package Clases;


import java.util.Date;

public class Fichaje {

    private int id;
    private int userId;
    private int proyecto;
    private Date fecha;
    private int horas;

    public Fichaje() {}

    public Fichaje(int id,int userId, int proyecto, Date fecha, int horas) {
        this.id = id;
        this.userId = userId;
        this.proyecto = proyecto;
        this.fecha = fecha;
        this.horas = horas;
    }
    public Fichaje(int userId, int proyecto, Date fecha, int horas) {
        this.userId = userId;
        this.proyecto = proyecto;
        this.fecha = fecha;
        this.horas = horas;
    }

    public int getId() {
        return id;
    }

    public int getuserId() {
        return userId;
    }

    public int getProyecto() {
        return proyecto;
    }

    public Date getFecha() {
        return fecha;
    }

    public int getHoras() {
        return horas;
    }

    public void setuserId(int userId) {
        this.userId = userId;
    }

    public void setProyecto(int proyecto) {
        this.proyecto = proyecto;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    @Override
    public String toString() {
        return "Fichaje{" +
                "id=" + id +
                ", userId=" + userId +
                ", proyecto=" + proyecto +
                ", fecha=" + fecha +
                ", horas=" + horas +
                '}';
    }
}
