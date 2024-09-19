package fancyconsole.color;

public class Ansi256 implements  ConsoleColor
{
    private final String _colorForegroundCode;
    private  final String _colorBackgroundCode;
    private  final int _colorCode;


    public Ansi256(int color)
    {
        if(color < 0 || color > 255)
            throw new IllegalArgumentException("Color code must be between 0 and 255.");

        var colorCode = String.valueOf(color);
        _colorForegroundCode = "38;5;" + colorCode;
        _colorBackgroundCode = "48;5;" + colorCode;
        _colorCode = color;
    }

    public String getTextCode() { return _colorForegroundCode; }

    public String getBaseCode() { return _colorBackgroundCode; }


    /**
     * Returns a new instance where the current color is intensified by the given degree.
     * @param degree The degree of intensification from 1 to 5. This scale is absolute.
     * @apiNote If the degree is too high or too low for the current color, this method
     *          returns the current instance.
     * */
    public Ansi256 intensify(int degree)
    {
        if(degree < 1 || degree > 5)
            return this;

        //Base color palette: 0 - 15
        if(_colorCode < 16)
        {
            if(_colorCode < 8 && degree == 1)
                return new Ansi256((_colorCode + 8));

            //8 - 15: color is already high-intensity, no logical intensity increase.
            return  this;
        }

        //Grayscale color palette: 232 - 255
        if(_colorCode > 231)
        {
            if(_colorCode + degree > 255) //Color overflow
                return  this;

            return new Ansi256(_colorCode + degree);
        }

        //Main palette (6×6×6 colorbox): 16 - 231
        else
        {
            //Would cause overflow to grayscale color-space.
            if(_colorCode + (degree * 36) > 231)
                return this;

            return  new Ansi256(_colorCode + degree * 36);
        }
    }

    /**
     * Returns a new instance where the current color is deintensified by the given degree.
     * @param degree The degree of deintensification from 1 to 5. This scale is absolute.
     * @apiNote If the degree is too high or too low for the current color, this method
     *          returns the current instance.
     * */
    public Ansi256 deIntensify(int degree)
    {
        if(degree < 1 || degree > 5)
            return this;

        //Base color palette: 0 - 15
        if(_colorCode < 16)
        {
            if(_colorCode > 8 || degree == 1)
                return new Ansi256((_colorCode - 8));

            //Color is already standard intensity, no logical intensity decrease.
            return  this;
        }

        //Grayscale color palette: 232 - 255
        if(_colorCode > 231)
        {
            //Would cause underflow to normal color-space.
            if(_colorCode - degree < 232)
                return  this;

            return new Ansi256(_colorCode - degree);
        }

        //Main palette (6×6×6 colorbox): 16 - 231
        else
        {
            //Would cause underflow to base color-space.
            if(_colorCode - (degree * 36) < 16)
                return this;

            return  new Ansi256(_colorCode - degree * 36);
        }
    }
}
