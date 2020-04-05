package interpreter;

public class EndOfInputException extends Exception {
  
  // 因Myscaaner 採取 nextline 無法處理 EOF 在有效input行出現
  // 因此暫時出此下策
  // 請全面修改MyScanner 用 getChar的方式找一個完整的line
  
  public String ToString() {
    return "Program exits...";
  } // ToString()
} // class EndOfInputException
