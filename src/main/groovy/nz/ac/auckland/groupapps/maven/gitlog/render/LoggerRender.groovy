package nz.ac.auckland.groupapps.maven.gitlog.render

import groovy.transform.CompileStatic
import nz.ac.auckland.groupapps.maven.gitlog.PluginConstant
import nz.ac.auckland.groupapps.maven.gitlog.commit.CommitBundle
import org.apache.maven.plugin.logging.Log
import org.apache.maven.plugin.logging.SystemStreamLog

import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 *
 * @author Kefeng Deng (kden022, k.deng@auckland.ac.nz)
 */
@CompileStatic
class LoggerRender {

	private final List<CommitBundle> revCommitList

	private final Log log

	public LoggerRender(List<CommitBundle> commitList, Log log) {
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

	public void render() {
		renderSeparator()
		renderEmptyLine()

		revCommitList.each { CommitBundle revCommit ->
			log.info(convertCommitToString(revCommit))
		}

		renderEmptyLine()
		log.info("Total ${revCommitList.size()} commits")
		renderSeparator()
	}

	public static String convertCommitToString(CommitBundle commit) {
		return "[${formatTimestamp(commit.commitTime)}][Version ${commit.version}] - ${commit.message} (${commit.committerName},${commit.committerEmail})"
	}


	public static String formatTimestamp(long timeStamp) {
		return PluginConstant.DATE_FORMAT.format(new Date(timeStamp))
	}


}
