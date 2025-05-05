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

public class IntermediateQuizActivity extends AppCompatActivity {

    private TextView questionText;
    private RadioGroup optionsGroup;
    private Button btnNext;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private TextToSpeech textToSpeech;

    private final String[] questions = {
            "What is the primary advantage of Merge Sort over Quick Sort?",
            "Which sorting algorithm is most efficient for large datasets with random values?",
            "Which sorting algorithm is most efficient when the input array is nearly sorted?",
            "Which sorting algorithm uses a binary heap data structure?",
            "What is the time complexity of Counting Sort?"
    };

    private final String[][] options = {
            {"Faster average-case time complexity", "Uses less auxiliary space", "It is a stable sorting algorithm", "It sorts in O(n) time"},
            {"Bubble Sort", "Insertion Sort", "Quick Sort", "Selection Sort"},
            {"Quick Sort", "Merge Sort", "Bubble Sort", "Insertion Sort"},
            {"Quick Sort", "Heap Sort", "Bubble Sort", "Merge Sort"},
            {"O(n log n)", "O(n²)", "O(n + k)", "O(log n)"}
    };

    private final int[] correctAnswers = {2, 2, 3, 1, 2};

    private final String[] explanations = {
            "Merge Sort maintains the relative order of equal elements, making it a stable sorting algorithm.",
            "Quick Sort has an average-case complexity of O(n log n), making it efficient for large datasets.",
            "Insertion Sort performs very well on nearly sorted arrays, running in O(n) time in such cases.",
            "Heap Sort builds a max heap or min heap and repeatedly extracts the root to sort the elements.",
            "Counting Sort works in O(n + k) time, where k is the range of input values."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intermediate_quiz);

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

            if (optionsGroup.getChildCount() >= 4) {
                ((RadioButton) optionsGroup.getChildAt(0)).setText(options[currentQuestionIndex][0]);
                ((RadioButton) optionsGroup.getChildAt(1)).setText(options[currentQuestionIndex][1]);
                ((RadioButton) optionsGroup.getChildAt(2)).setText(options[currentQuestionIndex][2]);
                ((RadioButton) optionsGroup.getChildAt(3)).setText(options[currentQuestionIndex][3]);
            }

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
            showAlertWithTTS("Wrong!", "The correct answer is: " + options[currentQuestionIndex][correctAnswers[currentQuestionIndex]]
                    + "\n\nExplanation: " + explanations[currentQuestionIndex]);
        }

        currentQuestionIndex++;
    }

    private void showAlertWithTTS(String title, String message) {
        textToSpeech.speak(message, TextToSpeech.QUEUE_ADD, null, null);

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    textToSpeech.stop();
                    dialog.dismiss();
                    loadQuestion();
                })
                .show();
    }

    private void showResult() {
        String finalScoreMessage = "Quiz Finished! Your Score: " + score + "/" + questions.length;
        textToSpeech.speak(finalScoreMessage, TextToSpeech.QUEUE_FLUSH, null, null);

        new AlertDialog.Builder(this)
                .setTitle("Quiz Finished!")
                .setMessage(finalScoreMessage)
                .setPositiveButton("Go to Main Page", (dialog, which) -> {
                    textToSpeech.stop();
                    Intent intent = new Intent(IntermediateQuizActivity.this, Quiz.class);
                    startActivity(intent);
                    finish();
                })
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
