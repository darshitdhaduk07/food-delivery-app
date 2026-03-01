package food_delivery_app.model;

public class Address {
    private String address;
    private int id;
    private static  int counter = 0;
    public Address(String address)
    {
        this.id = ++counter;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }
    public static Address makeAddress(String address)
    {
        return new Address(address);
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Address{" +
                "address='" + address + '\'' +
                '}';
    }
}
