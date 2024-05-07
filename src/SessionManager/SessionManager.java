package SessionManager;

import Clases.Fichaje;
import Clases.Nomina;
import Clases.Proyecto;
import Clases.Usuario;

import java.util.ArrayList;

public class SessionManager {
    private static SessionManager instance;

    private String password;
    private String token;

    private Usuario usuario;
    private ArrayList<Usuario> usuariosDisponibles;

    private Fichaje fichaje;
    private ArrayList<Fichaje> fichajes;

    private Nomina nomina;

    private ArrayList<Nomina> nominas;

    private ArrayList<Proyecto> proyectos;

    private SessionManager() {}

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public String getToken() {
        return token;
    }

    public synchronized void setToken(String token) {
        this.token = token;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public synchronized void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ArrayList<Usuario> getUsuariosDisponibles() {
        return this.usuariosDisponibles;
    }

    public void setUsuariosDisponibles(ArrayList<Usuario> usuariosDisponibles) {
        this.usuariosDisponibles = usuariosDisponibles;
    }

    public Fichaje getFichaje() {
        return fichaje;
    }

    public void setFichaje(Fichaje fichaje) {
        this.fichaje = fichaje;
    }

    public ArrayList<Fichaje> getFichajes() {
        return fichajes;
    }

    public void setFichajes(ArrayList<Fichaje> fichajes) {
        this.fichajes = fichajes;
    }

    public Nomina getNomina() {
        return nomina;
    }

    public void setNomina(Nomina nomina) {
        this.nomina = nomina;
    }

    public ArrayList<Nomina> getNominas() {
        return nominas;
    }

    public void setNominas(ArrayList<Nomina> nominas) {
        this.nominas = nominas;
    }

    public ArrayList<Proyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(ArrayList<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void cleanUsuario() {
        this.usuario = new Usuario();
    }
}