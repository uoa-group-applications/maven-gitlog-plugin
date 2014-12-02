package nz.ac.auckland.groupapps.maven.gitlog.utils

import nz.ac.auckland.groupapps.maven.gitlog.git.CommitBundle
import org.apache.commons.lang3.StringUtils
import org.apache.maven.project.MavenProject
import org.eclipse.jgit.revwalk.RevCommit

/**
 * @author Kefeng Deng (kden022, k.deng@auckland.ac.nz)
 */
public class CommitUtils {

	public static final String ISSUE_PREFIX_SEPARATOR = ','

	public static final String DEFAULT_RELEASE_PATTERN = '[maven-release-plugin] prepare release'

	public static final String DEFAULT_SNAPSHOT_PATTERN = '-SNAPSHOT'

	protected List<String> issuePrefixList = []

	protected MavenProject project

	public CommitUtils(String issuePrefix, MavenProject project) {
		List<String> prefixList = []
		if (issuePrefix?.contains(ISSUE_PREFIX_SEPARATOR)) {
			prefixList = issuePrefix.tokenize(',')
		}
		prefixList?.each { String prefix ->
			if (StringUtils.isNotBlank(prefix)) {
				issuePrefixList.add(prefix.trim().toUpperCase())
			}
		}

		this.project = project
	}

	public boolean isIssueRelated(RevCommit revCommit) {
		return revCommit ? IssueFetcher.fetchIssueNumber(issuePrefixList, revCommit.shortMessage) != null : false
	}

	public boolean isReleaseCommit(RevCommit revCommit) {
		return revCommit?.shortMessage?.startsWith(DEFAULT_RELEASE_PATTERN)
	}

	public String fetchReleaseVersionNumber(RevCommit revCommit) {
		return revCommit.shortMessage.replace(DEFAULT_RELEASE_PATTERN, '').replace(project.getArtifactId(), '').trim().substring(1)
	}

	public CommitBundle convertToBundle(RevCommit revCommit, boolean isReleased, String versionNumber) {
		CommitBundle commitBundle = new CommitBundle()

		commitBundle.released = isReleased
		commitBundle.version = versionNumber
		commitBundle.message = revCommit.shortMessage
		commitBundle.issue = IssueFetcher.fetchIssueNumber(issuePrefixList, revCommit.shortMessage)
		commitBundle.committerName = revCommit.committerIdent.name
		commitBundle.committerEmail = revCommit.committerIdent.emailAddress

		commitBundle.commitTime = new Date(revCommit.commitTime * 1000L)

		return commitBundle

	}

	public boolean isSnapshot() {
		return project.version.toUpperCase().endsWith(DEFAULT_SNAPSHOT_PATTERN)
	}

}
