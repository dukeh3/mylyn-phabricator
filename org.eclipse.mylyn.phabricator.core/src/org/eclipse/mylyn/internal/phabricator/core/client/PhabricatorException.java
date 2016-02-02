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
 * Indicates an error during repository access.
 * 
 * @author Steffen Pingel
 */
public class PhabricatorException extends Exception {

	private static final long serialVersionUID = 1929614326467463462L;

	public PhabricatorException() {
	}

	public PhabricatorException(String message) {
		super(message);
	}

	public PhabricatorException(Throwable cause) {
		super(cause.getMessage(), cause);
	}

	public PhabricatorException(String message, Throwable cause) {
		super(message, cause);
	}

}
