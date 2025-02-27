import java.util.ArrayList;

public class SimpleOrderSystem
{
  public static final int ADD_CUSTOMER = 1;
  public static final int ADD_ORDER = 2;
  public static final int ADD_PRODUCT = 3;
  public static final int LIST_CUSTOMERS = 4;
  public static final int TOTAL = 5;
  public static final int QUIT = 10;
  private Input in = new Input();
  private ArrayList<Customer> customers;
  private ArrayList<Product> products;

  public SimpleOrderSystem()
  {
    customers = new ArrayList<Customer>();
    products = new ArrayList<Product>();
  }

  public void run()
  {
    while(true)
    {
      displayMenu();
      int option = getMenuInput();
      if (option == QUIT)
        break;
      doOption(option);
    }
  }

  private void displayMenu()
  {
    System.out.println("Simple Order System Menu");
    System.out.println(ADD_CUSTOMER + ". Add Customer");
    System.out.println(ADD_ORDER + ". Add Order");
    System.out.println(ADD_PRODUCT + ". Add Product");
    System.out.println(LIST_CUSTOMERS + ". List Customers");
    System.out.println(TOTAL + ". Total");
    System.out.println();
    System.out.println(QUIT + ". Quit");
  }
  
  private void doOption(int option)
  {
    switch (option)
    {
      case ADD_CUSTOMER:
        addCustomer();
        break;
      case ADD_ORDER:
         addOrder();
        break;
      case ADD_PRODUCT:
         addProduct();
         break;
      case LIST_CUSTOMERS:
        listCustomers();
        break;
      case TOTAL:
        overallTotal();
        break;
      default:
        System.out.println("Invalid option - try again");
    }
  }

  private int getMenuInput()
  {
    System.out.print("Enter menu selection: ");
    int option = in.nextInt();
    in.nextLine();
    return option;
  }

  private void addCustomer()
  {
    System.out.println("Add new customer");
    System.out.println("Enter first name:");
    String firstName = in.nextLine();
    System.out.println("Enter last name:");
    String lastName = in.nextLine();
    System.out.println("Enter address:");
    String address = in.nextLine();
    System.out.println("Enter phone number:");
    String phone = in.nextLine();
    System.out.println("Enter email address:");
    String email = in.nextLine();
    Customer customer = new Customer(firstName,lastName,address,phone,email);
    customers.add(customer);
  }

  private void addOrder()
  {
    Customer customer = findCustomer();
    if (customer == null)
    {
      System.out.println("Unable to add order");
      return;
    }
    Order order = new Order();
    addLineItems(order);
    if (order.getLineItemCount() == 0)
    {
      System.out.println("Cannot have an empty order");
      return;
    }
    customer.addOrder(order);
  }

  private Customer findCustomer()
  {
    System.out.print("Enter customer last name: ");
    String lastName = in.nextLine();
    System.out.print("Enter customer first name: ");
    String firstName = in.nextLine();
    return getCustomer(lastName, firstName);
  }

  private Customer getCustomer(String lastName, String firstName)
  {
    for (Customer customer : customers)
    {
      if (customer.getLastName().equals(lastName)
          && customer.getFirstName().equals(firstName))
      {
        return customer;
      }
    }
    return null;
  }

  private void addLineItems(Order order)
  {
    while (true)
    {
      System.out.print("Enter line item (y/n): ");
      String reply = in.nextLine();
      if (reply.startsWith("y"))
      {
        LineItem item = getLineItem();
        if (item != null)
        {
          order.add(item);
        }
      }
      else
      {
        break;
      }
    }
  }

  private LineItem getLineItem()
  {
    System.out.print("Enter product code: ");
    int code = in.nextInt();
    in.nextLine();
    Product product = getProduct(code);
    if (product == null)
    {
      System.out.println("Invalid product code");
      return null;
    }
    System.out.print("Enter quantity: ");
    int quantity = in.nextInt();
    in.nextLine();
    return new LineItem(quantity,product);
  }

  private Product getProduct(int code)
  {
    for (Product product : products)
    {
      if (product.getCode() == code)
      {
        return product;
      }
    }
    return null;
  }

  private void addProduct()
  {
    System.out.println("Enter product name: ");
    System.out.println("1. Bike");
    System.out.println("2. Phone");
    int choice = in.nextInt();
    System.out.print("Enter product code: ");
    int code = in.nextInt();
    in.nextLine();
    if (!isAvailableProductCode(code))
    {
      return;
    }
    System.out.print("Enter product price: ");
    int price = in.nextInt();
    in.nextLine();

    Product product;

    switch (choice){
      case 1: // Bike
      {
        System.out.println("Enter number of gears: ");
        int gears = in.nextInt();
        System.out.println("Enter wheel size in inches: ");
        int wheelSize = in.nextInt();
        product = new Bike(code, price, gears, wheelSize);
        break;
      }
      case 2: // Phone
      {
        System.out.println("Enter the phone brand: ");
        String brand = in.nextLine();
        System.out.println("Enter storage size in GB: ");
        int storage = in.nextInt();
        product = new Phone(code, price, brand, storage);
        break;
      }
      default:
        return;
    }

    products.add(product);
  }

  private boolean isAvailableProductCode(int code)
  {
    if (code < 1)
    {
      return false;
    }
    for (Product product : products)
    {
      if (product.getCode() == code)
      {
        return false;
      }
    }
    return true;
  }

  public void listCustomers()
  {
    System.out.println("List of customers");
    for (Customer customer : customers)
    {
      System.out.println("Name: " + customer.getLastName()
                                  + ", "
                                  + customer.getFirstName());
      System.out.println("Address: " + customer.getAddress());
      System.out.println("Phone: " + customer.getPhone());
      System.out.println("Email: " + customer.getEmail());
      System.out.println("Orders made: " + customer.getOrders().size());
      System.out.println("Total for all orders: " + customer.getTotalForAllOrders());
    }
  }

  public void overallTotal(){
    int total = 0;
    for (Customer c : customers){
      total += c.getTotalForAllOrders();
    }
    System.out.println("Total: " + "$" + total);
  }

  public void addData(){
    Customer mimi = new Customer("Miminka", "Vic", "Helsinki 3", "123", "miminka@uoh.fi");
    Customer rory = new Customer("Rory", "Byrne", "Oxford", "452", "rory@ucl.ac.uk");
    customers.add(mimi);
    customers.add(rory);

    Product bike = new Bike(0, 100, 5, 18);
    Product phone = new Phone(1, 650, "Samsung", 128);
    products.add(bike);
    products.add(phone);

    Order m = new Order();
    m.add(new LineItem(3, bike));
    m.add(new LineItem(1, phone));
    mimi.addOrder(m);

    Order r = new Order();
    r.add(new LineItem(7, bike));
    r.add(new LineItem(1, phone));
    rory.addOrder(r);
  }

  public static void main(String[] args)
  {
    SimpleOrderSystem orderSystem = new SimpleOrderSystem();
    orderSystem.addData();
    orderSystem.run();
  }
}
