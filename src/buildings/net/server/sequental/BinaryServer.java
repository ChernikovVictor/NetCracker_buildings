package buildings.net.server.sequental;

import buildings.Buildings;
import buildings.dwelling.DwellingFactory;
import buildings.dwelling.hotel.Hotel;
import buildings.dwelling.hotel.HotelFactory;
import buildings.exceptions.BuildingUnderArrestException;
import buildings.interfaces.Building;
import buildings.office.OfficeBuilding;
import buildings.office.OfficeFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class BinaryServer {
    private static final int DWELLING = 1;
    private static final int OFFICE_BUILDING = 2;
    private static final int HOTEL = 3;
    private static final int DISCONNECT = 0;

    private static Socket clientSocket; //сокет для общения с клиентом
    private static ServerSocket server;
    private static InputStream in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет

    public static void main(String[] args) {
        try {
            try {
                server = new ServerSocket(4004); // серверсокет прослушивает порт 4004
                System.out.println("Сервер запущен");
                clientSocket = server.accept(); // accept() будет ждать пока
                                                //кто-нибудь не захочет подключиться
                try {
                    System.out.println("К серверу подключился клиент");
                    in = clientSocket.getInputStream();
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

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
