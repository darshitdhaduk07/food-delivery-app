package food_delivery_app.utility;

import java.util.Scanner;
import java.util.regex.Pattern;

public class InputValidator {
    private static Scanner scanner = new Scanner(System.in);
    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    private static final String UPI_REGEX =
            "^[a-zA-Z0-9._-]{2,}@[a-zA-Z]{2,}$";
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
    public static String readPassword(String prompt) {

        while (true) {

            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            // minimum length check
            if (input.length() < 6) {
                System.out.println("Password must be at least 6 characters.");
                continue;
            }

            // regex:
            // (?=.*[A-Za-z])   -> at least one letter
            // (?=.*\\d)        -> at least one digit
            // (?=.*[@$!%*?&])  -> at least one special char
            String regex =
                    "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$";

            if (input.matches(regex)) {
                return input;
            }

            System.out.println(
                    "Password must contain letter, number and special character."
            );
        }
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


    /**
     * Reads non-empty string
     */
    public static String readString(String prompt) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();

            if (!input.isEmpty())
                break;

            System.out.println("Input cannot be empty!");
        }
        return input;
    }

    //only letter read
    public static String readStringOnlyLetters(String prompt) {
        String input;

        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();

            // check empty
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty!");
                continue;
            }

            // allow only letters and spaces
            if (input.matches("[a-zA-Z ]+")) {
                break;
            } else {
                System.out.println("Only alphabets are allowed!");
            }
        }

        return input;
    }

    /**
     * Reads valid email
     */
    public static String readEmail(String prompt) {
        String email;
        while (true) {
            System.out.print(prompt);
            email = scanner.nextLine().trim();

            if (Pattern.matches(EMAIL_REGEX, email))
                break;

            System.out.println("Invalid email format!");
        }
        return email;
    }

    //read double
    public static double readPositiveDouble(String prompt) {

        while (true) {

            System.out.print(prompt);

            String input = scanner.nextLine().trim();

            try {

                double value = Double.parseDouble(input);

                if (value > 0) {
                    return value;
                }

                System.out.println("Value must be greater than 0.");

            } catch (NumberFormatException e) {
                System.out.println("Enter valid number");
            }
        }
    }
    //read boolean
    public static boolean readBoolean(String prompt) {
        while (true) {
            System.out.print(prompt + " (true/false): ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("true")) {
                return true;
            } else if (input.equals("false")) {
                return false;
            } else {
                System.out.println("Invalid input! Please enter true or false.");
            }
        }
    }
    public static int readPositiveInt(String prompt) {

        int value;

        while (true) {

            value = readInt(prompt);

            if (value > 0) {
                return value;
            }

            System.out.println("Enter a positive number (> 0).");
        }
    }
    public static String readMessage(String prompt) {

        String input;

        while (true) {

            System.out.print(prompt);
            input = scanner.nextLine().trim();

            // empty check
            if (input.isEmpty()) {
                System.out.println("Message cannot be empty.");
                continue;
            }

//            // length check
//            if (input.length() > 200) {
//                System.out.println("Message too long (max 200 characters).");
//                continue;
//            }

            return input;
        }
    }
    public static String readPhoneNumber(String prompt) {

        while (true) {

            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            // only digits + exactly 10 digits
            if (!input.matches("^[6-9][0-9]{9}$")) {

                System.out.println(
                        "Invalid phone number. " +
                                "Enter exactly 10 digits."
                );
                continue;
            }

            return input;
        }
    }
}
