package interpreter;

import java.util.Vector;

import CYICE.ICEInputStream;

public class MyScanner {
  private static MyScanner sSingleTone_MyScanner;
  private ICEInputStream mStdIn;
  private Vector<Token> mStream = new Vector<Token>();
  private int mCounter;
  private boolean mIs_EndOfFile;
  
  private MyScanner() throws Throwable {
    mStdIn = new ICEInputStream();
    mStream = new Vector<Token>();
    mCounter = 0;
    mIs_EndOfFile = false;
    InitPAL();
    
  } // MyScanner()
  
  public boolean HasToken() throws Throwable {
    if ( mCounter < mStream.size() ) {
      return true;
    } // if
    else {
      while ( mCounter >= mStream.size() ) {
        
        GetInputFromeStream();
      } // while
      
      return true;
    } // else
    
  } // HasToken()
  
  public String Get_NextToken() throws Throwable {
    // if token stream is empty find a nonEmpty new line
    while ( mCounter >= mStream.size() ) {
      GetInputFromeStream();
    } // while
    
    return mStream.elementAt( mCounter++ ).Get();
  } // Get_NextToken()
  
  public Token Peek_NextToken() throws Throwable {
    while ( mCounter >= mStream.size() ) {
      GetInputFromeStream();
    } // while
    
    return mStream.elementAt( mCounter );
  } // Peek_NextToken()
  
  public static MyScanner GetMyScanner() throws Throwable {
    if ( sSingleTone_MyScanner == null ) {
      sSingleTone_MyScanner = new MyScanner();
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
    
  } // InitPAL()
  
  private String FindLine() throws Throwable {
    StringBuffer tempBuffer = new StringBuffer();
    while ( !mStdIn.AtEOF() ) {
      
      if ( !mStdIn.AtEOLN() ) {
        
        tempBuffer.append( mStdIn.ReadChar() );
        // System.out.println( tempBuffer.toString() );
        // System.out.println( tempBuffer.length() );
      }
      else {
        
        mStdIn.ReadChar();
        return tempBuffer.toString();
      }
      
    } // while
    
    // EOF found
    if ( mStdIn.AtEOF() ) {
      // System.out.println( "inEOF" );
      mIs_EndOfFile = true;
    } // if
    
    // 讀掉換行
    if ( mStdIn.AtEOLN() && !mIs_EndOfFile ) {
      // System.out.println( "inEOL" );
      mStdIn.ReadChar();
    } // if
    
    return tempBuffer.toString();
  } // FindLine()
  
  private void GetInputFromeStream() throws Throwable
  
  {
    if ( !mIs_EndOfFile ) {
      
      // init counter
      mCounter = 0;
      mStream = new Vector<Token>();
      // read a line
      StringProcessor stringProcessor = new StringProcessor( FindLine() );
      // process it
      while ( stringProcessor.HasToken() ) {
        mStream.add( new Token( stringProcessor.GetNextToken() ) );
      } // while
      
    } // if
    else {
      throw new ProgramQuitException();
    } // else
  } // GetInputFromeStream()
  
} // class MyScanner
