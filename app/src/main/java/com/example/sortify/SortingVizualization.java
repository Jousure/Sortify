package com.example.sortify;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class SortingVizualization extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BarAdapter adapter;
    private Spinner spinnerSorting;
    private Button btnSort, btnGenerate;
    private TextView tvComplexity;
    private RadioGroup speedGroup;
    private List<BarModel> barList;
    private int speedDelay = 500;
    private Handler handler = new Handler();

    private Button btnPause, btnResume;
    private volatile boolean isPaused = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vizualization_sorting);

        recyclerView = findViewById(R.id.recyclerView);
        spinnerSorting = findViewById(R.id.spinnerSorting);
        btnSort = findViewById(R.id.btnSort);
        btnGenerate = findViewById(R.id.btnGenerate);
        tvComplexity = findViewById(R.id.tvComplexity);
        speedGroup = findViewById(R.id.speedGroup);
        btnPause = findViewById(R.id.btnPause);
        btnResume = findViewById(R.id.btnResume);

        btnPause.setOnClickListener(view -> isPaused = true);
        btnResume.setOnClickListener(view -> isPaused = false);


        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        generateRandomBars();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        int numBars = barList.size();  // Get the total number of bars
        int columns = numBars > 30 ? 30 : numBars; // Dynamically adjust columns

        GridLayoutManager layoutManager = new GridLayoutManager(this, columns);
        recyclerView.setLayoutManager(layoutManager);


        btnGenerate.setOnClickListener(view -> generateRandomBars());

        btnSort.setOnClickListener(view -> {
            String selectedSort = spinnerSorting.getSelectedItem().toString();
            performSorting(selectedSort);
        });

        speedGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbSlow) speedDelay = 500;
            else if (checkedId == R.id.rbMedium) speedDelay = 200;
            else if (checkedId == R.id.rbFast) speedDelay = 50;
        });
    }

    private void generateRandomBars() {
        barList = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 15; i++) {
            int value = random.nextInt(100) + 1;
//            int height = random.nextInt(500) + 100;
            int height = value * 5;
            barList.add(new BarModel(value, height, Color.parseColor("#800080")));
        }
        adapter = new BarAdapter(barList);
        recyclerView.setAdapter(adapter);
    }

    private void checkPaused() {
        while (isPaused) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void performSorting(String sortType) {
        switch (sortType) {
            case "Insertion Sort":
                tvComplexity.setText("Time: O(n²), Space: O(1)");
                new Thread(this::insertionSort).start();
                break;
            case "Merge Sort":
                tvComplexity.setText("Time: O(n log n), Space: O(n)");
                new Thread(() -> mergeSort(0, barList.size() - 1)).start();
                break;
            case "Quick Sort":
                tvComplexity.setText("Time: O(n log n), Space: O(log n)");
                new Thread(() -> quickSort(0, barList.size() - 1)).start();
                break;
            case "Heap Sort":
                tvComplexity.setText("Time: O(n log n), Space: O(1)");
                new Thread(this::heapSort).start();
                break;
            case "Bubble Sort":
                tvComplexity.setText("Time: O(n²), Space: O(1)");
                new Thread(this::bubbleSort).start();
                break;
            case "Selection Sort":
                tvComplexity.setText("Time: O(n²), Space: O(1)");
                new Thread(this::selectionSort).start();
                break;
        }
    }

//    insertion sort    
    private void insertionSort() {
        new Thread(() -> {
            int n = barList.size();

            for (int i = 1; i < n; i++) {
                checkPaused(); 

                int keyValue = barList.get(i).getValue();
                int keyHeight = barList.get(i).getHeight();
                int j = i - 1;

                // Highlight the current key 
                barList.get(i).setColor(Color.parseColor("#DC143C"));
                updateRecyclerView();
                sleep();

                while (j >= 0 && barList.get(j).getValue() > keyValue) {
                    checkPaused(); 

                    // Highlight the comparison bar
                    barList.get(j).setColor(Color.parseColor("#DC143C")); 
                    updateRecyclerView();
                    sleep();

                    // Move the larger element one step ahead
                    barList.get(j + 1).setValue(barList.get(j).getValue());
                    barList.get(j + 1).setHeight(barList.get(j).getHeight());

                    // Reset previous bar to Purple
                    barList.get(j).setColor(Color.parseColor("#800080")); 
                    updateRecyclerView();
                    sleep();

                    j--;
                }

                // Place the key in the correct position
                barList.get(j + 1).setValue(keyValue);
                barList.get(j + 1).setHeight(keyHeight);
                barList.get(j + 1).setColor(Color.parseColor("#800080")); 
                updateRecyclerView();
                sleep();
            }

            // Mark the whole array as sorted 
            for (BarModel bar : barList) {
                checkPaused(); 
                bar.setColor(Color.parseColor("#008000")); 
            }
            updateRecyclerView();

        }).start();
    }


//    merge sort
    private void mergeSort(int left, int right) {
        checkPaused(); 

        if (left < right) {
            int mid = left + (right - left) / 2;

            mergeSort(left, mid);
            mergeSort(mid + 1, right);

            merge(left, mid, right);
        }
    }

    private void merge(int left, int mid, int right) {
        checkPaused(); 

        // Create a temporary list to store values instead of full BarModel objects
        List<Integer> tempValues = new ArrayList<>();
        List<Integer> tempHeights = new ArrayList<>();

        // Copy values and heights into temp lists
        for (int i = left; i <= right; i++) {
            tempValues.add(barList.get(i).getValue());
            tempHeights.add(barList.get(i).getHeight());
        }

        int i = left, j = mid + 1, k = left;

        while (i <= mid && j <= right) {
            checkPaused(); 

            // Highlight bars being compared
            barList.get(i).setColor(Color.parseColor("#DC143C")); 
            barList.get(j).setColor(Color.parseColor("#DC143C")); 
            updateRecyclerView();
            sleep();

            if (tempValues.get(i - left) <= tempValues.get(j - left)) {
                barList.get(k).setValue(tempValues.get(i - left));
                barList.get(k).setHeight(tempHeights.get(i - left));
                i++;
            } else {
                barList.get(k).setValue(tempValues.get(j - left));
                barList.get(k).setHeight(tempHeights.get(j - left));
                j++;
            }

            // Reset color to Purple after placement
            barList.get(k).setColor(Color.parseColor("#800080")); 
            updateRecyclerView();
            sleep();

            k++;
        }

        // Copy remaining elements from the left sub-array
        while (i <= mid) {
            checkPaused(); 

            barList.get(k).setValue(tempValues.get(i - left));
            barList.get(k).setHeight(tempHeights.get(i - left));
            i++;
            k++;
            updateRecyclerView();
            sleep();
        }

        // Copy remaining elements from the right sub-array
        while (j <= right) {
            checkPaused(); 

            barList.get(k).setValue(tempValues.get(j - left));
            barList.get(k).setHeight(tempHeights.get(j - left));
            j++;
            k++;
            updateRecyclerView();
            sleep();
        }

        // Mark the merged section as sorted
        for (int x = left; x <= right; x++) {
            checkPaused(); 
            barList.get(x).setColor(Color.parseColor("#008000")); 
        }
        updateRecyclerView();
        sleep();
    }


//    quick sort
    private void quickSort(int low, int high) {
        checkPaused();

        if (low < high) {
            int pi = partition(low, high);

            // Recursively sort the two halves
            quickSort(low, pi - 1);
            quickSort(pi + 1, high);
        }
    }

    private int partition(int low, int high) {
        checkPaused(); 

        int pivotHeight = barList.get(high).getHeight();
        int i = low - 1;

        // Highlight pivot bar
        barList.get(high).setColor(Color.parseColor("#FFD700")); 
        updateRecyclerView();
        sleep();

        for (int j = low; j < high; j++) {
            checkPaused(); // Pause checking

            // Highlight bars being compared
            barList.get(j).setColor(Color.parseColor("#DC143C")); 
            updateRecyclerView();
            sleep();

            if (barList.get(j).getHeight() < pivotHeight) {
                i++;
                swap(i, j); // Using your existing swap method

                // Highlight swapped bars
                barList.get(i).setColor(Color.parseColor("#4682B4")); 
                barList.get(j).setColor(Color.parseColor("#4682B4")); 
                updateRecyclerView();
                sleep();
            }

            // Reset color to default after checking
            barList.get(j).setColor(Color.parseColor("#800080")); 
        }

        // Swap pivot into correct position
        swap(i + 1, high); // Using your existing swap method

        // Mark pivot as sorted 
        barList.get(i + 1).setColor(Color.parseColor("#008000"));
        updateRecyclerView();
        sleep();

        return i + 1;
    }


//    heap sort
    private void heapSort() {
        int n = barList.size();

        // Build max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            checkPaused(); 
            heapify(n, i);
        }

        // Extract elements from heap
        for (int i = n - 1; i > 0; i--) {
            checkPaused();

            // Highlight elements being swapped
            highlightBars(0, i, "#DC143C");
            updateRecyclerView();
            sleep();

            swap(0, i);

            // Mark sorted elements in Green
            barList.get(i).setColor(Color.parseColor("#008000")); 
            updateRecyclerView();
            sleep();

            heapify(i, 0);

            // Reset root color to default
            barList.get(0).setColor(Color.parseColor("#800080")); 
            updateRecyclerView();
        }

        // Mark the last element as sorted
        barList.get(0).setColor(Color.parseColor("#008000"));
        updateRecyclerView();
    }

    /**
     * Heapify function to maintain the max heap property.
     */
    private void heapify(int n, int i) {
        checkPaused(); 

        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        // Find the largest among root, left child, and right child
        if (left < n && barList.get(left).getHeight() > barList.get(largest).getHeight()) {
            largest = left;
        }
        if (right < n && barList.get(right).getHeight() > barList.get(largest).getHeight()) {
            largest = right;
        }

        // Swap if necessary
        if (largest != i) {
            checkPaused(); 

            // Highlight elements being swapped
            highlightBars(i, largest, "#DC143C");
            updateRecyclerView();
            sleep();

            swap(i, largest);

            // Reset swapped bars to default color
            highlightBars(i, largest, "#800080"); 
            updateRecyclerView();
            sleep();

            // Recursively heapify the affected subtree
            heapify(n, largest);
        }
    }

    //Utility function to highlight two bars with a specific color.
    private void highlightBars(int index1, int index2, String color) {
        barList.get(index1).setColor(Color.parseColor(color));
        barList.get(index2).setColor(Color.parseColor(color));
    }



