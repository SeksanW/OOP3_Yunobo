/* Course: 		Object-Oriented Programming 3 (CPRG-304-A)
 * Instructor:	Kitty Wong
 * Team: 		Team Yunobo
 * Team Members:Seksan Wangkhiree 
 * 				Cooper Jacques 
 * 				Justin Penner 
 * 				Yashneet Kaur
 * 
 * Assignment:  Assignment 1: Complexity and Sorting
 * 
 * Purpose:
 * AppDriver is the main entry point for the Complexity and Sorting application.
 * 
 * This program reads a file of three-dimensional shapes and sorts them
 * using one of six available sorting algorithms. 
 * The sorting can be performed by height, volume, or base area.
 * 
 * Command line usage:
 * java -jar Sort.jar -f<filename> -t<compareType> -s<sortType>
*/
package appDomain;

import java.io.File;
import java.util.Scanner;

import shapes.*;
import utilities.SortingUtility;
import utilities.ShapeComparator;
/**
 * <p>
 * This application driver code is designed to be used as a basis for the
 * Complexity and Sorting assignment that will be developed in the CPRG304 
 * W2026 class at SAIT. The implementors of this applications will be required
 * to add all the correct functionality.
 * </p>
 */
public class AppDriver
{
	/**
	 *  The main method is the entry point of the application.
	 *  
	 *  @param args The input to control the execution of the application.
	 */
	public static void main( String[] args )
	{
		String fileName = null;
        char compareType = 'h';
        char sortType = 'b';

        // Parse command line arguments
        for (String arg : args) {
            String argLower = arg.toLowerCase();

            if (argLower.startsWith("-f")) {
                fileName = arg.substring(2);

            } else if (argLower.startsWith("-t")) {
                compareType = Character.toLowerCase(arg.charAt(2));

            } else if (argLower.startsWith("-s")) {
                sortType = Character.toLowerCase(arg.charAt(2));
            }
        }
        
        // Error messages
        if (fileName == null) {
            System.out.println("Error: No input file specified. Use -f followed by the file name.");
            return;
        }

        if (compareType != 'h' && compareType != 'v' && compareType != 'a') {
            System.out.println("Error: Invalid compare type '" + compareType + "'. Use -th, -tv, or -ta");
            return;
        }

        if (sortType != 'b' && sortType != 's' && sortType != 'i' 
            && sortType != 'm' && sortType != 'q' && sortType != 'z') {
            System.out.println("Error: Invalid sort type '" + sortType + "'. Use -sb, -ss, -si, -sm, -sq, or -sz");
            return;
        }
        
        try {
        	// Read files under res
            Scanner fileScanner = new Scanner(new File(fileName));

            int size = Integer.parseInt(fileScanner.nextLine());

            Shape[] shapes = new Shape[size];

            for (int i = 0; i < size; i++) {

                String type = fileScanner.next();
                double height = fileScanner.nextDouble();
                double value = fileScanner.nextDouble();

                switch (type) {

                    case "Cylinder":
                        shapes[i] = new Cylinder(height, value);
                        break;

                    case "Cone":
                        shapes[i] = new Cone(height, value);
                        break;

                    case "Pyramid":
                        shapes[i] = new Pyramid(height, value);
                        break;

                    case "SquarePrism":
                        shapes[i] = new SquarePrism(height, value);
                        break;

                    case "TriangularPrism":
                        shapes[i] = new TriangularPrism(height, value);
                        break;

                    case "PentagonalPrism":
                    	shapes[i] = new PentagonalPrism(height, value);
                        break;

                    case "OctagonalPrism":
                        shapes[i] = new OctagonalPrism(height, value);
                        break;
                }
            }

            fileScanner.close();

            // Choose comparator
            ShapeComparator comparator = new ShapeComparator(compareType);

            long start = System.currentTimeMillis();

            // Choose sorting algorithm
            switch (sortType) {

                case 'b':
                    SortingUtility.bubbleSort(shapes, comparator);
                    break;

                case 's':
                    SortingUtility.selectionSort(shapes, comparator);
                    break;

                case 'i':
                    SortingUtility.insertionSort(shapes, comparator);
                    break;

                case 'm':
                    SortingUtility.mergeSort(shapes, comparator);
                    break;

                case 'q':
                    SortingUtility.quickSort(shapes, comparator);
                    break;

                case 'z':
                    SortingUtility.mySort(shapes, comparator);
                    break;
            }

            long end = System.currentTimeMillis();

         // Print first element
         System.out.printf("%-18s %-30s%n", "First element is:", formatShape(shapes[0], compareType));

         // Print every 1000th element in between
         for (int i = 999; i < shapes.length - 1; i += 1000) {
        	  System.out.printf("%-18s %-30s%n", (i + 1) + "-th element:", formatShape(shapes[i], compareType));
         }

         // Print last element
         System.out.printf("%-18s %-30s%n", "Last element is:", formatShape(shapes[shapes.length - 1], compareType));

         // Print sorting time
         String sortName = getSortName(sortType);
         System.out.println(sortName + " run time was: " + (end - start) + " milliseconds");

        } catch (Exception e) {
            System.out.println("Error reading file.");
        }
        
    }
	
	private static String formatShape(Shape shape, char compareType) {
	    switch (compareType) {
	        case 'a':
	            return shape.getClass().getSimpleName() + " Area: " + shape.calcBaseArea();
	        case 'v':
	            return shape.getClass().getSimpleName() + " Volume: " + shape.calcVolume();
	        case 'h':
	        default:
	            return shape.getClass().getSimpleName() + " Height: " + shape.getHeight();
	    }
	}
	
	private static String getSortName(char sortType) {
	    switch (sortType) {
	        case 'b': return "Bubble Sort";
	        case 's': return "Selection Sort";
	        case 'i': return "Insertion Sort";
	        case 'm': return "Merge Sort";
	        case 'q': return "Quick Sort";
	        case 'z': return "Heap Sort";
	        default:  return "Unknown Sort";
	    }
	}
}
