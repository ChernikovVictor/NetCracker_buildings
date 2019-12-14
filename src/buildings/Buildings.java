package buildings;

import buildings.dwelling.*;
import buildings.interfaces.*;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Buildings {

    /* текущая порождающая фабрика */
    private static BuildingFactory buildingFactory = new DwellingFactory();

    /* изменить текущую порождающую фабрику */
    public static void setBuildingFactory(BuildingFactory bf)
    {
        buildingFactory = bf;
    }

    /* запись данных о здании в байтовый поток */
    public static void outputBuilding(Building building, OutputStream out) throws IOException {
        DataOutputStream output = new DataOutputStream(out);
        int floorCount = building.floorCount();
        output.writeInt(floorCount);
        for(int i = 0; i < floorCount; i++) {
            Floor floor = building.getFloor(i);
            int spaceCount = floor.spaceCount();
            output.writeInt(spaceCount);
            for(int j = 0; j < spaceCount; j++) {
                Space space = floor.getSpace(j);
                output.writeInt(space.getRoomsCount());
                output.writeDouble(space.getArea());
            }
        }
    }

    /* чтение данных о здании из байтового потока */
    public static Building inputBuilding(InputStream in) throws IOException {
        DataInputStream input = new DataInputStream(in);
        int floorCount = input.readInt();
        Floor[] floors = new Floor[floorCount];
        for(int i = 0; i < floorCount; i++) {
            int spaceCount = input.readInt();
            floors[i] = buildingFactory.createFloor(spaceCount);
            for(int j = 0; j < spaceCount; j++) {
                floors[i].setSpace(j, buildingFactory.createSpace(input.readInt(), input.readDouble()));
            }
        }
        return buildingFactory.createBuilding(floors);
    }

    /* записать данные о здании в символьный поток */
    public static void writeBuilding(Building building, Writer out) throws IOException {
        PrintWriter output = new PrintWriter(out);
        int floorCount = building.floorCount();
        output.print(floorCount);
        for(int i = 0; i < floorCount; i++) {
            Floor floor = building.getFloor(i);
            int spaceCount = floor.spaceCount();
            output.print(' ');
            output.print(spaceCount);
            for(int j = 0; j < spaceCount; j++) {
                Space space = floor.getSpace(j);
                output.print(' ');
                output.print(space.getRoomsCount());
                output.print(' ');
                output.print(space.getArea());
            }
        }
    }

    /* записать данные о здании в символьный поток с помощью Formatter */
    public static void writeBuildingFormat(Building building, Writer out) throws IOException {
        Formatter formatter = new Formatter(out);
        int floorCount = building.floorCount();
        formatter.format("%d", floorCount);
        for(int i = 0; i < floorCount; i++) {
            Floor floor = building.getFloor(i);
            int spaceCount = floor.spaceCount();
            formatter.format(" %d", spaceCount);
            for(int j = 0; j < spaceCount; j++) {
                Space space = floor.getSpace(j);
                formatter.format(" %d %.1f", space.getRoomsCount(), space.getArea());
            }
        }
        formatter.flush();
    }

    /* чтение данных о здании из символьного потока */
    public static Building readBuilding(Reader in) throws IOException {
        StreamTokenizer streamTokenizer = new StreamTokenizer(in);
        streamTokenizer.nextToken();
        int floorCount = (int)streamTokenizer.nval;
        Floor[] floors = new Floor[floorCount];
        for(int i = 0; i < floorCount; i++) {
            streamTokenizer.nextToken();
            int spaceCount = (int)streamTokenizer.nval;
            floors[i] = buildingFactory.createFloor(spaceCount);
            for(int j = 0; j < spaceCount; j++) {
                streamTokenizer.nextToken();
                int roomsCount = (int)streamTokenizer.nval;
                streamTokenizer.nextToken();
                double area = streamTokenizer.nval;
                floors[i].setSpace(j, buildingFactory.createSpace(roomsCount, area));
            }
        }
        return buildingFactory.createBuilding(floors);
    }

    /* чтение данных о здании из сивольного потока при помощи Scanner */
    public static Building readBuilding(Scanner scanner) throws NoSuchElementException {
        int floorCount = scanner.nextInt();
        Floor[] floors = new Floor[floorCount];
        for(int i = 0; i < floorCount; i++) {
            int spaceCount = scanner.nextInt();
            floors[i] = buildingFactory.createFloor(spaceCount);
            for(int j = 0; j < spaceCount; j++) {
                int roomsCount = scanner.nextInt();
                double area = scanner.nextDouble();
                floors[i].setSpace(j, buildingFactory.createSpace(roomsCount, area));
            }
        }
        return buildingFactory.createBuilding(floors);
    }

    /* Сериализация здания */
    public static void serializeBuilding(Building building, OutputStream out) throws IOException {
        ObjectOutputStream output = new ObjectOutputStream(out);
        output.writeObject(building);
    }

    /* десериализация здания */
    public static Building deserializeBuilding(InputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream input = new ObjectInputStream(in);
        return (Building)input.readObject();
    }

    /* Параметризованная сортировка без критерия */
    public static <T extends Comparable<T>> void sortArray(T[] objects) {
        qSort(objects, 0, objects.length - 1);
    }

    /* Параметризованная сортировка с критерием */
    public static <T> void sortArray(T[] objects, Comparator<T> comparator) {
        qSort(objects, 0, objects.length - 1, comparator);
    }

    /* быстрая сортировка без критерия */
    private static <T extends Comparable<T>> void qSort(T[] objects, int left, int right) {
        int i = left, j = right;
        T middle = objects[(i + j) / 2];
        do {
            while (objects[i].compareTo(middle) < 0)
                i++;
            while (objects[j].compareTo(middle) > 0)
                j--;
            if (i <= j) {
                T buffer = objects[i];
                objects[i] = objects[j];
                objects[j] = buffer;
                i++;
                j--;
            }
        } while (i <= j);
        if (left < j)
            qSort(objects, left, j);
        if (i < right)
            qSort(objects, i, right);
    }

    /* быстрая сортировка с критерием */
    private static <T> void qSort(T[] objects, int left, int right, Comparator<T> comparator) {
        int i = left, j = right;
        T middle = objects[(i + j) / 2];
        do {
            while (comparator.compare(objects[i], middle) < 0)
                i++;
            while (comparator.compare(objects[j], middle) > 0)
                j--;
            if (i <= j) {
                T buffer = objects[i];
                objects[i] = objects[j];
                objects[j] = buffer;
                i++;
                j--;
            }
        } while (i <= j);
        if (left < j)
            qSort(objects, left, j, comparator);
        if (i < right)
            qSort(objects, i, right, comparator);
    }

    //region Методы создания объектов с помощью текущей порождающей фабрики
    public static Space createSpace(double area) {
        return buildingFactory.createSpace(area);
    }

    public static Space createSpace(int roomsCount, double area) {
        return buildingFactory.createSpace(roomsCount, area);
    }

    public static Floor createFloor(int spacesCount) {
        return buildingFactory.createFloor(spacesCount);
    }

    public static Floor createFloor(Space ... spaces) {
        return buildingFactory.createFloor(spaces);
    }

    public static Building createBuilding(int ... spacesCount) {
        return buildingFactory.createBuilding(spacesCount);
    }

    public static Building createBuilding(Floor ... floors) {
        return buildingFactory.createBuilding(floors);
    }
    //endregion

    //region Методы создания помещений, этажей и зданий с помощью механизма рефлексии
    public static Space createSpace(double area, Class<Space> spaceClass) {
        try {
            Constructor<Space> constructor = spaceClass.getConstructor(double.class);
            return constructor.newInstance(area);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException();
        }
    }

    public static Space createSpace(int roomsCount, double area, Class<Space> spaceClass) {
        try {
            Constructor<Space> constructor = spaceClass.getConstructor(int.class, double.class);
            return constructor.newInstance(roomsCount, area);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException();
        }
    }

    public static Floor createFloor(int spacesCount, Class<Floor> floorClass) {
        try {
            Constructor<Floor> constructor = floorClass.getConstructor(int.class);
            return constructor.newInstance(spacesCount);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException();
        }
    }

    public static Floor createFloor(Class<Floor> floorClass, Space ... spaces) {
        try {
            Constructor<Floor> constructor = floorClass.getConstructor(Space[].class);
            return constructor.newInstance((Object) spaces);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException();
        }
    }

    public static Building createBuilding(Class<Building> buildingClass, int ... spacesCount) {
        try {
            Constructor<Building> constructor = buildingClass.getConstructor(int[].class);
            return constructor.newInstance((Object) spacesCount);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException();
        }
    }

    public static Building createBuilding(Class<Building> buildingClass, Floor ... floors) {
        try {
            Constructor<Building> constructor = buildingClass.getConstructor(Floor[].class);
            return constructor.newInstance((Object) floors);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException();
        }
    }
    //endregion

    /* чтение данных о здании из байтового потока (объекты создаются рефлексивными методами) */
    public static Building inputBuilding(InputStream in, Class<Building> buildingClass,
                                         Class<Floor> floorClass, Class<Space> spaceClass) throws IOException {
        DataInputStream input = new DataInputStream(in);
        int floorCount = input.readInt();
        Floor[] floors = new Floor[floorCount];
        for(int i = 0; i < floorCount; i++) {
            int spaceCount = input.readInt();
            floors[i] = createFloor(spaceCount, floorClass);
            for(int j = 0; j < spaceCount; j++) {
                floors[i].setSpace(j, createSpace(input.readInt(), input.readDouble(), spaceClass));
            }
        }
        return createBuilding(buildingClass, floors);
    }

    /* чтение данных о здании из символьного потока (объекты создаются рефлексивными методами) */
    public static Building readBuilding(Reader in, Class<Building> buildingClass,
                                        Class<Floor> floorClass, Class<Space> spaceClass) throws IOException {
        StreamTokenizer streamTokenizer = new StreamTokenizer(in);
        streamTokenizer.nextToken();
        int floorCount = (int)streamTokenizer.nval;
        Floor[] floors = new Floor[floorCount];
        for(int i = 0; i < floorCount; i++) {
            streamTokenizer.nextToken();
            int spaceCount = (int)streamTokenizer.nval;
            floors[i] = createFloor(spaceCount, floorClass);
            for(int j = 0; j < spaceCount; j++) {
                streamTokenizer.nextToken();
                int roomsCount = (int)streamTokenizer.nval;
                streamTokenizer.nextToken();
                double area = streamTokenizer.nval;
                floors[i].setSpace(j, createSpace(roomsCount, area, spaceClass));
            }
        }
        return createBuilding(buildingClass, floors);
    }

    /* Обернуть объект Floor декоратором, безопасным для многопоточности */
    public static Floor synchronizedFloor(Floor floor) {
        return new SynchronizedFloor(floor);
    }
}
