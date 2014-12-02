package nz.ac.auckland.groupapps.maven.gitlog

import nz.ac.auckland.groupapps.maven.gitlog.git.CommitBundle
import nz.ac.auckland.groupapps.maven.gitlog.utils.CommitUtils
import org.apache.maven.plugin.logging.Log
import org.apache.maven.project.MavenProject
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.api.errors.NoHeadException
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.lib.RepositoryBuilder
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.revwalk.RevWalk

/**
 * @author Kefeng Deng (kden022, k.deng@auckland.ac.nz)
 */
public class GitLogGenerator {

	private GitLogGenerator() {}

	/**
	 * Generate a release note under current repository
	 *
	 * @param project is the current project
	 * @param issuePrefix is the issuePrefix config with the plugin
	 * @param log is the Logger
	 */
	public static List<CommitBundle> generate(MavenProject project, String issuePrefix, Log log) {

		List<CommitBundle> allCommits = []

		CommitUtils commitUtils = new CommitUtils(issuePrefix, project)

		try {
			Repository repository = buildRepository()

			RevWalk logs = new Git(repository).log().call()

			Iterator<RevCommit> logIterator = logs.iterator()

			List<CommitBundle> currentReleaseCommits = []

			String versionNumber = project.getVersion()
			boolean isReleased = false

			while (logIterator.hasNext()) {
				RevCommit revCommit = (RevCommit) logIterator.next()

				if (commitUtils.isReleaseCommit(revCommit)) {
					isReleased = true
					versionNumber = commitUtils.fetchReleaseVersionNumber(revCommit)
					allCommits.addAll(currentReleaseCommits)
					currentReleaseCommits.clear()
				} else if (commitUtils.isIssueRelated(revCommit)) {
					currentReleaseCommits.add(commitUtils.convertToBundle(revCommit, isReleased, versionNumber))
				}

			}

			allCommits.addAll(currentReleaseCommits)

		} catch (IllegalArgumentException iae) {
			log.error("No Git repository be found")
		} catch (NoHeadException nhEx) {
			log.debug("No commits be found")
		}

		return allCommits
	}

	/**
	 * @return current repository build
	 */
	protected static Repository buildRepository() {
		return new RepositoryBuilder().findGitDir().build()
	}

}
