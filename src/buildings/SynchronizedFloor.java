package buildings;

import buildings.interfaces.*;

import java.util.Iterator;

// Декоратор объекта Floor, безопасный с точки зрения многопоточности
public class SynchronizedFloor implements Floor
{
    Floor floor; // объект, оборачиваемый декоратором

    public SynchronizedFloor(Floor floor)
    {
        this.floor = floor;
    }

    @Override
    public synchronized int spaceCount()
    {
        return floor.spaceCount();
    }

    @Override
    public synchronized double totalSpaceArea() {
        return floor.totalSpaceArea();
    }

    @Override
    public synchronized int totalRoomsCount() {
        return floor.totalRoomsCount();
    }

    @Override
    public synchronized Space[] getSpaceArray() {
        return floor.getSpaceArray();
    }

    @Override
    public synchronized Space getSpace(int index) {
        return floor.getSpace(index);
    }

    @Override
    public synchronized void setSpace(int index, Space space) {
        floor.setSpace(index, space);
    }

    @Override
    public synchronized void addSpace(int index, Space space) {
        floor.addSpace(index, space);
    }

    @Override
    public synchronized void removeSpace(int index) {
        floor.removeSpace(index);
    }

    @Override
    public synchronized Space getBestSpace() {
        return floor.getBestSpace();
    }

    @Override
    public synchronized Object clone() throws CloneNotSupportedException {
        return floor.clone();
    }

    @Override
    public synchronized Iterator<Space> iterator() {
        return floor.iterator();
    }

    @Override
    public synchronized int compareTo(Floor o) {
        return floor.compareTo(o);
    }

    @Override
    public synchronized String toString() {
        return floor.toString();
    }

    @Override
    public synchronized boolean equals(Object obj) {
        return floor.equals(obj);
    }

    @Override
    public synchronized int hashCode() {
        return floor.hashCode();
    }
}