//    bubble sort
    private void bubbleSort() {
        int n = barList.size();
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false; 

            for (int j = 0; j < n - i - 1; j++) {
                checkPaused(); 

                // Mark elements being compared as Crimson
                barList.get(j).setColor(Color.parseColor("#DC143C")); 
                barList.get(j + 1).setColor(Color.parseColor("#DC143C")); 
                updateRecyclerView();
                sleep();

                if (barList.get(j).getValue() > barList.get(j + 1).getValue()) { 
                    // Swap values
                    int tempValue = barList.get(j).getValue();
                    barList.get(j).setValue(barList.get(j + 1).getValue());
                    barList.get(j + 1).setValue(tempValue);

                    // Swap heights (to match values)
                    int tempHeight = barList.get(j).getHeight();
                    barList.get(j).setHeight(barList.get(j + 1).getHeight());
                    barList.get(j + 1).setHeight(tempHeight);

                    swapped = true; 
                    updateRecyclerView();
                    sleep();
                }

                // Revert colors back to Purple after comparison
                barList.get(j).setColor(Color.parseColor("#800080")); 
                barList.get(j + 1).setColor(Color.parseColor("#800080")); 
                updateRecyclerView();
            }

            // Mark last sorted element as Green
            barList.get(n - i - 1).setColor(Color.parseColor("#008000")); 
            updateRecyclerView();
            sleep();

            if (!swapped) break; 
        }

        // Mark the first element as Green after sorting completes
        barList.get(0).setColor(Color.parseColor("#008000")); 
        updateRecyclerView();
    }



