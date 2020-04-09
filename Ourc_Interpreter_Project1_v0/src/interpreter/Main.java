package interpreter;

public class Main {
  public static int suTestNum;
  
  public static void main( String[] args ) throws Throwable {
    // 讀入uTestNum
    // User_interface.InitPAL();
    // 測試testNum
    // System.out.println( "uTestNum is :" + uTestNum );
    // 初始化tokenStream
    MyScanner myScanner = MyScanner.GetMyScanner();
    // 初始化parser
    MyParser myParser = MyParser.GetMyParser();
    // 初始化 runtime
    Command_Runner command_Runner = Command_Runner.GetCommand_Runner();
    // 讀入使用者輸入
    myScanner.GetInputFromStream();
    // 印出所有token Stream
    // myScanner.PrintAll();
    
    User_interface.Generate_ProgramStart();
    
    try {
      while ( myScanner.HasToken() ) {
        try {
          Instruction instruction = myParser.GetNextInstruction();
          // instruction.PrintAll();
          // System.out.println( "start computing" );
          command_Runner.Run( instruction );
          // System.out.println( "finish computing" );
          
        } // try
        catch ( LexicalErrorException e ) {
          User_interface.PrintResult( e.ToString() );
          myScanner.SkipLine();
          command_Runner.Init();
          
        } // catch
        catch ( SyntxErrorException e ) {
          User_interface.PrintResult( e.ToString() );
          myScanner.SkipLine();
          command_Runner.Init();
        } // catch
        catch ( SegmenticErrorException e ) {
          User_interface.PrintResult( e.ToString() );
          myScanner.SkipLine();
          command_Runner.Init();
        } // catch
        
      } // while
    } // try
    catch ( EndOfInputException e ) {
      User_interface.PrintResult( e.ToString() );
    } // catch
    catch ( ProgramQuitException e ) {
      System.out.println( "> Program exits..." );
    } // catch
  } // main()
  
} // class Main
