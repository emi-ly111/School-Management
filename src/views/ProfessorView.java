package src.views;

import javax.swing.*;

import src.models.Professor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class ProfessorView extends JFrame implements StyleAttributes {
  List<Professor> professorsList = new ArrayList<>();

  public ProfessorView(List<Professor> professorsList) {
    this.professorsList = professorsList;
  }
}