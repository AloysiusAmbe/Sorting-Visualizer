import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class SortingAlgorithms {

    // Specified delay for each algorithm
    private static int bubbleSortDelay = 20;
    private static int mergeSortDelay = 2;
    private static int quickSortDelay = 2;
    private static int selectionSortDelay = 10;
    private static int insertionSortDelay = 10;
    private static int heapSortDelay = 2;

    // Bubble Sort
    public void bubbleSort(int[] arr) {
        int arraySize = arr.length;
        boolean swapped = true;
        for (int i = 0; i < arraySize-1; i++) {
            swapped = false;
            delay(bubbleSortDelay);
            for (int j = 0; j < arraySize - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // swap arr[j+1] and arr[i]
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            // Exists is no swap occurs
            if (!swapped) {
                return;
            }
        }
    }


    /* MERGE SORT */

    // Merges two subarrays of arr[].
    // First subarray is arr[l..middle]
    // Second subarray is arr[middle+1..right]
    private static void merge(int arr[], int left, int middle, int right) {
        // Find sizes of two subarrays to be merged
        int n1 = middle - left + 1;
        int n2 = right - middle;

        // Create temp arrays
        int L[] = new int[n1];
        int R[] = new int[n2];

        // Copy data to temp arrays
        for (int i = 0; i < n1; i++)
            L[i] = arr[left + i];
        for (int j = 0; j < n2; j++)
            R[j] = arr[middle + 1 + j];

        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarry array
        int k = left;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            }
            else {
                arr[k] = R[j];
                j++;
            }
            k++;
            delay(mergeSortDelay);
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
            delay(mergeSortDelay);
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
            delay(mergeSortDelay);
        }

    }

    // Main function that sorts arr[l..r] using merge()
    public void mergeSort(int arr[], int left, int right) {
        if (left < right) {
            // Find the middle point
            int middle = (left + right) / 2;

            // Sort first and second halves
            mergeSort(arr, left, middle);
            mergeSort(arr, middle + 1, right);

            // Merge the sorted halves
            merge(arr, left, middle, right);
        }
    }
    /* MERGE SORT END */


    /* QUICK SORT */
    private static int partition(int arr[], int low, int high) {
        int pivot = arr[high];
        int i = (low - 1); // index of smaller element
        for (int j = low; j < high; j++) {
            // If current element is smaller than the pivot
            if (arr[j] < pivot) {
                i++;

                // swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
            delay(quickSortDelay);
        }

        // swap arr[i+1] and arr[high] (or pivot)
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }


    /* The main function that implements QuickSort()
      arr[] --> Array to be sorted,
      low  --> Starting index,
      high  --> Ending index */
    public void quickSort(int arr[], int low, int high) {
        if (low < high) {
            /* pi is partitioning index, arr[pi] is
              now at right place */
            int pi = partition(arr, low, high);

            // Recursively sort elements before
            // partition and after partition
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }
    /* QUICK SORT END */


    /* SELECTION SORT */
    public void selectionSort(int arr[]) {
        int arraySize = arr.length;

        // One by one move boundary of unsorted subarray
        for (int i = 0; i < arraySize - 1; i++) {
            delay(selectionSortDelay);
            // Find the minimum element in unsorted array
            int min = i;
            for (int j = i + 1; j < arraySize; j++) {
                if (arr[j] < arr[min]) {
                    min = j;
                }
            }

            // Swap the found minimum element with the first element
            int temp = arr[min];
            arr[min] = arr[i];
            arr[i] = temp;
        }
    }

    /* INSERTION SORT */
    public void insertionSort(int arr[]) {
        int arraySize = arr.length;
        for (int i = 1; i < arraySize; i++) {
            int key = arr[i];
            int j = i - 1;

            /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
            delay(insertionSortDelay);
        }
    }


    /* HEAP SORT */
    public void heapSort(int arr[]) {
        int arraySize = arr.length;

        // Build heap (rearrange array)
        for (int i = arraySize / 2 - 1; i >= 0; i--) {
            heapify(arr, arraySize, i);
        }

        // One by one extract an element from heap
        for (int i = arraySize - 1; i > 0; i--) {
            delay(heapSortDelay);
            // Move current root to end
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // call max heapify on the reduced heap
            heapify(arr, i, 0);
        }
    }

    // To heapify a subtree rooted with node i which is
    // an index in arr[]. n is size of heap
    private static void heapify(int arr[], int n, int i) {
        int largest = i; // Initialize largest as root
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        // If left child is larger than root
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        // If right child is larger than largest so far
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        // If largest is not root
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            delay(heapSortDelay);

            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest);
        }
    }

    // Delays the program
    private static void delay(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
