public class Phone extends Product{
    private String brand;
    private int storage;
    Phone(int code, int price, String brand, int storage){
        super(code, price);
        this.brand = brand;
        this.storage = storage;
    }

    @Override
    public String getDescription() {
        return "Brand: " + brand + " " + storage + "GB";
    }
}
