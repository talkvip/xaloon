/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.xaloon.wicket.plugin.user.admin.panel;

import java.util.List;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.xaloon.core.api.bookmark.Bookmarkable;
import org.xaloon.core.api.security.SecurityAuthorities;
import org.xaloon.core.api.security.model.SecurityGroup;
import org.xaloon.core.api.security.model.SecurityRole;
import org.xaloon.wicket.component.test.MockedApplication;
import org.xaloon.wicket.plugin.user.admin.AbstractUserAdminTestCase;

/**
 * @author vytautas r.
 */
public class GroupDetailPanelTest extends AbstractUserAdminTestCase {

	final List<SecurityRole> availableRoles = newSecurityRoleListWithItems(3);

	WicketTester tester;

	SecurityGroup selectedGroup;

	/**
	 * 
	 */
	@Before
	public void init() {
		String groupPath = "path";

		MockedApplication app = createMockedApplication();
		tester = new WicketTester(app);

		// Set security to True by default
		selectedGroup = newGroup(1L, "group");
		selectedGroup.getRoles().add(newRole(1L, "assignedRole"));
		Mockito.when(app.getSecurityFacade().hasAny(SecurityAuthorities.SYSTEM_ADMINISTRATOR)).thenReturn(true);
		Mockito.when(groupService.getAuthorityByPath(groupPath)).thenReturn(selectedGroup);


		Mockito.when(roleService.getAuthorities(0, -1)).thenReturn(availableRoles);

		PageParameters params = new PageParameters();
		params.add(Bookmarkable.PARAM_PATH, groupPath);
		tester.startComponentInPage(new GroupDetailPanel("id", params));
		Assert.assertNotNull(tester.getTagByWicketId("name"));
		Assert.assertNotNull(tester.getTagByWicketId("role-admin"));

		Assert.assertEquals(1, tester.getTagsByWicketId("roleDetails").size());
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void testNoParams() throws Exception {
		try {
			tester.startComponentInPage(new GroupDetailPanel("id", new PageParameters()));
			Assert.fail();
		} catch (RestartResponseException e) {
			Assert.assertTrue(true);
		}

	}

	/**
	 * @throws Exception
	 */
	@Test
	public void testAssignRole() throws Exception {
		// TEST ASSIGN ROLE TO GROUP
		Assert.assertNotNull(tester.getTagByWicketId("link-assign-entities"));
		tester.clickLink("id:role-admin:choice-management:link-assign-entities");
		tester.assertNoErrorMessage();

		ModalWindow modalWindow = (ModalWindow)tester.getComponentFromLastRenderedPage("id:role-admin:choice-management:modal-assign-entities");
		String modalPath = modalWindow.getPageRelativePath() + ":" + modalWindow.getContentId();
		Assert.assertTrue(modalWindow.isVisible());

		// Submit the form
		String formPath = modalPath + ":form";
		FormTester form = tester.newFormTester(formPath);
		form.selectMultiple("choices", new int[] { 1 });

		Mockito.when(groupService.assignChildren((SecurityGroup)Matchers.anyObject(), Matchers.anyListOf(SecurityRole.class))).thenAnswer(
			new Answer<SecurityGroup>() {

				@SuppressWarnings("unchecked")
				@Override
				public SecurityGroup answer(InvocationOnMock invocation) throws Throwable {
					Object[] args = invocation.getArguments();
					List<SecurityRole> selections = (List<SecurityRole>)args[1];
					Assert.assertEquals(1, selections.size());
					Assert.assertEquals(availableRoles.get(1), selections.get(0));
					return null;
				}
			});
		// Submit ajax form
		tester.executeAjaxEvent(formPath + ":submit", "onclick");
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void testRevokeRole() throws Exception {
		// TEST REVOKE A ROLE FROM GROUP
		Assert.assertNotNull(tester.getTagByWicketId("role-admin"));

		Mockito.when(groupService.revokeChild((SecurityGroup)Matchers.anyObject(), (SecurityRole)Matchers.anyObject())).thenAnswer(
			new Answer<SecurityGroup>() {

				@Override
				public SecurityGroup answer(InvocationOnMock invocation) throws Throwable {
					Object[] args = invocation.getArguments();
					SecurityRole roleToBeRevoked = (SecurityRole)args[1];
					Assert.assertEquals(selectedGroup.getRoles().get(0), roleToBeRevoked);
					return null;
				}
			});
		tester.clickLink("id:role-admin:current-view:0:revoke");
		tester.assertNoErrorMessage();
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void testCloseModalWindow() throws Exception {
		Assert.assertNotNull(tester.getTagByWicketId("link-assign-entities"));
		tester.clickLink("id:role-admin:choice-management:link-assign-entities");
		tester.assertNoErrorMessage();

		ModalWindow modalWindow = (ModalWindow)tester.getComponentFromLastRenderedPage("id:role-admin:choice-management:modal-assign-entities");
		Assert.assertTrue(modalWindow.isVisible());
		closeModalWindow(modalWindow, tester);
	}
}
