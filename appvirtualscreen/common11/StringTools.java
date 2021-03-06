/*
StringTools: Miscellaneous static methods for dealing with Strings.

copyright (c) 1997-2007 Roedy Green, Canadian Mind Products
may be copied and used freely for any purpose but military.
Roedy Green
Canadian Mind Products
#101 - 2536 Wark Street
Victoria, BC Canada
V8T 4G8
tel: (250) 361-9093
mailto:roedyg@mindprod.com
http://mindprod.com

version history

version 1.5 2005-07-14 split off from Misc, allow for compilation with old compiler.

version 1.6 2006-10-15 add condense method.

vernion 1.7 2006-03-04 format with IntelliJ and prepare Javadoc
*/
package com.mindprod.common11;

import java.awt.Color;

/**
 * Miscellaneous static methods for dealing with Strings.
 *
 * @author Roedy Green, Canadian Mind Products
 * @version 1.8,  2007-03-22, added RemoveHead and removeTail
 * @since 2003-05-15
 */
public final class StringTools {

    // ------------------------------ FIELDS ------------------------------

    /**
     * true if you want extra debugging output and test code
     */
    private static final boolean DEBUGGING = false;

    // -------------------------- PUBLIC STATIC METHODS --------------------------

    /**
     * makeshift system beep if awt.Toolkit.beep is not available. Works also in
     * JDK 1.02.
     */
    public static void beep()
        {
        System.out.print( "\007" );
        System.out.flush();
        }// end beep

    /**
     * Convert String to canonical standard form. null -> "". Trims lead trail
     * blanks.
     *
     * @param s String to be converted.
     *
     * @return String in canonical form.
     */
    public static String canonical( String s )
        {
        if ( s == null )
            {
            return "";
            }
        else
            {
            return s.trim();
            }
        }// end canonical

    /**
     * Collapse multiple spaces in string down to a single space. Remove lead
     * and trailing spaces.
     *
     * @param s String to strip of blanks.
     *
     * @return String with all blanks, lead/trail/embedded removed.
     *
     * @noinspection WeakerAccess,SameParameterValue
     * @see #squish(String)
     */
    public static String condense( String s )
        {
        if ( s == null )
            {
            return null;
            }
        s = s.trim();
        if ( s.indexOf( "  " ) < 0 )
            {
            return s;
            }
        int len = s.length();
        // have to use StringBuffer for JDK 1.1
        StringBuffer b = new StringBuffer( len - 1 );
        boolean suppressSpaces = false;
        for ( int i = 0; i < len; i++ )
            {
            char c = s.charAt( i );
            if ( c == ' ' )
                {
                if ( suppressSpaces )
                    {
                    // subsequent space
                    }
                else
                    {
                    // first space
                    b.append( c );
                    suppressSpaces = true;
                    }
                }
            else
                {
                // was not a space
                b.append( c );
                suppressSpaces = false;
                }
            }// end for
        return b.toString();
        }// end condense

    /**
     * count of how many leading characters there are on a string matching a
     * given character. It does not remove them.
     *
     * @param text text with possible leading characters, possibly empty, but
     *             not null.
     * @param c    the leading character of interest, usually ' ' or '\n'
     *
     * @return count of leading matching characters, possibly 0.
     *
     * @noinspection WeakerAccess
     */
    public static int countLeading( String text, char c )
        {
        // need defined outside the for loop.
        int count;
        for ( count = 0; count < text.length()
                         && text.charAt( count ) == c; count++ )
            {
            }
        return count;
        }

    /**
     * count of how many trailing characters there are on a string matching a
     * given character. It does not remove them.
     *
     * @param text text with possible trailing characters, possibly empty, but
     *             not null.
     * @param c    the trailing character of interest, usually ' ' or '\n'
     *
     * @return count of trailing matching characters, possibly 0.
     *
     * @noinspection WeakerAccess
     */
    public static int countTrailing( String text, char c )
        {
        int length = text.length();
        // need defined outside the for loop.
        int count;
        for ( count = 0; count < length
                         && text.charAt( length - 1 - count ) == c; count++ )
            {
            }
        return count;
        }

