package fancyconsole;

import fancyconsole.color.ConsoleColor;
import java.util.Map;
import static java.util.Map.entry;


public class FancyConsole
{
    //In some places using it would create more concats. Only used when CSI is standalone and it makes the code cleaner.
    static final String CSI = "\u001b[";


    /**
     * Clear the console output from the console windows.
     * @param retainScrollback When true, additionally clears the scrollback buffer.
     * @apiNote Clearing the scrollback buffer means all text output up to
     * that point will be erased from the console window. Otherwise, the console
     * will scroll down to present a clean screen while retaining all previous output.
     * */
    public static String clear(boolean retainScrollback) { return retainScrollback ?  "\u001b[3J\u001b[H" : "\u001b[2J\u001b[H"; }


    /**
     * Right-pads the last line of output in the console window with whitespace.
     * @param count The amount of space characters to insert. Must be between 0 and 255.
     * @throws IllegalArgumentException When count is out of range (0 and 255).
     * */
    public static String padRightBy(int count)
    {
        if(count < 0 || count > 255)
            throw new IllegalArgumentException("Pad amount must be between 0 and 255.");

        return "\u001b[H\u001b[" + count + "@";
    }


    /**
     * Changes the cursor shape in the console window to the given cursor shape.
     * @param cursor The cursor shape to apply.
     * @apiNote Because of varying terminal support, this method may give a different result
     *          than expected from the given CursorType value.
     */
    public static String changeCursor(CursorType cursor) { return CSI + cursor.ordinal() + " q"; }


    /**
     * Moves the cursor to the given position.
     * @param column The column in the window.
     * @param row The row in the windows.
     * @apiNote Important: positions start from 1. This means the top
     *          left corner of the window is row 1, column 1.
     * */
    public static String setCursor(int row, int column)
    {
        return CSI + row + ";" + column + "H";
    }


    //Convenience method using lib calls on stdout (most common use case)
    /** Sets the foreground (text) color in the standard output stream to the given color. */
    public static void changeForeground(ConsoleColor color) { System.out.print(Set.background(color)); }
    /** Sets the background color in the standard output stream to the given color. */
    public static void changeBackground(ConsoleColor color) { System.out.print(Set.foreground(color)); }
    /** Resets the colors in the standard output stream. */
    public  static void resetColors() { System.out.print(Unset.bothColors()); }
    /** Resets the background color in the standard output stream. */
    public  static void resetBackground() { System.out.print(Unset.background()); }
    /** Resets the foreground color in the standard output stream. */
    public static void resetForeground()  { System.out.print(Unset.foreground()); }
    /** Applies the provided text style to the standard output stream. */
    public  static void textStyle(TextStyle style) { System.out.print(Set.style(style)); }
    /** Resets all text styles in the standard output stream. */
    public static void resetTextStyle() { System.out.print(Unset.allStyles());}
    /** Reverts the console to its default state. */
    public  static void reset() { System.out.print(Unset.all()); }

    /**
     * Outputs the full 8-bit color spectrum. Useful as a simple method for
     * testing terminal capabilities and previewing some of the colors.
     */
    public static void test8BitColorSpace()
    {
        System.out.println("\n\n              <Standard>                          <High Intensity>");
        for(int i = 0; i < 8; i++)
            System.out.print("\u001b[48;5;"+i+"m ### \u001b[0m");

        System.out.print(' ');
        for(int i = 8; i < 16; i++)
            System.out.print("\u001b[38;5;00;48;5;"+i+"m ### \u001b[0m");

        System.out.println("\n\n                                                                <Main>");
        for(int i = 16; i < 232; i++)
        {
            if((i - 16) % 36 > 17)
                System.out.print("\u001b[38;5;0;48;5;"+i+"m ### \u001b[0m");

            else
                System.out.print("\u001b[48;5;"+i+"m ### \u001b[0m");

            if((i + 1) % 36 == 16)
                System.out.print('\n');
        }

        System.out.println("\n\n");
        System.out.println("                                <Grayscale>");

        for(int i = 232; i <256; i++)
        {
            if(i < 244)
                System.out.print("\u001b[48;5;"+i+"m ### \u001b[0m");
            else
                System.out.print("\u001b[38;5;0;48;5;"+i+"m ### \u001b[0m");
        }

        System.out.println("\n\n");
    }


