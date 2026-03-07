package shapes;

public class PentagonalPrism extends Shape {

    private double side;

    public PentagonalPrism(double height, double side) {
        super(height);
        this.side = side;
    }

    @Override
    public double calcBaseArea() {
        return (5 * side * side) / (4 * Math.tan(Math.toRadians(54)));
    }

    @Override
    public double calcVolume() {
        return calcBaseArea() * height;
    }
}