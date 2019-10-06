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
                dwellingFloors[i].setFlat(j, flat);
            }
        }

        Dwelling dwelling = new Dwelling(dwellingFloors);
        Flat[] Flats = dwelling.getFlats();
        System.out.println("Квартиры в доме:");
        for(int i = 0; i < Flats.length; i++)
        {
            System.out.println(i + ": " + Flats[i]);
        }

        System.out.println("Число всех квартир дома " + dwelling.flatsCount());
        System.out.println("Общая площадь всех квартир дома " + dwelling.totalFlatsArea());
        System.out.println("Общее число всех комнат дома " + dwelling.roomsCount());
        System.out.println("Квартира с максимальной площадью: " + dwelling.getBestSpace());

        System.out.println("Квартира с индексом 6: " + dwelling.getFlat(6));
        System.out.println("Заменим ее на квартиру по умолчанию");
        dwelling.setFlat(6, new Flat());

        System.out.println("Квартира с индексом 4: " + dwelling.getFlat(4));
        System.out.println("Удалим ее");
        dwelling.removeFlat(4);

        System.out.println("Добавим квартиру из 5 комнат с площадью 300 кв.м. по индексу 3");
        dwelling.addFlat(3, new Flat(5, 300));

        System.out.println("Проверим изменения:");
        Flats = dwelling.getFlats();
        for(int i = 0; i < Flats.length; i++)
        {
            System.out.println(i + ": " + Flats[i]);
        }

        System.out.println("Отсортируем по убыванию площадей:");
        Flats = dwelling.sortedFlats();
        for(Flat flat : Flats)
        {
            System.out.println(flat);
        }
        */

        // task 3
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
                officeFloors[i].setOffice(j, office);
            }
        }

        OfficeBuilding officeBuilding = new OfficeBuilding(officeFloors);
        Office[] offices = officeBuilding.getOffices();
        System.out.println("Офисы в здании:");
        for(int i = 0; i < offices.length; i++)
        {
            System.out.println(i + ": " + offices[i]);
        }

        System.out.println("Число всех офисов здания " + officeBuilding.officesCount());
        System.out.println("Общая площадь всех офисов здания " + officeBuilding.Area());
        System.out.println("Общее число всех комнат здания " + officeBuilding.roomsCount());
        System.out.println("Офис с максимальной площадью: " + officeBuilding.getBestSpace());

        System.out.println("Офис с индексом 6: " + officeBuilding.getOffice(6));
        System.out.println("Заменим его на офис по умолчанию");
        officeBuilding.setOffice(6, new Office());

        System.out.println("Офис с индексом 4: " + officeBuilding.getOffice(4));
        System.out.println("Удалим его");
        officeBuilding.removeOffice(4);

        System.out.println("Добавим офис из 5 комнат с площадью 300 кв.м. по индексу 3");
        officeBuilding.addOffice(3, new Office(5, 300));

        System.out.println("Проверим изменения:");
        offices = officeBuilding.getOffices();
        for(int i = 0; i < offices.length; i++)
        {
            System.out.println(i + ": " + offices[i]);
        }

        System.out.println("Отсортируем по убыванию площадей:");
        offices = officeBuilding.sortedOffices();
        for(Office office : offices)
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
            Office testOffice = officeBuilding.getOffice(9);
        }
        catch (SpaceIndexOutOfBoundsException e) {}

        System.out.println("Запросим этаж с номером -5:");
        try
        {
            OfficeFloor testOfficeFloor = officeBuilding.getFloor(-5);
        }
        catch (FloorIndexOutOfBoundsException e) {}

        System.out.println("Изменим площадь первого офиса на отрицательую:");
        try
        {
            officeBuilding.getOffice(0).setArea(-100);
        }
        catch (InvalidSpaceAreaException e) { System.out.println("Ошибка: Некорректное значение площади"); }
    }
}
