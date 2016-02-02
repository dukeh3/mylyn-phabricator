/*******************************************************************************
 * Copyright (c) 2006, 2010 Steffen Pingel and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Steffen Pingel - initial API and implementation
 *     BREDEX GmbH - fix for bug 295050
 *******************************************************************************/

package org.eclipse.mylyn.internal.phabricator.ui.wizard;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.mylyn.commons.core.StatusHandler;
import org.eclipse.mylyn.commons.net.AbstractWebLocation;
import org.eclipse.mylyn.internal.phabricator.core.PhabricatorCorePlugin;
import org.eclipse.mylyn.internal.phabricator.core.client.PhabricatorException;
import org.eclipse.mylyn.internal.phabricator.ui.PhabricatorUiPlugin;
import org.eclipse.mylyn.tasks.core.RepositoryStatus;
import org.eclipse.mylyn.tasks.core.RepositoryTemplate;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.core.TaskRepositoryLocationFactory;
import org.eclipse.mylyn.tasks.ui.wizards.AbstractRepositorySettingsPage;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;
import org.phabricator.java.PhabricatorConnection;

import net.sf.json.JSONObject;

/**
 * @author Steffen Pingel
 */
public class PhabricatorRepositorySettingsPage extends AbstractRepositorySettingsPage {

	private static final String TITLE = Messages.TracRepositorySettingsPage_Trac_Repository_Settings;

	private static final String DESCRIPTION = Messages.TracRepositorySettingsPage_EXAMPLE_HTTP_TRAC_EDGEWALL_ORG;

//	private Combo accessTypeCombo;

//	/** Supported access types. */
//	private Version[] versions;

	public PhabricatorRepositorySettingsPage(TaskRepository taskRepository) {
		super(TITLE, DESCRIPTION, taskRepository);
		setNeedsCertAuth(true);
		setNeedsAnonymousLogin(true);
		setNeedsEncoding(false);
		setNeedsTimeZone(false);
	}

	@Override
	protected void repositoryTemplateSelected(RepositoryTemplate template) {
		repositoryLabelEditor.setStringValue(template.label);
		setUrl(template.repositoryUrl);
		setAnonymous(template.anonymous);

//		try {
//			Version version = Version.valueOf(template.version);
//			setTracVersion(version);
//		} catch (RuntimeException ex) {
//			setTracVersion(Version.TRAC_0_9);
//		}

		getContainer().updateButtons();
	}

	@Override
	protected void createAdditionalControls(final Composite parent) {
		addRepositoryTemplatesToServerUrlCombo();

		apiTokenEditor = new StringFieldEditor(API_TOKEN, "Text", parent);
		apiTokenEditor.setStringValue("api-kp7flwjp55ysb3p5plcqud4twc4k");

//		Label accessTypeLabel = new Label(parent, SWT.NONE);
//		accessTypeLabel.setText(Messages.TracRepositorySettingsPage_Access_Type_);
//		accessTypeCombo = new Combo(parent, SWT.READ_ONLY);
//
//		accessTypeCombo.add(Messages.TracRepositorySettingsPage_Automatic__Use_Validate_Settings_);
//
//		versions = Version.values();
//		for (Version version : versions) {
//			accessTypeCombo.add(version.toString());
//		}

//		if (repository != null) {
//			setTracVersion(Version.fromVersion(repository.getVersion()));
//		} else {
//			setTracVersion(null);
//		}

//		accessTypeCombo.addSelectionListener(new SelectionListener() {
//			public void widgetSelected(SelectionEvent e) {
//				if (accessTypeCombo.getSelectionIndex() > 0) {
//					setVersion(versions[accessTypeCombo.getSelectionIndex() - 1].name());
//				}
//				getWizard().getContainer().updateButtons();
//			}
//
//			public void widgetDefaultSelected(SelectionEvent e) {
//				// ignore
//			}
//		});

	}

//	@Override
//	public boolean isPageComplete() {
//		// make sure "Automatic" is not selected as a version
//		return super.isPageComplete() && accessTypeCombo != null && accessTypeCombo.getSelectionIndex() != 0;
//	}

//	@Override
//	protected boolean isValidUrl(String url) {
//		boolean isValid = super.isValidUrl(url);
//		return isValid && !url.endsWith("/"); //$NON-NLS-1$
//	}

//	public Version getTracVersion() {
//		if (accessTypeCombo.getSelectionIndex() == 0) {
//			return null;
//		} else {
//			return versions[accessTypeCombo.getSelectionIndex() - 1];
//		}
//	}

//	public void setTracVersion(Version version) {
//		if (version == null) {
//			// select "Automatic"
//			accessTypeCombo.select(0);
//		} else {
//			int i = accessTypeCombo.indexOf(version.toString());
//			if (i != -1) {
//				accessTypeCombo.select(i);
//			}
//			setVersion(version.name());
//		}
//	}

