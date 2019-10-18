package buildings.office_building;

import buildings.exceptions.*;
import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;

import java.io.Serializable;

public class OfficeBuilding implements Building, Serializable
{
    private List floors;

    // конструктор по колличеству этажей
    public OfficeBuilding(int count)
    {
        try
        {
            floors = new List(count);
        }
        catch (FloorIndexOutOfBoundsException e)
        {
            System.out.println("Некорректное колличество этажей");
        }
    }

    // конструктор по массиву колличества офисов по этажам
    public OfficeBuilding(int ... array)
    {
        floors = new List(array);
    }

    // конструктор по массиву офисных этажей
    public OfficeBuilding(Floor... array)
    {
        floors = new List(array);
    }

    // колличество этажей в здании
    public int floorCount()
    {
        return floors.length();
    }

    // колличество офисов в здании
    public int spaceCount()
    {
        return floors.officesCount();
    }

    // общая площадь помещений здания
    public int totalSpaceArea()
    {
        return floors.totalFloorsArea();
    }

    // общее колличество комнат здания
    public int totalRoomsCount()
    {
        return floors.floorRoomsCount();
    }

    // получить массив этажей здания
    public Floor[] getFloorArray()
    {
        return floors.convertToArray();
    }

    // получить этаж по номеру в здании
    public Floor getFloor(int index)
    {
        try
        {
            return floors.getFloor(index);
        }
        catch (FloorIndexOutOfBoundsException e)
        {
            System.out.println("Некорректный номер этажа");
            throw e;
        }
    }

    // изменить этаж по номеру в здании
    public void setFloor(int index, Floor floor)
    {
        try
        {
            floors.setFloor(index, floor);
        }
        catch (FloorIndexOutOfBoundsException e)
        {
            System.out.println("Некорректный номер этажа");
        }
    }

    // получить офис по номеру в здании
    public Space getSpace(int index)
    {
        try
        {
            return floors.getSpace(index);
        }
        catch (SpaceIndexOutOfBoundsException e)
        {
            System.out.println("Некорректный номер офиса");
            throw e;
        }
    }

    // изменить офис по номеру в здании
    public void setSpace(int index, Space space)
    {
        try
        {
            floors.setSpace(index, space);
        }
        catch (SpaceIndexOutOfBoundsException e)
        {
            System.out.println("Некорректный номер офиса");
        }
    }

    // добавить офис по номеру в здании
    public void addSpace(int index, Space space)
    {
        try
        {
            floors.addSpace(index, space);
        }
        catch (SpaceIndexOutOfBoundsException e)
        {
            System.out.println("Некорректный номер офиса");
        }
    }

    // удалить офис по номеру в здании
    public void removeSpace(int index)
    {
        try
        {
            floors.removeSpace(index);
        }
        catch (SpaceIndexOutOfBoundsException e)
        {
            System.out.println("Некорректный номер офиса");
        }
    }

    // наибольший по площади офис здания
    public Space getBestSpace()
    {
        try
        {
            return floors.maxAreaSpace();
        }
        catch (FloorIndexOutOfBoundsException e)
        {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    // получить массив всех офисов в здании
    public Space[] getSpaces(){ return floors.getSpaces(); }

    // получить отсортированный по убыванию площадей массив офисов
    public Space[] sortedSpaceArray()
    {
        Space[] spaces = floors.getSpaces();
        qSort(spaces, 0, spaces.length - 1);
        return spaces;
    }

    // быстрая сортировка
    private void qSort(Space[] spaces, int left, int right)
    {
        int i = left, j = right;
        int middle = spaces[(i + j) / 2].getArea();
        do
        {
            while (spaces[i].getArea() > middle)
                i++;
            while (spaces[j].getArea() < middle)
                j--;
            if (i <= j)
            {
                Space buffer = spaces[i];
                spaces[i] = spaces[j];
                spaces[j] = buffer;
                i++;
                j--;
            }
        } while (i <= j);
        if (left < j)
            this.qSort(spaces, left, j);
        if (i < right)
            this.qSort(spaces, i, right);
    }
}
