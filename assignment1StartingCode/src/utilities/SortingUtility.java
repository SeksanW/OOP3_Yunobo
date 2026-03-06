/*
 * 
 * Algorithm sorting:
 * Bubble: 		Goes through the array comparing two numbers at a time. If they're in the wrong order, swap them. Keeps doing this until everything is sorted. Like bubbling the biggest items to the front.
 * Selection: 	Scans the whole array to find the biggest item, puts it at the front. Then scans the rest to find the next biggest, puts it second. Repeats until done.
 * Insertion:	Takes one number at a time and slides it into the correct position among the already sorted items.
 * Merge:		Splits the array in half, sorts each half, then merges them back together. It keeps splitting until each piece is just one item, then starts merging back up.
 * Quick:		Picks a "pivot" value (using median of three to pick a good one), then puts everything bigger than the pivot on one side and everything smaller on the other. Then repeats that process on each side.
 * 
 * Heap:		phase 1 Build:Sets up each number in a bracket and where the bigger number wins and moves up the bracket till the program finds the number that is the largest and setting the top of the bracket as index 0, top to bottom, left to right.
 * 				phase 2 Extract:Swaps index 0 with last index and locks the last position that was swapped, then redo the heap with the left over indexes, rinse and repeat till all index are locked and the sorting oder will be correct.
*/
package utilities;

import java.util.Comparator;
import shapes.Shape;

public class SortingUtility {
	
	// Bubble sort
    public static void bubbleSort(Shape[] shapes, Comparator<Shape> comp) {
        int n = shapes.length;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (comp.compare(shapes[j], shapes[j + 1]) < 0) {
                    swap(shapes, j, j + 1);
                }
            }
        }
    }
    
    // Selection Sort
    public static void selectionSort(Shape[] shapes, Comparator<Shape> comp) {
        int n = shapes.length;

        for (int i = 0; i < n - 1; i++) {
            int maxIndex = i;

            for (int j = i + 1; j < n; j++) {
                if (comp.compare(shapes[j], shapes[maxIndex]) > 0) {
                    maxIndex = j;
                }
            }

            swap(shapes, i, maxIndex);
        }
    }
    
    // Insert Sort
    public static void insertionSort(Shape[] shapes, Comparator<Shape> comp) {
        for (int i = 1; i < shapes.length; i++) {
            Shape key = shapes[i];
            int j = i - 1;

            while (j >= 0 && comp.compare(shapes[j], key) < 0) {
                shapes[j + 1] = shapes[j];
                j--;
            }

            shapes[j + 1] = key;
        }
    }
    
    // Merge Sort
    public static void mergeSort(Shape[] shapes, Comparator<Shape> comp) {
        mergeSortHelper(shapes, 0, shapes.length - 1, comp);
    }

    private static void mergeSortHelper(Shape[] shapes, int left, int right, Comparator<Shape> comp) {
        if (left < right) {
            int mid = (left + right) / 2;

            mergeSortHelper(shapes, left, mid, comp);
            mergeSortHelper(shapes, mid + 1, right, comp);
            merge(shapes, left, mid, right, comp);
        }
    }
    
    private static void merge(Shape[] shapes, int left, int mid, int right, Comparator<Shape> comp) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Shape[] leftArray = new Shape[n1];
        Shape[] rightArray = new Shape[n2];

        for (int i = 0; i < n1; i++) {
            leftArray[i] = shapes[left + i];
        }

        for (int j = 0; j < n2; j++) {
            rightArray[j] = shapes[mid + 1 + j];
        }

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            if (comp.compare(leftArray[i], rightArray[j]) >= 0) {
                shapes[k++] = leftArray[i++];
            } else {
                shapes[k++] = rightArray[j++];
            }
        }

        while (i < n1) {
            shapes[k++] = leftArray[i++];
        }

        while (j < n2) {
            shapes[k++] = rightArray[j++];
        }
    }
    
    // Quick sort
    public static void quickSort(Shape[] shapes, Comparator<Shape> comp) {
        quickSortHelper(shapes, 0, shapes.length - 1, comp);
    }

    private static void quickSortHelper(Shape[] shapes, int left, int right, Comparator<Shape> comp) {
        if (left >= right) {
            return;
        }

        int pivotIndex = medianOfThree(shapes, left, right, comp);
        swap(shapes, pivotIndex, right);

        int partitionIndex = partition(shapes, left, right, comp);

        quickSortHelper(shapes, left, partitionIndex - 1, comp);
        quickSortHelper(shapes, partitionIndex + 1, right, comp);
    }

    private static int medianOfThree(Shape[] shapes, int left, int right, Comparator<Shape> comp) {
        int mid = (left + right) / 2;

        // Order left, mid, right in ascending relation
        if (comp.compare(shapes[left], shapes[mid]) > 0) {
            swap(shapes, left, mid);
        }

        if (comp.compare(shapes[left], shapes[right]) > 0) {
            swap(shapes, left, right);
        }

        if (comp.compare(shapes[mid], shapes[right]) > 0) {
            swap(shapes, mid, right);
        }

        return mid;
    }

    private static int partition(Shape[] shapes, int left, int right, Comparator<Shape> comp) {
        Shape pivot = shapes[right];
        int i = left - 1;

        for (int j = left; j < right; j++) {
            if (comp.compare(shapes[j], pivot) >= 0) {
                i++;
                swap(shapes, i, j);
            }
        }

        swap(shapes, i + 1, right);
        return i + 1;
    }
    
    //Heap short
    public static void mySort(Shape[] shapes, Comparator<Shape> comp) {
        int n = shapes.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(shapes, n, i, comp);
        }

        for (int i = n - 1; i > 0; i--) {
            swap(shapes, 0, i);
            heapify(shapes, i, 0, comp);
        }
    }

    private static void heapify(Shape[] shapes, int n, int i, Comparator<Shape> comp) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && comp.compare(shapes[left], shapes[largest]) > 0) {
            largest = left;
        }

        if (right < n && comp.compare(shapes[right], shapes[largest]) > 0) {
            largest = right;
        }

        if (largest != i) {
            swap(shapes, i, largest);
            heapify(shapes, n, largest, comp);
        }
    }

    private static void swap(Shape[] shapes, int i, int j) {
        Shape temp = shapes[i];
        shapes[i] = shapes[j];
        shapes[j] = temp;
    }
}