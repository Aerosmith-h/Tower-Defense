package com.nevo.SVOyaIgra;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.BasicStroke;
import java.util.ArrayList;

public class Path {
    private ArrayList<Point> points;

    public Path() {
        points = new ArrayList<>();
        points.add(new Point(5, 500));
        points.add(new Point(300, 500));
        points.add(new Point(300, 300));
        points.add(new Point(500, 300));
        points.add(new Point(500, 100));
        points.add(new Point(700, 100));
        points.add(new Point(700, 300));
        points.add(new Point(700, 500));
        points.add(new Point(900, 500));
        points.add(new Point(900, 300));
        points.add(new Point(1200, 300));
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(7)); // Устанавливаем толщину линии

        for (int i = 0; i < points.size() - 1; i++) {
            Point start = points.get(i);
            Point end = points.get(i + 1);
            g2d.drawLine(start.x, start.y, end.x, end.y);
        }
    }

    public Point getStartPoint() {
        return points.get(0);
    }

    public Point getEndPoint() {
        return points.get(points.size() - 1);
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public int getStartX() {
        return points.get(0).x; // Возвращаем X первой точки пути
    }

    public int getStartY() {
        return points.get(0).y; // Возвращаем Y первой точки пути
    }

    public Point getPoint(int index) {
        if (index >= 0 && index < points.size()) {
            return points.get(index);
        } else {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
    }

    public void addPoint(int x, int y) {
        points.add(new Point(x, y));
    }

    public void addTurn(int x, int y) {
        addPoint(x, y);
        addPoint(x + 100, y); // Добавляем поворот
    }
}
