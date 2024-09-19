import fancyconsole.*;
import fancyconsole.color.AnsiSimple;


public class Program
{
    public static void main(String[] args)
    {
        FancyConsole.changeForeground(AnsiSimple.Red);
        FancyConsole.changeBackground(AnsiSimple.Black);
        FancyConsole.textStyle(TextStyle.Bold);
        FancyConsole.textStyle(TextStyle.Underline);

        System.out.println("<very important warning you ignore>");

        FancyConsole.reset();
    }
}
