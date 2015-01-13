package nz.ac.auckland.groupapps.maven.gitlog

import groovy.transform.CompileStatic

import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 * @author Kefeng Deng (kden022, k.deng@auckland.ac.nz)
 */
@CompileStatic
public class PluginConstant {

	private PluginConstant() {}

	public static final List<String> IGNORE_PACKAGING_TYPE = ['pom', 'tile']

	public static final String RELEASE_NOTES_TEXT = 'release_notes.txt'

	public static final String RELEASE_NOTES_JSON = 'release_notes.json'

	public static final META_INF_LOCATION = './META-INF/'

	public static final List<String> VALID_PACKAGE_TYPE = ['jar', 'war', 'bathe-war']

	/**
	 * The separator in plugin configuration.
	 */
	public static final String ISSUE_PREFIX_SEPARATOR = ','

	/**
	 * Default commit message by maven release plugin
	 * e.g. [maven-release-plugin] prepare release dam-domain-1.5
	 */
	public static final String DEFAULT_RELEASE_PATTERN = '[maven-release-plugin] prepare release'

	public static final String SNAPSHOT_PATTERN = '-SNAPSHOT'

	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss"

	public static final DateFormat DATE_FORMAT = new SimpleDateFormat(DEFAULT_FORMAT)


}
