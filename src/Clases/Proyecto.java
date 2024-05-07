package Clases;

import com.google.gson.annotations.SerializedName;

public class Proyecto {

    @SerializedName("Codigo")
    private int codigo;
    @SerializedName("Nombre")
    private String nombre;

    public int getCodigo() { return codigo; }

    public String getNombre() {
        return nombre;
    }
}
