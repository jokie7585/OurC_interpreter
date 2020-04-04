package interpreter;

import java.util.Vector;

// this class define structure of instruction which runs by Command_Runner with MyCPU
// Instruction cannot run directly should be processed by Command_Runner
// Instruction是編譯器層級的指令 不可被MyCPU直接執行
// 它包含了編譯器層級指令所包含的block condition 等等metadata
public class Instruction {
  Vector<Command> mCommandStorage = new Vector<Command>();
  InstructionType mInstructionType;
  int mCounter = 0;
  
  public Command Get_NextCommand() {
    if ( mCounter < mCommandStorage.size() ) {
      return mCommandStorage.elementAt( mCounter++ );
    } // if
    
    // reset counter
    mCounter = 0;
    return null;
  } // Get_NextCommand()
  
  public Vector<Command> GetAll() {
    return mCommandStorage;
  } // GetAll
  
  public void AddFromCommandWriter( CommandWritter writter ) {
    mCommandStorage.addAll( writter.Get() );
  } // AddFromCommandWriter()
  
  public void Insert( int index, Command command ) {
    mCommandStorage.insertElementAt( command, index );
  } // Insert
  
  public void PrintAll() {
    System.out.println( mInstructionType.TOString() );
    for ( int i = 0 ; i < mCommandStorage.size() ; i++ ) {
      System.out.println( mCommandStorage.elementAt( i ).TO_String() );
    } // for
  } // PrintAll()
  
} // class Instruction

class Computing extends Instruction {
  public Computing() {
    mInstructionType = InstructionType.sCOMPUTING;
  } // Computing()
  
} // class Computing

class InstructionType {
  String mTypeString;
  
  public InstructionType( String type ) {
    mTypeString = "Instruction : " + type;
  }
  
  public static final InstructionType sCOMPUTING = new InstructionType( "computing" );
  
  public String TOString() {
    return mTypeString;
  }
} // class InstructionType
