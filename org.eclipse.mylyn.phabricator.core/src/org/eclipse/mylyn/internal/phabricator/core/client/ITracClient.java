/*******************************************************************************
 * Copyright (c) 2006, 2010 Steffen Pingel and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Steffen Pingel - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.internal.phabricator.core.client;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.mylyn.internal.phabricator.core.model.TracComment;
import org.eclipse.mylyn.internal.phabricator.core.model.TracComponent;
import org.eclipse.mylyn.internal.phabricator.core.model.TracMilestone;
import org.eclipse.mylyn.internal.phabricator.core.model.TracPriority;
import org.eclipse.mylyn.internal.phabricator.core.model.TracRepositoryInfo;
import org.eclipse.mylyn.internal.phabricator.core.model.TracSearch;
import org.eclipse.mylyn.internal.phabricator.core.model.TracSeverity;
import org.eclipse.mylyn.internal.phabricator.core.model.TracTicket;
import org.eclipse.mylyn.internal.phabricator.core.model.TracTicketField;
import org.eclipse.mylyn.internal.phabricator.core.model.TracTicketResolution;
import org.eclipse.mylyn.internal.phabricator.core.model.TracTicketStatus;
import org.eclipse.mylyn.internal.phabricator.core.model.TracTicketType;
import org.eclipse.mylyn.internal.phabricator.core.model.TracVersion;

/**
 * Defines the requirements for classes that provide remote access to Trac repositories.
 *
 * @author Steffen Pingel
 */
public interface ITracClient {

	public enum Version {
		XML_RPC, TRAC_0_9;

		public static Version fromVersion(String version) {
			try {
				return Version.valueOf(version);
			} catch (IllegalArgumentException e) {
				return null;
			}
		}

		@Override
		public String toString() {
			switch (this) {
			case TRAC_0_9:
				return "Web"; //$NON-NLS-1$
			case XML_RPC:
				return "XML-RPC"; //$NON-NLS-1$
			default:
				return null;
			}
		}

	}

	public static final String CHARSET = "UTF-8"; //$NON-NLS-1$

	public static final String TIME_ZONE = "UTC"; //$NON-NLS-1$

	public static final String LOGIN_URL = "/login"; //$NON-NLS-1$

	public static final String QUERY_URL = "/query?format=tab"; //$NON-NLS-1$

	public static final String TICKET_URL = "/ticket/"; //$NON-NLS-1$

	public static final String NEW_TICKET_URL = "/maniphest/task/edit/form/1"; //$NON-NLS-1$

	public static final String CUSTOM_QUERY_URL = "/query"; //$NON-NLS-1$

	public static final String TICKET_ATTACHMENT_URL = "/attachment/ticket/"; //$NON-NLS-1$

	public static final String DEFAULT_USERNAME = "anonymous"; //$NON-NLS-1$

	public static final String WIKI_URL = "/wiki/"; //$NON-NLS-1$

	public static final String REPORT_URL = "/report/"; //$NON-NLS-1$

	public static final String CHANGESET_URL = "/changeset/"; //$NON-NLS-1$

	public static final String REVISION_LOG_URL = "/log/"; //$NON-NLS-1$

	public static final String MILESTONE_URL = "/milestone/"; //$NON-NLS-1$

	public static final String BROWSER_URL = "/browser/"; //$NON-NLS-1$

	public static final String ATTACHMENT_URL = "/attachment/ticket/"; //$NON-NLS-1$

	/**
	 * Gets ticket with <code>id</code> from repository.
	 *
	 * @param id
	 *            the id of the ticket to get
	 * @param monitor
	 *            TODO
	 * @return the ticket
	 * @throws PhabricatorException
	 *             thrown in case of a connection error
	 */
	TracTicket getTicket(int id, IProgressMonitor monitor) throws PhabricatorException;

	/**
	 * Returns the access type.
	 */
	Version getAccessMode();

	/**
	 * Returns the repository url.
	 */
	String getUrl();

	/**
	 * Queries tickets from repository. All found tickets are added to <code>result</code>.
	 *
	 * @param query
	 *            the search criteria
	 * @param result
	 *            the list of found tickets
	 * @throws PhabricatorException
	 *             thrown in case of a connection error
	 */
	void search(TracSearch query, List<TracTicket> result, IProgressMonitor monitor) throws PhabricatorException;

	/**
	 * Queries ticket id from repository. All found tickets are added to <code>result</code>.
	 *
	 * @param query
	 *            the search criteria
	 * @param result
	 *            the list of found tickets
	 * @throws PhabricatorException
	 *             thrown in case of a connection error
	 */
	void searchForTicketIds(TracSearch query, List<Integer> result, IProgressMonitor monitor)
			throws PhabricatorException;

	/**
	 * Validates the repository connection.
	 *
	 * @return information about the repository
	 * @throws PhabricatorException
	 *             thrown in case of a connection error
	 */
	TracRepositoryInfo validate(IProgressMonitor monitor) throws PhabricatorException;

	/**
	 * Returns true, if the repository details are cached. If this method returns true, invoking
	 * <tt>updateAttributes(monitor, false)</tt> will return without opening a connection.
	 *
	 * @see #updateAttributes(IProgressMonitor, boolean)
	 */
	boolean hasAttributes();

	/**
	 * Updates cached repository details: milestones, versions etc.
	 *
	 * @throws PhabricatorException
	 *             thrown in case of a connection error
	 */
	void updateAttributes(IProgressMonitor monitor, boolean force) throws PhabricatorException;

	TracComponent[] getComponents();

	TracTicketField[] getTicketFields();

	TracTicketField getTicketFieldByName(String tracKey);

	TracMilestone[] getMilestones();

	TracPriority[] getPriorities();

	TracSeverity[] getSeverities();

	TracTicketResolution[] getTicketResolutions();

	TracTicketStatus[] getTicketStatus();

	TracTicketType[] getTicketTypes();

	TracVersion[] getVersions();

	InputStream getAttachmentData(int ticketId, String filename, IProgressMonitor monitor) throws PhabricatorException;

	void putAttachmentData(int ticketId, String name, String description, InputStream source, IProgressMonitor monitor,
			boolean replace) throws PhabricatorException;

	void deleteAttachment(int ticketId, String filename, IProgressMonitor monitor) throws PhabricatorException;

	/**
	 * @return the id of the created ticket
	 */
	int createTicket(TracTicket ticket, IProgressMonitor monitor) throws PhabricatorException;

	void updateTicket(TracTicket ticket, String comment, IProgressMonitor monitor) throws PhabricatorException;

	/**
	 * Sets a reference to the cached repository attributes.
	 *
	 * @param data
	 *            cached repository attributes
	 */
	void setData(TracClientData data);

	Set<Integer> getChangedTickets(Date since, IProgressMonitor monitor) throws PhabricatorException;

	Date getTicketLastChanged(Integer id, IProgressMonitor monitor) throws PhabricatorException;

	void deleteTicket(int ticketId, IProgressMonitor monitor) throws PhabricatorException;

	List<TracComment> getComments(int id, IProgressMonitor monitor) throws PhabricatorException;

}
