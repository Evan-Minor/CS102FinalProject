/*  CS102 Final Project - WeatherApp
*   
*   PROGRAMMERS: Evan Minor and Joshua Eli Swick
*   CLASS: CS102
*   ASSIGNMENT: Final Project
*   INSTRUCTOR: Chris Harris
*   SUBMISSION DATE: 2018/12/02
*
*   DESCRIPTION:
*   Basic weather application that leverages OpenWeatherMap API: https://openweathermap.org/api
*
*   EXTERNAL PACKAGES:
*   https://github.com/google/gson
*
*   COPYRIGHT:
*   This program is copyright (c) 2018 Evan Minor, Joshua Swick and Chris Harris.
*   This is original work, without use of outside sources.
*/

import java.util.*;
import java.io.*;
import java.net.*;

public class OpenWeatherMap
{

    public static String getWeather(int optionSelected, String locationType, String location) throws Exception
    {
        /*
        *   .getWeather(int optionSelected, String locationType, String location)
        *
        *   Makes an HTTP request to OpenWeatherMap.com's API based on method arguments.
        *   Returns a JSON object as a string.
        *
        */
        String apiKey = "&APPID=238800e84194ea5fd444c1a1d82b9fe8";
        String apiBaseUrl = "https://api.openweathermap.org/data/2.5";
        
        // Assign endpoint based on choice
        String apiEndpointUrl = null;
        if(optionSelected == 1)
        {
            apiEndpointUrl = "/weather";
        }
        else if(optionSelected == 2)
        {
            apiEndpointUrl = "/forecast";
        }
        // Query string based on Zip Code or City input
        String queryType = null;
        if (locationType.equals("Zip"))
        {
            queryType = "zip="; // Zip Code
        }
        else 
        {
            queryType = "q="; // Default to city
        }

        // Encode query for proper URL format
        String query = "?"+ queryType + URLEncoder.encode((location), "UTF-8");
        String requestUrlFull = apiBaseUrl + apiEndpointUrl + query + apiKey;

        // Attempt http request
        String responseBody = "";
        try
        {
            URL url = new URL(requestUrlFull);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            
            InputStream response = connection.getInputStream();
            Scanner _scanner = new Scanner(response);
            responseBody = _scanner.useDelimiter("\\A").next();
            _scanner.close();
        }
        catch(Exception error)
        {
            System.out.println("\nRequest failed. Please try again.");
        }

        return responseBody;
    }
}
