package interpreter;

public class MyParser {
  private static MyParser sSingleTone_MyParser = new MyParser();
  private MyScanner mMyScanner;
  private MyRuntime mMyRuntime;
  
  private MyParser() {
    mMyScanner = MyScanner.GetMyScanner();
    mMyRuntime = MyRuntime.GetMyRuntime();
  } // MyParser()
  
  public static MyParser GetMyParser() {
    return sSingleTone_MyParser;
  } // GetMyParser()
  
  // parser get a instruction from tokenStream(MyScanner)
  public void RunNextInstruction() throws Throwable {
    CommandWritter writter = new CommandWritter();
    Command( writter );
  } // GetNextInstruction()
  
  private void Command( CommandWritter writter ) throws Throwable {
    
    // writter is no longer write command
    
    if ( mMyScanner.Peek_NextToken().SymbolOf() == Terminal_symbol.sIDENT ) {
      if ( mMyScanner.Peek_NextToken().Get().equals( "quit" ) ) {
        throw new ProgramQuitException();
      } // if
      
      // 緩存 用以檢查
      String tempString = mMyScanner.Get_NextToken();
      mMyRuntime.RunACommand( new Command( tempString, Terminal_symbol.sIDENT ) );
      
      if ( mMyScanner.Peek_NextToken().Get().equals( ":=" ) ) {
        String operatorString = mMyScanner.Get_NextToken();
        
        if ( ArithExp( writter ) ) {
          if ( mMyScanner.Peek_NextToken().Get().equals( ";" ) ) {
            mMyScanner.Get_NextToken();
            mMyRuntime.RunACommand( new Command( operatorString, Terminal_symbol.sDELIMITER ) );
            mMyRuntime.PrintTopOfStack();
            
          } // if
          else {
            throw new SyntxErrorException( mMyScanner.Get_NextToken() );
          } // else
          
        } // if
        else {
          throw new SyntxErrorException( mMyScanner.Get_NextToken() );
        } // else
        
      } // if
      else {
        if ( Register.sRegister.Is_Defined( tempString ) ) {
          IDlessArithExpOrBexp( writter );
          if ( mMyScanner.Peek_NextToken().Get().equals( ";" ) ) {
            mMyScanner.Get_NextToken();
            mMyRuntime.PrintTopOfStack();
            
          } // if
          else {
            throw new SyntxErrorException( mMyScanner.Get_NextToken() );
          } // else
        } // if
        else {
          throw new SegmenticErrorException( tempString );
        } // else
        
      } // else
      
    } // if
    else if ( NOT_ID_StartArithExpOrBexp( writter ) ) {
      
      if ( mMyScanner.Peek_NextToken().Get().equals( ";" ) ) {
        mMyScanner.Get_NextToken();
        mMyRuntime.PrintTopOfStack();
        
      } // if
      else {
        throw new SyntxErrorException( mMyScanner.Get_NextToken() );
      } // else
      
    } // else if
    else {
      throw new SyntxErrorException( mMyScanner.Get_NextToken() );
    } // else
    
  } // Command()
  
  private boolean IDlessArithExpOrBexp( CommandWritter writter ) throws Throwable {
    while ( mMyScanner.Peek_NextToken().Get().equals( "+" ) || mMyScanner.Peek_NextToken().Get().equals( "-" )
        || mMyScanner.Peek_NextToken().Get().equals( "*" )
        || mMyScanner.Peek_NextToken().Get().equals( "/" ) ) {
      
      String operatorString = mMyScanner.Get_NextToken();
      if ( mMyScanner.Peek_NextToken().Get().equals( "+" )
          || mMyScanner.Peek_NextToken().Get().equals( "-" ) ) {
        if ( Term( writter ) ) {
          mMyRuntime.RunACommand( new Command( operatorString, Terminal_symbol.sDELIMITER ) );
          
        } // if
      } // if
      else {
        if ( Factor( writter ) ) {
          mMyRuntime.RunACommand( new Command( operatorString, Terminal_symbol.sDELIMITER ) );
          
        } // if
      } // else
    } // while
    
    StringBuffer booleanOperator = new StringBuffer();
    // match [<BooleanOperator> <ArithExp>]
    if ( BooleanOperator( booleanOperator ) ) {
      if ( ArithExp( writter ) ) {
        mMyRuntime.RunACommand( new Command( booleanOperator.toString(), Terminal_symbol.sDELIMITER ) );
        
      } // if
      else {
        throw new SyntxErrorException( mMyScanner.Get_NextToken() );
      } // else
    } // if
    
    // always true, except error occurs
    return true;
  } // IDlessArithExpOrBexp()
  
