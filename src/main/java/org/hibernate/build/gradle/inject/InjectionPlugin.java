/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2011, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package org.hibernate.build.gradle.inject;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;

/**
 * Mainly used to apply the InjectionAction to the main compileJava task
 *
 * @author Steve Ebersole
 */
public class InjectionPlugin implements Plugin<Project> {
	public static final String EXTENSION_NAME = "versionInjection";

	@Override
	public void apply(Project project) {
		project.getPluginManager().apply( "java" );

		// Allow user to configure
		final InjectionSpec injectionSpec = new InjectionSpec( project );
		project.getExtensions().add( EXTENSION_NAME, injectionSpec );

		// The action to run after compilation
		final InjectionAction injectionAction = new InjectionAction( injectionSpec );
		final Task compileJava = project.getTasks().getByName( "compileJava" );
		compileJava.getInputs().property( "version-injection-targets", injectionSpec.getTargetMembers() );
		compileJava.doLast( injectionAction );
	}

}
