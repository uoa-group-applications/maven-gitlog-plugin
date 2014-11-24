package nz.ac.auckland.groupapps.maven.gitlog.render

import org.apache.maven.plugin.logging.Log
import org.apache.maven.plugin.logging.SystemStreamLog
import org.eclipse.jgit.revwalk.RevCommit

/**
 *
 * @author Kefeng Deng (kden022, k.deng@auckland.ac.nz)
 */
class MavenLoggerRender {

	private final Log logger

	public MavenLoggerRender(Log log) {
		this.logger = log ?: new SystemStreamLog()
	}

	public void renderHeader(String title) {
		logger.info('=============================================================')
		logger.info(title)
		logger.info('=============================================================')
	}

	public void renderCommit(RevCommit commit) {
		logger.info("${commit.commitTime} ${commit.shortMessage} (${commit.committerIdent.name} - ${commit.committerIdent.emailAddress})")
	}

	public void renderFooter() {
		logger.info("\n=============================================================")
	}

}
