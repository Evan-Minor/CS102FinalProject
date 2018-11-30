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

// Standard API
import java.util.*;
import java.io.*;

public class FinalProjectWeatherApp
{
    public static void main(String[] args) throws Exception
    {
        /* Welcome user */
        System.out.println("\nWelcome to Evan and Eli's Weather App!");
        Scanner _scanner = new Scanner(System.in); // Inititalize Scanner
        while(true)
        {
            /* Display --MENU-- */
            System.out.println(); // Empty line for formatting
            System.out.println("--MENU--");
            String[] optionsMenu = {"1. Current Weather", "2. 5 Day Forecast", "3. Exit"};
            for(int i = 0; i < optionsMenu.length; i++)
            {
                String option = optionsMenu[i];
                System.out.println(option);
            }
            System.out.print("\nPlease enter a number associated with the options above: ");
            int optionSelected = _scanner.nextInt();
            int optionIndex = optionSelected - 1;
            _scanner.nextLine(); // Consume the rest of the line

            if(optionSelected == 1 || optionSelected == 2) // If current weather or forecast selected
            {
                /* Prompt for location */
                System.out.print("\nEnter a location (City or Zip Code): ");
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
                System.out.println(responseBody);

                /* Current Weather Response */
                if(optionSelected == 1)
                {
                    String weatherResults = OpenWeatherMap.parseWeather(responseBody, optionSelected);

                    // // Format and print results
                    // for(result in results[])
                    //     print(result)

                    // // Ask the user if they would like to print results to a file
                    // // if the user does wish to print to a file
                    // if yes:
                    //     for(result in results[])
                    //         file.print(result)
                }

                /* 5 Day Forecast Response */
                else if(optionSelected == 2)
                {
                    String weatherResults = OpenWeatherMap.parseWeather(responseBody, optionSelected);
                    List<String> weatherArray = 
                        new ArrayList<String>(Arrays.asList(weatherResults.split(",")));

                    // // Format and print results
                    // for(int r = 0; r in results[][])
                    // {
                    //     System.out.println(result);
                    // }

                    // // Ask the user if they would like to print results to a file
                    // // if the user does wish to print to a file
                    // if(yes)
                    // {
                    //     for(result in results[][])
                    //         file.print(result)
                    // }
        
                }
            }

            /* Exit option */
            else if(optionSelected == 3)
            {
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