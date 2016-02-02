/*******************************************************************************
 * Copyright (c) 2006, 2008 Steffen Pingel and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Steffen Pingel - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.internal.phabricator.ui;

import java.util.List;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.mylyn.internal.phabricator.core.PhabricatorCorePlugin;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.ui.AbstractTaskHyperlinkDetector;

/**
 * @author Steffen Pingel
 */
public class TracHyperlinkDetector extends AbstractTaskHyperlinkDetector {

	public TracHyperlinkDetector() {
	}

	@Override
	protected List<IHyperlink> detectHyperlinks(ITextViewer textViewer, String content, int index, int contentOffset) {
		TaskRepository taskRepository = getTaskRepository(textViewer);
		if (taskRepository != null && PhabricatorCorePlugin.CONNECTOR_KIND.equals(taskRepository.getConnectorKind())) {
			return TracHyperlinkUtil.findTracHyperlinks(taskRepository, content, index, contentOffset);
		}
		return null;
	}

}
