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

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		System.setProperty("maven.home", MAVEN_HOME_VALUE);
		executeDependencyTree("org.jboss.errai:errai-cdi-jetty", new File("src/main/resources/tm_pom.xml"));

	}

	/**
	 * @param libraryName
	 *            Name of the maven library within the
	 */
	public static void executeDependencyTree(final String libraryName, final File pomFile) {

		InvocationRequest request = new DefaultInvocationRequest();
		request.setPomFile(pomFile);
		request.setGoals(Arrays.asList("dependency:tree"));
		request.setMavenOpts("-Dincludes=" + libraryName);

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
			});
			invoker.execute(request);
		} catch (MavenInvocationException e) {
			// TODO exception handling
			e.printStackTrace();
		}

	}
}
