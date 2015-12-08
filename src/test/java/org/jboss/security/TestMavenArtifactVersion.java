package org.jboss.security;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the "MavenArtifactVersion" class.
 * 
 * @author Marc
 */
public class TestMavenArtifactVersion {
	
    @Test
    public void securityMitigationEffort() {
        assertEquals(Effort.High, new MavenArtifactVersion("", "", "").getSecurityMitigationEffort());
        assertEquals(Effort.High, new MavenArtifactVersion("0.3.9","0.3.9","null").getSecurityMitigationEffort());
        assertEquals(Effort.Low, new MavenArtifactVersion("1.0.4","1.2","1.1").getSecurityMitigationEffort());
        assertEquals(Effort.Low, new MavenArtifactVersion("1.2","1.3.1","1.3.1").getSecurityMitigationEffort());
        assertEquals(Effort.Low, new MavenArtifactVersion("1.3.2-1","1.4.4","1.4.1").getSecurityMitigationEffort());
        assertEquals(Effort.High, new MavenArtifactVersion("1.45","1.46","null").getSecurityMitigationEffort());
        assertEquals(Effort.Medium, new MavenArtifactVersion("1.5.4","2.0.6","2.0.3").getSecurityMitigationEffort());
        assertEquals(Effort.High, new MavenArtifactVersion("1.6.10","1.6.19","null").getSecurityMitigationEffort());
        assertEquals(Effort.High, new MavenArtifactVersion("1.6.10","1.6.19","null").getSecurityMitigationEffort());
        assertEquals(Effort.Low, new MavenArtifactVersion("1.6.6","1.7.13","1.7.0").getSecurityMitigationEffort());
        assertEquals(Effort.Low, new MavenArtifactVersion("1.7","20,041,127,091,804","1.8").getSecurityMitigationEffort());
        assertEquals(Effort.High, new MavenArtifactVersion("2.0GA","2.1.0","null").getSecurityMitigationEffort());
        assertEquals(Effort.High, new MavenArtifactVersion("2.1.0","2.1.0","null").getSecurityMitigationEffort());
        assertEquals(Effort.High, new MavenArtifactVersion("2.10.0","2.11.0","null").getSecurityMitigationEffort());
        assertEquals(Effort.Low, new MavenArtifactVersion("2.5","2.9.1","2.6").getSecurityMitigationEffort());
        assertEquals(Effort.Low, new MavenArtifactVersion("2.5.1-1","2.6.4","2.6.4").getSecurityMitigationEffort());
        assertEquals(Effort.Low, new MavenArtifactVersion("2.6.0","2.7.2","2.7.2").getSecurityMitigationEffort());
        assertEquals(Effort.Low, new MavenArtifactVersion("2.7.0","2.7.2","2.7.2").getSecurityMitigationEffort());
        assertEquals(Effort.Low, new MavenArtifactVersion("2.7.1","2.7.2","2.7.2").getSecurityMitigationEffort());
        assertEquals(Effort.High, new MavenArtifactVersion("2.8.0","2.9.1","null").getSecurityMitigationEffort());
        assertEquals(Effort.High, new MavenArtifactVersion("3.0","3.1","null").getSecurityMitigationEffort());
        assertEquals(Effort.High, new MavenArtifactVersion("3.1","3.1-rc1","null").getSecurityMitigationEffort());
        assertEquals(Effort.Low, new MavenArtifactVersion("3.1","3.2.2","3.2.2").getSecurityMitigationEffort());
        assertEquals(Effort.Low, new MavenArtifactVersion("3.1.1.RELEASE","4.2.3.RELEASE","3.2.9.RELEASE").getSecurityMitigationEffort());
        assertEquals(Effort.Low, new MavenArtifactVersion("3.1.4.RELEASE","4.2.3.RELEASE","3.2.9.RELEASE").getSecurityMitigationEffort());
        assertEquals(Effort.Low, new MavenArtifactVersion("3.1.4.RELEASE","4.2.3.RELEASE","3.2.12.RELEASE").getSecurityMitigationEffort());
        assertEquals(Effort.Low, new MavenArtifactVersion("3.2","3.2.2","3.2.2").getSecurityMitigationEffort());
        assertEquals(Effort.Low, new MavenArtifactVersion("3.2.1","3.2.2","3.2.2").getSecurityMitigationEffort());
        assertEquals(Effort.Low, new MavenArtifactVersion("4.0.6.RELEASE","4.2.3.RELEASE","4.0.8.RELEASE").getSecurityMitigationEffort());
        assertEquals(Effort.Low, new MavenArtifactVersion("4.0.6.RELEASE","4.2.3.RELEASE","4.2.3.RELEASE").getSecurityMitigationEffort());
        assertEquals(Effort.None, new MavenArtifactVersion("1.0.4","1.0.4","1.0.4").getSecurityMitigationEffort());
        
        
    }
}
