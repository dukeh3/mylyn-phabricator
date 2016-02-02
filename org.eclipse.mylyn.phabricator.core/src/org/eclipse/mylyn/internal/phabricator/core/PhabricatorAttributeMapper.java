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

package org.eclipse.mylyn.internal.phabricator.core;

import java.util.EnumSet;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.mylyn.tasks.core.IRepositoryPerson;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.core.data.TaskAttribute;
import org.eclipse.mylyn.tasks.core.data.TaskAttributeMapper;

/**
 * Provides a mapping from Mylyn task keys to Trac ticket keys.
 *
 * @author Steffen Pingel
 */
public class PhabricatorAttributeMapper extends TaskAttributeMapper {

	public enum Flag {
		READ_ONLY, ATTRIBUTE, PEOPLE
	};

	public static final String NEW_CC = "task.common.newcc"; //$NON-NLS-1$

	public static final String REMOVE_CC = "task.common.removecc"; //$NON-NLS-1$

	public static final EnumSet<Flag> NO_FLAGS = EnumSet.noneOf(Flag.class);

//	private final ITracClient client;

//	public static boolean isInternalAttribute(TaskAttribute attribute) {
//		String type = attribute.getMetaData().getType();
//		if (TaskAttribute.TYPE_ATTACHMENT.equals(type) || TaskAttribute.TYPE_OPERATION.equals(type)
//				|| TaskAttribute.TYPE_COMMENT.equals(type)) {
//			return true;
//		}
//		String id = attribute.getId();
//		return TaskAttribute.COMMENT_NEW.equals(id) || TaskAttribute.ADD_SELF_CC.equals(id) || REMOVE_CC.equals(id)
//				|| NEW_CC.equals(id);
//	}

//	http://s02.labs.h3.se/ticket/22

	public PhabricatorAttributeMapper(TaskRepository taskRepository) {
		super(taskRepository);
//		Assert.isNotNull(client);
//		this.client = client;
	}

	@Override
	public String mapToRepositoryKey(@NonNull TaskAttribute parent, @NonNull String key) {
		// ignore
		System.out.println("PhabricatorAttributeMapper.mapToRepositoryKey(" + parent + "," + key + ")");
		String mapToRepositoryKey = super.mapToRepositoryKey(parent, key);

		System.out.println(mapToRepositoryKey);

		return mapToRepositoryKey;
	}

	@Override
	@Nullable
	public String getLabel(@NonNull TaskAttribute taskAttribute) {
		// ignore
		System.out.println("PhabricatorAttributeMapper.getLabel()");
		return super.getLabel(taskAttribute);
	}

	@Override
	@NonNull
	public IRepositoryPerson getRepositoryPerson(@NonNull TaskAttribute taskAttribute) {
		// ignore
		System.out.println("PhabricatorAttributeMapper.getRepositoryPerson()");
		return super.getRepositoryPerson(taskAttribute);
	}

//	@Override
//	public Date getDateValue(TaskAttribute attribute) {
//		return TracUtil.parseDate(attribute.getValue());
//	}
//
//	@Override
//	public String mapToRepositoryKey(TaskAttribute parent, String key) {
//		TracAttribute attribute = TracAttribute.getByTaskKey(key);
//		return (attribute != null) ? attribute.getTracKey() : key;
//	}
//
//	@Override
//	public void setDateValue(TaskAttribute attribute, Date date) {
//		if (date == null) {
//			attribute.clearValues();
//		} else {
//			attribute.setValue(TracUtil.toTracTime(date) + ""); //$NON-NLS-1$
//		}
//	}

	@Override
	@NonNull
	public Map<String, String> getOptions(@NonNull TaskAttribute attribute) {
		System.out.println("PhabricatorAttributeMapper.getOptions()");

		Map<String, String> options = super.getOptions(attribute);
		return options;
	}

//	@Override
//	public Map<String, String> getOptions(TaskAttribute attribute) {
////		Map<String, String> options = getRepositoryOptions(client, attribute.getId());
////		return (options != null) ? options : super.getOptions(attribute);
//
//		return super(attribute);
//
//	}

//	public static Map<String, String> getRepositoryOptions(ITracClient client, String trackKey) {
//		if (client.hasAttributes()) {
//			if (TracAttribute.STATUS.getTracKey().equals(trackKey)) {
//				return getOptions(client.getTicketStatus(), false);
//			} else if (TracAttribute.RESOLUTION.getTracKey().equals(trackKey)) {
//				return getOptions(client.getTicketResolutions(), false);
//			} else if (TracAttribute.COMPONENT.getTracKey().equals(trackKey)) {
//				return getOptions(client.getComponents(), false);
//			} else if (TracAttribute.VERSION.getTracKey().equals(trackKey)) {
//				return getOptions(client.getVersions(), true);
//			} else if (TracAttribute.PRIORITY.getTracKey().equals(trackKey)) {
//				return getOptions(client.getPriorities(), false);
//			} else if (TracAttribute.SEVERITY.getTracKey().equals(trackKey)) {
//				return getOptions(client.getSeverities(), false);
//			} else if (TracAttribute.MILESTONE.getTracKey().equals(trackKey)) {
//				return getOptions(client.getMilestones(), true);
//			} else if (TracAttribute.TYPE.getTracKey().equals(trackKey)) {
//				return getOptions(client.getTicketTypes(), false);
//			}
//		}
//		return null;
//	}
//
//	private static Map<String, String> getOptions(Object[] values, boolean allowEmpty) {
//		if (values != null && values.length > 0) {
//			Map<String, String> options = new LinkedHashMap<String, String>();
//			if (allowEmpty) {
//				options.put("", ""); //$NON-NLS-1$ //$NON-NLS-2$
//			}
//			for (Object value : values) {
//				options.put(value.toString(), value.toString());
//			}
//			return options;
//		}
//		return null;
//	}

}
