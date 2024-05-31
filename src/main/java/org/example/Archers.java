package org.example;

import java.io.IOException;

public class Archers extends Army{
    static Log myLog;
    static {
        try {
            myLog = new Log("Archerslogger.log", "acherslog");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    void Work() {
        myLog.logger.info("Теперь ваши характеристики такие:");
        System.out.println(this.rank);
        System.out.println(this.count);
        System.out.println(this.bowLevel);
        System.out.println(this.aroowsLevel);
        myLog.logger.info("Характеристики созданы:");
    }
    private int bowLevel;
    private int aroowsLevel;
    public Archers(String rank, int count, int bowLevel, int aroowsLevel) {
        super(rank, count);
        this.bowLevel = bowLevel;
        this.aroowsLevel = aroowsLevel;
    }
}