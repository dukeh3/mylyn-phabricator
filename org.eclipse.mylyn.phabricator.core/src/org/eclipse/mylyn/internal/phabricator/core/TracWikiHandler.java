/*******************************************************************************
 * Copyright (c) 2006, 2008 Steffen Pingel and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Xiaoyang Guan - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.internal.phabricator.core;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.mylyn.internal.phabricator.core.client.AbstractWikiHandler;
import org.eclipse.mylyn.internal.phabricator.core.client.ITracClient;
import org.eclipse.mylyn.internal.phabricator.core.client.ITracWikiClient;
import org.eclipse.mylyn.internal.phabricator.core.client.PhabricatorException;
import org.eclipse.mylyn.internal.phabricator.core.model.TracWikiPage;
import org.eclipse.mylyn.internal.phabricator.core.model.TracWikiPageInfo;
import org.eclipse.mylyn.tasks.core.TaskRepository;

/**
 * @author Xiaoyang Guan
 */
public class TracWikiHandler extends AbstractWikiHandler {

	private final PhabricatorRepositoryConnector connector;

	public TracWikiHandler(PhabricatorRepositoryConnector connector) {
		this.connector = connector;
	}

	@Override
	public String[] downloadAllPageNames(TaskRepository repository, IProgressMonitor monitor) throws CoreException {
		monitor.beginTask(Messages.TracWikiHandler_Download_Wiki_Page_Names, IProgressMonitor.UNKNOWN);
		try {
			String[] names = getTracWikiClient(repository).getAllWikiPageNames(monitor);
			return names;
		} catch (PhabricatorException e) {
			throw new CoreException(PhabricatorCorePlugin.toStatus(e, repository));
		} finally {
			monitor.done();
		}
	}

	@Override
	public TracWikiPage getWikiPage(TaskRepository repository, String pageName, IProgressMonitor monitor)
			throws CoreException {
		monitor.beginTask(Messages.TracWikiHandler_Download_Wiki_Page, IProgressMonitor.UNKNOWN);
		try {
			TracWikiPage page = getTracWikiClient(repository).getWikiPage(pageName, monitor);
			return page;
		} catch (PhabricatorException e) {
			throw new CoreException(PhabricatorCorePlugin.toStatus(e, repository));
		} finally {
			monitor.done();
		}
	}

	@Override
	public void postWikiPage(TaskRepository repository, TracWikiPage newPage, IProgressMonitor monitor)
			throws CoreException {
		monitor.beginTask(Messages.TracWikiHandler_Upload_Wiki_Page, IProgressMonitor.UNKNOWN);
		try {
			String pageName = newPage.getPageInfo().getPageName();
			String content = newPage.getContent();
			Map<String, Object> attributes = new HashMap<String, Object>();
			attributes.put("comment", newPage.getPageInfo().getComment()); //$NON-NLS-1$
			attributes.put("author", newPage.getPageInfo().getAuthor()); //$NON-NLS-1$
			boolean success = getTracWikiClient(repository).putWikipage(pageName, content, attributes, monitor);
			if (success) {
				return;
			} else {
				throw new CoreException(PhabricatorCorePlugin.toStatus(new PhabricatorException(
						"Failed to upload wiki page. No further information available."), repository)); //$NON-NLS-1$
			}
		} catch (PhabricatorException e) {
			throw new CoreException(PhabricatorCorePlugin.toStatus(e, repository));
		} finally {
			monitor.done();
		}
	}

	@Override
	public TracWikiPageInfo[] getPageHistory(TaskRepository repository, String pageName, IProgressMonitor monitor)
			throws CoreException {
		monitor.beginTask(Messages.TracWikiHandler_Retrieve_Wiki_Page_History, IProgressMonitor.UNKNOWN);
		try {
			TracWikiPageInfo[] versions = getTracWikiClient(repository).getWikiPageInfoAllVersions(pageName, monitor);
			return versions;
		} catch (PhabricatorException e) {
			throw new CoreException(PhabricatorCorePlugin.toStatus(e, repository));
		} finally {
			monitor.done();
		}
	}

	private ITracWikiClient getTracWikiClient(TaskRepository repository) throws PhabricatorException {
		ITracClient client = connector.getClientManager().getTracClient(repository);
		if (client instanceof ITracWikiClient) {
			return (ITracWikiClient) client;
		} else {
			throw new PhabricatorException("The access mode of " + repository.toString() //$NON-NLS-1$
					+ " does not support Wiki page editting."); //$NON-NLS-1$
		}
	}

	@Override
	public String getWikiUrl(TaskRepository repository) {
		return repository.getRepositoryUrl() + ITracClient.WIKI_URL;
	}
}
