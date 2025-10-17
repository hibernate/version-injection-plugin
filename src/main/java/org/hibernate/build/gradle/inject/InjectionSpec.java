package org.hibernate.build.gradle.inject;

import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;

/**
 * @author Steve Ebersole
 */
public abstract class InjectionSpec {

	public abstract Property<String> getVersion();

	public abstract ListProperty<TargetMember> getTargetMembers();

}
