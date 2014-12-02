package nz.ac.auckland.groupapps.maven.gitlog

import groovy.transform.CompileStatic
import nz.ac.auckland.groupapps.maven.gitlog.git.CommitBundle
import nz.ac.auckland.groupapps.maven.gitlog.render.MavenLoggerRender
import org.apache.maven.plugin.MojoExecutionException
import org.apache.maven.plugin.MojoFailureException
import org.apache.maven.plugins.annotations.Mojo

/**
 * @goal show
 *
 * @author Kefeng Deng (kden022, k.deng@auckland.ac.nz)
 */
@CompileStatic
@Mojo(name = 'show')
public class ShowMojo extends GitLogBaseMojo {

	@Override
	void execute() throws MojoExecutionException, MojoFailureException {
		logProperties()

		if (PluginConstant.IGNORE_PACKAGING_TYPE.contains(project.packaging?.toLowerCase())) {
			getLog().info('Current artifact type is not supported')
			return
		}

		List<CommitBundle> allCommits = GitLogGenerator.generate(project, issuePrefix, getLog())

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
