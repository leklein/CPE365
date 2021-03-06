/**
 * CPE 365 - Spring 2017
 * Lauren Klein, Andrew Gough
 */

public class Student {
    String stLast, stFirst;
    String gradeNum, classroomNum, busNum, GPA;

    public Student(String stLast, String stFirst, String gradeNum, String classNum, String busNum, String GPA) {
        this.stLast = stLast;
        this.stFirst = stFirst;
        this.gradeNum = gradeNum;
        this.classroomNum = classNum;
        this.busNum = busNum;
        this.GPA = GPA;
    }

    public String getStLast() {
        return stLast;
    }

    public void setStLast(String stLast) {
        this.stLast = stLast;
    }

    public String getStFirst() {
        return stFirst;
    }

    public void setStFirst(String stFirst) {
        this.stFirst = stFirst;
    }

    public String getGrade() {
        return gradeNum;
    }

    public void setGrade(String grade) {
        this.gradeNum = grade;
    }

    public String getClassroomNum() {
        return classroomNum;
    }

    public void setClassroomNum(String classroomNum) {
        this.classroomNum = classroomNum;
    }

    public String getBusNum() {
        return busNum;
    }

    public void setBusNum(String busNum) {
        this.busNum = busNum;
    }

    public String getGPA() {
        return GPA;
    }

    public void setGPA(String GPA) {
        this.GPA = GPA;
    }
}
