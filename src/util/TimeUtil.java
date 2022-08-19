package util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimeUtil {
	public static String getCurrentTimestamp() {
		Timestamp timestamp = new Timestamp( System.currentTimeMillis() );
		return timestamp.toString();
	}

	public static String formatTime( String time, String format ) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat( format );
		String formatTime = sdf.format( Timestamp.valueOf( time ) );
		return formatTime;
	}

}
