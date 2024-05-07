package Clases;

public class JsonResponse {

    private int status;
    private Object data;

    public JsonResponse(int status, Usuario user) {
        this.status = status;
        this.data = user;
    }

    public JsonResponse(int status, Fichaje fichaje) {
        this.status = status;
        this.data = fichaje;
    }

    public Object getData() {
        return data;
    }
}
