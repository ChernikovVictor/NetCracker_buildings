package buildings.dwelling;

import buildings.interfaces.Space;

import java.io.Serializable;

public class Flat implements Space, Serializable, Cloneable
{
    private final int DEFAULT_ROOMS_COUNT = 2;
    private final double DEFAULT_AREA = 50.0;

    private int roomsCount;
    private double area;

    public int getRoomsCount() { return roomsCount; }
    public void setRoomsCount(int value) { roomsCount = value; }
    public double getArea() { return area; }
    public void setArea(double value) { area = value; }

    public Flat()
    {
        roomsCount = DEFAULT_ROOMS_COUNT;
        area = DEFAULT_AREA;
    }

    public Flat(double area)
    {
        roomsCount = DEFAULT_ROOMS_COUNT;
        this.area = area;
    }

    public Flat(int roomsCount, double area)
    {
        this.roomsCount = roomsCount;
        this.area = area;
    }

    @Override
    public String toString()
    {
        return String.format("Flat (%d, %.1f)", roomsCount, area);
    }

    @Override
    public boolean equals(Object object)
    {
        if (object == this)
            return true;
        if (!(object instanceof Flat))
            return false;
        Flat flat = (Flat)object;
        return (this.roomsCount == flat.roomsCount) && (Math.abs(this.area - flat.area) < 1e-3);
    }

    @Override
    public int hashCode()
    {
        int result = roomsCount;
        String binaryArea = Long.toBinaryString(Double.doubleToLongBits(area));
        String firstFourBytes = binaryArea.substring(0, 31), secondFourBytes = binaryArea.substring(32);
        int k1 = Integer.parseInt(firstFourBytes, 2), k2 = Integer.parseInt(secondFourBytes, 2);
        result = result ^ k1;
        result = result ^ k2;
        return result;
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
}
