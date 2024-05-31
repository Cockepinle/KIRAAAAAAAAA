package org.example;

import java.io.IOException;

public class Riders extends Army{
    int horses;
    int armor;

    static Log myLog;
    static {
        try {
            myLog = new Log("ridersLogger.log", "ridersLog");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Riders(String rank, int count, int horses, int armor) {
        super(rank, count);
        this.horses = horses;
        this.armor = armor;
    }

    @Override
    void Work() {
        myLog.logger.info("Теперь ваши характеристики такие:");
        System.out.println(this.armor);
        System.out.println(this.horses);
        System.out.println(this.count);
    }
}