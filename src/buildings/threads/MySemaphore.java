package buildings.threads;

import buildings.interfaces.Space;

// семафор для огранизации порядка работы потоков SequentalRepair и SequentalCleaner
public class MySemaphore
{
    int count; // count == 0 => можно ремонтировать; count == 1 => можно убираться
    public MySemaphore(){}

    // увеличить count (отремонтировать помещение)
    public synchronized void up(int index, Space space)
    {
        while (count != 0)
        {
            try { wait(); }
            catch (InterruptedException e) { System.out.println(e.getMessage()); }
        }
        System.out.println(String.format("Repairing space number %d with total area %.1f square metres",
                index, space.getArea()));
        count++;
        notify();
    }

    // уменьшить count (убрать помещение)
    public synchronized void down(int index, Space space)
    {
        while (count != 1)
        {
            try { wait(); }
            catch(InterruptedException e) { System.out.println(e.getMessage()); }
        }
        System.out.println(String.format("Cleaning space number %d with total area %.1f square metres",
                        index, space.getArea()));
        count--;
        notify();
    }
}
