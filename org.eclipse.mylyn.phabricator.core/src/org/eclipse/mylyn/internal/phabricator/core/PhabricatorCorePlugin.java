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

package org.eclipse.mylyn.internal.phabricator.core;

import java.net.MalformedURLException;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.mylyn.internal.phabricator.core.client.InvalidTicketException;
import org.eclipse.mylyn.internal.phabricator.core.client.PhabricatorException;
import org.eclipse.mylyn.internal.phabricator.core.client.PhabricatorLoginException;
import org.eclipse.mylyn.internal.phabricator.core.client.PhabricatorPermissionDeniedException;
import org.eclipse.mylyn.internal.phabricator.core.client.TracMidAirCollisionException;
import org.eclipse.mylyn.internal.phabricator.core.util.TracUtil;
import org.eclipse.mylyn.tasks.core.RepositoryStatus;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.osgi.framework.BundleContext;

import fenixtest.TestClass;

/**
 * The headless Trac plug-in class.
 *
 * @author Steffen Pingel
 */
public class PhabricatorCorePlugin extends Plugin {

	public static final String ID_PLUGIN = "org.eclipse.mylyn.phabricator.core"; //$NON-NLS-1$

	public static final String ENCODING_UTF_8 = "UTF-8"; //$NON-NLS-1$

	private static PhabricatorCorePlugin plugin;

	public final static String CONNECTOR_KIND = "phabricator"; //$NON-NLS-1$

	private PhabricatorRepositoryConnector connector;

	public PhabricatorCorePlugin() {
		TestClass.main(null);
	}

	public static PhabricatorCorePlugin getDefault() {
		return plugin;
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		if (connector != null) {
			connector.stop();
			connector = null;
		}

		plugin = null;
		super.stop(context);
	}

	public PhabricatorRepositoryConnector getConnector() {
		return connector;
	}

	void setConnector(PhabricatorRepositoryConnector connector) {
		this.connector = connector;
	}

	/**
	 * Returns the path to the file caching repository attributes.
	 */
	protected IPath getRepostioryAttributeCachePath() {
		IPath stateLocation = Platform.getStateLocation(getBundle());
		IPath cacheFile = stateLocation.append("repositoryConfigurations"); //$NON-NLS-1$
		return cacheFile;
	}

	public static IStatus toStatus(Throwable e, TaskRepository repository) {
		if (e instanceof PhabricatorLoginException) {
			return RepositoryStatus.createLoginError(repository.getRepositoryUrl(), ID_PLUGIN);
		} else if (e instanceof PhabricatorPermissionDeniedException) {
			return TracUtil.createPermissionDeniedError(repository.getRepositoryUrl(), ID_PLUGIN);
		} else if (e instanceof InvalidTicketException) {
			return new RepositoryStatus(repository.getRepositoryUrl(), IStatus.ERROR, ID_PLUGIN,
					RepositoryStatus.ERROR_IO, Messages.TracCorePlugin_the_SERVER_RETURNED_an_UNEXPECTED_RESOPNSE, e);
		} else if (e instanceof TracMidAirCollisionException) {
			return RepositoryStatus.createCollisionError(repository.getUrl(), PhabricatorCorePlugin.ID_PLUGIN);
		} else if (e instanceof PhabricatorException) {
			String message = e.getMessage();
			if (message == null) {
				message = Messages.TracCorePlugin_I_O_error_has_occured;
			}
			return new RepositoryStatus(repository.getRepositoryUrl(), IStatus.ERROR, ID_PLUGIN,
					RepositoryStatus.ERROR_IO, message, e);
		} else if (e instanceof ClassCastException) {
			return new RepositoryStatus(IStatus.ERROR, ID_PLUGIN, RepositoryStatus.ERROR_IO,
					Messages.TracCorePlugin_Unexpected_server_response_ + e.getMessage(), e);
		} else if (e instanceof MalformedURLException) {
			return new RepositoryStatus(IStatus.ERROR, ID_PLUGIN, RepositoryStatus.ERROR_IO,
					Messages.TracCorePlugin_Repository_URL_is_invalid, e);
		} else {
			return new RepositoryStatus(IStatus.ERROR, ID_PLUGIN, RepositoryStatus.ERROR_INTERNAL,
					Messages.TracCorePlugin_Unexpected_error, e);
		}
	}

}
