package Network.Fichajes;

import Clases.Fichaje;
import Clases.JsonResponse;
import Clases.Usuario;
import SessionManager.SessionManager;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class FichajeConnections {

    String api = "http://localhost:8081";
    SessionManager sessionManager = SessionManager.getInstance();

    public void getFichajesByUsuario(String userId) {
        String token = sessionManager.getToken();
        ArrayList<Fichaje> fichajes = new ArrayList<>();
        try {
            URL url = new URL(api + "/api/v1/fichajes/getFichajesByUserId");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("authorization", "Bearer " + token);
            con.setRequestProperty("_id", userId);
            con.connect();

            int responseCode = con.getResponseCode();
            if(responseCode == 200 || responseCode == 201) {
                StringBuilder res = new StringBuilder();
                Scanner sc = new Scanner(con.getInputStream());

                while(sc.hasNext()) {
                    res.append(sc.nextLine());
                }
                sc.close();
                con.disconnect();

                Gson gson = new Gson();
                JsonResponse response = gson.fromJson(res.toString(), JsonResponse.class);
                Object data = response.getData();

                if(data instanceof ArrayList) {
                    ArrayList<?> dataList = (ArrayList<?>) data;

                    for (Object obj : dataList) {
                        if (obj instanceof LinkedTreeMap) {
                            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

                            LinkedTreeMap<?, ?> map = (LinkedTreeMap<?, ?>) obj;
                            int id = ((Double) map.get("ID")).intValue();
                            int trabajador = ((Double) map.get("Trabajador")).intValue();
                            String fechaStr = (String) map.get("Fecha");
                            Date fecha = inputFormat.parse(fechaStr);
                            int horas = ((Double) map.get("Horas")).intValue();
                            int proyecto = ((Double) map.get("Proyecto")).intValue();

                            Fichaje fichaje = new Fichaje(id, trabajador, proyecto, fecha, horas);
                            fichajes.add(fichaje);
                        }
                    }
                }
                sessionManager.setFichajes(fichajes);

            } else {
                throw new RuntimeException("Error al obtener los fichajes del usuario");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    public void getFichajeById(String id) {
        String token = sessionManager.getToken();
        try {
            URL url = new URL(api + "/api/v1/fichajes/getFichajeById");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("authorization", "Bearer " + token);
            con.setRequestProperty("id", id);
            con.connect();

            int responseCode = con.getResponseCode();
            if(responseCode == 200 || responseCode == 201) {
                StringBuilder res = new StringBuilder();
                Scanner sc = new Scanner(con.getInputStream());

                while(sc.hasNext()) {
                    res.append(sc.nextLine());
                }
                sc.close();
                con.disconnect();

                Gson gson = new Gson();
                JsonResponse response = gson.fromJson(res.toString(), JsonResponse.class);
                Fichaje fichaje = gson.fromJson(gson.toJson(response.getData()), Fichaje.class);

                sessionManager.setFichaje(fichaje);
            } else {
                throw new RuntimeException("Error al hacer obtener el fichaje");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Fichar(String proyecto, String horas, String fecha) {
        Usuario user = sessionManager.getUsuario();
        String token = sessionManager.getToken();

        try {
            URL url = new URL(api + "/api/v1/fichajes/fichar");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("authorization", "Bearer " + token);
            con.setDoOutput(true);
            con.connect();

            String body = "{" +
                    "\"userId\": \""+ user.getId() +"\", " +
                    "\"fecha\": \""+ fecha +"\", " +
                    "\"horas\": \""+ horas +"\", " +
                    "\"proyecto\": \""+ proyecto +"\"" +
                    "}";

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = body.getBytes("utf-8");
                os.write(input, 0, input.length);
            }


            int responseCode = con.getResponseCode();
            if(responseCode == 200 || responseCode == 201) {
                StringBuilder res = new StringBuilder();
                Scanner sc = new Scanner(con.getInputStream());

                while(sc.hasNext()) {
                    res.append(sc.nextLine());
                }
                sc.close();
                con.disconnect();

            } else {
                throw new RuntimeException("Error al fichar");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ActualizarFichaje(String id,String proyecto, String horas, String fecha) {
        Usuario user = sessionManager.getUsuario();
        String token = sessionManager.getToken();

        try {
            URL url = new URL(api + "/api/v1/fichajes/updateFichaje");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("authorization", "Bearer " + token);
            con.setDoOutput(true);
            con.connect();

            String body = "{" +
                    "\"_id\": \""+ id +"\", " +
                    "\"userId\": \""+ user.getId() +"\", " +
                    "\"fecha\": \""+ fecha +"\", " +
                    "\"horas\": \""+ horas +"\", " +
                    "\"proyecto\": \""+ proyecto +"\"" +
                    "}";

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = body.getBytes("utf-8");
                os.write(input, 0, input.length);
            }


            int responseCode = con.getResponseCode();
            if(responseCode == 200 || responseCode == 201) {
                StringBuilder res = new StringBuilder();
                Scanner sc = new Scanner(con.getInputStream());

                while(sc.hasNext()) {
                    res.append(sc.nextLine());
                }
                sc.close();
                con.disconnect();

            } else {
                throw new RuntimeException("Error al actualizar el fichaje");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String BorrarFichaje(String id) {
        String token = sessionManager.getToken();

        try {
            URL url = new URL(api + "/api/v1/fichajes/deleteFichaje");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("DELETE");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("authorization", "Bearer " + token);
            con.setDoOutput(true);
            con.connect();

            String body = "{\"_id\": \""+ id +"\"}";

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = body.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            int responseCode = con.getResponseCode();
            if(responseCode == 200 ||responseCode == 201) {
                return "Fichaje Borrado Correctamente";
            } else {
                return "No se ha podido borrar el fichaje";
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return "No se ha podido borrar el fichaje";
    }
}
