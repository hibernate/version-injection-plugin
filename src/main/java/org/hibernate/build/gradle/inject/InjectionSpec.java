package org.hibernate.build.gradle.inject;

import org.gradle.api.Project;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;

/**
 * @author Steve Ebersole
 */
public class InjectionSpec {
	private final ListProperty<TargetMember> targetMembers;
    private final Property<String> projectVersion;

	public InjectionSpec(Project project) {
        this.targetMembers = project.getObjects().listProperty( TargetMember.class );
        this.projectVersion =  project.getObjects().property( String.class )
                .convention(project.getVersion().toString());
	}

	public ListProperty<TargetMember> getTargetMembers() {
		return targetMembers;
	}

    public Property<String> getProjectVersion() {
        return projectVersion;
    }

    public void into(String className, String member) {
		into( new TargetMember( className, member ) );
	}

	private void into(TargetMember targetMember) {
		targetMembers.add( targetMember );
	}
}
