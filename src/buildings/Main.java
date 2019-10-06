package buildings;

import buildings.exceptions.*;

import java.util.Random;

public class Main {
    public static void main(String[] args)
    {
        /* task 2
        Random rnd = new Random();
        int floorsCount = 3;
        int flatsCount = 3;
        DwellingFloor[] dwellingFloors = new DwellingFloor[floorsCount];
        for(int i = 0; i < floorsCount; i++)
        {
            dwellingFloors[i] = new DwellingFloor(flatsCount);
            for(int j = 0; j < flatsCount; j++)
            {
                Flat flat = new Flat(1 + rnd.nextInt(4), rnd.nextInt(200));
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
        }*/

        /* task 3 (exceptions)
        Random rnd = new Random();
        int floorsCount = 3;
        int officeCount = 3;
        OfficeFloor[] officeFloors = new OfficeFloor[floorsCount];
        for(int i = 0; i < floorsCount; i++)
        {
            officeFloors[i] = new OfficeFloor(officeCount);
            for(int j = 0; j < officeCount; j++)
            {
                Office office = new Office(1 + rnd.nextInt(4), rnd.nextInt(200));
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
        catch (InvalidSpaceAreaException e) { System.out.println("Ошибка: Некорректное значение площади"); }*/

        // task 3 (interfaces)
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
}
