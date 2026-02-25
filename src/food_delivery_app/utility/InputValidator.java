package food_delivery_app.utility;

import java.util.Scanner;

public class InputValidator {
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Reads an integer from user input.
     * Keeps asking until a valid integer is entered.
     */
    public static int readInt(String prompt) {
        int value;
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                value = Integer.parseInt(input);
                break; // valid integer
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
        return value;
    }

    /**
     * Reads an integer within a range [min, max]
     */
    public static int readInt(String prompt, int min, int max) {
        int value;
        while (true) {
            value = readInt(prompt);
            if (value >= min && value <= max) {
                break;
            } else {
                System.out.println("Input must be between " + min + " and " + max);
            }
        }
        return value;
    }
}
