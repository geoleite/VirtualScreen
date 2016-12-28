/*
Mouse: determines where the mouse is on the screen.

copyright (c) 2003-2007 Roedy Green, Canadian Mind Products
may be copied and used freely for any purpose but military.

Roedy Green
Canadian Mind Products
#101 - 2536 Wark Street
Victoria, BC Canada
V8T 4G8
tel: (250) 361-9093
mailto:roedyg@mindprod.com
http://mindprod.com

version History

1.0 2004-04-30 initial
1.1 2005-07-30, ANT build, consistent naming.
1.2 2006-03-06 - reformat with IntelliJ, add Javadoc.
1.3 2007-05-25 - add pad, icon, clean code to IntelliJ inspector standards.
*/
package com.mindprod.mouse;

import java.awt.Point;

/**
 * Determines where the mouse is on screen. Similar to the functionality in Java
 * 1.5+ MouseInfo.getPointerInfo. It can tell where the mouse is even if it is
 * not on this application. Uses JNI native C++ code that only works on XP,
 * Windows 2000 and Windowns NT.
 * <pre>
 * Must generate mouse.h with
 *  javah.exe -jni -o mouse.h com.mindprod.mouse.Mouse
 *  NOT javah.exe Mouse
 * </pre>
 *
 * @author Roedy Green, Canadian Mind Products
 * @version 1.3 2007-05-25
 * @since 2004-04-30
 */
public final class Mouse {

    // ------------------------------ FIELDS ------------------------------

    /**
     * undisplayed copyright notice
     *
     * @noinspection UnusedDeclaration
     */
    public static final String EMBEDDEDCOPYRIGHT =
            "copyright (c) 2003-2007 Roedy Green, Canadian Mind Products, http://mindprod.com";

    /**
     * date this version released
     *
     * @noinspection UnusedDeclaration
     */
    private static final String RELEASEDATE = "2007-05-25";

    /**
     * embedded version string.
     *
     * @noinspection UnusedDeclaration
     */
    public static final String VERSIONSTRING = "1.3";

    // -------------------------- PUBLIC STATIC METHODS --------------------------

    /**
     * Get version of the DLL. Useful in debugging to make sure you have the
     * latest DLL installed.
     *
     * @return version number, simple integer. Should be 30.
     */
    public static native int getVersion();

    /**
     * Get position of mouse on the screen. 0,0 is the upper left corner.
     *
     * @return Point containing x,y where the mouse in now measured in screen
     *         pixels from the top left corner.
     *
     * @noinspection WeakerAccess
     */
    public static Point getWhereMouseIsOnScreen()
        {
        long xy = jniGetMousePosition();
        // y in high order, MS is little endian.
        int y = (int) ( xy >>> 32 );
        int x = (int) ( xy & 0xffffffffL );
        return new Point( x, y );
        }

    // -------------------------- STATIC METHODS --------------------------

    static
        {
        // get DLL loaded from somewhere on java.library path.
        System.loadLibrary( "nativemouse" );
        // if have troubles change this code to use load(
        // "E:\\com\\mindprod\\mouse\\nativemouse.dll" );
        // or similar.
        }

    /**
     * returns current mouse position x,y bundled into an int with y in the high
     * order bits.
     *
     * @return mouse position bundled xy.
     */
    private static native long jniGetMousePosition();

    // --------------------------- main() method ---------------------------

    /**
     * test harness
     *
     * @param args not used
     *
     * @noinspection InfiniteLoopStatement
     */
    public static void main( String[] args )
        {
        System.out.println( "version: " + Mouse.getVersion() );

        while ( true )
            {
            Point p = Mouse.getWhereMouseIsOnScreen();
            System.out.println( "x=" + p.x + " y=" + p.y );
            try
                {
                Thread.sleep( 100 );
                }
            catch ( InterruptedException e )
                {
                // no problem
                }
            }
        }
}
