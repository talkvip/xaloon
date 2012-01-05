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
package org.xaloon.wicket.plugin.user.panel;

import javax.inject.Inject;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.ValidationError;
import org.apache.wicket.validation.validator.AbstractValidator;
import org.xaloon.core.api.plugin.email.EmailFacade;

/**
 * @author vytautas r.
 */
public class EmailPluginEnabledValidator extends AbstractValidator<Void> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private EmailFacade emailFacade;

	/**
	 * Construct.
	 */
	public EmailPluginEnabledValidator() {
		Injector.get().inject(this);
	}

	@Override
	protected void onValidate(IValidatable<Void> validatable) {
		if (!emailFacade.isEnabled()) {
			ValidationError error = new ValidationError();
			error.addMessageKey(EmailFacade.EMAIL_PROPERTIES_NOT_CONFIGURED);
			validatable.error(error);
		}
	}
}
