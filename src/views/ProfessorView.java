package src.views;

import javax.swing.*;

import src.models.Professor;
import src.models.Student;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ProfessorView extends JFrame implements StyleAttributes {
  private JPanel professorsScrollablePanel;
  private JScrollPane professorsScrollPane;
  
  List<Professor> professorsList = new ArrayList<>();

  Set<String> specialties = new HashSet<>();

  public ProfessorView(List<Professor> professorsList) {
    this.professorsList = professorsList;

    this.specialties.add("Matemática");
    this.specialties.add("Biologia");
    this.specialties.add("Química");
    this.specialties.add("Física");
    this.specialties.add("Inglês");
    this.specialties.add("Espanhol");
    this.specialties.add("Português");
    this.specialties.add("Redação");
    this.specialties.add("Literatura");
    this.specialties.add("Educação física");
    this.specialties.add("Artes");

  }

  public ProfessorView(List<Professor> professorsList, Set<String> specialties) {
    this.professorsList = professorsList;
    this.specialties = specialties;
  }

  public void openProfessorModal() {
    JDialog modal = new JDialog(this, "Alterar professores", true);

    modal.setSize(700, 600);

    modal.setLayout(null);

    JLabel addProfessorTitle = new JLabel("Adicionar professor");

    addProfessorTitle.setFont(new Font(this.fontFamily, Font.BOLD, this.subTitleFontSize));
    addProfessorTitle.setBounds(10, 10, 300, 25);

    JLabel professorNameLabel = new JLabel("Nome:");

    professorNameLabel.setFont(new Font(this.fontFamily, Font.PLAIN, this.textFontSize));
    professorNameLabel.setBounds(10, 40, 300, 25);

    JTextField professorNameInput = new JTextField();    
    professorNameInput.setBounds(10, 70, 200, 20);    

    JLabel professorAgeLabel = new JLabel("Idade:");

    professorAgeLabel.setFont(new Font(this.fontFamily, Font.PLAIN, this.textFontSize));
    professorAgeLabel.setBounds(10, 95, 300, 25);

    JTextField professorAgeInput = new JTextField();
    professorAgeInput.setBounds(10, 125, 200, 20);    

    JLabel professorRegistrationLabel = new JLabel("Matrícula:");

    professorRegistrationLabel.setFont(new Font(this.fontFamily, Font.PLAIN, this.textFontSize));
    professorRegistrationLabel.setBounds(10, 150, 300, 25);

    JTextField professorRegistrationInput = new JTextField();
    professorRegistrationInput.setBounds(10, 180, 200, 20);    

    JLabel professorSpecialtyLabel = new JLabel("Especialidade:");

    professorSpecialtyLabel.setFont(new Font(this.fontFamily, Font.PLAIN, this.textFontSize));
    professorSpecialtyLabel.setBounds(10, 205, 200, 25);


    JComboBox<String> specialtiesDropdown = new JComboBox<>(this.specialties.toArray(new String[0]));
    specialtiesDropdown.setBounds(10, 235, 300, 25);

    JButton addProfessorButton = new JButton("Adicionar professor");    
    addProfessorButton.setBounds(getScreenMiddleX(450, 150), 270, 150, 20);
    
    JLabel searchProfessorSection = new JLabel("Pesquise por um professor:");

    searchProfessorSection.setFont(new Font(this.fontFamily, Font.BOLD, this.subTitleFontSize));
    searchProfessorSection.setBounds(10, 285, 300, 25);

    JTextField searchProfessorInput = new JTextField();
    searchProfessorInput.setBounds(10, 315, 200, 20);


    modal.add(addProfessorTitle);
    modal.add(professorNameLabel);
    modal.add(professorNameInput);
    modal.add(professorAgeLabel);
    modal.add(professorAgeInput);
    modal.add(professorRegistrationLabel);
    modal.add(professorRegistrationInput);
    modal.add(professorSpecialtyLabel);
    modal.add(specialtiesDropdown);
    modal.add(addProfessorButton);

    modal.setLocationRelativeTo(this);

    modal.setVisible(true);
  }

    private JPanel createEditableProfessorPanel(Professor professor) {
    JPanel professorItemPanel = new JPanel();
    professorItemPanel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.HORIZONTAL;

  
    JLabel nameLabel = new JLabel("Nome:");
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 0;
    professorItemPanel.add(nameLabel, gbc);
    
    JTextField nameField = new JTextField(professor.getName());
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 1;
    professorItemPanel.add(nameField, gbc);

  
    JLabel ageLabel = new JLabel("Idade:");
    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.weightx = 0;
    professorItemPanel.add(ageLabel, gbc);

    JTextField ageField = new JTextField(String.valueOf(professor.getAge()));
    gbc.gridx = 1;
    gbc.gridy = 1;
    gbc.weightx = 1;
    professorItemPanel.add(ageField, gbc);

  
    JLabel registrationLabel = new JLabel("Matrícula:");
    gbc.gridx = 2;
    gbc.gridy = 0;
    gbc.weightx = 0;
    professorItemPanel.add(registrationLabel, gbc);

    JTextField registrationField = new JTextField(professor.getRegistration());
    gbc.gridx = 2;
    gbc.gridy = 1;
    gbc.weightx = 1;
    professorItemPanel.add(registrationField, gbc);

    JLabel specialtyLabel = new JLabel("Especialidade:");
    gbc.gridx = 3;
    gbc.gridy = 0;
    gbc.weightx = 0;
    professorItemPanel.add(specialtyLabel, gbc);


    JComboBox<String> specialtiesDropdown = new JComboBox<>(this.specialties.toArray(new String[0]));
    specialtiesDropdown.setSelectedItem(professor.getSpecialty());
    gbc.gridx = 3;
    gbc.gridy = 1;
    gbc.weightx = 1;
    professorItemPanel.add(specialtiesDropdown);
    
  
    JButton removeProfessorButton = new JButton("Remover");
    removeProfessorButton.addActionListener(e -> this.removeProfessor(professor));
    gbc.gridx = 4;
    gbc.gridy = 0;
    gbc.gridheight = 2;    
    gbc.anchor = GridBagConstraints.CENTER;
    professorItemPanel.add(removeProfessorButton, gbc);

    return professorItemPanel;
  }

  private void removeProfessor(Professor professor) {
    int professorIndex = this.professorsList.indexOf(professor);

    if(professorIndex >= 0) {
      professorsList.remove(professorIndex);
      professorsScrollablePanel.remove(professorIndex);

      professorsScrollablePanel.revalidate();
      professorsScrollablePanel.repaint();
    }
  }

  public static int getScreenMiddleX(int screenSize, int componentSize) {
    return (screenSize - componentSize) / 2;
  }

}