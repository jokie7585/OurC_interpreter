package interpreter;

public class User_interface {
  
  public static void initPAL() throws Throwable {
    
    Main.uTestNum = MyScanner.getMyScanner().getsscanner().nextInt();
    // 讀掉回車鍵
    MyScanner.getMyScanner().getsscanner().nextLine();
    
  }
  
  public static void generate_inputPrompt() throws Throwable {
    System.out.print( "> " );
  }
  
  public static void generate_ProgramStart() throws Throwable {
    System.out.print( "Program Starts..." );
  }
  
  public static void printError( Exception e ) throws Throwable {
    System.out.println( e.toString() );
  }
  
  public static void printResult( String string, DataType type ) throws Throwable {
    
  }
  
}
