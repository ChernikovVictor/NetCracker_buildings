package buildings;

public class DwellingFloor
{
    private Flat[] flats;

    public Flat[] getFlats()
    {
        return flats;
    }

    public DwellingFloor(int flatsCount)
    {
        flats = new Flat[flatsCount];
        for(int i = 0; i < flatsCount; i++)
            flats[i] = new Flat();
    }

    public DwellingFloor(Flat[] flats)
    {
        this.flats = flats;
    }

    public int flatsCount() { return flats.length; }

    // общая площадь всех квартир на этаже
    public int totalFlatsArea()
    {
        int area = 0;
        for(Flat flat : flats)
        {
            area += flat.getArea();
        }
        return area;
    }

    // общее число комнат на этаже
    public int totalRoomsCount()
    {
        int count = 0;
        for(Flat flat : flats)
        {
            count += flat.getRoomsCount();
        }
        return count;
    }

    public Flat getFlat(int index) { return flats[index]; }
    public void setFlat(int index, Flat flat) { flats[index] = flat; }

    public void addFlat(int index, Flat flat)
    {
        index = index > flats.length ? flats.length : index;
        Flat[] buffer = new Flat[flats.length + 1];
        for(int i = 0; i < index; i++)
            buffer[i] = flats[i];
        buffer[index] = flat;
        for (int i = index; i < flats.length; i++)
            buffer[i + 1] = flats[i];
        flats = buffer;
    }

    public void removeFlat(int index)
    {
        if (index >= flats.length)
            return;
        Flat[] buffer = new Flat[flats.length - 1];
        for(int i = 0; i < index; i++)
            buffer[i] = flats[i];
        for (int i = index; i < buffer.length; i++)
            buffer[i] = flats[i + 1];
        flats = buffer;
    }

    // наибольшая по площади квартира на этаже
    public Flat getBestSpace()
    {
        Flat maxAreaFlat = flats[0];
        for(Flat flat : flats)
        {
            if (flat.getArea() > maxAreaFlat.getArea())
                maxAreaFlat = flat;
        }
        return maxAreaFlat;
    }
}
