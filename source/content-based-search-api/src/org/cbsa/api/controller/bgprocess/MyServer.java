package org.cbsa.api.controller.bgprocess;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class MyServer {
    public static void main(String[] args) throws IOException {
        File file = new File("/home/aditya/dataset/counter.txt");
        if (!file.exists()) {
            file.createNewFile();

            FileWriter fw = new FileWriter(file);
            fw.write("0");
            fw.close();

            System.out.println("File Created Successfully");
        }

        try {
            ServerSocket ss = new ServerSocket(1111);
            Socket s = ss.accept();// establishes connection
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            WatchService service = FileSystems.getDefault().newWatchService(); // Create
                                                                               // a
                                                                               // WatchService
            Path path = Paths.get("/home/aditya/dataset"); // Get the directory
                                                           // to be monitored
            path.register(service, StandardWatchEventKinds.ENTRY_CREATE); // Register
                                                                          // the
                                                                          // directory

            while (true) {
                WatchKey key = service.take(); // retrieve the watchkey
                for (WatchEvent event : key.pollEvents()) {
                    // dout.writeUTF(event.kind() + ": "+ event.context());
                    dout.writeUTF("/home/aditya/dataset/"
                            + event.context().toString());
                    dout.flush();
                }
                boolean valid = key.reset();
                if (!valid) {
                    break; // Exit if directory is deleted
                }
            }

            dout.close();
            ss.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}