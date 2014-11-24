package nz.ac.auckland.groupapps.maven.gitlog

import nz.ac.auckland.groupapps.maven.gitlog.render.MavenLoggerRender
import org.apache.maven.plugin.AbstractMojo
import org.apache.maven.plugin.MojoExecutionException
import org.apache.maven.plugin.MojoFailureException
import org.apache.maven.plugin.logging.Log
import org.apache.maven.plugins.annotations.Mojo
import org.apache.maven.plugins.annotations.Parameter
import org.apache.maven.project.MavenProject
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.api.errors.NoHeadException
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.lib.RepositoryBuilder
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.revwalk.RevWalk

/**
 * @goal show
 *
 * @author Kefeng Deng (kden022, k.deng@auckland.ac.nz)
 */
@Mojo(name = 'show')
public class ShowMojo extends AbstractMojo {

	public final static List<String> IGNORE_PACKAGING_TYPE = ['pom', 'tile']

	private final MavenLoggerRender loggerRender

	@Parameter(required = true, readonly = true, property = "project")
	protected MavenProject project

	private final Log log

	public ShowMojo() {
		log = getLog()
	}

	@Override
	void execute() throws MojoExecutionException, MojoFailureException {

		if (IGNORE_PACKAGING_TYPE.contains(project.packaging?.toLowerCase())) {
			return
		}

		GitLogGenerator gitLogGenerator = new GitLogGenerator(log)
		List<RevCommit> allCommits = gitLogGenerator.processGitLogs()

		MavenLoggerRender mavenLoggerRender = new MavenLoggerRender(allCommits, log)
		mavenLoggerRender.render()

//		log.debug("Opened " + repository + ". About to load the commits.");
//		walk = createWalk(repository);
//		log.debug("Loaded commits. about to load the tags.");
//		commitIDToTagsMap = createCommitIDToTagsMap(repository, walk);
//		log.debug("Loaded tag map: " + commitIDToTagsMap);
	}
//
//	private void openRepository() throws IOException, NoGitRepositoryException {
//		Repository repository = null;
//		try {
//			repository = new RepositoryBuilder().findGitDir().build()
//		} catch (IllegalArgumentException iaEx) {
//			throw new NoGitRepositoryException()
//		}
//
//		log.info("Opened ${repository}, and loading the commits...")
//		RevWalk walk = createWalk(repository)
//		commitIDToTagsMap = createCommitIDToTagsMap(repository, walk);
//		log.debug("Loaded tag map: " + commitIDToTagsMap);
//
//	}
//
//	private boolean show(RevCommit commit) {
//		for (CommitFilter commitFilter : commitFilters) {
//			if (!commitFilter.renderCommit(commit)) {
//				log.debug("Commit filtered out by " + commitFilter.getClass().getSimpleName());
//				return false;
//			}
//		}
//		return true;
//	}
//
//
//	private static RevWalk createWalk(Repository repository) throws IOException {
//		RevWalk revWalk = new RevWalk(repository);
//		ObjectId head = repository.resolve("HEAD");
//		if (head != null) {
//			// if head is null, it means there are no commits in the repository.  The walk will be empty.
//			RevCommit mostRecentCommit = revWalk.parseCommit(head);
//			revWalk.markStart(mostRecentCommit);
//		}
//		return revWalk;
//	}
//
//
//	private Map<String, List<RevTag>> createCommitIDToTagsMap(Repository repository, RevWalk revWalk) throws IOException {
//		Map<String, Ref> allTags = repository.getTags();
//
//		Map<String, List<RevTag>> revTags = new HashMap<String, List<RevTag>>();
//
//		for (Ref ref : allTags.values()) {
//			try {
//				RevTag revTag = revWalk.parseTag(ref.getObjectId());
//				String commitID = revTag.getObject().getId().getName();
//				if (!revTags.containsKey(commitID)) {
//					revTags.put(commitID, new ArrayList<RevTag>());
//				}
//				revTags.get(commitID).add(revTag);
//			} catch (IncorrectObjectTypeException e) {
//				log.debug("Light-weight tags not supported. Skipping " + ref.getName());
//			}
//		}


}
