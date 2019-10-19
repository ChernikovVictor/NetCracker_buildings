package buildings;

import buildings.dwelling_building.*;
import buildings.interfaces.*;
import buildings.office_building.*;
import buildings.exceptions.*;
import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)
    {
        task5();
    }

    private static void task2()
    {
        Random rnd = new Random();
        int floorsCount = 3;
        int flatsCount = 3;
        DwellingFloor[] dwellingFloors = new DwellingFloor[floorsCount];
        for(int i = 0; i < floorsCount; i++)
        {
            dwellingFloors[i] = new DwellingFloor(flatsCount);
            for(int j = 0; j < flatsCount; j++)
            {
                Flat flat = new Flat(1 + rnd.nextInt(4), 1 + rnd.nextInt(200));
                dwellingFloors[i].setSpace(j, flat);
            }
        }

        Dwelling dwelling = new Dwelling(dwellingFloors);
        Space[] Flats = dwelling.getSpaces();
        System.out.println("Квартиры в доме:");
        for(int i = 0; i < Flats.length; i++)
        {
            System.out.println(i + ": " + Flats[i]);
        }

        System.out.println("Число всех квартир дома " + dwelling.spaceCount());
        System.out.println("Общая площадь всех квартир дома " + dwelling.totalSpaceArea());
        System.out.println("Общее число всех комнат дома " + dwelling.totalRoomsCount());
        System.out.println("Квартира с максимальной площадью: " + dwelling.getBestSpace());

        System.out.println("Квартира с индексом 6: " + dwelling.getSpace(6));
        System.out.println("Заменим ее на квартиру по умолчанию");
        dwelling.setSpace(6, new Flat());

        System.out.println("Квартира с индексом 4: " + dwelling.getSpace(4));
        System.out.println("Удалим ее");
        dwelling.removeSpace(4);

        System.out.println("Добавим квартиру из 5 комнат с площадью 300 кв.м. по индексу 3");
        dwelling.addSpace(3, new Flat(5, 300));

        System.out.println("Проверим изменения:");
        Flats = dwelling.getSpaces();
        for(int i = 0; i < Flats.length; i++)
        {
            System.out.println(i + ": " + Flats[i]);
        }

        System.out.println("Отсортируем по убыванию площадей:");
        Flats = dwelling.sortedSpaceArray();
        for(Space flat : Flats)
        {
            System.out.println(flat);
        }
    }

    private static void task3Exceptions()
    {
        Random rnd = new Random();
        int floorsCount = 3;
        int officeCount = 3;
        OfficeFloor[] officeFloors = new OfficeFloor[floorsCount];
        for(int i = 0; i < floorsCount; i++)
        {
            officeFloors[i] = new OfficeFloor(officeCount);
            for(int j = 0; j < officeCount; j++)
            {
                Office office = new Office(1 + rnd.nextInt(4), 1 + rnd.nextInt(200));
                officeFloors[i].setSpace(j, office);
            }
        }

        OfficeBuilding officeBuilding = new OfficeBuilding(officeFloors);
        Space[] offices = officeBuilding.getSpaces();
        System.out.println("Офисы в здании:");
        for(int i = 0; i < offices.length; i++)
        {
            System.out.println(i + ": " + offices[i]);
        }

        System.out.println("Число всех офисов здания " + officeBuilding.spaceCount());
        System.out.println("Общая площадь всех офисов здания " + officeBuilding.totalSpaceArea());
        System.out.println("Общее число всех комнат здания " + officeBuilding.totalRoomsCount());
        System.out.println("Офис с максимальной площадью: " + officeBuilding.getBestSpace());

        System.out.println("Офис с индексом 6: " + officeBuilding.getSpace(6));
        System.out.println("Заменим его на офис по умолчанию");
        officeBuilding.setSpace(6, new Office());

        System.out.println("Офис с индексом 4: " + officeBuilding.getSpace(4));
        System.out.println("Удалим его");
        officeBuilding.removeSpace(4);

        System.out.println("Добавим офис из 5 комнат с площадью 300 кв.м. по индексу 3");
        officeBuilding.addSpace(3, new Office(5, 300));

        System.out.println("Проверим изменения:");
        offices = officeBuilding.getSpaces();
        for(int i = 0; i < offices.length; i++)
        {
            System.out.println(i + ": " + offices[i]);
        }

        System.out.println("Отсортируем по убыванию площадей:");
        offices = officeBuilding.sortedSpaceArray();
        for(Space office : offices)
        {
            System.out.println(office);
        }

        System.out.println("Проверим исключения:");
        System.out.println("Создадим офис с отрицательным числом комнат:");
        try
        {
            Office testOffice = new Office(-2, 100);
        }
        catch (InvalidRoomsCountException e)
        {
            System.out.println("Ошибка: Некорректное число комнат");
        }

        System.out.println("Запросим офис с номером 9:");
        try
        {
            Space testOffice = officeBuilding.getSpace(9);
        }
        catch (SpaceIndexOutOfBoundsException e) {}

        System.out.println("Запросим этаж с номером -5:");
        try
        {
            Floor testOfficeFloor = officeBuilding.getFloor(-5);
        }
        catch (FloorIndexOutOfBoundsException e) {}

        System.out.println("Изменим площадь первого офиса на отрицательую:");
        try
        {
            officeBuilding.getSpace(0).setArea(-100);
        }
        catch (InvalidSpaceAreaException e) { System.out.println("Ошибка: Некорректное значение площади"); }
    }

    private static void task3Interfaces()
    {
        System.out.println("Проверка interface:\nСоздадим два здания (офисное и жилое) с двумя этажами\n" +
                "(жилым и офисным) с двумя помещениями (офисом и квартирой)");
        DwellingFloor dwellingFloor = new DwellingFloor(new Flat(), new Office());
        OfficeFloor officeFloor = new OfficeFloor(new Office(3, 300), new Flat(5, 100));
        Dwelling dwelling = new Dwelling(officeFloor, dwellingFloor);
        OfficeBuilding officeBuilding = new OfficeBuilding(dwellingFloor, officeFloor);

        System.out.println("Помещения жилого дома:");
        Space[] spaces = dwelling.getSpaces();
        for (Space space : spaces)
            System.out.println(space);

        System.out.println("Помещения офисного здания:");
        spaces = officeBuilding.getSpaces();
        for (Space space : spaces)
            System.out.println(space);
    }

    private static void task4()
    {
        // Создадим три здания, выведем их в System.out
        Floor floor1 = new DwellingFloor(new Flat(1, 100), new Flat(2, 200));
        Floor floor2 = new OfficeFloor(new Office(3,300));
        Floor floor3 = new DwellingFloor(new Flat(4, 400), new Office(5, 500));
        Building building1 = new Dwelling(floor1, floor2);
        Building building2 = new Dwelling(floor1, floor3);
        Building building3 = new OfficeBuilding(floor2, floor3);
        OutputStreamWriter out = new OutputStreamWriter(System.out);
        try
        {
            out.write("Создадим три здания, выведем их в System.out\n");
            Buildings.writeBuilding(building1, out);
            out.write('\n');
            Buildings.writeBuilding(building2, out);
            out.write('\n');
            Buildings.writeBuilding(building3, out);
            out.write("\nЗапишем в три файла: байтовый, текстовый, файл сериализации\n" +
                    "Считаем здания обратно\n" +
                    "Выведем считанные здания для сравнения в System.out\n");
        }
        catch (IOException e){ System.err.println(e.getMessage());}

        // Запишем здания в три файла: байтовый, текстовый, файл сериализации
        try(FileOutputStream out1 = new FileOutputStream("file.bin");
            FileWriter out2 = new FileWriter("file.txt");
            FileOutputStream out3 = new FileOutputStream("file.srz"))
        {
            Buildings.outputBuilding(building1, out1);
            Buildings.writeBuilding(building2, out2);
            Buildings.serializeBuilding(building3, out3);
        }
        catch (IOException e){ System.err.println(e.getMessage());}

        // Считаем здания обратно
        try(FileInputStream in1 = new FileInputStream("file.bin");
            FileReader in2 = new FileReader("file.txt");
            FileInputStream in3 = new FileInputStream("file.srz"))
        {
            building1 = Buildings.inputBuilding(in1);
            building2 = Buildings.readBuilding(in2);
            building3 = Buildings.deserializeBuilding(in3);
        }
        catch (IOException | ClassNotFoundException e) { System.err.println(e.getMessage()); }

        // Выведем считанные здания для сравнения в System.out
        try
        {
            Buildings.writeBuilding(building1, out);
            out.write('\n');
            Buildings.writeBuilding(building2, out);
            out.write('\n');
            Buildings.writeBuilding(building3, out);
            out.flush();
        }
        catch (IOException e){ System.err.println(e.getMessage()); }

        // проверка методов считвания и записи здания с помощью Formatter и Scanner
        // запишем данные о первом здании в файл
        try(FileWriter fout = new FileWriter("fileFormatter.txt"))
        {
            out.write("\nЗапишем и считаем первое здание с помощью методов, использующих Formatter и Scanner\n");
            Buildings.writeBuildingFormat(building1, fout);
        }
        catch (IOException e) { System.err.println(e.getMessage()); }

        // считаем данные из файла обратно
        try(FileReader in = new FileReader("fileFormatter.txt"))
        {
            building1 = Buildings.readBuilding(new Scanner(in));
        }
        catch (IOException e) { System.err.println(e.getMessage()); }

        // выведем в для System.out проверки
        try
        {
            Buildings.writeBuildingFormat(building1, out);
            out.flush();
        }
        catch (IOException e) { System.err.println(e.getMessage()); }
    }

    private static void task5()
    {
        // проверка методов toString()
        System.out.println("toString():");
        Floor floor1 = new DwellingFloor(new Flat(1, 100), new Flat(2, 200));
        Floor floor2 = new OfficeFloor(new Office(3,300));
        Floor floor3 = new DwellingFloor(new Flat(4, 400), new Office(5, 500));
        Floor floor4 = new OfficeFloor(new Flat(6, 400.5), new Office(5, 500.5));
        OfficeBuilding ob = new OfficeBuilding(floor1, floor2, floor3, floor4);
        Dwelling dw = new Dwelling(floor4, floor3, floor2, floor1);
        System.out.println(ob);
        System.out.println(dw);

        // проверка методов equals()
        System.out.println("equals():");
        Floor floor5 = new DwellingFloor(new Flat(1, 100), new Flat(2, 200));
        Floor floor6 = new OfficeFloor(new Office(3,300));
        Floor floor7 = new DwellingFloor(new Flat(4, 400), new Office(5, 500));
        Floor floor8 = new OfficeFloor(new Flat(6, 400.5), new Office(5, 500.5));
        Building ob1 = new OfficeBuilding(floor5, floor6, floor7, floor8);
        Building dw1 = new Dwelling(floor8, floor7, floor6, floor5);
        System.out.println(ob1.equals(ob));
        System.out.println(dw1.equals(dw));
        System.out.println(ob.equals(ob1));
        System.out.println(dw.equals(dw1));

        // проверка методов hashCode()
        System.out.println("hashCode():");
        System.out.println(ob1.hashCode());
        System.out.println(ob.hashCode());
        System.out.println(dw1.hashCode());
        System.out.println((new Dwelling(floor1, floor2, floor2, floor3)).hashCode());

        // проверка методов clone()
        System.out.println("clone():");
        try
        {
            Building building = new OfficeBuilding(floor1, floor2);
            System.out.println(building);
            Building cloneBuilding = (Building)building.clone();
            System.out.println("клон:\n" + cloneBuilding);
            cloneBuilding.setSpace(0, new Flat(1000, 1000));
            System.out.println("поменяли в клоне первое помещение, в оригинале ничего не изменилось:");
            System.out.println(building);
            System.out.println(cloneBuilding);
        }
        catch (CloneNotSupportedException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
