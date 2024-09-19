package fancyconsole.color;

public interface ConsoleColor
{
    public String getTextCode();
    public static final String textUnsetCode = "39";


    public String getBaseCode();
    public static final String baseUnsetCode = "49";
}
