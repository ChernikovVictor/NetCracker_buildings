package buildings.office;

import buildings.exceptions.*;
import buildings.interfaces.*;

import java.io.Serializable;
import java.util.Iterator;

public class OfficeBuilding implements Building, Serializable
{
    private List floors;

    public OfficeBuilding(){ floors = new List(); }

    /* конструктор по колличеству этажей */
    public OfficeBuilding(int count) {
        try {
            floors = new List(count);
        } catch (FloorIndexOutOfBoundsException e) {
            System.out.println("Некорректное колличество этажей");
        }
    }

    /* конструктор по массиву колличества офисов по этажам */
    public OfficeBuilding(int ... array)
    {
        floors = new List(array);
    }

    /* конструктор по массиву офисных этажей */
    public OfficeBuilding(Floor ... array) {
        floors = new List(array);
    }

    /* колличество этажей в здании */
    public int floorCount()
    {
        return floors.length();
    }

    /* колличество офисов в здании */
    public int spaceCount()
    {
        return floors.officesCount();
    }

    /* общая площадь помещений здания */
    public double totalSpaceArea()
    {
        return floors.totalFloorsArea();
    }

    /* общее колличество комнат здания */
    public int totalRoomsCount()
    {
        return floors.floorRoomsCount();
    }

    /* получить массив этажей здания */
    public Floor[] getFloorArray()
    {
        return floors.convertToArray();
    }

    /* получить этаж по номеру в здании */
    public Floor getFloor(int index) {
        try {
            return floors.getFloor(index);
        } catch (FloorIndexOutOfBoundsException e) {
            System.out.println("Некорректный номер этажа");
            throw e;
        }
    }

    /* изменить этаж по номеру в здании */
    public void setFloor(int index, Floor floor) {
        try {
            floors.setFloor(index, floor);
        } catch (FloorIndexOutOfBoundsException e) {
            System.out.println("Некорректный номер этажа");
        }
    }

    /* получить офис по номеру в здании */
    public Space getSpace(int index) {
        try {
            return floors.getSpace(index);
        } catch (SpaceIndexOutOfBoundsException e) {
            System.out.println("Некорректный номер офиса");
            throw e;
        }
    }

    /* изменить офис по номеру в здании */
    public void setSpace(int index, Space space) {
        try {
            floors.setSpace(index, space);
        } catch (SpaceIndexOutOfBoundsException e) {
            System.out.println("Некорректный номер офиса");
        }
    }

    /* добавить офис по номеру в здании */
    public void addSpace(int index, Space space) {
        try {
            floors.addSpace(index, space);
        } catch (SpaceIndexOutOfBoundsException e) {
            System.out.println("Некорректный номер офиса");
        }
    }

    /* удалить офис по номеру в здании */
    public void removeSpace(int index) {
        try {
            floors.removeSpace(index);
        } catch (SpaceIndexOutOfBoundsException e) {
            System.out.println("Некорректный номер офиса");
        }
    }

    /* наибольший по площади офис здания */
    public Space getBestSpace()
    {
        try {
            return floors.maxAreaSpace();
        } catch (FloorIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    /* получить массив всех офисов в здании */
    public Space[] getSpaces(){ return floors.getSpaces(); }

    /* получить отсортированный по убыванию площадей массив офисов */
    public Space[] sortedSpaceArray() {
        Space[] spaces = floors.getSpaces();
        qSort(spaces, 0, spaces.length - 1);
        return spaces;
    }

    /* быстрая сортировка */
    private void qSort(Space[] spaces, int left, int right) {
        int i = left, j = right;
        double middle = spaces[(i + j) / 2].getArea();
        do {
            while (spaces[i].getArea() > middle)
                i++;
            while (spaces[j].getArea() < middle)
                j--;
            if (i <= j) {
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

    /* получить начало списка */
    public Node getHead() { return floors.getHead(); }

    @Override
    public String toString() {
        return String.format("OfficeBuilding (%s)", floors.toString());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (!(object instanceof OfficeBuilding))
            return false;

        OfficeBuilding building = (OfficeBuilding)object;
        Node head1 = building.getHead(), head2 = this.getHead();
        if (head1 == null && head2 == null)
            return true;
        if (head1 == null || head2 == null)
            return false;

        // поэлементное сравнение
        if (!head1.floor.equals(head2.floor))
            return false;
        Node node1 = head1.next, node2 = head2.next;
        while(node1 != head1 || node2 != head2) {
            if (!node1.floor.equals(node2.floor))
                return false;
            node1 = node1.next;
            node2 = node2.next;
        }

        return true;
    }

    @Override
    public int hashCode() {
        Node head = floors.getHead();
        if (head == null)
            return 0;
        int result = head.floor.hashCode(), count = 1;
        Node currentNode = head.next;
        while (currentNode != head) {
            result = result ^ currentNode.floor.hashCode();
            count++;
            currentNode = currentNode.next;
        }
        return result ^ count;
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        int count = this.floorCount();
        if (count == 0)
            return new OfficeBuilding();
        Floor[] result = new Floor[count];
        Node currentNode = this.getHead();
        for (int i = 0; i < count; i++)
        {
            result[i] = (Floor)currentNode.floor.clone();
            currentNode = currentNode.next;
        }
        return new OfficeBuilding(result);
    }

    /* итератор по этажам здания */
    @Override
    public Iterator<Floor> iterator()
    {
        return new floorIterator(this);
    }

    /* класс итератора по этажам здания */
    private class floorIterator implements Iterator<Floor>
    {
        private Node node;
        int index, count;

        public floorIterator(OfficeBuilding officeBuilding) {
            index = -1;
            count = officeBuilding.floorCount();
            // Node указывает на последний элемент, т.к. список циклический
            node = officeBuilding.getHead();
            for (int i = 0; i < count - 1; i++)
                node = node.next;
        }

        @Override
        public boolean hasNext()
        {
            return index + 1 < count;
        }

        @Override
        public Floor next() {
            index++;
            node = node.next;
            return node.floor;
        }
    }
}
