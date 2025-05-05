package com.example.sortify;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private TextView textTitle, textDescription, textCode;
    private ImageView sortImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Initialize UI elements
        textTitle = findViewById(R.id.textTitle);
        textDescription = findViewById(R.id.textDescription);
        textCode = findViewById(R.id.textCode);
        sortImage = findViewById(R.id.sortImage);

        // Retrieve data from intent
        Intent intent = getIntent();
        String sortName = intent.getStringExtra("sortName");
        String sortDescription = intent.getStringExtra("sortDescription");
        String sortCode = intent.getStringExtra("sortCode");

        // Check if values are null to avoid crashes
        if (sortName != null) textTitle.setText(sortName);
        if (sortDescription != null) textDescription.setText(sortDescription);
        if (sortCode != null) textCode.setText(sortCode);

        // Prepend "Code" at the top inside textCode
        if (sortCode != null) {
            String formattedCode = "CODE:\n\n" + sortCode;
            textCode.setText(formattedCode);
        }

        // Set default image (change this based on sorting type)
        sortImage.setImageResource(R.drawable.default_image2);

        // Apply color styling programmatically (optional)
        textTitle.setTextColor(Color.parseColor("#61C0BF"));  // Deep teal
        textDescription.setTextColor(Color.parseColor("#61C0BF"));
        textCode.setTextColor(Color.parseColor("#DE3163"));  //pink
    }
}










//package com.example.display2;
//
//
//import android.os.Bundle;
//import android.widget.TextView;
//import androidx.appcompat.app.AppCompatActivity;
//
//public class DetailActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detail);
//
//        TextView textTitle = findViewById(R.id.textTitle);
//        TextView textDescription = findViewById(R.id.textDescription);
//        TextView textCode = findViewById(R.id.textCode);
//
//        // Get data from intent
//        String sortName = getIntent().getStringExtra("sortName");
//        String sortDescription = getIntent().getStringExtra("sortDescription");
//        String sortCode = getIntent().getStringExtra("sortCode");
//
//        // Display data
//        textTitle.setText(sortName);
//        textDescription.setText(sortDescription);
//        textCode.setText(sortCode);
//    }
//}
