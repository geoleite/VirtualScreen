/*
CMPAboutBox.java an About Box template

copyright (c) 1999-2007 Roedy Green, Canadian Mind Products
may be copied and used freely for any purpose but military.
Roedy Green
Canadian Mind Products
#101 - 2536 Wark Street
Victoria, BC Canada
V8T 4G8
tel: (250) 361-9093
mailto:roedyg@mindprod.com
http://mindprod.com
*/
package com.mindprod.common11;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * An AWT About box that truly tells you about he program, not just the author's
 * name.
 *
 * @author Roedy Green
 * @version 1.1, 2006-03-04
 * @noinspection FieldCanBeLocal
 */
public final class CMPAboutBox extends Dialog {

    // ------------------------------ FIELDS ------------------------------

    /**
     * true if debugging
     */
    private static final boolean DEBUGGING = false;

    private static final Color BLACK = Color.black;

    private static final Color DARKGREEN = new Color( 0, 128, 0 );

    private static final Color LABEL_COLOUR = new Color( 0x0000b0 );

    /**
     * for titles
     */
    private static final Color TITLE_COLOUR = new Color( 0xdc143c );

    private static final Color WHITE = Color.white;

    /**
     * for for titles and About buttons
     */
    private static final Font TITLE_FONT = new Font( "Dialog", Font.BOLD, 16 );

    /**
     * button to dismiss the dialog
     */
    private Button _dismissButton;

    /**
     * line 1 of CMP mailing address
     */
    private Label _addr1;

    /**
     * line 2 of CMP mailing address
     */
    private Label _addr2;

    /**
     * program author
     */
    private Label _author;

    /**
     * Canadian Mind Products
     */
    private Label _cmp;

    /**
     * copyright notice
     */
    private Label _copyright;

    /**
     * download URL
     */
    private Label _http;

    /**
     * contact email
     */
    private Label _mailto;

    /**
     * CMP phone number
     */
    private Label _phone;

    /**
     * program name and version
     */
    private Label _prognameVersion;

    /**
     * line 1 of what program is for
     */
    private Label _purpose1;

    /**
     * line 2 of what program is for
     */
    private Label _purpose2;

    /**
     * yyyy-mm-dd this version released
     */
    private Label _released;

    /**
     * is prgram free, shareware etc.
     */
    private Label _status;

    // -------------------------- PUBLIC INSTANCE  METHODS --------------------------
    /**
     * Create an about box, with default author Roedy Green
     *
     * @param parent     frame for this about box.
     * @param progname   Program name
     * @param version    Program version e.g. "1.3"
     * @param purpose1   what is this program for? line-1
     * @param purpose2   what is this program for? line-2. may be null, or "".
     * @param status     e.g. "unregistered shareware", "freeware",
     *                   "commercial", "company confidential"
     * @param released   Date released e.g. "1999-12-31"
     * @param copyright  e.g. "1996-2005"
     * @param masterSite e.g. CONVERTER -- where to find most up to date ZIP
     *
     * @noinspection SameParameterValue,WeakerAccess
     */
    public CMPAboutBox( Frame parent,
                        String progname,
                        String version,
                        String purpose1,
                        String purpose2,
                        String status,
                        String released,
                        String copyright,
                        String masterSite )
        {
        this( parent,
              progname,
              version,
              purpose1,
              purpose2,
              status,
              released,
              copyright,
              "Roedy Green",
              masterSite );
        }

    /**
     * Create an about box when don't have parent
     *
     * @param progname   Program name
     * @param version    Program version e.g. "1.3"
     * @param purpose1   what is this program for? line-1
     * @param purpose2   what is this program for? line-2. may be null, or "".
     * @param status     e.g. "unregistered shareware", "freeware",
     *                   "commercial", "company confidential"
     * @param released   Date released e.g. "1999/12/31"
     * @param copyright  e.g. "1996-2004"
     * @param author     e.g. "Roedy Green"
     * @param masterSite e.g. CONVERTER -- where to find most up to date ZIP
     */
    public CMPAboutBox( String progname,
                        String version,
                        String purpose1,
                        String purpose2,
                        String status,
                        String released,
                        String copyright,
                        String author,
                        String masterSite )
        {
        this( new Frame( progname + " " + version )
              /*
              * dummy parent, won't be
              * disposed!!
              */,
              progname,
              version,
              purpose1,
              purpose2,
              status,
              released,
              copyright,
              author,
              masterSite );
        }

