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

import org.eclipse.mylyn.internal.tasks.core.AbstractSearchHandler;
import org.eclipse.mylyn.internal.phabricator.core.PhabricatorCorePlugin;
import org.eclipse.mylyn.internal.phabricator.core.client.ITracClient;
import org.eclipse.mylyn.internal.phabricator.core.model.TracSearch;
import org.eclipse.mylyn.internal.phabricator.core.model.TracSearchFilter;
import org.eclipse.mylyn.internal.phabricator.core.model.TracSearchFilter.CompareOperator;
import org.eclipse.mylyn.tasks.core.IRepositoryQuery;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.core.data.TaskData;

@SuppressWarnings("restriction")
public class TracSearchHandler extends AbstractSearchHandler {

	@Override
	public String getConnectorKind() {
		return PhabricatorCorePlugin.CONNECTOR_KIND;
	}

	@Override
	public boolean queryForText(TaskRepository taskRepository, IRepositoryQuery query, TaskData taskData,
			String searchString) {
		TracSearchFilter filter = new TracSearchFilter("description"); //$NON-NLS-1$
		filter.setOperator(CompareOperator.CONTAINS);
		filter.addValue(searchString);

		TracSearch search = new TracSearch();
		search.addFilter(filter);

		// TODO copied from TracQueryPage.getQueryUrl()
		StringBuilder sb = new StringBuilder();
		sb.append(taskRepository.getRepositoryUrl());
		sb.append(ITracClient.QUERY_URL);
		sb.append(search.toUrl());

		query.setUrl(sb.toString());

		return true;
	}

}
