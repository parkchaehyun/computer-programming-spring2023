package snusearch.search;

import snusearch.http.SimpleHttpClient;
import snusearch.utility.UtilityModule;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
public class SearchModule {
    public static String performSearch(String query) {
        SimpleHttpClient httpClient = new SimpleHttpClient();
        String jsonResponse = httpClient.sendGetRequest(query);

        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(jsonResponse);

        String searchResultsString = "";

        SearchResult[] searchResults = null;

        if (jsonElement != null) {
            JsonObject requestObject = jsonElement.getAsJsonObject()
                    .getAsJsonObject("queries")
                    .getAsJsonArray("request")
                    .get(0)
                    .getAsJsonObject();

            String searchTerms = requestObject.get("searchTerms").getAsString();
            searchTerms = UtilityModule.decodeURLToQuery(searchTerms);
            String totalResults = requestObject.get("totalResults").getAsString();

            JsonArray itemsArray = jsonElement.getAsJsonObject().getAsJsonArray("items");

            // add only the first three items to a new array
            JsonArray firstThreeItemsArray = new JsonArray();
            for (int i = 0; i < 3; i++) {
                if (i < itemsArray.size()) {
                    firstThreeItemsArray.add(itemsArray.get(i));
                }
            }

            // convert the first three items to a SearchResult array
            Gson gson = new Gson();
            searchResults = gson.fromJson(firstThreeItemsArray, SearchResult[].class);

            // save the searchTerms, totalResults, and searchResults to a single string
            searchResultsString = "Search Terms: " + searchTerms + "\nTotal Results: " + totalResults + "\n\n";
            for (int i = 0; i < searchResults.length; i++) {
                searchResultsString += Integer.toString(i + 1) + ".\n" + searchResults[i].toString() + "\n\n";
            }
        }

        return searchResultsString;
    }
}
