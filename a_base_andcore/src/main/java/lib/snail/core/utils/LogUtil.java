package lib.snail.core.utils;

import android.util.Log;

/**
 * Log Utils
 * 2019-3-22 levent
 */
public class LogUtil {
        public static  boolean DEBUG = true;

        public static void v (String tag, String mess ) {
                if ( DEBUG ) { Log.v ( tag, mess ); }
        }

        public static void d (String tag, String mess ) {
                if ( DEBUG ) { Log.d ( tag, mess ); }
        }

        public static void i (String tag, String mess ) {
                if ( DEBUG ) { Log.i ( tag, mess ); }
        }

        public static void w (String tag, String mess ) {
                if ( DEBUG ) { Log.w ( tag, mess ); }
        }

        public static void e (Exception e) {
                if ( DEBUG ) {
                        e.printStackTrace();
                }
        }

        public static void v ( String mess ) {
                if ( DEBUG ) { Log.v ( getTag ( ), mess ); }
        }

        public static void d ( String mess ) {
                if ( DEBUG ) { Log.d ( getTag ( ), mess ); }
        }

        public static void i ( String mess ) {
                if ( DEBUG ) {
                        Log.i ( getTag ( ), mess );
                }
        }

        public static void w ( String mess ) {
                if ( DEBUG ) { Log.w ( getTag ( ), mess ); }
        }

        private static String getTag ( ) {
                StackTraceElement[] trace = new Throwable( ).fillInStackTrace ( )
                        .getStackTrace ( );
                String callingClass = "";
                for ( int i = 2 ; i < trace.length ; i++ ) {
                        Class< ? > clazz = trace[ i ].getClass ( );
                        if ( ! clazz.equals ( LogUtil.class ) ) {
                                callingClass = trace[ i ].getClassName ( );
                                callingClass = callingClass.substring ( callingClass
                                        .lastIndexOf ( '.' ) + 1 );
                                break;
                        }
                }
                return callingClass;
        }
}