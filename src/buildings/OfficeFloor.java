package buildings;

import buildings.exceptions.*;

public class OfficeFloor
{
    private SimplyList offices;

    // конструктор по колличеству офисов
    public OfficeFloor(int count)
    {
        try
        {
            offices = new SimplyList(count);
        }
        catch (SpaceIndexOutOfBoundsException e)
        {
            System.out.println("Некорректное колличество офисов для этажа");
        }
    }

    // конструктор по массиву офисов
    public OfficeFloor(Office[] arrayOffices)
    {
        offices.addRange(arrayOffices);
    }

    // число офисов на этаже
    public int officesCount()
    {
        return offices.length();
    }

    // общая площадь офисов этажа
    public int totalOfficesArea()
    {
        return offices.totalOfficesArea();
    }

    // общее колличество комнат этажа
    public int totalOfficesRooms()
    {
        return offices.totalOfficesRooms();
    }

    // получить массив офисов этажа
    public Office[] getOfficesArray()
    {
        return offices.convertToArray();
    }

    // получить офис по номеру на этаже
    public Office getOffice(int index)
    {
        try
        {
            return offices.getNode(index).office;
        }
        catch (SpaceIndexOutOfBoundsException e)
        {
            System.out.println("Некорректный номер офиса");
            throw e;
        }
    }

    // изменить офис по номеру на этаже
    public void setOffice(int index, Office office)
    {
        try
        {
            offices.setOffice(index, office);
        }
        catch (SpaceIndexOutOfBoundsException e)
        {
            System.out.println("Некорректный номер офиса");
        }
    }

    // добавить офис по будущему номеру на этаже
    public void addOffice(int index, Office office)
    {
        try
        {
            offices.addNode(index, office);
        }
        catch (SpaceIndexOutOfBoundsException e)
        {
            System.out.println("Некорректный номер офиса");
        }
    }

    // удалить офис по номеру на этаже
    public void removeOffice(int index)
    {
        try
        {
            offices.removeNode(index);
        }
        catch (SpaceIndexOutOfBoundsException e)
        {
            System.out.println("Некорректный номер офиса");
        }
    }

    // наибольший по площади офис этажа
    public Office getBestSpace()
    {
        try
        {
            return offices.maxAreaOffice();
        }
        catch (SpaceIndexOutOfBoundsException e)
        {
            System.out.println(e.getMessage());
            throw e;
        }
    }
}
