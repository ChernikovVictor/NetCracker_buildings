package buildings.office;

import buildings.exceptions.*;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;

import java.io.Serializable;

public class OfficeFloor implements Floor, Serializable, Cloneable
{
    private SimplyList spaces;

    public OfficeFloor(){ spaces = new SimplyList(); }

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
    public double totalSpaceArea()
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

    // получить начало списка
    public SimplyNode getHead()
    {
        return spaces.getHead();
    }

    @Override
    public String toString()
    {
        return "OfficeFloor (" + spaces.toString() + ")";
    }

    @Override
    public boolean equals(Object object)
    {
        if (this == object)
            return true;
        if (!(object instanceof OfficeFloor))
            return false;
        OfficeFloor floor = (OfficeFloor)object;
        SimplyNode head1 = floor.getHead(), head2 = this.getHead();
        if (head1 == null && head2 == null)
            return true;
        if (head1 == null && head2 != null || head1 != null && head2 == null)
            return false;
        // поэлементное сравнение
        if (!head1.space.equals(head2.space))
            return false;
        SimplyNode node1 = head1.next, node2 = head2.next;
        while(node1 != head1 || node2 != head2)
        {
            if (!node1.space.equals(node2.space))
                return false;
            node1 = node1.next;
            node2 = node2.next;
        }
        return node1 == head1 && node2 == head2;
    }

    @Override
    public int hashCode()
    {
        SimplyNode head = spaces.getHead();
        if (head == null)
            return 0;
        int result = head.space.hashCode(), count = 1;
        SimplyNode currentNode = head.next;
        while (currentNode != head)
        {
            result = result ^ currentNode.space.hashCode();
            count++;
            currentNode = currentNode.next;
        }
        return result ^ count;
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        int count = this.spaceCount();
        if (count == 0)
            return new OfficeFloor();
        Space[] result = new Space[count];
        SimplyNode currentNode = this.getHead();
        for (int i = 0; i < count; i++)
        {
            result[i] = (Space)currentNode.space.clone();
            currentNode = currentNode.next;
        }
        return new OfficeFloor(result);
    }
}
