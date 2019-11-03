package buildings.threads;

import buildings.interfaces.Floor;

// поток с семафором
public class SequentalRepairer implements Runnable
{
    Floor floor;
    MySemaphore semaphore;

    public SequentalRepairer(Floor floor, MySemaphore semaphore)
    {
        this.floor = floor;
        this.semaphore = semaphore;
    }

    @Override
    public void run()
    {
        int index = 0, spaceCount = floor.spaceCount();
        while (!Thread.currentThread().isInterrupted() && index < spaceCount)
        {
            semaphore.up(index, floor.getSpace(index));
            index++;
        }
        if (Thread.currentThread().isInterrupted())
            System.out.println("the thread \"Repairer\" has interrupted");
        else
            System.out.println("the thread \"Repairer\" has finished");
    }
}
