import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


//Server receives a String with Name, height in inches and feet,
//and weight and outputs a string with greeting, bmi index and
//weight difference information
//All input/output strings include delimiter '#'
public class Server {

    public static void main(String[] args) {
        String name;
        String feet;
        String inches;
        String weight;
        try (ServerSocket server = new ServerSocket(8080)) {

            System.out.println("Server started, waiting for connection...");
            Socket socket = server.accept();

            // in
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // out
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("Connection established, listening...");

            String line;
            while ((line = in.readLine()) != null) {
                String arrReceived[] = line.split("#");
                name = arrReceived[0];
                feet = arrReceived[1];
                inches = arrReceived[2];
                weight = arrReceived[3];
                out.println(messageForClient(name, feet, inches, weight));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("server terminating...");

    }

    // function to generate output String message for Client
    public static String messageForClient(String name, String feet, String inches, String weight){
        return greeting(name) + "#" + showBMI(bmi(feet, inches, weight)) + "#" + description(bmi(feet, inches, weight)) + "#" + difFromNorm(feet, inches, weight);
    }
    // function generates greeting
    private static String greeting(String name){
        return "Welcome, " + name + "!";
    }
    // function calculates bmi
    private static double bmi(String feet, String inches, String weight){
        int ft = Integer.parseInt(feet);
        int in = Integer.parseInt(inches);
        int lb = Integer.parseInt(weight);
        double bmi = 703 * lb / Math.pow((in + ft * 12), 2);
        return round(bmi, 2);
    }

    //function generates bmi string
    private static String showBMI (double bmi){
        return "Your BMI is: " + bmi;
    }

    //function to determine the level of weight
    private static String description (double bmi) {
        String result = "";
        if (bmi < 18.5) {
            result += "You are underweight.";
        } else if (bmi >= 18.5 && bmi < 24.9) {
            result += "You have normal weight according to the WHO classification.";
        } else if (bmi >= 25 && bmi < 29.9){
            result += "You are pre-obese.";
        } else if (bmi >= 30 && bmi < 34.9){
            result += "You have Obesity Class I.";
        } else if (bmi >= 35 && bmi < 39.9){
            result += "You have Obesity Class II.";
        } else {
            result += "You have Obesity Class III.";
        }
        return result;
    }
    //function to find the difference from the normal weight
    private static String difFromNorm (String feet, String inches, String weight){
        double norm = 0;
        int ft = Integer.parseInt(feet);
        int in = Integer.parseInt(inches);
        int lb = Integer.parseInt(weight);
        double bmi = 703 * lb / Math.pow((in + ft * 12), 2);
        if (bmi >= 18.5 && bmi < 24.9){
            return " ";
        }
        if (bmi < 18.5){
            norm = 18.5 / 703 * Math.pow((in + ft * 12), 2);
            return "You are " + round(norm - lb, 2) + "lbs. below WHO Normal Weight classification";
        } else {
            norm = 24.9 / 703 * Math.pow((in + ft * 12), 2);
            return "You are " + round(lb - norm, 2) + "lbs. above WHO Normal Weight classification";
        }
    }


    // function to round up a number with 2 decimals
    private static double round(double num, int pl) {
        if (pl < 0) throw new IllegalArgumentException();
        long factorial = (long) Math.pow(10, pl);
        num = num * factorial;
        long temp = Math.round(num);
        return (double) temp / factorial;
    }
}