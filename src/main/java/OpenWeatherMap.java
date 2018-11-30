/*  CS102 Final Project - WeatherApp
*   
*   PROGRAMMERS: Evan Minor and Joshua Eli Swick
*   CLASS: CS102
*   ASSIGNMENT: Final Project
*   INSTRUCTOR: Chris Harris
*   SUBMISSION DATE: ???
*
*   DESCRIPTION:
*   Basic weather application that leverages OpenWeatherMap API: https://openweathermap.org/api
*
*   COPYRIGHT:
*   This program is copyright (c) 2018 Evan Minor, Joshua Swick and Chris Harris.
*   This is original work, without use of outside sources.
*/

import java.util.*;

// HTTP Dependencies
import java.net.HttpURLConnection;
import java.net.URLEncoder;

// JSON Dependencies
import com.google.code.gson;

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
        URLConnection connection = new URL(requestUrlFull).openConnection();
        connection.setRequestProperty("Accept-Charset", "UTF-8");

        // Attempt http request
        String responseBody = "";
        try
        {
            InputStream response = connection.getInputStream;
            Scanner _scanner = new Scanner(response)
            String responseBody = scanner.useDelimiter("\\A").next();
            System.out.println(responseBody);

            if(response.statusCode() == 404)
            {
                System.out.println("\nCity not recognized. Please try again.");
            }
        }
        catch(Exception error)
        {
            System.out.println("Request failed. Please try again later.");
        }

        return responsebody;
    }

    public static String parseWeather(String getWeatherResponse, int optionSelected) throws Exception
    {
        ArrayList<String> weatherArray = new ArrayList<String>();

        if(optionSelected == 1)
        {

        }
        else if(optionSelected == 2)
        {

        }

        String weatherResults = String.join(",", weatherArray);

        return weatherResults;
    }
}