package src.views;

import javax.swing.*;

import src.models.Student;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainView extends JFrame implements StyleAttributes {


  private JPanel studentsScrollablePanel;
  private JScrollPane studentsScrollPane;

  List<Student> studentsList = new ArrayList<>();


  public MainView() {
    setTitle("Students management");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    StudentView studentView = new StudentView(studentsList);

    this.studentsList.add(new Student("Estudante 1", 10, "123"));
    this.studentsList.add(new Student("Estudante 2", 10, "456"));
    this.studentsList.add(new Student("Estudante 3", 10, "789"));
    this.studentsList.add(new Student("Estudante 4", 10, "101"));

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
