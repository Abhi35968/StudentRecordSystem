package dao;

import java.sql.*;
import model.Student;
import util.DBConnection;

public class StudentDAO {

    Connection conn = DBConnection.getConnection();

    public void addStudent(Student student) {

        try {

            String query = "INSERT INTO students(name,age,course) VALUES(?,?,?)";

            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, student.getName());
            ps.setInt(2, student.getAge());
            ps.setString(3, student.getCourse());

            ps.executeUpdate();

            System.out.println("Student Added");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewStudents() {

        try {

            String query = "SELECT * FROM students";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()) {

                System.out.println(
                        rs.getInt("id") + " "
                        + rs.getString("name") + " "
                        + rs.getInt("age") + " "
                        + rs.getString("course")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteStudent(int id) {

        try {

            String query = "DELETE FROM students WHERE id=?";

            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, id);

            ps.executeUpdate();

            System.out.println("Student Deleted");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}