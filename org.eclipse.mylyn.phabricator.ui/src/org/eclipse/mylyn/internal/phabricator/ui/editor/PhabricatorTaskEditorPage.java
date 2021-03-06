/*******************************************************************************
 * Copyright (c) 2006, 2009 Steffen Pingel and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Steffen Pingel - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.internal.phabricator.ui.editor;

import java.util.Set;

import org.eclipse.mylyn.internal.phabricator.core.PhabricatorCorePlugin;
import org.eclipse.mylyn.tasks.core.data.TaskAttribute;
import org.eclipse.mylyn.tasks.ui.editors.AbstractAttributeEditor;
import org.eclipse.mylyn.tasks.ui.editors.AbstractTaskEditorPage;
import org.eclipse.mylyn.tasks.ui.editors.AttributeEditorFactory;
import org.eclipse.mylyn.tasks.ui.editors.TaskEditor;
import org.eclipse.mylyn.tasks.ui.editors.TaskEditorPartDescriptor;

/**
 * @author Steffen Pingel
 */
public class PhabricatorTaskEditorPage extends AbstractTaskEditorPage {

	private TracRenderingEngine renderingEngine;

	public PhabricatorTaskEditorPage(TaskEditor editor) {
		super(editor, PhabricatorCorePlugin.CONNECTOR_KIND);
		System.out.println("TracTaskEditorPage.TracTaskEditorPage()");
//		setNeedsPrivateSection(true);
		setNeedsSubmitButton(true);
	}

	@Override
	protected Set<TaskEditorPartDescriptor> createPartDescriptors() {
		System.out.println("PhabricatorTaskEditorPage.createPartDescriptors()");

		Set<TaskEditorPartDescriptor> descriptors = super.createPartDescriptors();
//
//		// remove unnecessary default editor parts
//		for (Iterator<TaskEditorPartDescriptor> it = descriptors.iterator(); it.hasNext();) {
//			TaskEditorPartDescriptor taskEditorPartDescriptor = it.next();
//			if (taskEditorPartDescriptor.getId().equals(ID_PART_PEOPLE)) {
//				it.remove();
//			}
//		}
//
//		descriptors.add(new TaskEditorPartDescriptor("") {
//
//			@Override
//			public AbstractTaskEditorPart createPart() {
//				// ignore
//				return null;
//			}
//		}.setPath(ID_PART_ATTRIBUTES));
//
//		descriptors.add(new TaskEditorPartDescriptor(ID_PART_PEOPLE) {
//			@Override
//			public AbstractTaskEditorPart createPart() {
//				return new TracPeoplePart();
//			}
//		}.setPath(PATH_PEOPLE));

		return descriptors;
	}

	@Override
	protected void createParts() {
		System.out.println("PhabricatorTaskEditorPage.createParts()");
//
//		if (renderingEngine == null) {
//			renderingEngine = new TracRenderingEngine();
//		}
//		getAttributeEditorToolkit().setRenderingEngine(renderingEngine);

		super.createParts();
	}

	@Override
	protected AttributeEditorFactory createAttributeEditorFactory() {
		System.out.println("TracTaskEditorPage.createAttributeEditorFactory()");

		AttributeEditorFactory factory = new AttributeEditorFactory(getModel(), getTaskRepository(), getEditorSite()) {
			@Override
			public AbstractAttributeEditor createEditor(String type, TaskAttribute taskAttribute) {
//				if (TracAttribute.CC.getTracKey().equals(taskAttribute.getId())) {
//					return new TracCcAttributeEditor(getModel(), taskAttribute);
//				}

				System.out.println(
						"TracTaskEditorPage.createAttributeEditorFactory().new AttributeEditorFactory() {...}.createEditor()");

				System.out.println(type);
				System.out.println(taskAttribute);

				return super.createEditor(type, taskAttribute);
			}
		};

		return factory;
	}

}
