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

public class SortingUtility {
	
	// Bubble sort
    public static <T> void bubbleSort(T[] arr, Comparator<? super T> comp) {
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (comp.compare(arr[j], arr[j + 1]) < 0) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }
    
    // Selection Sort
    public static <T> void selectionSort(T[] arr, Comparator<? super T> comp) {
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            int maxIndex = i;

            for (int j = i + 1; j < n; j++) {
                if (comp.compare(arr[j], arr[maxIndex]) > 0) {
                    maxIndex = j;
                }
            }

            swap(arr, i, maxIndex);
        }
    }
    
    // Insert Sort
    public static <T> void insertionSort(T[] arr, Comparator<? super T> comp) {
        for (int i = 1; i < arr.length; i++) {
            T key = arr[i];
            int j = i - 1;

            while (j >= 0 && comp.compare(arr[j], key) < 0) {
                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = key;
        }
    }
    
    // Merge Sort
    public static <T> void mergeSort(T[] arr, Comparator<? super T> comp) {
        mergeSortHelper(arr, 0, arr.length - 1, comp);
    }

    private static <T>void mergeSortHelper(T[] arr, int left, int right, Comparator<? super T> comp) {
        if (left < right) {
            int mid = (left + right) / 2;

            mergeSortHelper(arr, left, mid, comp);
            mergeSortHelper(arr, mid + 1, right, comp);
            merge(arr, left, mid, right, comp);
        }
    }
    
    private static <T> void merge(T[] arr, int left, int mid, int right, Comparator<? super T> comp) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Object[] leftArray = new Object[n1];
        Object[] rightArray = new Object[n2];

        for (int i = 0; i < n1; i++) {
            leftArray[i] = arr[left + i];
        }

        for (int j = 0; j < n2; j++) {
            rightArray[j] = arr[mid + 1 + j];
        }

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            @SuppressWarnings("unchecked")
            T leftVal = (T) leftArray[i];
            @SuppressWarnings("unchecked")
            T rightVal = (T) rightArray[j];
            
            if (comp.compare(leftVal, rightVal) >= 0) {
            	arr[k++] = leftVal;
            	i++;
            } else {
            	arr[k++] = rightVal;
            	j++;
            }
        }

        while (i < n1) {
        	@SuppressWarnings("unchecked")
            T leftVal = (T) leftArray[i++];
        	arr[k++] = leftVal;
        }

        while (j < n2) {
        	@SuppressWarnings("unchecked")
            T rightVal = (T) rightArray[j++];
        	arr[k++] = rightVal;
        }
    }
    
    // Quick sort
    public static <T> void quickSort(T[] arr, Comparator<? super T> comp) {
        quickSortHelper(arr, 0, arr.length - 1, comp);
    }

    private static <T>void quickSortHelper(T[] arr, int left, int right, Comparator<? super T> comp) {
        if (left >= right) {
            return;
        }

        int pivotIndex = medianOfThree(arr, left, right, comp);
        swap(arr, pivotIndex, right);

        int partitionIndex = partition(arr, left, right, comp);

        quickSortHelper(arr, left, partitionIndex - 1, comp);
        quickSortHelper(arr, partitionIndex + 1, right, comp);
    }

    private static <T> int medianOfThree(T[] arr, int left, int right, Comparator<? super T> comp) {
        int mid = (left + right) / 2;

        if (comp.compare(arr[left], arr[mid]) > 0) {
            swap(arr, left, mid);
        }

        if (comp.compare(arr[left], arr[right]) > 0) {
            swap(arr, left, right);
        }

        if (comp.compare(arr[mid], arr[right]) > 0) {
            swap(arr, mid, right);
        }

        return mid;
    }

    private static <T> int partition(T[] arr, int left, int right, Comparator<? super T> comp) {
        T pivot = arr[right];
        int i = left - 1;

        for (int j = left; j < right; j++) {
            if (comp.compare(arr[j], pivot) >= 0) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, right);
        return i + 1;
    }
    
    //Heap short
    public static <T> void heapSort(T[] arr, Comparator<? super T> comp) {
        int n = arr.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i, comp);
        }

        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);
            heapify(arr, i, 0, comp);
        }
    }

    private static <T> void heapify(T[] arr, int n, int i, Comparator<? super T> comp) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && comp.compare(arr[left], arr[largest]) > 0) {
            largest = left;
        }

        if (right < n && comp.compare(arr[right], arr[largest]) > 0) {
            largest = right;
        }

        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, n, largest, comp);
        }
    }

    private static <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}