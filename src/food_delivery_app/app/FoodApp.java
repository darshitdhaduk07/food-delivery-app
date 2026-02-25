package food_delivery_app.app;

import food_delivery_app.utility.InputValidator;

public class FoodApp {
    public static void main(String[] args) {

        while (true) {
            System.out.println("\n=== Food Delivery App ===");
            System.out.println("1. Manager");
            System.out.println("2. Customer");
            System.out.println("3. Delivery Boy");
            System.out.println("0. Exit");

            int choice = InputValidator.readInt("Select Your Role: ", 0, 3);

            switch (choice) {
                case 1:
                    managerMenu();
                    break;
                case 2:
                    customerMenu();
                    break;
                case 3:
                    deliveryBoyMenu();
                    break;
                case 0:
                    System.out.println("Exiting App. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}
