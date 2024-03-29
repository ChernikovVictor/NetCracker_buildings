package buildings.interfaces;

public interface Floor extends Cloneable, Iterable<Space>, Comparable<Floor>
{
    int spaceCount(); // число помещений на этаже
    double totalSpaceArea();   // общая площадь помещений этажа
    int totalRoomsCount();  // колличество комнат помещений этажа
    Space[] getSpaceArray(); // получить массив помещений этажа
    Space getSpace(int index);
    void setSpace(int index, Space space);
    void addSpace(int index, Space space);
    void removeSpace(int index);
    Space getBestSpace(); // наибольшее по площади помещение этажа
    Object clone() throws CloneNotSupportedException;
}
