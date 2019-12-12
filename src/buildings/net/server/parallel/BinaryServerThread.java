package buildings.net.server.parallel;

import buildings.Buildings;
import buildings.dwelling.DwellingFactory;
import buildings.dwelling.hotel.Hotel;
import buildings.dwelling.hotel.HotelFactory;
import buildings.exceptions.BuildingUnderArrestException;
import buildings.interfaces.Building;
import buildings.office.*;

import java.io.*;
import java.net.Socket;
import java.util.Random;

public class BinaryServerThread extends Thread {

    private static final int DWELLING = 1;
    private static final int OFFICE_BUILDING = 2;
    private static final int HOTEL = 3;
    private static final int DISCONNECT = 0;

    private Socket socket;      // сокет связи клиента и сервера
    private InputStream in;     // поток чтения из сокета
    private BufferedWriter out; // поток записи в сокет

    public BinaryServerThread(Socket socket) throws IOException {
        this.socket = socket;
        in = socket.getInputStream();
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        start(); // вызов потока работы с клиентом при создании объекта
    }

    @Override
    public void run() {
        try {
            try {
                while (true) {

                    /* Выбираем фабрику по переданному типу */
                    int type = in.read();   // тип здания передается клиентом
                    if (type == DWELLING) {
                        Buildings.setBuildingFactory(new DwellingFactory());
                    } else if (type == OFFICE_BUILDING) {
                        Buildings.setBuildingFactory(new OfficeFactory());
                    } else if (type == HOTEL) {
                        Buildings.setBuildingFactory(new HotelFactory());
                    } else if (type == DISCONNECT) {
                        break;  // клиент закончил работу с сервером
                    }

                    Building building = Buildings.inputBuilding(in);
                    System.out.println("Получено здание: " + building);

                    String answer = "";
                    try {
                        answer = String.format("%.3f", cost(building));
                    } catch (BuildingUnderArrestException e) {
                        answer = "Arrested";
                    } finally {
                        out.write(answer + "\n");
                        out.flush();
                        System.out.println("Ответ на запрос клиента: " + answer);
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
