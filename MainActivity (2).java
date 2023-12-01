package com.example.myapplication;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends ComponentActivity {

    // Масив з назвами продуктів пекарні
    private String[] products = {"Хліб", "Булочка", "Пиріг", "Торт", "Піца", "Круасан", "Печиво", "Вафлі", "Мафін", "Донат"};

    // Генератор випадкових чисел
    private Random random = new Random();

    // Компоненти інтерфейсу
    private TextView questionTextView;
    private TextView menuTextView;
    private TextView productTextView;
    private TextView timerTextView;
    private TextView scoreTextView;
    private Button yesButton;
    private Button noButton;
    private ProgressBar progressBar;

    // Змінні для логіки гри
    private List<String> menu; // Список з 5 продуктів пекарні, які відображаються на екрані
    private String product; // Продукт, який показується нижче
    private int score; // Кількість правильних відповідей
    private boolean isPlaying; // Чи триває гра
    private CountDownTimer timer; // Таймер для обмеження часу гри
    private int timeLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Знаходимо компоненти інтерфейсу за їх id
        questionTextView = findViewById(R.id.questionTextView);
        menuTextView = findViewById(R.id.menuTextView);
        productTextView = findViewById(R.id.productTextView);
        timerTextView = findViewById(R.id.timerTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        yesButton = findViewById(R.id.yesButton);
        noButton = findViewById(R.id.noButton);
        progressBar = findViewById(R.id.progressBar);

        // Створюємо таймер на 3 хвилини
        timer = new CountDownTimer(180000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Оновлюємо текст таймера кожну секунду
                timerTextView.setText("Час: " + millisUntilFinished / 1000 + " с");
                timeLeft = (int) (millisUntilFinished / 1000); // Зберігаємо залишок часу в змінній timeLeft
                progressBar.setProgress(timeLeft);
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
        setupProgressBar();
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
        // Вибираємо випадково 5 продуктів пекарні з масиву products
        menu = Arrays.asList(products).subList(random.nextInt(products.length - 4), random.nextInt(products.length - 4) + 5);
        // Перемішуємо список
        Collections.shuffle(menu);
        // Встановлюємо текст для компонента menuTextView
        menuTextView.setText("Меню: " + menu.toString());
        // Вибираємо випадково продукт з масиву products
        product = products[random.nextInt(products.length)];
        // Встановлюємо текст для компонента productTextView
        productTextView.setText("Продукт: " + product);
    }

    // Метод для перевірки відповіді
    private void checkAnswer(boolean answer) {
        // Перевіряємо, чи є продукт в списку menu
        boolean isMatch = menu.contains(product);
        // Якщо відповідь вірна, збільшуємо рахунок
        if (answer == isMatch) {
            score++;
            // Показуємо повідомлення про правильну відповідь
            Toast.makeText(this, "Вірно!", Toast.LENGTH_SHORT).show();
        } else {
            // Показуємо повідомлення про неправильну відповідь
            Toast.makeText(this, "Невірно!", Toast.LENGTH_SHORT).show();
        }
        // Оновлюємо текст з рахунком
        scoreTextView.setText("Рахунок: " + score);
        // Генеруємо наступне питання
        generateQuestion();
    }


    // Метод для налаштування progressbar
    private void setupProgressBar() {
        // Встановлюємо максимальне значення progressbar на 180 секунд
        progressBar.setMax(180);
        // Встановлюємо початкове значення progressbar на 180 секунд
        progressBar.setProgress(180);
    }
}