    /**
     * Create an about box
     *
     * @param parent     frame for this about box.
     * @param progname   Program name
     * @param version    Program version e.g. "1.3"
     * @param purpose1   what is this program for? line-1
     * @param purpose2   what is this program for? line-2. may be null, or "".
     * @param status     e.g. "unregistered shareware", "freeware",
     *                   "commercial", "company confidential"
     * @param released   Date released e.g. "1999-12-31"
     * @param copyright  e.g. "1996-2004"
     * @param author     e.g. "Roedy Green"
     * @param masterSite e.g. CONVERTER -- where to find most up to date ZIP
     *
     * @noinspection WeakerAccess
     */
    public CMPAboutBox( Frame parent,
                        String progname,
                        String version,
                        String purpose1,
                        String purpose2,
                        String status,
                        String released,
                        String copyright,
                        String author,
                        String masterSite )
        {
        super( parent, progname + " " + version, false/* not modal */ );
        guts( progname,
              version,
              purpose1,
              purpose2,
              status,
              released,
              copyright,
              author,
              masterSite );
        }

    /**
     * bypass setBackground bug, by setting it over and over, every time
     * addNotify gets called.
     */
    public void addNotify()
        {
        super.addNotify();
        setBackground( WHITE );
        }

    // -------------------------- OTHER METHODS --------------------------

    /**
     * Shutdown the about box
     */
    private void dismiss()
        {
        // close the about box
        this.setVisible( false );
        // tell AWT to discard all pointers to the Dialog box.
        this.dispose();
        }// end dismiss

