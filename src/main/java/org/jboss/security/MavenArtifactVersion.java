package org.jboss.security;

public class MavenArtifactVersion {

	final private String current;
	final private String latestAvailable;
	final private String closestSecure;
	
	public MavenArtifactVersion(String current, String latestAvailable, String closestSecure) {
		super();
		this.current = current;
		this.latestAvailable = latestAvailable;
		this.closestSecure = closestSecure;
	}
	
	final static protected boolean isInexistentVersion(String version) {
		return version==null || version.trim().length()==0 || "null".equalsIgnoreCase(version);
	}
	
	public Effort getSecurityMitigationEffort() {
		
		final Effort effort;
		if( isInexistentVersion(latestAvailable) || isInexistentVersion(closestSecure) ) {
			effort = Effort.High;
		} else if (isInexistentVersion(current)) {
			effort=Effort.Unknown;
		} else if(current.equalsIgnoreCase(closestSecure)) {
			effort=Effort.None;
		} else {

			String[] tokensCurrent = current.split("\\.");
			String[] tokensClosestSecure = closestSecure.split("\\.");
			
			if(tokensCurrent.length==0 || tokensClosestSecure.length==0) {
				effort=Effort.Unknown;
			} else {
				
				if(tokensCurrent[0]!=null&&tokensCurrent[0].equals(tokensClosestSecure[0])) {
					// Same major version and different minor / micro
					effort=Effort.Low;						
				} else {
					// Different major version ... 
					effort=Effort.Medium;
				}
			}
		}
		return effort;
	}
	
}