//    selection sort
    private void selectionSort() {
        int n = barList.size();

        for (int i = 0; i < n - 1; i++) {
            checkPaused(); 
            int minIndex = i;

            // Highlight the assumed minimum element
            barList.get(minIndex).setColor(Color.parseColor("#DC143C")); 
            updateRecyclerView();
            sleep();

            for (int j = i + 1; j < n; j++) {
                checkPaused(); 

                // Highlight comparison
                barList.get(j).setColor(Color.parseColor("#DC143C")); 
                updateRecyclerView();
                sleep();

                if (barList.get(j).getHeight() < barList.get(minIndex).getHeight()) {
                    // Reset previous minIndex color
                    barList.get(minIndex).setColor(Color.parseColor("#800080")); 
                    updateRecyclerView();

                    // Update minIndex
                    minIndex = j;

                    // Highlight new minimum
                    barList.get(minIndex).setColor(Color.parseColor("#DC143C")); 
                    updateRecyclerView();
                } else {
                    // Reset current bar color after comparison
                    barList.get(j).setColor(Color.parseColor("#800080")); 
                    updateRecyclerView();
                }
            }

            // Swap and reset colors only if necessary
            if (minIndex != i) {
                swap(i, minIndex);
            }

            // Mark the sorted element
            barList.get(i).setColor(Color.parseColor("#008000")); 
            updateRecyclerView();
            sleep();
        }

        // Mark the last element as sorted
        barList.get(n - 1).setColor(Color.parseColor("#008000")); 
        updateRecyclerView();
    }




    private void swap(int i, int j) {
        Collections.swap(barList, i, j);
        updateRecyclerView();
        sleep();
    }

    private void updateRecyclerView() {
        handler.post(() -> adapter.notifyDataSetChanged());
    }

    private void sleep() {
        try {
            Thread.sleep(speedDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}








