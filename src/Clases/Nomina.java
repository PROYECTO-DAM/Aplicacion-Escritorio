package Clases;

import com.google.gson.annotations.SerializedName;

public class Nomina {

    private int Empleado;

    @SerializedName("Mes")
    private String mes;

    @SerializedName("Pago")
    private float pago;

    @SerializedName("Horas")
    private int horas;
    @SerializedName("Año")
    private int ano;

    public Nomina(int empleado, String mes, float pago, int horas, int ano) {
        Empleado = empleado;
        this.mes = mes;
        this.pago = pago;
        this.horas = horas;
        this.ano = ano;
    }

    public int getEmpleado() {
        return Empleado;
    }
    public String getMes() {
        switch (mes) {
            case "1.0": return "Enero";
            case "2.0": return "Febrero";
            case "3.0": return  "Marzo";
            case "4.0": return  "Abril";
            case "5.0": return  "Mayo";
            case "6.0": return "Junio";
            case "7.0": return "Julio";
            case "8.0": return  "Agosto";
            case "9.0": return  "Septiembre";
            case "10.0": return "Octubre";
            case "11.0": return "Noviembre";
            case "12.0": return "Diciembre";
        }
        return null;
    }

    public float getPago() {
        return pago;
    }

    public int getHoras() {
        return horas;
    }
    public int getAno() {
        return ano;
    }

    @Override
    public String toString() {
        return "Nomina(" +
                "Empleado=" + Empleado +
                ", mes='" + mes + '\'' +
                ", pago=" + pago +
                ", horas=" + horas +
                ", ano=" + ano +
                ')';
    }
}
