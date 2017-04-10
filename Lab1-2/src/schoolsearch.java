/**
 * CPE 365 - Spring 2017
 * Lauren Klein, Andrew Gough
 */

import java.io.File;
import java.util.*;

public class schoolsearch {

    static HashMap<String, ArrayList<Teacher>> teachersByClassroom = new HashMap<>();
    static ArrayList<Student> students = new ArrayList<>();

    // Traceability: implements requirements R1, R2
    public static void main(String[] args) {

        readStudentFile();
        readTeacherFile();

        Scanner in = new Scanner(System.in);

        // Traceability: implements requirement R2
        System.out.println("Hey there user! Enter a search below... \n");
        while(in.hasNextLine()) {
            System.out.println();
            executeSearch(in.nextLine());
            System.out.println("\nEnter a search below...");
        }
    }

    public static void readTeacherFile() {
        // Traceability: implements requirement R13
        File f = new File("teachers.txt");
        String line, classroomNum, tLast, tFirst;
        String[] tokens;
        int lineNumber = 0;

        try {
            Scanner fileIn = new Scanner(f);
            while(fileIn.hasNextLine()) {
                line = fileIn.nextLine();
                lineNumber++;
                tokens = line.split(",\\s*");

                if (tokens.length == 3) {
                    tLast = tokens[0];
                    tFirst = tokens[1];
                    classroomNum = tokens[2];

                    Teacher teacher = new Teacher(tLast, tFirst);

                    if (teachersByClassroom.get(classroomNum) == null)
                        teachersByClassroom.put(classroomNum, new ArrayList<Teacher>());

                    teachersByClassroom.get(classroomNum).add(teacher);
                }
                // Traceability: implements requirement E1
                else {
                    System.out.printf("ERROR: Line %d of teachers.txt does not contain 3 attributes\n", lineNumber);
                    System.exit(1);
                }
            }
        }
        // Traceability: implements requirement E1
        catch (Exception e) {
            System.out.println("ERROR: Failed opening teachers.txt");
            System.exit(1);
        }
    }

    public static void readStudentFile() {
        // Traceability: implements requirement R13
        File f = new File("list.txt");
        String line, stLast, stFirst, grade, classroomNum, busNum, gpa, tLast, tFirst;
        String[] tokens;
        int lineNumber = 0;

        try {
            Scanner fileIn = new Scanner(f);
            while(fileIn.hasNextLine()) {
                line = fileIn.nextLine();
                lineNumber++;
                tokens = line.split(",\\s*");

                if (tokens.length == 6) {
                    stLast = tokens[0];
                    stFirst = tokens[1];
                    grade = tokens[2];
                    classroomNum = tokens[3];
                    busNum = tokens[4];
                    gpa = tokens[5];

                    Student student = new Student(stLast, stFirst, grade, classroomNum, busNum, gpa);
                    students.add(student);
                }
                // Traceability: implements requirement E1
                else {
                    System.out.printf("ERROR: Line %d of list.txt does not contain 6 attributes\n", lineNumber);
                    System.exit(1);
                }
            }
        }
        // Traceability: implements requirement E1
        catch (Exception e) {
            System.out.println("ERROR: Failed opening list.txt");
            System.exit(1);
        }
    }

