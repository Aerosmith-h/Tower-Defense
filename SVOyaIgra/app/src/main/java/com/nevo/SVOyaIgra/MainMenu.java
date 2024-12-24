package com.nevo.SVOyaIgra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("Tower Defense - Main Menu");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        BackgroundPanel backgroundPanel = new BackgroundPanel();
        backgroundPanel.setLayout(new GridBagLayout());

        JLabel labelS = new JLabel("С");
        labelS.setFont(new Font("Arial", Font.BOLD, 100));
        labelS.setForeground(Color.WHITE);
        labelS.setHorizontalAlignment(SwingConstants.CENTER);
        labelS.setVerticalAlignment(SwingConstants.CENTER);
        labelS.setOpaque(false);

        JLabel labelV = new JLabel("В");
        labelV.setFont(new Font("Arial", Font.BOLD, 100));
        labelV.setForeground(Color.BLUE);
        labelV.setHorizontalAlignment(SwingConstants.CENTER);
        labelV.setVerticalAlignment(SwingConstants.CENTER);
        labelV.setOpaque(false);

        JLabel labelO = new JLabel("О");
        labelO.setFont(new Font("Arial", Font.BOLD, 100));
        labelO.setForeground(Color.RED);
        labelO.setHorizontalAlignment(SwingConstants.CENTER);
        labelO.setVerticalAlignment(SwingConstants.CENTER);
        labelO.setOpaque(false);

        JLabel labelRemaining = new JLabel("я игра");
        labelRemaining.setFont(new Font("Arial", Font.BOLD, 50));
        labelRemaining.setForeground(Color.BLACK);
        labelRemaining.setHorizontalAlignment(SwingConstants.CENTER);
        labelRemaining.setVerticalAlignment(SwingConstants.CENTER);
        labelRemaining.setOpaque(false);

        JButton startButton = new JButton("Начать");
        startButton.setPreferredSize(new Dimension(200, 80));
        startButton.setFont(new Font("Arial", Font.BOLD, 24));
        startButton.setBackground(Color.BLACK);
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);
        backgroundPanel.add(labelS, gbc);

        gbc.gridx = 1;
        backgroundPanel.add(labelV, gbc);

        gbc.gridx = 2;
        backgroundPanel.add(labelO, gbc);

        gbc.gridx = 3;
        backgroundPanel.add(labelRemaining, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        backgroundPanel.add(startButton, gbc);

        add(backgroundPanel);
        setVisible(true);
    }

    private void startGame() {
        setVisible(false);
        SwingUtilities.invokeLater(GameApp::new);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenu::new);
    }
}

class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel() {
        backgroundImage = new ImageIcon(getClass().getResource("/Forest Battle Background.jpeg")).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
