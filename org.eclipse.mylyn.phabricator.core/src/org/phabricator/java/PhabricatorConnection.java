/*******************************************************************************
 * Copyright (c) 2015 Steffen Pingel and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Steffen Pingel - initial API and implementation
 *******************************************************************************/

package org.phabricator.java;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.uber.jenkins.phabricator.conduit.ConduitAPIClient;
import com.uber.jenkins.phabricator.conduit.ConduitAPIException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PhabricatorConnection {

	private ConduitAPIClient client;

	private final URI uri;

	private final String conduitToken;

	public PhabricatorConnection(String url, String conduitToken) throws URISyntaxException {

		this.conduitToken = conduitToken;
		this.uri = new URI(url).resolve("/conduit/method"); //$NON-NLS-1$
	}

	public void connect() {
		client = new ConduitAPIClient("" + uri, conduitToken);
	}

	public JSONObject call(String method, JSONObject params) {

		JSONObject result;

		try {
			result = client.perform(method, params);

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);

		} catch (ConduitAPIException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		if (!result.getJSONObject("error_code").isNullObject()) {
			throw new RuntimeException(new Exception(result.getString("error_info")));
		}

		return result.getJSONObject("result");
	}

	public JSONArray calla(String method, JSONObject params) {

		JSONObject result;

		try {

			result = client.perform(method, params);

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);

		} catch (ConduitAPIException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		if (!result.getJSONObject("error_code").isNullObject()) {
			throw new RuntimeException(new Exception(result.getString("error_info")));
		}

		return result.getJSONArray("result");
	}

}
