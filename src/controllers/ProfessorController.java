package src.controllers;

import src.data.DataBase;
import src.models.Professor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorController {

    public List<Professor> getAllProfessors() {
        List<Professor> professors = new ArrayList<>();
        String sql = "SELECT id, name, age, specialty, registration FROM professors";

        try (Connection conn = DataBase.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Professor professor = new Professor(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("specialty"),
                        rs.getString("registration")
                );
                professors.add(professor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return professors;
    }

    public Professor getProfessorById(String id) {
        String sql = "SELECT id, name, age, specialty, registration FROM professors WHERE id = ?";
        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Professor(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("age"),
                            rs.getString("specialty"),
                            rs.getString("registration")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean addProfessor(Professor professor) {
        String sql = "SELECT InsertTeacher(?, ?, '?); ";

        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, professor.getName());
            pstmt.setInt(2, professor.getAge());
            pstmt.setString(3, professor.getSpecialty());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateProfessor(Professor professor) {
        String sql = "UPDATE professors SET name = ?, age = ?, specialty = ?, registration = ? WHERE id = ?";

        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, professor.getName());
            pstmt.setInt(2, professor.getAge());
            pstmt.setString(3, professor.getSpecialty());
            pstmt.setString(4, professor.getRegistration());
            pstmt.setInt(5, professor.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteProfessor(int id) {
        String sql = "DELETE FROM professors WHERE id = ?";

        try (Connection conn = DataBase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
