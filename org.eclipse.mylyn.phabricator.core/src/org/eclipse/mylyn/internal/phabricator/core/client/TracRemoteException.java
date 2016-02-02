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

package org.eclipse.mylyn.internal.phabricator.core.client;

/**
 * Indicates that an exception on the repository side has been encountered while processing the request.
 * 
 * @author Steffen Pingel
 */
public class TracRemoteException extends PhabricatorException {

	private static final long serialVersionUID = -6761365344287289624L;

	public TracRemoteException() {
	}

	public TracRemoteException(String message) {
		super(message);
	}

	public TracRemoteException(Throwable cause) {
		super(cause);
	}

	public TracRemoteException(String message, Throwable cause) {
		super(message, cause);
	}

}
