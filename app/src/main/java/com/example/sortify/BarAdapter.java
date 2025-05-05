package com.example.sortify;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class BarAdapter extends RecyclerView.Adapter<BarAdapter.BarViewHolder> {

    private final List<BarModel> barList;

    public BarAdapter(List<BarModel> barList) {
        this.barList = barList;
    }

    @NonNull
    @Override
    public BarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bar, parent, false);
        return new BarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BarViewHolder holder, int position) {
        BarModel bar = barList.get(position);

        int value = bar.getValue(); // Use bar model's value

        // Ensure we have a valid max value
        int maxValue = Collections.max(getBarValues());
        if (maxValue == 0) maxValue = 1; // Prevent division by zero

        // Get the container height
        int containerHeight = 900;

        // Calculate proportional height
        float scaleFactor = (float) containerHeight / maxValue;
        int barHeight = (int) (value * scaleFactor);


        int minHeight = 20;
        barHeight = Math.max(barHeight, minHeight);


        ViewGroup.LayoutParams layoutParams = holder.barView.getLayoutParams();
        layoutParams.height = barHeight;
        holder.barView.setLayoutParams(layoutParams);
        holder.barView.setBackgroundColor(bar.getColor());
        holder.tvBarValue.setText(String.valueOf(value));

        // Fix bottom margin crash issue
        if (holder.tvBarValue.getLayoutParams() instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams textParams = (LinearLayout.LayoutParams) holder.tvBarValue.getLayoutParams();
            textParams.bottomMargin = 10; // Small gap between the number and the bar
            holder.tvBarValue.setLayoutParams(textParams);
        }
    }

    @Override
    public int getItemCount() {
        return barList.size();
    }

    // Helper function to extract values from barList
    private List<Integer> getBarValues() {
        List<Integer> values = new java.util.ArrayList<>();
        for (BarModel bar : barList) {
            values.add(bar.getValue());
        }
        return values;
    }

    public static class BarViewHolder extends RecyclerView.ViewHolder {
        View barView;
        TextView tvBarValue;

        public BarViewHolder(@NonNull View itemView) {
            super(itemView);
            barView = itemView.findViewById(R.id.barView);
            tvBarValue = itemView.findViewById(R.id.tvBarValue);
        }
    }
}
