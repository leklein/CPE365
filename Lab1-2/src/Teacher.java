/**
 * CPE 365 - Spring 2017
 * Lauren Klein, Andrew Gough
 */

public class Teacher {
    String last, first;

    public Teacher(String last, String first) {
        this.last = last;
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }
}
