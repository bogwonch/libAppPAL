package apppal;

public class Util
{
    public static void info(String msg)
    {   System.err.println("\033[0;32m[I]\033[0;39m]: "+msg); }

    public static void debug(String msg)
    {   System.err.println("\033[0;34m[D]\033[0;39m]: "+msg); }

    public static void warn(String msg)
    {   System.err.println("\033[0;33m[W]\033[0;39m]: "+msg); }

    public static void error(String msg)
    {
        System.err.println("\033[0;31m[E]\033[0;39m]: "+msg);
        System.exit(1);
    }
}
