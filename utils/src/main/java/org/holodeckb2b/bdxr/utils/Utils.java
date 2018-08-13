/*
 * Copyright (C) 2018 The Holodeck B2B Team
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.holodeckb2b.bdxr.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;

/**
 * Contains some generic helper methods.
 *
 * @author Sander Fieten (sander at chasquis-services.com)
 */
public final class Utils {

    /**
     * Transform a {@link Date} object to a {@link String} formatted according to
     * the specification of the <code>dateTime</code> datatype of XML schema.<br>
     * See <a href="http://www.w3.org/TR/xmlschema-2/#dateTime">section 3.2.7 of the XML
     * Specification</a> for details.
     *
     * @param   date  The date as Calendar object to convert to String.
     * @return  The date as an <code>xs:dateTime</code> formatted String
     *          or <code>null</code> when date object was <code>null</code>
     */
    public static String toXMLDateTime(final Date date) {
        if (date == null)
            return null;

        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXX");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(date);
    }

    /**
     * Parses a {@link String} for XML dateTime (see <a href="http://www.w3.org/TR/xmlschema-2/#dateTime">section 3.2.7
     * of the XML Specification</a>) and return a {@link Date} object when a valid date is found.
     *
     * @param   xmlDateTimeString   The String that should contain the <code>xs:dateTime</code> formatted date
     * @return  A {@link Date} object for the parsed date
     * @throws  ParseException on date time parsing error
     */
    public static Date fromXMLDateTime(final String xmlDateTimeString) throws ParseException {
        String s = xmlDateTimeString;
        String f = null;

        // If there is no text, there is no date
        if (s == null || s.isEmpty())
            return null;

        // Check whether UTC is specified as timezone using "Z" and replace with "+00:00" if yes
        if (s.indexOf("Z") > 0)
            s = s.replace("Z", "+00:00");

        // Check if value contains fractional seconds
        int i = s.indexOf(".");
        if (i > 0) {
            // Contains fractional seconds, limit to milliseconds (3 digits)
            // Get start of timezone which is indicated by either "+" or "-"
            // Because "-" also occurs in the date part, only start looking for it from start of the time part
            int z = Math.max(s.indexOf("+"), s.indexOf("-", s.indexOf("T")));
            z = (z == -1 ? s.length() : z); // If no timezone included, fractional seconds are last part of string
            // Limit the number of digits to extract to 3 but use less if no more available
            final int S = Math.min(z-i-1, 3);
            s = s.substring(0, i + S + 1) + s.substring(z);
            // Remove ':' from timezone
            i = s.indexOf(":", i + S + 1);
            s = s.substring(0, i) + s.substring(i+1);
            // Set format
            f = "yyyy-MM-dd'T'HH:mm:ss." + "SSS".substring(0, S)  +"Z";
        } else {
            // No fractional seconds, just remove the ':' from the timezone indication (when it is there)
            if (s.length() > 22 ) {
                s = s.substring(0, 22) + s.substring(23);
                // Set format
                f = "yyyy-MM-dd'T'HH:mm:ssZ";
            } else {
                // Only set format
                f = "yyyy-MM-dd'T'HH:mm:ss";
            }
        }

        return new SimpleDateFormat(f).parse(s);
    }


