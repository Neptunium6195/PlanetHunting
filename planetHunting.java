import java.util.Scanner;
public class planetHunting {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args){
        int temperature = 0;
        int size = 0;
        int brightness = 0;
        int yearLength = 0;
        // PlanetFetcher.java — fetch planets with temperature data
        String url = "https://exoplanetarchive.ipac.caltech.edu/TAP/sync?" +
            "query=SELECT+pl_name,pl_eqt,pl_rade,pl_masse,pl_orbper,sy_dist" +
            "+FROM+pscomppars+WHERE+pl_eqt+IS+NOT+NULL&format=json";

        //HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        //conn.setRequestMethod("GET");
        // read response stream → parse with Gson into List<Planet>
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
                System.out.println("Please input an integer. No decimals");
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
}
