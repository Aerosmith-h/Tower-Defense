package com.nevo.SVOyaIgra;

import java.awt.*;
import javax.swing.ImageIcon;
import java.awt.Point;

public class Enemy {
    private int health;
    private int x, y;
    private Path path;
    private boolean dead;
    private Image image;
    private int currentPointIndex;
    private int speed; // Скорость врага
    private static final int ENEMY_WIDTH = 70; // Ширина изображения врага
    private static final int ENEMY_HEIGHT = 70; // Высота изображения врага

    public Enemy(Path path, int waveCount, String imagePath, int speed) {
        this.path = path;
        this.health = 10 * waveCount; // увеличиваем здоровье врага с каждой волной
        this.x = path.getStartX();
        this.y = path.getStartY();
        this.dead = false;
        this.currentPointIndex = 0;
        this.image = new ImageIcon(getClass().getResource(imagePath)).getImage();
        this.speed = speed; // Устанавливаем скорость врага
    }

    public void move() {
        if (currentPointIndex < path.getPoints().size() - 1) {
            Point target = path.getPoints().get(currentPointIndex + 1);
            double dx = target.x - x;
            double dy = target.y - y;
            double length = Math.sqrt(dx * dx + dy * dy);
            double unitX = dx / length;
            double unitY = dy / length;
            x += unitX * speed;
            y += unitY * speed;

            // Если достигли следующей точки пути
            if (Math.abs(x - target.x) < 2 && Math.abs(y - target.y) < 2) {
                currentPointIndex++;
            }
        }
    }

    public boolean isDead() {
        return dead;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            dead = true; // Враг умирает
        }
    }

    public int getReward() {
        return 20; // за каждого врага дается 20 монет
    }

    // Геттеры для координат (если нужно)
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Если враг достиг конца пути
    public boolean hasReachedEnd() {
        return currentPointIndex >= path.getPoints().size() - 1;
    }

    public void draw(Graphics g) {
        g.drawImage(image, x - ENEMY_WIDTH / 2, y - ENEMY_HEIGHT / 2, ENEMY_WIDTH, ENEMY_HEIGHT, null);
    }

    public boolean isHit(Bullet bullet) {
        int dx = bullet.getX() - x;
        int dy = bullet.getY() - y;
        int radius = ENEMY_WIDTH / 2; // Радиус врага
        return dx * dx + dy * dy <= radius * radius;
    }
}
