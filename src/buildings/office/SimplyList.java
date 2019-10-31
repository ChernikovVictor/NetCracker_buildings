package buildings.office;

import buildings.exceptions.*;
import buildings.interfaces.Space;

import java.io.Serializable;

// односвязный циклический список офисов
public class SimplyList implements Serializable
{
    private SimplyNode head;
    public SimplyList(){}

    // получить начало списка
    public SimplyNode getHead()
    {
        return head;
    }

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
    public void addRange(Space... spaces)
    {
        if (spaces == null)
        {
            head = null;
            return;
        }
        if (head == null)
        {
            SimplyNode currentNode = new SimplyNode();
            head = currentNode;
            for(Space space : spaces)
            {
                currentNode.next = new SimplyNode(space);
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
            for(Space space : spaces)
            {
                currentNode.next = new SimplyNode(space);
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
    public double totalOfficesArea()
    {
        if (head == null)
            return 0;
        SimplyNode currentNode = head;
        double area = head.space.getArea();
        while (currentNode.next != head)
        {
            currentNode = currentNode.next;
            area += currentNode.space.getArea();
        }
        return area;
    }

    // общее колличество комнат этажа
    public int totalOfficesRooms()
    {
        if (head == null)
            return 0;
        SimplyNode currentNode = head;
        int count = head.space.getRoomsCount();
        while (currentNode.next != head)
        {
            currentNode = currentNode.next;
            count += currentNode.space.getRoomsCount();
        }
        return count;
    }

    // получить массив офисов этажа
    public Space[] convertToArray()
    {
        if (head == null)
            return null;
        Space[] spaces = new Space[length()];
        SimplyNode currentNode = head;
        int i = 0;
        spaces[i] = head.space;
        while (currentNode.next != head)
        {
            currentNode = currentNode.next;
            i++;
            spaces[i] = currentNode.space;
        }
        return spaces;
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
    public void setNode(int index, Space space)
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
        currentNode.space = space;
    }

    // добавить офис по номеру в списке
    public void addNode(int index, Space space)
    {
        if (index < 0 || index >= this.length())
            throw new SpaceIndexOutOfBoundsException();
        SimplyNode node = new SimplyNode(space);
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
    public Space maxAreaOffice()
    {
        if (head == null)
            throw new SpaceIndexOutOfBoundsException("Список офисов пуст");
        Space result = head.space;
        SimplyNode currentNode = head;
        while(currentNode.next != head)
        {
            currentNode = currentNode.next;
            if (currentNode.space.getArea() > result.getArea())
                result = currentNode.space;
        }
        return result;
    }

    @Override
    public String toString()
    {
        if (head == null)
            return "";
        StringBuffer result = new StringBuffer(", " + head.space.toString());
        int count = 1;
        SimplyNode currentNode = head.next;
        while (currentNode != head)
        {
            result.append(", " + currentNode.space.toString());
            currentNode = currentNode.next;
            count++;
        }
        result.insert(0, count);
        return result.toString();
    }
}
