package nz.ac.auckland.groupapps.maven.gitlog.commit

import groovy.transform.CompileStatic
import nz.ac.auckland.groupapps.maven.gitlog.PluginConstant
import nz.ac.auckland.groupapps.maven.gitlog.utils.VersionFetcher
import org.apache.commons.collections4.CollectionUtils
import org.apache.maven.project.MavenProject
import org.eclipse.jgit.revwalk.RevCommit

/**
 * @author Kefeng Deng (kden022, k.deng@auckland.ac.nz)
 */
@CompileStatic
class CommitHelper {

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

	public static List<CommitBundle> mergeForProject(MavenProject project, List<CommitBundle> localCommitBundleList, List<CommitBundle> dependencyCommitBundleList) {

		List<CommitBundle> finalCommitList = []

		if (CollectionUtils.isNotEmpty(localCommitBundleList) && CollectionUtils.isNotEmpty(dependencyCommitBundleList)) {

			List<CommitBundle> mergedCommits = []
			mergedCommits.addAll(localCommitBundleList)
			mergedCommits.addAll(dependencyCommitBundleList)

			finalCommitList = mergedCommits.sort(false) { CommitBundle commitBundle1, CommitBundle commitBundle2 ->
				return commitBundle1 <=> commitBundle2
			}

			String versionNumber = project.version
			boolean isSnapshot = versionNumber.toUpperCase().trim().endsWith(PluginConstant.SNAPSHOT_PATTERN)
			finalCommitList.each { CommitBundle commitBundle ->

				if (isReleaseCommit(commitBundle.message)) {
					isSnapshot = false
					versionNumber = VersionFetcher.fetchReleaseVersionNumber(project, commitBundle.message)
				}

				commitBundle.released = isSnapshot == false
				commitBundle.version = versionNumber
			}

		} else if (CollectionUtils.isEmpty(localCommitBundleList) && CollectionUtils.isNotEmpty(dependencyCommitBundleList)) {
			dependencyCommitBundleList*.version = project?.version ?: '1.1-SNAPSHOT'
			finalCommitList = dependencyCommitBundleList.sort(false) { CommitBundle commitBundle1, CommitBundle commitBundle2 ->
				return commitBundle1 <=> commitBundle2
			}
		} else if (CollectionUtils.isNotEmpty(localCommitBundleList) && CollectionUtils.isEmpty(dependencyCommitBundleList)) {
			finalCommitList.addAll(localCommitBundleList)
		}

		return finalCommitList

	}

}
