package buildings;

public class Dwelling
{
    DwellingFloor[] floors;

    public Dwelling(int floorsCount, int ... flatsCount)
    {
        floors = new DwellingFloor[floorsCount];
        for(int i = 0; i < floorsCount; i++)
            floors[i] = new DwellingFloor(flatsCount[i]);
    }

    public Dwelling(DwellingFloor[] floors) { this.floors = floors; }

    public int floorsCount() { return floors.length; }
    public DwellingFloor[] getFloors() { return floors; }
    public DwellingFloor getFloor(int index) { return floors[index]; }
    public void setFloor(int index, DwellingFloor floor) { floors[index] = floor; }

    // число всех квартир дома
    public int flatsCount()
    {
        int result = 0;
        for(DwellingFloor floor : floors)
            result += floor.flatsCount();
        return result;
    }

    // общая площадь всех квартир дома
    public int totalFlatsArea()
    {
        int result = 0;
        for(DwellingFloor floor : floors)
            result += floor.totalFlatsArea();
        return result;
    }

    // общее число комнат в доме
    public int roomsCount()
    {
        int result = 0;
        for(DwellingFloor floor : floors)
        {
            result += floor.totalRoomsCount();
        }
        return result;
    }

    // Получить номер этажа, на котором находится квартира с номером indexFlat для дома, и номер этой квартиры на этаже
    private int[] getIndexFloorAndFlat(int indexFlat)
    {
        int indexFloor = 0;
        while (indexFlat >= floors[indexFloor].flatsCount())
        {
            indexFlat -= floors[indexFloor].flatsCount();
            indexFloor++;
        }
        return new int[] {indexFloor, indexFlat};
    }

    public Flat getFlat(int index)
    {
        int[] indexFloorAndFlat = getIndexFloorAndFlat(index);
        return floors[indexFloorAndFlat[0]].getFlat(indexFloorAndFlat[1]);
    }

    public void setFlat(int index, Flat flat)
    {
        int[] indexFloorAndFlat = getIndexFloorAndFlat(index);
        floors[indexFloorAndFlat[0]].setFlat(indexFloorAndFlat[1], flat);
    }

    public void addFlat(int index, Flat flat)
    {
        int[] indexFloorAndFlat = getIndexFloorAndFlat(index);
        floors[indexFloorAndFlat[0]].addFlat(indexFloorAndFlat[1], flat);
    }

    public void removeFlat(int index)
    {
        int[] indexFloorAndFlat = getIndexFloorAndFlat(index);
        floors[indexFloorAndFlat[0]].removeFlat(indexFloorAndFlat[1]);
    }

    // Наибольшая по площади квартира в доме
    public Flat getBestSpace()
    {
        Flat result = floors[0].getBestSpace();
        for(int i = 1; i < floors.length; i++)
        {
            Flat flat = floors[i].getBestSpace();
            if (result.getArea() < flat.getArea())
                result = flat;
        }
        return result;
    }

    // получение массива всех квартир в доме
    public Flat[] getFlats()
    {
        Flat[] flats = new Flat[flatsCount()];
        int index = 0;
        for(DwellingFloor floor : floors)
        {
            Flat[] flatsOnFloor = floor.getFlats();
            for(int i = 0; i < flatsOnFloor.length; i++)
            {
                flats[index] = flatsOnFloor[i];
                index++;
            }
        }
        return flats;
    }

    // получить отсортированный по убыванию площадей массив квартир
    public Flat[] sortedFlats()
    {
        Flat[] flats = getFlats();
        qSort(flats, 0, flats.length - 1);
        return flats;
    }

    // быстрая сортировка
    private void qSort(Flat[] flats, int left, int right)
    {
        int i = left, j = right;
        int middle = flats[(i + j) / 2].getArea();
        do
        {
            while (flats[i].getArea() > middle)
                i++;
            while (flats[j].getArea() < middle)
                j--;
            if (i <= j)
            {
                Flat buffer = flats[i];
                flats[i] = flats[j];
                flats[j] = buffer;
                i++;
                j--;
            }
        } while (i <= j);
        if (left < j)
            this.qSort(flats, left, j);
        if (i < right)
            this.qSort(flats, i, right);
    }
}
