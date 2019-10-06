package buildings;

// Узел для односвязного списка офисов
public class SimplyNode
{
    public Office office;
    public SimplyNode next;

    public SimplyNode()
    {
        this(new Office(), null);
    }

    public SimplyNode(Office office)
    {
        this(office, null);
    }

    public SimplyNode(Office office, SimplyNode next)
    {
        this.office = office;
        this.next = next;
    }
}
