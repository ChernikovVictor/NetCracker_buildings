package buildings.dwelling.hotel;

import buildings.dwelling.DwellingFloor;
import buildings.interfaces.Space;

public class HotelFloor extends DwellingFloor
{
    private final int DEFAULT_STARS = 1;
    private int stars;

    // конструктор по колличеству помещений
    public HotelFloor(int count)
    {
        super(count);
        stars = DEFAULT_STARS;
    }

    // конструктор по массиву помещений
    public HotelFloor(Space ... spaces)
    {
        super(spaces);
        stars = DEFAULT_STARS;
    }

    public int getStars() { return stars; }
    public void setStars(int value) { stars = value >= 1 && value <= 5 ? value : DEFAULT_STARS; }

    @Override
    public String toString()
    {
        StringBuffer result = new StringBuffer(String.format(("HotelFloor (%d, %d"), stars, spaceCount()));
        for(Space space : spaces)
            result.append(", " + space.toString());
        result.append(')');
        return result.toString();
    }

    @Override
    public boolean equals(Object object)
    {
        if (object == this)
            return true;
        if (!(object instanceof HotelFloor))
            return false;
        HotelFloor floor = (HotelFloor) object;
        if (floor.spaceCount() != this.spaceCount() || floor.getStars() != stars)
            return false;
        for (int i = 0; i < this.spaceCount(); i++) {
            if (!floor.getSpace(i).equals(this.getSpace(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        return super.hashCode() ^ stars;
    }
}
