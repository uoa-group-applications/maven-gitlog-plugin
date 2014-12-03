package nz.ac.auckland.groupapps.maven.gitlog.commit

import org.apache.maven.project.MavenProject
import org.junit.Test


/**
 * @author Kefeng Deng (kden022, k.deng@auckland.ac.nz)
 */
class CommitMergerTest {

	@Test
	public void testBlankMerge() {
		assert [] == CommitMerger.mergeForProject(null, [], [])
		assert [] == CommitMerger.mergeForProject(null, [], null)
		assert [] == CommitMerger.mergeForProject(null, null, null)
		assert [] == CommitMerger.mergeForProject(null, null, [])
	}

	@Test
	public void testMergeFromDependencyCommit() {
		def dependencyCommit = [
				new CommitBundle(),
				new CommitBundle(),
				new CommitBundle()
		]

		def mergedCommits = CommitMerger.mergeForProject(null, null, dependencyCommit)

		assert mergedCommits.size() == 3
		assert mergedCommits.every { it.version.equals('1.1-SNAPSHOT') }
	}

	@Test
	public void testMergeLocalCommitsOnly() {
		def localCommit = [new CommitBundle(version: '1.3'), new CommitBundle(version: '1.3')]
		def project = [
				version: '1.2'
		] as MavenProject
		def mergedCommits = CommitMerger.mergeForProject(project, localCommit, [])

		assert mergedCommits.size() == 2
		assert mergedCommits.every { it.version.equals('1.3') }
	}

	@Test
	public void testMergeTwoCommits() {
		def localCommit = [
				new CommitBundle(released: false, version: '1.4-SNAPSHOT', commitTime: new Date(2014, 1, 12, 12, 0), message: 'DAM-126'),

				new CommitBundle(released: true, version: '1.3', commitTime: new Date(2014, 1, 10, 9, 0), message: '[maven-release-plugin] prepare release dam-war-1.3'),
				new CommitBundle(released: true, version: '1.3', commitTime: new Date(2014, 1, 9, 16, 0), message: 'DAM-125'),
				new CommitBundle(released: true, version: '1.3', commitTime: new Date(2014, 1, 8, 10, 0), message: 'DAM-124'),

				new CommitBundle(released: true, version: '1.2', commitTime: new Date(2014, 1, 7, 12, 0), message: '[maven-release-plugin] prepare release dam-war-1.2'),
				new CommitBundle(released: true, version: '1.2', commitTime: new Date(2014, 1, 7, 11, 30), message: 'DAM-123'),
				new CommitBundle(released: true, version: '1.2', commitTime: new Date(2014, 1, 5, 9, 30), message: 'DAM-122'),

				new CommitBundle(released: true, version: '1.1', commitTime: new Date(2014, 1, 2, 14, 0), message: '[maven-release-plugin] prepare release dam-war-1.1'),
				new CommitBundle(released: true, version: '1.1', commitTime: new Date(2014, 1, 2, 10, 30), message: 'DAM-121')
		]

		def dependencyCommit = [
				new CommitBundle(released: false, version: '1.4-SNAPSHOT', commitTime: new Date(2014, 1, 11, 11, 0), message: 'DAM-116'),

				new CommitBundle(released: true, version: '1.3', commitTime: new Date(2014, 1, 10, 16, 0), message: 'DAM-115'),
				new CommitBundle(released: true, version: '1.3', commitTime: new Date(2014, 1, 10, 10, 0), message: 'DAM-114'),

				new CommitBundle(released: true, version: '1.2', commitTime: new Date(2014, 1, 7, 9, 45), message: 'DAM-113'),
				new CommitBundle(released: true, version: '1.2', commitTime: new Date(2014, 1, 5, 8, 30), message: 'DAM-112'),

				new CommitBundle(released: true, version: '1.1', commitTime: new Date(2014, 1, 2, 8, 30), message: 'DAM-111')
		]

		def project = [
				getVersion: {
					return '1.4-SNAPSHOT'
				},
				getArtifactId: {
					return 'dam-war'
				}
		] as MavenProject

		def mergedCommits = CommitMerger.mergeForProject(project, localCommit, dependencyCommit)

		assert mergedCommits.size() == 15

		assert mergedCommits.get(0).message == 'DAM-126'
		assert mergedCommits.get(0).version == '1.4-SNAPSHOT'
		assert !mergedCommits.get(0).released

		assert mergedCommits.get(1).message == 'DAM-116'
		assert mergedCommits.get(1).version == '1.4-SNAPSHOT'
		assert !mergedCommits.get(1).released

		assert mergedCommits.get(2).message == 'DAM-115'
		assert mergedCommits.get(2).version == '1.4-SNAPSHOT'
		assert !mergedCommits.get(2).released

		assert mergedCommits.get(3).message == 'DAM-114'
		assert mergedCommits.get(3).version == '1.4-SNAPSHOT'
		assert !mergedCommits.get(3).released

		assert mergedCommits.get(4).message == '[maven-release-plugin] prepare release dam-war-1.3'
		assert mergedCommits.get(4).version == '1.3'
		assert mergedCommits.get(4).released

		assert mergedCommits.get(5).message == 'DAM-125'
		assert mergedCommits.get(5).version == '1.3'
		assert mergedCommits.get(5).released

		assert mergedCommits.get(6).message == 'DAM-124'
		assert mergedCommits.get(6).version == '1.3'
		assert mergedCommits.get(6).released

		assert 4 == mergedCommits.findAll { !it.released }.size()
		assert 11 == mergedCommits.findAll { it.released }.size()

	}

}