    public static void executeSearch(String input) {
        String[] tokens = input.split("\\s+");

        if (tokens.length == 0) return;
        char searchChar = tokens[0].charAt(0);
        int numArgs = tokens.length;

        // Traceability: implements requirement R3
        switch (searchChar) {

            case 'S':
                String stLast;

                if (numArgs == 2) {
                    stLast = tokens[1];
                    searchStudent(stLast);
                } else if (numArgs == 3 && tokens[2].charAt(0) == 'B') {
                    stLast = tokens[1];
                    searchStudentBus(stLast);
                } else
                    System.out.println("Incorrect args for student search");
                break;

            case 'T':
                String tLast;

                if (numArgs == 2) {
                    tLast = tokens[1];
                    searchTeacher(tLast);
                }
                else {
                    System.out.println("Incorrect args for teacher search");
                }
                break;

            case 'G':
                String grade;

                if (numArgs == 2) {
                    grade = tokens[1];
                    searchGrade(grade);
                }
                else if (numArgs == 3 && (tokens[2].charAt(0) == 'H' || tokens[2].charAt(0) == 'L')) {
                    grade = tokens[1];
                    String keyword = tokens[2];
                    searchGradeExtreme(grade, keyword);
                }
                else if (numArgs == 3 && tokens[2].charAt(0) == 'T') {
                    grade = tokens[1];
                    searchGradeTeachers(grade);
                }
                else {
                    System.out.println("Incorrect args for grade search");
                }
                break;

            case 'B' :
                if (numArgs == 2) {
                    String busNum = tokens[1];
                    searchBus(busNum);
                }
                else {
                    System.out.println("Incorrect args for bus search");
                }
                break;

            case 'A' :
                if (numArgs == 2) {
                    String gradeNum = tokens[1];
                    searchAvgGPA(gradeNum);
                }
                else {
                    System.out.println("Incorrect args for avg gpa search");
                }
                break;

            case 'C':
                if (numArgs == 2) {
                    String classroomNum = tokens[1];
                    searchClassroomStudents(classroomNum);
                }
                else if (numArgs == 3 && tokens[2].charAt(0) == 'T') {
                    String classroomNum = tokens[1];
                    searchClassroomTeacher(classroomNum);
                }
                else {
                    System.out.println("Incorrect args for classroom search");
                }
                break;

            case 'D':
                boolean g = false, t = false, b = false;
                if (numArgs < 2) {
                    g = true;
                    t = true;
                    b = true;
                }
                else {
                    for (int i = 1 ; i < numArgs; i++) {
                        if (tokens[i].charAt(0) == 'G') g = true;
                        if (tokens[i].charAt(0) == 'T') t = true;
                        if (tokens[i].charAt(0) == 'B') b = true;
                    }
                }
                dataAnalytics(g, t, b);
                break;

            case 'E':
                printEnrollment();
                break;

            case 'I' :
                printInfo();
                break;

            case 'Q' :
                quitProgram();
                break;

            // Traceability: implements requirement E1
            default:
                System.out.println("Incorrect search command... try again");
        }
    }

    // Traceability: implements requirements R3, R4
    public static void searchStudent(String stLast) {

        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);

