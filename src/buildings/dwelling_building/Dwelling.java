package buildings.dwelling_building;

import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;

import java.io.Serializable;

public class Dwelling implements Building, Serializable
{
    private Floor[] floors;

    public Dwelling(int floorsCount, int ... flatsCount)
    {
        floors = new Floor[floorsCount];
        for(int i = 0; i < floorsCount; i++)
            floors[i] = new DwellingFloor(flatsCount[i]);
    }

    public Dwelling(Floor ... floors) { this.floors = floors; }

    public int floorCount() { return floors.length; }
    public Floor[] getFloorArray() { return floors; }
    public Floor getFloor(int index) { return floors[index]; }
    public void setFloor(int index, Floor floor) { floors[index] = floor; }

    // число всех квартир дома
    public int spaceCount()
    {
        int result = 0;
        for(Floor floor : floors)
            result += floor.spaceCount();
        return result;
    }

    // общая площадь всех квартир дома
    public double totalSpaceArea()
    {
        int result = 0;
        for(Floor floor : floors)
            result += floor.totalSpaceArea();
        return result;
    }

    // общее число комнат в доме
    public int totalRoomsCount()
    {
        int result = 0;
        for(Floor floor : floors)
        {
            result += floor.totalRoomsCount();
        }
        return result;
    }

    // Получить номер этажа, на котором находится квартира с номером indexFlat для дома, и номер этой квартиры на этаже
    private int[] getIndexFloorAndFlat(int indexFlat)
    {
        int indexFloor = 0;
        while (indexFlat >= floors[indexFloor].spaceCount())
        {
            indexFlat -= floors[indexFloor].spaceCount();
            indexFloor++;
        }
        return new int[] {indexFloor, indexFlat};
    }

    public Space getSpace(int index)
    {
        int[] indexFloorAndFlat = getIndexFloorAndFlat(index);
        return floors[indexFloorAndFlat[0]].getSpace(indexFloorAndFlat[1]);
    }

    public void setSpace(int index, Space space)
    {
        int[] indexFloorAndFlat = getIndexFloorAndFlat(index);
        floors[indexFloorAndFlat[0]].setSpace(indexFloorAndFlat[1], space);
    }

    public void addSpace(int index, Space space)
    {
        int[] indexFloorAndFlat = getIndexFloorAndFlat(index);
        floors[indexFloorAndFlat[0]].addSpace(indexFloorAndFlat[1], space);
    }

    public void removeSpace(int index)
    {
        int[] indexFloorAndFlat = getIndexFloorAndFlat(index);
        floors[indexFloorAndFlat[0]].removeSpace(indexFloorAndFlat[1]);
    }

    // Наибольшая по площади квартира в доме
    public Space getBestSpace()
    {
        Space result = floors[0].getBestSpace();
        for(int i = 1; i < floors.length; i++)
        {
            Space space = floors[i].getBestSpace();
            if (result.getArea() < space.getArea())
                result = space;
        }
        return result;
    }

    // получение массива всех квартир в доме
    public Space[] getSpaces()
    {
        Space[] spaces = new Space[spaceCount()];
        int index = 0;
        for(Floor floor : floors)
        {
            Space[] flatsOnFloor = floor.getSpaceArray();
            for(int i = 0; i < flatsOnFloor.length; i++)
            {
                spaces[index] = flatsOnFloor[i];
                index++;
            }
        }
        return spaces;
    }

    // получить отсортированный по убыванию площадей массив квартир
    public Space[] sortedSpaceArray()
    {
        Space[] spaces = getSpaces();
        qSort(spaces, 0, spaces.length - 1);
        return spaces;
    }

    // быстрая сортировка
    private void qSort(Space[] flats, int left, int right)
    {
        int i = left, j = right;
        double middle = flats[(i + j) / 2].getArea();
        do
        {
            while (flats[i].getArea() > middle)
                i++;
            while (flats[j].getArea() < middle)
                j--;
            if (i <= j)
            {
                Space buffer = flats[i];
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