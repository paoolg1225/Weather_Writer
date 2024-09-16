package psgomez_CSCI201_Assignment1;

//we get importing important imports

//need to fix save changes so it actually saves and i can 
//search for the file when i start the program again.
//need to change search for weather in location so i can
//display submenu and pick from there
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class WeatherMain {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Gson gson = new Gson();
		List<WeatherInfo> weatherDataList = null;
		String fileName = null;
		
//This first section (lines 27-62) prompts the user to enter the name of the file and if the file is not formatted properly
// or does not exist, then it lets the user know and if the filename and formatting are valid then it displays the menu
		while(weatherDataList == null) {
			System.out.println("What is the name of the weather file?: ");
			fileName = sc.nextLine();
			
			
			try(FileReader reader = new FileReader(fileName)){
				JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
				
				if(!jsonObject.has("data")) {
					throw new Exception("File is missing the 'data' field.");
				}
			    weatherDataList = gson.fromJson(jsonObject.get("data"), new TypeToken<List<WeatherInfo>>(){}.getType());
			    
//if loop ensures that if the file has empty or missing fields then it will throw an exception, might not for grading but well see			    
			    for(WeatherInfo weather : weatherDataList) {
			    	if(weather.getLocation() == null || weather.getCondition() == null ||
			    		weather.getTemperature() == 0 || weather.getTemperatureMin() == 0 ||
			    		weather.getTemperatureMax() == 0 || weather.getHumidity() == 0 ||
			    		weather.getPressureSeaLevel() == 0.0f || weather.getVisibility() == 0.0f ||
			    		weather.getWindSpeed() == 0.0f || weather.getWindDirection() == 0) {
			    		throw new Exception("One or more Fields are missing in the weather data");
			    	}
			    }
			    
			    
			    
				System.out.println(fileName + " File loaded successfully.");
			}	catch(IOException e) {
				System.out.println("The file " + fileName + " could not be found.");
			}	catch(Exception e) {
				System.out.println("The file " + fileName + " is not formatted properly.");
			}
			
		}
		displayMenu(weatherDataList, sc, fileName);
	}

	
//lines 67-107 displays the menu and lists the options and also has a switch case for each option in the menu and then calls to the method chosen
private static void displayMenu(List<WeatherInfo> weatherDataList, Scanner sc, String fileName) {
	boolean exit = false;
	
	while(!exit) {
		System.out.println("\n1) Display weather on all loactions");
		System.out.println("2) Search for weather on a location");
		System.out.println("3) Add a new location");
		System.out.println("4) Remove a location");
		System.out.println("5) Sort locations");
		System.out.println("6) Exit");
		System.out.println("Which option would you like to choose from?");
		
		String choice = sc.nextLine();
		
		switch(choice) {
		case "1":
			displayAllLocations(weatherDataList);
			break;
		case "2":
			searchLocations(weatherDataList, sc);
			break;
		case "3":
			addNewLocation(weatherDataList, sc);
			break;
		case "4":
			removeLocation(weatherDataList, sc);
			break;
		case "5":
			sortLocation(weatherDataList, sc);
			break;
		case "6":
			exit = true;
			saveChanges(weatherDataList, sc);
			break;
			default:
				System.out.println("Invalid, Please choose a valid option.");
		
		}
		
	}
}


//this method displays all the locations with their weatherInfo  in the json file 
private static void displayAllLocations(List<WeatherInfo> weatherDataList) {
	for(WeatherInfo weather : weatherDataList) {
		weather.display();
	}
}


// this method lets you enter a location and then search from the weather info individually
// prints out Location not found if it doesn't exist 
private static void searchLocations(List<WeatherInfo> weatherDataList, Scanner sc) {
	System.out.println("What is the location you would like to search from?: ");
	String location = sc.nextLine().toLowerCase();
	boolean found = false;
	
	for(WeatherInfo weather : weatherDataList) {
		if(weather.getLocation().equalsIgnoreCase(location)) {
			System.out.println("I have information about the weather in " + location);
			displayWeatherSubmenu(weather, sc);
			found = true;
			break;
		}
	}
	if(!found) {
		System.out.println(location + " not found, Please try again.");
	}
}

