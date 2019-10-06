package buildings;

import buildings.exceptions.*;

// односвязный циклический список офисов
public class SimplyList
{
    private SimplyNode head;
    public SimplyList(){}

    // конструктор по колличеству офисов
    public SimplyList(int count)
    {
        if (count <= 0)
            throw new SpaceIndexOutOfBoundsException();
        head = new SimplyNode();
        head.next = head;
        SimplyNode currentNode = head;
        for (int i = 1; i < count; i++)
        {
            currentNode.next = new SimplyNode(null, head);
            currentNode = currentNode.next;
        }
    }

    // добавить в конец списка
    public void add(Office office)
    {
        if (head == null)
        {
            head = new SimplyNode(office);
            head.next = head;
        }
        else
        {
            SimplyNode currentNode = head;
            while(currentNode.next != head)
                currentNode = currentNode.next;
            currentNode.next = new SimplyNode(office);
            (currentNode.next).next = head;
        }
    }

    // добавить несколько элементов в конец списка
    public void addRange(Office ... offices)
    {
        if (offices == null)
        {
            head = null;
            return;
        }
        if (head == null)
        {
            SimplyNode currentNode = new SimplyNode();
            head = currentNode;
            for(Office office : offices)
            {
                currentNode.next = new SimplyNode(office);
                currentNode = currentNode.next;
            }
            currentNode.next = head.next;
            head = head.next;
        }
        else
        {
            SimplyNode currentNode = head;
            while(currentNode.next != head)
                currentNode = currentNode.next;
            for(Office office : offices)
            {
                currentNode.next = new SimplyNode(office);
                currentNode = currentNode.next;
            }
            currentNode.next = head;
        }
    }

    // длина списка (колличество офисов)
    public int length()
    {
        if (head == null)
            return 0;
        int count = 1;
        SimplyNode currentNode = head;
        while (currentNode.next != head)
        {
            count++;
            currentNode = currentNode.next;
        }
        return count;
    }

    // общая площадь офисов
    public int totalOfficesArea()
    {
        if (head == null)
            return 0;
        SimplyNode currentNode = head;
        int area = head.office.getArea();
        while (currentNode.next != head)
        {
            currentNode = currentNode.next;
            area += currentNode.office.getArea();
        }
        return area;
    }

    // общее колличество комнат этажа
    public int totalOfficesRooms()
    {
        if (head == null)
            return 0;
        SimplyNode currentNode = head;
        int count = head.office.getRoomsCount();
        while (currentNode.next != head)
        {
            currentNode = currentNode.next;
            count += currentNode.office.getRoomsCount();
        }
        return count;
    }

    // получить массив офисов этажа
    public Office[] convertToArray()
    {
        if (head == null)
            return null;
        Office[] offices = new Office[length()];
        SimplyNode currentNode = head;
        int i = 0;
        offices[i] = head.office;
        while (currentNode.next != head)
        {
            currentNode = currentNode.next;
            i++;
            offices[i] = currentNode.office;
        }
        return offices;
    }

    // получение офиса по номеру на этаже
    public SimplyNode getNode(int index)
    {
        if (index < 0 || index >= this.length())
            throw new SpaceIndexOutOfBoundsException();
        int i = 0;
        SimplyNode currentNode = head;
        while (i < index)
        {
            currentNode = currentNode.next;
            i++;
        }
        return currentNode;
    }

    // изменить офис по номеру в списке
    public void setOffice(int index, Office office)
    {
        if (index < 0 || index >= this.length())
            throw new SpaceIndexOutOfBoundsException();
        int i = 0;
        SimplyNode currentNode = head;
        while (i < index)
        {
            currentNode = currentNode.next;
            i++;
        }
        currentNode.office = office;
    }

    // добавить офис по номеру в списке
    public void addNode(int index, Office office)
    {
        if (index < 0 || index >= this.length())
            throw new SpaceIndexOutOfBoundsException();
        SimplyNode node = new SimplyNode(office);
        if (index == 0)
        {
            SimplyNode currentNode = head.next;
            while (currentNode.next != head)
                currentNode = currentNode.next;
            currentNode.next = node;
            node.next = head;
            head = node;
        }
        else
        {
            int i = 1;
            SimplyNode currentNode = head.next;
            while (i < index - 1)
            {
                currentNode = currentNode.next;
                i++;
            }
            node.next = currentNode.next;
            currentNode.next = node;
        }
    }

    // удаление узла по номеру на этаже
    public void removeNode(int index)
    {
        if (index < 0 || index >= this.length())
            throw new SpaceIndexOutOfBoundsException();
        if (index == 0)
        {
            SimplyNode currentNode = head.next;
            while (currentNode.next != head)
                currentNode = currentNode.next;
            currentNode.next = head.next;
            head = head.next;
        }
        else
        {
            int i = 1;
            SimplyNode currentNode = head.next;
            while (i < index - 1)
            {
                currentNode = currentNode.next;
                i++;
            }
            currentNode.next = (currentNode.next).next;
        }
    }

    // наибольший по площади офис этажа
    public Office maxAreaOffice()
    {
        if (head == null)
            throw new SpaceIndexOutOfBoundsException("Список офисов пуст");
        Office result = head.office;
        SimplyNode currentNode = head;
        while(currentNode.next != head)
        {
            currentNode = currentNode.next;
            if (currentNode.office.getArea() > result.getArea())
                result = currentNode.office;
        }
        return result;
    }
}
