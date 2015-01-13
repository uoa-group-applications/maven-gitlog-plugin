package nz.ac.auckland.groupapps.maven.gitlog.jira

import groovy.json.JsonOutput
import groovy.transform.CompileStatic
import nz.ac.auckland.groupapps.maven.gitlog.commit.CommitBundle
import org.apache.maven.project.MavenProject

/**
 * @author Kefeng Deng (kden022, k.deng@auckland.ac.nz)
 */
@CompileStatic
class JiraVerification {

	private JiraVerification() {

	}

	public static boolean verify(String jiraVerificationUrl, MavenProject project, List<CommitBundle> commitBundles) {

		ReleaseRequest releaseRequest = new ReleaseRequest()
		releaseRequest.commits = []
		releaseRequest.app = project.getArtifactId()
		releaseRequest.version = project.getVersion()

		commitBundles.each { CommitBundle commitBundle ->
			if (commitBundle.version.equalsIgnoreCase(project.version)) {
				releaseRequest.commits.add(commitBundle.issue)
			}
		}

		String responseText = postRequest(jiraVerificationUrl, JsonOutput.toJson(releaseRequest))
		return responseText.contains("true")
	}

	public static final class ReleaseRequest {
		String app
		String version
		List<String> commits
	}


	private static String postRequest(String url, String data) {
		URL host = new URL(url)
		HttpURLConnection connection = (HttpURLConnection) host.openConnection()
		connection.setDoOutput(true)
		connection.setRequestMethod('POST')
		connection.setRequestProperty("Accept", "application/json");
		connection.setRequestProperty("Content-Type", "application/json");

		OutputStreamWriter outputStream = new OutputStreamWriter(connection.outputStream)
		outputStream.write(data)
		outputStream.flush()
		outputStream.close()
		String text = connection.inputStream.text
		connection.inputStream.close()
		connection.disconnect()

		return text
	}

}
