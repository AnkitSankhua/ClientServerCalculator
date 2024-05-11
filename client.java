package cn;

import java.io.BufferedReader;   //read text from character based input
import java.io.InputStreamReader;  // convert byte stream to char stream
import java.io.PrintWriter;    //formatted text
import java.net.*;

public class client {

    public static void main(String[] args) {

        try {
            System.out.println("CalculatorClient Started ");

            while (true) {   //infinite loop for continous interaction with server

                try (Socket soc = new Socket("localhost", 4589)) {   //create localhost socket 

                    System.out.println("Enter your Operations ('exit' to disconnect from server): ");  //user input
                    BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
                    String str = userInput.readLine();

                    if (str.equalsIgnoreCase("exit")) {      // Exit the loop if the user inputs 'exit'
                        System.out.println("Connection disconnected.");
                        break;                  

                    } else if (str.equalsIgnoreCase("disconnect")) {    // Disconnect from the server if the user inputs 'disconnect'
                        System.out.println("Disconnecting from server...");
                        break; 
                    }
 
                    PrintWriter out = new PrintWriter(soc.getOutputStream(), true);
                    out.println(str);         // Send operation to server

                    BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream()));  //read results from the server
                    System.out.println("Result: " + in.readLine());

                } catch (ConnectException ce) {     // for connection mismatch
                    System.out.println("Connection refused. Ensure the server is running.");
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}