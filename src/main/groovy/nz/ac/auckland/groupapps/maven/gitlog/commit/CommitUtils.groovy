package nz.ac.auckland.groupapps.maven.gitlog.commit

import nz.ac.auckland.groupapps.maven.gitlog.PluginConstant
import nz.ac.auckland.groupapps.maven.gitlog.utils.IssueFetcher
import org.apache.commons.lang3.StringUtils
import org.eclipse.jgit.revwalk.RevCommit

/**
 * @author Kefeng Deng (kden022, k.deng@auckland.ac.nz)
 */
public class CommitUtils {

	protected List<String> issuePrefixList = []

	public CommitUtils(String issuePrefix) {
		List<String> prefixList = []
		if (issuePrefix?.contains(PluginConstant.ISSUE_PREFIX_SEPARATOR)) {
			prefixList = issuePrefix.tokenize(',')
		}
		prefixList?.each { String prefix ->
			if (StringUtils.isNotBlank(prefix)) {
				issuePrefixList.add(prefix.trim().toUpperCase())
			}
		}
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
		commitBundle.commitTime = revCommit.commitTime * 1000L

		return commitBundle

	}

}