    /**
     * common parts to all creation Guts of reating an an about box
     *
     * @param progname   Program name
     * @param version    Program version e.g. "1.3"
     * @param purpose1   what is this program for? line-1
     * @param purpose2   what is this program for? line-2. may be null, or "".
     * @param status     e.g. "unregistered shareware", "freeware",
     *                   "commercial", "company confidential"
     * @param released   Date released e.g. "1999-12-31"
     * @param copyright  e.g. "1996-2004"
     * @param author     e.g. "Roedy Green"
     * @param masterSite e.g. CONVERTER -- where to find most up to date ZIP
     */
    private void guts( String progname,
                       String version,
                       String purpose1,
                       String purpose2,
                       String status,
                       String released,
                       String copyright,
                       String author,
                       String masterSite )
        {
        // basic layout
        // 0 1
        // 0 ---------------progname version--------------------------------- 0
        //
        // 1 ---------------------purpose1------------------------------------ 1
        // 2 ---------------------purpose2------------------------------------ 2
        //
        // 3 ---------------------status-------------------------------------- 3
        // 4 --------------released: xxxxxxxxx-------------------------------- 4
        //
        // 5 copyright 2000 5
        // 6 Roedy Green 6
        // 7 Canadian Mind Products 7
        // 8 #101 - 2536 Wark Street 8
        // 9 Victoria, BC Canada V8T 4G8 phone:(250) 361-9093 9
        // 10 roedyg@mindprod.com http://mindprod.com/products#CONVERTER 10
        //
        // 11 (Dismiss) 11
        // 0 1

        // leave room for warning this frame belongs to Java applet.
        setSize( 500, 380 );
        setLocation( 0, 0 );
        setBackground( WHITE );
        GridBagLayout gridBagLayout;
        gridBagLayout = new GridBagLayout();
        GridBagConstraints gbc;

        setLayout( gridBagLayout );

        _prognameVersion =
                new Label( progname + " " + version + " build " + Build
                        .BUILD_NUMBER, Label.CENTER );
        _prognameVersion.setFont( TITLE_FONT );
        _prognameVersion.setForeground( TITLE_COLOUR );
        _prognameVersion.setBackground( WHITE );
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets( 15, 10, 5, 10 );
        ( (GridBagLayout) getLayout() ).setConstraints( _prognameVersion, gbc );
        add( _prognameVersion );

        _purpose1 = new Label( purpose1, Label.CENTER );
        _purpose1.setFont( new Font( "Dialog", Font.ITALIC, 12 ) );
        _purpose1.setForeground( BLACK );
        _purpose1.setBackground( WHITE );
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets( 5, 10, 0, 5 );
        ( (GridBagLayout) getLayout() ).setConstraints( _purpose1, gbc );
        add( _purpose1 );

        if ( purpose2 != null && purpose2.length() > 0 )
            {
            _purpose2 = new Label( purpose2, Label.CENTER );
            _purpose2.setFont( new Font( "Dialog", Font.ITALIC, 12 ) );
            _purpose2.setForeground( BLACK );
            _purpose2.setBackground( WHITE );
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets( 0, 10, 0, 10 );
            ( (GridBagLayout) getLayout() ).setConstraints( _purpose2, gbc );
            add( _purpose2 );
            }

        _status = new Label( status, Label.CENTER );
        _status.setFont( new Font( "Dialog", Font.BOLD, 12 ) );
        _status.setForeground( LABEL_COLOUR );
        _status.setBackground( WHITE );
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets( 10, 10, 0, 10 );
        ( (GridBagLayout) getLayout() ).setConstraints( _status, gbc );
        add( _status );

        _released = new Label( "released: " + released, Label.CENTER );
        _released.setFont( new Font( "Dialog", Font.PLAIN, 11 ) );
        _released.setForeground( LABEL_COLOUR );
        _released.setBackground( WHITE );
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets( 0, 10, 10, 10 );
        ( (GridBagLayout) getLayout() ).setConstraints( _released, gbc );
        add( _released );

        _copyright = new Label( "copyright " + copyright, Label.LEFT );
        _copyright.setFont( new Font( "Dialog", Font.ITALIC, 11 ) );
        _copyright.setForeground( DARKGREEN );
        _copyright.setBackground( WHITE );
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets( 5, 10, 0, 5 );
        ( (GridBagLayout) getLayout() ).setConstraints( _copyright, gbc );
        add( _copyright );

        _author = new Label( author, Label.LEFT );
        _author.setFont( new Font( "Dialog", Font.BOLD + Font.ITALIC, 11 ) );
        _author.setForeground( DARKGREEN );
        _author.setBackground( WHITE );
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets( 0, 10, 0, 5 );
        ( (GridBagLayout) getLayout() ).setConstraints( _author, gbc );
        add( _author );

        _cmp = new Label( "Canadian Mind Products", Label.LEFT );
        _cmp.setFont( new Font( "Dialog", Font.BOLD + Font.ITALIC, 11 ) );
        _cmp.setForeground( DARKGREEN );
        _cmp.setBackground( WHITE );
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets( 0, 10, 0, 5 );
        ( (GridBagLayout) getLayout() ).setConstraints( _cmp, gbc );
        add( _cmp );

        _addr1 = new Label( "#101 - 2536 Wark Street", Label.LEFT );
        _addr1.setFont( new Font( "Dialog", Font.ITALIC, 11 ) );
        _addr1.setForeground( DARKGREEN );
        _addr1.setBackground( WHITE );
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets( 0, 10, 0, 5 );
        ( (GridBagLayout) getLayout() ).setConstraints( _addr1, gbc );
        add( _addr1 );

        _addr2 = new Label( "Victoria, BC Canada V8T 4G8", Label.LEFT );
        _addr2.setFont( new Font( "Dialog", Font.ITALIC, 11 ) );
        _addr2.setForeground( DARKGREEN );
        _addr2.setBackground( WHITE );
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets( 0, 10, 0, 5 );
        ( (GridBagLayout) getLayout() ).setConstraints( _addr2, gbc );
        add( _addr2 );

        _phone = new Label( "phone:(250) 361-9093", Label.RIGHT );
        _phone.setFont( new Font( "Dialog", Font.ITALIC, 11 ) );
        _phone.setForeground( DARKGREEN );
        _phone.setBackground( WHITE );
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets( 0, 5, 0, 10 );
        ( (GridBagLayout) getLayout() ).setConstraints( _phone, gbc );
        add( _phone );

        _mailto = new Label( "roedyg@mindprod.com", Label.LEFT );
        _mailto.setFont( new Font( "Dialog", Font.ITALIC, 11 ) );
        _mailto.setForeground( DARKGREEN );
        _mailto.setBackground( WHITE );
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets( 0, 10, 0, 5 );
        ( (GridBagLayout) getLayout() ).setConstraints( _mailto, gbc );
        add( _mailto );

        _http =
                new Label( "http://mindprod.com/products.html#" + masterSite,
                           Label.RIGHT );
        _http.setFont( new Font( "Dialog", Font.ITALIC, 10 ) );
        _http.setForeground( DARKGREEN );
        _http.setBackground( WHITE );
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 10;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets( 0, 5, 0, 10 );
        ( (GridBagLayout) getLayout() ).setConstraints( _http, gbc );
        add( _http );

        _dismissButton = new Button( "Dismiss" );
        _dismissButton.setFont( new Font( "Dialog", Font.BOLD, 15 ) );
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets( 15, 10, 15, 10 );
        ( (GridBagLayout) getLayout() ).setConstraints( _dismissButton, gbc );
        _dismissButton.requestFocus();
        add( _dismissButton );

        // hook up About box listeners

        this.addFocusListener( new FocusAdapter() {
            /**
             * Handle About Dialog getting focus, give to dismiss Button.
             *
             * @param e event giving details
             */
            public void focusGained( FocusEvent e )
                {
                _dismissButton.requestFocus();
                }// end focusGained
        }// end anonymous class
        );// end addFocusListenerline

        this.addWindowListener( new WindowAdapter() {
            /**
             * Handle request to close about box
             *
             * @param e event giving details of closing.
             */
            public void windowClosing( WindowEvent e )
                {
                dismiss();
                }// end WindowClosing
        }// end anonymous class
        );// end addWindowListener line

        _dismissButton.addActionListener( new ActionListener() {
            /**
             * close down the About box when user clicks Dismiss
             */
            public void actionPerformed( ActionEvent e )
                {
                Object object = e.getSource();
                if ( object == _dismissButton )
                    {
                    dismiss();
                    }// end if
                }// end actionPerformed
        }// end anonymous class
        );// end addActionListener line

        // The following code is only necessary if you want
        // any keystroke while the Dismiss button has focus
        // to simulate clicking the Dismiss button.

        _dismissButton.addKeyListener( new KeyAdapter() {
            /**
             * Handle Dismiss button getting a function keystroke, treat like a
             * dismiss Button click
             *
             * @param e event giving details
             */
            public void keyPressed( KeyEvent e )
                {
                dismiss();
                }// end keyPressed

            /**
             * Handle Dismiss button getting an ordinary keystroke, treat like a
             * dismiss Button click
             *
             * @param e event giving details
             */
            public void keyTyped( KeyEvent e )
                {
                dismiss();
                }// end keyTyped
        }// end anonymous class
        );// end addKeyListener

        this.validate();
        this.setVisible( true );
        }// end constructor

