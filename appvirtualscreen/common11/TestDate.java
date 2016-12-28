/*
TestDate: Validate BigDate to ensure it is bug-free.
Also demonstrate and  exercise the various methods.

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

// TestDate.java

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.TimeZone;

/**
 * Validate BigDate to ensure it is bug-free. Also demonstrate and exercise the
 * various methods.
 *
 * @author Roedy Green, Canadian Mind Products
 * @version 4.9, 2006-03-04
 */
public final class TestDate {

    // ------------------------------ FIELDS ------------------------------

    private static int count;

    private static final int MILLISECONDS__PER__DAY = 24 * 60 * 60 * 1000;

    // -------------------------- STATIC METHODS --------------------------

    /**
     * show off some of the things BigDate can do See also
     * com.mindprod.holidays.Holiday to compute various holidays and
     * com.mindprod.holidays.IsHoliday to tell if a given day is a holiday.
     *
     * @noinspection PointlessArithmeticExpression
     */
    private static void demoDate()
        {
        sep();
        {
        // Using traditional tools
        Date date = new Date();
        System.out.println( "Now, according to Java Date is : " + date );
        System.out
                .println( "That is "
                          + date.getTime()
                          + " milliseconds since 1970-01-01." );
        System.out
                .println(
                        "In contrast, BigDate works with pure dates, without times." );
        System.out
                .println( "Today, according to BigDate is : "
                          + BigDate.localToday()
                          + "." );
        }
        sep();
        {
        // Test Normalise
        System.out
                .println( "How an invalid date can be normalised:" );
        testNormalize( 2007, 2, 34 );
        }
        sep();
        {
        // what is today's date in American format
        System.out
                .println( "Today American style is " + BigDate.localToday()
                        .toDowMMDDYY() );
        }
        sep();
        {
        // what is today's date in iso format
        System.out
                .println( "Today ISO style is " + BigDate.localToday()
                        .toString() );
        }
        sep();
        {
        // What date is it in Greenwich?
        BigDate bigDate = BigDate.UTCToday();
        System.out
                .println( "Today in Greenwich England = "
                          + bigDate.toString()
                          + "." );
        /* 0=Sunday to 6=Saturday */
        int dayOfWeek = bigDate.getDayOfWeek();
        String[] daysOfTheWeek = {
                "Sunday",
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday"};
        String dayName = daysOfTheWeek[ dayOfWeek ];
        System.out.println( "In Greenwich it is " + dayName + "." );
        }
        sep();
        {
        // What day of the week is today?
        // display ISO 8601:1988 international standard format: yyyy-mm-dd.
        BigDate bigDate = BigDate.localToday();
        System.out.println( "Today = " + bigDate.toString() + "." );
        /* 0=Sunday to 6=Saturday */
        int dayOfWeek = bigDate.getDayOfWeek();
        String[] daysOfTheWeek = {
                "Sunday",
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday"};
        String dayName = daysOfTheWeek[ dayOfWeek ];
        System.out.println( "Today is " + dayName + "." );
        System.out
                .println( "Today is dayOfWeek number "
                          + bigDate.getDayOfWeek()
                          + " if Sunday=0." );
        System.out
                .println( "Today is dayOfWeek number "
                          + bigDate.getCalendarDayOfWeek()
                          + " if Sunday=1." );
        }
        pause();
        {
        // what season is it?
        String[] seasons = new String[] {"spring", "summer", "fall", "winter"};
        BigDate today = BigDate.localToday();
        System.out
                .println( "It is " + seasons[ today.getSeason() ] );
        System.out.println( "Season for months are: " );
        for ( int i = 1; i <= 12; i++ )
            {
            BigDate seasonStart = new BigDate( today.getYYYY(), i, 1 );
            System.out.println( i + " " + seasons[ seasonStart.getSeason() ] );
            }
        }
        pause();
        {
        // What Date is Cobol yyddd bigDate 99360?
        BigDate bigDate = new BigDate( 99 + 1900, 1, 360, BigDate.NORMALISE );
        System.out
                .println( "COBOL-style yyddd date 99360 = "
                          + bigDate.toString()
                          + "." );
        }
        sep();
        {
        // How do you convert Java timestamps into Windows timestamps.
        // Java timestamps use 64-bit milliseconds since 1970 GMT.
        // Windows timestamps use 64-bit value representing the number
        // of 100-nanosecond intervals since January 1, 1601.
        int windowsBase = BigDate.toOrdinal( 1601, 1, 1 );
        int javaBase = BigDate.toOrdinal( 1970, 1, 1 );
        int daysDifference = javaBase - windowsBase;
        // but Microsoft forgot that
        // 86,400,000 = 1000 * 60 * 60 * 24 = milliseconds per day
        long millisDifference = daysDifference * 86400000L;
        System.out
                .println( "To convert Java Timestamps to Windows Timestamps:" );
        System.out
                .println( "windows = ( java + "
                          + millisDifference
                          + "L ) * 10000" );
        }
        sep();
        {
        // Display a BigDate with SimpleDateFormat using local time
        BigDate bigDate = new BigDate( 1999, 12, 31 );
        Date date = bigDate.getLocalDate();
        SimpleDateFormat sdf =
                new SimpleDateFormat(
                        "EEEE yyyy/MM/dd G hh:mm:ss aa zz : zzzzzz" );
        sdf.setTimeZone( TimeZone.getDefault() );// local time
        String dateString = sdf.format( date );
        System.out.println( "Local SimpleDateFormat: " + dateString );
        }
        sep();
        {
        // Display a BigDate with SimpleDateFormat using UTC (Greenwich GMT)
        // time
        BigDate bigDate = new BigDate( 1999, 12, 31 );
        Date date = bigDate.getUTCDate();
        SimpleDateFormat sdf =
                new SimpleDateFormat(
                        "EEEE yyyy/MM/dd G hh:mm:ss aa zz : zzzzzz" );
        sdf.setTimeZone( TimeZone.getTimeZone( "GMT" ) );// GMT time
        String dateString = sdf.format( date );
        System.out.println( "UTC/GMT SimpleDateFormat: " + dateString );
        }
        sep();
        {
        // Display a BigDate with default-locale DateFormat using UTC
        // (Greenwich GMT) time
        BigDate bigDate = new BigDate( 1999, 12, 31 );
        Date date = bigDate.getUTCDate();
        DateFormat df = DateFormat.getDateInstance();
        df.setTimeZone( TimeZone.getTimeZone( "GMT" ) );// GMT time
        String dateString = df.format( date );
        System.out.println( "UTC/GMT locale DateFormat: " + dateString );
        }
        sep();
        {
        // What are the earliest and latest dates BigDate can handle
        BigDate bigDate = new BigDate( BigDate.MIN_ORDINAL );
        System.out
                .println( "Earliest BigDate possible is "
                          + bigDate.toString() );
        bigDate.setOrdinal( BigDate.MAX_ORDINAL );
        System.out
                .println( "Latest BigDate possible is " + bigDate.toString() );
        }
        sep();
        {
        // What is the last day of February in 2000, preferred method
        BigDate bigDate = new BigDate( 2000, 3, 1 );/* 2000, March 1 */
        bigDate.addDays( -1 );/* last day of Feb */
        System.out
                .println( "Last day in Feb 2000 = "
                          + bigDate.toString()
                          + "." );
        }
        sep();
        {
        // What is the last day of February in 2004, alternate less safe,
        // no-check method
        BigDate bigDate = new BigDate( 2004, 2 + 1, 1 - 1, BigDate.NORMALIZE );/*
                                                                                 * 2004,
                                                                                 * March
                                                                                 * 0
                                                                                 */
        System.out
                .println( "Last day in Feb 2004 = "
                          + bigDate.toString()
                          + "." );
        }
        sep();
        {
        // What is the last day of February in 2006, generic method.
        BigDate bigDate = new BigDate( 2006, 2, 1 );/* 2004, Feb 1 */
        bigDate.addDays( BigDate.daysInMonth( 2, 2006 ) - 1 );
        System.out
                .println( "Last day in Feb 2006 = "
                          + bigDate.toString()
                          + "." );
        }
        sep();
        {
        // What Date was it yesterday?
        BigDate bigDate = BigDate.localToday();
        bigDate.addDays( -1 );
        System.out.println( "Yesterday = " + bigDate.toString() + "." );
        }
        sep();
        {
        // What Date will it be tomorrow?
        BigDate bigDate = BigDate.localToday();
        bigDate.addDays( 1 );
        System.out.println( "Tomorrow = " + bigDate.toString() + "." );
        }
        sep();
        {
        // What Date will it be 1000 days from now?
        BigDate bigDate = BigDate.localToday();
        bigDate.addDays( 1000 );
        System.out.println( "Today+1000 days = " + bigDate.toString() + "." );
        }
        sep();
        {
        // What date will it be 50 months after 1998-01-03
        // You can think of this as how to turn a possibly invalid date into
        // the equivalent valid one.
        BigDate bigDate = new BigDate( 1998, 1 + 50, 3, BigDate.NORMALISE );
        System.out
                .println( "1998-01-03+50 months = "
                          + bigDate.toString()
                          + "." );
        }
        sep();
        {
        // What date will it be 60 months from today
        BigDate today = BigDate.localToday();
        BigDate bigDate =
                new BigDate( today.getYYYY(), today.getMM() + 60, today
                        .getDD(), BigDate.NORMALISE );
        System.out
                .println( "60 months from today will be "
                          + bigDate.toString()
                          + "." );
        }

        sep();
        {
        // Was 1830-02-29 a valid date?
        boolean ok = BigDate.isValid( 1830, 2, 29 );
        System.out
                .println( "True or false: 1830/02/29 was a valid date: "
                          + ok
                          + "." );
        }
        sep();
        {
        // how many seconds between Jan 1, 1900 0:00 and Jan 1, 1970 0:00
        // i.e. difference between SNTP and Java timestamp bases.
        // No leap seconds to worry about.
        long diffInDays =
                BigDate.toOrdinal( 1970, 1, 1 ) - BigDate.toOrdinal( 1900,
                                                                     1,
                                                                     1 );
        long diffInSecs = diffInDays * ( 24 * 60 * 60L );
        System.out
                .println( diffInDays
                          + " days or "
                          + diffInSecs
                          + " seconds between 1900 Jan 1 and 1970 Jan 1" );
        }
        sep();
        {
        // How Many days since 1970 is today ?
        BigDate bigDate = BigDate.localToday();
        System.out
                .println( "Today is "
                          + bigDate.getOrdinal()
                          + " days since 1970" );
        }
        sep();
        {
        // How Many days since 1970 is 2000/2/29?

        BigDate bigDate = new BigDate( 2000, 2, 29 );

        System.out
                .println( "1999-12-29 is "
                          + bigDate.getOrdinal()
                          + " days since 1970" );
        }
        sep();
        {
        // How Many milliseconds since 1970 is 1999/11/7
        BigDate bigDate = new BigDate( 1999, 11, 7 );
        System.out
                .println( "1999-11-07is "
                          + bigDate.getLocalTimeStamp()
                          + " milliseconds since 1970" );
        }
        sep();
        {
        // Is the year 2000 a leap year?
        System.out
                .println( "True or false: 2000 is a leap year: "
                          + BigDate.isLeap( 2000 )
                          + "." );
        }
        sep();

        {
        // What is the last day of this week, e.g. this Saturday?
        // display yyyy/mm/dd format.
        BigDate bigDate = BigDate.localToday();
        /* 0=Sunday to 6=Saturday */
        bigDate.addDays( 6 - bigDate.getDayOfWeek() );
        System.out.println( "This Saturday is " + bigDate.toString() + "." );
        }
        sep();
        {
        // In what week number did 1999/08/20 fall?

        BigDate bigDate = new BigDate( 1999, 8, 20 );
        System.out
                .println( "1999/08/20 fell in ISO week number "
                          + bigDate.getISOWeekNumber()
                          + " on ISO day of week number "
                          + bigDate.getISODayOfWeek()
                          + "." );

        System.out
                .println(
                        " According to BigDate.getWeekNumber that is week number "
                        + bigDate.getWeekNumber() );

        GregorianCalendar g = new GregorianCalendar( 1999, 8 - 1, 20 );
        System.out
                .println(
                        " According to java.util.Calendar that is week number "
                        + g.get( Calendar.WEEK_OF_YEAR )
                        + "." );
        }
        sep();
        {
        // what is the next business day?
        BigDate bigDate = BigDate.localToday();
        int interval;
        switch ( bigDate.getDayOfWeek() )
            {
            case 5/* Friday -> Monday */:
                interval = 3;
                break;

            case 6/* Saturday -> Monday */:
                interval = 2;
                break;

            case 0/* Sunday -> Monday */:
            case 1/* Monday -> Tuesday */:
            case 2/* Tuesday -> Wednesday */:
            case 3/* Wednesday -> Thursday */:
            case 4/* Thursday -> Friday */:
            default:
                interval = 1;
                break;
            }// end switch
        bigDate.addDays( interval );
        System.out.println( "Next business day = " + bigDate.toString() + "." );
        }
        sep();
        {
        // What Day of the Month Is The Second Thursday Of This Month?
        // This is when the Vancouver PC User Society (VPCUS) meets.
        // MacMillan planetarium.
        BigDate today = BigDate.localToday();
        int dayOfMonthOfSecondThursday = BigDate.nthXXXDay( 2
                                                            /* second */,
                                                            4
                                                            /* thursday */,
                                                            today.getYYYY(),
                                                            today
                                                                    .getMM() );
        System.out
                .println(
                        "VPCUS meets the second Thursday of the month. This month's meeting is on the "
                        + dayOfMonthOfSecondThursday
                        + "th." );
        }
        sep();
        {
        // What Day of the Month Is The third Thursday Of This Month?
        // This is when the Vancouver Apple User Society meets.
        // Scottish Cultural Center, 8886 Hudson, near the Oak St Bridge
        BigDate today = BigDate.localToday();
        int dayOfMonthOfThirdThursday = BigDate.nthXXXDay( 3
                                                           /* third */,
                                                           4
                                                           /* thursday */,
                                                           today.getYYYY(),
                                                           today.getMM() );

        System.out
                .println(
                        "Apples BC meets the third Thursday of the month. This month's meeting is on the "
                        + dayOfMonthOfThirdThursday
                        + "th." );
        }
        sep();
        {
        // When is the next issue of Xtra West magazine due out
        // It came out 2000-07-27, and comes out every two weeks.
        BigDate today = BigDate.localToday();
        BigDate first = new BigDate( 2000, 7, 27 );
        int days = today.getOrdinal() - first.getOrdinal();
        int fortnights = ( days + 13 ) / 14;// round up
        BigDate next = new BigDate( first.getOrdinal() + fortnights * 14 );
        System.out
                .println( "Next issue of Xtra West magazine is due out "
                          + next.toString()
                          + "." );
        }
        sep();
        {
        // When is J. McRee (Mac) Elrod's next potluck?
        // http://www.islandnet.com/~jelrod/gpl.html
        // the fourth Saturday of the month, jan, apr, jul, oct, every 3
        // months.
        BigDate today = BigDate.localToday();
        int year = today.getYYYY();
        // 1>1 2>4 3>4 4>4 5>7 6>7 7>7 8>10 9>10 10>10 11>1 12>1
        int month = ( ( ( today.getMM() + ( 3 - 1 - 1 ) ) / 3 ) * 3 ) % 12 + 1;
        BigDate when = new BigDate( BigDate.ordinalOfnthXXXDay( 4
                                                                /* fourth */,
                                                                6
                                                                /* saturday */,
                                                                year,
                                                                month ) );
        if ( when.getOrdinal() < today.getOrdinal() )
            {
            // we missed this month's potlock, get next one.
            month += 3;
            if ( month > 12 )
                {
                year++;
                month -= 12;
                when = new BigDate( BigDate.ordinalOfnthXXXDay( 4
                                                                /* fourth */,
                                                                6
                                                                /* saturday */,
                                                                year,
                                                                month ) );
                }
            }
        System.out
                .println(
                        "Mac's next potluck in on the fourth Saturday of the month, every 3 months,\nNext is on "
                        + when );
        }
        sep();
        {
        // What Day of the Month Is The Last Friday Of This Month?
        BigDate today = BigDate.localToday();
        int dayOfMonthOfLastFriday = BigDate.nthXXXDay( 5
                                                        /* last */,
                                                        5
                                                        /* friday */,
                                                        today.getYYYY(),
                                                        today.getMM() );

        System.out
                .println( "The last Friday of the month is on the "
                          + dayOfMonthOfLastFriday
                          + "th." );
        }
        sep();

        {
        // Precisely how old is Roedy Green, in years, months and days
        // Roedy was born on February 4, 1948.
        BigDate birthDate = new BigDate( 1948, 2, 4 );
        BigDate today = BigDate.localToday();
        int[] age = BigDate.age( birthDate, today );
        System.out
                .println( "Today Roedy is "
                          + age[ 0 ]
                          + " years and "
                          + age[ 1 ]
                          + " months and "
                          + age[ 2 ]
                          + " days old." );
        System.out
                .println( "or "
                          + ( today.getOrdinal() - birthDate.getOrdinal() )
                          + " days." );
        }
        sep();

        {
        // How old would John Lennon be, in years, months and days
        // Lennon was born on October 9, 1940.
        BigDate birthDate = new BigDate( 1940, 10, 9 );
        BigDate today = BigDate.localToday();
        int[] age = BigDate.age( birthDate, today );
        System.out
                .println( "Today John Lennon would be "
                          + age[ 0 ]
                          + " years and "
                          + age[ 1 ]
                          + " months and "
                          + age[ 2 ]
                          + " days old." );
        }
        sep();
        {
        // How long since John Lennon died, in years, months and days
        // Lennon was born on December 8, 1980.
        BigDate deathDate = new BigDate( 1980, 12, 8 );
        BigDate today = BigDate.localToday();
        int[] age = BigDate.age( deathDate, today );
        System.out
                .println( "It has been "
                          + age[ 0 ]
                          + " years and "
                          + age[ 1 ]
                          + " months and "
                          + age[ 2 ]
                          + " days since John Lennon was murdered." );
        }
        sep();
        {
        // How old was Bush's Press secretary Scott McClellan when he married
        // Jill Martinez on 2003-11-22.
        // Scotty was born on 1968-02-14.
        BigDate birthDate = new BigDate( 1968, 2, 14 );
        BigDate wedding = new BigDate( 2003, 11, 22 );
        int[] age = BigDate.age( birthDate, wedding );
        System.out
                .println( "Scott McClelland was "
                          + age[ 0 ]
                          + " years and "
                          + age[ 1 ]
                          + " months and "
                          + age[ 2 ]
                          + " days old when he married Jill Martinez." );
        }
        sep();

        {
        // Dr. Paul Norman was killed in plane crash
        // on the Sunday before Friday 2004-07-02. What date was that?
        BigDate deathDate = new BigDate( 2004, 7, 2 );
        deathDate
                .setOrdinal( deathDate.getOrdinal() - 5/* Friday */ + 0
                             /* Sunday */ );
        System.out
                .println( "Dr. Paul Norman was killed in a plane crash on "
                          + deathDate
                          + "." );
        }
        sep();

        {
        // How much time elapsed after Hitler died before George W. Bush was
        // born.

        BigDate hitler = new BigDate( 1945, 5, 1 );
        BigDate bush = new BigDate( 1946, 7, 6 );
        int[] age = BigDate.age( hitler, bush );
        System.out
                .println( "George W. Bush was born "
                          + age[ 0 ]
                          + " years and "
                          + age[ 1 ]
                          + " months and "
                          + age[ 2 ]
                          + " days after Hitler died on 1945-05-01" );
        System.out
                .println( "or "
                          + ( bush.getOrdinal() - hitler.getOrdinal() )
                          + " days." );
        }
        sep();

        {
        // Precisely how old was Hillary Clinton (born 1947-10-26)
        // when Sir Edmund Hillary climbed
        // mount everent in 1953-05-29 in years, months and days
        BigDate birthDate = new BigDate( 1947, 10, 26 );
        BigDate climb = new BigDate( 1953, 5, 29 );
        int[] age = BigDate.age( birthDate, climb );
        System.out
                .println( "Hillary Clinton was "
                          + age[ 0 ]
                          + " years and "
                          + age[ 1 ]
                          + " months and "
                          + age[ 2 ]
                          + " days old when Sir Edmund Hillary climbed Mount Everest on 1953-05-29" );
        System.out
                .println( "or "
                          + ( climb.getOrdinal() - birthDate.getOrdinal() )
                          + " days." );
        }
        sep();

        {
        // What day of the week was Jesus born on?
        // assume Jesus was born on December 25, 0001.
        // Scholars assure us he was NOT actually born then.
        int dayOfWeek = BigDate.dayOfWeek( BigDate.toOrdinal( 1, 12, 25 ) );
        String[] daysOfTheWeek = {
                "Sunday",
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday"};
        String dayName = daysOfTheWeek[ dayOfWeek ];
        System.out.println( "Jesus was born on a " + dayName + "." );
        }
        sep();
        {
        // How much time elapsed between December 25 4 BC and April 30, 28
        // AD
        BigDate fromDate = new BigDate( -4, 12, 25 );
        BigDate toDate = new BigDate( 28, 4, 30 );
        System.out.println( "How long did Jesus live?" );
        System.out
                .println( toDate.getOrdinal() - fromDate.getOrdinal()
                          + " days elapsed between 0004/12/25 BC and 0028/04/30 AD," );
        int[] age = BigDate.age( fromDate, toDate );
        System.out
                .println( "or put another way, "
                          + age[ 0 ]
                          + " years and "
                          + age[ 1 ]
                          + " months and "
                          + age[ 2 ]
                          + " days." );
        }
        sep();

        {
        // Precisely how old is George Bush Jr., in years, months and days
        // George Bush Jr. was born on July 6. 1946.
        BigDate birthDate = new BigDate( 1946, 7, 6 );
        BigDate today = BigDate.localToday();
        int[] age = BigDate.age( birthDate, today );
        System.out
                .println( "Today, George Bush Jr. is "
                          + age[ 0 ]
                          + " years and "
                          + age[ 1 ]
                          + " months and "
                          + age[ 2 ]
                          + " days old." );
        }
        sep();
        {
        // Precisely how old is Saddam Hussein, in years, months and days
        // Saddam Hussein was born on April 28, 1937.
        BigDate birthDate = new BigDate( 1937, 4, 28 );
        BigDate today = BigDate.localToday();
        int[] age = BigDate.age( birthDate, today );
        System.out
                .println( "Today, Saddam Hussein would be "
                          + age[ 0 ]
                          + " years and "
                          + age[ 1 ]
                          + " months and "
                          + age[ 2 ]
                          + " days old." );
        }
        sep();

        {
        // How Many days after bin Laden's last documented meeting with the
        // CIA
        // was the World Trade attack?
        BigDate fromDate = new BigDate( 2001, 7, 1 );// 2001-07-01
        BigDate toDate = new BigDate( 2001, 9, 11 );// 2001-09-11
        System.out
                .println( "The World Trade attack occurred "
                          + ( toDate.getOrdinal() - fromDate.getOrdinal() )
                          + " days after Osmama bin Laden's last meeting with the CIA." );
        }
        sep();
        {
        // How long ago was the World Trade Center destroyed?

        // The attack was 2001-09-11.
        BigDate hitDate = new BigDate( 2001, 9, 11 );
        BigDate today = BigDate.localToday();
        int[] age = BigDate.age( hitDate, today );
        System.out
                .println( "The World Trade center was destroyed "
                          + age[ 0 ]
                          + " years and "
                          + age[ 1 ]
                          + " months and "
                          + age[ 2 ]
                          + " days ago." );
        }
        sep();
        {
        // How long did it take Bush to give up trying to catch bin Laden.
        // The attack was 2001-09-11.
        // On 2002-03-13 Bush announced:
        // I don't know where he is. I have no idea and I really don't care.
        // It's not that important. It's not our priority.
        BigDate hitDate = new BigDate( 2001, 9, 11 );
        BigDate giveUpDate = new BigDate( 2002, 3, 13 );
        int[] patience = BigDate.age( hitDate, giveUpDate );
        System.out
                .println( "Bush officially gave up chasing bin Laden after "
                          + patience[ 0 ]
                          + " years,  "
                          + patience[ 1 ]
                          + " months and "
                          + patience[ 2 ]
                          + " days." );
        }
        sep();
        {
        // What was the date and day of week 72 days prior to Lincoln's
        // assassination?
        BigDate assassinDate = new BigDate( 1865, 4, 18 );// 1865-04-18
        BigDate priorDate = new BigDate( assassinDate.getOrdinal() - 72 );
        int dayOfWeek = priorDate.getDayOfWeek();
        final String[] daysOfTheWeek = {
                "Sunday",
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday"};
        System.out
                .println( "72 days prior to Lincoln's assassination was "
                          + priorDate.toString()
                          + ", a "
                          + daysOfTheWeek[ dayOfWeek ] );
        }
        sep();
        {
        // how long has it been since since John F. Kennedy was
        // assassinated?
        // John F. Kennedy was assassinated on November 22, 1963
        System.out
                .println( "John F. Kennedy was assassinated "
                          + ( BigDate.localToday().getOrdinal()
                              - BigDate.toOrdinal( 1963, 11, 22 ) )
                          + " days ago." );
        }
        sep();
        {
        // How long after Martin Luther King's assassination
        // was Bobby Kennedy assassinated?
        // MLK was assassinated April 4, 1968.
        // RK was assassinated June 4, 1968.
        System.out
                .println( "Robert Kennedy was assassinated "
                          + ( BigDate.toOrdinal( 1968, 6, 4 )
                              - BigDate.toOrdinal( 1968, 4, 4 ) )
                          + " days after Martin Luther King." );
        }
        sep();
        {
        // How old would Martin Luther King be if he were still alive today?
        // MLK was born 1929-01-15, celebrated 3rd monday in January
        BigDate mlk = new BigDate( 1929, 1, 15 );
        BigDate today = BigDate.localToday();
        int[] age = BigDate.age( mlk, today );
        System.out
                .println( "Martin Luther King would be "
                          + age[ 0 ]
                          + " years and "
                          + age[ 1 ]
                          + " months and "
                          + age[ 2 ]
                          + " days old today." );
        }

        sep();
        {
        // Mysterious missing days in the calendar.
        // Pope Gregory: 1582 Oct 4 Thursday, was followed immediately
        // by 1582 Oct 15 Friday dropping 10 days.
        //
        // British: 1752 Sep 2 Wednesday was followed immediately
        // by 1752 Sep 14 Thursday dropping 12 days.
        BigDate priorDate;
        BigDate afterDate;
        if ( BigDate.isBritish )
            {
            System.out
                    .println(
                            "According to the British/American/Canadian calendar, 12 days were dropped." );
            priorDate = new BigDate( 1752, 9, 2 );
            afterDate = new BigDate( 1752, 9, 14 );
            }
        else
            {
            System.out
                    .println(
                            "According to Pope Gregory's calendar, 10 days were dropped." );
            priorDate = new BigDate( 1582, 10, 4 );
            afterDate = new BigDate( 1582, 10, 15 );
            }
        final String[] daysOfTheWeek = {
                "Sunday",
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday"};

        int dayOfWeek = priorDate.getDayOfWeek();
        System.out
                .println( priorDate.toString()
                          + " was a "
                          + daysOfTheWeek[ dayOfWeek ]
                          + "." );
        dayOfWeek = afterDate.getDayOfWeek();
        System.out
                .println( afterDate.toString()
                          + " was a "
                          + daysOfTheWeek[ dayOfWeek ]
                          + "." );
        }
        pause();
        {
        // What is the Julian Day number of various dates 1970/1/1
        // Cross check these with the US Naval Observatory at:
        // http://aa.usno.navy.mil/AA/data/docs/JulianDate.html
        BigDate bigDate = new BigDate( 2000, 3, 20 );
        System.out
                .println(
                        "Astronomical Julian Propleptic calendar day for 2000/03/20 is "
                        + bigDate.getProplepticJulianDay() );
        bigDate = new BigDate( 1970, 1, 1 );
        System.out
                .println(
                        "Astronomical Julian Propleptic calendar day for 1970/01/01 is "
                        + bigDate.getProplepticJulianDay() );
        bigDate = new BigDate( 1600, 1, 1 );
        System.out
                .println(
                        "Astronomical Julian Propleptic calendar day for 1600/01/01 is "
                        + bigDate.getProplepticJulianDay() );
        bigDate = new BigDate( 1500, 1, 1 );
        System.out
                .println(
                        "Astronomical Julian Propleptic calendar day for 1500/01/01 is "
                        + bigDate.getProplepticJulianDay() );
        bigDate = new BigDate( 1, 1, 1 );
        System.out
                .println(
                        "Astronomical Julian Propleptic calendar day for 0001/01/01 is "
                        + bigDate.getProplepticJulianDay() );
        bigDate = new BigDate( -1, 12, 31 );
        System.out
                .println(
                        "Astronomical Julian Propleptic calendar day for -0001/12/31 is "
                        + bigDate.getProplepticJulianDay() );
        bigDate = new BigDate( -6, 1, 1 );
        System.out
                .println(
                        "Astronomical Julian Propleptic calendar day for -0006/01/01 is "
                        + bigDate.getProplepticJulianDay() );
        bigDate = new BigDate( -4713, 1, 1 );
        System.out
                .println(
                        "Astronomical Julian Propleptic calendar day for -4713/01/01 is "
                        + bigDate.getProplepticJulianDay() );
        }
        pause();
        {
        // How long till Christmas? ( Sun's way.)
        // this code will give a negative number just after Christmas.
        System.out
                .println( "Your child asks, how long is it until Christmas?" );
        System.out
                .println(
                        "Here are the possible answers you might give to an American child" );

        GregorianCalendar now = new GregorianCalendar();
        int thisYear = now.get( Calendar.YEAR );

        // You may open presents at 7 AM December 25, local time
        System.out
                .println(
                        "You may open your presents at 7 AM Christmas morning." );

        GregorianCalendar christmas =
                new GregorianCalendar( thisYear, 12 - 1, 25, 7, 0, 0 );

        // millis since 1970 Jan 1
        long christmasTimeStamp = christmas.getTime().getTime();

        long nowTimeStamp = now.getTime().getTime();

        double dayUnitsDiff =
                ( christmasTimeStamp - nowTimeStamp )
                / (double) MILLISECONDS__PER__DAY;

        System.out
                .println( "1. It is "
                          + dayUnitsDiff
                          + " day units of 24 hours until you may open your presents." );

        System.out
                .println( "2. It is "
                          + Math.ceil( dayUnitsDiff )
                          + " day units of 24 hours rounded up until you may open your presents." );

        System.out
                .println( "3. It is "
                          + Math.round( dayUnitsDiff )
                          + " day units of 24 hours rounded until you may open your presents." );

        System.out
                .println( "4. It is "
                          + Math.floor( dayUnitsDiff )
                          + " day units of 24 hours rounded down until you may open your presents." );

        int gmtChristmasOrdinal =
                (int) ( christmasTimeStamp / MILLISECONDS__PER__DAY );
        int gmtNowOrdinal = (int) ( nowTimeStamp / MILLISECONDS__PER__DAY );
        int gmtDiffInDays = gmtChristmasOrdinal - gmtNowOrdinal;
        System.out
                .println( "5. Children in Greenwich have "
                          + gmtDiffInDays
                          + " sleeps (midnight crossings) to go until\n"
                          + "you may open your presents.\n"
                          + "They may open theirs even sooner." );

        // For children living in the USA, the timezone offset will be
        // negative.
        // days since 1970 Jan 1
        // There is no GregorianCalender.getOffset or
        // get(ADJUSTED_ZONE_OFFSET);
        int christmasZoneOffset =
                christmas.get( Calendar.ZONE_OFFSET )
                + now.get( Calendar.DST_OFFSET );

        int localChristmasOrdinal =
                (int) ( ( christmasTimeStamp + christmasZoneOffset )
                        / MILLISECONDS__PER__DAY );
        int nowZoneOffset =
                now.get( Calendar.ZONE_OFFSET )
                + now.get( Calendar.DST_OFFSET );
        int localNowOrdinal =
                (int) ( ( nowTimeStamp + nowZoneOffset )
                        / MILLISECONDS__PER__DAY );
        int localDiffInDays = localChristmasOrdinal - localNowOrdinal;
        System.out
                .println( "6. You have "
                          + localDiffInDays
                          + " sleeps (midnight crossings) to go." );
        }
        {
        // how long till Christmas? ( with BigDate. )
        // this code will give a negative number just after Christmas.

        BigDate today = BigDate.localToday();
        BigDate christmas = new BigDate( today.getYYYY(), 12, 25 );
        System.out
                .println( "7. Uncle Roedy's Bigdate says it is "
                          + ( christmas.getOrdinal() - today.getOrdinal() )
                          + " sleeps until Christmas." );

        int[] age = BigDate.age( today, christmas );
        System.out
                .println( "8. Or put another way "
                          + age[ 0 ]
                          + " years and "
                          + age[ 1 ]
                          + " months and "
                          + age[ 2 ]
                          + " days to go." );
        }
        }// end demoDate

    /**
     * Display an error message and exit.
     *
     * @param why the error message explaining why we are terminating.
     */
    private static void fail( String why )
        {
        System.out.println( why );
        pause();
        System.exit( 99 );
        }

    /**
     * Pauses the display until the user hits enter.
     */
    private static void pause()
        {
        count = 0;
        System.out.println();

        try
            {
            for ( int available = System.in.available(); available
                                                         > 0; available-- )
                {
                // discard pending chars
                System.in.read();
                }
            System.out.println( "Hit Enter to continue" );

            // might get cr, lf, cr lf. Ignored previous input from InputStream
            System.in.read();// wait for user to hit Enter, discard result
            }
        catch ( IOException e )
            {
            System.out.println( "keyboard failed" );
            }
        System.out.println();
        System.out.println();
        System.out.println();
        }// end pause

    /**
     * Separates items
     */
    private static void sep()
        {
        // pause after every three items
        count++;
        if ( count >= 3 )
            {
            // will reset to 0
            pause();
            }
        else
            {
            System.out.println();
            }
        }

    /**
     * Display a date and its ordinal on System.out.
     *
     * @param desc a description of the date to label it.
     * @param yyyy the year to display.
     * @param mm   the month to display.
     * @param dd   the day of the month to display.
     */
    private static void show( String desc, int yyyy, int mm, int dd )
        {
        System.out.println( desc + BigDate.toOrdinal( yyyy, mm, dd ) );
        }

    /**
     * Test that the age routine generates plausible results.
     *
     * @param fromDate    earliest date to test.
     * @param toDate      last date to test.
     * @param samples     how many random pairs to test.
     * @param showDetails true if want details of individual tests
     */
    private static void testAge( BigDate fromDate,
                                 BigDate toDate,
                                 int samples,
                                 boolean showDetails )
        {
        // test with a random sampling of date pairs.

        System.out
                .println( "Testing age from: "
                          + fromDate.toString()
                          + " to: "
                          + toDate.toString()
                          + " using "
                          + samples
                          + " random samples." );

        Random wheel = new Random();
        BigDate birth = new BigDate();
        BigDate asof = new BigDate();
        // date after which our approximations hold.
        int plausibleYear = BigDate.isBritish ? 1752 : 1582;

        // calculate transform to take result of Random.nextInt into our range.
        int base = fromDate.getOrdinal();
        int divisor = toDate.getOrdinal() - base + 1;
        if ( divisor < 2 )
            {
            divisor = 2;
            }

        // Any difference 4 or less is reasonable because of the way months have
        // unequal lengths. Only flag really bad cases. There should not be any.
        int worstApprox = 4;
        for ( int i = 0; i < samples; i++ )
            {
            // Generate a pair of random dates in range. Might not be in order.
            int ord1 = ( wheel.nextInt() & Integer.MAX_VALUE ) % divisor + base;
            int ord2 = ( wheel.nextInt() & Integer.MAX_VALUE ) % divisor + base;
            // put them in order
            if ( ord1 > ord2 )
                {
                int temp = ord1;
                ord1 = ord2;
                ord2 = temp;
                }
            birth.set( ord1 );
            asof.set( ord2 );

            int[] age = BigDate.age( birth, asof );

            if ( showDetails )
                {
                System.out.print( "birth: " + birth.toString() + " " );
                System.out.print( "as of: " + asof.toString() + " " );
                System.out
                        .println( age[ 0 ]
                                  + " years and "
                                  + age[ 1 ]
                                  + " months and "
                                  + age[ 2 ]
                                  + " days old." );
                }

            if ( age[ 0 ] < 0 )
                {
                fail( "Negative age in years" );
                }
            if ( age[ 1 ] < 0 )
                {
                fail( "Negative age in months" );
                }
            if ( age[ 2 ] < 0 )
                {
                fail( "Negative age in days" );
                }

            if ( age[ 1 ] > 11 )
                {
                fail( "Age > 11 months" );
                }
            if ( age[ 2 ] > 31 )
                {
                fail( "Age > 31 days" );
                }
            if ( age[ 0 ] > 0
                 && age[ 1 ] > 0
                 && age[ 2 ] > 0
                 && birth.getYYYY() > plausibleYear )
                {
                // plausibility test, approximation works after Gregorian
                // instituted.
                int roughAgeInDays =
                        (int) Math.round( age[ 0 ] * 365.2425d
                                          + age[ 1 ] * 30.436875d
                                          + age[ 2 ] );
                int exactAgeInDays = asof.getOrdinal() - birth.getOrdinal();
                int approx = Math.abs( roughAgeInDays - exactAgeInDays );
                if ( approx > worstApprox )
                    {
                    worstApprox = approx;
                    System.out.print( "birth: " + birth.toString() + " " );
                    System.out.print( "as of: " + asof.toString() + " " );
                    System.out
                            .println( age[ 0 ]
                                      + " years and "
                                      + age[ 1 ]
                                      + " months and "
                                      + age[ 2 ]
                                      + " days old. Differs from approx by "
                                      + approx
                                      + "." );
                    }// end if got a new worst approximation
                }// end if plausibility test
            }// end for
        }// end testAge

    /**
     * Check for bugs in toOrdinal and toGregorian.
     *
     * @param fromYear the first year to test.
     * @param toYear   the last year to test.
     */
    private static void testDate( int fromYear, int toYear )
        {
        int gotOrdinal, expectedOrdinal;
        BigDate gotGregorian;
        int gotYear, expectedYear;
        int gotMonth, expectedMonth;
        int gotDay, expectedDay;

        System.out
                .println( "Testing toOrdinal and toGregorian "
                          + fromYear
                          + " .. "
                          + toYear );

        System.out.println( "This could take a while..." );
        try
            {
            expectedOrdinal = BigDate.toOrdinal( fromYear, 1, 1 );

            for ( expectedYear = fromYear; expectedYear
                                           <= toYear; expectedYear++ )
                {
                if ( expectedYear % 10000 == 0 )
                    {
                    System.out.println( "reached " + expectedYear );
                    }

                for ( expectedMonth = 1; expectedMonth <= 12; expectedMonth++ )
                    {
                    for ( expectedDay = 1; expectedDay <= 31; expectedDay++ )
                        {
                        if ( BigDate.isValid( expectedYear,
                                              expectedMonth,
                                              expectedDay ) )
                            {
                            // test toOrdinal
                            gotOrdinal =
                                    BigDate.toOrdinal( expectedYear,
                                                       expectedMonth,
                                                       expectedDay );
                            if ( gotOrdinal != expectedOrdinal )
                                {
                                fail( "toOrdinal oops "
                                      + " expected: "
                                      + " YYYY:"
                                      + expectedYear
                                      + " MM:"
                                      + expectedMonth
                                      + " DD:"
                                      + expectedDay
                                      + " JJJJ:"
                                      + expectedOrdinal
                                      + " got: "
                                      + " JJJJ:"
                                      + gotOrdinal );
                                }// end if

                            // test toGregorian
                            gotGregorian = new BigDate( expectedOrdinal );
                            gotYear = gotGregorian.getYYYY();
                            gotMonth = gotGregorian.getMM();
                            gotDay = gotGregorian.getDD();

                            if ( ( gotYear != expectedYear )
                                 || ( gotMonth != expectedMonth )
                                 || ( gotDay != expectedDay ) )
                                {
                                fail( "toGregorian failed"
                                      + " expected: "
                                      + " JJJJ:"
                                      + expectedOrdinal
                                      + " YYYY:"
                                      + expectedYear
                                      + " MM:"
                                      + expectedMonth
                                      + " DD:"
                                      + expectedDay
                                      + " got: "
                                      + " YYYY:"
                                      + gotYear
                                      + " MM:"
                                      + gotMonth
                                      + " DD:"
                                      + gotDay );
                                }// end if

                            // increment only for valid dates
                            expectedOrdinal = gotOrdinal + 1;
                            }// end if isValid
                        }
                    }
                }// all three for loops
            }// end try
        catch ( IllegalArgumentException e )
            {
            fail( "test failed " + e.getMessage() );
            }
        System.out
                .println(
                        "BigDate toOrdinal and toGregorian test completed successfully" );
        }// end testDate

    /**
     * Test getISOWeekNumber and getISODayOfWeek.
     *
     * @param fromYear first year to test.
     * @param toYear   last year to test.
     *
     * @noinspection SameParameterValue
     */
    private static void testISOWeekNumber( int fromYear, int toYear )
        {
        BigDate start = new BigDate( fromYear, 1, 1 );
        BigDate stop = new BigDate( toYear, 12, 31 );
        System.out
                .println( "Testing getISOWeekNumber and getISODayOfWeek "
                          + start.toString()
                          + " .. "
                          + stop.toString() );

        BigDate candidate = new BigDate( start );
        int expectedWeekNumber = candidate.getISOWeekNumber();
        int expectedDayOfWeek = candidate.getISODayOfWeek();

        for ( int i = start.getOrdinal(); i < stop.getOrdinal(); i++ )
            {
            candidate.setOrdinal( i );
            int weekNumber = candidate.getISOWeekNumber();
            int dayOfWeek = candidate.getISODayOfWeek();
            if ( weekNumber != expectedWeekNumber )
                {
                if ( expectedWeekNumber >= 53 && weekNumber == 1 )
                    {
                    expectedWeekNumber = 1;
                    }
                else
                    {
                    fail( "getISOWeekNumber bug at " + candidate.toString() );
                    }
                }
            if ( dayOfWeek != expectedDayOfWeek )
                {
                fail( "getISoDayOfWeek bug at " + candidate.toString() );
                }
            expectedDayOfWeek = dayOfWeek + 1;
            if ( expectedDayOfWeek == 8 )
                {
                expectedDayOfWeek = 1;
                expectedWeekNumber++;
                }
            }
        }// end testIOSWeekNumber

    /**
     * Test Normalize by displaying how an invalid date is converted.
     *
     * @param yyyy the year to test, may be negative or bigger than 4 digits.
     * @param mm   the month to test.
     * @param dd   the day of the month to test.
     *
     * @noinspection SameParameterValue
     */
    private static void testNormalize( int yyyy, int mm, int dd )
        {
        BigDate g = new BigDate( yyyy, mm, dd, BigDate.NORMALIZE );

        System.out
                .println( " input: "
                          + " YYYY:"
                          + yyyy
                          + " MM:"
                          + mm
                          + " DD:"
                          + dd );
        System.out
                .println( "output: "
                          + " YYYY:"
                          + g.getYYYY()
                          + " MM:"
                          + g.getMM()
                          + " DD:"
                          + g.getDD()
                          + " ORD:"
                          + g.getOrdinal()
                          + " DOW:"
                          + g.getDayOfWeek()
                          + " DDD:"
                          + g.getDDD()
                          + " TIMESTAMP:"
                          + g.getUTCTimeStamp() );
        }// end testNormalize

    // --------------------------- main() method ---------------------------

    /**
     * Run a series of tests on BigDate to ensure it is bug-free.
     *
     * @param args command line arguments, not used.
     */
    public static void main( String[] args )
        {
        demoDate();

        pause();

        show( "NULL date = ", 0, 0, 0 );
        show( "Jan 01, 999999BC = ", -999999, 1, 1 );
        show( "Jan 01, 04BC = ", -4, 1, 1 );
        show( "Dec 31, 01BC = ", -1, 12, 31 );
        show( "Jan 01, 01AD = ", 1, 1, 1 );
        show( "Oct 04, 1582 = ", 1582, 10, 4 );
        show( "Oct 15, 1582 = ", 1582, 10, 15 );
        show( "Jan 01, 1860 = ", 1860, 1, 1 );
        show( "Dec 31, 1969 = ", 1969, 12, 31 );
        show( "Jan 01, 1970 = ", 1970, 1, 1 );
        show( "Jan 02, 1970 = ", 1970, 1, 2 );
        show( "Jun 18, 1998 = ", 1998, 6, 18 );
        show( "Dec 31, 999999AD = ", 999999, 12, 31 );

        pause();

        testAge( new BigDate( 1990, 1, 1 ),
                 new BigDate( 2006, 12, 31 ),
                 10,
                 true );
        testAge( new BigDate( -999999, 1, 1 ),
                 new BigDate( 999999, 12, 31 ),
                 1000000,
                 false );
        testAge( new BigDate( 1900, 1, 1 ),
                 new BigDate( 2050, 12, 31 ),
                 1000000,
                 false );
        // after all danger of goofy missing days
        testISOWeekNumber( 1800, 9999 );

        pause();

        testDate( -2100, +2100 );
        testDate( -999999, -999000 );
        testDate( +999000, +999999 );
        testDate( -99999, +99999 );

        pause();
        }// end main
}
