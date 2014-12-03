package nz.ac.auckland.groupapps.maven.gitlog.utils

import nz.ac.auckland.groupapps.maven.gitlog.PluginConstant
import org.apache.maven.project.MavenProject
import org.eclipse.jgit.revwalk.RevCommit

/**
 * @author Kefeng Deng (kden022, k.deng@auckland.ac.nz)
 */
class VersionFetcher {

	private VersionFetcher() {}

	/**
	 * fetch the release version number from the commit message
	 *
	 * @param project is current artifact
	 * @param revCommit is the commit record
	 * @return the version number in the commit message
	 */
	public static String fetchReleaseVersionNumber(MavenProject project, RevCommit revCommit) {
		return fetchReleaseVersionNumber(project, revCommit.shortMessage)
	}

	public static String fetchReleaseVersionNumber(MavenProject project, String message) {
		return message.replace(PluginConstant.DEFAULT_RELEASE_PATTERN, '').replace(project.artifactId, '').trim().substring(1)
	}

}
