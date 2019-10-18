package buildings.office_building;

import buildings.interfaces.Space;

import java.io.Serializable;

// Узел для односвязного списка офисов
public class SimplyNode implements Serializable
{
    public Space space;
    public SimplyNode next;

    public SimplyNode()
    {
        this(new Office(), null);
    }

    public SimplyNode(Space space)
    {
        this(space, null);
    }

    public SimplyNode(Space space, SimplyNode next)
    {
        this.space = space;
        this.next = next;
    }
}
