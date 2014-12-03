package nz.ac.auckland.groupapps.maven.gitlog.mojo

import groovy.transform.CompileStatic
import nz.ac.auckland.groupapps.maven.gitlog.commit.CommitBundle
import nz.ac.auckland.groupapps.maven.gitlog.utils.DependencyResourceHelper
import org.apache.maven.plugin.AbstractMojo
import org.apache.maven.plugins.annotations.Parameter
import org.apache.maven.project.MavenProject

/**
 * @author Kefeng Deng (kden022, k.deng@auckland.ac.nz)
 */
@CompileStatic
public abstract class GitLogBaseMojo extends AbstractMojo {

	@Parameter(required = true, readonly = true, property = 'project')
	protected MavenProject project

	@Parameter(required = false, property = 'deployedArtifact')
	protected boolean deployedArtifact = false

	@Parameter(required = false, property = 'issuePrefix')
	protected String issuePrefix = null

	@Parameter(required=false, property='groupIdPrefix')
	protected String groupIdPrefix = null

	protected void logProperties() {
		getLog().info("Current artifact version is ${project.getVersion()}, [deployed=${deployedArtifact}], [issuePrefix=${issuePrefix}]")
	}

	/**
	 * @return the output directory of project
	 */
	protected String getProjectOutputDirectory() {
		return project.getBuild().getOutputDirectory()
	}


	protected List<CommitBundle> fetchDependencyReleaseNotes() {
		DependencyResourceHelper dependencyResourceHelper = new DependencyResourceHelper(project, getLog())
		dependencyResourceHelper.processReleaseNotes(groupIdPrefix)
		return dependencyResourceHelper.dependencyResourceCommits.collect {
			return (CommitBundle) it
		}
	}

}
