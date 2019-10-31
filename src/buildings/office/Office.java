package buildings.office;

import buildings.exceptions.*;
import buildings.interfaces.Space;

import java.io.Serializable;

public class Office implements Space, Serializable, Cloneable
{
    private final int DEFAULT_ROOMS_COUNT = 1;
    private final double DEFAULT_AREA = 250.0;

    private int roomsCount;
    private double area;

    public int getRoomsCount() { return roomsCount; }
    public void setRoomsCount (int value)
    {
        if (value <= 0)
            throw new InvalidRoomsCountException();
        roomsCount = value;
    }
    public double getArea() { return area; }
    public void setArea(double value)
    {
        if (value <= 0)
            throw new InvalidSpaceAreaException();
        area = value;
    }

    public Office()
    {
        roomsCount = DEFAULT_ROOMS_COUNT;
        area = DEFAULT_AREA;
    }

    public Office(double area)
    {
        if (area <= 0)
            throw new InvalidSpaceAreaException();
        roomsCount = DEFAULT_ROOMS_COUNT;
        this.area = area;
    }

    public Office(int roomsCount, double area)
    {
        if (roomsCount <= 0)
            throw new InvalidRoomsCountException();
        if (area <= 0)
            throw new InvalidSpaceAreaException();
        this.roomsCount = roomsCount;
        this.area = area;
    }

    @Override
    public String toString()
    {
        return String.format("Office (%d, %.1f)", roomsCount, area);
    }

    @Override
    public boolean equals(Object object)
    {
        if (object == this)
            return true;
        if (!(object instanceof Office))
            return false;
        Office office = (Office) object;
        return (this.roomsCount == office.roomsCount) && (Math.abs(this.area - office.area) < 1e-3);
    }

    @Override
    public int hashCode()
    {
        int result = new Integer(roomsCount);
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
