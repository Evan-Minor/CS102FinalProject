import java.net.*;
import java.net.http.HttpClient; //https://openjdk.java.net/groups/net/httpclient/intro.html
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.*;
import java.net.URLEncoder;

public class OpenWeatherMap
{
    public static String getWeather(String location, int option) throws Exception
    {
        String apiKey = "&APPID=238800e84194ea5fd444c1a1d82b9fe8";
        String apiBaseUrl = "https://api.openweathermap.org/data/2.5";
        
        // Assign endpoint based on choice
        String apiEndpointUrl = null;
        if(option == 1)
        {
            apiEndpointUrl = "/weather";
        }
        else if(option == 2)
        {
            apiEndpointUrl = "/forecast";
        }

        // Encode query for proper URL format
        String query = "?q=" + URLEncoder.encode((location), "UTF-8"); 
        String requestUrlFull = apiBaseUrl + apiEndpointUrl + query + apiKey;

        // Create HTTP client and build request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(requestUrlFull))
            .header("Content-Type", "application/json")
            .build();

        // Attempt http request
        String weatherString = "";
        try
        {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            //System.out.println(response.statusCode());
            weatherString = response.body();
        }
        catch(Exception error){
            System.out.println("Request failed. Please try again later.");
        }

        return weatherString;
    }
}