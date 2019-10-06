package buildings;

import buildings.exceptions.*;

// двусвязный циклический список офисных этажей
public class List
{
    private Node head;

    // конструктор по колличеству этажей
    public List(int count)
    {
        if (count <= 0)
            throw new FloorIndexOutOfBoundsException();
        head = new Node();
        Node currentNode = head;
        for (int i = 1; i < count; i++)
        {
            currentNode.next = new Node();
            (currentNode.next).previous = currentNode;
            currentNode = currentNode.next;
        }
        currentNode.next = head;
        head.previous = currentNode;
    }

    // конструктор по массиву колличества офисов по этажам
    public List(int ... array)
    {
        head = new Node(array[0]);
        Node currentNode = head;
        for (int i = 1; i < array.length; i++)
        {
            currentNode.next = new Node(array[i]);
            (currentNode.next).previous = currentNode;
            currentNode = currentNode.next;
        }
        currentNode.next = head;
        head.previous = currentNode;
    }

    // конструктор по массиву офисных этажей
    public List(Floor ... array)
    {
        head = new Node(array[0]);
        Node currentNode = head;
        for (int i = 1; i < array.length; i++)
        {
            currentNode.next = new Node(array[i]);
            (currentNode.next).previous = currentNode;
            currentNode = currentNode.next;
        }
        currentNode.next = head;
        head.previous = currentNode;
    }

    // длина списка (колличество этажей в здании)
    public int length()
    {
        if (head == null)
            return 0;
        int count = 1;
        Node currentNode = head;
        while (currentNode.next != head)
        {
            count++;
            currentNode = currentNode.next;
        }
        return count;
    }

    // колличество офисов в здании
    public int officesCount()
    {
        if (head == null)
            return 0;
        Node currentNode = head;
        int count = head.floor.spaceCount();
        while (currentNode.next != head)
        {
            currentNode = currentNode.next;
            count += currentNode.floor.spaceCount();
        }
        return count;
    }

    // общая площадь помещений здания
    public int totalFloorsArea()
    {
        if (head == null)
            return 0;
        Node currentNode = head;
        int area = head.floor.totalSpaceArea();
        while (currentNode.next != head)
        {
            currentNode = currentNode.next;
            area += currentNode.floor.totalSpaceArea();
        }
        return area;
    }

    // общее колличество комнат здания
    public int floorRoomsCount()
    {
        if (head == null)
            return 0;
        Node currentNode = head;
        int count = head.floor.totalRoomsCount();
        while (currentNode.next != head)
        {
            currentNode = currentNode.next;
            count += currentNode.floor.totalRoomsCount();
        }
        return count;
    }

    // получить массив этажей здания
    public Floor[] convertToArray()
    {
        if (head == null)
            return null;
        Floor[] floors = new Floor[length()];
        Node currentNode = head;
        int i = 0;
        floors[i] = head.floor;
        while (currentNode.next != head)
        {
            currentNode = currentNode.next;
            i++;
            floors[i] = currentNode.floor;
        }
        return floors;
    }

    // получить этаж по номеру в здании
    public Floor getFloor(int index)
    {
        if (index < 0 || index >= this.length())
            throw new FloorIndexOutOfBoundsException();
        int i = 0;
        Node currentNode = head;
        while (i < index)
        {
            currentNode = currentNode.next;
            i++;
        }
        return currentNode.floor;
    }

    // изменить этаж по номеру в здании
    public void setFloor(int index, Floor floor)
    {
        if (index < 0 || index >= this.length())
            throw new FloorIndexOutOfBoundsException();
        int i = 0;
        Node currentNode = head;
        while (i < index)
        {
            currentNode = currentNode.next;
            i++;
        }
        currentNode.floor = floor;
    }

    // Получить номер этажа, на котором находится офис с номером indexOffice для здания, и номер этого офиса на этаже
    private int[] getIndexFloorAndOffice(int indexOffice)
    {
        try
        {
            int indexFloor = 0;
            while (indexOffice >= this.getFloor(indexFloor).spaceCount())
            {
                indexOffice -= this.getFloor(indexFloor).spaceCount();
                indexFloor++;
            }
            return new int[] {indexFloor, indexOffice};
        }
        catch (FloorIndexOutOfBoundsException e)
        {
            throw new SpaceIndexOutOfBoundsException();
        }
    }

    // получить офис по номеру в здании
    public Space getSpace(int index)
    {
        int[] indexFloorAndOffice = getIndexFloorAndOffice(index);
        return getFloor(indexFloorAndOffice[0]).getSpace(indexFloorAndOffice[1]);
    }

    // изменить офис по номеру в здании
    public void setSpace(int index, Space space)
    {
        int[] indexFloorAndOffice = getIndexFloorAndOffice(index);
        getFloor(indexFloorAndOffice[0]).setSpace(indexFloorAndOffice[1], space);
    }

    // добавить офис по номеру в здании
    public void addSpace(int index, Space space)
    {
        int[] indexFloorAndOffice = getIndexFloorAndOffice(index);
        getFloor(indexFloorAndOffice[0]).addSpace(indexFloorAndOffice[1], space);
    }

    // удалить офис по номеру в здании
    public void removeSpace(int index)
    {
        int[] indexFloorAndOffice = getIndexFloorAndOffice(index);
        getFloor(indexFloorAndOffice[0]).removeSpace(indexFloorAndOffice[1]);
    }

    // наибольший по площади офис здания
    public Space maxAreaSpace()
    {
        if (head == null)
            throw new FloorIndexOutOfBoundsException("Список этажей пуст");
        Node currentNode = head;
        Space result = head.floor.getBestSpace();
        while (currentNode.next != head)
        {
            currentNode = currentNode.next;
            if (currentNode.floor.getBestSpace().getArea() > result.getArea())
            {
                result = currentNode.floor.getBestSpace();
            }
        }
        return result;
    }

    // получить массив всех офисов в здании
    public Space[] getSpaces()
    {
        Space[] spaces = new Space[this.officesCount()];
        int index = 0;
        Floor[] floors = this.convertToArray();
        for (int i = 0; i < floors.length; i++)
        {
            Space[] spacesOnFloor = floors[i].getSpaceArray();
            for (int j = 0; j < spacesOnFloor.length; j++)
            {
                spaces[index] = spacesOnFloor[j];
                index++;
            }
        }
        return spaces;
    }
}
