package src.views;

import javax.swing.*;

import src.models.Professor;
import src.models.Student;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainView extends JFrame implements StyleAttributes {
  List<Student> studentsList = new ArrayList<>();
  List<Professor> professorsList = new ArrayList<>();


  public MainView() {
    setTitle("Students management");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    StudentView studentView = new StudentView(studentsList);
    ProfessorView professorView = new ProfessorView(professorsList);
    CourseView courseView = new CourseView(professorsList, studentsList, null);

    this.studentsList.add(new Student("1","Estudante 1", 10, "123"));
    this.studentsList.add(new Student("2","Estudante 2", 10, "456"));
    this.studentsList.add(new Student("3","Estudante 3", 10, "789"));
    this.studentsList.add(new Student("4","Estudante 4", 10, "101"));

    this.professorsList.add(new Professor("1", "Professor 1", 20, "Matemática", "123"));
    this.professorsList.add(new Professor("2", "Professor 2", 20, "Física", "456"));
    this.professorsList.add(new Professor("3","Professor 3", 20, "Biologia", "789"));
    this.professorsList.add(new Professor("4","Professor 4", 20, "Artes", "101"));

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    setSize(screenSize);

    setLocation(0,0);

    JPanel panel = new JPanel();
    
    panel.setLayout(null);    


    JLabel title = new JLabel("Sistema de gerenciamento estudantil");

    title.setFont(new Font(this.fontFamily, Font.BOLD, this.titleFontSize));  
    title.setBounds(this.getScreenMiddleX(screenSize.width, 500), 0, 500, 30);

    JButton studentsButton = new JButton("Alunos");
    studentsButton.setBounds(getScreenMiddleX(screenSize.width, 150), 100, 150, 20);
    
    JButton professorsButton = new JButton("Professores");
    professorsButton.setBounds(getScreenMiddleX(screenSize.width, 150), 125, 150, 20);
    
    JButton coursesButton = new JButton("Cursos");
    coursesButton.setBounds(getScreenMiddleX(screenSize.width, 150), 150, 150, 20);

    studentsButton.addActionListener(e -> studentView.openStudentsModal());
    professorsButton.addActionListener(e -> professorView.openProfessorModal());
    coursesButton.addActionListener(e -> courseView.openCourseModal());

    panel.add(title);

    panel.add(studentsButton);
    panel.add(professorsButton);
    panel.add(coursesButton);

    add(panel);
  }

 
  private int getScreenMiddleX(int screenSize, int componentSize) {
    return (screenSize - componentSize) / 2;
  }
}
