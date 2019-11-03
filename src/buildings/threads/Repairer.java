package buildings.threads;

import buildings.interfaces.*;

// независимый поток (без семафора)
public class Repairer extends Thread
{
    Floor floor;

    public Repairer(Floor floor)
    {
        this.floor = floor;
    }

    @Override
    public void run()
    {
        int i = 0, spaceCount = floor.spaceCount();
        while (!isInterrupted() && i < spaceCount)
        {
            System.out.println(String.format("Repairing space number %d with total area %.1f square metres",
                    i, floor.getSpace(i).getArea()));
            i++;
        }
        if (isInterrupted())
            System.out.println("the thread \"Repairer\" has interrupted");
        else
            System.out.println("the thread \"Repairer\" has finished");
    }
}
