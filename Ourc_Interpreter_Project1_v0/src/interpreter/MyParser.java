package interpreter;

public class MyParser {
  private static MyParser sSingleTone_MyParser;
  private MyScanner mNew_MyScanner;
  private MyRuntime mMyRuntime;
  
  private MyParser() throws Throwable {
    mNew_MyScanner = MyScanner.GetMyScanner();
    mMyRuntime = MyRuntime.GetMyRuntime();
  } // MyParser()
  
  public static MyParser GetMyParser() throws Throwable {
    if ( sSingleTone_MyParser == null ) {
      sSingleTone_MyParser = new MyParser();
    } // if
    
    return sSingleTone_MyParser;
  } // GetMyParser()
  
  // parser get a instruction from tokenStream(New_MyScanner)
  
  public void RunNextInstruction() throws Throwable {
    
    // writter is no longer write command
    
    if ( mNew_MyScanner.Peek_NextToken().SymbolOf() == Terminal_symbol.sIDENT ) {
      if ( mNew_MyScanner.Peek_NextToken().Get().equals( "quit" ) ) {
        throw new ProgramQuitException();
      } // if
      
      // 緩存 用以檢查
      String tempString = mNew_MyScanner.Get_NextToken();
      mMyRuntime.RunACommand( new Command( tempString, Terminal_symbol.sIDENT ) );
      
      if ( mNew_MyScanner.Peek_NextToken().Get().equals( ":=" ) ) {
        String operatorString = mNew_MyScanner.Get_NextToken();
        
        if ( ArithExp() ) {
          if ( mNew_MyScanner.Peek_NextToken().Get().equals( ";" ) ) {
            mMyRuntime.RunACommand( new Command( operatorString, Terminal_symbol.sDELIMITER ) );
            mMyRuntime.PrintTopOfStack();
            mNew_MyScanner.Get_NextToken();
            
          } // if
          else {
            throw new SyntxErrorException( mNew_MyScanner.Peek_NextToken().Get() );
          } // else
          
        } // if
        else {
          throw new SyntxErrorException( mNew_MyScanner.Peek_NextToken().Get() );
        } // else
        
      } // if
      else if ( mNew_MyScanner.Peek_NextToken().Get().equals( "=" )
          || mNew_MyScanner.Peek_NextToken().Get().equals( ">=" )
          || mNew_MyScanner.Peek_NextToken().Get().equals( "<=" )
          || mNew_MyScanner.Peek_NextToken().Get().equals( ">" )
          || mNew_MyScanner.Peek_NextToken().Get().equals( "<" )
          || mNew_MyScanner.Peek_NextToken().Get().equals( "<>" )
          || mNew_MyScanner.Peek_NextToken().Get().equals( "+" )
          || mNew_MyScanner.Peek_NextToken().Get().equals( "-" )
          || mNew_MyScanner.Peek_NextToken().Get().equals( "*" )
          || mNew_MyScanner.Peek_NextToken().Get().equals( "/" )
          || mNew_MyScanner.Peek_NextToken().Get().equals( ";" ) ) {
        
        if ( Register.sRegister.Is_Defined( tempString ) ) {
          IDlessArithExpOrBexp();
          if ( mNew_MyScanner.Peek_NextToken().Get().equals( ";" ) ) {
            mMyRuntime.PrintTopOfStack();
            mNew_MyScanner.Get_NextToken();
            
          } // if
          else {
            throw new SyntxErrorException( mNew_MyScanner.Peek_NextToken().Get() );
          } // else
        } // if
        else {
          throw new SegmenticErrorException( tempString );
        } // else
        
      } // else if
      else {
        throw new SyntxErrorException( mNew_MyScanner.Peek_NextToken().Get() );
      } // else
      
    } // if
    else if ( NOT_ID_StartArithExpOrBexp() ) {
      
      if ( mNew_MyScanner.Peek_NextToken().Get().equals( ";" ) ) {
        mMyRuntime.PrintTopOfStack();
        mNew_MyScanner.Get_NextToken();
        
      } // if
      else {
        throw new SyntxErrorException( mNew_MyScanner.Peek_NextToken().Get() );
      } // else
      
    } // else if
    else {
      throw new SyntxErrorException( mNew_MyScanner.Peek_NextToken().Get() );
    } // else
    
  } // RunNextInstruction()
  
