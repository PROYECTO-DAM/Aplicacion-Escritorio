package Network.Nominas;

import Clases.JsonResponse;
import Clases.Nomina;
import SessionManager.SessionManager;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class NominaConnections {
    String api = "http://localhost:8081";
    SessionManager sessionManager = SessionManager.getInstance();

    public void getNominasByUser(String userId) {
        String token = sessionManager.getToken();

        try {
            URL url = new URL(api + "/api/v1/nominas/getNominasByUserId");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("authorization", "Bearer " + token);
            con.setRequestProperty("id", userId);
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
                Nomina[] nominas = gson.fromJson(gson.toJson(response.getData()), Nomina[].class);
                sessionManager.setNominas(new ArrayList<>(Arrays.asList(nominas)));
            } else {
                throw new RuntimeException("Error al obtener las nominas del usuario");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void getNominaById(String id) {
        String token = sessionManager.getToken();

        try {
            URL url = new URL(api + "/api/v1/nominas/getNominaById");
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
                Nomina nomina = gson.fromJson(gson.toJson(response.getData()), Nomina.class);

                sessionManager.setNominas(new ArrayList<>(Arrays.asList(nomina)));
            } else {
                throw new RuntimeException("Error al obtener la nomina");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