            if (student.getStLast().equalsIgnoreCase(stLast)) {
                Teacher teacher = teachersByClassroom.get(student.getClassroomNum()).get(0);
                System.out.printf("%s,%s,%s,%s,%s,%s\n",
                        student.getStLast(),student.getStFirst(), student.getGrade(), student.getClassroomNum(),
                        teacher.getLast(), teacher.getFirst());
            }
        }
    }

    // Traceability: implements requirements R3, R5
    public static void searchStudentBus(String stLast) {

        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);

            if (student.getStLast().equalsIgnoreCase(stLast)) {
                System.out.printf("%s,%s,%s\n",
                        student.getStLast(), student.getStFirst(), student.getBusNum());
            }
        }
    }

    // Traceability: implements requirements R3, R6
    public static void searchTeacher(String tLast) {

        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);

            if (teachersByClassroom.get(student.getClassroomNum()).get(0).getLast().equalsIgnoreCase(tLast)) {
                System.out.printf("%s,%s\n", student.getStLast(), student.getStFirst());
            }
        }
    }

    // Traceability: implements requirements R3, R7
    public static void searchGrade(String grade) {

        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);

            if (student.getGrade().equalsIgnoreCase(grade)) {
                System.out.printf("%s,%s\n", student.getStLast(), student.getStFirst());
            }
        }
    }

    // Traceability: implements requirements R3, R9
    public static void searchGradeExtreme(String grade, String keyword) {

        ArrayList<Student> studentsInGrade = new ArrayList<Student>();
        int trackIndex = 0;
        double trackGPA = keyword.charAt(0) == 'H' ? 0.0 : Double.MAX_VALUE;

        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);

            if (student.getGrade().equalsIgnoreCase(grade)) {
                studentsInGrade.add(student);

                if (keyword.charAt(0) == 'H' && Double.parseDouble(student.getGPA()) > trackGPA) {
                    trackGPA = Double.parseDouble(student.getGPA());
                    trackIndex = studentsInGrade.size() - 1;
                }
                else if (keyword.charAt(0) == 'L' && Double.parseDouble(student.getGPA()) < trackGPA) {
                    trackGPA = Double.parseDouble(student.getGPA());
                    trackIndex = studentsInGrade.size() - 1;
                }
            }
        }

        if (studentsInGrade.size() == 0) return;
        Student student = studentsInGrade.get(trackIndex);
        Teacher teacher = teachersByClassroom.get(student.getClassroomNum()).get(0);
        System.out.printf("%s,%s,%s,%s,%s,%s\n",
                student.getStLast(), student.getStFirst(), student.getGPA(), teacher.getLast(), teacher.getFirst(),
                student.getBusNum());
    }

    // Traceability: implements requirements NR3
    public static void searchGradeTeachers(String grade) {
        Set<String> uniqueTeachers = new HashSet<String>();

        for (Student student : students) {
            if (student.getGrade().equalsIgnoreCase(grade)) {
                Teacher teacher = teachersByClassroom.get(student.getClassroomNum()).get(0);
                uniqueTeachers.add(teacher.getLast().toUpperCase() + "," + teacher.getFirst().toUpperCase());
            }
        }

        for (String teacher : uniqueTeachers) {
            System.out.println(teacher);
        }
    }

    // Traceability: implements requirements R3, R8
    public static void searchBus(String busNum) {

        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);

            if (student.getBusNum().equalsIgnoreCase(busNum)) {
                System.out.printf("%s,%s,%s,%s\n",
                        student.getStLast(), student.getStFirst(), student.getGrade(), student.getClassroomNum());
            }
        }
    }

    // Traceability: implements requirements R3, R10
    public static void searchAvgGPA(String grade) {

        double gpaSum = 0.0;
        int count = 0;

        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);

            if (student.getGrade().equalsIgnoreCase(grade)) {
                gpaSum += Double.parseDouble(student.getGPA());
                count++;
            }
        }

        System.out.printf("%s,%f\n", grade, gpaSum/count);
    }

    // Traceability: implements requirements NR1
    public static void searchClassroomStudents(String classroomNum) {
        for (Student student : students) {
            if (student.getClassroomNum().equalsIgnoreCase(classroomNum)) {
                System.out.printf("%s,%s\n", student.getStLast(), student.getStFirst());
            }
        }
    }

    // Traceability: implements requirements NR2
    public static void searchClassroomTeacher(String classroomNum) {
        if (teachersByClassroom.get(classroomNum) == null) {
            System.out.println();
            return;
        }
        for (Teacher teacher : teachersByClassroom.get(classroomNum)) {
            System.out.printf("%s,%s\n", teacher.getLast(), teacher.getFirst());
        }
    }

    // Traceability: implements requirements R3, R11
    public static void printInfo() {

        HashMap<Integer, Integer> studentCounts = new HashMap<Integer, Integer>();

        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            Integer grade = Integer.parseInt(student.getGrade());
            if (studentCounts.get(grade) == null)
                studentCounts.put(grade, 1);
            else
                studentCounts.put(grade, studentCounts.get(grade) + 1);
        }
        for (int g = 0; g <= 6; g++) {
            if (studentCounts.get(new Integer(g)) == null)
                System.out.printf("%d: %d\n", g, 0);
            else
                System.out.printf("%d: %d\n", g, studentCounts.get(g));
        }
    }

    // Traceability: implements requirements NR4
    public static void printEnrollment() {

        HashMap<String, Integer> studentCounts = new HashMap<String, Integer>();

        for (Student student : students) {
            String classroom = student.getClassroomNum();
            if (studentCounts.get(classroom) == null)
                studentCounts.put(classroom, 1);
            else
                studentCounts.put(classroom, studentCounts.get(classroom) + 1);
        }

        ArrayList<String> classrooms = new ArrayList<String>();
        classrooms.addAll(studentCounts.keySet());
        Collections.sort(classrooms);

        for (String classroom : classrooms) {
            System.out.printf("Classroom %s: %d\n", classroom, studentCounts.get(classroom));
        }
    }

    // Traceability: implements requirements NR5
    public static void dataAnalytics(boolean gradeFlag, boolean teacherFlag, boolean busFlag) {
        for (Student student : students) {
            System.out.printf("%s", student.getGPA());
            if (gradeFlag) System.out.printf(",%s", student.getGrade());
            if (teacherFlag) {
                Teacher teacher = teachersByClassroom.get(student.getClassroomNum()).get(0);
                System.out.printf(",%s,%s", teacher.getLast(), teacher.getFirst());
            }
            if (busFlag) System.out.printf(",%s", student.getBusNum());

            System.out.println();
        }
    }

    public static void quitProgram() {
        System.out.println("Exiting program...");
        System.exit(0);
    }
}

