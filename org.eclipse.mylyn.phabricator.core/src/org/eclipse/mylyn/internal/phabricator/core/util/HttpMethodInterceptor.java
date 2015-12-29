/*******************************************************************************
 * Copyright (c) 2006 Steffen Pingel and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Steffen Pingel - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.internal.phabricator.core.util;

import org.apache.commons.httpclient.HttpMethod;

/**
 * @author Steffen Pingel
 */
public interface HttpMethodInterceptor {

	public abstract void processRequest(HttpMethod method);

	public abstract void processResponse(HttpMethod method);

}
