package nz.ac.auckland.groupapps.maven.gitlog.utils

import nz.ac.auckland.groupapps.maven.gitlog.git.CommitBundle
import org.apache.commons.lang3.StringUtils
import org.apache.maven.project.MavenProject
import org.eclipse.jgit.revwalk.RevCommit

/**
 * @author Kefeng Deng (kden022, k.deng@auckland.ac.nz)
 */
public class CommitUtils {

	/**
	 * The separator in plugin configuration.
	 */
	public static final String ISSUE_PREFIX_SEPARATOR = ','

	/**
	 * Default commit message by maven release plugin
	 * e.g. [maven-release-plugin] prepare release dam-domain-1.5
	 */
	public static final String DEFAULT_RELEASE_PATTERN = '[maven-release-plugin] prepare release'

	protected List<String> issuePrefixList = []

	/**
	 * Current maven project
	 */
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

	/**
	 * Verify whether the short message of passing commit contains an issue number
	 *
	 * @param revCommit the commit record
	 * @return true if the short message contains an issue number
	 */
	public boolean isIssueRelated(RevCommit revCommit) {
		return revCommit ? IssueFetcher.fetchIssueNumber(issuePrefixList, revCommit.shortMessage) != null : false
	}

	/**
	 * Verify whether the passing commit is a release commit
	 *
	 * @param revCommit is the commit record
	 * @return true if the commit message contains maven release message
	 */
	public boolean isReleaseCommit(RevCommit revCommit) {
		return revCommit?.shortMessage?.startsWith(DEFAULT_RELEASE_PATTERN)
	}

	/**
	 * fetch the release version number from the commit message
	 *
	 * @param revCommit is the commit record
	 * @return the version number in the commit message
	 */
	public String fetchReleaseVersionNumber(RevCommit revCommit) {
		return revCommit.shortMessage.replace(DEFAULT_RELEASE_PATTERN, '').replace(project.getArtifactId(), '').trim().substring(1)
	}

	/**
	 * Convert RevCommit to CommitBundle object
	 *
	 * @param revCommit is the commit record
	 * @param isReleased is the flag that current commit be released or not.
	 * @param versionNumber is the release version number
	 * @return the commit bundle object
	 */
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

}
