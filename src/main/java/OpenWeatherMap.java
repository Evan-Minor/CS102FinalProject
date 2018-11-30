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
//import java.net.URLEncoder;

// JSON Dependencies
import com.google.gson.*;

public class OpenWeatherMap
{
    public static String getWeather(int optionSelected, String locationType, String location) throws Exception
    {
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

        // Encode query for proper URL format
        String query = "?q=" + URLEncoder.encode((location), "UTF-8");
        String requestUrlFull = apiBaseUrl + apiEndpointUrl + query + apiKey;

        // Create HTTP client and build request
        URL url = new URL(requestUrlFull);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept-Charset", "UTF-8");

        // Attempt http request
        String responseBody = "";
        try
        {
            InputStream response = connection.getInputStream();
            Scanner _scanner = new Scanner(response);
            responseBody = _scanner.useDelimiter("\\A").next();

            if(connection.getResponseCode() == 404)
            {
                System.out.println("\nCity not recognized. Please try again.");
            }
        }
        catch(Exception error)
        {
            System.out.println("Request failed. Please try again later.");
        }

        return responseBody;
    }

    public static String parseWeather(String getWeatherResponse, int optionSelected) throws Exception
    {
        ArrayList<String> weatherArray = new ArrayList<String>();

        if(optionSelected == 1) // Current weather
        {

        }
        else if(optionSelected == 2) // 5 Day Forecast
        {

        }

        String weatherResults = String.join(",", weatherArray);

        return weatherResults;
    }
}