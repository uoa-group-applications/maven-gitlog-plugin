package nz.ac.auckland.groupapps.maven.gitlog

import groovy.transform.CompileStatic

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

}
