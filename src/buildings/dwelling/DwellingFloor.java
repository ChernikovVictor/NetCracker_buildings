package buildings.dwelling;

import buildings.interfaces.Floor;
import buildings.interfaces.Space;

import java.io.Serializable;
import java.util.Iterator;

public class DwellingFloor implements Floor, Serializable
{
    protected Space[] spaces;
    public Space[] getSpaceArray()
    {
        return spaces;
    }

    /* конструктор по числу квартир */
    public DwellingFloor(int flatsCount) {
        spaces = new Space[flatsCount];
        for(int i = 0; i < flatsCount; i++)
            spaces[i] = new Flat();
    }

    /* конструктор по массиву помещений */
    public DwellingFloor(Space ... spaces) {
        this.spaces = spaces;
    }

    public int spaceCount() { return spaces.length; }

    /* общая площадь всех квартир на этаже */
    public double totalSpaceArea() {
        double area = 0;
        for(Space space : spaces) {
            area += space.getArea();
        }
        return area;
    }

    /* общее число комнат на этаже */
    public int totalRoomsCount() {
        int count = 0;
        for(Space space : spaces) {
            count += space.getRoomsCount();
        }
        return count;
    }

    public Space getSpace(int index) { return spaces[index]; }
    public void setSpace(int index, Space space) { spaces[index] = space; }

    public void addSpace(int index, Space space) {
        index = Math.min(index, spaces.length);
        Space[] buffer = new Space[spaces.length + 1];
        for(int i = 0; i < index; i++)
            buffer[i] = spaces[i];
        buffer[index] = space;
        for (int i = index; i < spaces.length; i++)
            buffer[i + 1] = spaces[i];
        spaces = buffer;
    }

    public void removeSpace(int index) {
        if (index >= spaces.length)
            return;
        Space[] buffer = new Flat[spaces.length - 1];
        for(int i = 0; i < index; i++)
            buffer[i] = spaces[i];
        for (int i = index; i < buffer.length; i++)
            buffer[i] = spaces[i + 1];
        spaces = buffer;
    }

    /* наибольшая по площади квартира на этаже */
    public Space getBestSpace() {
        Space maxAreaSpace = spaces[0];
        for(Space space : spaces) {
            if (space.getArea() > maxAreaSpace.getArea())
                maxAreaSpace = space;
        }
        return maxAreaSpace;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("DwellingFloor (" + spaceCount());
        for(Space space : spaces)
            result.append(", ").append(space.toString());
        result.append(')');
        return result.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this)
            return true;
        if (!(object instanceof DwellingFloor))
            return false;
        DwellingFloor floor = (DwellingFloor)object;
        if (floor.spaceCount() != this.spaceCount())
            return false;
        for (int i = 0; i < this.spaceCount(); i++) {
            if (!floor.getSpace(i).equals(this.getSpace(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = spaceCount();
        for (Space space : spaces)
            result = result ^ space.hashCode();
        return result;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        DwellingFloor result = new DwellingFloor(spaceCount());
        for (int i = 0; i < spaceCount(); i++) {
            result.setSpace(i, (Space)(this.getSpace(i)).clone());
        }
        return result;
    }

    /* итератор по помещениям на этаже */
    @Override
    public Iterator<Space> iterator() {
        return new spaceIterator(this);
    }

    /* класс итератора по помещениям на этаже */
    private class spaceIterator implements Iterator<Space>
    {
        private DwellingFloor dwellingFloor;
        private int index;
        public spaceIterator(DwellingFloor dwellingFloor) {
            this.dwellingFloor = dwellingFloor;
            index = -1;
        }

        @Override
        public boolean hasNext() {
            return index + 1 < dwellingFloor.spaceCount();
        }

        @Override
        public Space next() {
            index++;
            return dwellingFloor.getSpace(index);
        }
    }

    @Override
    public int compareTo(Floor o) {
        return this.spaceCount() - o.spaceCount();
    }
}
