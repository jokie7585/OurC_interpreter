package interpreter;

public class Terminal_symbol {
	public static final Terminal_symbol IDENT;
    public static final Terminal_symbol NUM;
    public static final Terminal_symbol Delimiter;
    
    static {
    	IDENT = new Terminal_symbol();
    	NUM = new Terminal_symbol();
    	Delimiter = new Terminal_symbol();
    }
}
