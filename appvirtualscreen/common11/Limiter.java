/*
Limiter: simple methods to corral values into bounds.

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
*/
package com.mindprod.common11;

/**
 * Limiter.java Miscellaneous static methods to check and constrain to within
 * bounds.
 *
 * @author Roedy Green, Canadian Mind Products
 * @version 1.6 2005-07-14
 */
public final class Limiter {

    // ------------------------------ FIELDS ------------------------------

    /**
     * true if you want extra debugging output and test code
     */
    static final boolean DEBUGGING = false;

    // -------------------------- PUBLIC STATIC METHODS --------------------------

    /**
     * Caps the max value, ensuring it does not go too high. alias for min.
     *
     * @param v    the value
     * @param high the high bound above which v cannot go.
     *
     * @return the lesser of v and high.
     *
     * @see Math#min(int,int)
     */
    public static int cap( int v, int high )
        {
        if ( v > high )
            {
            return high;
            }
        else
            {
            return v;
            }
        }// end cap

    /**
     * Corrals a value back into safe bounds.
     *
     * @param v    the value
     * @param low  the low bound below which v cannot go.
     * @param high the high bound above which v cannot go.
     *
     * @return low if v < low, high if v > high, but normally just v.
     */
    public static int corral( int v, int low, int high )
        {
        if ( v < low )
            {
            return low;
            }
        else if ( v > high )
            {
            return high;
            }
        else
            {
            return v;
            }
        }// end corral

    /**
     * Ensures a value does not go too low. alias for max
     *
     * @param v   the value
     * @param low the low bound below which v cannot go.
     *
     * @return the greater of v and low.
     *
     * @see Math#max(int,int)
     */
    public static int hem( int v, int low )
        {
        if ( v < low )
            {
            return low;
            }
        else
            {
            return v;
            }
        }// end hem

    // --------------------------- CONSTRUCTORS ---------------------------

    /**
     * Limiter contains only static methods.
     */
    private Limiter()
        {

        }
}
