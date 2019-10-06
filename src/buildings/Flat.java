package buildings;

public class Flat implements Space
{
    private final int DEFAULT_ROOMS_COUNT = 2;
    private final int DEFAULT_AREA = 50;

    private int roomsCount;
    private int area;

    public int getRoomsCount() { return roomsCount; }
    public void setRoomsCount(int value) { roomsCount = value; }
    public int getArea() { return area; }
    public void setArea(int value) { area = value; }

    public Flat()
    {
        roomsCount = DEFAULT_ROOMS_COUNT;
        area = DEFAULT_AREA;
    }

    public Flat(int area)
    {
        roomsCount = DEFAULT_ROOMS_COUNT;
        this.area = area;
    }

    public Flat(int roomsCount, int area)
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