    /**
     * Is this string empty? In Java 1.6 + isEmpty is build in. Sun's version
     * being an instance method cannot test for null.
     *
     * @param s String to be tested for emptiness.
     *
     * @return true if the string is null or equal to the "" null string. or
     *         just blanks
     */
    public static boolean isEmpty( String s )
        {
        return ( s == null ) || s.trim().length() == 0;
        }// end isEmpty

    /**
     * Ensure the string contains only legal characters.
     *
     * @param candidate  string to test.
     * @param legalChars characters than are legal for candidate.
     *
     * @return true if candidate is formed only of chars from the legal set.
     */
    public static boolean isLegal( String candidate, String legalChars )
        {
        for ( int i = 0; i < candidate.length(); i++ )
            {
            if ( legalChars.indexOf( candidate.charAt( i ) ) < 0 )
                {
                return false;
                }
            }
        return true;
        }

    /**
     * Check if char is plain ASCII lower case.
     *
     * @param c char to check.
     *
     * @return true if char is in range a..z.
     *
     * @see Character#isLowerCase(char)
     */
    public static boolean isUnaccentedLowerCase( char c )
        {
        return 'a' <= c && c <= 'z';
        }// isUnaccentedLowerCase

    /**
     * Check if char is plain ASCII upper case.
     *
     * @param c char to check.
     *
     * @return true if char is in range A..Z.
     *
     * @see Character#isUpperCase(char)
     */
    public static boolean isUnaccentedUpperCase( char c )
        {
        return 'A' <= c && c <= 'Z';
        }// end isUnaccentedUpperCase

    /**
     * Pads the string out to the given length by applying blanks on the left.
     *
     * @param s      String to be padded/chopped.
     * @param newLen length of new String desired.
     * @param chop   true if Strings longer than newLen should be truncated to
     *               newLen chars.
     *
     * @return String padded on left/chopped to the desired length. Spaces are
     *         inserted on the left.
     */
    public static String leftPad( String s, int newLen, boolean chop )
        {
        int grow = newLen - s.length();
        if ( grow <= 0 )
            {
            if ( chop )
                {
                return s.substring( 0, newLen );
                }
            else
                {
                return s;
                }
            }
        else if ( grow <= 30 )
            {
            return "                              ".substring( 0, grow ) + s;
            }
        else
            {
            return rep( ' ', grow ) + s;
            }
        }// end leftPad

    /**
     * convert a String to a long. The routine is very forgiving. It ignores
     * invalid chars, lead trail, embedded spaces, decimal points etc. Dash is
     * treated as a minus sign.
     *
     * @param numStr String to be parsed.
     *
     * @return long value of String with junk characters stripped.
     *
     * @throws NumberFormatException if the number is too big to fit in a long.
     */
    public static long parseDirtyLong( String numStr )
        {
        numStr = numStr.trim();
        // strip commas, spaces, decimals + etc
        StringBuffer b = new StringBuffer( numStr.length() );
        boolean negative = false;
        for ( int i = 0; i < numStr.length(); i++ )
            {
            char c = numStr.charAt( i );
            if ( c == '-' )
                {
                negative = true;
                }
            else if ( '0' <= c && c <= '9' )
                {
                b.append( c );
                }
            }// end for
        numStr = b.toString();
        if ( numStr.length() == 0 )
            {
            return 0;
            }
        long num = Long.parseLong( numStr );
        if ( negative )
            {
            num = -num;
            }
        return num;
        }// end parseDirtyLong

