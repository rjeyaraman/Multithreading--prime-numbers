package com.newSumOfPrimes.java.helena;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicLong;

public class NewSumOfPrimes extends Thread{
    private static volatile long number = (long) Math.pow(10, 8);
    private static volatile long sum = 0;
    private static volatile long numOfPrimes = 0;
    private static volatile long[] Prime = new long[(int) (number + 1)];
    private static final AtomicLong i = new AtomicLong(2);
    private static final AtomicLong j = new AtomicLong(2);


    public static long getValue() {
        return i.get();
    }

    public static long getValue1() {
        return j.get();
    }

    public static long getSum() {
        return sum;
    }

    public static long getNum() {
        return numOfPrimes;
    }


    public static void iIncrement() {
        while (true) {
            long existingValue = getValue();
            long newValue = existingValue + 1;
            if (i.compareAndSet(existingValue, newValue)) {
                return;
            }
        }
    }

    public static void jIncrement() {
        while (true) {
            long existingValue1 = getValue1();
            long newValue1 = existingValue1 + i.get();
            if (j.compareAndSet(existingValue1, newValue1)) {
                return;
            }
        }
    }

    public synchronized static void calculatePrime(){
        Prime[0] = 1;
        Prime[1] = 1;
        Prime[2] = 0;

        for (i.get(); i.get() <= Math.sqrt(number); iIncrement()) {

            if (Prime[(int) i.get()] == 0) {
                for (j.set(i.get()*i.get()); j.get() <= number; jIncrement()) {
                    Prime[(int) j.get()] = 1;
                }
            }
        }
    }


    @Override
    public void run() {

        calculatePrime();

    }
    public static void main(String[] args) throws FileNotFoundException {

        long startTime = System.currentTimeMillis();



        NewSumOfPrimes s1 = new NewSumOfPrimes();
      NewSumOfPrimes s2 = new NewSumOfPrimes();
        NewSumOfPrimes s3 = new NewSumOfPrimes();
        NewSumOfPrimes s4 = new NewSumOfPrimes();
        NewSumOfPrimes s5 = new NewSumOfPrimes();
        NewSumOfPrimes s6 = new NewSumOfPrimes();
        NewSumOfPrimes s7 = new NewSumOfPrimes();
        NewSumOfPrimes s8 = new NewSumOfPrimes();

        s1.start();
        s2.start();
        s3.start();
        s4.start();
        s5.start();
        s6.start();
        s7.start();
        s8.start();

        try {
            s1.join();
             s2.join();
            s3.join();
            s4.join();
            s5.join();
            s6.join();
            s7.join();
            s8.join();
        } catch (InterruptedException e) {
        e.printStackTrace();
    }
        long[] PrimeNumbers=new long[(int) number];

        int i=0;
        for (long j = 2; j <= number; j++) {
            if (Prime[(int) j] == 0) {
      //         System.out.println("Prime="+j);
                PrimeNumbers[i]=j;
                i=i+1;
                numOfPrimes = numOfPrimes + 1;
                sum = sum + j;

            }
        }
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        File file=new File("C:\\Users\\Helena J Arpudaraj\\Desktop\\primes.txt");
        PrintWriter writer=new PrintWriter(file);
        writer.println("<Execution time="+elapsedTime+"> <Total number of primes found="+getNum()+"> <Sum of all primes found="+getSum()+">");
        writer.println("<top ten maximum primes, listed in order from lowest to highest>");
        for(long k=getNum()-10;k<getNum();k++)
        {
            writer.println(PrimeNumbers[(int) k]);
        }
        writer.close();
        System.out.println("Task finished");
          System.out.println("Sum of Prime= " + getSum());

        System.out.println("Number of Prime Numbers= " + getNum());
          System.out.println("Time: "+elapsedTime+" milliseconds");


  }

    }

