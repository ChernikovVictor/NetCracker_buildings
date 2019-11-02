package buildings.interfaces;

import java.util.Comparator;

public interface Space extends Cloneable, Comparable<Space>
{
    int getRoomsCount();
    void setRoomsCount(int value);

    double getArea();
    void setArea(double value);

    Object clone() throws CloneNotSupportedException;

    int compareTo(Space o);

}
