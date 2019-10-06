package buildings;

// Узел для двусвязного списка офисных этажей
public class Node
{
    public OfficeFloor officeFloor;
    public Node next, previous;

    public Node() {}

    public Node(OfficeFloor officeFloor)
    {
        this.officeFloor = officeFloor;
    }

    // конструктор по колличеству офисов на этаже
    public Node(int count)
    {
        officeFloor = new OfficeFloor(count);
    }
}
