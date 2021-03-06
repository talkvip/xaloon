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
package org.xaloon.wicket.plugin.system.panel;

import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.xaloon.wicket.plugin.admin.AbstractPluginAdministrationPanel;
import org.xaloon.wicket.plugin.system.SystemPlugin;
import org.xaloon.wicket.plugin.system.SystemPluginBean;

/**
 * @author vytautas r.
 */
public class SystemAdministrationPanel extends AbstractPluginAdministrationPanel<SystemPluginBean, SystemPlugin> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construct.
	 * 
	 * @param id
	 */
	public SystemAdministrationPanel(String id) {
		super(id);
	}

	@Override
	protected void onInitialize(SystemPlugin plugin, SystemPluginBean pluginBean) {
		add(new CheckBox("userRegistrationEnabled"));
		add(new RequiredTextField<String>("shortDateFormat"));
		add(new RequiredTextField<String>("longDateFormat"));
		add(new RequiredTextField<String>("temporaryFileLocation"));
	}
}
