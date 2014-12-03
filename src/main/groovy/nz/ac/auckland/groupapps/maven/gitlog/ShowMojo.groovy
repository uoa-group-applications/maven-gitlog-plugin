package nz.ac.auckland.groupapps.maven.gitlog

import groovy.transform.CompileStatic
import nz.ac.auckland.groupapps.maven.gitlog.mojo.GitLogBaseMojo
import nz.ac.auckland.groupapps.maven.gitlog.commit.CommitBundle
import nz.ac.auckland.groupapps.maven.gitlog.git.GitLogGenerator
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

	}

}