    /**
     * convert a String into long pennies. It ignores invalid chars, lead trail,
     * embedded spaces. Dash is treated as a minus sign. 0 or 2 decimal places
     * are permitted.
     *
     * @param numStr String to be parsed.
     *
     * @return long pennies.
     *
     * @throws NumberFormatException if the number is too big to fit in a long.
     * @noinspection WeakerAccess
     */
    public static long parseLongPennies( String numStr )
        {
        numStr = numStr.trim();
        // strip commas, spaces, + etc
        StringBuffer b = new StringBuffer( numStr.length() );
        boolean negative = false;
        int decpl = -1;
        for ( int i = 0; i < numStr.length(); i++ )
            {
            char c = numStr.charAt( i );
            switch ( c )
                {
                case '-':
                    negative = true;
                    break;

                case '.':
                    if ( decpl == -1 )
                        {
                        decpl = 0;
                        }
                    else
                        {
                        throw new NumberFormatException(
                                "more than one decimal point" );
                        }
                    break;

                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    if ( decpl != -1 )
                        {
                        decpl++;
                        }
                    b.append( c );
                    break;

                default:
                    // ignore junk chars
                    break;
                }// end switch
            }// end for
        if ( numStr.length() != b.length() )
            {
            numStr = b.toString();
            }

        if ( numStr.length() == 0 )
            {
            return 0;
            }
        long num = Long.parseLong( numStr );
        if ( decpl == -1 || decpl == 0 )
            {
            num *= 100;
            }
        else if ( decpl == 2 )
            {/* it is fine as is */
            }

        else
            {
            throw new NumberFormatException( "wrong number of decimal places." );
            }

        if ( negative )
            {
            num = -num;
            }
        return num;
        }// end parseLongPennies

    /**
     * Print dollar currency, stored internally as scaled int. convert pennies
     * to a string with a decorative decimal point.
     *
     * @param pennies long amount in pennies.
     *
     * @return amount with decorative decimal point, but no lead $.
     *
     * @noinspection WeakerAccess
     */
    public static String penniesToString( long pennies )
        {
        boolean negative;
        if ( pennies < 0 )
            {
            pennies = -pennies;
            negative = true;
            }
        else
            {
            negative = false;
            }
        String s = Long.toString( pennies );
        int len = s.length();
        switch ( len )
            {
            case 1:
                s = "0.0" + s;
                break;
            case 2:
                s = "0." + s;
                break;
            default:
                s =
                        s.substring( 0, len - 2 ) + "." + s.substring( len - 2,
                                                                       len );
                break;
            }// end switch
        if ( negative )
            {
            s = "-" + s;
            }
        return s;
        }// end penniesToString

    /**
     * Extracts a number from a string, returns 0 if malformed.
     *
     * @param s String containing the integer.
     *
     * @return binary integer.
     */
    public static int pluck( String s )
        {
        int result = 0;
        try
            {
            result = Integer.parseInt( s );
            }
        catch ( NumberFormatException e )
            {
            // leave result at 0
            }
        return result;
        }// end pluck

    /**
     * used to prepare SQL string literals by doubling each embedded ' and
     * wrapping in ' at each end. Further quoting is required to use the results
     * in Java String literals. If you use PreparedStatement, then this method
     * is not needed. The ' quoting is automatically handled for you.
     *
     * @param sql Raw SQL string literal
     *
     * @return sql String literal enclosed in '
     *
     * @noinspection WeakerAccess,SameParameterValue
     */
    public static String quoteSQL( String sql )
        {
        StringBuffer sb = new StringBuffer( sql.length() + 5 );
        sb.append( '\'' );
        for ( int i = 0; i < sql.length(); i++ )
            {
            char c = sql.charAt( i );
            if ( c == '\'' )
                {
                sb.append( "\'\'" );
                }
            else
                {
                sb.append( c );
                }
            }
        sb.append( '\'' );
        return sb.toString();
        }

