package com.nevo.SVOyaIgra;

public class Shop {
    private int towerPrice = 50; // Цена одной башни
    private int upgradePrice = 100; // Цена улучшения башни

    public int getTowerPrice() {
        return towerPrice;
    }

    public int getUpgradePrice() {
        return upgradePrice;
    }

    // Метод для проверки, может ли игрок позволить себе купить башню
    public boolean canAffordTower(int coins) {
        return coins >= towerPrice;
    }

    // Метод для проверки, может ли игрок позволить себе улучшить башню
    public boolean canAffordUpgrade(int coins) {
        return coins >= upgradePrice;
    }

    // Метод для покупки башни
    public boolean buyTower(int coins) {
        if (canAffordTower(coins)) {
            coins -= towerPrice;
            return true;
        }
        return false;
    }

    // Метод для улучшения башни
    public boolean upgradeTower(int coins) {
        if (canAffordUpgrade(coins)) {
            coins -= upgradePrice;
            return true;
        }
        return false;
    }
}
   
