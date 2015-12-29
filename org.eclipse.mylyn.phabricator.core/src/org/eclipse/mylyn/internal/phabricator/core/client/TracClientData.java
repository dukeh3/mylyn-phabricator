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

package org.eclipse.mylyn.internal.phabricator.core.client;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.eclipse.mylyn.internal.phabricator.core.model.TracComponent;
import org.eclipse.mylyn.internal.phabricator.core.model.TracMilestone;
import org.eclipse.mylyn.internal.phabricator.core.model.TracPriority;
import org.eclipse.mylyn.internal.phabricator.core.model.TracSeverity;
import org.eclipse.mylyn.internal.phabricator.core.model.TracTicketField;
import org.eclipse.mylyn.internal.phabricator.core.model.TracTicketResolution;
import org.eclipse.mylyn.internal.phabricator.core.model.TracTicketStatus;
import org.eclipse.mylyn.internal.phabricator.core.model.TracTicketType;
import org.eclipse.mylyn.internal.phabricator.core.model.TracVersion;

public class TracClientData implements Serializable {

	private static final long serialVersionUID = 6891961984245981675L;

	List<TracComponent> components;

	List<TracMilestone> milestones;

	List<TracPriority> priorities;

	List<TracSeverity> severities;

	List<TracTicketField> ticketFields;

	List<TracTicketResolution> ticketResolutions;

	List<TracTicketStatus> ticketStatus;

	List<TracTicketType> ticketTypes;

	List<TracVersion> versions;

	long lastUpdate;

	transient Map<String, TracTicketField> ticketFieldByName;

}
