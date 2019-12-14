package buildings.dwelling.hotel;

import buildings.dwelling.Dwelling;
import buildings.dwelling.Flat;
import buildings.interfaces.*;

public class Hotel extends Dwelling
{
    /* конструктор по числу этажей и числу квартир на каждом этаже */
    public Hotel(int ... flatsCount)
    {
        super(flatsCount);
    }

    /* конструктор по массиву этажей */
    public Hotel(Floor ... floors) { super(floors); }

    /* кол-во зведз отеля (максимум среди кол-ва звезд этажей) */
    public int getStars() {
        int stars = 0;
        for (int i = 0; i < floorCount(); i++) {
            Floor floor = getFloor(i);
            if (floor instanceof HotelFloor && ((HotelFloor) floor).getStars() > stars) {
                stars = ((HotelFloor) floor).getStars();
            }
        }
        return stars;
    }

    /* лучший номер - максимум (площадь * коэффициент) по всем номерам */
    @Override
    public Space getBestSpace() {
        double[] coeff = {0, 0.25, 0.5, 1, 1.25, 1.5};
        Space result = new Flat(1);
        double bestResult = 0;
        for (int i = 0; i < floorCount(); i++) {
            Floor floor = getFloor(i);
            if (floor instanceof HotelFloor) {
                int stars = ((HotelFloor) floor).getStars();
                Space space = floor.getBestSpace();
                if (space.getArea() * coeff[stars] > bestResult) {
                    bestResult = space.getArea() * coeff[stars];
                    result = space;
                }
            }
        }
        return bestResult < 1e-6 ? null : result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(String.format("Hotel (%d, %d", getStars(), floorCount()));
        for(Floor floor : floors)
            result.append(", ").append(floor.toString());
        result.append(')');
        return result.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this)
            return true;
        if (!(object instanceof Hotel))
            return false;
        Hotel hotel = (Hotel)object;
        if (hotel.floorCount() != this.floorCount())
            return false;
        for (int i = 0; i < hotel.floorCount(); i++) {
            if (!this.getFloor(i).equals(hotel.getFloor(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }
}
