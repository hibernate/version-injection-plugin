package org.hibernate.build.gradle.inject;

import javax.inject.Inject;

import org.gradle.api.Project;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ListProperty;

/**
 * @author Steve Ebersole
 */
public class InjectionSpec {
	private final ListProperty<TargetMember> targetMembers;

	@Inject
	public InjectionSpec(ObjectFactory objects) {
		targetMembers = objects.listProperty( TargetMember.class );
	}

	public InjectionSpec(Project project) {
		this( project.getObjects() );
	}

	public ListProperty<TargetMember> getTargetMembers() {
		return targetMembers;
	}

	public void into(String className, String member) {
		into( new TargetMember( className, member ) );
	}

	private void into(TargetMember targetMember) {
		targetMembers.add( targetMember );
	}
}