    /**
     * Remove an unwanted bit of text at the head end of a string if it is
     * present. Case sensitive.
     *
     * @param s    the string with the possibly wanted head. Will not be
     *             modified.
     * @param head the characters you want to remove from the start of the
     *             string s if they are present.
     *
     * @return s with the head removed if it is present, otherwise s
     *         unmodified.
     */
    public static String removeHead( final String s, final String head )
        {
        if ( s != null && s.startsWith( head ) )
            {
            return s.substring( head.length() );
            }
        else
            {
            return s;
            }
        }

    /**
     * Remove an unwanted bit of text at the tail end of a string if it is
     * present. Case sensitive.
     *
     * @param s    the string with the possibly wanted tail. Will not be
     *             modified.
     * @param tail the characters you want to remove from the end if they are
     *             present.
     *
     * @return s with the tail removed if it is present, otherwise s
     *         unmodified.
     */
    public static String removeTail( final String s, final String tail )
        {
        if ( s != null && s.endsWith( tail ) )
            {
            return s.substring( 0, s.length() - tail.length() );
            }
        else
            {
            return s;
            }
        }

    /**
     * Produce a String of a given repeating character.
     *
     * @param c     the character to repeat
     * @param count the number of times to repeat
     *
     * @return String, e.g. rep('*',4) returns "****"
     *
     * @noinspection WeakerAccess,SameParameterValue
     */
    public static String rep( char c, int count )
        {
        char[] s = new char[count];
        for ( int i = 0; i < count; i++ )
            {
            s[ i ] = c;
            }
        return new String( s ).intern();
        }// end rep

    /**
     * Pads the string out to the given length by applying blanks on the right.
     *
     * @param s      String to be padded/chopped.
     * @param newLen length of new String desired.
     * @param chop   true if Strings longer than newLen should be truncated to
     *               newLen chars.
     *
     * @return String padded on right/chopped to the desired length. Spaces are
     *         inserted on the right.
     *
     * @noinspection WeakerAccess,SameParameterValue
     */
    public static String rightPad( String s, int newLen, boolean chop )
        {
        int grow = newLen - s.length();
        if ( grow <= 0 )
            {
            if ( chop )
                {
                return s.substring( 0, newLen );
                }
            else
                {
                return s;
                }
            }
        else if ( grow <= 30 )
            {
            return s + "                              ".substring( 0, grow );
            }
        else
            {
            return s + rep( ' ', grow );
            }
        }// end rightPad

    /**
     * Remove all spaces from a String.
     *
     * @param s String to strip of blanks.
     *
     * @return String with all blanks, lead/trail/embedded removed.
     *
     * @see #condense(String)
     */
    public static String squish( String s )
        {
        if ( s == null )
            {
            return null;
            }
        s = s.trim();
        if ( s.indexOf( ' ' ) < 0 )
            {
            return s;
            }
        int len = s.length();
        // have to use StringBuffer for JDK 1.1
        StringBuffer b = new StringBuffer( len - 1 );
        for ( int i = 0; i < len; i++ )
            {
            char c = s.charAt( i );
            if ( c != ' ' )
                {
                b.append( c );
                }
            }// end for
        return b.toString();
        }// end squish

