package psgomez_CSCI201_Assignment1;


public class WeatherInfo {
//created the private variables started with strings, then ints, then floats

	private String location;
	private String condition;
	private int temperature;
	private int temperatureMin;
	private int temperatureMax;
	private int humidity;
	private int windDirection;
	private float pressureSeaLevel;
	private float visibility;
	private float windSpeed;
	
//created constructor to initialize the weather info
	
	public WeatherInfo(String location, String condition, int temperature, int temperatureMin, int temperatureMax, 
			int humidity, int windDirection, float pressureSeaLevel, float visibility, float windSpeed) {
		
		this.location = location;
		this.condition = condition;
		this.temperature = temperature;
		this.temperatureMin = temperatureMin;
		this.temperatureMax = temperatureMax;
		this.humidity = humidity;
		this.windDirection = windDirection;
		this.pressureSeaLevel = pressureSeaLevel;
		this.visibility = visibility;
		this.windSpeed = windSpeed;
	}
	
//Created all the setters and getters to set and get stuff :)
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public int getTemperature() {
		return temperature;
	}
	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}
	public int getTemperatureMin() {
		return temperatureMin;
	}
	public void setTemperatureMin(int temperatureMin) {
		this.temperatureMin = temperatureMin;
	}
	public int getTemperatureMax() {
		return temperatureMax;
	}public void setMaxTemperature(int temperatureMax) {
		this.temperatureMax = temperatureMax;
	}
	public int getHumidity() {
		return humidity;
	}
	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}
	public int getWindDirection() {
		return windDirection;
	}
	public void setWindDirection(int windDirection) {
		this.windDirection = windDirection;
	}
	public float getPressureSeaLevel() {
		return pressureSeaLevel;
	}
	public void setPressureSeaLevel(float pressureSeaLevel) {
		this.pressureSeaLevel = pressureSeaLevel;
	}
	public float getVisibility() {
		return visibility;
	}
	public void setVisibility(float visibility) {
		this.visibility = visibility;
	}
	public float getWindSpeed() {
		return windSpeed;
	}
	public void setWindSpeed(float windSpeed) {
		this.windSpeed = windSpeed;
	}
	
	public void display() {
		System.out.println("Location: " + location);
		System.out.println("Condition: " + condition);
		System.out.println("Temperature: " + temperature + "°F");
		System.out.println("Min Temperature: " + temperatureMin + "°F");
		System.out.println("Max Temperature: " + temperatureMax + "°F");
		System.out.println("Humidity: " + humidity + "%");
		System.out.println("Wind Direction: " + windDirection + "°");
		System.out.println("Sea Level Pressure: " + pressureSeaLevel + " Inch Hg");
		System.out.println("Visibility: " + visibility + " Miles");
		System.out.println("Wind Speed: " + windSpeed + " mph");
		System.out.println(" ");
	}
	
	
}