    /**
     * Get the key from a Map entry by searching for the value.
     * <p>The Map must contain a one-to-one relationship. If not this method simply returns the key of the first entry
     * found.
     *
     * @param map       The Map containing key and value pairs.
     * @param value     The value to search the corresponding key value for.
     * @return          The key value corresponding to the provided value, or <code>null</code> if nothing is found
     */
    public static String getKeyByValue(final Map<String, String> map, final String value) {
        for (final Entry<String, String> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Compares two strings
     *
     * @param s     The first input string
     * @param p     The second input string
     * @return      -2 when both strings are non-empty and their values are different,<br>
     *              -1 when both strings are empty,<br>
     *              0  when both strings are non-empty but equal,<br>
     *              1  when only the first string is non-empty,<br>
     *              2  when only the second string is non-empty
     */
    public static int compareStrings(final String s, final String p) {
        if (isNullOrEmpty(s)) {
            if (!isNullOrEmpty(p)) {
                return 2;
            } else {
                return -1;
            }
        } else if (!isNullOrEmpty(p)) {
            if (s.equals(p)) {
                return 0;
            } else {
                return -2;
            }
        } else {
            return 1;
        }
    }

    /**
     * Check whether the given String is non-empty and returns its value if true, otherwise the supplied default will
     * be returned.
     *
     * @param value         The String to check
     * @param defaultValue  The default value to use if the given string is <code>null</code> or empty
     * @return      <code>value</code> if it is a non-empty string,<br>
     *              <code>defaultValue</code> otherwise
     */
    public static String getValue(final String value, final String defaultValue) {
        return (value != null && !value.isEmpty() ? value : defaultValue);
    }

    /**
     * Checks whether the given String is <code>null</code> or is an empty string, i.e. does not contain any other
     * characters then whitespace.
     *
     * @param s     The string to check
     * @return      <code>true</code> if <code>s == null || s.trim().isEmpty() == true</code>,<br>
     *              <code>false</code> otherwise
     */
    public static boolean isNullOrEmpty(final String s) {
        return s == null || s.trim().isEmpty();
    }

    /**
     * Checks whether the given Collection is <code>null</code> or is empty.
     *
     * @param c     The Collection to check
     * @return      <code>true</code> if <code>c == null || c.isEmpty() == true</code>,<br>
     *              <code>false</code> otherwise
     */
    public static boolean isNullOrEmpty(final Collection<?> c) {
        return c == null || c.isEmpty();
    }

    /**
     * Checks whether the given Map is <code>null</code> or is empty.
     *
     * @param m     The Map to check
     * @return      <code>true</code> if <code>m == null || m.isEmpty() == true</code>,<br>
     *              <code>false</code> otherwise
     */
    public static boolean isNullOrEmpty(final Map<?,?> m) {
        return m == null || m.isEmpty();
    }

    /**
     * Checks whether the given Iterator is <code>null</code> or does not contain any more objects.
     *
     * @param i     The Iterator to check
     * @return      <code>true</code> if <code>i == null || i.hasNext() == true</code>,<br>
     *              <code>false</code> otherwise
     */
    public static boolean isNullOrEmpty(final Iterator<?> i) {
        return i == null || !i.hasNext();
    }

    /**
     * Gets the root cause of the exception by traversing the exception stack and returning the
     * last available exception in it.
     *
     * @param t     The {@link Throwable} object to get the root cause for. May not be <code>null</code>
     *              because otherwise it crashes with an ArrayIndexOutOfBoundsException
     * @return      The root cause (note that this can be the throwable itself)
     */
    public static Throwable getRootCause(final Throwable t) {
        final List<Throwable> exceptionStack = getCauses(t);
        return exceptionStack.get(exceptionStack.size() - 1);
    }

    /**
     * Gets the exception stack of an exception, i.e. the list of all exception that where registered as causes.
     *
     * @param t     The {@link Throwable} object to get the exception stack for
     * @return      A list of {@link Throwable} object with the first item being the exception itself and the last
     *              item the root cause.
     */
    public static List<Throwable> getCauses(final Throwable t) {
        final List<Throwable> exceptionStack = new ArrayList<>();
        Throwable i = t;
        while (i != null) {
            exceptionStack.add(i);
            i = i.getCause();
        }

        return exceptionStack;
    }

    /**
     * Creates a string for inclusion in logs containing the list of exceptions including their exception messages that
     * caused the given exception.
     * <p>The format of the generated string is with the exception message only included if one is provided by the
     * exception:
     * <pre>
     * «Exception class name» : «exception message»
     *      Caused by: «Exception class name» : «exception message»
     *      Caused by: «Exception class name» : «exception message»
     *      ...
     * </pre>
     *
     * @param t The exception that occurred
     * @return  The list of exceptions that caused this exception including their error message
     */
    public static String getExceptionTrace(final Throwable t) {
        final StringBuffer r = new StringBuffer();

        r.append(t.getClass().getSimpleName());
        if (!isNullOrEmpty(t.getMessage()))
            r.append(" : ").append(t.getMessage());
        Throwable cause = t.getCause();
        if (cause != null) {
            do {
                r.append('\n').append('\t').append("Caused by: ").append(cause.getClass().getSimpleName());
                if (!isNullOrEmpty(cause.getMessage()))
                    r.append(" : ").append(cause.getMessage());
                cause = cause.getCause();
            } while (cause != null);
        }

        return r.toString();
    }

    /**
     * Compare any two objects in a <code>null</code> safe manner.
     * <p>If both objects are <code>null</code> they are interpreted as being equal. If only one object is <code>null
     * </code> they are different. If both objects are non-<code>null</code> than the {@link #equals(Object)} method is
     * invoked on them.
     *
     * @param o1    First object. May be <code>null</code>.
     * @param o2    Second object. May be <code>null</code>.
     * @param <T>
     * @return      <code>true</code> if both are <code>null</code> or if both are equal,<br>
     *              <code>false</code> otherwise
     */
    public static <T> boolean nullSafeEqual (final T o1, final T o2) {
        return o1 == null ? o2 == null : o1.equals (o2);
    }

    /**
     * Executes a case insensitive and <code>null</code> safe comparison between two {@link String} objects.
     * <p>If both passed Strings are <code>null</code> they are interpreted as being equal. If only one object is <code>
     * null</code> they are interpreted as different. If both objects are non-<code>null</code> than the {@link
     * String#equalsIgnoreCase(String)} method is invoked on them.
     *
     * @param s1    First String. May be <code>null</code>.
     * @param s2    Second String. May be <code>null</code>.
     * @return      <code>true</code> if both are <code>null</code> or if both are equal ignoring the case,<br>
     *              <code>false</code> otherwise
     */
    public static boolean nullSafeEqualIgnoreCase (final String s1, final String s2) {
        return s1 == null ? s2 == null : s1.equalsIgnoreCase (s2);
    }
}
