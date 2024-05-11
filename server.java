package cn;

import java.io.BufferedReader;  //read text from character based input
import java.io.InputStreamReader;  // convert bute stream to char stream
import java.io.PrintWriter;    //formatted text
import java.net.*;

public class server {

    public static void main(String[] args) {
        try {
            System.out.println("Waiting for clients operation: ");
            ServerSocket ss = new ServerSocket(4589);  //create socket instance at 4589 for connection

            while (true) {   //infinetely accept connection

                Socket ssoc = ss.accept();  //accept client connection
                System.out.println("Connection Established:");

                BufferedReader in = new BufferedReader(new InputStreamReader(ssoc.getInputStream()));   //read input from client
                String clientOperation = in.readLine(); // Read client's operation
                System.out.println("Operation received from client: " + clientOperation);    
                // Show the operation perform by the client

                if (clientOperation.equalsIgnoreCase("exit")) {   //request to close the connection
                    System.out.println("Disconnected");
                    ssoc.close();       // Close the connection
                    break;             // Break out of the loop
                }

                double result = evaluateExpression(clientOperation);  //calls the func to perfrom calcu
                PrintWriter out = new PrintWriter(ssoc.getOutputStream(), true);    //sends a result back to client

                if (!Double.isNaN(result)) {    // chechks for devide by 0 error
                    out.println("Answer: " + result);
                } else {
                    out.println("Error: Division by zero");
                }

                ssoc.close(); // Close the connection after processing the operation
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static double evaluateExpression(String expression) {   // expression evaluation logic
        try {
            String[] parts = expression.split(" ");
            double num1 = Double.parseDouble(parts[0]);
            double num2 = Double.parseDouble(parts[2]);
            String operator = parts[1];

            switch (operator) {
                case "+":
                    return num1 + num2;        
                case "-":
                    return num1 - num2;
                case "*":
                    return num1 * num2;
                case "/":
                    if (num2 != 0) {
                        return num1 / num2;
                    } else {
                        throw new ArithmeticException("Division by zero");
                    }
                case "%":
                    return num1 % num2;
                case "sqrt":
                    return Math.sqrt(num1);
                default:
                    throw new IllegalArgumentException("Invalid operator: " + operator);
            }

        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
            return Double.NaN;           // Return NaN for division by zero

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Double.NaN;          // Return NaN for any other exceptions
        }
    }
}