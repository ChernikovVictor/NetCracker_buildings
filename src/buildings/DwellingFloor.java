package buildings;

public class DwellingFloor implements Floor
{
    private Space[] spaces;
    public Space[] getSpaceArray()
    {
        return spaces;
    }

    public DwellingFloor(int flatsCount)
    {
        spaces = new Flat[flatsCount];
        for(int i = 0; i < flatsCount; i++)
            spaces[i] = new Flat();
    }

    public DwellingFloor(Space ... spaces)
    {
        this.spaces = spaces;
    }

    public int spaceCount() { return spaces.length; }

    // общая площадь всех квартир на этаже
    public int totalSpaceArea()
    {
        int area = 0;
        for(Space space : spaces)
        {
            area += space.getArea();
        }
        return area;
    }

    // общее число комнат на этаже
    public int totalRoomsCount()
    {
        int count = 0;
        for(Space space : spaces)
        {
            count += space.getRoomsCount();
        }
        return count;
    }

    public Space getSpace(int index) { return spaces[index]; }
    public void setSpace(int index, Space space) { spaces[index] = space; }

    public void addSpace(int index, Space space)
    {
        index = index > spaces.length ? spaces.length : index;
        Space[] buffer = new Flat[spaces.length + 1];
        for(int i = 0; i < index; i++)
            buffer[i] = spaces[i];
        buffer[index] = space;
        for (int i = index; i < spaces.length; i++)
            buffer[i + 1] = spaces[i];
        spaces = buffer;
    }

    public void removeSpace(int index)
    {
        if (index >= spaces.length)
            return;
        Space[] buffer = new Flat[spaces.length - 1];
        for(int i = 0; i < index; i++)
            buffer[i] = spaces[i];
        for (int i = index; i < buffer.length; i++)
            buffer[i] = spaces[i + 1];
        spaces = buffer;
    }

    // наибольшая по площади квартира на этаже
    public Space getBestSpace()
    {
        Space maxAreaSpace = spaces[0];
        for(Space space : spaces)
        {
            if (space.getArea() > maxAreaSpace.getArea())
                maxAreaSpace = space;
        }
        return maxAreaSpace;
    }
}
