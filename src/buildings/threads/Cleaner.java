package buildings.threads;

import buildings.interfaces.*;

/* независимый поток (без семафора) */
public class Cleaner extends Thread
{
    private Floor floor;

    public Cleaner(Floor floor)
    {
        this.floor = floor;
    }

    @Override
    public void run() {
        int i = 0, spaceCount = floor.spaceCount();
        while (!isInterrupted() && i < spaceCount) {
            System.out.println(String.format("Cleaning space number %d with total area %.1f square metres",
                    i, floor.getSpace(i).getArea()));
            i++;
        }
        if (isInterrupted())
            System.out.println("the thread \"Cleaner\" has interrupted");
        else
            System.out.println("the thread \"Cleaner\" has finished");
    }
}