    // --------------------------- main() method ---------------------------

    /**
     * sample test driver
     *
     * @param args not used
     */
    public static void main( String[] args )
        {
        if ( DEBUGGING )
            {
            final Frame frame = new Frame( "About box test" );
            frame.setSize( 450, 400 );

            MenuBar mb = frame.getMenuBar();
            if ( mb == null )
                {
                mb = new MenuBar();
                frame.setMenuBar( mb );
                }

            Menu help = mb.getHelpMenu();
            if ( help == null )
                {
                help = new Menu( "Help", /* tearoff */false );
                mb.setHelpMenu( help );
                }

            help.add( new MenuItem( "keyboard" ) );
            help.add( new MenuItem( "command line" ) );
            help.add( new MenuItem( "About" ) );
            help.addActionListener( new ActionListener() {
                /**
                 * Handle Menu Selection Request
                 *
                 * @param e event giving details of selection
                 */
                public void actionPerformed( ActionEvent e )
                    {
                    String command = e.getActionCommand();
                    if ( command.equals( "About" ) )
                        {
                        // Don't need to retain a reference.
                        // We do nothing more that fire it up.
                        new CMPAboutBox( frame,
                                         "Sample Amanuensis",
                                         "1.3",
                                         "Teaches you how to interconvert the 16 basic Java types,",
                                         "e.g. String to int, Long to double.",
                                         "freeware",
                                         "2000-01-01"
                                         /* RELEASEDATE */,
                                         "1996-2007"
                                         /* copyright */,
                                         "CONVERTER" );
                        }// end if
                    }// end ActionListener
            }// end anonymous class
            );// end addActionListener line

            frame.addWindowListener( new WindowAdapter() {
                /**
                 * Handle request to shutdown.
                 *
                 * @param e event giving details of closing.
                 */
                public void windowClosing( WindowEvent e )
                    {
                    System.exit( 0 );
                    }// end WindowClosing
            }// end anonymous class
            );// end addWindowListener line

            frame.validate();
            frame.setVisible( true );
            }// end if
        }// end main
}
