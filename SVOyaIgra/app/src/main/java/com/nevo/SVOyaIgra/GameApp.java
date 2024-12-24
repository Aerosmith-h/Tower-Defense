package com.nevo.SVOyaIgra;

import javax.swing.*;

public class GameApp extends JFrame {
    public GameApp() {
        setTitle("Tower Defense");
        setSize(1200, 800); // размер окна
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        GamePanel1 gamePanel = new GamePanel1();
        add(gamePanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameApp::new);
    }
}