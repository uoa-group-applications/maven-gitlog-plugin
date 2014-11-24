package nz.ac.auckland.groupapps.maven.gitlog

import groovy.transform.CompileStatic
import nz.ac.auckland.groupapps.maven.gitlog.render.CommitRender
import org.apache.maven.plugin.AbstractMojo
import org.apache.maven.plugin.MojoExecutionException
import org.apache.maven.plugin.MojoFailureException
import org.apache.maven.plugins.annotations.LifecyclePhase
import org.apache.maven.plugins.annotations.Mojo
import org.apache.maven.plugins.annotations.Parameter
import org.apache.maven.plugins.annotations.ResolutionScope
import org.apache.maven.project.MavenProject
import org.eclipse.jgit.revwalk.RevCommit

/**
 * @author Kefeng Deng (kden022, k.deng@auckland.ac.nz)
 */
@Mojo(
		name='compile',
		requiresProject = true,
		requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME,
		defaultPhase = LifecyclePhase.GENERATE_RESOURCES
)
@CompileStatic
class ReleaseNoteMojo extends AbstractMojo {

	@Parameter(required = true, readonly = true, property = "project")
	protected MavenProject project

	@Parameter(required = true, defaultValue = 'false')
	protected boolean deployedArtifact

	@Override
	void execute() throws MojoExecutionException, MojoFailureException {
		List<RevCommit> currentRepositoryCommits = GitLogGenerator.loadGitLogs(log)

		File releaseNotes = new File(project.getBuild().getOutputDirectory(), './META-INF/release-notes.txt')

		if (releaseNotes.exists()) {
			releaseNotes.delete()
		}

		releaseNotes.withWriter { Writer writer ->
			writer << "[release-notes] ${project.getVersion()}" << '\n'
			currentRepositoryCommits.each { RevCommit revCommit ->
				writer << CommitRender.render(revCommit) << '\n'
			}
			writer << '\n\n'
		}

		getLog().info("Generated release-notes.txt")

	}
}
