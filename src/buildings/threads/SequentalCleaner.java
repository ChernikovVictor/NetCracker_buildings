package buildings.threads;

import buildings.interfaces.Floor;

// поток с семафором
public class SequentalCleaner implements Runnable
{
    Floor floor;
    MySemaphore semaphore;

    public SequentalCleaner(Floor floor, MySemaphore semaphore)
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
            semaphore.down(index, floor.getSpace(index));
            index++;
        }
        if (Thread.currentThread().isInterrupted())
            System.out.println("the thread \"Cleaner\" has interrupted");
        else
            System.out.println("the thread \"Cleaner\" has finished");
    }
}
