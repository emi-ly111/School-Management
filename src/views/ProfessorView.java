package src.views;

import javax.swing.*;

import src.models.Professor;

import src.controllers.ProfessorController;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;


public class ProfessorView extends JFrame implements StyleAttributes {
  private JPanel professorsScrollablePanel;
  private JScrollPane professorsScrollPane;
  
  private ProfessorController professorController = new ProfessorController();

  private List<Professor> professorsList = professorController.getAllProfessors();

  private Set<String> specialties = new HashSet<>();

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

    addProfessorButton.addActionListener(e -> this.addProfessorToList(professorNameInput, professorAgeInput, professorRegistrationInput, specialtiesDropdown));
    
    JLabel searchProfessorSection = new JLabel("Pesquise por um professor:");

    searchProfessorSection.setFont(new Font(this.fontFamily, Font.BOLD, this.subTitleFontSize));
    searchProfessorSection.setBounds(10, 285, 300, 25);

    JTextField searchProfessorInput = new JTextField();
    searchProfessorInput.setBounds(10, 315, 200, 20);

    searchProfessorInput.addActionListener(e -> this.searchProfessor(searchProfessorInput));

    professorsScrollablePanel = new JPanel();
    professorsScrollablePanel.setLayout(new BoxLayout(professorsScrollablePanel, BoxLayout.Y_AXIS));
    professorsScrollablePanel.setBackground(Color.WHITE);
    

    for(Professor professor : professorsList) {
      JPanel professorItemPanel = createEditableProfessorPanel(professor);
      professorsScrollablePanel.add(professorItemPanel);
    }

    professorsScrollPane = new JScrollPane(professorsScrollablePanel);
    professorsScrollPane.setBounds(10, 340, 600, 200);
    professorsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    JButton saveButton = new JButton("Salvar Alterações");
    saveButton.setBounds(170, 520, 150, 30);
    saveButton.addActionListener(e -> {
      this.addProfessorToList(professorAgeInput, professorAgeInput, professorRegistrationInput, specialtiesDropdown);

      for (Component component : professorsScrollablePanel.getComponents()) {          

          if (component instanceof JPanel) {
            JPanel professorItemPanel = (JPanel) component;
    
            JTextField nameField = (JTextField) professorItemPanel.getComponent(1);
            JTextField ageField = (JTextField) professorItemPanel.getComponent(3);
            JTextField registrationField = (JTextField) professorItemPanel.getComponent(5);
            
    
            int index = professorsScrollablePanel.getComponentZOrder(professorItemPanel);
            Professor professor = professorsList.get(index);              
    
            professor.setName(nameField.getText());
            professor.setAge(Integer.parseInt(ageField.getText()));
            professor.setRegistration(registrationField.getText());

            this.professorController.updateProfessor(professor);
          }
      }
          JOptionPane.showMessageDialog(modal, "Alterações salvas com sucesso!");
    });

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
    modal.add(searchProfessorSection);
    modal.add(searchProfessorInput);
    modal.add(professorsScrollPane);

    modal.setLocationRelativeTo(this);

    modal.setVisible(true);
  }

  private void addProfessorToList(JTextField inputName, JTextField inputAge, JTextField inputRegistration, JComboBox<String> inputSpecialty) {
    String professorToAddName = inputName.getText();
    String professorToAddAge = inputAge.getText();
    String professorToAddRegistration = inputRegistration.getText();
    String professorToAddSpecialty = inputSpecialty.getSelectedItem().toString();

    boolean isNameNotNull = professorToAddName != null;
    boolean isAgeNotNull = professorToAddAge != null;
    boolean isRegistrationNotNull = professorToAddRegistration != null;
    boolean isSpecialtyNotNull = professorToAddSpecialty != null;

    boolean isNameNotBlank = !professorToAddName.isBlank();
    boolean isAgeNotBlank = !professorToAddAge.isBlank();
    boolean isRegistrationNotBlank = !professorToAddRegistration.isBlank();
    boolean isSpecialtyNotBlank = !professorToAddSpecialty.isBlank();

    boolean isNameValid = isNameNotNull && isNameNotBlank;
    boolean isAgeValid = isAgeNotNull && isAgeNotBlank;
    boolean isRegistrationValid = isRegistrationNotNull && isRegistrationNotBlank;
    boolean isSpecialtyValid = isSpecialtyNotNull && isSpecialtyNotBlank;

    if(isNameValid && isAgeValid && isRegistrationValid && isSpecialtyValid) {
      Professor newProfessor = new Professor(professorToAddName, Integer.parseInt(professorToAddAge), professorToAddSpecialty, professorToAddRegistration);

      this.professorController.addProfessor(newProfessor);

      this.professorsList.add(newProfessor);

      JPanel newProfessorPanel = createEditableProfessorPanel(newProfessor);
      professorsScrollablePanel.add(newProfessorPanel);

      professorsScrollablePanel.revalidate();
      professorsScrollablePanel.repaint();
    }

    inputName.setText("");
    inputAge.setText("");
    inputRegistration.setText("");
    inputSpecialty.setSelectedItem(this.specialties.toArray()[0]);
  } 

    private JPanel createEditableProfessorPanel(Professor professor) {
    JPanel professorItemPanel = new JPanel();
    professorItemPanel.setLayout(new GridBagLayout());
    professorItemPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
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

    private void searchProfessor(JTextField searchProfessorInput) {
    String searchInput = searchProfessorInput.getText();

    if(searchInput != null && !searchInput.isEmpty()) {      
      Stream<Professor> filteredProfessorsStream = this.professorsList.stream().filter(professor -> {
        boolean isNameEqual = professor.getName().equals(searchInput);
        boolean isRegistrationEqual = professor.getRegistration().equals(searchInput);

        return isNameEqual || isRegistrationEqual;
      });

      professorsScrollablePanel.removeAll();

      List<Professor> filteredProfessorsList = filteredProfessorsStream.toList();            

      for(Professor professor : filteredProfessorsList) {
        JPanel filteredProfessor = this.createEditableProfessorPanel(professor);

        professorsScrollablePanel.add(filteredProfessor);
      }
      
      professorsScrollablePanel.revalidate();
      professorsScrollablePanel.repaint();
    } else {      
      professorsScrollablePanel.removeAll();
      for(Professor professor : this.professorsList) {        

        JPanel studentPane = this.createEditableProfessorPanel(professor);

        professorsScrollablePanel.add(studentPane);
      }

      professorsScrollablePanel.revalidate();
      professorsScrollablePanel.repaint();
    }
  }


  private void removeProfessor(Professor professor) {
  
    Component componentToRemove = null;

    for (Component component : professorsScrollablePanel.getComponents()) {
        if (component instanceof JPanel) {
            JPanel professorItemPanel = (JPanel) component;

          
            JTextField nameField = (JTextField) professorItemPanel.getComponent(1);

            this.professorController.deleteProfessor(professor.getId());
          
            if (nameField.getText().equals(professor.getName())) {
                componentToRemove = component;
                break;
            }
        }
    }

  
    if (componentToRemove != null) {
        professorsList.remove(professor);
        professorsScrollablePanel.remove(componentToRemove);

        professorsScrollablePanel.revalidate();
        professorsScrollablePanel.repaint();
    }
}


  public static int getScreenMiddleX(int screenSize, int componentSize) {
    return (screenSize - componentSize) / 2;
  }

}