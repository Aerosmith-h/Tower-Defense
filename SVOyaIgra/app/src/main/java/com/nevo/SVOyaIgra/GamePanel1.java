package com.nevo.SVOyaIgra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class GamePanel1 extends JPanel {
    private Timer timer;
    private Timer waveTimer;
    private ArrayList<Tower1> towers;
    private ArrayList<Enemy> enemies;
    private Path path;
    private int totalHp;
    private int maxHp;
    private Image backgroundImage;
    private Image villageImage;
    private int waveCount;
    private int maxWaves;
    private boolean gameWon;
    private boolean gameOver;
    private int waveCountdown; // Countdown for time until the next wave starts
    private final int waveDuration = 10; // Set wave duration (in seconds)
    private boolean waveInProgress; // Flag to check if the current wave is in progress
    private boolean towerInProcess = false; // Флаг для отслеживания состояния башни

    private Shop shop;
    private int coins = 100; // Начальная сумма монет
    private boolean shopVisible = false; // Флаг для отображения магазина

    private Timer enemySpawnTimer; // Таймер для добавления врагов с задержкой
    private int enemiesToSpawn; // Количество врагов, которые нужно добавить
    private int spawnedEnemies; // Количество уже добавленных врагов

    public GamePanel1() {
        URL backgroundUrl = getClass().getResource("/Forest Battle Background.jpeg");
        if (backgroundUrl == null) {
            throw new RuntimeException("Background image not found");
        }
        backgroundImage = new ImageIcon(backgroundUrl).getImage();

        URL villageUrl = getClass().getResource("/Vilage.png");
        if (villageUrl == null) {
            throw new RuntimeException("Village image not found");
        }
        villageImage = new ImageIcon(villageUrl).getImage();

        setSize(1200, 800); // Размер окна
        towers = new ArrayList<>();
        enemies = new ArrayList<>();
        path = new Path();
        totalHp = 100;
        maxHp = 100;
        waveCount = 0; // Счетчик волн с 0
        maxWaves = 10;
        gameWon = false;
        gameOver = false;
        waveCountdown = waveDuration; // Initialize countdown to the wave duration
        waveInProgress = false; // No wave is in progress initially

        // Инициализация магазина
        shop = new Shop();

        timer = new Timer(1000 / 60, e -> actionPerformed()); // Set timer to update 60 times per second
        timer.start();

        waveTimer = new Timer(1000, e -> updateWave());
        waveTimer.start();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                if (e.getButton() == MouseEvent.BUTTON3) { // Правая кнопка
                    if (shopVisible && x >= 100 && x <= 200 && y >= 650 && y <= 680) {
                        if (shop.canAffordTower(coins) && !towerInProcess) {
                            coins -= shop.getTowerPrice();
                            towerInProcess = true;
                        }
                    } else if (shopVisible && x >= 100 && x <= 200 && y >= 700 && y <= 730) {
                        if (shop.canAffordUpgrade(coins)) {
                            coins -= shop.getUpgradePrice();
                        }
                    }
                }

                if (e.getButton() == MouseEvent.BUTTON1) { // Левая кнопка
                    if (towerInProcess) {
                        addTower(x, y);
                        towerInProcess = false;
                    }

                    // Обработка нажатия на стрелочку для показа/скрытия магазина
                    if (x >= 0 && x <= 40 && y >= getHeight() - 40 && y <= getHeight()) {
                        shopVisible = !shopVisible;
                    }

                    // Обработка нажатия на кнопку закрытия магазина
                    if (shopVisible && x >= 0 && x <= 40 && y >= 620 && y <= 650) {
                        shopVisible = false;
                    }
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        path.draw(g);
        for (Tower1 tower : towers) {
            tower.draw(g);
        }
        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }

        // Отрисовка деревни в конце пути
        Point endPoint = path.getEndPoint();
        g.drawImage(villageImage, endPoint.x - villageImage.getWidth(null) / 2, endPoint.y - villageImage.getHeight(null) / 2, null);

        g.setColor(Color.RED);
        g.fillRect(10, 10, 600 * totalHp / maxHp, 30);
        g.setColor(Color.BLACK);
        g.drawRect(10, 10, 600, 30);

        // Отображение текущего здоровья посередине полоски
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        String hpText = totalHp + "/" + maxHp;
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int x = 10 + (600 - metrics.stringWidth(hpText)) / 2;
        int y = 10 + ((30 - metrics.getHeight()) / 2) + metrics.getAscent();
        g.drawString(hpText, x, y);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Волна: " + waveCount + "/" + maxWaves, 10, 95);

        if (waveInProgress && waveCount < maxWaves) {
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.drawString("До начала волны: " + waveCountdown + " сек", 10, 120);
        }

        if (gameWon) {
            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            String winMessage = "Поздравляю ты победил!";
            FontMetrics winMetrics = g.getFontMetrics(g.getFont());
            int winX = (getWidth() - winMetrics.stringWidth(winMessage)) / 2;
            int winY = (getHeight() - winMetrics.getHeight()) / 2 + winMetrics.getAscent();
            g.drawString(winMessage, winX, winY);
        }

        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            String loseMessage = "Поздравляю Ты проиграл!";
            FontMetrics loseMetrics = g.getFontMetrics(g.getFont());
            int loseX = (getWidth() - loseMetrics.stringWidth(loseMessage)) / 2;
            int loseY = (getHeight() - loseMetrics.getHeight()) / 2 + loseMetrics.getAscent();
            g.drawString(loseMessage, loseX, loseY);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Монеты: " + coins, 10, 70);

        // Отрисовка стрелочки для показа/скрытия магазина
        int arrowX = 0; // Координата x верхнего левого угла прямоугольника
        int arrowY = getHeight() - 40; // Координата y верхнего левого угла прямоугольника
        g.setColor(Color.BLACK);
        g.fillRect(arrowX, arrowY, 40, 40);
        g.setColor(Color.WHITE);
        g.drawString(shopVisible ? "↑" : "↓", arrowX + 15, arrowY + 25);

        // Отрисовка панели магазина, если она видима
        if (shopVisible) {
            g.setColor(Color.BLACK);
            g.fillRect(1, 650, 600, 150); // Панель магазина
            g.setColor(Color.WHITE);
            g.drawString("Купить башню: " + shop.getTowerPrice() + " монет", 1, 670);
            if (coins >= shop.getTowerPrice() && !towerInProcess) {
                g.drawString("Нажмите правой кнопкой для покупки!", 1, 690);
            } else if (towerInProcess) {
                g.drawString("Башня куплена! Разместите её.", 1, 690);
            } else {
                g.drawString("Не хватает монет!", 1, 690);
            }

            g.setColor(Color.WHITE);
            g.drawString("Улучшение башни: " + shop.getUpgradePrice() + " монет", 1, 730);
            if (coins >= shop.getUpgradePrice()) {
                g.drawString("Нажмите правой кнопкой для улучшения!", 1, 760);
            } else {
                g.drawString("Не хватает монет!", 1, 760);
            }

            // Отрисовка кнопки закрытия магазина
            g.setColor(Color.BLACK);
            g.fillRect(0, 610, 40, 40); // Кнопка закрытия
            g.setColor(Color.WHITE);
            g.drawString("↓", 15, 635);
        }
    }

    public void actionPerformed() {
        for (Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext(); ) {
            Enemy enemy = iterator.next();
            enemy.move();
            if (enemy.hasReachedEnd()) {
                iterator.remove();
                totalHp -= 10;
                if (totalHp <= 0) {
                    gameOver();
                }
            } else {
                for (Tower1 tower : towers) {
                    if (tower.isInRange(enemy)) {
                        tower.shoot(enemy);
                    }
                }
                for (Tower1 tower : towers) {
                    for (Bullet bullet : tower.getBullets()) {
                        if (enemy.isHit(bullet)) {
                            enemy.takeDamage(bullet.getDamage());
                            tower.getBullets().remove(bullet);
                            break;
                        }
                    }
                }
                if (enemy.isDead()) {
                    coins += enemy.getReward(); // Добавляем монеты за убитого врага
                    iterator.remove();
                }
            }
        }

        for (Tower1 tower : towers) {
            tower.update();
        }

        if (enemies.isEmpty() && waveCount >= maxWaves && !waveInProgress && !gameWon) {
            gameWon = true;
            timer.stop();
            waveTimer.stop();
        } else if (enemies.isEmpty() && !waveInProgress && waveCount < maxWaves) {
            System.out.println("Starting new wave: " + (waveCount + 1));
            waveInProgress = true;
            waveCountdown = waveDuration; // Reset the countdown
        }

        repaint();
    }

    private void updateWave() {
        if (waveInProgress) {
            if (waveCountdown > 0) {
                waveCountdown--;
            } else {
                waveCount++;
                waveInProgress = false;
                waveCountdown = waveDuration; // Reset the countdown
                if (waveCount > maxWaves) {
                    System.out.println("All waves completed. Game won!");
                    gameWon = true;
                    timer.stop();
                    waveTimer.stop();
                } else {
                    int enemiesInWave = waveCount * 2;
                    enemiesToSpawn = enemiesInWave;
                    spawnedEnemies = 0;
                    if (enemySpawnTimer != null) {
                        enemySpawnTimer.stop();
                    }
                    enemySpawnTimer = new Timer(1000, e -> spawnEnemy()); // Таймер для добавления врагов с задержкой
                    enemySpawnTimer.start();
                }
            }
        } else if (waveCount < maxWaves && enemies.isEmpty()) {
            System.out.println("No enemies left. Starting next wave.");
            waveInProgress = true;
            waveCountdown = waveDuration; // Reset the countdown
        }
    }

    private void spawnEnemy() {
        if (spawnedEnemies < enemiesToSpawn) {
            String imagePath = (spawnedEnemies % 2 == 0) ? "/enemy1.gif" : "/enemy2.gif";
            URL imageURL = getClass().getResource(imagePath);
            if (imageURL != null) {
                enemies.add(new Enemy(path, waveCount, imagePath, 2)); // Фиксированная скорость для всех врагов
                spawnedEnemies++;
            } else {
                System.err.println("Resource not found: " + imagePath);
            }
        } else {
            enemySpawnTimer.stop();
        }
    }

    public void addTower(int x, int y) {
        int towerWidth = 100; // Ширина башни
        int towerHeight = 100; // Высота башни
        int offsetX = towerWidth / 2; // Смещение по оси X
        int offsetY = towerHeight / 2; // Смещение по оси Y

        // Корректируем координаты, чтобы башня появлялась по центру клика
        int correctedX = x - offsetX;
        int correctedY = y - offsetY;

        towers.add(new Tower1(correctedX, correctedY));
    }

    private void gameOver() {
        gameOver = true;
        timer.stop();
        waveTimer.stop();
    }
}
