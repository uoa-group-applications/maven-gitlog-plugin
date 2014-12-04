package nz.ac.auckland.groupapps.maven.gitlog.utils

import org.apache.maven.project.MavenProject
import org.junit.Test

/**
 * @author Kefeng Deng (kden022, k.deng@auckland.ac.nz)
 */
class VersionFetcherTest {

	@Test
	public void testFetchWithNull() {
		assert VersionFetcher.fetchReleaseVersionNumber(null, null) == null
		assert VersionFetcher.fetchReleaseVersionNumber(null, '') == null
	}

	@Test
	public void testFetchWithIllegalCommitMessage() {
		assert VersionFetcher.fetchReleaseVersionNumber(mockProject(), '') == null
		assert VersionFetcher.fetchReleaseVersionNumber(mockProject(), 'done') == null
		assert VersionFetcher.fetchReleaseVersionNumber(mockProject(), 'I want to fix this') == null
	}

	@Test
	public void testFetchWithLegalCommitMessage() {
		assert VersionFetcher.fetchReleaseVersionNumber(mockProject(), 'RAM-111: fix version') == null

		assert VersionFetcher.fetchReleaseVersionNumber(mockProject(), '[maven-release-plugin] prepare release ram-core-1.1') == null
		assert VersionFetcher.fetchReleaseVersionNumber(mockProject(), '[maven-release-plugin] prepare release ram-core-1.1-SNAPSHOT') == null

		assert VersionFetcher.fetchReleaseVersionNumber(mockProject(), '[maven-release-plugin] prepare release ram-domain-1.1') == '1.1'
		assert VersionFetcher.fetchReleaseVersionNumber(mockProject(), '[maven-release-plugin] prepare release ram-domain-1.1-SNAPSHOT') == '1.1-SNAPSHOT'
	}

	protected MavenProject mockProject() {
		return [
				getArtifactId: {
					return 'ram-domain'
				}
		] as MavenProject

	}

}
