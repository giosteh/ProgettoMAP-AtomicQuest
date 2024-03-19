
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class RESTMeteoThread extends Thread {
    
    private interface RiconoscitoreMeteo {
        boolean isMeteoCorrente(int meteoID);
    }

    private final RiconoscitoreMeteo pioggia = (id) -> ((id >= 200 && id <= 232) || (id >= 300 && id <= 321)
            || (id >= 500 && id <= 531) || (id >= 701 && id <= 781));
    private final RiconoscitoreMeteo cieloSereno = (id) -> (id == 800);
    private final RiconoscitoreMeteo nuvoloso = (id) -> (id >= 801 && id <= 804);

    private final static int INTERVALLO = 30000;

    private final JLabel label;

    
    public RESTMeteoThread(final JLabel label) {
        this.label = label;
    }


    public int getMeteoID() {
        String meteoID = "";
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://api.openweathermap.org/data/2.5/weather")
                                .queryParam("q", "Bari", "IT", "BA")
                                .queryParam("appid", "9227dfb3f4da47f31e7d27e9f3d3048b");

        Response resp = target.request(MediaType.APPLICATION_JSON).get();
        String jsonResponse = resp.readEntity(String.class);

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);

        if (jsonObject.has("weather") && jsonObject.get("weather").isJsonArray()) {
            JsonArray weatherArray = jsonObject.getAsJsonArray("weather");
            for (JsonElement weatherElement : weatherArray) {
                if (weatherElement.isJsonObject()) {
                    JsonObject weatherObject = weatherElement.getAsJsonObject();

                    if (weatherObject.has("id")) {
                        meteoID = weatherObject.get("id").getAsString();
                    }
                }
            }
        }
        return Integer.parseInt(meteoID);
    }

    public void run() {
        while (true) {
            try {
                int meteoID = this.getMeteoID();
                if (cieloSereno.isMeteoCorrente(meteoID)) {
                    this.label.setIcon(new ImageIcon("MeteoSole.jpg"));
                } else if (nuvoloso.isMeteoCorrente(meteoID)) {
                    this.label.setIcon(new ImageIcon("MeteoNuvoloso.jpg"));
                } else if (pioggia.isMeteoCorrente(meteoID)) {
                    this.label.setIcon(new ImageIcon("MeteoPioggia.jpg"));
                }
                Thread.sleep(RESTMeteoThread.INTERVALLO);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