	@Override
	public void applyTo(@NonNull TaskRepository repository) {
		// ignore

		repository.setProperty(API_TOKEN, apiTokenEditor.getStringValue());

		super.applyTo(repository);
	}

	@Override
	protected void applyValidatorResult(Validator validator) {
//		if (((PhabricatorValidator) validator).getResult() != null) {
//			setTracVersion(((PhabricatorValidator) validator).getResult());
//			getContainer().updateButtons();
//		}

		System.out.println("PhabricatorRepositorySettingsPage.applyValidatorResult()");
		super.applyValidatorResult(validator);

//		System.out.println(apiTokenEditor.getStringValue());

//		this.getRepository().setProperty(API_TOKEN, "api-kp7flwjp55ysb3p5plcqud4twc4k");
		getContainer().updateButtons();

	}

	public static final String API_TOKEN = "apiToken";

	private StringFieldEditor apiTokenEditor;

	// public for testing
	public class PhabricatorValidator extends Validator {

//		private final String repositoryUrl;

		private final TaskRepository taskRepository;

//		private Version version;
//
//		private Version result;

		public PhabricatorValidator(TaskRepository taskRepository) {
//			this.repositoryUrl = taskRepository.getRepositoryUrl();
			this.taskRepository = taskRepository;
//			this.version = version;
		}

		@Override
		public void run(IProgressMonitor monitor) throws CoreException {

			AbstractWebLocation location = new TaskRepositoryLocationFactory().createWebLocation(taskRepository);

			if (taskRepository.getUrl() == null || taskRepository.getUrl().isEmpty()) {
				throw new CoreException(RepositoryStatus.createStatus(taskRepository, IStatus.ERROR,
						PhabricatorUiPlugin.ID_PLUGIN, INVALID_REPOSITORY_URL));
			}

			//Check that the API token is not null
			if (taskRepository.getProperty(API_TOKEN) == null || taskRepository.getProperty(API_TOKEN).isEmpty()) {
				throw new CoreException(RepositoryStatus.createStatus(taskRepository, IStatus.ERROR,
						PhabricatorUiPlugin.ID_PLUGIN,
						"ApiToken can not be empty see " + taskRepository.getUrl() + "settings/panel/apitokens/"));
			}

			try {
				PhabricatorConnection pc = new PhabricatorConnection(taskRepository.getUrl(),
						taskRepository.getProperty(API_TOKEN));

				JSONObject params = new JSONObject();
				params.element("task_id", "1");

				pc.connect();

				JSONObject result = pc.call("maniphest.info", params);

			} catch (URISyntaxException e) {
				throw new CoreException(RepositoryStatus.createStatus(taskRepository, IStatus.ERROR,
						PhabricatorUiPlugin.ID_PLUGIN, INVALID_REPOSITORY_URL));
			}

//			try {
//				//validate(Provider.of(monitor));
//				validate(monitor);
//			} catch (MalformedURLException e) {
//				throw new CoreException(RepositoryStatus.createStatus(taskRepository, IStatus.ERROR,
//						PhabricatorUiPlugin.ID_PLUGIN, INVALID_REPOSITORY_URL));
//
//			} catch (PhabricatorLoginException e) {
//				if (e.isNtlmAuthRequested()) {
//					AuthenticationCredentials credentials = taskRepository.getCredentials(AuthenticationType.REPOSITORY);
//					if (credentials == null || credentials.getUserName() == null || credentials.getPassword() == null) {
//						throw new CoreException(new RepositoryStatus(IStatus.ERROR, PhabricatorUiPlugin.ID_PLUGIN,
//								RepositoryStatus.ERROR_EMPTY_PASSWORD,
//								Messages.TracRepositorySettingsPage_auth_failed_missing_credentials, e));
//					}
//					if (!credentials.getUserName().contains("\\")) { //$NON-NLS-1$
//						throw new CoreException(RepositoryStatus.createStatus(repositoryUrl, IStatus.ERROR,
//								PhabricatorUiPlugin.ID_PLUGIN,
//								Messages.TracRepositorySettingsPage_NTLM_authentication_requested_Error));
//					}
//				}
//				throw new CoreException(RepositoryStatus.createStatus(repositoryUrl, IStatus.ERROR,
//						PhabricatorUiPlugin.ID_PLUGIN, INVALID_LOGIN));
//			} catch (PhabricatorPermissionDeniedException e) {
//				throw new CoreException(RepositoryStatus.createStatus(taskRepository, IStatus.ERROR,
//						PhabricatorUiPlugin.ID_PLUGIN, "Insufficient permissions for selected access type.")); //$NON-NLS-1$
//			} catch (PhabricatorException e) {
//				String message = Messages.TracRepositorySettingsPage_No_Trac_repository_found_at_url;
//				if (e.getMessage() != null) {
//					message += ": " + e.getMessage(); //$NON-NLS-1$
//				}
//				throw new CoreException(RepositoryStatus.createStatus(taskRepository, IStatus.ERROR,
//						PhabricatorUiPlugin.ID_PLUGIN, message));
//			}
		}

