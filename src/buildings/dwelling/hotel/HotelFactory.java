package buildings.dwelling.hotel;

import buildings.dwelling.Flat;
import buildings.interfaces.*;

// фабрика для создания отельных зданий, этажей, помещений
public class HotelFactory implements BuildingFactory
{
    @Override
    public Space createSpace(double area)
    {
        return new Flat(area);
    }

    @Override
    public Space createSpace(int roomsCount, double area)
    {
        return new Flat(roomsCount, area);
    }

    @Override
    public Floor createFloor(int spacesCount)
    {
        return new HotelFloor(spacesCount);
    }

    @Override
    public Floor createFloor(Space... spaces)
    {
        return new HotelFloor(spaces);
    }

    @Override
    public Building createBuilding(int floorsCount, int... spacesCount)
    {
        return new Hotel(floorsCount, spacesCount);
    }

    @Override
    public Building createBuilding(Floor... floors)
    {
        return new Hotel(floors);
    }
}
