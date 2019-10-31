package buildings.office;

import buildings.interfaces.Floor;

import java.io.Serializable;

// Узел для двусвязного списка офисных этажей
public class Node implements Serializable
{
    public Floor floor;
    public Node next, previous;

    public Node() {}

    public Node(Floor floor)
    {
        this.floor = floor;
    }

    // конструктор по колличеству офисов на этаже
    public Node(int count)
    {
        floor = new OfficeFloor(count);
    }
}