    /**
     * convert to Book Title case, with first letter of each word capitalised.
     * e.g. "handbook to HIGHER consciousness" -> "Handbook to Higher
     * Consciousness" e.g. "THE HISTORY OF THE U.S.A." -> "The History of the
     * U.S.A." e.g. "THE HISTORY OF THE USA" -> "The History of the Usa" (sorry
     * about that.) Don't confuse this with Character.isTitleCase which concerns
     * ligatures.
     *
     * @param s String to convert. May be any mixture of case.
     *
     * @return String with each word capitalised, except embedded words "the"
     *         "of" "to"
     *
     * @noinspection WeakerAccess
     */
    public static String toBookTitleCase( String s )
        {
        char[] ca = s.toCharArray();
        // Track if we changed anything so that
        // we can avoid creating a duplicate String
        // object if the String is already in Title case.
        boolean changed = false;
        boolean capitalise = true;
        boolean firstCap = true;
        for ( int i = 0; i < ca.length; i++ )
            {
            char oldLetter = ca[ i ];
            if ( oldLetter <= '/'
                 || ':' <= oldLetter && oldLetter <= '?'
                 || ']' <= oldLetter && oldLetter <= '`' )
                {
                /* whitespace, control chars or punctuation */
                /* Next normal char should be capitalised */
                capitalise = true;
                }
            else
                {
                if ( capitalise && !firstCap )
                    {
                    // might be the_ of_ or to_
                    capitalise =
                            !( s.substring( i, Math.min( i + 4, s.length() ) )
                                    .equalsIgnoreCase( "the " )
                               || s.substring( i,
                                               Math.min( i + 3, s.length() ) )
                                    .equalsIgnoreCase( "of " )
                               || s.substring( i,
                                               Math.min( i + 3, s.length() ) )
                                    .equalsIgnoreCase( "to " ) );
                    }// end if
                char newLetter =
                        capitalise
                        ? Character.toUpperCase( oldLetter )
                        : Character.toLowerCase( oldLetter );
                ca[ i ] = newLetter;
                changed |= ( newLetter != oldLetter );
                capitalise = false;
                firstCap = false;
                }// end if
            }// end for
        if ( changed )
            {
            s = new String( ca );
            }
        return s;
        }// end toBookTitleCase

    /**
     * Convert int to hex with lead zeroes
     *
     * @param h number you want to convert to hex
     *
     * @return 0x followed by unsigned hex 8-digit representation
     *
     * @noinspection WeakerAccess
     * @see #toString(Color)
     */
    public static String toHexString( int h )
        {
        String s = Integer.toHexString( h );
        if ( s.length() < 8 )
            {// pad on left with zeros
            s = "00000000".substring( 0, 8 - s.length() ) + s;
            }
        return "0x" + s;
        }

    /**
     * Convert an integer to a String, with left zeroes.
     *
     * @param i   the integer to be converted
     * @param len the length of the resulting string. Warning. It will chop the
     *            result on the left if it is too long.
     *
     * @return String representation of the int e.g. 007
     */
    public static String toLZ( int i, int len )
        {
        // Since String is final, we could not add this method there.
        String s = Integer.toString( i );
        if ( s.length() > len )
            {/* return rightmost len chars */
            return s.substring( s.length() - len );
            }
        else if ( s.length() < len )
            // pad on left with zeros
            {
            return "000000000000000000000000000000".substring( 0,
                                                               len
                                                               - s.length() )
                   + s;
            }
        else
            {
            return s;
            }
        }// end toLZ

    /**
     * Get #ffffff html hex number for a colour
     *
     * @param c Color object whose html colour number you want as a string
     *
     * @return # followed by 6 hex digits
     *
     * @noinspection WeakerAccess,SameParameterValue
     * @see #toHexString(int)
     */
    public static String toString( Color c )
        {
        String s = Integer.toHexString( c.getRGB() & 0xffffff );
        if ( s.length() < 6 )
            {// pad on left with zeros
            s = "000000".substring( 0, 6 - s.length() ) + s;
            }
        return '#' + s;
        }

    /**
     * Removes white space from beginning this string.
     *
     * @param s String to process. As always the original in unchanged.
     *
     * @return this string, with leading white space removed
     *
     * @noinspection WeakerAccess,WeakerAccess,WeakerAccess,SameParameterValue,WeakerAccess
     * @see #trimLeading(String,char)
     *      <p/>
     *      All characters that have codes less than or equal to
     *      <code>'&#92;u0020'</code> (the space character) are considered to be
     *      white space.
     */
    public static String trimLeading( String s )
        {
        if ( s == null )
            {
            return null;
            }
        int len = s.length();
        int st = 0;
        while ( ( st < len ) && ( s.charAt( st ) <= ' ' ) )
            {
            st++;
            }
        return ( st > 0 ) ? s.substring( st, len ) : s;
        }// end trimLeading

