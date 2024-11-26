package src.views;

import javax.swing.*;

import src.models.Student;
import src.models.Professor;
import src.controllers.CourseController;
import src.controllers.ProfessorController;
import src.controllers.StudentController;
import src.models.Course;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CourseView extends JFrame implements StyleAttributes {
    private CourseController courseController = new CourseController();
    private ProfessorController professorController = new ProfessorController();
    private StudentController studentController = new StudentController();

    private List<Professor> professorsList;
    private List<Student> studentsList;
    private List<Course> coursesList;

    private JPanel coursesScrollablePanel;

    public CourseView() {
        this.professorsList = this.professorController.getAllProfessors();
        this.studentsList = this.studentController.getAllStudents();
        this.coursesList = this.courseController.getAllCourses();
    }

    public CourseView(List<Course> coursesList) {
        this.professorsList = this.professorController.getAllProfessors();
        this.studentsList = this.studentController.getAllStudents();
        this.coursesList = coursesList;
    }

    public CourseView(List<Professor> professorsList, List<Student> studentsList, List<Course> coursesList) {
        this.professorsList = professorsList != null ? professorsList : new ArrayList<>();
        this.studentsList = studentsList != null ? studentsList : new ArrayList<>();
        this.coursesList = coursesList != null ? coursesList : new ArrayList<>();
    }

    public void openCourseModal() {
        JDialog modal = new JDialog(this, "Gerenciar Cursos", true);
        modal.setSize(900, 700);
        modal.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel addCourseTitle = new JLabel("Adicionar Novo Curso");
        addCourseTitle.setFont(new Font(this.fontFamily, Font.BOLD, this.subTitleFontSize));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(addCourseTitle, gbc);

        JLabel courseNameLabel = new JLabel("Nome:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(courseNameLabel, gbc);

        JTextField courseNameInput = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.gridwidth = 2;
        mainPanel.add(courseNameInput, gbc);

        JLabel courseLoadLabel = new JLabel("Carga horária:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.gridwidth = 1;
        mainPanel.add(courseLoadLabel, gbc);

        JTextField courseLoadInput = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.gridwidth = 2;
        mainPanel.add(courseLoadInput, gbc);

        JLabel courseProfessorLabel = new JLabel("Professor:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.gridwidth = 1;
        mainPanel.add(courseProfessorLabel, gbc);

        JComboBox<Professor> professorDropdown = new JComboBox<>(professorsList.toArray(new Professor[0]));
        professorDropdown.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Professor) {
                    setText(((Professor) value).getName());
                }
                return this;
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.gridwidth = 2;
        mainPanel.add(professorDropdown, gbc);

        JLabel selectStudentsLabel = new JLabel("Selecionar Alunos:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(selectStudentsLabel, gbc);

        DefaultListModel<Student> studentListModel = new DefaultListModel<>();
        for (Student student : studentsList) {
            studentListModel.addElement(student);
        }

        JList<Student> studentList = new JList<>(studentListModel);
        studentList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        studentList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Student) {
                    setText(((Student) value).getName());
                }
                return this;
            }
        });

        JScrollPane studentScrollPane = new JScrollPane(studentList);
        studentScrollPane.setPreferredSize(new Dimension(800, 200));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(studentScrollPane, gbc);

        JButton addCourseButton = new JButton("Adicionar Curso");
        addCourseButton.addActionListener(e -> {
            String courseName = courseNameInput.getText();
            int courseLoad = Integer.parseInt(courseLoadInput.getText());
            Professor selectedProfessor = (Professor) professorDropdown.getSelectedItem();

            List<Student> selectedStudents = studentList.getSelectedValuesList();

            if (courseName.isBlank() || selectedProfessor == null || selectedStudents.isEmpty()) {
                JOptionPane.showMessageDialog(modal, "Preencha todos os campos e selecione ao menos um aluno!");
                return;
            }

            Course newCourse = new Course(courseName, courseLoad, selectedProfessor);
            coursesList.add(newCourse);

            this.courseController.addCourse(newCourse);

            JPanel coursePanel = createEditableCoursePanel(newCourse);
            coursesScrollablePanel.add(coursePanel);

            coursesScrollablePanel.revalidate();
            coursesScrollablePanel.repaint();

            courseNameInput.setText("");
            courseLoadInput.setText("");
            professorDropdown.setSelectedIndex(0);
            studentList.clearSelection();
        });
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        mainPanel.add(addCourseButton, gbc);

        JLabel filterCoursesLabel = new JLabel("Pesquisar Cursos:");
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        mainPanel.add(filterCoursesLabel, gbc);

        JTextField searchCourseInput = new JTextField();
        searchCourseInput.addActionListener(e -> filterCourses(searchCourseInput.getText()));
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        mainPanel.add(searchCourseInput, gbc);

        JLabel existingCoursesLabel = new JLabel("Cursos Existentes:");
        existingCoursesLabel.setFont(new Font(this.fontFamily, Font.BOLD, this.subTitleFontSize));
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 3;
        mainPanel.add(existingCoursesLabel, gbc);

        coursesScrollablePanel = new JPanel();
        coursesScrollablePanel.setLayout(new BoxLayout(coursesScrollablePanel, BoxLayout.Y_AXIS));
        for (Course course : coursesList) {
            JPanel coursePanel = createEditableCoursePanel(course);
            coursesScrollablePanel.add(coursePanel);
        }
        JScrollPane coursesScrollPane = new JScrollPane(coursesScrollablePanel);
        coursesScrollPane.setPreferredSize(new Dimension(800, 300));
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        mainPanel.add(coursesScrollPane, gbc);

        modal.add(mainPanel, BorderLayout.CENTER);

        JButton saveButton = new JButton("Salvar Alterações");
        saveButton.addActionListener(e -> JOptionPane.showMessageDialog(modal, "Alterações salvas com sucesso!"));
        modal.add(saveButton, BorderLayout.SOUTH);

        modal.setLocationRelativeTo(this);
        modal.setVisible(true);
    }

    private JPanel createEditableCoursePanel(Course course) {
        JPanel coursePanel = new JPanel();
        coursePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Nome:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        coursePanel.add(nameLabel, gbc);

        JTextField nameField = new JTextField(course.getName());
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        coursePanel.add(nameField, gbc);

        JLabel loadLabel = new JLabel("Carga:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        coursePanel.add(loadLabel, gbc);

        JTextField loadField = new JTextField(String.valueOf(course.getCourseLoad()));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        coursePanel.add(loadField, gbc);

        JLabel professorLabel = new JLabel("Professor:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        coursePanel.add(professorLabel, gbc);
        
        JComboBox<Professor> professorDropdown = new JComboBox<>(professorsList.toArray(new Professor[0]));
        professorDropdown.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Professor) {
                    setText(((Professor) value).getName());
                }
                return this;
            }
        });
        professorDropdown.setSelectedItem(course.getProfessor());
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        coursePanel.add(professorDropdown, gbc);

        JButton removeButton = new JButton("Remover");
        removeButton.addActionListener(e -> {
            coursesList.remove(course);
            this.courseController.deleteCourse(course.getId());
            coursesScrollablePanel.remove(coursePanel);
            coursesScrollablePanel.revalidate();
            coursesScrollablePanel.repaint();
        });
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        coursePanel.add(removeButton, gbc);

        return coursePanel;
    }

    private void filterCourses(String searchText) {
        coursesScrollablePanel.removeAll();

        coursesList.stream()
                .filter(course -> course.getName().toLowerCase().contains(searchText.toLowerCase()))
                .forEach(course -> {
                    JPanel coursePanel = createEditableCoursePanel(course);
                    coursesScrollablePanel.add(coursePanel);
                });

        coursesScrollablePanel.revalidate();
        coursesScrollablePanel.repaint();
    }
}
