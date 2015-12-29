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

package org.eclipse.mylyn.internal.phabricator.core.model;

/**
 * @author Steffen Pingel
 */
public class TracPriority extends TracTicketAttribute {

	private static final long serialVersionUID = 3617078252773178266L;

	public TracPriority(String name, int value) {
		super(name, value);
	}

}
