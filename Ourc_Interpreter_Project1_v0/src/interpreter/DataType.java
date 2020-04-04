package interpreter;

public class DataType {
  int mPriority;
  String mTypeString;
  
  public DataType( int priority, String string ) {
    mPriority = priority;
    mTypeString = string;
  } // DataType()
  
  public static final DataType sFLOAT = new DataType( 2, "float" );
  public static final DataType sINT = new DataType( 1, "int" );
  public static final DataType sBOOLEAN = new DataType( 3, "bool" );
} // class DataType
