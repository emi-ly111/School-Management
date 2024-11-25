package src.controllers;

import src.data.DataBase;
import src.models.Course;
import src.models.Professor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseController {

    public void addCourse(Course course) {
        String sql = "INSERT INTO courses (name, course_load, professor_id) VALUES (?, ?, ?)";

        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, course.getName());
            pstmt.setInt(2, course.getCourseLoad());
            pstmt.setString(3, course.getProfessor().getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT c.id, c.name, c.course_load, p.id as professor_id, p.name as professor_name " +
                     "FROM courses c JOIN professors p ON c.professor_id = p.id";

        try (Connection conn = DataBase.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Professor professor = new Professor(rs.getString("id"), rs.getString("name"), rs.getInt("age"), rs.getString("specialty"), rs.getString("registration"));
                Course course = new Course(rs.getString("id"), rs.getString("name"), rs.getInt("course_load"), professor);
                courses.add(course);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }

    public void updateCourse(Course course) {
        String sql = "UPDATE courses SET name = ?, course_load = ?, professor_id = ? WHERE id = ?";

        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, course.getName());
            pstmt.setInt(2, course.getCourseLoad());
            pstmt.setString(3, course.getProfessor().getId());
            pstmt.setString(4, course.getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCourse(int courseId) {
        String sql = "DELETE FROM courses WHERE id = ?";

        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, courseId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
