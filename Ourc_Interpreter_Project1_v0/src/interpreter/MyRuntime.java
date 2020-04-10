package interpreter;

import java.util.Stack;
import java.util.Vector;

// CommandRunner透過MyRuntime調用 Runtime Unit 執行 Command
public class MyRuntime {
  private static MyRuntime sSingleton_MyRuntime = new MyRuntime();
  // Init RuntimeUnit
  private MyCPU mMyCPU;
  
  private MyRuntime() {
    mMyCPU = MyCPU.GetMyCPU();
  } // MyRuntime()
  
  public static MyRuntime GetMyRuntime() {
    return sSingleton_MyRuntime;
  } // GetMyRuntime()
  
  // invoke MyCPU and run all Command in CallStack
  // 攔截所有runtime error 並以 SegmenticErrorException代替
  public void Run() throws Throwable {
    // try {
    // mMyCPU.Run();
    // } // try
    // catch ( Exception e ) {
    // throw new SegmenticErrorException();
    // } // catch
    
    mMyCPU.Run();
  } // Run()
  
  // append Command into callStack
  public void Push( Vector<Command> commands ) {
    mMyCPU.LoadCommand( commands );
  } // Push()
  
  public void InitCPU() {
    mMyCPU.InitCPU();
  } // InitCPU()
  
} // class MyRuntime

// MyCPU是此編譯器編譯出的指令所執行的Runtime Unit之一
// 它包含了 functionCallStack 與 register
// 在日後的project也會擴充scope與block的管理。
class MyCPU {
  private static MyCPU sSingleton_MyCPU = new MyCPU();
  private Stack<Variable> mLocalVariables = new Stack<Variable>();
  private Vector<Command> mCommands = new Vector<Command>();
  private boolean mMode = false;
  
  private MyCPU() {
    
  } // MyCPU()
  
  public static MyCPU GetMyCPU() {
    return sSingleton_MyCPU;
  } // GetMyCPU()
  
  public void LoadCommand( Vector<Command> commands ) {
    for ( int i = 0 ; i < commands.size() ; i++ ) {
      mCommands.add( commands.elementAt( i ) );
    } // for
  } // LoadCommand()
  
  public void Run() throws Throwable {
    while ( !mCommands.isEmpty() ) {
      Command tempCommand = mCommands.elementAt( 0 );
      if ( tempCommand.mCommnad_Type == Commnad_Type.sADD ) {
        Add( tempCommand );
      } // if
      else if ( tempCommand.mCommnad_Type == Commnad_Type.sMULT ) {
        Mult( tempCommand );
      } // else if
      else if ( tempCommand.mCommnad_Type == Commnad_Type.sDIV ) {
        Div( tempCommand );
      } // else if
      else if ( tempCommand.mCommnad_Type == Commnad_Type.sSUB ) {
        Sub( tempCommand );
      } // else if
      else if ( tempCommand.mCommnad_Type == Commnad_Type.sPUSH ) {
        Push( tempCommand );
      } // else if
      else if ( tempCommand.mCommnad_Type == Commnad_Type.sASSIGN ) {
        Assign( tempCommand );
      } // else if
      else if ( tempCommand.mCommnad_Type == Commnad_Type.sBOOLEANOPERATION ) {
        BooleanOperation( tempCommand );
      } // else if
      // else if ( tempCommand.mCommnad_Type == Commnad_Type.sSWITCHMODE ) {
      // Switch( tempCommand );
      // } // else if
      
      mCommands.remove( 0 );
    } // while
    
    User_interface.PrintResult( mLocalVariables.pop() );
  } // Run()
  
  private void Switch( Command command ) {
    mMode = !mMode;
  } // Switch()
  
  private void Assign( Command command ) throws Throwable {
    Variable tempVariable = mLocalVariables.pop();
    Variable assignTargetVariable = mLocalVariables.pop();
    
    // 設計上有問題 要先GetVlue才會設定mDataType
    assignTargetVariable.mValue = tempVariable.GetVlue();
    assignTargetVariable.mDataType = tempVariable.mDataType;
    Register.sRegister.Assign( assignTargetVariable );
    // push 回去 讓userInterface印出最終結果
    mLocalVariables.push( assignTargetVariable );
  } // Assign()
  
  private void Push( Command command ) {
    mLocalVariables.push( new Variable( command.mOperand, command.mSymbolOfOperand ) );
  } // Push()
  
