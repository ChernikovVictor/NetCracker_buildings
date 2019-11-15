package buildings.net.server.parallel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class SerialServer {

    private static final int PORT = 4010;
    private static ServerSocket server;
    private static LinkedList<SerialServerThread> serverThreads = new LinkedList<>(); // список всех нитей

    public static void main(String[] args) {
        System.out.println("Сервер запущен");
        try {
            try {
                server = new ServerSocket(PORT);
                int count = 1; // сервер закончит работу после приема трех клиентов
                while (count < 4) {
                    Socket socket = server.accept();
                    System.out.println("Сервер принял запрос клиента номер " + count +
                            " и запустил для него отдельный поток");
                    serverThreads.add(new SerialServerThread(socket));
                    count++;
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                Thread.sleep(2000);
                System.out.println("Сервер завершил работу");
                server.close();
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
