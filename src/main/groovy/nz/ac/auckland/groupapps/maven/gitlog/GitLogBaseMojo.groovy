package nz.ac.auckland.groupapps.maven.gitlog

import groovy.transform.CompileStatic
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

	protected void logProperties() {
		getLog().info("Current artifact version is ${project.getVersion()}, [deployed=${deployedArtifact}], [issuePrefix=${issuePrefix}]")
	}

	/**
	 * @return the output directory of project
	 */
	protected String getProjectOutputDirectory() {
		return project.getBuild().getOutputDirectory()
	}

}
