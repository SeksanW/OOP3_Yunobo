/*
 * standard Comparator for the shapes
 * 
 * handles comparison for sorting shapes by their attributes.
 * compare types:
 * a for area comparison
 * v for volume comparison 
 * h for height comparison  
*/

package utilities;

import java.util.Comparator;
import shapes.Shape;

public class ShapeComparator implements Comparator<Shape> {

    private char compareType;

    public ShapeComparator(char compareType) {
        this.compareType = Character.toLowerCase(compareType);
    }

    @Override
    public int compare(Shape s1, Shape s2) {

        switch(compareType) {

            case 'a':
                return Double.compare(s1.calcBaseArea(), s2.calcBaseArea());

            case 'v':
                return Double.compare(s1.calcVolume(), s2.calcVolume());

            case 'h':
            default:
                return Double.compare(s1.getHeight(), s2.getHeight());
        }
    }
}