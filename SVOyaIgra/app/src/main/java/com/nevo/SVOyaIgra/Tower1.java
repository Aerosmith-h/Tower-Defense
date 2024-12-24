package com.nevo.SVOyaIgra;

import java.awt.*;
import javax.swing.ImageIcon;
import java.util.ArrayList;

public class Tower1 {
    private int x, y;
    private int range;
    private Image towerImage;
    private ArrayList<Bullet> bullets;
    private long lastShotTime;
    private long shotInterval = 1000; // Интервал между выстрелами в миллисекундах
    private int bulletSpeed = 10; // Скорость пули
    private int bulletDamage = 20; // Урон пули

    public Tower1(int x, int y) {
        this.x = x;
        this.y = y;
        this.range = 130;
        this.bullets = new ArrayList<>();
        this.lastShotTime = 0;
        towerImage = new ImageIcon(getClass().getResource("/Tower2.gif")).getImage();
    }

    public void draw(Graphics g) {
        g.drawImage(towerImage, x - 15, y - 15, 100, 100, null);
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
    }

    public void update() {
        for (Bullet bullet : bullets) {
            bullet.move();
        }
        bullets.removeIf(bullet -> bullet.isOutOfBounds(1200, 800));
    }

    public void shoot(Enemy enemy) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime >= shotInterval) {
            int dx = enemy.getX() - (x + 45);
            int dy = enemy.getY() - (y + 45);
            double distance = Math.sqrt(dx * dx + dy * dy);
            int speedX = (int) (dx * bulletSpeed / distance); // Скорость по оси X
            int speedY = (int) (dy * bulletSpeed / distance); // Скорость по оси Y
            bullets.add(new Bullet(x + 45, y + 45, speedX, speedY, bulletDamage)); // Скорость и урон снаряда
            lastShotTime = currentTime;
        }
    }

    public boolean isInRange(Enemy enemy) {
        int dx = enemy.getX() - (x + 45);
        int dy = enemy.getY() - (y + 45);
        return dx * dx + dy * dy <= range * range;
    }

    public boolean isClicked(int clickX, int clickY) {
        int dx = clickX - (x + 45);
        int dy = clickY - (y + 45);
        return dx * dx + dy * dy <= 15 * 15;
    }

    public int getX() {
        return x + 45;
    }

    public int getY() {
        return y + 45;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }
}
