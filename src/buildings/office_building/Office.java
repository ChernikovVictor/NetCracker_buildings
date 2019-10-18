package buildings.office_building;

import buildings.exceptions.*;
import buildings.interfaces.Space;

import java.io.Serializable;

public class Office implements Space, Serializable
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
        return "В офисе " + roomsCount + " комнат общей площадью " + area + " кв.м.";
    }
}
