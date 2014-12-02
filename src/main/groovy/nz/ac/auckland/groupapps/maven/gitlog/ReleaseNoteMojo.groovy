package nz.ac.auckland.groupapps.maven.gitlog

import groovy.json.JsonBuilder
import nz.ac.auckland.groupapps.maven.gitlog.git.CommitBundle
import nz.ac.auckland.groupapps.maven.gitlog.render.CommitRender
import org.apache.maven.plugin.MojoExecutionException
import org.apache.maven.plugin.MojoFailureException
import org.apache.maven.plugins.annotations.LifecyclePhase
import org.apache.maven.plugins.annotations.Mojo
import org.apache.maven.plugins.annotations.ResolutionScope

/**
 * @goal 'release-notes'
 *
 * @author Kefeng Deng (kden022, k.deng@auckland.ac.nz)
 */
@Mojo(
		name = 'release-notes',
		requiresProject = true,
		requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME,
		defaultPhase = LifecyclePhase.PREPARE_PACKAGE
)
class ReleaseNoteMojo extends GitLogBaseMojo {

	@Override
	void execute() throws MojoExecutionException, MojoFailureException {
		List<CommitBundle> currentRepositoryCommits = GitLogGenerator.generate(project, issuePrefix, getLog())
		generateReleaseNotesInText(currentRepositoryCommits)
		generateReleaseNotesInJson(currentRepositoryCommits)
	}

	protected void generateReleaseNotesInText(List<CommitBundle> commitBundleList) {
		try {
			File releaseNote = new File(projectOutputDirectory, "${PluginConstant.META_INF_LOCATION + PluginConstant.RELEASE_NOTES_TEXT}")
			cleanAndWriteReleaseNotes(releaseNote, commitBundleList, false)
			getLog().info("Generating release notes into ${releaseNote.getAbsolutePath()}")
		} catch (IOException ioe) {
			getLog().error("Cannot generate release notes file '${PluginConstant.RELEASE_NOTES_TEXT}'")
		}
	}

	protected void generateReleaseNotesInJson(List<CommitBundle> commitBundleList) {
		try {
			File releaseNote = new File(projectOutputDirectory, "${PluginConstant.META_INF_LOCATION + PluginConstant.RELEASE_NOTES_JSON}")
			cleanAndWriteReleaseNotes(releaseNote, commitBundleList, true)
			getLog().info("Parsing release notes into ${releaseNote.getAbsolutePath()}")
		} catch (IOException ioe) {
			getLog().error("Cannot generate release notes file '${PluginConstant.RELEASE_NOTES_JSON}'")
		}
	}

	/**
	 *
	 * @param file
	 * @param isJsonFormat
	 */
	public void cleanAndWriteReleaseNotes(File releaseNotes, List<CommitBundle> commitBundleList, boolean isJsonFormat) {
		if (releaseNotes.exists()) {
			releaseNotes.delete()
		}

		if (isJsonFormat) {
			releaseNotes.withWriter { Writer writer ->
				writer << new JsonBuilder(commitBundleList).toPrettyString()
			}
		} else {
			releaseNotes.withWriter { Writer writer ->
				commitBundleList.each { CommitBundle revCommit ->
					writer << CommitRender.render(revCommit) << '\n'
				}
				writer << '\n\n'
			}
		}
	}

}
