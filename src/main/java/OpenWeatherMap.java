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
import java.text.*;
import java.net.*;
import com.google.gson.*; // JSON Parser

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

    public static String parseWeather(String responseBody, int optionSelected)
    {
        /*
        *   .parseWeather(String getWeatherResponse, int optionSelected)
        *
        *   Parses given responseBody JSON based on optionSelected.
        *   Returns an arrayList of data as a string.
        *
        */
        ArrayList<String> weatherResultsArray = new ArrayList<String>(); // Empty arrayList

        JsonParser _jsonParser = new JsonParser();
        JsonElement jsonTree = _jsonParser.parse(responseBody);

        if(optionSelected == 1) // Current weather
        {
            String[] weatherObject = new String[2];

            String currentTemperatureFahrenheit;
            String currentWeatherDescription;

            // response.main.temp
            JsonElement temperatureCurrentElement = jsonTree.getAsJsonObject().get("main").getAsJsonObject().get("temp");
            double currentTemperatureKelvin = temperatureCurrentElement.getAsDouble();
            double currentTemperatureFahrenheitAsDouble = ((currentTemperatureKelvin - 273.15 ) * (9/5) + 32);
            currentTemperatureFahrenheit = Double.toString(currentTemperatureFahrenheitAsDouble).substring(0,5);

            // response.weather[0].description
            JsonArray weatherCurrentElement = jsonTree.getAsJsonObject().get("weather").getAsJsonArray();
            JsonElement currentWeatherDescriptionElement = weatherCurrentElement.get(0).getAsJsonObject().get("description");
            currentWeatherDescription = currentWeatherDescriptionElement.getAsString();

            weatherObject[0] = currentWeatherDescription;
            weatherObject[1] = currentTemperatureFahrenheit;
            
            String weatherObjectString = Arrays.toString(weatherObject);
            System.out.println(weatherObjectString);

            weatherResultsArray.add(weatherObjectString);
        }
        else if(optionSelected == 2) // 5 Day Forecast
        {
            // response.list[]
            JsonArray listElement = jsonTree.getAsJsonObject().get("list").getAsJsonArray();

            // list[i]
            for (int objectIndex = 0; objectIndex < listElement.size(); objectIndex++)
            {
                String[] weatherObject = new String[3];
                
                String dateTime; // response.list[i].dt
                String weatherDescription; // response.list[i].weather.description
                String temperatureFahrenheit; // response.list[i].main.temp_max

                // Date and Time
                JsonElement listObject = listElement.get(objectIndex);
                long dateTimeEpoc = listObject.getAsJsonObject().get("dt").getAsLong() * 1000;
                Date date = new Date(dateTimeEpoc);
                SimpleDateFormat format = new SimpleDateFormat("E MM/dd/yyyy ha z");
                format.setTimeZone(TimeZone.getTimeZone("MST"));
                dateTime = format.format(date);

                // Temperature
                Double temperatureKelvin = listObject.getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsDouble();
                Double temperatureFahrenheitAsDouble = (((temperatureKelvin - 273.15 ) * (9/5)) + 32);
                temperatureFahrenheit = Double.toString(temperatureFahrenheitAsDouble).substring(0,5);

                // Weather Description
                weatherDescription = listObject.getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description").getAsString();
                
                weatherObject[0] = dateTime;
                weatherObject[1] = temperatureFahrenheit;
                weatherObject[2] = weatherDescription;

                String weatherObjectString = Arrays.toString(weatherObject);
                System.out.println(weatherObjectString);

                weatherResultsArray.add(weatherObjectString);
            }
        }

        String weatherResults = String.join(",", weatherResultsArray);

        return weatherResults;
    }
}
