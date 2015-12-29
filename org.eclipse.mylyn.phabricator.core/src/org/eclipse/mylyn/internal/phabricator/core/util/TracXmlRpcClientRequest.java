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

package org.eclipse.mylyn.internal.phabricator.core.util;

import org.apache.xmlrpc.XmlRpcRequestConfig;
import org.apache.xmlrpc.client.XmlRpcClientRequestImpl;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * @author Steffen Pingel
 */
public class TracXmlRpcClientRequest extends XmlRpcClientRequestImpl {

	private final IProgressMonitor progressMonitor;

	public TracXmlRpcClientRequest(XmlRpcRequestConfig config, String methodName, Object[] params,
			IProgressMonitor monitor) {
		super(config, methodName, params);
		this.progressMonitor = monitor;
	}

	public IProgressMonitor getProgressMonitor() {
		return progressMonitor;
	}

}
