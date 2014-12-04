package nz.ac.auckland.groupapps.maven.gitlog.commit
/**
 * @author Kefeng Deng (kden022, k.deng@auckland.ac.nz)
 */
public class CommitBundle implements Comparable<CommitBundle> {

	boolean released

	String version

	String message

	String issue

	String committerName

	String committerEmail

	long commitTime

	@Override
	int compareTo(CommitBundle otherCommit) {
		return otherCommit.commitTime <=> commitTime
	}
}
