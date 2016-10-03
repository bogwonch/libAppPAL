package apppal;

public class Util
{
    public static boolean enable_debug = false;

    public static void info(String msg)
    {   System.err.println("\033[0;32m[INFO]\033[0;39m "+msg); }

    public static void debug(String msg)
    {   if (Util.enable_debug) System.err.println("\033[0;34m[DEBUG]\033[0;39m "+msg); }

    public static void warn(String msg)
    {   System.err.println("\033[0;33m[WARNING]\033[0;39m "+msg); }

    public static void error(String msg)
    {
        System.err.println("\033[0;31m[ERROR]\033[0;39m "+msg);
        System.exit(1);
    }
}
