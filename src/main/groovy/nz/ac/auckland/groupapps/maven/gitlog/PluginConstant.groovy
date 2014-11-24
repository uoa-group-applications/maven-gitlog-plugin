package nz.ac.auckland.groupapps.maven.gitlog

import groovy.transform.CompileStatic

/**
 * @author Kefeng Deng (kden022, k.deng@auckland.ac.nz)
 */
@CompileStatic
public class PluginConstant {

	private PluginConstant() {}

	public final static List<String> IGNORE_PACKAGING_TYPE = ['pom', 'tile']
}
