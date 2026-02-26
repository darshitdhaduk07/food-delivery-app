package food_delivery_app.services;

import food_delivery_app.cart.Cart;
import food_delivery_app.model.Customer;
import food_delivery_app.model.DeliveryAgent;
import food_delivery_app.model.Manager;
import food_delivery_app.repository.CustomerRepository;
import food_delivery_app.repository.DeliveryRepository;
import food_delivery_app.utility.InputValidator;

public class AuthenticationService {
    private final CustomerRepository customerRepo =
            CustomerRepository.getInstance();

    private final DeliveryRepository deliveryRepo =
            DeliveryRepository.getInstance();


    public Customer loginCustomer() {
        String email =
                InputValidator.readEmail("Email: ");

        int password =
                InputValidator.readPassword("Password: ");

        Customer customer =
                customerRepo.findByEmail(email);

        if (customer != null &&
                customer.getPassword() == password)
            return customer;

        return null;
    }
    public Manager loginManager()
    {
        Manager manager = Manager.getInstance();

        if(manager == null)
        {
            System.out.println("First Create Manager");
            manager = Manager.createManager();
        }
        String email =
                InputValidator.readEmail("Enter Email: ");

        int password =
                InputValidator.readPassword("Enter Password: ");



        if(manager.getEmail().equals(email) && manager.getPassword() == password)
            return manager;
        return null;
    }

    public void registerCustomer() {
        String name =
                InputValidator.readStringOnlyLetters("Name: ");

        String email =
                InputValidator.readEmail("Email: ");

        int password =
                InputValidator.readPassword("Password: ");

        if (customerRepo.findByEmail(email) != null) {
            System.out.println("Email already exists.");
            return;
        }

        Customer customer =
                new Customer(name,email,password);

        customer.setCart(new Cart());

        customerRepo.addCustomer(customer);

    }

    public DeliveryAgent loginDelivery() {

        String email =
                InputValidator.readEmail("Email: ");
        int password =
                InputValidator.readPassword("Password: ");

        for (DeliveryAgent boy : deliveryRepo.findAll()) {

            if (boy.getEmail().equals(email)
                    && boy.getPassword() == password)
                return boy;
        }

        return null;
    }
}
