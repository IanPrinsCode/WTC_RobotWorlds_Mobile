package Display;

import java.util.Scanner;

public class Print {
    private final static Scanner scanner = new Scanner(System.in);

    public static String nameWorldPrompt(){
        System.out.print(ConsoleColors.CYAN);
        System.out.print("Enter a name of world: ");
        String name = scanner.nextLine();
        System.out.print(ConsoleColors.RESET);
        System.out.println();
        return name;
    }

    public static void purged(String name){
        System.out.print(ConsoleColors.RED_BOLD);
        System.out.println("Purged " + name + ".");
        System.out.print(ConsoleColors.RESET);
        System.out.println();
    }

    public static void terminated(){
        System.out.print(ConsoleColors.BLUE_UNDERLINED);
        System.out.println("\nServer has been terminated. Goodbye.");
        System.out.print(ConsoleColors.RESET);
    }

    public static void outputText(String text){
        System.out.print(ConsoleColors.BLUE_UNDERLINED);
        System.out.println(text);
        System.out.print(ConsoleColors.RESET);
    }
}


