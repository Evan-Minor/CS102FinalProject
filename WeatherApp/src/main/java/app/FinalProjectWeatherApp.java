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

// Standard API
import java.util.*;
import java.io.*;

public class FinalProjectWeatherApp
{
    public static void main(String[] args) throws IOException
    {
        // Welcome user
        System.out.println("\nWelcome to Evan and Eli's Weather App!");

        // Inititalize Scanner
        Scanner _scanner = new Scanner(System.in);

        // User Interface Loop
        while(true)
        {

            // Display options and prompt for input
            System.out.println(); // Empty line for formatting
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

            
            // If current weather or forecast selected, prompt for location
            if(optionSelected == 1 || optionSelected == 2)
            {
                System.out.print("\nEnter a location: ");
                String location = _scanner.nextLine();
            }

            // /* Current Weather */
            // if(option == 1)
            // {
            //     results[] = parse(response)

            //     // Format and print results
            //     for(result in results[])
            //         print(result)

            //     // Ask the user if they would like to print results to a file
            //     // if the user does wish to print to a file
            //     if yes:
            //         for(result in results[])
            //             file.print(result)
            // }

            // /* 5 Day Forecast */
            // else if(optionSelected = 2)
            // {
            //     results[][] = parse(response)

            //     // Format and print results
            //     for(int r = 0; r in results[][])
            //     {
            //         System.out.println(result);
            //     }

            //     // Ask the user if they would like to print results to a file
            //     // if the user does wish to print to a file
            //     if yes:
            //         for(result in results[][])
            //             file.print(result)
            // }

            // Exit option
            else if(optionSelected == 3)
            {
                System.exit(0);
            }

            // Bad input
            else
            {
                System.out.println("\nBad input, please try again.");
            }
        }
    }
}