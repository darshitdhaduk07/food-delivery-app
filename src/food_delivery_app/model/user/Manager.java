package food_delivery_app.model.user;

public class Manager extends User {

    private static Manager instance;
    private static int counter = 0;

    private Manager(String name, String email, String password) {
        super(name, email, password);
        this.id = ++counter;
    }
    public static Manager getInstance() {
        return instance;
    }


    public static void registerManager(String name,String email,String password) {
        instance = new Manager(name,email,password);
    }

    public static boolean exists() {
        return instance != null;
    }

    @Override
    public String toString() {
        return "Manager{" + "id='" + id + '\'' + ", name='" + getName() + '\'' + ", email='" + getEmail() + '\'' + '}';
    }
}