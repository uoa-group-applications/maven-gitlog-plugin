package nz.ac.auckland.groupapps.maven.gitlog.utils

import groovy.json.JsonSlurper
import nz.ac.auckland.groupapps.maven.gitlog.PluginConstant
import nz.ac.auckland.groupapps.maven.gitlog.commit.CommitBundle
import org.apache.maven.artifact.Artifact
import org.apache.maven.plugin.logging.Log
import org.apache.maven.project.MavenProject
import org.eclipse.jetty.util.resource.JarFileResource
import org.eclipse.jetty.util.resource.Resource

/**
 * @author Kefeng Deng (kden022, k.deng@auckland.ac.nz)
 */
class DependencyResourceHelper {

	MavenProject project

	Log log

	List<CommitBundle> dependencyResourceCommits = new ArrayList<>()

	public DependencyResourceHelper(MavenProject project, Log log) {
		this.project = project
		this.log = log
	}

	protected List<Resource> findDependencyResources(String groupIdPrefix) {

		Set<Artifact> runtimeArtifacts = (Set<Artifact>) project.getDependencyArtifacts()
		List<Resource> foundResource = []

		if (groupIdPrefix) {
			runtimeArtifacts.removeAll { Artifact artifact ->
				return artifact.getGroupId().toUpperCase().trim().startsWith(groupIdPrefix.toUpperCase().trim())
			}
		}

		runtimeArtifacts.each { Artifact artifact ->
			Resource resource = dependencyIsResource(artifact)

			if (resource) {
				foundResource.add(resource)
				log.info("Found ${artifact} contains release notes")
			}
		}

		if (foundResource.size() > 0) {
			return foundResource
		}

		return null

	}

	public void processReleaseNotes(String groupIdPrefix) {
		List<JarFileResource> resources = findDependencyResources(groupIdPrefix)
		if (resources) {
			discoverMatchingResources(resources)
		}
		log.info("There are ${dependencyResourceCommits.size()} release notes totally")
	}

	protected void discoverMatchingResources(Collection<Resource> resources) {
		resources.each { Resource resource ->
			String name = resource.name

			if (name.indexOf('META-INF/release_notes.json') > 0) {
				List<CommitBundle> resourceReleaseNotes  = new JsonSlurper().parse(resource.inputStream)
				log.info("Found ${resourceReleaseNotes.size()} release notes in ${resource.name}")
				dependencyResourceCommits.addAll(resourceReleaseNotes)
			}
		}
	}

	protected Resource dependencyIsResource(Artifact artifact) {
		if (!PluginConstant.VALID_PACKAGE_TYPE.contains(artifact.type.toLowerCase())) {
			return null
		}

		Resource resource = Resource.newResource("jar:file:${artifact.file.absolutePath}!/META-INF/release_notes.json")

		if (resource && resource.exists()) {
			return resource
		} else {
			return null
		}
	}

}
