package sample.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Network {
    // Class variables to access within the program
    private static String shareOut;
    private static String shareIn;

    // Getters and setters for the variables
    public static String getShareIn() {
        return shareIn;
    }

    public static void setShareIn(String shareIn) {
        Network.shareIn = shareIn;
    }


    public static String getShareOut() {
        return shareOut;
    }

    public static void setShareOut(String shareOut) {
        Network.shareOut = shareOut;
    }
    // Function to create a new socket connection
    public static void startNetwork(){
        /////////////-----------------
        try (Socket socket = new Socket("localhost", 8080)) {
            // in
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // out
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            //
            out.println(shareOut);
            shareIn = in.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        /////////////------------------


    }

}
