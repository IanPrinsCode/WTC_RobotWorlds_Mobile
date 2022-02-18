import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Client {
    private static final String SERVER_HOST= "localhost";
    private static final int SERVER_PORT= 5000;
    private static Scanner scanner = new Scanner(System.in);
    private static String name;
    private static String messageFromServer;
    private static boolean killed = false;


    public static void main(String[] args) throws ClassNotFoundException{
        try (
                Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
                PrintStream out = new PrintStream(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
        )
        {

            do {
                name = getInput("What do you want to name your robot?");
                System.out.println("Robot Classes Available: \n" +
                        "Shooter \n" +
                        "Fighter \n" +
                        "Tank \n" +
                        "Sniper");
                String type = getInput("Choose class");
                System.out.println(launchRequest(type).getRequest());
                out.println(launchRequest(type).getRequest());

                System.out.println("Hello Kiddo!");
                out.println();


                String instruction = getInput(name + "> What must I do next?").strip().toLowerCase();

            } while (true);

        } catch (IOException e) {
            e.printStackTrace();
        }
        if(killed) {
            System.out.println("You have died (✖╭╮✖)");
        }
        else {
            System.out.println("GoodBye!");
        }
        System.exit(0);
    }

    public static String getInput(String prompt) {
        System.out.print(prompt+"\n");
        String input = scanner.nextLine();

        while (input.isBlank()) {
            System.out.println(prompt);
            input = scanner.nextLine();
        }
        return input;
    }

    private static LaunchRequest launchRequest(String type) {
        LaunchRequest launch = new LaunchRequest(name,"standard",
                3,3);
        if (type.equals("sniper")) {
            launch = new LaunchRequest(name,"sniper",
                    1,1);
        }
        else if (type.equals("shooter")) {
            launch = new LaunchRequest(name,"shooter",
                    3,3);
        }
        else if (type.equals("tank")) {
            launch = new LaunchRequest(name,"tank",
                    5,1);
        }
        else if (type.equals("fighter")) {
            launch = new LaunchRequest(name,"fighter",
                    2,5);
        }
        return launch;
    }

    private static void showResponse(JSONObject jsonObject) {
        JSONObject state = (JSONObject) jsonObject.get("state");
        JSONObject data = (JSONObject) jsonObject.get("data");
        System.out.println("**********************************"+"\n");
        System.out.println("Result: "+jsonObject.get("result"));
        System.out.println("Data: ");
//        System.out.println("Message: "+data.get("message"));
        if (data.has("objects")) {
            System.out.println("Objects: "+data.get("objects"));
        }
        System.out.println("State: ");
        System.out.println("Shield: "+state.get("shield"));
        System.out.println("Position: "+state.get("position"));
//        display.drawPlayer((JSONArray) state.get("position"));
        System.out.println("Shots: "+state.get("shots"));
        System.out.println("Direction: "+state.get("direction"));
//        if (data.has("objects")) {
//            display.drawObstruction((JSONArray) data.get("objects"));
//        }
        System.out.println("Status: "+state.get("status"));
        System.out.println("\n"+"**********************************");

    }

}
