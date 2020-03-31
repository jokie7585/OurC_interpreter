package interpreter;

public class User_interface {
  
  public static void InitPAL() throws Throwable {
    Main.suTestNum = MyScanner.GetMyScanner().Getsscanner().nextInt();
    // 讀掉回車鍵
    
    MyScanner.GetMyScanner().Getsscanner().nextLine();
    
  } // InitPAL()
  
  public static void Generate_ProgramStart() throws Throwable {
    System.out.print( "Program Starts..." );
  } // Generate_ProgramStart()
  
  public static void PrintResult( String string, DataType type ) throws Throwable {
    
  } // PrintResult()
  
} // end class User_interface
