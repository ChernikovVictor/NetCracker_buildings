package buildings.office_building;

import buildings.exceptions.*;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;

import java.io.Serializable;

public class OfficeFloor implements Floor, Serializable
{
    private SimplyList spaces;

    // конструктор по колличеству офисов
    public OfficeFloor(int count)
    {
        try
        {
            spaces = new SimplyList(count);
        }
        catch (SpaceIndexOutOfBoundsException e)
        {
            System.out.println("Некорректное колличество офисов для этажа");
        }
    }

    // конструктор по массиву офисов
    public OfficeFloor(Space... arrayOffices)
    {
        spaces = new SimplyList();
        spaces.addRange(arrayOffices);
    }

    // число офисов на этаже
    public int spaceCount()
    {
        return spaces.length();
    }

    // общая площадь офисов этажа
    public int totalSpaceArea()
    {
        return spaces.totalOfficesArea();
    }

    // общее колличество комнат этажа
    public int totalRoomsCount()
    {
        return spaces.totalOfficesRooms();
    }

    // получить массив офисов этажа
    public Space[] getSpaceArray()
    {
        return spaces.convertToArray();
    }

    // получить офис по номеру на этаже
    public Space getSpace(int index)
    {
        try
        {
            return spaces.getNode(index).space;
        }
        catch (SpaceIndexOutOfBoundsException e)
        {
            System.out.println("Некорректный номер офиса");
            throw e;
        }
    }

    // изменить офис по номеру на этаже
    public void setSpace(int index, Space space)
    {
        try
        {
            spaces.setNode(index, space);
        }
        catch (SpaceIndexOutOfBoundsException e)
        {
            System.out.println("Некорректный номер офиса");
        }
    }

    // добавить офис по будущему номеру на этаже
    public void addSpace(int index, Space space)
    {
        try
        {
            spaces.addNode(index, space);
        }
        catch (SpaceIndexOutOfBoundsException e)
        {
            System.out.println("Некорректный номер офиса");
        }
    }

    // удалить офис по номеру на этаже
    public void removeSpace(int index)
    {
        try
        {
            spaces.removeNode(index);
        }
        catch (SpaceIndexOutOfBoundsException e)
        {
            System.out.println("Некорректный номер офиса");
        }
    }

    // наибольший по площади офис этажа
    public Space getBestSpace()
    {
        try
        {
            return spaces.maxAreaOffice();
        }
        catch (SpaceIndexOutOfBoundsException e)
        {
            System.out.println(e.getMessage());
            throw e;
        }
    }
}