  private void Add( Command command ) throws Throwable {
    // computing
    Variable addentVariable = mLocalVariables.pop();
    Variable agentVariable = mLocalVariables.pop();
    
    agentVariable.mValue = agentVariable.GetVlue() + addentVariable.GetVlue();
    
    // type check
    if ( agentVariable.mDataType.mPriority < addentVariable.mDataType.mPriority ) {
      agentVariable.mDataType = addentVariable.mDataType;
    } // if
    
    // 存回stack
    mLocalVariables.push( agentVariable );
    
  } // Add()
  
  private void Mult( Command command ) throws Throwable {
    // computing
    Variable addentVariable = mLocalVariables.pop();
    Variable agentVariable = mLocalVariables.pop();
    
    agentVariable.mValue = agentVariable.GetVlue() * addentVariable.GetVlue();
    
    // type check
    if ( agentVariable.mDataType.mPriority < addentVariable.mDataType.mPriority ) {
      agentVariable.mDataType = addentVariable.mDataType;
    } // if
    
    // 存回stack
    mLocalVariables.push( agentVariable );
    
  } // Mult()
  
  private void Div( Command command ) throws Throwable {
    // computing
    Variable addentVariable = mLocalVariables.pop();
    Variable agentVariable = mLocalVariables.pop();
    // if div 0 Error
    if ( addentVariable.GetVlue() == 0 ) {
      throw new SegmenticErrorException();
    } // if
    
    agentVariable.mValue = agentVariable.GetVlue() / addentVariable.GetVlue();
    // type check
    if ( agentVariable.mDataType.mPriority < addentVariable.mDataType.mPriority ) {
      agentVariable.mDataType = addentVariable.mDataType;
    } // if
    
    // if ( mMode ) {
    // agentVariable.mDataType = DataType.sFLOAT;
    // } // if
    //
    // if ( agentVariable.mDataType == DataType.sINT ) {
    // String tempString = "" + agentVariable.mValue.intValue();
    // agentVariable.mValue = Double.parseDouble( tempString );
    // } // if
    
    // 存回stack
    mLocalVariables.push( agentVariable );
    
  } // Div()
  
  private void Sub( Command command ) throws Throwable {
    // computing
    Variable addentVariable = mLocalVariables.pop();
    Variable agentVariable = mLocalVariables.pop();
    agentVariable.mValue = agentVariable.GetVlue() - addentVariable.GetVlue();
    // type check
    if ( agentVariable.mDataType.mPriority < addentVariable.mDataType.mPriority ) {
      agentVariable.mDataType = addentVariable.mDataType;
    } // if
    
    // 存回stack
    mLocalVariables.push( agentVariable );
    
  } // Sub()
  
  private void BooleanOperation( Command command ) throws Throwable {
    Variable rightVariable = mLocalVariables.pop();
    Variable leftVariable = mLocalVariables.pop();
    
    if ( command.mOperand.equals( "=" ) ) {
      if ( leftVariable.GetVlue() - rightVariable.GetVlue() <= 0.0001
          && leftVariable.GetVlue() - rightVariable.GetVlue() >= -0.0001 ) {
        leftVariable.mDataType = DataType.sBOOLEAN;
        leftVariable.mValue = 1.0f;
      } // if
      else {
        leftVariable.mDataType = DataType.sBOOLEAN;
        leftVariable.mValue = -1.0f;
      } // else
    } // if
    else if ( command.mOperand.equals( "<>" ) ) {
      if ( leftVariable.GetVlue() - rightVariable.GetVlue() > 0.0001
          || leftVariable.GetVlue() - rightVariable.GetVlue() < -0.0001 ) {
        leftVariable.mDataType = DataType.sBOOLEAN;
        leftVariable.mValue = 1.0f;
      } // if
      else {
        leftVariable.mDataType = DataType.sBOOLEAN;
        leftVariable.mValue = -1.0f;
      } // else
      
    } // else if
    else if ( command.mOperand.equals( ">" ) ) {
      if ( leftVariable.GetVlue() - rightVariable.GetVlue() > 0.0001 ) {
        leftVariable.mDataType = DataType.sBOOLEAN;
        leftVariable.mValue = 1.0f;
      } // if
      else {
        leftVariable.mDataType = DataType.sBOOLEAN;
        leftVariable.mValue = -1.0f;
      } // else
      
    } // else if
    else if ( command.mOperand.equals( "<" ) ) {
      if ( leftVariable.GetVlue() - rightVariable.GetVlue() < -0.0001 ) {
        leftVariable.mDataType = DataType.sBOOLEAN;
        leftVariable.mValue = 1.0f;
      } // if
      else {
        leftVariable.mDataType = DataType.sBOOLEAN;
        leftVariable.mValue = -1.0f;
      } // else
      
    } // else if
    else if ( command.mOperand.equals( ">=" ) ) {
      if ( leftVariable.GetVlue() - rightVariable.GetVlue() >= -0.0001 ) {
        leftVariable.mDataType = DataType.sBOOLEAN;
        leftVariable.mValue = 1.0f;
      } // if
      else {
        leftVariable.mDataType = DataType.sBOOLEAN;
        leftVariable.mValue = -1.0f;
      } // else
    } // else if
    else if ( command.mOperand.equals( "<=" ) ) {
      if ( leftVariable.GetVlue() - rightVariable.GetVlue() <= 0.0001 ) {
        leftVariable.mDataType = DataType.sBOOLEAN;
        leftVariable.mValue = 1.0f;
      } // if
      else {
        leftVariable.mDataType = DataType.sBOOLEAN;
        leftVariable.mValue = -1.0f;
      } // else
    } // else if
    
    // 存回stack
    mLocalVariables.push( leftVariable );
    
  } // BooleanOperation()
  
