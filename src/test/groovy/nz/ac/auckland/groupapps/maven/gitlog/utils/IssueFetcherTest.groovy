package nz.ac.auckland.groupapps.maven.gitlog.utils

import org.junit.Test

/**
 * @author Kefeng Deng (kden022, k.deng@auckland.ac.nz)
 */
class IssueFetcherTest {

	@Test
	public void testFetchWithNull() {
		assert IssueFetcher.fetchIssueNumber(null, null) == null
	}

	@Test
	public void testFetchWithBlank() {
		assert IssueFetcher.fetchIssueNumber(null, '') == null
	}

	@Test
	public void testFetchWithDefault() {
		assert IssueFetcher.fetchIssueNumber(null, 'No issue number') == null
		assert IssueFetcher.fetchIssueNumber(null, 'DAM-111: this is issue') == 'DAM-111'

		assert IssueFetcher.fetchIssueNumber([], 'No issue number') == null
		assert IssueFetcher.fetchIssueNumber([], 'dam-111: this is issue') == 'DAM-111'
	}

	@Test
	public void testFetchWithValidIssuePattern() {

		assert IssueFetcher.fetchIssueNumber(['dam', 'epr'], 'no issue here') == null
		assert IssueFetcher.fetchIssueNumber(['dam', 'epr'], 'afa-111: afa issue') == null
		assert IssueFetcher.fetchIssueNumber(['dam', 'epr'], 'epr-111: epr issue') == 'EPR-111'

	}

}
