package interpreter;

public class DataType {
	public static final Terminal_symbol Float;
    public static final Terminal_symbol INT;
    public static final Terminal_symbol String;
    
    static {
    	Float = new Terminal_symbol();
    	INT = new Terminal_symbol();
    	String = new Terminal_symbol();
    }
}
