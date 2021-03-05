package com.company;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class MainClass {
    public static final int CARS_COUNT = 4;


    public static void main(String[] args) {
        final CountDownLatch readyCdl = new CountDownLatch(CARS_COUNT);
        final CountDownLatch finalCdl = new CountDownLatch(CARS_COUNT);
        final Semaphore tunnelSemaphore = new Semaphore(CARS_COUNT/2);
        boolean winFlag = false;
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(winFlag, new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];

        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), readyCdl, finalCdl, tunnelSemaphore);
        }

        for (Car car : cars) {
            new Thread(car).start();
        }

        try {
            readyCdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");

        try {
            finalCdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}