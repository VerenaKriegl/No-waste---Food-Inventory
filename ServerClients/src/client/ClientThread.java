package client;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientThread implements Runnable {

    private BufferedReader br;

    public ClientThread(BufferedReader br) {
        this.br = br;
    }

    @Override
    public void run() {
        String line = "";
        do {
            try {
                line = br.readLine();
                System.out.println(line);
            } catch (IOException ex) {
                System.out.println(ex.toString());
            }
        } while (!line.equals("EXIT"));
        System.out.println("ClientThread finished!");
    }
}
