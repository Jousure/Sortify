package com.example.sortify;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SortingDisplay extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SortAdapter adapter;
    private ArrayList<SortingAlgorithm> sortingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_sorting);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sortingList = new ArrayList<>();
        sortingList.add(new SortingAlgorithm("Bubble Sort",
                "A simple sorting algorithm that repeatedly swaps adjacent elements if they are in the wrong order. Larger elements \"bubble up\" to their correct positions.\n" +
                        "\n" +
                        "Main Features:\n" +
                        "Time Complexity: Worst & Average: O(n²), Best: O(n) (adaptive).\n" +
                        "Space Complexity: O(1) (in-place).\n" +
                        "Stable & adaptive but inefficient for large datasets.",
                "public void bubbleSort(int[] arr) {\n" +
                        "    for (int i = 0; i < arr.length - 1; i++) {\n" +
                        "        for (int j = 0; j < arr.length - i - 1; j++) {\n" +
                        "            if (arr[j] > arr[j + 1]) {\n" +
                        "                int temp = arr[j];\n" +
                        "                arr[j] = arr[j + 1];\n" +
                        "                arr[j + 1] = temp;\n" +
                        "            }\n" +
                        "        }\n" +
                        "    }\n" +
                        "}"));

        sortingList.add(new SortingAlgorithm("Selection Sort",
                "Repeatedly finds the minimum element and places it at the beginning of the unsorted portion.\n" +
                        "\n" +
                        "Main Features:\n" +
                        "Time Complexity: Best, Worst, and Average: O(n²).\n" +
                        "Space Complexity: O(1).\n" +
                        "Not stable, fewer swaps than Bubble Sort, good for small lists.",
                "public void selectionSort(int[] arr) {\n" +
                        "    for (int i = 0; i < arr.length - 1; i++) {\n" +
                        "        int minIdx = i;\n" +
                        "        for (int j = i + 1; j < arr.length; j++) {\n" +
                        "            if (arr[j] < arr[minIdx]) {\n" +
                        "                minIdx = j;\n" +
                        "            }\n" +
                        "        }\n" +
                        "        int temp = arr[minIdx];\n" +
                        "        arr[minIdx] = arr[i];\n" +
                        "        arr[i] = temp;\n" +
                        "    }\n" +
                        "}"));

        sortingList.add(new SortingAlgorithm("Insertion Sort",
                "Builds a sorted array one element at a time by inserting elements into their correct position.\n" +
                        "\n" +
                        "Main Features:\n" +
                        "Time Complexity: Worst & Average: O(n²), Best: O(n).\n" +
                        "Space Complexity: O(1).\n" +
                        "Stable, efficient for small/nearly sorted datasets, and online sorting.",
                "public void insertionSort(int[] arr) {\n" +
                        "    for (int i = 1; i < arr.length; i++) {\n" +
                        "        int key = arr[i];\n" +
                        "        int j = i - 1;\n" +
                        "        while (j >= 0 && arr[j] > key) {\n" +
                        "            arr[j + 1] = arr[j];\n" +
                        "            j = j - 1;\n" +
                        "        }\n" +
                        "        arr[j + 1] = key;\n" +
                        "    }\n" +
                        "}"));

        sortingList.add(new SortingAlgorithm("Merge Sort",
                "A divide-and-conquer algorithm that splits, sorts, and merges subarrays.\n" +
                        "\n" +
                        "Main Features:\n" +
                        "Time Complexity: Best, Worst, and Average: O(n log n).\n" +
                        "Space Complexity: O(n) (extra space required).\n" +
                        "Stable, efficient for large datasets, works well with linked lists.",
                "public void mergeSort(int[] arr, int l, int r) {\n" +
                        "    if (l < r) {\n" +
                        "        int m = (l + r) / 2;\n" +
                        "        mergeSort(arr, l, m);\n" +
                        "        mergeSort(arr, m + 1, r);\n" +
                        "        merge(arr, l, m, r);\n" +
                        "    }\n" +
                        "}\n\n" +
                        "public void merge(int[] arr, int l, int m, int r) {\n" +
                        "    int n1 = m - l + 1;\n" +
                        "    int n2 = r - m;\n" +
                        "    int[] L = new int[n1];\n" +
                        "    int[] R = new int[n2];\n" +
                        "    for (int i = 0; i < n1; i++)\n" +
                        "        L[i] = arr[l + i];\n" +
                        "    for (int j = 0; j < n2; j++)\n" +
                        "        R[j] = arr[m + 1 + j];\n" +
                        "    int i = 0, j = 0, k = l;\n" +
                        "    while (i < n1 && j < n2) {\n" +
                        "        if (L[i] <= R[j]) {\n" +
                        "            arr[k] = L[i];\n" +
                        "            i++;\n" +
                        "        } else {\n" +
                        "            arr[k] = R[j];\n" +
                        "            j++;\n" +
                        "        }\n" +
                        "        k++;\n" +
                        "    }\n" +
                        "    while (i < n1) {\n" +
                        "        arr[k] = L[i];\n" +
                        "        i++;\n" +
                        "        k++;\n" +
                        "    }\n" +
                        "    while (j < n2) {\n" +
                        "        arr[k] = R[j];\n" +
                        "        j++;\n" +
                        "        k++;\n" +
                        "    }\n" +
                        "}"));

        sortingList.add(new SortingAlgorithm("Quick Sort",
                "Selects a pivot, partitions the array, and recursively sorts subarrays.\n" +
                        "\n" +
                        "Main Features:\n" +
                        "Time Complexity: Worst: O(n²), Average & Best: O(n log n).\n" +
                        "Space Complexity: O(log n) (in-place).\n" +
                        "Not stable, very fast in practice, used in system libraries..",
                "public void quickSort(int[] arr, int low, int high) {\n" +
                        "    if (low < high) {\n" +
                        "        int pi = partition(arr, low, high);\n" +
                        "        quickSort(arr, low, pi - 1);\n" +
                        "        quickSort(arr, pi + 1, high);\n" +
                        "    }\n" +
                        "}\n\n" +
                        "public int partition(int[] arr, int low, int high) {\n" +
                        "    int pivot = arr[high];\n" +
                        "    int i = (low - 1);\n" +
                        "    for (int j = low; j < high; j++) {\n" +
                        "        if (arr[j] < pivot) {\n" +
                        "            i++;\n" +
                        "            int temp = arr[i];\n" +
                        "            arr[i] = arr[j];\n" +
                        "            arr[j] = temp;\n" +
                        "        }\n" +
                        "    }\n" +
                        "    int temp = arr[i + 1];\n" +
                        "    arr[i + 1] = arr[high];\n" +
                        "    arr[high] = temp;\n" +
                        "    return i + 1;\n" +
                        "}"));

        sortingList.add(new SortingAlgorithm("Heap Sort",
                "Converts an array into a binary heap and repeatedly extracts the maximum/minimum element.\n" +
                        "\n" +
                        "Main Features:\n" +
                        "Time Complexity: Best, Worst, and Average: O(n log n).\n" +
                        "Space Complexity: O(1) (in-place).\n" +
                        "Not stable, efficient for large datasets, used in priority queues.",
                "public void heapSort(int arr[]) {\n" +
                        "    int n = arr.length;\n" +
                        "    for (int i = n / 2 - 1; i >= 0; i--)\n" +
                        "        heapify(arr, n, i);\n" +
                        "    for (int i = n - 1; i > 0; i--) {\n" +
                        "        int temp = arr[0];\n" +
                        "        arr[0] = arr[i];\n" +
                        "        arr[i] = temp;\n" +
                        "        heapify(arr, i, 0);\n" +
                        "    }\n" +
                        "}\n\n" +
                        "public void heapify(int arr[], int n, int i) {\n" +
                        "    int largest = i;\n" +
                        "    int left = 2 * i + 1;\n" +
                        "    int right = 2 * i + 2;\n" +
                        "    if (left < n && arr[left] > arr[largest])\n" +
                        "        largest = left;\n" +
                        "    if (right < n && arr[right] > arr[largest])\n" +
                        "        largest = right;\n" +
                        "    if (largest != i) {\n" +
                        "        int swap = arr[i];\n" +
                        "        arr[i] = arr[largest];\n" +
                        "        arr[largest] = swap;\n" +
                        "        heapify(arr, n, largest);\n" +
                        "    }\n" +
                        "}"));

        adapter = new SortAdapter(this, sortingList);
        recyclerView.setAdapter(adapter);
    }
}
