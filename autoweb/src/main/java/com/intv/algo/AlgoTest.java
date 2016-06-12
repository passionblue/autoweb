package com.intv.algo;

import java.util.TreeSet;

public class AlgoTest {

    public static void main(String[] args) {
        
        try {
            binarySearchTest();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("xx");
        TreeSet s = new TreeSet();

    }

    
    public static void binarySearchTest(){
        
        int org[] = new int[] {1,2,3,4,7,10,12,14,18};
        System.out.println(BinarySearch.binarySearch( org, 13, 0, org.length-1));
        
        int org2[] = new int[] {1,4,3,7,10,5,34,2};
        MySort.bubbleSort(org2);
        printArray(org2);
        
        org2 = new int[] {1,4,3,7,10,5,34,2};
        
        MySort.selectionSort(org2);
        printArray(org2);
        
        
        MySort.quickSort(org2,0, org2.length-1);
        printArray(org2);
        
        throw new RuntimeException();
    }
    
    
    public static void printArray(int a[]){
        
        for (int i = 0; i < a.length; i++){
            System.out.print(a[i] + " - ");
        }
        System.out.println("");
    }
    
}

class BinarySearch {
    public static int binarySearch(int[] array, int value, int left, int right) {

        if (left > right)

            return -1;

        int middle = (left + right) / 2;

        if (array[middle] == value)

            return middle;

        else if (array[middle] > value)

            return binarySearch(array, value, left, middle - 1);

        else

            return binarySearch(array, value, middle + 1, right);

    }
}


class MySort {

    public static int partition(int arr[], int left, int right)

    {

          int i = left, j = right;

          int tmp;

          int pivot = arr[(left + right) / 2];

         

          while (i <= j) {

                while (arr[i] < pivot)

                      i++;

                while (arr[j] > pivot)

                      j--;

                if (i <= j) {

                      tmp = arr[i];

                      arr[i] = arr[j];

                      arr[j] = tmp;

                      i++;

                      j--;

                }

          };

         

          return i;

    }

     

    public static void quickSort(int arr[], int left, int right) {

          int index = partition(arr, left, right);

          if (left < index - 1)

                quickSort(arr, left, index - 1);

          if (index < right)

                quickSort(arr, index, right);

    }
    
    public static void selectionSort(int[] arr) {

        int i, j, minIndex, tmp;

        int n = arr.length;

        for (i = 0; i < n - 1; i++) {

              minIndex = i;

              for (j = i + 1; j < n; j++)

                    if (arr[j] < arr[minIndex])

                          minIndex = j;

              if (minIndex != i) {

                    tmp = arr[i];

                    arr[i] = arr[minIndex];

                    arr[minIndex] = tmp;

              }

        }

  }
    public static void bubbleSort(int[] arr) {

        boolean swapped = true;

        int j = 0;

        int tmp;

        while (swapped) {

              swapped = false;

              j++;

              for (int i = 0; i < arr.length - j; i++) {                                       

                    if (arr[i] > arr[i + 1]) {                          

                          tmp = arr[i];

                          arr[i] = arr[i + 1];

                          arr[i + 1] = tmp;

                          swapped = true;

                    }

              }                

        }

  }
}