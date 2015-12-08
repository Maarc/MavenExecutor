package org.jboss.maven;

import java.io.File;
import java.util.Arrays;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationOutputHandler;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;

public class ExecuteMavenPlugin {

	/** Update this to point to your local maven installation. */
	public final static String MAVEN_HOME_VALUE = "/opt/maven/apache-maven-3.3.3";

	/** This local directory will be used to download all pom files. */
	public final static String TMP_DIR = "/tmp/test";

	/** URL to the repository containing the installed applications. */
	public final static String REMOTE_REPOSITORY = "http://repo1.maven.org/maven2/";

	public final static String SEPARATOR = "__";

	public final static boolean OUTPUT_VERBOSE_DEPENDENCIES = false;

	public final static boolean OUTPUT_DEBUG = false;

	/**
	 * Main method 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		System.setProperty("maven.home", MAVEN_HOME_VALUE);
		
		System.out.println(">>> Retrieve and save the pom.xml");
		File f = executeDependencyGet("io.hawt:hawtio-jboss:1.4.47");
		// Equivalent to the call above ...
		// File f = executeDependencyGet("io.hawt", "hawtio-jboss", "1.4.47");
		
		System.out.println("\n>>> Full dependency tree");
		executeDependencyTree(f);
		
		System.out.println("\n>>> Filtered dependency tree");
		executeDependencyTree(f, "io.hawt:hawtio-git");
		
		// Example with a local pom.xml file
		//executeDependencyTree(new File("src/main/resources/tm_pom.xml"), "org.jboss.errai:errai-cdi-jetty");

	}

	final static protected String generatePomName(final String applicationGroupId, final String applicationArtifactId, final String applicationVersion) {
		return "pom" + SEPARATOR + applicationGroupId + SEPARATOR + applicationArtifactId + SEPARATOR + applicationVersion + ".xml";
	}

	final static protected String generatePomFile(final String applicationGroupId, final String applicationArtifactId, final String applicationVersion) {
		return TMP_DIR + "/" + generatePomName(applicationGroupId, applicationArtifactId, applicationVersion);
	}

	/**
	 * Execute the maven "dependency:get" plugin to retrieve the remote pom.xml file.
	 *
	 * @param applicationMavenId
	 * 
	 * @return 
	 */
	public static File executeDependencyGet(final String applicationMavenId) {
		String[] tokens = applicationMavenId.split(":");
		return executeDependencyGet(tokens[0].trim(), tokens[1].trim(), tokens[2].trim());
	}

	/**
	 * Execute the maven "dependency:get" plugin to retrieve the remote pom.xml file.
	 * 
	 * @param applicationGroupId
	 * @param applicationArtifactId
	 * @param applicationVersion
	 * 
	 * @return
	 */
	public static File executeDependencyGet(final String applicationGroupId, final String applicationArtifactId, final String applicationVersion) {

		final String generatedPomFile = generatePomFile(applicationGroupId, applicationArtifactId, applicationVersion);

		final InvocationRequest request = new DefaultInvocationRequest();
		request.setGoals(Arrays.asList("dependency:get"));

		String mavenOpts = "-Dtransitive=false -Dpackaging=pom -Ddest=" + generatePomFile(applicationGroupId, applicationArtifactId, applicationVersion);
		mavenOpts += " -DremoteRepositories=" + REMOTE_REPOSITORY + " -DgroupId=" + applicationGroupId + " -DartifactId=" + applicationArtifactId + " -Dversion=" + applicationVersion;

		request.setMavenOpts(mavenOpts);

		Invoker invoker = new DefaultInvoker();
		try {
			invoker.setOutputHandler(new InvocationOutputHandler() {
				/**
				 * Filter the system output to get only the dependency tree.
				 */
				@Override
				public void consumeLine(String line) {
					if (line != null) {
						if (OUTPUT_DEBUG) {
							System.out.println(line);
						} else if (line.startsWith("[INFO] Copying")) {
							System.out.println(line.substring(7));
						}
					}
				}
			});
			invoker.execute(request);
		} catch (MavenInvocationException e) {
			// TODO exception handling
			e.printStackTrace();
		}
		return new File(generatedPomFile);
	}

	/**
	 * Execute the maven "dependency:tree" plugin to display the full dependenc tree of the target pom.xml file.
	 *
	 * @param pomFile
	 */
	public static void executeDependencyTree(final File pomFile) {
		executeDependencyTree(pomFile, null);
	}

	/**
	 * Execute the maven "dependency:tree" plugin to display the dependency tree of the target pom.xml file for a specific filtered library.
	 * 
	 * @param pomFile
	 * @param libraryName
	 */
	public static void executeDependencyTree(final File pomFile, final String libraryName) {

		InvocationRequest request = new DefaultInvocationRequest();
		request.setPomFile(pomFile);
		request.setGoals(Arrays.asList("dependency:tree"));
		if (libraryName != null) {
			request.setMavenOpts("-Dincludes=" + libraryName);
		}

		Invoker invoker = new DefaultInvoker();
		try {
			invoker.setOutputHandler(new InvocationOutputHandler() {

				boolean isTree = false;

				/**
				 * Filter the system output to get only the dependency tree.
				 */
				@Override
				public void consumeLine(String line) {
					if (line != null) {
						if (OUTPUT_DEBUG) {
							System.out.println(line);
						} else if (OUTPUT_VERBOSE_DEPENDENCIES) {
							if (line.startsWith("[INFO] --- maven-dependency-plugin:")) {
								isTree = true;
							}
							if (isTree && line.length() > 7) {
								System.out.println(line.substring(7));
							}
							if (isTree && line.startsWith("[INFO] ------------------------------------------------------------------------")) {
								isTree = false;
							}
						} else {
							if (isTree && line.startsWith("[INFO] ------------------------------------------------------------------------")) {
								isTree = false;
							}
							if (isTree && line.length() > 7) {
								System.out.println(line.substring(7));
							}
							if (line.startsWith("[INFO] --- maven-dependency-plugin:")) {
								isTree = true;
							}
						}
					}
				}
			});
			invoker.execute(request);
		} catch (MavenInvocationException e) {
			// TODO exception handling
			e.printStackTrace();
		}

	}
}
