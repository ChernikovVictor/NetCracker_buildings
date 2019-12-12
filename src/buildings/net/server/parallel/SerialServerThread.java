package buildings.net.server.parallel;

import buildings.Buildings;
import buildings.dwelling.hotel.Hotel;
import buildings.exceptions.BuildingUnderArrestException;
import buildings.interfaces.Building;
import buildings.office.OfficeBuilding;

import java.io.*;
import java.net.Socket;
import java.util.Random;

public class SerialServerThread extends Thread {

    private Socket socket;      // сокет связи клиента и сервера
    private ObjectInputStream in;     // поток чтения из сокета
    private ObjectOutputStream out; // поток записи в сокет

    public SerialServerThread(Socket socket) throws IOException {
        this.socket = socket;
        /* Сначала инициализируется out, потом in. Иначе работать не будет!*/
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        start(); // вызов потока работы с клиентом при создании объекта
    }

    @Override
    public void run() {
        try {
            try {
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
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                System.out.println("Клиент отсоединился");
                in.close();
                out.close();
                socket.close();
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
