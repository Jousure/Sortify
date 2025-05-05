package com.example.sortify;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SortAdapter extends RecyclerView.Adapter<SortAdapter.ViewHolder> {

    private Context context;
    private ArrayList<SortingAlgorithm> sortingList;

    public SortAdapter(Context context, ArrayList<SortingAlgorithm> sortingList) {
        this.context = context;
        this.sortingList = sortingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sort, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SortingAlgorithm sortingAlgorithm = sortingList.get(position);

        // Set title and description
        holder.textTitle.setText(sortingAlgorithm.getName());
        holder.textDescription.setText(sortingAlgorithm.getDescription());

        // Set dynamic image for each sorting algorithm
        holder.imageSort.setImageResource(getSortImage(sortingAlgorithm.getName()));

        // Alternating background colors for better UI
        int colorRes = (position % 2 == 0) ? R.color.soft_teal : R.color.soft_pink;
        holder.itemView.setBackgroundColor(ContextCompat.getColor(context, colorRes));

        // Click event to open DetailActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("sortName", sortingAlgorithm.getName());
            intent.putExtra("sortDescription", sortingAlgorithm.getDescription());
            intent.putExtra("sortCode", sortingAlgorithm.getCode());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return sortingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textDescription;
        ImageView imageSort;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.sortTitle);
            textDescription = itemView.findViewById(R.id.sortDescription);
            imageSort = itemView.findViewById(R.id.sortImage);
        }
    }

    // Method to return correct image based on sorting name
    private int getSortImage(String name) {
        switch (name) {
            case "Bubble Sort":
                return R.drawable.bubble_sort;
            case "Selection Sort":
                return R.drawable.selection_sort;
            case "Insertion Sort":
                return R.drawable.insertion_sort;
            case "Merge Sort":
                return R.drawable.merge_sort;
            case "Quick Sort":
                return R.drawable.quick_sort;
            case "Heap Sort":
                return R.drawable.heap_sort;
            default:
                return R.drawable.default_image;
        }
    }
}










