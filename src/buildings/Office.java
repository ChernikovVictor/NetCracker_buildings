package buildings;

import buildings.exceptions.*;

public class Office
{
    private final int DEFAULT_ROOMS_COUNT = 1;
    private final int DEFAULT_AREA = 250;

    private int roomsCount;
    private int area;

    public int getRoomsCount() { return roomsCount; }
    public void setRoomsCount (int value)
    {
        if (value <= 0)
            throw new InvalidRoomsCountException();
        roomsCount = value;
    }
    public int getArea() { return area; }
    public void setArea(int value)
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

    public Office(int area)
    {
        if (area <= 0)
            throw new InvalidSpaceAreaException();
        roomsCount = DEFAULT_ROOMS_COUNT;
        this.area = area;
    }

    public Office(int roomsCount, int area)
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
