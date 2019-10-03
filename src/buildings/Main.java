package buildings;

import java.util.Random;

public class Main {
    public static void main(String[] args)
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
                Flat flat = new Flat(rnd.nextInt(4), rnd.nextInt(200));
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
    }
}
