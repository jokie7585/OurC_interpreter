package interpreter;

// Command_Runner處理的是編譯器層級的指令也就是Instruction
// Command_Runner負責解析Instruction 並準備實際執行時需要的指令集
// 最後把實際執行時所需的指令藉由invoke MyCPU推入functionCallStack中執行
public class Command_Runner {
  private static Command_Runner sSingletone_Command_Runner = new Command_Runner();
  private MyRuntime mMyRuntime = MyRuntime.GetMyRuntime();
  
  private Command_Runner() {
    
  } // Command_Runner()
  
  public static Command_Runner GetCommand_Runner() {
    return sSingletone_Command_Runner;
  } // GetCommand_Runner()
  
  public void Run( Instruction instruction ) throws Throwable {
    if ( instruction.mInstructionType == InstructionType.sCOMPUTING ) {
      Computing( ( Computing ) instruction );
    }
  } // Run
  
  private void Computing( Computing computing ) throws Throwable {
    mMyRuntime.Push( computing.GetAll() );
    mMyRuntime.Run();
  } // Computing
  
  // 初始化MyCPU
  public void Init() {
    mMyRuntime.InitCPU();
  } // Init()
  
} // class Command_Runner
