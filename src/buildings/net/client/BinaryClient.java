package buildings.net.client;

import buildings.Buildings;
import buildings.dwelling.Dwelling;
import buildings.dwelling.DwellingFactory;
import buildings.dwelling.hotel.HotelFactory;
import buildings.interfaces.Building;
import buildings.office.OfficeFactory;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class BinaryClient {

    private static final String BUILDINGS = "BUILDINGS.txt";
    private static final String TYPES_OF_BUILDINGS = "TYPES.txt";
    private static final String COSTS = "COSTS.txt";
    private static final int DWELLING = 1;
    private static final int OFFICE_BUILDING = 2;
    private static final int HOTEL = 3;
    private static final int DISCONNECT = 0;

    private static Socket clientSocket;
    private static BufferedReader in;   // поток чтения из сокета
    private static OutputStream out;    // поток записи в сокет

    public static void main(String[] args) {
        System.out.println("Клиент начал работу");
        try (Scanner types = new Scanner(new FileReader(TYPES_OF_BUILDINGS));
                FileReader buildings = new FileReader(BUILDINGS);
                PrintWriter costs = new PrintWriter(new FileWriter(COSTS))) {
            try {
                clientSocket = new Socket("localhost", 4004);   // номер порта совпадает с сервером
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new DataOutputStream(clientSocket.getOutputStream());

                /* читаем все здания и их типы из файлов */
                while (types.hasNextLine()) {

                    /* читаем очередной тип здания и передаем его на сервер */
                    String type = types.nextLine();
                    if (type.equals("Dwelling")) {
                        Buildings.setBuildingFactory(new DwellingFactory());
                        out.write(DWELLING);
                    } else if (type.equals("OfficeBuilding")) {
                        Buildings.setBuildingFactory(new OfficeFactory());
                        out.write(OFFICE_BUILDING);
                    } else if (type.equals("Hotel")) {
                        Buildings.setBuildingFactory(new HotelFactory());
                        out.write(HOTEL);
                    }
                    out.flush();

                    Building building = Buildings.readBuilding(buildings);
                    Buildings.outputBuilding(building, out);
                    out.flush();
                    System.out.println("Клиент отправил на сервер здание: " + building);

                    String answer = in.readLine();
                    System.out.println("Ответ сервера: " + answer);

                    costs.println(answer);
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
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
