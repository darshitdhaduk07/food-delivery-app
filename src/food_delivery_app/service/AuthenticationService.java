package food_delivery_app.service;

import food_delivery_app.model.cart.Cart;
import food_delivery_app.model.Address;
import food_delivery_app.model.user.Customer;
import food_delivery_app.model.user.DeliveryAgent;
import food_delivery_app.model.user.Manager;
import food_delivery_app.repository.CustomerRepository;
import food_delivery_app.repository.DeliveryRepository;
import food_delivery_app.utility.InputValidator;

import java.util.Set;

public class AuthenticationService {
    private final CustomerRepository customerRepo = CustomerRepository.getInstance();
    private Set<String> phoneNumbers = customerRepo.getPhoneNumber();
    private final DeliveryRepository deliveryRepo = DeliveryRepository.getInstance();

    public void registerCustomer() {

        String name = InputValidator.readStringOnlyLetters("Name: ");

        String email = InputValidator.readEmail("Email: ");

        if(customerRepo.isCustomerExist(email))
        {
            System.out.println("Customer already Exist");
            return;
        }
        String password = InputValidator.readPassword("Password: ");
        String address = InputValidator.readString("Address: ");

        String phone = InputValidator.readPhoneNumber("Mobile No.: ");
        if(phoneNumbers.contains(phone))
        {
            System.out.println("Mobile number already Exist.");
            return;
        }

        Customer customer = new Customer(name, email, password, phone);

        customer.addAddress(Address.makeAddress(address));

        customer.setCart(new Cart());
        customerRepo.addCustomer(customer);
        System.out.println("Customer Register Successfully");
    }
    public Customer loginCustomer() {

        String email = InputValidator.readEmail("Email: ");

        String password = InputValidator.readPassword("Password: ");

        Customer customer = customerRepo.findByEmail(email);
        if(customer == null)
        {
            System.out.println("Invalid Credentials");
            return null;
        }

        if (customer.getPassword().equals(password))
        {
            System.out.println("Login Successfully");
            return customer;
        }
        System.out.println("Wrong Password");
        return null;
    }
    public void registerManager()
    {
        Manager manager = Manager.getInstance();

        if (manager != null) {
            System.out.println("Manager already Exist");
            return;
        }
        String name = InputValidator.readStringOnlyLetters("Name :");
        String email = InputValidator.readEmail("Email :");

        String pass = InputValidator.readPassword("Password: ");
        Manager.registerManager(name,email,pass);
        System.out.println("Manager Register Successfully");

    }
    public Manager loginManager() {
        Manager manager = Manager.getInstance();

        if (manager == null) {
            System.out.println("Manager Not Exist");
            return null;
        }
        String email = InputValidator.readEmail("Enter Email: ");

        String password = InputValidator.readPassword("Enter Password: ");

        if (manager.getEmail().equals(email) && manager.getPassword().equals(password))
        {
            System.out.println("Login Successfully");
            return manager;
        }

        System.out.println("Invalid Credentials");
        return null;
    }

    public DeliveryAgent loginDeliveryAgent() {

        String email = InputValidator.readEmail("Email: ");
        String password = InputValidator.readPassword("Password: ");

        DeliveryAgent agent = deliveryRepo.findByEmail(email);
        if(agent == null)
        {
            System.out.println("Invalid Credentials");
            return null;
        }
        if(agent.getPassword().equals(password))
        {
            System.out.println("Login Successfully");
            return agent;
        }
        return null;
    }
    public DeliveryAgent registerDeliveryAgent()
    {

        String name = InputValidator.readStringOnlyLetters("Name: ");
        String email = InputValidator.readEmail("Email: ");
        if(deliveryRepo.isEmailExist(email))
        {
            System.out.println("DeliveryAgent already Exist");
            return null;
        }
        String pass = InputValidator.readPassword("Password: ");
        String phone = InputValidator.readPhoneNumber("Mobile No: ");
        if(phoneNumbers.contains(phone))
        {
            System.out.println("Mobile number already Exist");
            return null;
        }
        DeliveryAgent agent = new DeliveryAgent(name,email,pass,phone);
        System.out.println("Agent Registered");
        return agent;
    }
}
