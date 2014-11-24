package nz.ac.auckland.groupapps.maven.gitlog.render

import groovy.transform.CompileStatic
import org.eclipse.jgit.revwalk.RevCommit

/**
 * @author Kefeng Deng (kden022, k.deng@auckland.ac.nz)
 */
@CompileStatic
class CommitRender {

	private CommitRender() {}

	public static String render(RevCommit revCommit) {
		return "[${TimeRender.formatDatetime(revCommit.commitTime)}] ${revCommit.shortMessage} (${revCommit.committerIdent.name},${revCommit.committerIdent.emailAddress})"
	}
}
