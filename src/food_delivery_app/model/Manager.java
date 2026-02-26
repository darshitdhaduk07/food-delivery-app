package food_delivery_app.model;

import food_delivery_app.utility.InputValidator;

public class Manager extends User {

    private static Manager instance;
    private static int counter = 0;

    // private constructor
    private Manager(String name, String email, int password) {
        super(name, email, password);
        this.id = ++counter;
    }

    // create first time
    public static Manager createManager() {
        if (instance != null)
            return instance;

        String name =
                InputValidator.readStringOnlyLetters("Name: ");

        String email =
                InputValidator.readEmail("Email: ");

        int password =
                InputValidator.readInt("Password: ");

        instance = new Manager(name, email, password);


        return instance;
    }

    // get existing manager
    public static Manager getInstance() {
        return instance;
    }

    public static boolean exists() {
        return instance != null;
    }

    @Override
    public String toString() {
        return "Manager{" + "id='" + id + '\'' + ", name='" + getName() + '\'' + ", email='" + getEmail() + '\'' + '}';
    }
}