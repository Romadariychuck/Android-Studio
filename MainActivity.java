package com.example.myapplication;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends ComponentActivity {

    // Масив з назвами порід собак
    private String[] breeds = {"Булочка з маком", "булочка з повидлом", "сосиска в тісті", "круасан", "пончик", "сочник", "торт", "вафлі", "печиво", "шоколадні трубочки"};

    // Генератор випадкових чисел
    private Random random = new Random();

    // Компоненти інтерфейсу
    private TextView questionTextView;
    private TextView arrayTextView;
    private TextView breedTextView;
    private TextView timerTextView;
    private TextView scoreTextView;
    private Button yesButton;
    private Button noButton;

    // Змінні для логіки гри
    private List<String> array; // Список з 5 порід собак, які відображаються на екрані
    private String breed; // Порода собаки, яка показується нижче
    private int score; // Кількість правильних відповідей
    private boolean isPlaying; // Чи триває гра
    private CountDownTimer timer; // Таймер для обмеження часу гри

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Знаходимо компоненти інтерфейсу за їх id
        questionTextView = findViewById(R.id.questionTextView);
        arrayTextView = findViewById(R.id.arrayTextView);
        breedTextView = findViewById(R.id.breedTextView);
        timerTextView = findViewById(R.id.timerTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        yesButton = findViewById(R.id.yesButton);
        noButton = findViewById(R.id.noButton);

        // Створюємо таймер на 3 хвилини
        timer = new CountDownTimer(180000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Оновлюємо текст таймера кожну секунду
                timerTextView.setText("Час: " + millisUntilFinished / 1000 + " с");
            }

            @Override
            public void onFinish() {
                // Завершуємо гру, коли час вийшов
                endGame();
            }
        };

        // Додаємо слухачі для кнопок
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Перевіряємо відповідь, якщо гра триває
                if (isPlaying) {
                    checkAnswer(true);
                }
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Перевіряємо відповідь, якщо гра триває
                if (isPlaying) {
                    checkAnswer(false);
                }
            }
        });

        // Починаємо гру
        startGame();
    }

    // Метод для початку гри
    private void startGame() {
        // Скидаємо кількість правильних відповідей
        score = 0;
        // Оновлюємо текст з рахунком
        scoreTextView.setText("Рахунок: " + score);
        // Запускаємо таймер
        timer.start();
        // Встановлюємо статус гри
        isPlaying = true;
        // Генеруємо перше питання
        generateQuestion();
    }

    // Метод для завершення гри
    private void endGame() {
        // Зупиняємо таймер
        timer.cancel();
        // Встановлюємо статус гри
        isPlaying = false;
        // Показуємо повідомлення з результатом
        Toast.makeText(this, "Гра закінчена. Ви набрали " + score + " балів.", Toast.LENGTH_LONG).show();
    }

    // Метод для генерації питання
    private void generateQuestion() {
        // Вибираємо випадково 5 порід собак з масиву breeds
        array = Arrays.asList(breeds).subList(random.nextInt(breeds.length - 4), random.nextInt(breeds.length - 4) + 5);
        // Перемішуємо список
        Collections.shuffle(array);
        // Встановлюємо текст для компонента arrayTextView
        arrayTextView.setText("Масив: " + array.toString());
        // Вибираємо випадково породу собаки з масиву breeds
        breed = breeds[random.nextInt(breeds.length)];
        // Встановлюємо текст для компонента breedTextView
        breedTextView.setText("Їжа: " + breed);
    }

    private void checkAnswer(boolean answer) {
        // Перевіряємо, чи є порода собаки в списку array
        boolean isMatch = array.contains(breed);
        // Якщо відповідь вірна, збільшуємо рахунок
        if (answer == isMatch) {
            score++;
            Toast.makeText(this, "Вірно!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Невірно!", Toast.LENGTH_SHORT).show();
        }
        scoreTextView.setText("Рахунок: " + score);
        generateQuestion();
    }
}
