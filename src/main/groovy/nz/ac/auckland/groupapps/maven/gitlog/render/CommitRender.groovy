package nz.ac.auckland.groupapps.maven.gitlog.render

import groovy.transform.CompileStatic
import nz.ac.auckland.groupapps.maven.gitlog.commit.CommitBundle

/**
 * @author Kefeng Deng (kden022, k.deng@auckland.ac.nz)
 */
@CompileStatic
class CommitRender {

	private CommitRender() {}

	public static String render(CommitBundle commit) {
		return "[${TimeRender.formatTimestamp(commit.commitTime)}][Version ${commit.version}] - ${commit.message} (${commit.committerName},${commit.committerEmail})"
	}

}