  private boolean IDlessArithExpOrBexp() throws Throwable {
    while ( mNew_MyScanner.Peek_NextToken().Get().equals( "+" )
        || mNew_MyScanner.Peek_NextToken().Get().equals( "-" )
        || mNew_MyScanner.Peek_NextToken().Get().equals( "*" )
        || mNew_MyScanner.Peek_NextToken().Get().equals( "/" ) ) {
      
      String operatorString = mNew_MyScanner.Get_NextToken();
      if ( mNew_MyScanner.Peek_NextToken().Get().equals( "+" )
          || mNew_MyScanner.Peek_NextToken().Get().equals( "-" ) ) {
        if ( Term() ) {
          mMyRuntime.RunACommand( new Command( operatorString, Terminal_symbol.sDELIMITER ) );
          
        } // if
      } // if
      else {
        if ( Factor() ) {
          mMyRuntime.RunACommand( new Command( operatorString, Terminal_symbol.sDELIMITER ) );
          
        } // if
      } // else
    } // while
    
    StringBuffer booleanOperator = new StringBuffer();
    // match [<BooleanOperator> <ArithExp>]
    if ( BooleanOperator( booleanOperator ) ) {
      if ( ArithExp() ) {
        mMyRuntime.RunACommand( new Command( booleanOperator.toString(), Terminal_symbol.sDELIMITER ) );
        
      } // if
      else {
        throw new SyntxErrorException( mNew_MyScanner.Peek_NextToken().Get() );
      } // else
    } // if
    
    // always true, except error occurs
    return true;
  } // IDlessArithExpOrBexp()
  
  private boolean NOT_ID_StartArithExpOrBexp() throws Throwable {
    if ( NOT_ID_StartArithExp() ) {
      
      StringBuffer booleanOperator = new StringBuffer();
      // match [<BooleanOperator> <ArithExp>]
      if ( BooleanOperator( booleanOperator ) ) {
        if ( ArithExp() ) {
          mMyRuntime.RunACommand( new Command( booleanOperator.toString(), Terminal_symbol.sDELIMITER ) );
          // below is abandon
          
        } // if
        else {
          throw new SyntxErrorException( mNew_MyScanner.Peek_NextToken().Get() );
        } // else
      } // if
      
      return true;
    } // if
    
    return false;
  } // NOT_ID_StartArithExpOrBexp()
  
  private boolean BooleanOperator( StringBuffer booleanOperator ) throws Throwable {
    if ( mNew_MyScanner.Peek_NextToken().Get().equals( "=" )
        || mNew_MyScanner.Peek_NextToken().Get().equals( ">" )
        || mNew_MyScanner.Peek_NextToken().Get().equals( ">=" )
        || mNew_MyScanner.Peek_NextToken().Get().equals( "<>" )
        || mNew_MyScanner.Peek_NextToken().Get().equals( "<=" )
        || mNew_MyScanner.Peek_NextToken().Get().equals( "<" ) ) {
      
      booleanOperator.append( mNew_MyScanner.Get_NextToken() );
      return true;
    } // if
    
    return false;
  } // BooleanOperator()
  
  private boolean NOT_ID_StartArithExp() throws Throwable {
    
    if ( NOT_ID_StartTerm() ) {
      
      // match {'+'<Term> | '-'<Term>}
      while ( mNew_MyScanner.Peek_NextToken().Get().equals( "+" )
          || mNew_MyScanner.Peek_NextToken().Get().equals( "-" ) ) {
        // <Term>
        String operatorString = mNew_MyScanner.Get_NextToken();
        if ( Term() ) {
          
          mMyRuntime.RunACommand( new Command( operatorString, Terminal_symbol.sDELIMITER ) );
        } // if
        
      } // while
      
      return true;
    } // if
    
    return false;
    
  } // NOT_ID_StartArithExp()
  
  private boolean NOT_ID_StartTerm() throws Throwable {
    if ( NOT_ID_StartFactor() ) {
      while ( mNew_MyScanner.Peek_NextToken().Get().equals( "*" )
          || mNew_MyScanner.Peek_NextToken().Get().equals( "/" ) ) {
        // match { '*' Factor | '/' Factor }
        String operatorString = mNew_MyScanner.Get_NextToken();
        if ( Factor() ) {
          mMyRuntime.RunACommand( new Command( operatorString, Terminal_symbol.sDELIMITER ) );
          
        } // if
        
      } // while
      
      return true;
    } // if
    
    return false;
    
  } // NOT_ID_StartTerm()
  
