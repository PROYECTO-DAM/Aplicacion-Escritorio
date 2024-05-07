package Network.Usuarios;

import Clases.JsonResponse;
import Clases.Usuario;
import SessionManager.SessionManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class UserConnections {

    String api = "http://localhost:8081";
    SessionManager sessionManager = SessionManager.getInstance();

    public void getAllUsers() {
        Usuario[] users = null;
        String token = sessionManager.getToken();
        try {
            URL url = new URL(api + "/api/v1/users/all");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("authorization", "Bearer " + token);
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
                users = gson.fromJson(gson.toJson(response.getData()), Usuario[].class);

                sessionManager.setUsuariosDisponibles(new ArrayList<>(Arrays.asList(users)));

            } else {
                throw new RuntimeException("Error al hacer obtener el usuario");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public Boolean getUserByID(String id) {
        String token = sessionManager.getToken();

        try {
            URL url = new URL(api + "/api/v1/users/getUser");
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
                Usuario user = gson.fromJson(gson.toJson(response.getData()), Usuario.class);

                if(!user.getName().isEmpty() && !user.getName().isBlank()) {
                    sessionManager.cleanUsuario();
                    sessionManager.setUsuario(user);
                    return true;
                }
            } else {
                throw new RuntimeException("Error al hacer obtener el usuario");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return false;
    }

    public Boolean login(Object id,String email, String password, String name) {
        try {
            URL url = new URL(api + "/api/v1/users/signIn");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("authorization", "Bearer "+ "noToken");
            con.setDoOutput(true);
            con.connect();

            String body = "{" +
                    "\"_id\": \""+ id +"\", " +
                    "\"password\": \""+ password +"\", " +
                    "\"email\": \""+ email +"\", " +
                    "\"name\": \""+ name +"\"" +
                    "}";

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = body.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = con.getResponseCode();
            if(responseCode == 200 ||responseCode == 201) {
                StringBuilder res = new StringBuilder();
                Scanner sc = new Scanner(con.getInputStream());

                while (sc.hasNext()) {
                    res.append(sc.nextLine());
                }
                sc.close();
                con.disconnect();

                JsonObject jsonObject = JsonParser.parseString(res.toString()).getAsJsonObject();

                String token = jsonObject.get("data").getAsString();
                sessionManager.setToken(token);
                sessionManager.setPassword(password);

                if(!token.isBlank() && !token.isEmpty()) {
                    return true;
                }
            }

        } catch (IOException e) {
            System.out.println(e);
        }
        return false;
    }

    public Boolean register(Usuario user) {
        Gson gson = new Gson();
        String usuarioJson = gson.toJson(user);

        try {
            URL url = new URL(api + "/api/v1/users/signUp");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            con.connect();

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = usuarioJson.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            int responseCode = con.getResponseCode();
            if(responseCode == 200 ||responseCode == 201) {
                StringBuilder res = new StringBuilder();
                Scanner sc = new Scanner(con.getInputStream());

                while(sc.hasNext()) {
                    res.append(sc.nextLine());
                }
                sc.close();
                con.disconnect();

                JSONObject jsonResponse = new JSONObject(res.toString());

                String token = jsonResponse.getString("data");
                sessionManager.setToken(token);
                sessionManager.setPassword(user.getPassword());
                if(!token.isEmpty() && !token.isBlank()) {
                    return true;
                }

            } else {
                throw new RuntimeException("Error al registrarse");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return false;
    }

    public Boolean updateUser(Object id,String name,String role, String email, String password) {
        String token = sessionManager.getToken();
        try {
            URL url = new URL(api + "/api/v1/users/updateUser");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("authorization", "Bearer " + token);
            con.setDoOutput(true);
            con.connect();

            String body = "{" +
                    "\"_id\": \""+ id +"\", " +
                    "\"fullname\": \""+ name +"\", " +
                    "\"role\": \""+ role +"\", " +
                    "\"email\": \""+ email +"\"," +
                    "\"password\": \""+ password +"\"" +
                    "}";

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = body.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            int responseCode = con.getResponseCode();
            if(responseCode == 200 ||responseCode == 201) {
                StringBuilder res = new StringBuilder();
                Scanner sc = new Scanner(con.getInputStream());

                while (sc.hasNext()) {
                    res.append(sc.nextLine());
                }
                sc.close();
                con.disconnect();

                Gson gson = new Gson();
                JsonResponse response = gson.fromJson(res.toString(), JsonResponse.class);
                Usuario user = gson.fromJson(gson.toJson(response.getData()), Usuario.class);

                System.out.println(user);

                if(user != null) {
                    sessionManager.cleanUsuario();
                    sessionManager.setUsuario(user);
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return false;
    }

    public void deleteUser(Object id) {
        String token = sessionManager.getToken();
        try {
            URL url = new URL(api + "/api/v1/users/deleteUser");
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
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