    /**
     * trim leading characters there are on a string matching a given
     * character.
     *
     * @param text text with possible trailing characters, possibly empty, but
     *             not null.
     * @param c    the trailing character of interest, usually ' ' or '\n'
     *
     * @return string with any of those trailing characters removed.
     *
     * @see #trimLeading(String)
     */
    public static String trimLeading( String text, char c )
        {
        int count = countLeading( text, c );
        // substring will optimise the 0 case.
        return text.substring( count );
        }

    /**
     * Removes white space from end this string.
     *
     * @param s String to process. As always the original in unchanged.
     *
     * @return this string, with trailing white space removed
     *
     * @see #trimTrailing(String,char)
     *      <p/>
     *      All characters that have codes less than or equal to
     *      <code>'&#92;u0020'</code> (the space character) are considered to be
     *      white space.
     */
    public static String trimTrailing( String s )
        {
        if ( s == null )
            {
            return null;
            }
        int len = s.length();
        int origLen = len;
        while ( ( len > 0 ) && ( s.charAt( len - 1 ) <= ' ' ) )
            {
            len--;
            }
        return ( len != origLen ) ? s.substring( 0, len ) : s;
        }// end trimTrailing

    /**
     * trim trailing characters there are on a string matching a given
     * character.
     *
     * @param text text with possible trailing characters, possibly empty, but
     *             not null.
     * @param c    the trailing character of interest, usually ' ' or '\n'
     *
     * @return string with any of those trailing characters removed.
     *
     * @see #trimTrailing(String)
     */
    public static String trimTrailing( String text, char c )
        {
        int count = countTrailing( text, c );
        // substring will optimise the 0 case.
        return text.substring( 0, text.length() - count );
        }

    // -------------------------- STATIC METHODS --------------------------

    /**
     * Quick replacement for Character.toLowerCase for use with English-only. It
     * does not deal with accented characters.
     *
     * @param c character to convert
     *
     * @return character converted to lower case
     */
    static char toLowerCase( char c )
        {
        return 'A' <= c && c <= 'Z' ? (char) ( c + ( 'a' - 'A' ) ) : c;
        }

    /**
     * Quick replacement for Character.toLowerCase for use with English-only. It
     * does not deal with accented characters.
     *
     * @param s String to convert
     *
     * @return String converted to lower case
     */
    static String toLowerCase( String s )
        {
        final char[] ca = s.toCharArray();
        final int length = ca.length;
        boolean changed = false;
        // can't use for:each since we need the index to set.
        for ( int i = 0; i < length; i++ )
            {
            final char c = ca[ i ];
            if ( 'A' <= c && c <= 'Z' )
                {
                // found a char that needs conversion.
                ca[ i ] = (char) ( c + ( 'a' - 'A' ) );
                changed = true;
                }
            }
        // give back same string if unchanged.
        return changed ? new String( ca ) : s;
        }

    /**
     * Quick replacement for Character.toUpperCase for use with English-only. It
     * does not deal with accented characters.
     *
     * @param c character to convert
     *
     * @return character converted to upper case
     */
    static char toUpperCase( char c )
        {
        return 'a' <= c && c <= 'z' ? (char) ( c + ( 'A' - 'a' ) ) : c;
        }

    /**
     * Quick replacement for Character.toUpperCase for use with English-only. It
     * does not deal with accented characters.
     *
     * @param s String to convert
     *
     * @return String converted to upper case
     */
    static String toUpperCase( String s )
        {
        final char[] ca = s.toCharArray();
        final int length = ca.length;
        boolean changed = false;
        // can't use for:each since we need the index to set.
        for ( int i = 0; i < length; i++ )
            {
            final char c = ca[ i ];
            if ( 'a' <= c && c <= 'z' )
                {
                // found a char that needs conversion.
                ca[ i ] = (char) ( c + ( 'A' - 'a' ) );
                changed = true;
                }
            }
        // give back same string if unchanged.
        return changed ? new String( ca ) : s;
        }