  private boolean NOT_ID_StartFactor() throws Throwable {
    if ( Sign() || mNew_MyScanner.Peek_NextToken().SymbolOf() == Terminal_symbol.sNUM ) {
      // match [sign]<NUM>
      String operandString = "";
      if ( Sign() ) {
        operandString = operandString + mNew_MyScanner.Get_NextToken();
      } // if
      
      if ( mNew_MyScanner.Peek_NextToken().SymbolOf() == Terminal_symbol.sNUM ) {
        operandString = operandString + mNew_MyScanner.Get_NextToken();
        // push NUM
        mMyRuntime.RunACommand( new Command( operandString, Terminal_symbol.sNUM ) );
        
      } // if
      else {
        throw new SyntxErrorException( mNew_MyScanner.Peek_NextToken().Get() );
      } // else
      
      return true;
    } // if
    else if ( mNew_MyScanner.Peek_NextToken().Get().equals( "(" ) ) {
      
      // read in and abandon Left parenthesis
      mMyRuntime.RunACommand( new Command( mNew_MyScanner.Get_NextToken(), Terminal_symbol.sDELIMITER ) );
      
      if ( ArithExp() ) {
        if ( mNew_MyScanner.Peek_NextToken().Get().equals( ")" ) ) {
          
          // read in and abandon Right parenthesis
          mMyRuntime.RunACommand( new Command( mNew_MyScanner.Get_NextToken(), Terminal_symbol.sDELIMITER ) );
          return true;
        } // if
        else {
          throw new SyntxErrorException( mNew_MyScanner.Peek_NextToken().Get() );
        } // else
      } // if
      
    } // else if
    else {
      throw new SyntxErrorException( mNew_MyScanner.Get_NextToken() );
    } // else
    
    return false;
  } // NOT_ID_StartFactor()
  
  private boolean ArithExp() throws Throwable {
    if ( Term() ) {
      while ( mNew_MyScanner.Peek_NextToken().Get().equals( "+" )
          || mNew_MyScanner.Peek_NextToken().Get().equals( "-" ) ) {
        // match { '+' Term | '-' Term }
        String operatorString = mNew_MyScanner.Get_NextToken();
        if ( Term() ) {
          mMyRuntime.RunACommand( new Command( operatorString, Terminal_symbol.sDELIMITER ) );
          
        } // if
        
      } // while
      
      return true;
    } // if
    
    return false;
  } // ArithExp()
  
  private boolean Term() throws Throwable {
    if ( Factor() ) {
      while ( mNew_MyScanner.Peek_NextToken().Get().equals( "*" )
          || mNew_MyScanner.Peek_NextToken().Get().equals( "/" ) ) {
        // match { '*' Factor | '/' Factor }
        String operatorString = mNew_MyScanner.Get_NextToken();
        if ( Factor() ) {
          mMyRuntime.RunACommand( new Command( operatorString, Terminal_symbol.sDELIMITER ) );
          
        } // if
        
      } // while
      
      return true;
    } // if
    
    return false;
  } // Term()
  
  private boolean Factor() throws Throwable {
    if ( mNew_MyScanner.Peek_NextToken().SymbolOf() == Terminal_symbol.sIDENT ) {
      if ( !Register.sRegister.Is_Defined( mNew_MyScanner.Peek_NextToken().Get() ) ) {
        throw new SegmenticErrorException( mNew_MyScanner.Peek_NextToken().Get() );
      } // if
      
      mMyRuntime.RunACommand( new Command( mNew_MyScanner.Get_NextToken(), Terminal_symbol.sIDENT ) );
      // writter.Write( mNew_MyScanner.Get_NextToken(), Terminal_symbol.sIDENT
      // );
      return true;
    } // if
    else if ( Sign() || mNew_MyScanner.Peek_NextToken().SymbolOf() == Terminal_symbol.sNUM ) {
      // match [sign]<NUM>
      String operandString = "";
      if ( Sign() ) {
        operandString = operandString + mNew_MyScanner.Get_NextToken();
      } // if
      
      if ( mNew_MyScanner.Peek_NextToken().SymbolOf() == Terminal_symbol.sNUM ) {
        operandString = operandString + mNew_MyScanner.Get_NextToken();
        // push NUM
        
        mMyRuntime.RunACommand( new Command( operandString, Terminal_symbol.sNUM ) );
        
      } // if
      else {
        throw new SyntxErrorException( mNew_MyScanner.Peek_NextToken().Get() );
      } // else
      
      return true;
    } // else if
    else if ( mNew_MyScanner.Peek_NextToken().Get().equals( "(" ) ) {
      // read in and abandon Left parenthesis
      
      mMyRuntime.RunACommand( new Command( mNew_MyScanner.Get_NextToken(), Terminal_symbol.sDELIMITER ) );
      
      if ( ArithExp() ) {
        if ( mNew_MyScanner.Peek_NextToken().Get().equals( ")" ) ) {
          // read in and abandon Right parenthesis
          mMyRuntime.RunACommand( new Command( mNew_MyScanner.Get_NextToken(), Terminal_symbol.sDELIMITER ) );
          
          return true;
        } // if
        else {
          throw new SyntxErrorException( mNew_MyScanner.Peek_NextToken().Get() );
        } // else
      } // if
    } // else if
    else {
      throw new SyntxErrorException( mNew_MyScanner.Peek_NextToken().Get() );
    } // else
    
    return false;
  } // Factor()
  
  private boolean Sign() throws Throwable {
    if ( mNew_MyScanner.Peek_NextToken().Get().equals( "+" )
        || mNew_MyScanner.Peek_NextToken().Get().equals( "-" ) ) {
      return true;
    } // if
    
    return false;
  } // Sign()
  
} // class MyParser