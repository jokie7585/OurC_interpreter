package interpreter;

public class Main {
  public static int uTestNum;
  
  public static void main( String[] args ) throws Throwable {
    // 讀入uTestNum
    User_interface.InitPAL();
    // 測試testNum
    // System.out.println( "uTestNum is :" + uTestNum );
    // 初始化tokenStream
    MyScanner myScanner = MyScanner.GetMyScanner();
    // 讀入使用者輸入
    myScanner.GetInputFromStream();
    // 印出所有token Stream
    myScanner.PrintAll();
  } // main
  
} // class Main