  private boolean NOT_ID_StartArithExpOrBexp( CommandWritter writter ) throws Throwable {
    if ( NOT_ID_StartArithExp( writter ) ) {
      
      StringBuffer booleanOperator = new StringBuffer();
      // match [<BooleanOperator> <ArithExp>]
      if ( BooleanOperator( booleanOperator ) ) {
        if ( ArithExp( writter ) ) {
          mMyRuntime.RunACommand( new Command( booleanOperator.toString(), Terminal_symbol.sDELIMITER ) );
          // below is abandon
          
        } // if
        else {
          throw new SyntxErrorException( mMyScanner.Get_NextToken() );
        } // else
      } // if
      
      return true;
    } // if
    
    return false;
  } // NOT_ID_StartArithExpOrBexp()
  
  private boolean BooleanOperator( StringBuffer booleanOperator ) throws Throwable {
    if ( mMyScanner.Peek_NextToken().Get().equals( "=" ) || mMyScanner.Peek_NextToken().Get().equals( ">" )
        || mMyScanner.Peek_NextToken().Get().equals( ">=" )
        || mMyScanner.Peek_NextToken().Get().equals( "<>" )
        || mMyScanner.Peek_NextToken().Get().equals( "<=" )
        || mMyScanner.Peek_NextToken().Get().equals( "<" ) ) {
      
      booleanOperator.append( mMyScanner.Get_NextToken() );
      return true;
    } // if
    
    return false;
  } // BooleanOperator()
  
  private boolean NOT_ID_StartArithExp( CommandWritter writter ) throws Throwable {
    
    if ( NOT_ID_StartTerm( writter ) ) {
      
      // match {'+'<Term> | '-'<Term>}
      while ( mMyScanner.Peek_NextToken().Get().equals( "+" )
          || mMyScanner.Peek_NextToken().Get().equals( "-" ) ) {
        // <Term>
        String operatorString = mMyScanner.Get_NextToken();
        if ( Term( writter ) ) {
          
          mMyRuntime.RunACommand( new Command( operatorString, Terminal_symbol.sDELIMITER ) );
        } // if
        
      } // while
      
      return true;
    } // if
    
    return false;
    
  } // NOT_ID_StartArithExp()
  
  private boolean NOT_ID_StartTerm( CommandWritter writter ) throws Throwable {
    if ( NOT_ID_StartFactor( writter ) ) {
      while ( mMyScanner.Peek_NextToken().Get().equals( "*" )
          || mMyScanner.Peek_NextToken().Get().equals( "/" ) ) {
        // match { '*' Factor | '/' Factor }
        String operatorString = mMyScanner.Get_NextToken();
        if ( Factor( writter ) ) {
          mMyRuntime.RunACommand( new Command( operatorString, Terminal_symbol.sDELIMITER ) );
          
        } // if
        
      } // while
      
      return true;
    } // if
    
    return false;
    
  } // NOT_ID_StartTerm()
  
  private boolean NOT_ID_StartFactor( CommandWritter writter ) throws Throwable {
    if ( Sign() || mMyScanner.Peek_NextToken().SymbolOf() == Terminal_symbol.sNUM ) {
      // match [sign]<NUM>
      String operandString = "";
      if ( Sign() ) {
        operandString = operandString + mMyScanner.Get_NextToken();
      } // if
      
      if ( mMyScanner.Peek_NextToken().SymbolOf() == Terminal_symbol.sNUM ) {
        operandString = operandString + mMyScanner.Get_NextToken();
        // push NUM
        mMyRuntime.RunACommand( new Command( operandString, Terminal_symbol.sNUM ) );
        
      } // if
      else {
        throw new SyntxErrorException( mMyScanner.Get_NextToken() );
      } // else
      
      return true;
    } // if
    else if ( mMyScanner.Peek_NextToken().Get().equals( "(" ) ) {
      
      // read in and abandon Left parenthesis
      mMyScanner.Get_NextToken();
      if ( ArithExp( writter ) ) {
        if ( mMyScanner.Peek_NextToken().Get().equals( ")" ) ) {
          
          // read in and abandon Right parenthesis
          mMyScanner.Get_NextToken();
          return true;
        } // if
        else {
          throw new SyntxErrorException( mMyScanner.Get_NextToken() );
        } // else
      } // if
      
    } // else if
    
    return false;
  } // NOT_ID_StartFactor()
  
