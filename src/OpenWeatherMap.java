/*  CS102 Final Project - WeatherApp
*   
*   PROGRAMMERS: Evan Minor and Joshua Eli Swick
*   CLASS: CS102
*   ASSIGNMENT: Final Project
*   INSTRUCTOR: Chris Harris
*   SUBMISSION DATE: ???
*
*   DESCRIPTION:
*   HTTP Interface for OpenWeatherMap API: https://openweathermap.org/api
*
*   COPYRIGHT:
*   This program is copyright (c) 2018 Evan Minor, Joshua Swick and Chris Harris.
*   This is original work, without use of outside sources.
*/

// Standard API
import java.util.*;

// HTTP Request Client - http://unirest.io/java.html

public class OpenWeatherMap
{
    private String urlBase = "https://api.openweathermap.org/data/2.5";
    private String apiKey = "APPID=238800e84194ea5fd444c1a1d82b9fe8";

    public String current()
    {
        String endpoint = "/weather";

        return weather;

    }

    public String forecast()
    {
        String endpoint = "/forecast";

        return forecast;
    }
}