package src.views;

public interface StyleAttributes {
  String fontFamily = "Arial";
  int titleFontSize = 22;
  int subTitleFontSize = 16;
  int textFontSize = 14;

  public static int getScreenMiddleX(int screenSize, int componentSize) {
    return (screenSize - componentSize) / 2;
  }
}
