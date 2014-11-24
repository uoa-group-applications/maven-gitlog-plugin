package nz.ac.auckland.groupapps.maven.gitlog.render

import org.apache.maven.plugin.logging.Log
import org.apache.maven.plugin.logging.SystemStreamLog
import org.eclipse.jgit.revwalk.RevCommit

/**
 *
 * @author Kefeng Deng (kden022, k.deng@auckland.ac.nz)
 */
class MavenLoggerRender {

	private final List<RevCommit> revCommitList

	private final Log log

	public MavenLoggerRender(List<RevCommit> commitList, Log log) {
		if (!log) {
			log = new SystemStreamLog()
		}
		this.revCommitList = commitList
		this.log = log

	}

	protected void renderEmptyLine() {
		log.info("\n")
	}

	protected void renderSeparator() {
		log.info('=============================================================')
	}

	protected void renderCommit(RevCommit commit) {
		log.info("${TimeRender.formatDatetime(commit.commitTime)} ${commit.shortMessage} (${commit.committerIdent.name} - ${commit.committerIdent.emailAddress})")
	}

	public void render() {
		renderSeparator()
		renderEmptyLine()

		revCommitList.each { RevCommit revCommit ->
			renderCommit(revCommit)
		}

		renderEmptyLine()
		log.info("Total ${revCommitList.size()} commits")
		renderSeparator()
	}

}
