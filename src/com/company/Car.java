package com.company;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private Race race;
    private int speed;
    private String name;
    private CountDownLatch prepareCdl;
    private CountDownLatch readyCdl;
    private CountDownLatch finalCdl;
    private Semaphore tunnelSemaphore;

    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed, CountDownLatch prepareCdl, CountDownLatch readyCdl, CountDownLatch finalCdl, Semaphore tunnelSemaphore) {
        this.race = race;
        this.speed = speed;
        this.prepareCdl = prepareCdl;
        this.readyCdl = readyCdl;
        this.finalCdl = finalCdl;
        this.tunnelSemaphore = tunnelSemaphore;

        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            prepareCdl.countDown();
            try {
                prepareCdl.await();
            } catch (Exception e){
                e.printStackTrace();
            }
            System.out.println(this.name + " готов");
            readyCdl.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }


        for (int i = 0; i < race.getStages().size(); i++) {
            if (race.getStages().get(i) instanceof Tunnel) {
                try {
                    tunnelSemaphore.acquire();
                    race.getStages().get(i).go(this);
                    tunnelSemaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                race.getStages().get(i).go(this);
            }
        }

        if (!race.isWinFlag()) {
            race.setWinFlag(true);
            System.out.println(this.name + " ПОБЕДИЛ");
        } else {
            System.out.println(this.name + " закончил гонку");
        }

        finalCdl.countDown();

    }

}