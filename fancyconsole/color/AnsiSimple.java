package fancyconsole.color;


public enum AnsiSimple implements ConsoleColor
{
    //Reference: https://en.wikipedia.org/wiki/ANSI_escape_code#3-bit_and_4-bit

    //Standard (3-bit)
    Red(31),
    Green(32),
    Yellow(33),
    Blue(34),
    Magenta(35),
    Cyan(36),

    //High-intenisty (4-bit)
    BrightRed(91),
    BrightGreen(92),
    BrightYellow(93),
    BrightBlue(94),
    BrightMagenta(95),
    BrightCyan(96),

    //Grayscale (3 and 4-bit)
    Black(30),    //ANSI "black"
    Gray(37),     //ANSI "white"
    DarkGray(90), //ANSI "bright black"
    White(97);    //ANSI "bright white"


    AnsiSimple(int colorCode)  { _colorCode = colorCode; }

    private  final int _colorCode;

    public String getTextCode() { return String.valueOf(_colorCode); }

    public String getBaseCode() { return  String.valueOf(_colorCode + 10); }
}

