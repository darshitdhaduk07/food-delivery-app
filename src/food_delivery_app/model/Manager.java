package food_delivery_app.model;

import food_delivery_app.cart.Cart;


import java.util.List;

public class Manager extends User{
    private static int counter = 0;
    public Manager(String name, String email, int password)
    {
        super(name,email,password);
        this.id = ++counter;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "id='" + id + '\'' +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';

    }
}
