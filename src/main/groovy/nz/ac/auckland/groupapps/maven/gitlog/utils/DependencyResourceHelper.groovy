package nz.ac.auckland.groupapps.maven.gitlog.utils

import org.apache.maven.artifact.Artifact
import org.apache.maven.project.MavenProject
import org.eclipse.jetty.util.resource.Resource
import org.eclipse.jetty.util.resource.ResourceCollection

/**
 * @author Kefeng Deng (kden022, k.deng@auckland.ac.nz)
 */
class DependencyResourceHelper {

	private DependencyResourceHelper() {
	}

	public findDependencyResourcesByProject(MavenProject project) {
		List<Artifact> runtimeArtifacts = (List<Artifact>) project.getRuntimeArtifacts()
		List<Resource> foundResources = []

		File ourResources = new File(project.basedir, "src/main/resources/META-INF/resources")

		if (ourResources.exists()) {
			foundResources.add(Resource.newResource(ourResources))
		}

		runtimeArtifacts.each { Artifact artifact ->
			Resource resource = dependencyIsResource(artifact)

			if (resource) {
				foundResources.add(resource)
			}
		}

		if (foundResources.size() > 0) {
			Resource[] resources = new Resource[foundResources.size()]
			return new ResourceCollection(foundResources.toArray(resources))
		} else {
			return null
		}
	}

	protected Resource dependencyIsResource(Artifact artifact) {
		if (artifact.type != "jar") { return null }

		Resource resource = Resource.newResource("jar:file:${artifact.file.absolutePath}!/META-INF/resources")

		if (resource && resource.exists()) {
			return resource
		} else {
			return null
		}
	}

}
