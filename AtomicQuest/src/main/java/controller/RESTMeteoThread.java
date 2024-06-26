
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

/**
 * Classe che si occupa di gestire il meteo tramite REST.
 */
public class RESTMeteoThread extends Thread {

    /**
     * Interfaccia funzionale per riconoscere il meteo.
     */
    private interface RiconoscitoreMeteo {
        boolean isMeteoCorrente(int meteoID);
    }

    private final RiconoscitoreMeteo pioggia = (id) -> ((id >= 200 && id <= 232) || (id >= 300 && id <= 321)
            || (id >= 500 && id <= 531) || (id >= 701 && id <= 781));
    private final RiconoscitoreMeteo cieloSereno = (id) -> (id == 800 || id == 801);
    private final RiconoscitoreMeteo nuvoloso = (id) -> (id >= 802 && id <= 804);

    private final static int INTERVALLO = 180000;

    private final JLabel label;

    /**
     * Costruttore di default.
     */
    public RESTMeteoThread(final JLabel label) {
        this.label = label;
    }

    /**
     * Metodo che restituisce l'id del meteo.
     * @return il codice del meteo
     */
    private int getMeteoID() {
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

    /**
     * Metodo che esegue il thread.
     */
    public void run() {
        while (true) {
            try {
                int meteoID = this.getMeteoID();
                if (cieloSereno.isMeteoCorrente(meteoID)) {
                    this.label.setIcon(new ImageIcon("./risorse/img/MeteoSole.jpg"));
                } else if (nuvoloso.isMeteoCorrente(meteoID)) {
                    this.label.setIcon(new ImageIcon("./risorse/img/MeteoNuvoloso.jpg"));
                } else if (pioggia.isMeteoCorrente(meteoID)) {
                    this.label.setIcon(new ImageIcon("./risorse/img/MeteoPioggia.jpg"));
                }
                Thread.sleep(RESTMeteoThread.INTERVALLO);
            } catch (InterruptedException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    /**
     * Metodo che visualizza il meteo.
     */
    public void visualizzaMeteo() {
        this.start();
    }
}