//this method displays the submenu when option 2 is chosen and has the switch cases for which weather info the user wants to know for a specific location
private static void displayWeatherSubmenu(WeatherInfo weather, Scanner sc) {
	boolean exitSubmenu = false;
	
	while(!exitSubmenu) {
		System.out.println("\n1) Temperature");
		System.out.println("2) Max and Min temperature");
		System.out.println("3) Humidity");
		System.out.println("4) Pressure");
		System.out.println("5) Visibility");
		System.out.println("6) Wind speed and Direction");
		System.out.println("7) Weather conditions");
		System.out.println("8) Return to main menu");
		System.out.println("What weather information do you want to know about " + weather.getLocation() + "?");
		
		String option = sc.nextLine();
		
		
		switch (option) {
		case "1":
			System.out.println("The temperature in " + weather.getLocation() + " is " + weather.getTemperature() + " degrees Fahrenheit.");
			break;
		case "2":
			System.out.println("The Max/Min in " + weather.getLocation() + " is " + weather.getTemperatureMax() + " / " + weather.getTemperatureMin() + " degrees Fahrenheit.");
			break;
		case "3":
			System.out.println("The Humidity in " + weather.getLocation() + " is " + weather.getHumidity() + "%.");
			break;
		case "4":
			System.out.println("The pressure in " + weather.getLocation() + " is " + weather.getPressureSeaLevel() + " Inch Hg.");
			break;
		case "5":
			System.out.println("The Visibility in " + weather.getLocation() + " is " + weather.getVisibility() + " miles.");
			break;
		case "6":
			System.out.println("The wind speed and direction in " + weather.getLocation() + " is " + weather.getWindSpeed() + " mph and " + weather.getWindDirection() + " degrees.");
			break;
		case "7":
			System.out.println("The weather condition in " + weather.getLocation() + " can be described as " + weather.getCondition() + ".");
			break;
		case "8":
			exitSubmenu = true;
			break;
		default:
			System.out.println("Invalid option, Please try again");
			
		}
	}
}

//Lets the user add a new location  and checks that the inputs are valid for each field in the weather info list
private static void addNewLocation(List<WeatherInfo> weatherDataList, Scanner sc) {
	System.out.println("Please enter the location you would like to add: ");
	String location = sc.nextLine();
	for(WeatherInfo weather : weatherDataList) {
		if(weather.getLocation().equalsIgnoreCase(location)) {
			System.out.println(location + " already exists.");
			return;
		}
	}
	
	String condition;
	while(true) {
		System.out.println("What is the condition: ");
		condition = sc.nextLine();
		
		if(condition.matches("[a-zA-Z]+")) {
			break;
		}else {
			System.out.println("Invalid condition. Please enter a valid condition.");
		}
	}
	
	
	

	int temperature = 0;
	while(true) {
		System.out.println("What is the Temperature");
		try {
			temperature = Integer.parseInt(sc.nextLine());
			break;
		} catch (NumberFormatException e) {
			System.out.println("Invalid Input, please enter a valid integer");
		}
	}
	
	
	int temperatureMin = 0;
	while(true) {
		System.out.println("What is the Min Temperature");
		try {
			temperatureMin = Integer.parseInt(sc.nextLine());
			break;
		} catch (NumberFormatException e) {
			System.out.println("Invalid Input, please enter a valid integer");
		}
	}
	
	int temperatureMax = 0;
	while(true) {
		System.out.println("What is the Max Temperature");
		try {
			temperatureMax = Integer.parseInt(sc.nextLine());
			break;
		} catch (NumberFormatException e) {
			System.out.println("Invalid Input, please enter a valid integer");
		}
	}
	
	
	int humidity = 0;
	while(true) {
		System.out.println("What is the Humidity");
		try {
			humidity = Integer.parseInt(sc.nextLine());
			break;
		} catch (NumberFormatException e) {
			System.out.println("Invalid Input, please enter a valid integer");
		}
	}
	
	
	int windDirection = 0;
	while(true) {
		System.out.println("What is the Wind Direction");
		try {
			windDirection = Integer.parseInt(sc.nextLine());
			break;
		} catch (NumberFormatException e) {
			System.out.println("Invalid Input, please enter a valid integer");
		}
	}
	
	
	
	float pressureSeaLevel = 0.0f;
	while(true) {
		System.out.println("What is the Sea Level Pressure");
		try {
			pressureSeaLevel = Float.parseFloat(sc.nextLine());
			break;
		} catch (NumberFormatException e) {
			System.out.println("Invalid Input, please enter a valid number");
		}
	}
	
	float visibility = 0.0f;
	while(true) {
		System.out.println("What is the Visibility");
		try {
			visibility = Float.parseFloat(sc.nextLine());
			break;
		} catch (NumberFormatException e) {
			System.out.println("Invalid Input, please enter a valid number");
		}
	}
	float windSpeed = 0.0f;
	while(true) {
		System.out.println("What is the Sea Level Pressure");
		try {
			windSpeed = Float.parseFloat(sc.nextLine());
			break;
		} catch (NumberFormatException e) {
			System.out.println("Invalid Input, please enter a valid number");
		}
	}
	
	WeatherInfo newWeather = new WeatherInfo(location, condition, temperature, temperatureMin, temperatureMax, humidity, 
			windDirection, pressureSeaLevel, visibility, windSpeed);
	weatherDataList.add(newWeather);
	System.out.println(location + " successfully added!");
			
}

