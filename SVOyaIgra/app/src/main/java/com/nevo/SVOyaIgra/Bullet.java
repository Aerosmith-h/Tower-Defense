package com.nevo.SVOyaIgra;

import java.awt.*;

public class Bullet {
    private int x, y;
    private int speedX, speedY; // Скорость по осям X и Y
    private int damage;
    private int radius = 5; // Радиус пули

    public Bullet(int x, int y, int speedX, int speedY, int damage) {
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
        this.damage = damage;
    }

    public void move() {
        x += speedX;
        y += speedY;
    }

    public void draw(Graphics g) {
        g.setColor(Color.black);
        g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isOutOfBounds(int screenWidth, int screenHeight) {
        return x < 0 || x > screenWidth || y < 0 || y > screenHeight;
    }
}
