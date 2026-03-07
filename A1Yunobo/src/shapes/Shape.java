/* 
 * Taking all other shapes, cone, cylinder, and octagon etc 
 * after their area, heigh has already been calculated in their own files
 * and feeds their bare bones base information to appdriver in a digestable format
*/

package shapes;

public abstract class Shape implements Comparable<Shape> {

    protected double height;

    public Shape(double height) {
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    public abstract double calcBaseArea();
    public abstract double calcVolume();

    @Override
    public int compareTo(Shape other) {
        return Double.compare(this.height, other.height);
    }
    @Override
    public String toString() {
        return getClass().getSimpleName() + " Height: " + height;
    }
}