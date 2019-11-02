package buildings.comparators;

import buildings.interfaces.Space;

import java.util.Comparator;

// компаратор, реализующий сортировку по убыванию кол-ва комнат в помещении
public class SpaceRoomsCountComparator implements Comparator<Space>
{
    @Override
    public int compare(Space o1, Space o2)
    {
        return o2.getRoomsCount() - o1.getRoomsCount();
    }
}
