package buildings.comparators;

import buildings.interfaces.Floor;

import java.util.Comparator;

/* компаратор, реализующий сортировку по убыванию общей площади помещений этажа */
public class FloorTotalAreaComparator implements Comparator<Floor> {
    @Override
    public int compare(Floor o1, Floor o2) {
        if (Math.abs(o1.totalSpaceArea() - o2.totalSpaceArea()) < 1e-3)
            return 0;
        return o2.totalSpaceArea() - o1.totalSpaceArea() > 0 ? 1 : -1;
    }
}
