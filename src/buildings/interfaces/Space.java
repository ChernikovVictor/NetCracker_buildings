package buildings.interfaces;

public interface Space extends Cloneable
{
    int getRoomsCount();
    void setRoomsCount(int value);

    double getArea();
    void setArea(double value);

    Object clone() throws CloneNotSupportedException;
}