    /**
     * The main API for applying various colors and font styles.
     * */
    public static class Set
    {
        static final Map<TextStyle, String> _setCodes = Map.ofEntries(
                entry(TextStyle.Bold, CSI + "1m"),
                entry(TextStyle.Faint, CSI + "2m"),
                entry(TextStyle.Underline, CSI + "4m"),
                entry(TextStyle.DoubleUnderline, CSI + "21m"),
                entry(TextStyle.Inverted, CSI + "7m"),
                entry(TextStyle.StrikeThrough, CSI +"9m"),
                entry(TextStyle.Overline, CSI + "53m"),
                entry(TextStyle.Italic, CSI + "3m"),
                entry(TextStyle.Blink, CSI + "5m"),
                entry(TextStyle.RapidBlink, CSI + "6m"),
                entry(TextStyle.Hide, CSI + "8m")
        );


        /**
         * Adds the given text style to the console's output.
         * @param style The style to add.
         * @apiNote All calls to set are sticky.
         */
        public static String style(TextStyle style)
        {
            return _setCodes.get(style);
        }


        /**
         * Sets the console foreground color (text color) to the given color.
         * @param color The desired text color.
         * @apiNote All calls to setText are sticky.
         * */
        public static String foreground(ConsoleColor color)
        {
            return CSI + color.getTextCode() + "m";
        }


        /**
         * Sets the console background color to the given color.
         * @param color The desired background color.
         * @apiNote All calls to setBase are sticky.
         * */
        public static String background(ConsoleColor color)
        {
            return  CSI + color.getBaseCode() + "m";
        }


        /**
         * Sets the console's output to the given colors.
         * @param foreground The desired text color.
         * @param background The desired background color.
         * */
        public static String colors(ConsoleColor foreground, ConsoleColor background)
        {
            return CSI + background.getBaseCode() + ";" + foreground.getTextCode() + "m";
        }
    }

    /**
     * The main API for removing various colors and font styles.
     * */
    public static class Unset
    {
        static final Map<TextStyle, String> _unsetCodes = Map.ofEntries(
                entry(TextStyle.Bold, CSI + "22m"),
                entry(TextStyle.Faint, CSI + "22m"),
                entry(TextStyle.Underline, CSI + "24m"),
                entry(TextStyle.DoubleUnderline, CSI + "24m"),
                entry(TextStyle.Inverted, CSI + "27m"),
                entry(TextStyle.StrikeThrough, CSI + "29m"),
                entry(TextStyle.Overline, CSI + "55m"),
                entry(TextStyle.Italic, CSI + "23m"),
                entry(TextStyle.Blink, CSI + "25m"),
                entry(TextStyle.RapidBlink, CSI + "25m"),
                entry(TextStyle.Hide, CSI + "28m")
        );

        /**
         * Reverts the console to its initial state.
         */
        public  static String all()
        {
            return CSI + "0m";
        }


        /**
         * Removes the given text style.
         * @param style The style to remove.
         * @apiNote All calls to unset are sticky.
         * */
        public static String style(TextStyle style)
        {
            return _unsetCodes.get(style);
        }


        /**
         * Reverts all currently applied text styles.
         * @apiNote Colors are unaffected by this call.
         * */
        public static String allStyles()
        {
            return CSI + "22;23;24;25;27;28;29;55m";
        }


        /**
         * Unsets the current console foreground color (text color).
         * @apiNote Calls to this method are sticky.
         * */
        public static String foreground()
        {
            return CSI + ConsoleColor.textUnsetCode + "m";
        }


        /**
         * Unsets the current console background color.
         * @apiNote All calls to this method are sticky.
         * */
        public static String background()
        {
            return CSI + ConsoleColor.baseUnsetCode + "m";
        }


        /**
         * Resets the console's text and background colors.
         * */
        public static String bothColors()
        {
            return CSI + ConsoleColor.baseUnsetCode + ";" + ConsoleColor.textUnsetCode + "m";
        }
    }


    /**
     * The main API for wrapping messages in various colors and font styles.
     * */
    public static class Wrap
    {
        /**
         * Returns the given message wrapped with the provided text style.
         * @param message The message to wrap.
         * @param style The style to wrap the message with.
         * */
        public static String style(String message, TextStyle style)
        {
            return Set.style(style) + message + Unset.style(style);
        }

        /**
         * Returns the given message wrapped with the provided foreground (text) color.
         * @param message The message to wrap.
         * @param color The desired foreground (text) color.
         * */
        public static String foreground(String message, ConsoleColor color)
        {
            return Set.foreground(color) + message + Unset.foreground();
        }


        /**
         * Returns the given message wrapped with the provided background color.
         * @param message The message to wrap.
         * @param color The desired background color.
         * */
        public  static String background(String message, ConsoleColor color)
        {
            return Set.background(color) + message + Unset.background();
        }


        /**
         * Wraps a message in the provided foreground and background colors.
         * @param foreground The desired text color.
         * @param background The desired background color.
         * */
        public static String bothColors(String message, ConsoleColor foreground, ConsoleColor background)
        {
            return Set.colors(foreground,background) + message + Unset.bothColors();
        }
    }
}
