package nz.ac.auckland.groupapps.maven.gitlog.render

import groovy.transform.CompileStatic

import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 * @author Kefeng Deng (kden022, k.deng@auckland.ac.nz)
 */
@CompileStatic
class TimeRender {

	private final static String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss"

	private final static DateFormat DATE_FORMAT = new SimpleDateFormat(DEFAULT_FORMAT)

	public static String formatTimestamp(int timeStamp) {
		return DATE_FORMAT.format(new Date(timeStamp))
	}

}
