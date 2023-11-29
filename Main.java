import java.io.*;
import java.util.*;

class Student {
    String id;
    String name;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }
}

class Course {
    String id;
    String courseCode;
    int finalGrade;

    public Course(String id, String courseCode, int finalGrade) {
        this.id = id;
        this.courseCode = courseCode;
        this.finalGrade = finalGrade;
    }
}

class CompleteStudent {
    String id;
    String name;
    Map<String, Integer> courses; 

    public CompleteStudent(String id, String name) {
        this.id = id;
        this.name = name;
        this.courses = new HashMap<>();
    }

    public void addCourse(String courseCode, int finalGrade) {
        courses.put(courseCode, finalGrade);
    }
}

public class Main {
    public static void main(String[] args) {
        String pathToNameFile = "NameFile.txt"; 
        String pathToCourseFile = "CourseFile.txt"; 

        Map<String, Student> students = readNameFile(pathToNameFile);
        Map<String, Course> courses = readCourseFile(pathToCourseFile);

        Map<String, CompleteStudent> completeStudents = matchKeys(students, courses);

        // For demonstration: Print out the merged information
        for (String id : completeStudents.keySet()) {
            CompleteStudent student = completeStudents.get(id);
            System.out.println("ID: " + student.id + ", Name: " + student.name);
            for (String courseCode : student.courses.keySet()) {
                System.out.println("  Course: " + courseCode + ", Grade: " + student.courses.get(courseCode));
            }
        }
    }

    static Map<String, Student> readNameFile(String filePath) {
        Map<String, Student> students = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 2) {
                    students.put(parts[0], new Student(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return students;
    }

    static Map<String, Course> readCourseFile(String filePath) {
        Map<String, Course> courses = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 6) {
                    int finalGrade = calculateFinalGrade(Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), 
                                                         Integer.parseInt(parts[4]), Integer.parseInt(parts[5]));
                    courses.put(parts[0], new Course(parts[0], parts[1], finalGrade));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return courses;
    }

    static int calculateFinalGrade(int test1, int test2, int test3, int finalExam) {
        return (int) (test1 * 0.2 + test2 * 0.2 + test3 * 0.2 + finalExam * 0.4);
    }

    static Map<String, CompleteStudent> matchKeys(Map<String, Student> students, Map<String, Course> courses) {
        Map<String, CompleteStudent> completeStudents = new HashMap<>();
        for (String id : students.keySet()) {
            if (courses.containsKey(id)) {
                Student student = students.get(id);
                Course course = courses.get(id);
                CompleteStudent completeStudent = new CompleteStudent(student.id, student.name);
                completeStudent.addCourse(course.courseCode, course.finalGrade);
                completeStudents.put(id, completeStudent);
            }
        }
        return completeStudents;
    }
}
