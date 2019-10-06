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
    public List(int[] array)
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
    public List(OfficeFloor[] array)
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
        int count = head.officeFloor.officesCount();
        while (currentNode.next != head)
        {
            currentNode = currentNode.next;
            count += currentNode.officeFloor.officesCount();
        }
        return count;
    }

    // общая площадь помещений здания
    public int totalFloorsArea()
    {
        if (head == null)
            return 0;
        Node currentNode = head;
        int area = head.officeFloor.totalOfficesArea();
        while (currentNode.next != head)
        {
            currentNode = currentNode.next;
            area += currentNode.officeFloor.totalOfficesArea();
        }
        return area;
    }

    // общее колличество комнат здания
    public int floorRoomsCount()
    {
        if (head == null)
            return 0;
        Node currentNode = head;
        int count = head.officeFloor.totalOfficesRooms();
        while (currentNode.next != head)
        {
            currentNode = currentNode.next;
            count += currentNode.officeFloor.totalOfficesRooms();
        }
        return count;
    }

    // получить массив этажей здания
    public OfficeFloor[] convertToArray()
    {
        if (head == null)
            return null;
        OfficeFloor[] floors = new OfficeFloor[length()];
        Node currentNode = head;
        int i = 0;
        floors[i] = head.officeFloor;
        while (currentNode.next != head)
        {
            currentNode = currentNode.next;
            i++;
            floors[i] = currentNode.officeFloor;
        }
        return floors;
    }

    // получить этаж по номеру в здании
    public OfficeFloor getFloor(int index)
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
        return currentNode.officeFloor;
    }

    // изменить этаж по номеру в здании
    public void setFloor(int index, OfficeFloor officeFloor)
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
        currentNode.officeFloor = officeFloor;
    }

    // Получить номер этажа, на котором находится офис с номером indexOffice для здания, и номер этого офиса на этаже
    private int[] getIndexFloorAndOffice(int indexOffice)
    {
        try
        {
            int indexFloor = 0;
            while (indexOffice >= this.getFloor(indexFloor).officesCount())
            {
                indexOffice -= this.getFloor(indexFloor).officesCount();
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
    public Office getOffice(int index)
    {
        int[] indexFloorAndOffice = getIndexFloorAndOffice(index);
        return getFloor(indexFloorAndOffice[0]).getOffice(indexFloorAndOffice[1]);
    }

    // изменить офис по номеру в здании
    public void setOffice(int index, Office office)
    {
        int[] indexFloorAndOffice = getIndexFloorAndOffice(index);
        getFloor(indexFloorAndOffice[0]).setOffice(indexFloorAndOffice[1], office);
    }

    // добавить офис по номеру в здании
    public void addOffice(int index, Office office)
    {
        int[] indexFloorAndOffice = getIndexFloorAndOffice(index);
        getFloor(indexFloorAndOffice[0]).addOffice(indexFloorAndOffice[1], office);
    }

    // удалить офис по номеру в здании
    public void removeOffice(int index)
    {
        int[] indexFloorAndOffice = getIndexFloorAndOffice(index);
        getFloor(indexFloorAndOffice[0]).removeOffice(indexFloorAndOffice[1]);
    }

    // наибольший по площади офис здания
    public Office maxAreaOffice()
    {
        if (head == null)
            throw new FloorIndexOutOfBoundsException("Список этажей пуст");
        Node currentNode = head;
        Office result = head.officeFloor.getBestSpace();
        while (currentNode.next != head)
        {
            currentNode = currentNode.next;
            if (currentNode.officeFloor.getBestSpace().getArea() > result.getArea())
            {
                result = currentNode.officeFloor.getBestSpace();
            }
        }
        return result;
    }

    // получить массив всех офисов в здании
    public Office[] getOffices()
    {
        Office[] offices = new Office[this.officesCount()];
        int index = 0;
        OfficeFloor[] officeFloors = this.convertToArray();
        for (int i = 0; i < officeFloors.length; i++)
        {
            Office[] officesOnFloor = officeFloors[i].getOfficesArray();
            for (int j = 0; j < officesOnFloor.length; j++)
            {
                offices[index] = officesOnFloor[j];
                index++;
            }
        }
        return offices;
    }
}
