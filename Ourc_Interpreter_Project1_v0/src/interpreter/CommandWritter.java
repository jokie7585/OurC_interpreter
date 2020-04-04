package interpreter;

import java.util.Vector;

public class CommandWritter {
  Vector<Command> mCommandStorage = new Vector<Command>();
  
  public void Write( String operand, Terminal_symbol symbol ) {
    mCommandStorage.add( new Command( operand, symbol ) );
  } // Write()
  
  // get commandSroage
  public Vector<Command> Get() {
    return mCommandStorage;
  } // Get()
  
} // class CommandWritter

// Command can run in MyCPU and is pushed by Command_Rnner from Instruction
class Command {
  Commnad_Type mCommnad_Type;
  String mOperand;
  Terminal_symbol mSymbolOfOperand;
  
  public Command( String operand, Terminal_symbol symbol ) {
    if ( operand.equals( "+" ) ) {
      mCommnad_Type = Commnad_Type.sADD;
      mSymbolOfOperand = symbol;
    } // if
    else if ( operand.equals( "-" ) ) {
      mCommnad_Type = Commnad_Type.sSUB;
      mSymbolOfOperand = symbol;
    } // else if
    else if ( operand.equals( "*" ) ) {
      mCommnad_Type = Commnad_Type.sMULT;
      mSymbolOfOperand = symbol;
    } // else if
    else if ( operand.equals( "/" ) ) {
      mCommnad_Type = Commnad_Type.sDIV;
      mSymbolOfOperand = symbol;
    } // else if
    else if ( operand.equals( "=" ) || operand.equals( ">" ) || operand.equals( "<" )
        || operand.equals( ">=" ) || operand.equals( "<=" ) || operand.equals( "<>" ) ) {
      
      mCommnad_Type = Commnad_Type.sBOOLEANOPERATION;
      mOperand = operand;
      mSymbolOfOperand = symbol;
    } // else if
    else if ( operand.equals( ":=" ) ) {
      mCommnad_Type = Commnad_Type.sASSIGN;
      mOperand = operand;
      mSymbolOfOperand = symbol;
    } // else if
    else {
      mCommnad_Type = Commnad_Type.sPUSH;
      mOperand = operand;
      mSymbolOfOperand = symbol;
    } // else
    
  } // Command()
  
  public String TO_String() {
    return mCommnad_Type.ToString() + " " + mOperand + " " + mSymbolOfOperand.TOString();
  } // TO_String()
  
} // class Command

// this class work as ENUM
class Commnad_Type {
  String mTypeString;
  
  public Commnad_Type( String typString ) {
    this.mTypeString = typString;
  } // Commnad_Type()
  
  public static final Commnad_Type sPUSH = new Commnad_Type( "push" );
  public static final Commnad_Type sMULT = new Commnad_Type( "mult" );
  public static final Commnad_Type sADD = new Commnad_Type( "add" );
  public static final Commnad_Type sSUB = new Commnad_Type( "sub" );
  public static final Commnad_Type sDIV = new Commnad_Type( "div" );
  public static final Commnad_Type sASSIGN = new Commnad_Type( "assigh" );
  public static final Commnad_Type sBOOLEANOPERATION = new Commnad_Type( "booleanOperation" );
  
  public String ToString() {
    return mTypeString;
  } // ToString()
  
} // class Commnad_Type
