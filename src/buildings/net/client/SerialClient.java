package buildings.net.client;

import buildings.Buildings;
import buildings.dwelling.DwellingFactory;
import buildings.dwelling.hotel.HotelFactory;
import buildings.exceptions.BuildingUnderArrestException;
import buildings.interfaces.Building;
import buildings.office.OfficeFactory;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SerialClient {
    private static final String BUILDINGS = "BUILDINGS.txt";
    private static final String TYPES_OF_BUILDINGS = "TYPES.txt";
    private static final String COSTS = "COSTS.txt";
    private static final int DISCONNECT = 0;

    private static Socket clientSocket;
    private static ObjectInputStream in;   // поток чтения из сокета
    private static ObjectOutputStream out;    // поток записи в сокет

    public static void main(String[] args) {
        System.out.println("Клиент начал работу");
        try (Scanner types = new Scanner(new FileReader(TYPES_OF_BUILDINGS));
             FileReader buildings = new FileReader(BUILDINGS);
             PrintWriter costs = new PrintWriter(new FileWriter(COSTS))) {
            try {
                clientSocket = new Socket("localhost", 4010);   // номер порта совпадает с сервером

                /* Сначала инициализируется out, потом in. Иначе работать не будет!*/
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                in = new ObjectInputStream(clientSocket.getInputStream());

                /* читаем все здания и их типы из файлов */
                while (types.hasNextLine()) {

                    /* читаем очередной тип здания и устанавливаем фабрику */
                    String type = types.nextLine();
                    if (type.equals("Dwelling")) {
                        Buildings.setBuildingFactory(new DwellingFactory());
                    } else if (type.equals("OfficeBuilding")) {
                        Buildings.setBuildingFactory(new OfficeFactory());
                    } else if (type.equals("Hotel")) {
                        Buildings.setBuildingFactory(new HotelFactory());
                    }

                    Building building = Buildings.readBuilding(buildings);
                    Buildings.serializeBuilding(building, out);
                    out.flush();
                    System.out.println("Клиент отправил на сервер здание: " + building);

                    Object answer = in.readObject();
                    if (answer instanceof BuildingUnderArrestException) {
                        costs.println("Arrested");
                        System.out.println("Ответ сервера: Arrested");
                    } else {
                        costs.println(answer);
                        System.out.println("Ответ сервера: " + answer);
                    }
                }

                /* Сообщить серверу о прекращении работы */
                out.write(DISCONNECT);
                out.flush();
                System.out.println("Клиент отсоединился от сервера");
            } finally {
                in.close();
                out.close();
                clientSocket.close();
                System.out.println("Клиент закончил работу");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
