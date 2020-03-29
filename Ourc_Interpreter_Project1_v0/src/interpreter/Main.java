package interpreter;

public class Main {
  public static int uTestNum;
  
  public static void main( String[] args ) throws Throwable {
    // 讀入uTestNum
    User_interface.initPAL();
    // 測試testNum
    // System.out.println( "uTestNum is :" + uTestNum );
    // 初始化tokenStream
    MyScanner myScanner = MyScanner.getMyScanner();
    // 讀入使用者輸入
    myScanner.getInputFromStream();
    // 印出所有token Stream
    myScanner._dev_printAll();
  }
  
}
