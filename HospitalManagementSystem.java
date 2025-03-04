import java.sql.*;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HospitalManagementSystem {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hospital_management";
    private static final String USER = "root";
    private static final String PASS = "Rain";
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        System.out.println("HOSPITAL MANAGEMENT");
        System.out.println("----");

        while (true) {
            System.out.println("\nHospital Management System");
            System.out.println("1. Doctor");
            System.out.println("2. Nurse");
            System.out.println("3. Labour");
            System.out.println("4. Patient");
            System.out.println("5. Pharmacy");
            System.out.println("6. Ward");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    manageDoctors();
                    break;
                case 2:
                    manageNurses();
                    break;
                case 3:
                    manageLabours();
                    break;
                case 4:
                    managePatients();
                    break;
                case 5:
                    managePharmacies();
                    break;
                case 6:
                    manageWards();
                    break;
                case 7:
                    System.out.println("Exiting program...");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void manageDoctors() {
        System.out.println("\nDoctor Management");
        System.out.println("1. Add Doctor");
        System.out.println("2. Update Doctor");
        System.out.println("3. Delete Doctor");
        System.out.println("4. Display Doctors");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                addDoctor();
                break;
            case 2:
                updateDoctor();
                break;
            case 3:
                deleteDoctor();
                break;
            case 4:
                displayDoctors();
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    private static void addDoctor() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_management", "root", "Rain");
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO doctor (ID, Name, Phone_Number, Address, DOB, Speciality, Branch, Date_of_Joining, Date_of_Resignation) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

            System.out.print("Enter doctor ID: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            System.out.print("Enter doctor name: ");
            String name = scanner.nextLine();

            System.out.print("Enter doctor phone number: ");
            String phoneNumber = scanner.nextLine();

            System.out.print("Enter doctor address: ");
            String address = scanner.nextLine();

            System.out.print("Enter doctor date of birth (dd/MM/yyyy): ");
            String dob = scanner.nextLine();

            System.out.print("Enter doctor speciality: ");
            String speciality = scanner.nextLine();

            System.out.print("Enter branch: ");
            String branch = scanner.nextLine();

            System.out.print("Enter date of joining (dd/MM/yyyy): ");
            String dateOfJoining = scanner.nextLine();

            System.out.print("Enter date of resignation (dd/MM/yyyy) or leave empty if still working: ");
            String dateOfResignation = scanner.nextLine();

            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, phoneNumber);
            stmt.setString(4, address);
            stmt.setDate(5, java.sql.Date.valueOf(LocalDate.parse(dob, dateFormatter)));
            stmt.setString(6, speciality);
            stmt.setString(7, branch);
            stmt.setDate(8, java.sql.Date.valueOf(LocalDate.parse(dateOfJoining, dateFormatter)));
            if (!dateOfResignation.isEmpty()) {
                stmt.setDate(9, java.sql.Date.valueOf(LocalDate.parse(dateOfResignation, dateFormatter)));
            } else {
                stmt.setNull(9, java.sql.Types.DATE);
            }

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Doctor added successfully");
            } else {
                System.out.println("Failed to add doctor");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateDoctor() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            System.out.print("Enter doctor ID to update: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            System.out.println("1. Update Name");
            System.out.println("2. Update Phone Number");
            System.out.println("3. Update Address");
            System.out.println("4. Update Speciality");
            System.out.println("5. Update Branch");
            System.out.println("6. Update Date of Joining");
            System.out.println("7. Update Date of Resignation");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            String sql = "";
            String value = "";

            switch (choice) {
                case 1:
                    System.out.print("Enter updated name: ");
                    value = scanner.nextLine();
                    sql = "UPDATE doctor SET Name=? WHERE ID=?";
                    break;
                case 2:
                    System.out.print("Enter updated phone number: ");
                    value = scanner.nextLine();
                    sql = "UPDATE doctor SET Phone_Number=? WHERE ID=?";
                    break;
                case 3:
                    System.out.print("Enter updated address: ");
                    value = scanner.nextLine();
                    sql = "UPDATE doctor SET Address=? WHERE ID=?";
                    break;
                case 4:
                    System.out.print("Enter updated speciality: ");
                    value = scanner.nextLine();
                    sql = "UPDATE doctor SET Speciality=? WHERE ID=?";
                    break;
                case 5:
                    System.out.print("Enter updated branch: ");
                    value = scanner.nextLine();
                    sql = "UPDATE doctor SET Branch=? WHERE ID=?";
                    break;
                case 6:
                    System.out.print("Enter updated date of joining (dd/MM/yyyy): ");
                    value = scanner.nextLine();
                    sql = "UPDATE doctor SET Date_of_Joining=? WHERE ID=?";
                    break;
                case 7:
                    System.out.print("Enter updated date of resignation (dd/MM/yyyy) or leave empty if still working: ");
                    value = scanner.nextLine();
                    sql = "UPDATE doctor SET Date_of_Resignation=? WHERE ID=?";
                    break;
                default:
                    System.out.println("Invalid choice");
                    return;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                if (choice == 6 || choice == 7) {
                    if (!value.isEmpty()) {
                        stmt.setDate(1, java.sql.Date.valueOf(LocalDate.parse(value, dateFormatter)));
                    } else {
                        stmt.setNull(1, java.sql.Types.DATE);
                    }
                } else {
                    stmt.setString(1, value);
                }
                stmt.setInt(2, id);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Doctor details updated successfully");
                } else {
                    System.out.println("No doctor found with the provided ID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteDoctor() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM doctor WHERE ID=?")) {

            System.out.print("Enter doctor ID to delete: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Doctor deleted successfully");
            } else {
                System.out.println("No doctor found with the provided ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayDoctors() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            System.out.print("Do you want to display specific ID (yes/no)? ");
            String specific = scanner.nextLine().toLowerCase();

            String sql;
            PreparedStatement stmt;

            if (specific.equals("yes")) {
                System.out.print("Enter doctor ID: ");
                int id = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                sql = "SELECT * FROM doctor WHERE ID=?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, id);
            } else {
                sql = "SELECT * FROM doctor";
                stmt = conn.prepareStatement(sql);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("ID\tName\tPhone Number\tAddress\tDOB\tSpeciality\tBranch\tDate of Joining\tDate of Resignation");
                    do {
                        System.out.printf("%d\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\n",
                                rs.getInt("ID"),
                                rs.getString("Name"),
                                rs.getString("Phone_Number"),
                                rs.getString("Address"),
                                rs.getDate("DOB"),
                                rs.getString("Speciality"),
                                rs.getString("Branch"),
                                rs.getDate("Date_of_Joining"),
                                rs.getDate("Date_of_Resignation"));
                    } while (rs.next());
                } else {
                    System.out.println("No doctors found");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Similar methods for Nurse, Labour, Patient, Pharmacy, and Ward management would be implemented here
    // For brevity, I'm not including all of them in this example, but they would follow a similar structure

    private static void manageNurses() {
        System.out.println("Nurse management functionality not implemented in this example.");
    }

    private static void manageLabours() {
        System.out.println("Labour management functionality not implemented in this example.");
    }

    private static void managePatients() {
        System.out.println("Patient management functionality not implemented in this example.");
    }

    private static void managePharmacies() {
        System.out.println("Pharmacy management functionality not implemented in this example.");
    }

    private static void manageWards() {
        System.out.println("Ward management functionality not implemented in this example.");
    }
}java -cp ".;C:\Users\aadit\Downloads\mysql-connector-j-8.0.31.jar" HospitalManagementSystem