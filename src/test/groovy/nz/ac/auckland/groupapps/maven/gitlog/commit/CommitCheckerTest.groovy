package nz.ac.auckland.groupapps.maven.gitlog.commit

import org.junit.Test

/**
 * @author Kefeng Deng (kden022, k.deng@auckland.ac.nz)
 */
class CommitCheckerTest {

	@Test
	public void testReleaseCommitMessage() {
		assert !CommitHelper.isReleaseCommit(null)
	}

}
