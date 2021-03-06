package org.xaloon.wicket.plugin.user.admin;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.util.tester.WicketTester;
import org.xaloon.core.api.security.AuthorityService;
import org.xaloon.core.api.security.GroupService;
import org.xaloon.core.api.security.RoleService;
import org.xaloon.core.api.security.model.Authority;
import org.xaloon.core.api.security.model.SecurityGroup;
import org.xaloon.core.api.security.model.SecurityRole;
import org.xaloon.core.api.security.model.UserDetails;
import org.xaloon.core.api.user.model.User;
import org.xaloon.core.api.util.UrlUtil;
import org.xaloon.core.jpa.security.model.JpaAuthority;
import org.xaloon.core.jpa.security.model.JpaGroup;
import org.xaloon.core.jpa.security.model.JpaRole;
import org.xaloon.wicket.component.test.MockedApplication;
import org.xaloon.wicket.plugin.system.SystemPlugin;
import org.xaloon.wicket.plugin.system.SystemPluginBean;

/**
 * @author vytautas r.
 */
public abstract class AbstractUserAdminTestCase {
	protected RoleService roleService = mock(RoleService.class);
	protected GroupService groupService = mock(GroupService.class);
	protected AuthorityService authorityService = mock(AuthorityService.class);


	protected UserDetails details = mock(UserDetails.class);

	protected MockedApplication createMockedApplication() {
		MockedApplication app = new MockedApplication();
		app.getMockedServices().put(RoleService.class.getName(), roleService);
		app.getMockedServices().put(GroupService.class.getName(), groupService);
		app.getMockedServices().put(AuthorityService.class.getName(), authorityService);

		SystemPluginBean systemPluginBean = new SystemPluginBean();

		when(app.getPluginRegistry().getPluginBean(SystemPlugin.class)).thenReturn(systemPluginBean);
		when(app.getPluginRegistry().isEnabled(SystemPlugin.class)).thenReturn(true);


		when(app.getUserFacade().loadUserDetails("demo")).thenReturn(details);

		User user = mock(User.class);
		when(app.getUserFacade().getUserByUsername("demo")).thenReturn(user);

		return app;
	}

	protected void closeModalWindow(ModalWindow modal, WicketTester tester) {
		for (Behavior behavior : modal.getBehaviors()) {
			if (behavior instanceof AbstractDefaultAjaxBehavior) {
				String name = behavior.getClass().getSimpleName();
				if (name.startsWith("WindowClosedBehavior")) {
					tester.executeBehavior((AbstractAjaxBehavior)behavior);
				}
			}
		}
	}

	protected SecurityRole newRole(Long id, String name) {
		SecurityRole role = new JpaRole();
		role.setId(id);
		role.setName(name);
		role.setPath(UrlUtil.encode(name));
		return role;
	}

	protected Authority newAuthority(Long id, String name) {
		Authority item = new JpaAuthority();
		item.setId(id);
		item.setName(name);
		item.setPath(UrlUtil.encode(name));
		return item;
	}

	protected SecurityGroup newGroup(Long id, String name) {
		SecurityGroup item = new JpaGroup();
		item.setId(id);
		item.setName(name);
		item.setPath(UrlUtil.encode(name));
		return item;
	}

	protected List<SecurityGroup> newSecurityGroupListWithItems(int count) {
		List<SecurityGroup> groups = new ArrayList<SecurityGroup>();
		for (int i = 0; i < count; i++) {
			groups.add(newGroup(new Long(i), "name" + i));
		}
		return groups;
	}

	protected List<SecurityRole> newSecurityRoleListWithItems(int count) {
		List<SecurityRole> groups = new ArrayList<SecurityRole>();
		for (int i = 0; i < count; i++) {
			groups.add(newRole(new Long(i), "name" + i));
		}
		return groups;
	}

	protected List<Authority> newSecurityAuthorityListWithItems(int count) {
		List<Authority> items = new ArrayList<Authority>();
		for (int i = 0; i < count; i++) {
			items.add(newAuthority(new Long(i), "name" + i));
		}
		return items;
	}
}