    // --------------------------- CONSTRUCTORS ---------------------------

    /**
     * StringTools contains only static methods.
     */
    private StringTools()
        {

        }

    // --------------------------- main() method ---------------------------

    /**
     * Test harness, used in debugging
     *
     * @param args not used
     */
    public static void main( String[] args )
        {
        if ( DEBUGGING )
            {
            System.out.println( condense( "   this  is   spaced.  " ) );
            System.out.println( trimLeading( "   trim   " ) );
            System.out.println( trimTrailing( "   trim   " ) );

            System.out.println( trimLeading( "*****t*r*i*m****", '*' ) );
            System.out.println( trimTrailing( "*****t*r*i*m****", '*' ) );

            System.out.println( toString( Color.red ) );
            System.out.println( toHexString( -3 ) );
            System.out.println( toHexString( 3 ) );
            System.out.println( countLeading( "none", ' ' ) );
            System.out.println( countLeading( "*one***", '*' ) );
            System.out.println( countLeading( "\n\ntw\n\n\no\n\n\n\n", '\n' ) );
            System.out.println( countTrailing( "none", ' ' ) );
            System.out.println( countTrailing( "***one*", '*' ) );
            System.out
                    .println( countTrailing( "\n\n\n\nt\n\n\n\nwo\n\n",
                                             '\n' ) );

            System.out.println( quoteSQL( "Judy's Place" ) );
            System.out.println( parseLongPennies( "$5.00" ) );
            System.out.println( parseLongPennies( "$50" ) );
            System.out.println( parseLongPennies( "50" ) );
            System.out.println( parseLongPennies( "$50-" ) );

            System.out.println( penniesToString( 0 ) );
            System.out.println( penniesToString( -1 ) );
            System.out.println( penniesToString( 20 ) );
            System.out.println( penniesToString( 302 ) );
            System.out.println( penniesToString( -100000 ) );
            System.out
                    .println( toBookTitleCase(
                            "handbook to HIGHER consciousness" ) );
            System.out
                    .println( toBookTitleCase( "THE HISTORY OF THE U.S.A." ) );
            System.out.println( toBookTitleCase( "THE HISTORY OF THE USA" ) );

            System.out.println( rightPad( "abc", 6, true ) + "*" );
            System.out.println( rightPad( "abc", 2, true ) + "*" );
            System.out.println( rightPad( "abc", 2, false ) + "*" );
            System.out.println( rightPad( "abc", 3, true ) + "*" );
            System.out.println( rightPad( "abc", 3, false ) + "*" );
            System.out.println( rightPad( "abc", 0, true ) + "*" );
            System.out.println( rightPad( "abc", 20, true ) + "*" );
            System.out.println( rightPad( "abc", 29, true ) + "*" );
            System.out.println( rightPad( "abc", 30, true ) + "*" );
            System.out.println( rightPad( "abc", 31, true ) + "*" );
            System.out.println( rightPad( "abc", 40, true ) + "*" );

            System.out.println( toUpperCase( 'q' ) );
            System.out.println( toUpperCase( 'Q' ) );
            System.out
                    .println( toUpperCase(
                            "The quick brown fox was 10 feet tall." ) );
            System.out
                    .println( toUpperCase(
                            "THE QUICK BROWN FOX WAS 10 FEET TALL." ) );
            System.out
                    .println( toUpperCase(
                            "the quick brown fox was 10 feet tall." ) );
            System.out.println( toLowerCase( 'q' ) );
            System.out.println( toLowerCase( 'Q' ) );
            System.out
                    .println( toLowerCase(
                            "The quick brown fox was 10 feet tall." ) );
            System.out
                    .println( toLowerCase(
                            "THE QUICK BROWN FOX WAS 10 FEET TALL." ) );
            System.out
                    .println( toLowerCase(
                            "the quick brown fox was 10 feet tall." ) );
            }
        }// end main
}
