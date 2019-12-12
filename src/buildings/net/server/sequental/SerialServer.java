package buildings.net.server.sequental;

import buildings.Buildings;
import buildings.dwelling.hotel.Hotel;
import buildings.exceptions.BuildingUnderArrestException;
import buildings.interfaces.Building;
import buildings.office.OfficeBuilding;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class SerialServer {

    private static Socket clientSocket; //сокет для общения с клиентом
    private static ServerSocket server;
    private static ObjectInputStream in; // поток чтения из сокета
    private static ObjectOutputStream out; // поток записи в сокет

    public static void main(String[] args) {
        try {
            try {
                server = new ServerSocket(4008);
                System.out.println("Сервер запущен");
                clientSocket = server.accept(); // accept() будет ждать пока
                                                //кто-нибудь не захочет подключиться
                try {
                    System.out.println("К серверу подключился клиент");

                    /* Сначала инициализируется out, потом in. Иначе работать не будет!*/
                    out = new ObjectOutputStream(clientSocket.getOutputStream());
                    in = new ObjectInputStream(clientSocket.getInputStream());

                    while (true) {
                        Building building;
                        try {
                            building = Buildings.deserializeBuilding(in);
                        } catch (ClassNotFoundException | IOException e) {
                            break;  // получили что-то кроме здания => закончили работу
                        }
                        System.out.println("Получено здание: " + building);

                        String answer;
                        try {
                            answer = String.format("%.3f", cost(building));
                            out.writeObject(answer);
                            System.out.println("Ответ на запрос клиента: " + answer);
                            out.flush();
                        } catch (BuildingUnderArrestException e) {
                            out.writeObject(e);
                            System.out.println("Ответ на запрос клиента: Arrested");
                            out.flush();
                        }
                    }
                } finally { // в любом случае сокет надо закрыть
                    System.out.println("Клиент отсоединился");
                    in.close();
                    out.close();
                    clientSocket.close();
                }
            } finally { // в любом случае сервер надо закрыть
                System.out.println("Сервер завершил работу");
                server.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // Оценить стоимость здания с проверкой, арестовано ли оно
    private static double cost(Building building) throws BuildingUnderArrestException {
        if (isUnderArrest())
            throw new BuildingUnderArrestException();
        if (building instanceof OfficeBuilding) {
            return 1500 * building.totalSpaceArea();
        }
        if (building instanceof Hotel) {
            return 2000 * building.totalSpaceArea();
        }
        return 1000 * building.totalSpaceArea();
    }

    // любое здание арестовано с вероятностью в 10 процентов
    private static boolean isUnderArrest() {
        return (new Random().nextInt(100)) < 10;
    }
}
