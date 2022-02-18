import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Locale;
import java.util.Scanner;

public class Play {
    private static Scanner scanner;
    private static String response;
    private static String reply;
    private static final String SERVER_HOST= "localhost";
    private static final int SERVER_PORT= 5000;

    public Play() {
        this.scanner = new Scanner(System.in);
    }

    public static void start() throws ClassNotFoundException, IOException {
        Play();
    }

    public static void Play() {
        scanner = new Scanner(System.in);
        String name;

        while (true) {
            name = getInput("What do you want to name your robot?");
            dumpResponse(name.toLowerCase());
            if (reply.equals("This username is already taken")) {
            }
            else {
                break;
            }
        }
        System.out.println("Hello Kiddo!");


        Request request = null;
        boolean shouldContinue = true;
        do {

            String instruction = getInput(name + "> What must I do next?").strip().toLowerCase();
            if (instruction.equals("off")) {
                shouldContinue = false;
            }
            try {
                request = request.create(instruction, name);
                response = request.getRequest();
            } catch (IllegalArgumentException e) {
            }

            dumpResponse(response);

        } while (shouldContinue);
    }

    //

    //
    public static void dumpResponse(String response) {
        try (
                Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
                PrintStream out = new PrintStream(socket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
        ) {
            out.println(response);
            out.flush();
            String messageFromServer = in.readLine();
            reply = messageFromServer;
            System.out.println("Response: " + messageFromServer);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