		public void validate(IProgressMonitor monitor) throws MalformedURLException, PhabricatorException {
			AbstractWebLocation location = new TaskRepositoryLocationFactory().createWebLocation(taskRepository);

			System.out.println(location.getUrl());

//			TracRepositoryInfo info;

//			if (version != null) {
//				ITracClient client = TracClientFactory.createClient(location, version);
//				info = client.validate(monitor);
//			} else {
//				// probe version: XML-RPC access first, then web
//				// access
//				try {
//					version = Version.XML_RPC;
//					ITracClient client = TracClientFactory.createClient(location, version);
//					info = client.validate(monitor);
//				} catch (PhabricatorException e) {
//					try {
//						version = Version.TRAC_0_9;
//						ITracClient client = TracClientFactory.createClient(location, version);
//						info = client.validate(monitor);
//
//						if (e instanceof PhabricatorPermissionDeniedException) {
//							setStatus(RepositoryStatus.createStatus(repositoryUrl, IStatus.INFO,
//									PhabricatorUiPlugin.ID_PLUGIN,
//									Messages.TracRepositorySettingsPage_Authentication_credentials_are_valid));
//						}
//					} catch (PhabricatorLoginException e2) {
//						throw e;
//					} catch (PhabricatorException e2) {
//						throw new PhabricatorException();
//					}
//				}
//				result = version;
//			}
//
//			if (version == Version.XML_RPC //
//					&& (info.isApiVersion(1, 0, 0) //
//							|| (info.isApiVersionOrHigher(1, 0, 3) && info.isApiVersionOrSmaller(1, 0, 5)))) {
//				setStatus(RepositoryStatus.createStatus(repositoryUrl, IStatus.INFO, PhabricatorUiPlugin.ID_PLUGIN,
//						Messages.TracRepositorySettingsPage_Authentication_credentials_valid_Update_to_latest_XmlRpcPlugin_Warning));
//			}

			MultiStatus status = new MultiStatus(PhabricatorUiPlugin.ID_PLUGIN, 0,
					NLS.bind("Validation results for {0}", //$NON-NLS-1$
							taskRepository.getRepositoryLabel()),
					null);
			status.add(new Status(IStatus.INFO, PhabricatorUiPlugin.ID_PLUGIN, NLS.bind("Version: {0}", "ZOOL!"))); //$NON-NLS-1$
//			status.add(new Status(IStatus.INFO, PhabricatorUiPlugin.ID_PLUGIN,
//					NLS.bind("Access Type: {0}", version.toString()))); //$NON-NLS-1$
			StatusHandler.log(status);
		}

//		public Version getResult() {
//			return result;
//		}

	}

	@Override
	protected Validator getValidator(TaskRepository repository) {
		return new PhabricatorValidator(repository);
	}

	@Override
	public String getConnectorKind() {
		return PhabricatorCorePlugin.CONNECTOR_KIND;
	}

}
