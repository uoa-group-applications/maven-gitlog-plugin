package nz.ac.auckland.groupapps.maven.gitlog.commit

import org.junit.Test

/**
 * @author Kefeng Deng (kden022, k.deng@auckland.ac.nz)
 */
class CommitBundleTest {



	@Test
	public void testCommitBundleCompare() {

		CommitBundle firstCommitBundle = new CommitBundle()
		CommitBundle secondCommitBundle = new CommitBundle()

		firstCommitBundle.version = '1.1'
		firstCommitBundle.commitTime = new Date(2010, 1, 1, 1, 0).time

		secondCommitBundle.version = '1.2'
		secondCommitBundle.commitTime = new Date(2010, 1, 1, 2, 0).time

		assert firstCommitBundle > secondCommitBundle

		def commitList = [firstCommitBundle, secondCommitBundle]

		def sortedCommitList = commitList.sort(false)

		assert sortedCommitList.get(0).version == '1.2'
		assert sortedCommitList.get(1).version == '1.1'

	}

}
