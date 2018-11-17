import java.net.http.HttpClient; //https://openjdk.java.net/groups/net/httpclient/intro.html
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.*;
import java.util.*;
import java.net.*;

public class OpenWeatherMap
{

    public static void main(String[] args) throws Exception
    {
        System.out.println("Running...");
        String location = "Greeley";
        String response = current(location);
    }

    public static String current(String location) throws Exception
    {
        String apiKey = "&APPID=238800e84194ea5fd444c1a1d82b9fe8";
        String apiBaseUrl = "https://api.openweathermap.org/data/2.5/";

        String apiEndpointUrl = "/weather";
        String requestParams = "?q=" + location;
        String requestUrlFull = apiBaseUrl + apiEndpointUrl + requestParams + apiKey;
        System.out.println(requestUrlFull);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(requestUrlFull))
            .header("Content-Type", "application/json")
            .build();

        HttpResponse<String> response =
            client.send(request, BodyHandlers.ofString());
        System.out.println(response.statusCode());
        System.out.println(response.body());

        return response.body();
        
    }
}