  private boolean ArithExp( CommandWritter writter ) throws Throwable {
    if ( Term( writter ) ) {
      while ( mMyScanner.Peek_NextToken().Get().equals( "+" )
          || mMyScanner.Peek_NextToken().Get().equals( "-" ) ) {
        // match { '+' Term | '-' Term }
        String operatorString = mMyScanner.Get_NextToken();
        if ( Term( writter ) ) {
          mMyRuntime.RunACommand( new Command( operatorString, Terminal_symbol.sDELIMITER ) );
          
        } // if
        
      } // while
      
      return true;
    } // if
    
    return false;
  } // ArithExp()
  
  private boolean Term( CommandWritter writter ) throws Throwable {
    if ( Factor( writter ) ) {
      while ( mMyScanner.Peek_NextToken().Get().equals( "*" )
          || mMyScanner.Peek_NextToken().Get().equals( "/" ) ) {
        // match { '*' Factor | '/' Factor }
        String operatorString = mMyScanner.Get_NextToken();
        if ( Factor( writter ) ) {
          mMyRuntime.RunACommand( new Command( operatorString, Terminal_symbol.sDELIMITER ) );
          
        } // if
        
      } // while
      
      return true;
    } // if
    
    return false;
  } // Term()
  
  private boolean Factor( CommandWritter writter ) throws Throwable {
    if ( mMyScanner.Peek_NextToken().SymbolOf() == Terminal_symbol.sIDENT ) {
      if ( !Register.sRegister.Is_Defined( mMyScanner.Peek_NextToken().Get() ) ) {
        throw new SegmenticErrorException( mMyScanner.Peek_NextToken().Get() );
      } // if
      
      mMyRuntime.RunACommand( new Command( mMyScanner.Get_NextToken(), Terminal_symbol.sIDENT ) );
      // writter.Write( mMyScanner.Get_NextToken(), Terminal_symbol.sIDENT );
      return true;
    } // if
    else if ( Sign() || mMyScanner.Peek_NextToken().SymbolOf() == Terminal_symbol.sNUM ) {
      // match [sign]<NUM>
      String operandString = "";
      if ( Sign() ) {
        operandString = operandString + mMyScanner.Get_NextToken();
      } // if
      
      if ( mMyScanner.Peek_NextToken().SymbolOf() == Terminal_symbol.sNUM ) {
        operandString = operandString + mMyScanner.Get_NextToken();
        // push NUM
        
        mMyRuntime.RunACommand( new Command( operandString, Terminal_symbol.sNUM ) );
        
      } // if
      else {
        throw new SyntxErrorException( mMyScanner.Get_NextToken() );
      } // else
      
      return true;
    } // else if
    else if ( mMyScanner.Peek_NextToken().Get().equals( "(" ) ) {
      // read in and abandon Left parenthesis
      mMyScanner.Get_NextToken();
      
      if ( ArithExp( writter ) ) {
        if ( mMyScanner.Peek_NextToken().Get().equals( ")" ) ) {
          // read in and abandon Right parenthesis
          mMyScanner.Get_NextToken();
          
          return true;
        } // if
        else {
          throw new SyntxErrorException( mMyScanner.Get_NextToken() );
        } // else
      } // if
    } // else if
    else {
      throw new SyntxErrorException( mMyScanner.Get_NextToken() );
    } // else
    
    return false;
  } // Factor()
  
  private boolean Sign() throws Throwable {
    if ( mMyScanner.Peek_NextToken().Get().equals( "+" )
        || mMyScanner.Peek_NextToken().Get().equals( "-" ) ) {
      return true;
    } // if
    
    return false;
  } // Sign()
  
} // class MyParser