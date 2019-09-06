package com.company;

import jdk.jfr.Percentage;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class Main {

    public static void main(String[] args) {
        Semaphore sem = new Semaphore(4);
        CountDownLatch cdl = new CountDownLatch(100);
        for (int i = 1; i <= 100; i++) {
            new PassengerThread(sem,cdl,i).start();

        }
        try {
            cdl.await();
            System.out.println("Автобус уезжает");
        } catch (InterruptedException e) {

        }
    }
}

class PassengerThread extends Thread {
    Semaphore sem;
    CountDownLatch cdl;
    int id;

    public PassengerThread(Semaphore sem,CountDownLatch cdl, int id) {
        this.sem = sem;
        this.cdl = cdl;
        this.id = id;
    }

    @Override
    public void run() {

        try {
            sem.acquire();
            System.out.println("Пассажир " + id + " покупает билет");
            sleep(1000);


            System.out.println("Пассажир " + id + " освобождает кассу");
            sem.release();
            sleep(1000);
            System.out.println("Пассажир " + id + " садиться в автобус");
            sleep(1000);
            cdl.countDown();



        } catch (InterruptedException e) {

        }

        super.run();
    }
}

