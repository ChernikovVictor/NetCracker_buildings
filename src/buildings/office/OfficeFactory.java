package buildings.office;

import buildings.interfaces.*;

// фабрика для создания офисных зданий, этажей, помещений
public class OfficeFactory implements BuildingFactory
{
    @Override
    public Space createSpace(double area)
    {
        return new Office(area);
    }

    @Override
    public Space createSpace(int roomsCount, double area)
    {
        return new Office(roomsCount, area);
    }

    @Override
    public Floor createFloor(int spacesCount)
    {
        return new OfficeFloor(spacesCount);
    }

    @Override
    public Floor createFloor(Space... spaces)
    {
        return new OfficeFloor(spaces);
    }

    @Override
    public Building createBuilding(int floorsCount, int... spacesCount)
    {
        return new OfficeBuilding(spacesCount);
    }

    @Override
    public Building createBuilding(Floor... floors)
    {
        return new OfficeBuilding(floors);
    }
}
