package buildings.dwelling_building;

import buildings.interfaces.Space;

import java.io.Serializable;

public class Flat implements Space, Serializable
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
        return "В квартире " + roomsCount + " комнат общей площадью " + area + " кв.м.";
    }
}
