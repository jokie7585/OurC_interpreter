package interpreter;

import java.util.Vector;

import CYICE.ICEInputStream;

public class New_MyScanner {
  private static New_MyScanner sSingleTone_MyScanner;
  private ICEInputStream mStdIn;
  private Vector<Token> mStream = new Vector<Token>();
  private int counter;
  
  private New_MyScanner() throws Throwable {
    mStdIn = new ICEInputStream();
    mStream = new Vector<Token>();
    counter = 0;
    InitPAL();
    
  } // New_MyScanner()
  
  public boolean HasToken() throws Throwable {
    if ( !mStdIn.AtEOF() ) {
      return true;
    } // if
    
    return false;
  } // HasToken()
  
  public String Get_NextToken() throws Throwable {
    // if token stream is empty find a nonEmpty new line
    while ( counter >= mStream.size() ) {
      GetInputFromeStream();
    } // if
    
    return mStream.elementAt( counter++ ).Get();
  } // Get_NextToken()
  
  public Token Peek_NextToken() throws Throwable {
    while ( counter >= mStream.size() ) {
      GetInputFromeStream();
    } // if
    
    return mStream.elementAt( counter );
  } // Peek_NextToken()
  
  public static New_MyScanner GetMyScanner() throws Throwable {
    if ( sSingleTone_MyScanner == null ) {
      sSingleTone_MyScanner = new New_MyScanner();
    } // if
    
    return sSingleTone_MyScanner;
  } // GetMyScanner()
  
  public void ErrorInite() throws Throwable {
    mStream = new Vector<Token>();
    // init scanner token Stream
    GetInputFromeStream();
  } // ErrorInite()
  
  private void InitPAL() throws Throwable {
    // inite uTestNum
    Main.suTestNum = mStdIn.ReadInt();
    // 讀掉一換行
    mStdIn.ReadChar();
    // inite scanner
    GetInputFromeStream();
    
  } // InitPAL()
  
  private void GetInputFromeStream() throws Throwable
  
  {
    if ( !mStdIn.AtEOF() ) {
      // init counter
      counter = 0;
      mStream = new Vector<Token>();
      // read a line
      StringProcessor stringProcessor = new StringProcessor( mStdIn.ReadInputLine() );
      // process it
      while ( stringProcessor.HasToken() ) {
        mStream.add( new Token( stringProcessor.GetNextToken() ) );
      } // while
      
    } // if
    else {
      throw new ProgramQuitException();
    } // else
  } // GetCharFromStream()
  
}
