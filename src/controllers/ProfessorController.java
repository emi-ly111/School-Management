package src.controllers;

import src.data.DataBase;
import src.models.Professor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorController {

    public List<Professor> getAllProfessors() {
        List<Professor> professors = new ArrayList<>();
        String sql = "SELECT id, name FROM professors";

        try (Connection conn = DataBase.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Professor professor = new Professor(rs.getString("id"), rs.getString("name"), rs.getInt("age"), rs.getString("specialty"), rs.getString("registration"));
                professors.add(professor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return professors;
    }
}
