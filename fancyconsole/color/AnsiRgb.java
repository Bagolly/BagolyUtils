package fancyconsole.color;

public class AnsiRgb implements ConsoleColor
{
    private  final String _colorBackgroundCode;
    private  final String _colorForegroundCode;

    public AnsiRgb(int red, int green, int blue)
    {
        if((red   < 0  || red   > 255) ||
           (green < 0  || green > 255) ||
           (blue  < 0  || blue  > 255))
        {
            throw new IllegalArgumentException("All components must be between 0 and 255");
        }

        //The official syntax would be <48 | 32>::2::<r>:<g>:<b> but some terminals,
        //such as Windows console, do not support it. This (non-standard) syntax
        // has wider support because of historical reasons.
        _colorBackgroundCode = String.format("38;2;%d;%d;%d", red, green, blue);
        _colorForegroundCode = String.format("48;2;%d;%d;%d", red, green,blue);
    }


    public static AnsiRgb fromCmyk(double cyan, double magenta, double yellow, double key)
    {
        if(cyan    < 0 || cyan    > 1 ||
           magenta < 0 || magenta > 1 ||
           yellow  < 0 || yellow  > 1 ||
           key     < 0 || key     > 1)
        {
            throw new IllegalArgumentException("All components must be between 0 and 1.");
        }

        return new AnsiRgb((int)(255 * (1 - cyan) * (1- key)),
                                (int)(255 * (1 - magenta) * (1 - key)),
                                (int)(255 * (1- yellow) * (1 - key)));
    }

    public String getTextCode() { return _colorBackgroundCode; }
    public String getBaseCode() { return _colorForegroundCode; }
}
