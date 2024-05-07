package Network.Proyectos;

import Clases.JsonResponse;
import Clases.Proyecto;
import SessionManager.SessionManager;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ProyectoConnections {

    String api = "http://localhost:8081";
    SessionManager sessionManager = SessionManager.getInstance();

    public void getAllProyectos() {
        String token = sessionManager.getToken();

        try {
            URL url = new URL(api + "/api/v1/proyecto/getAllProyectos");
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
                Proyecto[] proyectos = gson.fromJson(gson.toJson(response.getData()), Proyecto[].class);

                sessionManager.setProyectos(new ArrayList<Proyecto>(Arrays.asList(proyectos)));
            } else {
                throw new RuntimeException("Error al obtener los proyectos");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
