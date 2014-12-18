package nz.ac.auckland.groupapps.maven.gitlog.render

import groovy.transform.CompileStatic
import nz.ac.auckland.groupapps.maven.gitlog.commit.CommitBundle
import org.apache.maven.plugin.logging.Log
import org.apache.maven.plugin.logging.SystemStreamLog

/**
 *
 * @author Kefeng Deng (kden022, k.deng@auckland.ac.nz)
 */
@CompileStatic
class MavenLoggerRender {

	private final List<CommitBundle> revCommitList

	private final Log log

	public MavenLoggerRender(List<CommitBundle> commitList, Log log) {
		if (!log) {
			log = new SystemStreamLog()
		}
		this.revCommitList = commitList
		this.log = log
	}

	protected void renderEmptyLine() {
		log.info('')
	}

	protected void renderSeparator() {
		log.info('=============================================================')
	}

	protected void renderCommit(CommitBundle commit) {
		log.info(CommitRender.render(commit))
	}

	public void render() {
		renderSeparator()
		renderEmptyLine()

		revCommitList.each { CommitBundle revCommit ->
//			if (!revCommit.releaseCommit) {
			renderCommit(revCommit)
//			}
		}

		renderEmptyLine()
		log.info("Total ${revCommitList.size()} commits")
		renderSeparator()
	}

}
