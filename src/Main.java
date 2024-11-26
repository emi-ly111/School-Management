package src;

import src.views.*;

public class Main {
  public static void main(String[] args) {
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {      
      e.printStackTrace();
    }

    MainView mainPage = new MainView();

    mainPage.setVisible(true);
  }
}