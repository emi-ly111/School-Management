package src.views;

import javax.swing.*;

import src.models.Student;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class StudentView extends JFrame implements StyleAttributes{

  private List<Student> studentsList = new ArrayList<>();

  private JPanel studentsScrollablePanel;
  private JScrollPane studentsScrollPane;

  public StudentView(List<Student> studentsList) {
    this.studentsList = studentsList;
  }

  public void openStudentsModal() {
    JDialog modal = new JDialog(this, "Alterar estudantes", true);

    modal.setSize(700, 600);

    modal.setLayout(null);

    JLabel addStudentTitle = new JLabel("Adicionar estudante");

    addStudentTitle.setFont(new Font(this.fontFamily, Font.BOLD, this.subTitleFontSize));
    addStudentTitle.setBounds(10, 10, 300, 25);

    JLabel studentNameLabel = new JLabel("Nome:");

    studentNameLabel.setFont(new Font(this.fontFamily, Font.PLAIN, this.textFontSize));
    studentNameLabel.setBounds(10, 40, 300, 25);

    JTextField studentNameInput = new JTextField();    
    studentNameInput.setBounds(10, 70, 200, 20);    
    
    JLabel studentAgeLabel = new JLabel("Idade:");

    studentAgeLabel.setFont(new Font(this.fontFamily, Font.PLAIN, this.textFontSize));
    studentAgeLabel.setBounds(10, 95, 300, 25);

    JTextField studentAgeInput = new JTextField();
    studentAgeInput.setBounds(10, 125, 200, 20);    

    JLabel studentRegistrationLabel = new JLabel("Matrícula:");

    studentRegistrationLabel.setFont(new Font(this.fontFamily, Font.PLAIN, this.textFontSize));
    studentRegistrationLabel.setBounds(10, 150, 300, 25);

    JTextField studentRegistrationInput = new JTextField();
    studentRegistrationInput.setBounds(10, 180, 200, 20);    

    JButton addStudentButton = new JButton("Adicionar aluno");    
    addStudentButton.setBounds(getScreenMiddleX(450, 150), 210, 150, 20);
    
    addStudentButton.addActionListener(e -> this.addStudentToList(studentNameInput, studentAgeInput, studentRegistrationInput));

    JLabel searchStudentSection = new JLabel("Pesquise por um aluno:");

    searchStudentSection.setFont(new Font(this.fontFamily, Font.BOLD, this.subTitleFontSize));
    searchStudentSection.setBounds(10, 240, 300, 25);

    JTextField searchStudentInput = new JTextField();
    searchStudentInput.setBounds(10, 270, 200, 20);

    searchStudentInput.addActionListener(e -> this.searchStudent(searchStudentInput));

    studentsScrollablePanel = new JPanel();
    studentsScrollablePanel.setLayout(new BoxLayout(studentsScrollablePanel, BoxLayout.Y_AXIS));
    studentsScrollablePanel.setBackground(Color.WHITE);

    for (Student student : studentsList) {
      JPanel studentItemPanel = createEditableStudentPanel(student);
      studentsScrollablePanel.add(studentItemPanel);
    }

    studentsScrollPane = new JScrollPane(studentsScrollablePanel);
    studentsScrollPane.setBounds(10, 295, 600, 100);
    studentsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    JButton saveButton = new JButton("Salvar Alterações");
    saveButton.setBounds(170, 520, 150, 30);
    saveButton.addActionListener(e -> {
      this.addStudentToList(studentAgeInput, searchStudentInput, studentRegistrationInput);



    for (Component component : studentsScrollablePanel.getComponents()) {          

        if (component instanceof JPanel) {
            JPanel studentItemPanel = (JPanel) component;

    
            JTextField nameField = (JTextField) studentItemPanel.getComponent(1);
            JTextField ageField = (JTextField) studentItemPanel.getComponent(3);
            JTextField registrationField = (JTextField) studentItemPanel.getComponent(5);

    
            int index = studentsScrollablePanel.getComponentZOrder(studentItemPanel);
            Student student = studentsList.get(index);
    
            student.setName(nameField.getText());
            student.setAge(Integer.parseInt(ageField.getText()));
            student.setRegistration(registrationField.getText());
        }
    }
        JOptionPane.showMessageDialog(modal, "Alterações salvas com sucesso!");
    });



    modal.add(addStudentTitle);
    modal.add(studentNameLabel);
    modal.add(studentNameInput);
    modal.add(studentAgeLabel);
    modal.add(studentAgeInput);
    modal.add(studentRegistrationLabel);
    modal.add(studentRegistrationInput);
    modal.add(addStudentButton);
    modal.add(searchStudentSection);
    modal.add(searchStudentInput);
    modal.add(studentsScrollPane);
    modal.add(saveButton);

    modal.setLocationRelativeTo(this);

    modal.setVisible(true);
  }

  private void addStudentToList(JTextField inputName, JTextField inputAge, JTextField inputRegistration) {
    String studentToAddName = inputName.getText();
    String studentToAddAge = inputAge.getText();
    String studentToAddRegistration = inputRegistration.getText();

    boolean isNameNotNull = studentToAddName != null;
    boolean isAgeNotNull = studentToAddAge != null;
    boolean isRegistrationNotNull = studentToAddRegistration != null;

    boolean isNameNotBlank = !studentToAddName.isBlank();
    boolean isAgeNotBlank = !studentToAddAge.isBlank();
    boolean isRegistrationNotBlank = !studentToAddRegistration.isBlank();

    boolean isNameValid = isNameNotNull && isNameNotBlank;
    boolean isAgeValid = isAgeNotNull && isAgeNotBlank;
    boolean isRegistrationValid = isRegistrationNotNull && isRegistrationNotBlank;

    if(isNameValid && isAgeValid && isRegistrationValid) {
      Student newStudent = new Student(studentToAddName, Integer.parseInt(studentToAddAge), studentToAddRegistration);

      this.studentsList.add(newStudent);

      JPanel newStudentPanel = createEditableStudentPanel(newStudent);
      studentsScrollablePanel.add(newStudentPanel);

      studentsScrollablePanel.revalidate();
      studentsScrollablePanel.repaint();
    }

    inputName.setText("");
    inputAge.setText("");
    inputRegistration.setText("");
  } 

  private JPanel createEditableStudentPanel(Student student) {
    JPanel studentItemPanel = new JPanel();
    studentItemPanel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.HORIZONTAL;

  
    JLabel nameLabel = new JLabel("Nome:");
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 0;
    studentItemPanel.add(nameLabel, gbc);
    
    JTextField nameField = new JTextField(student.getName());
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 1;
    studentItemPanel.add(nameField, gbc);

  
    JLabel ageLabel = new JLabel("Idade:");
    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.weightx = 0;
    studentItemPanel.add(ageLabel, gbc);

    JTextField ageField = new JTextField(String.valueOf(student.getAge()));
    gbc.gridx = 1;
    gbc.gridy = 1;
    gbc.weightx = 1;
    studentItemPanel.add(ageField, gbc);

  
    JLabel registrationLabel = new JLabel("Matrícula:");
    gbc.gridx = 2;
    gbc.gridy = 0;
    gbc.weightx = 0;
    studentItemPanel.add(registrationLabel, gbc);

    JTextField registrationField = new JTextField(student.getRegistration());
    gbc.gridx = 2;
    gbc.gridy = 1;
    gbc.weightx = 1;
    studentItemPanel.add(registrationField, gbc);

  
    JButton removeStudentButton = new JButton("Remover");
    removeStudentButton.addActionListener(e -> this.removeStudent(student));
    gbc.gridx = 3;
    gbc.gridy = 0;
    gbc.gridheight = 2;    
    gbc.anchor = GridBagConstraints.CENTER;
    studentItemPanel.add(removeStudentButton, gbc);

    return studentItemPanel;
  }


  private void searchStudent(JTextField searchStudentInput) {
    String searchInput = searchStudentInput.getText();

    if(searchInput != null && !searchInput.isEmpty()) {      
      Stream<Student> filteredStudentsStream = this.studentsList.stream().filter(student -> {
        boolean isNameEqual = student.getName().equals(searchInput);
        boolean isRegistrationEqual = student.getRegistration().equals(searchInput);

        return isNameEqual || isRegistrationEqual;
      });

      studentsScrollablePanel.removeAll();

      List<Student> filteredStudentsList = filteredStudentsStream.toList();            

      for(Student student : filteredStudentsList) {
        JPanel filteredStudent = this.createEditableStudentPanel(student);

        studentsScrollablePanel.add(filteredStudent);
      }
      
      studentsScrollablePanel.revalidate();
      studentsScrollablePanel.repaint();
    } else {      
      studentsScrollablePanel.removeAll();
      for(Student student : this.studentsList) {        

        JPanel studentPane = this.createEditableStudentPanel(student);

        studentsScrollablePanel.add(studentPane);
      }

      studentsScrollablePanel.revalidate();
      studentsScrollablePanel.repaint();
    }
  }

  private void removeStudent(Student student) {
    int studentIndex = this.studentsList.indexOf(student);

    if(studentIndex >= 0) {
      studentsList.remove(studentIndex);
      studentsScrollablePanel.remove(studentIndex);

      studentsScrollablePanel.revalidate();
      studentsScrollablePanel.repaint();
    }
  }

  public static int getScreenMiddleX(int screenSize, int componentSize) {
    return (screenSize - componentSize) / 2;
  }

}
