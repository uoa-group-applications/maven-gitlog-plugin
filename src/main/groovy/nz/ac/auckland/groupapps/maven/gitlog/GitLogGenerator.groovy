package nz.ac.auckland.groupapps.maven.gitlog

import org.apache.maven.plugin.logging.Log
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

	private final Log log

	public GitLogGenerator(Log log) {
		this.log = log
	}

	public List<RevCommit> processGitLogs() {
		List<RevCommit> allCommits = new ArrayList<>()

		try {
			Repository repository = buildRepository()

			RevWalk logs = new Git(repository).log().call()

			Iterator<RevCommit> logIterator = logs.iterator()

			while (logIterator.hasNext()) {
				RevCommit revCommit = (RevCommit) logIterator.next()
				allCommits.add(0, revCommit)
			}

		} catch (IllegalArgumentException iae) {
			log.error("No Git repository be found")
		} catch (NoHeadException nhEx) {
			log.debug("No commits be found")
		}

		return allCommits
	}

	public Repository buildRepository() {
		return new RepositoryBuilder().findGitDir().build()
	}

}
