/*
    Converts a Applet into an Application.

    copyright (c) 2006-2007Roedy Green, Canadian Mind Products
    may be copied and used freely for any purpose but military.
    Roedy Green
    Canadian Mind Products
    #101 - 2536 Wark Street
    Victoria, BC Canada
    V8T 4G8
    tel: (250) 361-9093
    mailto:roedyg@mindprod.com
    http://mindprod.com

    Version History
    1.0 2006-03-07 initial version.
    */
package com.mindprod.common11;

import java.applet.Applet;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Converts a Applet into an Application.
 *
 * @author Roedy Green, Canadian Mind Products
 * @version 1.0, 2006-03-07 Created with IntelliJ IDEA.
 */
public final class Hybrid {

    // -------------------------- PUBLIC STATIC METHODS --------------------------

    /**
     * Fire up the Applet as an application
     *
     * @param title             title for frame usually TITLESTRING+ " " +
     *                          VERSIONSTRING
     * @param applicationWidth  width of frame, usually APPLETWIDTH
     * @param applicationHeight height of frame body, usually  APPLETHEIGHT
     */
    public static void fireup( final Applet applet,
                               final String title,
                               final int applicationWidth,
                               final int applicationHeight )
        {
        final Frame frame = new Frame( title );
        // allow some extra room for the frame title bar.
        frame.setSize( applicationWidth, applicationHeight + 26 );
        frame.addWindowListener( new WindowAdapter() {
            /**
             * Handle request to shutdown.
             *
             * @param e event giving details of closing.
             */
            public void windowClosing( WindowEvent e )
                {
                applet.stop();
                applet.destroy();
                System.exit( 0 );
                }// end WindowClosing
        }// end anonymous class
        );// end addWindowListener line

        frame.add( applet );
        applet.init();
        frame.validate();
        frame.setVisible( true );
        applet.start();
        }
}
