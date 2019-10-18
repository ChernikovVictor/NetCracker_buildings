package buildings;

import buildings.exceptions.*;
import buildings.interfaces.*;

public class PlacementExchanger
{
    // проверить возможность обмена помещений
    public static boolean isExchangeable(Space space1, Space space2)
    {
        return space1.getArea() == space2.getArea() && space1.getRoomsCount() == space2.getRoomsCount();
    }

    // проверить возможность обмена этажами
    public static boolean isExchangeable(Floor floor1, Floor floor2)
    {
        return floor1.totalSpaceArea() == floor2.totalSpaceArea() && floor1.totalRoomsCount() == floor2.totalRoomsCount();
    }

    // обменять два помещения
    public static void exchangeFloorRooms(Floor floor1, int index1, Floor floor2, int index2)
    {
        try
        {
            Space space1 = floor1.getSpace(index1);
            Space space2 = floor2.getSpace(index2);
            if (!PlacementExchanger.isExchangeable(space1, space2))
                throw new InexchangeableSpacesException();
            floor1.setSpace(index1, space2);
            floor2.setSpace(index2, space1);
        }
        catch (SpaceIndexOutOfBoundsException e)
        {
            System.out.println("Некорректный номер помещений");
        }
        catch (InexchangeableSpacesException e)
        {
            System.out.println("Помещения не подлежат обмену");
        }
    }

    // обмен этажей
    public static void exchangeBuildingFloors(Building building1, int index1, Building building2, int index2)
    {
        try
        {
            Floor floor1 = building1.getFloor(index1);
            Floor floor2 = building2.getFloor(index2);
            if (!PlacementExchanger.isExchangeable(floor1, floor2))
                throw new InexchangeableFloorsException();
            building1.setFloor(index1, floor2);
            building2.setFloor(index2, floor1);
        }
        catch (FloorIndexOutOfBoundsException e)
        {
            System.out.println("Некорректный номер этажей");
        }
        catch (InexchangeableFloorsException e)
        {
            System.out.println("Этажи не подлежат обмену");
        }
    }
}
