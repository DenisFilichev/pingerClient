/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pingerclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author denis
 */
public class SocketClient {
    Socket socket;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    
    public void run (){
        try (Socket socket = new Socket("85.21.168.185", 7001)){
            oos = new ObjectOutputStream((socket.getOutputStream()));
            ois = new ObjectInputStream((socket.getInputStream()));
            this.socket = socket;
            System.out.println("Socket клиента создан");
            ping();
        } catch (IOException ex) {}
    }
    
    private void ping (){
        try (ObjectOutputStream oos = this.oos;
                ObjectInputStream ois = this.ois){
            Scanner s;
            while(true){
                System.out.println("Введите IP адрес ");
                s = new Scanner(System.in);
                String ip = s.nextLine();
                System.out.println("Введено " + ip);
                oos.writeObject(ip);
                oos.flush();
                System.out.println("IP отправлен серверу");
                String line = "";
                while (!(line.equals("END"))) {// reading output stream of the command
                    line = (String)ois.readObject();
                    System.out.println(line);
                }
            }
                /*while (true){
                    line = (String)ois.readObject();
                    System.out.println(line);
                }*/
            } catch (IOException ex) {
            System.out.println("Связь разорвана");
            run();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}