// prints out the locations in the json file and lets the user choose which one to remove from the file
private static void removeLocation(List<WeatherInfo> weatherDataList, Scanner sc) {
	
	if(weatherDataList.isEmpty()) {
		System.out.println("There are no locations to remove.");
		return;
	}
	
	
	System.out.println("Here are the current locations.");
	for (int i = 0; i < weatherDataList.size(); i++) {
		System.out.println((i+1) + ") " + weatherDataList.get(i).getLocation());
	}
	
	int indexToRemove = -1;
	while(true) {
		System.out.println("Which location would you like to remove?");
		try {
			indexToRemove = Integer.parseInt(sc.nextLine()) - 1;
			
			if(indexToRemove >= 0 && indexToRemove < weatherDataList.size()) {
				String removedLocation = weatherDataList.get(indexToRemove).getLocation();
				weatherDataList.remove(indexToRemove);
				System.out.println(removedLocation + " is now removed.");
				break;
			}else {
				System.out.println("Invalid number. Please enter a valid number.");
			}
		} catch(NumberFormatException e) {
			System.out.println("Invalid input. Please enter a valid number");
		}
	}
}


//this method lets the user sort the locations from a-z or z-a depending on if the user types 1 or 2, anything else will 
//print invalid choice

private static void sortLocation(List<WeatherInfo> weatherDataList, Scanner sc) {
	System.out.println("How would you like to sort the locations?");
	System.out.println("1) A to Z");
	System.out.println("2) Z to A");
	String sortChoice = sc.nextLine();
	
	if(sortChoice.equals("1")) {
		weatherDataList.sort((w1, w2) -> w1.getLocation().compareToIgnoreCase(w2.getLocation()));
		System.out.println("Your locations are now sorted from (A to Z).");
	} else if(sortChoice.equals("2")) {
		weatherDataList.sort((w1, w2) -> w2.getLocation().compareToIgnoreCase(w1.getLocation()));
		System.out.println("Your locations are now sorted from (Z to A).");
	} else {
		System.out.println("Invalid choice. please select 1 or 2");
	}
}

	
	
	
//I was stuck on a section for so long because i couldn't open a file after i had just saved it,
//but it turns out that on line 335, I had accidentally typed dada instead of data :/
//this saves the file and lets the user open it when they boot the program up again

private static void saveChanges(List<WeatherInfo> weatherDataList, Scanner sc) {
	System.out.println("Would you like to save your changes?");
	System.out.println("1) Yes");
	System.out.println("2) No");
	String save = sc.nextLine().toLowerCase();
	
	if (save.equals("1")){
		System.out.println("Enter the file name to save to: ");
		String newFileName = sc.nextLine();
		
		try(FileWriter writer = new FileWriter(newFileName)){
			Gson gson = new Gson();
			JsonObject jsonObject = new JsonObject();
			jsonObject.add("data", gson.toJsonTree(weatherDataList));
			gson.toJson(jsonObject, writer);
			System.out.println("Your edits have been saved to " + newFileName);
		} catch (IOException e) {
			System.out.println("Error saving changes to " + newFileName);
		}
	} else {
		System.out.println("Changes not saved");
	}
	System.out.println("Thank you for using my program!");
	
}
}
