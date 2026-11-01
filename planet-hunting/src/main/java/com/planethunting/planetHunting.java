package com.planethunting;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class planetHunting{
    static Scanner sc = new Scanner(System.in);
    private static final double[][] temps = {
        {100, 230}, {230, 360}, {360, 490}, {490, 620}, {620, 750}
    };
    private static final double[][] sizes = {
        {0.3, 0.8}, {0.8, 1.3}, {1.3, 2.0}, {2.0, 3.0}, {3.0, 4.5}
    };
    private static final double[][] brightnesses = {
        {0.01, 0.3}, {0.3, 0.7}, {0.7, 1.5}, {1.5, 4.0}, {4.0, 15.0}
    };
    private static final double[][] years = {
        {1, 20}, {20, 80}, {80, 200}, {200, 365}, {365, 700}
    };

   
    public static List<Planet> fetch(String url) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        String json = response.body();

        // Check whether NASA returned a successful response
        if (response.statusCode() != 200) {
            throw new RuntimeException(
                "NASA API error: " + response.statusCode() + "\n" + json
            );
        }

        // Parse the JSON array into a List of Planet objects
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Planet>>() {}.getType();

        return gson.fromJson(json, listType);
    }
    public static void main(String[] args) throws Exception{
        int temperature = 0;
        int size = 0;
        int brightness = 0;
        int yearLength = 0;
        
        String query = "SELECT pl_name, pl_eqt, pl_rade, pl_orbper, sy_vmag, sy_dist " +
                    "FROM pscomppars " +
                    "WHERE pl_eqt IS NOT NULL " +
                    "AND pl_rade IS NOT NULL " +
                    "AND pl_orbper IS NOT NULL " +
                    "AND sy_vmag IS NOT NULL " +
                    "AND sy_dist IS NOT NULL";

        String url = "https://exoplanetarchive.ipac.caltech.edu/TAP/sync?query="
                + URLEncoder.encode(query, StandardCharsets.UTF_8)
                + "&format=json";

        List<Planet> planets = planetHunting.fetch(url);
        int numberOfPlanets = planets.size();


        String str1 = "Welcome to Planet Hunting! Let's find you the best planet to live on!";
        Typing(str1);
        String str2 = "Please rate the following living conditions from 1 - 5";
        Typing(str2);
        String str3 = "Temperature: 1- Cold 5- Hot";
        Typing (str3);
        temperature = validInput();
        /*try {
            temperature = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Please input an integer. No decimals");
            temperature = sc.nextInt();
        }
        while (temperature < 1 || temperature > 5){
            System.out.println("Please input a number 1-5");
            temperature = sc.nextInt();
        }*/
        String str4 = "Planet size: 1- Small 5- Big";
        Typing (str4);
        size = validInput();
        String str5 = "Brightness: 1- Dim 5- Bright";
        Typing (str5);
        brightness = validInput();
        String str6 = "Year Length: 1- Short 5 - Long";
        Typing(str6);
        yearLength = validInput();
        String str10 = "Okay, confirm lift off? (y/n)";
        Typing(str10);
        if (validYN() == 'n'){
            System.out.println("Aw ok... Let's go hunting later!");
            return;
        }
        String str7 = "Great! Let's start searching!";
        Typing(str7);
        String str8 = "Searching.............................................";
        Typing(str8);
        String str9 = "Searched " + numberOfPlanets + " planets and found these!";
        Typing(str9);
        for (Planet p : planets) {
            p.score = score(p, temperature - 1, size - 1, brightness - 1, yearLength - 1);
        }
        selectionSort(planets);

        int numberOfResults = Math.min(5, planets.size());

        for (int i = 0; i < numberOfResults; i++) {
            Planet p = planets.get(i);

            System.out.println("\n#" + (i + 1) + " - " + p.name);
            System.out.println("Compatibility Score: " + p.score);
            System.out.println("Temperature: " + p.temp + " K");
            System.out.println("Radius: " + p.radius + " Earth radii");
            System.out.println("Orbital Period: " + p.orbit + " days");
        }
    }
    public static int validYN(){
        char answer = 'n';
        while (true) {
            if (sc.hasNext()) {
                answer = sc.next().charAt(0);
                
                if (answer != 'y' && answer != 'n') {
                    System.out.println("Please input 'y' or 'n' ");
                } else {
                    if (answer == 'y'){
                        return answer;
                    }
                    else{
                        return 'n';
                    }
                }
            } else {
                String str11 = ("Okay, confirm lift off? (y/n)");
                Typing(str11);
                sc.next();
            }
        }
    }
    public static int validInput(){
        Integer number = null;
        while (true) {
            if (sc.hasNextInt()) {
                number = sc.nextInt();
                
                if (number >= 1 && number <= 5) {
                    break;
                } else {
                    System.out.println("Please input a number 1-5");
                }
            } else {
                System.out.println("Okay, confirm lift off? (y/n)");
                sc.next();
            }
        }
        return number;
    }
    public static void Typing(String line){
        for (char letter : line.toCharArray()){
            System.out.print(letter);
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println();
    }

    public static void selectionSort(List<Planet> planets) {
        for (int i = 0; i < planets.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < planets.size(); j++) {
                if (planets.get(j).score < planets.get(minIndex).score) {
                    minIndex = j;
                }
            }
            Planet temp = planets.get(i);
            planets.set(i, planets.get(minIndex));
            planets.set(minIndex, temp);
        }
    }

    public static double score(Planet p, int tempR, int sizeR, int brightR, int yearR){
        // less point -> best
        int tempIndex = index(p, temps, p.temp);
        int sizeIndex = index(p, sizes, p.radius);
        int brightIndex = index(p, brightnesses, p.brightness);
        int yearIndex = index(p, years, p.orbit);
        double tempScore = tempIndex == -1 ? 5 : Math.abs(tempIndex - tempR);
        double sizeScore = sizeIndex == -1 ? 5 : Math.abs(sizeIndex - sizeR);
        double brightScore = brightIndex == -1 ? 5 : Math.abs(brightIndex - brightR);
        double yearScore = yearIndex == -1 ? 5 : Math.abs(yearIndex - yearR);
        /*System.out.println("Planet: " + p.name);
        System.out.println("Temperature index: " + index(p, temps, p.temp));
        System.out.println("Size index: " + index(p, sizes, p.radius));
        System.out.println("Brightness index: " + index(p, brightnesses, p.brightness));
        System.out.println("Year index: " + index(p, years, p.orbit));*/
        return (tempScore + sizeScore + brightScore + yearScore) / 4.0;
    }

    public static int index(Planet p, double[][] range, double planetTemp){
        int index = 0;
        for (int i = 0; i <5; i++){ //finding the rating of the planet's temp
            if (planetTemp >= range[i][0] && planetTemp < range[i][1]){
                index = i;
                return index;
            }
        }
        return -1;
    }
}
