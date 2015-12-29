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

import org.eclipse.mylyn.commons.ui.CommonImages;
import org.eclipse.mylyn.internal.phabricator.core.TracCorePlugin;
import org.eclipse.mylyn.internal.phabricator.core.TracRepositoryConnector;
import org.eclipse.mylyn.tasks.ui.ITasksUiConstants;
import org.eclipse.mylyn.tasks.ui.TasksUiImages;
import org.eclipse.mylyn.tasks.ui.TasksUiUtil;
import org.eclipse.mylyn.tasks.ui.editors.AbstractTaskEditorPageFactory;
import org.eclipse.mylyn.tasks.ui.editors.BrowserFormPage;
import org.eclipse.mylyn.tasks.ui.editors.TaskEditor;
import org.eclipse.mylyn.tasks.ui.editors.TaskEditorInput;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.forms.editor.FormPage;

/**
 * @author Steffen Pingel
 */
public class TracTaskEditorPageFactory extends AbstractTaskEditorPageFactory {

	@Override
	public boolean canCreatePageFor(TaskEditorInput input) {
		if (input.getTask().getConnectorKind().equals(TracCorePlugin.CONNECTOR_KIND)) {
			return true;
		} else if (TasksUiUtil.isOutgoingNewTask(input.getTask(), TracCorePlugin.CONNECTOR_KIND)) {
			return true;
		}
		return false;
	}

	@Override
	public FormPage createPage(TaskEditor parentEditor) {
		TaskEditorInput input = parentEditor.getTaskEditorInput();
		if (TasksUiUtil.isOutgoingNewTask(input.getTask(), TracCorePlugin.CONNECTOR_KIND)) {
			return new TracTaskEditorPage(parentEditor);
		} else if (TracRepositoryConnector.hasRichEditor(input.getTaskRepository())) {
			return new TracTaskEditorPage(parentEditor);
		} else {
			return new BrowserFormPage(parentEditor, Messages.TracTaskEditorPageFactory_Browser);
		}
	}

	@Override
	public String[] getConflictingIds(TaskEditorInput input) {
		if (TracRepositoryConnector.hasRichEditor(input.getTaskRepository())
				|| TasksUiUtil.isOutgoingNewTask(input.getTask(), TracCorePlugin.CONNECTOR_KIND)) {
			return new String[] { ITasksUiConstants.ID_PAGE_PLANNING };
		} else {
			return super.getConflictingIds(input);
		}
	}

	@Override
	public Image getPageImage() {
		return CommonImages.getImage(TasksUiImages.REPOSITORY_SMALL);
	}

	@Override
	public String getPageText() {
		return "Trac"; //$NON-NLS-1$
	}

	@Override
	public int getPriority() {
		return PRIORITY_TASK;
	}

}
