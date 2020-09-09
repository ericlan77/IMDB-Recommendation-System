/**
 * A rating object stores the movie(or rater) id and its value(only one)
 * 拥有compareTo比较方法, 能进行排序的对象
 * @author DUKE
 *
 */
public class Rating implements Comparable<Rating> {
    private String item; // store id
    private double value;

    public Rating (String anItem, double aValue) {
        item = anItem;
        value = aValue;
    }

    // Returns item being rated
    public String getItem () {
        return item;
    }

    // Returns the value of this rating (as a number so it can be used in calculations)
    public double getValue () {
        return value;
    }

    // Returns a string of all the rating information
    public String toString () {
        return "[" + getItem() + ", " + getValue() + "]";
    }

    public int compareTo(Rating other) {
        if (value < other.value) return -1;
        if (value > other.value) return 1;
        
        return 0;
    }
}
