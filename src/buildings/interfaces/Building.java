package buildings.interfaces;

public interface Building extends Cloneable, Iterable<Floor>
{
    int floorCount(); // кол-во этажей в здании
    int spaceCount(); // общее число помещений в здании
    double totalSpaceArea();   // общая площадь всех помещений здания
    int totalRoomsCount();  // кол-во всех комнат зданич
    Floor[] getFloorArray(); // получить массив этажей здания
    Floor getFloor(int index);
    void setFloor(int index, Floor floor);
    Space getSpace(int index);
    void setSpace(int index, Space space);
    void addSpace(int index, Space space);
    void removeSpace(int index);
    Space getBestSpace(); // наибольее по площади помещение здания
    Space[] sortedSpaceArray(); // получить массив помещений, сортированный по убыванию площадей
    Object clone() throws CloneNotSupportedException;
}
