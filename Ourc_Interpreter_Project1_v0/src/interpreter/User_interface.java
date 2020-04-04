package interpreter;

public class User_interface {
  
  public static void InitPAL() throws Throwable {
    Main.suTestNum = MyScanner.GetMyScanner().Getsscanner().nextInt();
    // 讀掉回車鍵
    
    MyScanner.GetMyScanner().Getsscanner().nextLine();
    
  } // InitPAL()
  
  public static void Generate_ProgramStart() {
    System.out.println( "Program Starts..." );
  } // Generate_ProgramStart()
  
  public static void PrintResult( Variable var ) throws Throwable {
    if ( var.mDataType == DataType.sBOOLEAN ) {
      if ( var.mValue > 0 ) {
        System.out.println( "> true" );
      } // if
      else {
        System.out.println( "> false" );
      } // else
    } // if
    else {
      if ( Double.isInfinite( var.mValue ) || Double.isNaN( var.mValue ) ) {
        throw new SegmenticErrorException();
      } // if
      else {
        System.out.println( String.format( "%.3f", var.mValue ) );
      } // else
      
    } // else
  } // PrintResult()
  
  public static void PrintResult( String string ) {
    System.out.println( "> " + string );
  } // PrintResult()
  
} // end class User_interface
