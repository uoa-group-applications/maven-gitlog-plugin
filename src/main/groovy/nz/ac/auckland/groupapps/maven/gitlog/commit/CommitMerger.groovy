package nz.ac.auckland.groupapps.maven.gitlog.commit

import nz.ac.auckland.groupapps.maven.gitlog.PluginConstant
import nz.ac.auckland.groupapps.maven.gitlog.utils.VersionFetcher
import org.apache.commons.collections4.CollectionUtils
import org.apache.maven.project.MavenProject

/**
 * @author Kefeng Deng (kden022, k.deng@auckland.ac.nz)
 */
public class CommitMerger {

	private CommitMerger() {
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

				if (CommitChecker.isReleaseCommit(commitBundle.message)) {
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
