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
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.TaskProvider;

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

		final InjectionSpec injectionSpec = project.getExtensions().create( EXTENSION_NAME, InjectionSpec.class );

		final TaskProvider<InjectionTask> injectionTaskProvider = project.getTasks().register(
				InjectionTask.TASK_NAME, InjectionTask.class, task -> {
					JavaPluginExtension javaExtension = (JavaPluginExtension) project.getExtensions().getByName( "java" );

					task.getVersion().set( injectionSpec.getVersion() );
					task.getInjectionTargets().set( injectionSpec.getTargetMembers() );

					task.getClasspath().setFrom(
							javaExtension.getSourceSets().getByName( "main" ).getOutput().getClassesDirs()
					);

					task.dependsOn( project.getTasks().named( "compileJava" ) );
				}
		);
		project.getTasks().named( "classes" ).configure( classes -> classes.dependsOn( injectionTaskProvider ) );
	}

}
