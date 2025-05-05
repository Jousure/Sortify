package com.example.sortify;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class AdvancedQuizActivity extends AppCompatActivity {

    private TextView questionText;
    private RadioGroup optionsGroup;
    private Button btnNext;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private TextToSpeech textToSpeech;

    private String[] questions = {
            "Which sorting algorithm is best suited for sorting linked lists?",
            "Which sorting algorithm is NOT in-place?",
            "Which sorting algorithm has the worst-case time complexity of O(n log n)?",
            "Which sorting algorithm is NOT a comparison-based sorting algorithm?",
            "Which sorting algorithm is used in Python’s built-in sorted() function?"
    };

    private String[][] options = {
            {"Quick Sort", "Merge Sort", "Bubble Sort", "Heap Sort"},
            {"Quick Sort", "Merge Sort", "Heap Sort", "Selection Sort"},
            {"Quick Sort", "Merge Sort", "Heap Sort", "Counting Sort"},
            {"Quick Sort", "Counting Sort", "Merge Sort", "Selection Sort"},
            {"Quick Sort", "Merge Sort", "Timsort", "Heap Sort"}
    };

    private int[] correctAnswers = {1, 1, 2, 1, 2};

    private String[] explanations = {

            "Merge Sort is best suited for linked lists because it does not require random access to elements.",
            "Merge Sort is NOT an in-place sorting algorithm since it requires additional space for merging.",
            "Heap Sort has the worst-case time complexity of O(n log n), which is guaranteed for all cases.",
            "Counting Sort is NOT a comparison-based algorithm, as it works by counting occurrences of elements.",
            "Python's built-in sorted() function uses Timsort, a hybrid of Merge Sort and Insertion Sort."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_quiz);

        questionText = findViewById(R.id.questionText);
        optionsGroup = findViewById(R.id.optionsGroup);
        btnNext = findViewById(R.id.btn_next);

        // Initialize TextToSpeech
        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.setLanguage(Locale.US);
            }
        });

        loadQuestion();

        btnNext.setOnClickListener(view -> checkAnswer());
    }

    private void loadQuestion() {
        if (currentQuestionIndex < questions.length) {
            questionText.setText(questions[currentQuestionIndex]);

            ((RadioButton) optionsGroup.getChildAt(0)).setText(options[currentQuestionIndex][0]);
            ((RadioButton) optionsGroup.getChildAt(1)).setText(options[currentQuestionIndex][1]);
            ((RadioButton) optionsGroup.getChildAt(2)).setText(options[currentQuestionIndex][2]);
            ((RadioButton) optionsGroup.getChildAt(3)).setText(options[currentQuestionIndex][3]);

            optionsGroup.clearCheck();
        } else {
            showResult();
        }
    }

    private void checkAnswer() {
        int selectedId = optionsGroup.getCheckedRadioButtonId();
        if (selectedId == -1) return;

        RadioButton selectedAnswer = findViewById(selectedId);
        int answerIndex = optionsGroup.indexOfChild(selectedAnswer);

        if (answerIndex == correctAnswers[currentQuestionIndex]) {
            textToSpeech.speak("Correct!", TextToSpeech.QUEUE_FLUSH, null, null);
            showAlertWithTTS("Correct!", explanations[currentQuestionIndex]);
            score++;
        } else {
            textToSpeech.speak("Incorrect!", TextToSpeech.QUEUE_FLUSH, null, null);
            showAlertWithTTS("Incorrect!", "The correct answer is: " + options[currentQuestionIndex][correctAnswers[currentQuestionIndex]]
                    + "\n\n" + explanations[currentQuestionIndex]);
        }

        currentQuestionIndex++;

        if (currentQuestionIndex == questions.length) {
            new android.os.Handler().postDelayed(() -> {
                textToSpeech.speak(explanations[currentQuestionIndex - 1], TextToSpeech.QUEUE_ADD, null, null);
                new android.os.Handler().postDelayed(this::showResult, 5000);
            }, 2000);
        } else {
            loadQuestion();
        }
    }

    private void showAlertWithTTS(String title, String message) {
        textToSpeech.speak(message, TextToSpeech.QUEUE_ADD, null, null);

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    textToSpeech.stop();
                    dialog.dismiss();
                })
                .show();
    }

    private void showResult() {
        String finalScoreMessage = "Quiz Finished! Your Score: " + score + " out of " + questions.length;
        textToSpeech.speak(finalScoreMessage, TextToSpeech.QUEUE_FLUSH, null, null);

        new AlertDialog.Builder(this)
                .setTitle("Quiz Finished!")
                .setMessage(finalScoreMessage)
                .setPositiveButton("Go to Main Page", (dialog, which) -> {
                    textToSpeech.stop();
                    Intent intent = new Intent(AdvancedQuizActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                })
                .setCancelable(false)
                .show();
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}
