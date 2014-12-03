package nz.ac.auckland.groupapps.maven.gitlog.commit

import nz.ac.auckland.groupapps.maven.gitlog.PluginConstant
import org.eclipse.jgit.revwalk.RevCommit

/**
 * @author Kefeng Deng (kden022, k.deng@auckland.ac.nz)
 */
class CommitHelper {

	public CommitHelper() {}

	/**
	 * Verify whether the passing commit is a release commit
	 *
	 * @param revCommit is the commit record
	 * @return true if the commit message contains maven release message
	 */
	public static boolean isReleaseCommit(RevCommit revCommit) {
		return isReleaseCommit(revCommit?.shortMessage)
	}

	public static boolean isReleaseCommit(String commitMessage) {
		return commitMessage?.startsWith(PluginConstant.DEFAULT_RELEASE_PATTERN)
	}

}
