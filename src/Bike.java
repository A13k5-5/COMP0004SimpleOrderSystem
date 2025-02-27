public class Bike extends Product{
    private int gears;
    private int wheelSize;

    Bike(int code, int price, int gears, int wheelSize){
        super(code, price);
        this.gears = gears;
        this.wheelSize = wheelSize;
    }

    @Override
    public String getDescription() {
        return "Gears: " + gears + ", WheelSize: " + wheelSize;
    }
}
