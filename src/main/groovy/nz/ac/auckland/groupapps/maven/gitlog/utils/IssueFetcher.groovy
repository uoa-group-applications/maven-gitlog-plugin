package nz.ac.auckland.groupapps.maven.gitlog.utils

/**
 * @author Kefeng Deng (kden022, k.deng@auckland.ac.nz)
 */
public class IssueFetcher {

	public static final String DEFAULT_ISSUE_NUMBER_PATTERN = '-[1-9][0-9]*'

	public static final String DEFAULT_ISSUE_PREFIX_PATTERN = '[A-Z][A-Z]+'

	private IssueFetcher() {
	}

	public static String fetchIssueNumber(List<String> issuePrefixList, String shortMessage) {
		String issueNumber = null

		if (!shortMessage) {
			return issueNumber
		}

		if (issuePrefixList?.size() > 0) {
			issuePrefixList.each { String issuePrefix ->
				if (!issueNumber) {
					issueNumber = shortMessage.toUpperCase().find(issuePrefix + DEFAULT_ISSUE_NUMBER_PATTERN)
				}
			}
		} else {
			issueNumber = shortMessage.toUpperCase().find(DEFAULT_ISSUE_PREFIX_PATTERN + DEFAULT_ISSUE_NUMBER_PATTERN)
		}

		return issueNumber
	}

}
