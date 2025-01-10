import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

// Model Classes
class Admin {
    /**
     * Default constructor
     */
    public Admin() {
    }

    /**
     *
     */
    public String AdminID;

    /**
     *
     */
    public String Email;

    /**
     *
     */
    public String Name;

    /**
     *
     */
    private String Password;

    /**
     * @param CourseID
     * @param StudentID
     * @param AccessLevel
     */
    public void GrantCourseAccess(Object CourseID, Object StudentID, Object AccessLevel) {

    }

    /**
     * @param CourseID
     * @param StudentID
     * @param EnrollmentDate
     */
    public void AddStudent(Object CourseID, Object StudentID, Object EnrollmentDate) {

    }

    /**
     * @param StudentID
     * @param Name
     * @param Email
     * @param CourseLevel
     */
    public void UpdateStudentDetails(Object StudentID, Object Name, Object Email, Object CourseLevel) {

    }
}

class Student {
    /**
     * Default constructor
     */
    public Student() {
    }

    /**
     *
     */
    public String StudentID;

    /**
     *
     */
    public String Email;

    /**
     *
     */
    public String FirstName;

    /**
     *
     */
    public String LastName;

    /**
     *
     */
    public String Password;

    /**
     * @param StudentID
     * @param FirstName
     * @param LastName
     * @param Email
     */
    public void UpdateStudentDetails(Object StudentID, Object FirstName, Object LastName, Object Email) {

    }

    public static void addStudent(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter student ID:");
        int studentId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter first name:");
        String firstName = scanner.nextLine();

        System.out.println("Enter last name:");
        String lastName = scanner.nextLine();

        System.out.println("Enter password:");
        String password = scanner.nextLine();

        System.out.println("Enter email:");
        String email = scanner.nextLine();

        String checkQuery = "SELECT COUNT(*) AS count FROM Student WHERE StudentID = ?";
        PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
        checkStatement.setInt(1, studentId);
        ResultSet resultSet = checkStatement.executeQuery();
        resultSet.next();

        if (resultSet.getInt("count") > 0) {
            System.out.println("This student ID is already inserted: " + studentId);
        } else {
            String insertQuery = "INSERT INTO Student (StudentID, fname, lname, Password, Email) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, studentId);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, email);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("New student added successfully!");
            }
        }
    }

    public static void deleteStudent(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter the student ID to delete:");
        int studentId = scanner.nextInt();

        String deleteQuery = "DELETE FROM Student WHERE StudentID = ?";
        PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
        deleteStatement.setInt(1, studentId);

        int rowsDeleted = deleteStatement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Student with ID " + studentId + " has been deleted successfully!");
        } else {
            System.out.println("No student found with ID " + studentId + ". No records were deleted.");
        }
    }
}

class Course {
    /**
     * Default constructor
     */
    public Course() {
    }

    /**
     *
     */
    public String CourseID;

    /**
     *
     */
    public String Title;

    /**
     *
     */
    public String Description;

    /**
     *
     */
    public Object Badge;

    /**
     *
     */
    public int FinalGrade;

    /**
     * @param CourseID
     * @param StudentID
     * @param EnrollmentDate
     */
    public void AddStudent(Object CourseID, Object StudentID, Object EnrollmentDate) {

    }
}

// View Class
class View {
    public static void displayMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Add a new student");
        System.out.println("2. Delete a student by ID");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }
}

// Controller Class
public class MAINN {

    private static final String DB_URL = "jdbc:sqlserver://DELL\\MSSQLSERVER01:1433;databaseName=LEARNBEAM;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // Establish Database Connection
            Connection connection = DriverManager.getConnection(DB_URL);
            System.out.println("Connection established successfully!");

            while (true) {
                // Display Menu Options
                View.displayMenu();
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        Student.addStudent(connection, scanner);
                        break;
                    case 2:
                        Student.deleteStudent(connection, scanner);
                        break;
                    case 3:
                        System.out.println("Exiting program...");
                        connection.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }
}
