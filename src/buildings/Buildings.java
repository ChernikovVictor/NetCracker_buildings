package buildings;

import buildings.dwelling_building.*;
import buildings.interfaces.*;

import java.io.*;
import java.util.Formatter;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Buildings
{
    // запись данных о здании в байтовый поток
    public static void outputBuilding(Building building, OutputStream out) throws IOException
    {
        DataOutputStream output = new DataOutputStream(out);
        int floorCount = building.floorCount();
        output.writeInt(floorCount);
        for(int i = 0; i < floorCount; i++)
        {
            Floor floor = building.getFloor(i);
            int spaceCount = floor.spaceCount();
            output.writeInt(spaceCount);
            for(int j = 0; j < spaceCount; j++)
            {
                Space space = floor.getSpace(j);
                output.writeInt(space.getRoomsCount());
                output.writeInt(space.getArea());
            }
        }
    }

    // чтение данных о здании из байтового потока
    public static Building inputBuilding(InputStream in) throws IOException
    {
        DataInputStream input = new DataInputStream(in);
        int floorCount = input.readInt();
        Floor[] floors = new Floor[floorCount];
        for(int i = 0; i < floorCount; i++)
        {
            int spaceCount = input.readInt();
            floors[i] = new DwellingFloor(spaceCount);
            for(int j = 0; j < spaceCount; j++)
            {
                floors[i].setSpace(j, new Flat(input.readInt(), input.readInt()));
            }
        }
        return new Dwelling(floors);
    }

    // записать данные о здании в символьный поток
    public static void writeBuilding(Building building, Writer out) throws IOException
    {
        PrintWriter output = new PrintWriter(out);
        int floorCount = building.floorCount();
        output.print(floorCount);
        for(int i = 0; i < floorCount; i++)
        {
            Floor floor = building.getFloor(i);
            int spaceCount = floor.spaceCount();
            output.print(' ');
            output.print(spaceCount);
            for(int j = 0; j < spaceCount; j++)
            {
                Space space = floor.getSpace(j);
                output.print(' ');
                output.print(space.getRoomsCount());
                output.print(' ');
                output.print(space.getArea());
            }
        }
    }

    // записать данные о здании в символьный поток с помощью Formatter
    public static void writeBuildingFormat(Building building, Writer out) throws IOException
    {
        Formatter formatter = new Formatter(out);
        int floorCount = building.floorCount();
        formatter.format("%d", floorCount);
        for(int i = 0; i < floorCount; i++)
        {
            Floor floor = building.getFloor(i);
            int spaceCount = floor.spaceCount();
            formatter.format(" %d", spaceCount);
            for(int j = 0; j < spaceCount; j++)
            {
                Space space = floor.getSpace(j);
                formatter.format(" %d %d", space.getRoomsCount(), space.getArea());
            }
        }
        formatter.flush();
    }

    // чтение данных о здании из символьного потока
    public static Building readBuilding(Reader in) throws IOException
    {
        StreamTokenizer streamTokenizer = new StreamTokenizer(in);
        streamTokenizer.nextToken();
        int floorCount = (int)streamTokenizer.nval;
        Floor[] floors = new Floor[floorCount];
        for(int i = 0; i < floorCount; i++)
        {
            streamTokenizer.nextToken();
            int spaceCount = (int)streamTokenizer.nval;
            floors[i] = new DwellingFloor(spaceCount);
            for(int j = 0; j < spaceCount; j++)
            {
                streamTokenizer.nextToken();
                int roomsCount = (int)streamTokenizer.nval;
                streamTokenizer.nextToken();
                int area = (int)streamTokenizer.nval;
                floors[i].setSpace(j, new Flat(roomsCount, area));
            }
        }
        return new Dwelling(floors);
    }

    // чтение данных о здании из сивольного потока при помощи Scanner
    public static Building readBuilding(Scanner scanner) throws NoSuchElementException
    {
        int floorCount = scanner.nextInt();
        Floor[] floors = new Floor[floorCount];
        for(int i = 0; i < floorCount; i++)
        {
            int spaceCount = scanner.nextInt();
            floors[i] = new DwellingFloor(spaceCount);
            for(int j = 0; j < spaceCount; j++)
            {
                int roomsCount = scanner.nextInt();
                int area = scanner.nextInt();
                floors[i].setSpace(j, new Flat(roomsCount, area));
            }
        }
        return new Dwelling(floors);
    }

    // Сериализация здания
    public static void serializeBuilding(Building building, OutputStream out) throws IOException
    {
        ObjectOutputStream output = new ObjectOutputStream(out);
        output.writeObject(building);
    }

    //десериализация здания
    public static Building deserializeBuilding(InputStream in) throws IOException, ClassNotFoundException
    {
        ObjectInputStream input = new ObjectInputStream(in);
        return (Building)input.readObject();
    }


}
