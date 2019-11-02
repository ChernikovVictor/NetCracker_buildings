package buildings.interfaces;

// базовый интерфейс для фабрик, создающих здания, этажи, помещения
public interface BuildingFactory
{
    Space createSpace(double area);
    Space createSpace(int roomsCount, double area);
    Floor createFloor(int spacesCount);
    Floor createFloor(Space ... spaces);
    Building createBuilding(int floorsCount, int ... spacesCount);
    Building createBuilding(Floor ... floors);
}
