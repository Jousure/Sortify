package com.example.sortify;

import android.app.AlertDialog;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class BasicQuizActivity extends AppCompatActivity {

    private TextView questionText;
    private RadioGroup optionsGroup;
    private Button btnNext;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private TextToSpeech textToSpeech;

    private String[] questions = {"Which sorting algorithm repeatedly swaps adjacent elements if they are in the wrong order?", "What is the best-case time complexity of Insertion Sort?", "Which sorting algorithm selects the smallest element and swaps it with the first element?", "What is the worst-case time complexity of Quick Sort?", "Which sorting algorithm follows the \"divide and conquer\" approach?"};

    private String[][] options = {{"Merge Sort", "Quick Sort", "Bubble Sort", "Heap Sort"}, {"O(n²)", "O(n log n)", "O(n)", "O(log n)"}, {"Selection Sort", "Merge Sort", "Quick Sort", "Heap Sort"}, {"O(n log n)", "O(n²)", "O(n)", "O(n + k)"}, {"Merge Sort", "Bubble Sort", "Selection Sort", "Counting Sort"}};

    private int[] correctAnswers = {2, 2, 0, 1, 0};

    private String[] explanations = {"Bubble Sort compares adjacent elements and swaps them if needed, making it inefficient for large datasets.", "When the input is already sorted, Insertion Sort runs in O(n) time since each element is compared only once.", "Selection Sort finds the smallest element in the array and swaps it with the first unsorted element, repeating the process.", "Quick Sort has a worst-case complexity of O(n²) when the pivot selection is poor, such as always picking the smallest or largest element.", "Merge Sort divides the array into halves, recursively sorts them, and then merges them back."};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_quiz);

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
            showAlertWithTTS("Incorrect!", "The correct answer is: " + options[currentQuestionIndex][correctAnswers[currentQuestionIndex]] + "\n\n" + explanations[currentQuestionIndex]);
        }

        currentQuestionIndex++;


        if (currentQuestionIndex == questions.length) {
            new android.os.Handler().postDelayed(() -> {
                textToSpeech.speak(explanations[currentQuestionIndex - 1], TextToSpeech.QUEUE_ADD, null, null);
                new android.os.Handler().postDelayed(this::showFinalScore, 5000); // Show final score after explanation
            }, 2000);
        } else {
            loadQuestion();
        }
    }

    private void showAlertWithTTS(String title, String message) {
        textToSpeech.speak(message, TextToSpeech.QUEUE_ADD, null, null);

        new AlertDialog.Builder(this).setTitle(title).setMessage(message).setPositiveButton("OK", (dialog, which) -> {
            textToSpeech.stop(); // Stops TTS when ok is clicked
            dialog.dismiss();
        }).show();
    }

    private void showFinalScore() {
        String finalScoreMessage = "Quiz Finished! Your Score: " + score + " out of " + questions.length;
        textToSpeech.speak(finalScoreMessage, TextToSpeech.QUEUE_FLUSH, null, null);

        new AlertDialog.Builder(this).setTitle("Quiz Finished!").setMessage(finalScoreMessage).setPositiveButton("Go to Main Page", (dialog, which) -> {
            textToSpeech.stop(); // Stops TTS when final score dialog is closed
            finish(); // Close activity
        }).show();
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
