package buildings;

import buildings.exceptions.*;

public class OfficeBuilding
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
    public OfficeBuilding(int[] array)
    {
        floors = new List(array);
    }

    // конструктор по массиву офисных этажей
    public OfficeBuilding(OfficeFloor[] array)
    {
        floors = new List(array);
    }

    // колличество этажей в здании
    public int floorsCount()
    {
        return floors.length();
    }

    // колличество офисов в здании
    public int officesCount()
    {
        return floors.officesCount();
    }

    // общая площадь помещений здания
    public int Area()
    {
        return floors.totalFloorsArea();
    }

    // общее колличество комнат здания
    public int roomsCount()
    {
        return floors.floorRoomsCount();
    }

    // получить массив этажей здания
    public OfficeFloor[] getFloorsArray()
    {
        return floors.convertToArray();
    }

    // получить этаж по номеру в здании
    public OfficeFloor getFloor(int index)
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
    public void setFloor(int index, OfficeFloor officeFloor)
    {
        try
        {
            floors.setFloor(index, officeFloor);
        }
        catch (FloorIndexOutOfBoundsException e)
        {
            System.out.println("Некорректный номер этажа");
        }
    }

    // получить офис по номеру в здании
    public Office getOffice(int index)
    {
        try
        {
            return floors.getOffice(index);
        }
        catch (SpaceIndexOutOfBoundsException e)
        {
            System.out.println("Некорректный номер офиса");
            throw e;
        }
    }

    // изменить офис по номеру в здании
    public void setOffice(int index, Office office)
    {
        try
        {
            floors.setOffice(index, office);
        }
        catch (SpaceIndexOutOfBoundsException e)
        {
            System.out.println("Некорректный номер офиса");
        }
    }

    // добавить офис по номеру в здании
    public void addOffice(int index, Office office)
    {
        try
        {
            floors.addOffice(index, office);
        }
        catch (SpaceIndexOutOfBoundsException e)
        {
            System.out.println("Некорректный номер офиса");
        }
    }

    // удалить офис по номеру в здании
    public void removeOffice(int index)
    {
        try
        {
            floors.removeOffice(index);
        }
        catch (SpaceIndexOutOfBoundsException e)
        {
            System.out.println("Некорректный номер офиса");
        }
    }

    // наибольший по площади офис здания
    public Office getBestSpace()
    {
        try
        {
            return floors.maxAreaOffice();
        }
        catch (FloorIndexOutOfBoundsException e)
        {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    // получить массив всех офисов в здании
    public Office[] getOffices(){ return floors.getOffices(); }

    // получить отсортированный по убыванию площадей массив офисов
    public Office[] sortedOffices()
    {
        Office[] offices = floors.getOffices();
        qSort(offices, 0, offices.length - 1);
        return offices;
    }

    // быстрая сортировка
    private void qSort(Office[] offices, int left, int right)
    {
        int i = left, j = right;
        int middle = offices[(i + j) / 2].getArea();
        do
        {
            while (offices[i].getArea() > middle)
                i++;
            while (offices[j].getArea() < middle)
                j--;
            if (i <= j)
            {
                Office buffer = offices[i];
                offices[i] = offices[j];
                offices[j] = buffer;
                i++;
                j--;
            }
        } while (i <= j);
        if (left < j)
            this.qSort(offices, left, j);
        if (i < right)
            this.qSort(offices, i, right);
    }
}
