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
*   COPYRIGHT:
*   This program is copyright (c) 2018 Evan Minor, Joshua Swick and Chris Harris.
*   This is original work, without use of outside sources.
*/

import java.util.*;
import java.io.*;
import java.text.*;
import com.google.gson.*; // JSON Parser

public class FinalProjectWeatherApp
{
    public static void main(String[] args) throws Exception
    {
        /* Welcome user */
        System.out.println("\n Welcome to Evan and Eli's Weather App!");
        System.out.println();
        System.out.println("                 ,~``````~,             ");
        System.out.println("               ,`          `,           ");
        System.out.println("      .~```~. /              \\         ");
        System.out.println("     /       `                \\,```.   ");
        System.out.println("    ._______________________________\\  ");
        System.out.println("          |       |   |      |   |      ");
        System.out.println("              |     |     |        |    ");
        System.out.println("       |    |     |     |      |        ");

        Scanner _scanner = new Scanner(System.in); // Inititalize Scanner

        while(true)
        {
            /* Display Menu */
            System.out.println(); // Empty line for formatting
            System.out.println();
            System.out.println("---------------------");
            String[] optionsMenu = {" 1. Current Weather", " 2. 5 Day Forecast", " 3. Exit"};
            for(int i = 0; i < optionsMenu.length; i++)
            {
                String option = optionsMenu[i];
                System.out.println(option);
            }
            System.out.println("---------------------");
            System.out.print("\n Please enter a number associated with the options above: ");
            String optionSelectedString = _scanner.nextLine();
            int optionSelected = 0;
            try
            {
                optionSelected = Integer.parseInt(optionSelectedString);
            }
            catch (Exception error)
            {
                optionSelected = 0; // Set to bad input
            }

            if(optionSelected == 1 || optionSelected == 2) // If current weather or forecast selected
            {
                /* Prompt for location */
                System.out.print("\n Enter a location (U.S. City or Zip Code): ");
                String location = _scanner.nextLine();
                String firstChar = Character.toString(location.charAt(0));
                String locationType = "";

                if(location.length() == 5 && firstChar.matches("[0-9]"))
                {
                    // Zip Code
                    locationType = "Zip";
                }
                else
                {
                    // City, Country Code
                    locationType = "City";
                }

                /* Make API request */
                String responseBody = OpenWeatherMap.getWeather(optionSelected, locationType, location);

                JsonParser _jsonParser = new JsonParser();
                JsonElement jsonTree = _jsonParser.parse(responseBody);

                /* Current Weather Response */
                if(optionSelected == 1 && responseBody != "")
                {
                    String currentTemperatureFahrenheit;
                    String currentWeatherDescription;
        
                    // response.main.temp
                    JsonElement temperatureCurrentElement = jsonTree.getAsJsonObject().get("main").getAsJsonObject().get("temp");
                    double currentTemperatureKelvin = temperatureCurrentElement.getAsDouble();
                    double currentTemperatureFahrenheitAsDouble = ((currentTemperatureKelvin - 273.15 ) * (9/5) + 32);
                    currentTemperatureFahrenheit = Double.toString(currentTemperatureFahrenheitAsDouble).substring(0,4);
        
                    // response.weather[0].description
                    JsonArray weatherCurrentElement = jsonTree.getAsJsonObject().get("weather").getAsJsonArray();
                    JsonElement currentWeatherDescriptionElement = weatherCurrentElement.get(0).getAsJsonObject().get("description");
                    currentWeatherDescription = currentWeatherDescriptionElement.getAsString();

                    // Format and print results
                    System.out.println();
                    System.out.println("--- Current Weather ---");
                    System.out.println(currentTemperatureFahrenheit + " F " + currentWeatherDescription);

                    //print to a file
                    System.out.println("");
                    System.out.println("Would you like to print to a file?");
                    System.out.println("Please select yes or no");
                    String input = _scanner.nextLine();

                    while(true)
                    {
                    if(input.equals("yes"))
                    {
                    System.out.println("");
                    System.out.println("Output file name");
                    String outputFile = _scanner.nextLine();

                    PrintWriter output = new PrintWriter(outputFile);
                    output.println("--- Current Weather ---");
                    output.println(currentTemperatureFahrenheit + " F " + currentWeatherDescription);

                    output.close();
                    break;
                    }
                    else if (input.equals("no"))
                    {
                        break;
                    }
                    else
                    {
                        System.out.println("");
                        System.out.println("Please enter yes or no.");
                        input = _scanner.nextLine();
                    }
                    }

                }

                /* 5 Day Forecast Response */
                else if(optionSelected == 2 && responseBody != "")
                {
                    // response.list[]
                    JsonArray listElement = jsonTree.getAsJsonObject().get("list").getAsJsonArray();

                    String[][] weatherResults = new String[listElement.size()][3];

                    // Parse response and insert wanted data into nested array structure
                    for(int objectIndex = 0; objectIndex < listElement.size(); objectIndex++)
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
                        temperatureFahrenheit = Double.toString(temperatureFahrenheitAsDouble).substring(0,4);

                        // Weather Description
                        weatherDescription = listObject.getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description").getAsString();
                        
                        weatherResults[objectIndex][0] = dateTime;
                        weatherResults[objectIndex][1] = temperatureFahrenheit;
                        weatherResults[objectIndex][2] = weatherDescription;
                    }

                    // Format and print results
                    String yesterday = "";
                    for(int i = 0; i < weatherResults.length; i++)
                    {
                        String dateTime = weatherResults[i][0];
                        String temperatureFahrenheit = weatherResults[i][1];
                        String weatherDescription = weatherResults[i][2];

                        String date[] = dateTime.split(" ");
                        String weekday = date[0];
                        String dateString = date[1];
                        String time = date[2];
                        String timeZone = date[3];
                        
                        // Add 0 for formatting
                        if(time.length() < 4)
                        {
                            time = " "+time;
                        }

                        if(weekday.equals(yesterday))
                        {
                            System.out.println(time + " " + timeZone + " " + temperatureFahrenheit + " F " + weatherDescription);
                        }
                        else
                        {
                            System.out.println();
                            System.out.println("--- " + weekday + " " + dateString + " ---");
                            System.out.println(time + " " + timeZone + " " + temperatureFahrenheit + " F " + weatherDescription);
                        }
                        yesterday = weekday;
                    }

                    System.out.println("");
                    // // Ask the user if they would like to print results to a file
                    System.out.println("Would you like to print the results to a file?");
                    System.out.println("Please enter yes or no.");
                    String input = _scanner.nextLine();
                    // // if the user does wish to print to a file

                    while(true)
                    {
                    if (input .equals("yes"))
                    {
                        System.out.println("");
                        System.out.println("Output file name");
                        String outputFileList = _scanner.nextLine();
                        PrintWriter outputFile = new PrintWriter(outputFileList);

                       for(int i = 0; i < weatherResults.length; i++)
                    {
                        String dateTime = weatherResults[i][0];
                        String temperatureFahrenheit = weatherResults[i][1];
                        String weatherDescription = weatherResults[i][2];

                        String date[] = dateTime.split(" ");
                        String weekday = date[0];
                        String dateString = date[1];
                        String time = date[2];
                        String timeZone = date[3];
                        
                        // Add 0 for formatting
                        if(time.length() < 4)
                        {
                            time = " "+time;
                        }

                        if(weekday.equals(yesterday))
                        {
                            outputFile.println(time + " " + timeZone + " " + temperatureFahrenheit + " F " + weatherDescription);
                        }
                        else
                        {
                            outputFile.println();
                            outputFile.println("--- " + weekday + " " + dateString + " ---");
                            outputFile.println(time + " " + timeZone + " " + temperatureFahrenheit + " F " + weatherDescription);
                        }
                        yesterday = weekday;
                    } 
                        outputFile.close();

                        System.out.println("");
                        System.out.println("Successfully printed to file: " +outputFileList);

                        break;
                    }

                    else if (input.equals("no"))
                    {
                        break;
                    }
                    else
                    {
                        System.out.println("");
                        System.out.println("Please enter yes or no.");
                        input = _scanner.nextLine();
                    }
                    }
                }
            }

            /* Exit option */
            else if(optionSelected == 3)
            {
                _scanner.close();
                System.exit(0);
            }

            /* Bad input */
            else
            {
                System.out.println("\nBad input, please try again.");
            }
        }
    }
}
