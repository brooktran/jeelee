package notepad;

public class JavaFile {
  
  StringBuffer string = new StringBuffer();

  public void PutString(char a) {
    if( a != ' ') {
      string.insert(string.length(), a);
    } else if(string.equals("public")) {

    }
  }

  public void display() {
    System.out.println("javaFile = "+string);
  }
}