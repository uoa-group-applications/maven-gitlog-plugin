package nz.ac.auckland.groupapps.maven.gitlog.utils

import groovy.transform.CompileStatic
import nz.ac.auckland.groupapps.maven.gitlog.PluginConstant
import nz.ac.auckland.groupapps.maven.gitlog.commit.CommitHelper
import org.apache.maven.project.MavenProject
import org.eclipse.jgit.revwalk.RevCommit

import java.util.regex.Pattern

/**
 * @author Kefeng Deng (kden022, k.deng@auckland.ac.nz)
 */
@CompileStatic
class VersionFetcher {

	public static final String VERSION_PATTERN = '[1-9][0-9]*(\\.[0-9]+)*'

	private VersionFetcher() {}

	/**
	 * fetch the release version number from the commit message
	 *
	 * @param project is current artifact
	 * @param revCommit is the commit record
	 * @return the version number in the commit message
	 */
	public static String fetchReleaseVersionNumber(MavenProject project, RevCommit revCommit) {
		return fetchReleaseVersionNumber(project, revCommit?.shortMessage ?: null)
	}

	public static String fetchReleaseVersionNumber(MavenProject project, String message) {
		String versionNumber = null
		if (message && CommitHelper.isReleaseCommit(message)) {
			versionNumber = message.replace(PluginConstant.DEFAULT_RELEASE_PATTERN, '').replace(project.artifactId, '').trim().substring(1)

			if (versionNumber && !Pattern.compile(VERSION_PATTERN).matcher(versionNumber.trim().toUpperCase().replace(PluginConstant.SNAPSHOT_PATTERN, '')).matches()) {
				versionNumber = null
			}
		}
		return versionNumber
	}

}
