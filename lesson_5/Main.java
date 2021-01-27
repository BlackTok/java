package lesson_5;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        initArray();
        initArrayInTwoThread();
    }

    static void initArray() {
        int size = 10000000;
        int h = size / 2;
        float[] arr = new float[size];

        Arrays.fill(arr, 1f);

        System.out.println("START");
        long a = System.currentTimeMillis();

        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        System.out.println((double) (System.currentTimeMillis() - a) / 1000 + "c");
        System.out.println("END");
    }

    static void initArrayInTwoThread() {
        int size = 10000000;
        int h = size / 2;
        float[] arr = new float[size];
        float[] a1 = new float[h];
        float[] a2 = new float[h];

        Arrays.fill(arr, 1f);

        System.out.println("START");
        long a = System.currentTimeMillis();

        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < a1.length; i++) {
                a1[i] = (float)(a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < a2.length; i++) {
                a2[i] = (float)(a2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);

        System.out.println((double) (System.currentTimeMillis() - a) / 1000 + "c");
        System.out.println("END");
    }
}
