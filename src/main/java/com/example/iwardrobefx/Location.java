package com.example.iwardrobefx;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Location {

    private static final String API_URL = "http://ip-api.com/json/";

    public static String getCity() {
        String city = "Ukendt";

        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("DEBUG : Fejl ved vores api leverandør : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            StringBuilder jsonOutput = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                jsonOutput.append(output);
            }

            conn.disconnect();

            // Finder City i vores JSON String.
            String json = jsonOutput.toString();
            int cityIndex = json.indexOf("\"city\":\"") + 8;
            int cityEndIndex = json.indexOf("\"", cityIndex);
            city = json.substring(cityIndex, cityEndIndex);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return city;
    }
}