  public void InitCPU() {
    mLocalVariables = new Stack<Variable>();
    mCommands = new Vector<Command>();
    mMode = false;
  } // InitCPU()
  
} // class MyCPU

class Register {
  public static Register sRegister = new Register();
  
  Vector<Variable> mIdentTable;
  
  private Register() {
    mIdentTable = new Vector<Variable>();
  } // Register()
  
  public void Assign( Variable variable ) {
    for ( int i = 0 ; i < mIdentTable.size() ; i++ ) {
      if ( mIdentTable.elementAt( i ).mLiteralString.equals( variable.mLiteralString ) ) {
        
        mIdentTable.elementAt( i ).mValue = variable.mValue;
        mIdentTable.elementAt( i ).mDataType = variable.mDataType;
        return;
      } // if
    } // for
    
    mIdentTable.add( variable );
  } // Assign()
  
  public Variable Get( String literalString ) throws Throwable {
    
    for ( int i = 0 ; i < mIdentTable.size() ; i++ ) {
      if ( mIdentTable.elementAt( i ).mLiteralString.equals( literalString ) ) {
        return mIdentTable.elementAt( i );
      } // if
    } // for
    
    // undefined identity
    throw new SegmenticErrorException( literalString );
  } // Get()
  
  public boolean Is_Defined( String literalString ) throws Throwable {
    
    for ( int i = 0 ; i < mIdentTable.size() ; i++ ) {
      if ( mIdentTable.elementAt( i ).mLiteralString.equals( literalString ) ) {
        return true;
      } // if
    } // for
    
    // undefined identity
    return false;
  } // Is_Defined()
  
} // class Register

// 此class由於設計問題 一定要GetVlaue()後才會設定mSymbol
class Variable {
  String mLiteralString;
  Float mValue;
  Terminal_symbol mSymbol;
  DataType mDataType;
  
  public Variable( String literalString, Terminal_symbol symbol ) {
    mLiteralString = literalString;
    mSymbol = symbol;
    // 型別檢查與定植
    
  } // Variable()
  
  public void TypeCheck() {
    for ( int i = 0 ; i < mLiteralString.length() ; i++ ) {
      if ( mLiteralString.charAt( i ) == '.' ) {
        mDataType = DataType.sFLOAT;
        return;
      } // if
    } // for
    
    mDataType = DataType.sINT;
  } // TypeCheck()
  
  // 變數定值並設定型別, 若已定值則當作暫存的已運算過變數
  public float GetVlue() throws Throwable {
    if ( mSymbol == Terminal_symbol.sNUM ) {
      if ( mValue == null ) {
        TypeCheck();
        mValue = Float.parseFloat( mLiteralString );
        return mValue.floatValue();
      } // if
      else {
        return mValue.floatValue();
      } // else
    } // if
    else if ( mSymbol == Terminal_symbol.sIDENT ) {
      if ( mValue == null ) {
        // if 變數尚未定值
        Variable tempVariable = Register.sRegister.Get( mLiteralString );
        mValue = tempVariable.mValue;
        mDataType = tempVariable.mDataType;
      } // if
      
      return mValue;
    } // else if
    
    throw new SegmenticErrorException();
    
  } // GetVlue()
  
} // class Variable
