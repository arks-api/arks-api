package org.cbsa.api.controller.bgprocess;

import java.io.DataInputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MyClient {

    public static void main(String[] args) {
        Socket s;
        try {
            s = new Socket("localhost", 1111);

            DataInputStream dis = new DataInputStream(s.getInputStream());
            while (true) {
                String str = dis.readUTF();
                FileCounterManager.incCounter();
                // Vector<String> vector = new Vector<String>();
                ConcurrentLinkedQueue<String> vector = new ConcurrentLinkedQueue<String>();
                vector.add(str);
                System.out.println(vector);
                // FileCounterManager.incCounter();
                System.out.println(str + " Added in Queue");
                String front = vector.poll();
                Thread t = new Thread(new WordCount(front));
                t.start();
                t.join();
                // Thread.sleep(10000);

                // vector.remove(front);

                System.out
                        .println("Metadata Extracted Succuessfully of " + str);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}