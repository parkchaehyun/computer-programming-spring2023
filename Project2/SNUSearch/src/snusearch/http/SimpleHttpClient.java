package snusearch.http;

import snusearch.utility.UtilityModule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class SimpleHttpClient extends HttpClient {
    public String sendGetRequest(String query) {
        String jsonResponse = null;
        try {
            String url = "https://www.googleapis.com/customsearch/v1?key=REDACTED&cx=40d46f0f987484424&q=" +
                    query;
            // Create a URL object from the specified URL
            URL requestUrl = new URL(url);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();

            // Set the request method (GET by default)
            connection.setRequestMethod("GET");

            // Send the request and receive the response
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                jsonResponse = response.toString();
            } else {
                System.out.println("HTTP Error: " + responseCode);
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }

}


