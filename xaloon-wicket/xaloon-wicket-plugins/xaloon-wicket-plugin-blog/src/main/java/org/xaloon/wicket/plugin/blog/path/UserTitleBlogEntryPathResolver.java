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
package org.xaloon.wicket.plugin.blog.path;

import javax.inject.Named;

import org.apache.wicket.Page;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.xaloon.core.api.keyvalue.KeyValue;
import org.xaloon.wicket.plugin.blog.BlogConfigElement;
import org.xaloon.wicket.plugin.blog.BlogPageConstants;
import org.xaloon.wicket.plugin.blog.model.BlogEntry;

/**
 * @author vytautas r.
 */
@Named("USERNAME_TITLE")
public class UserTitleBlogEntryPathResolver extends BlogTitlePathResolver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public KeyValue<Class<? extends Page>, PageParameters> getBlogEntryLink(BlogEntry blogEntry) {
		BlogConfigElement cfg = blogPlugin.getTechnicalConfiguration();
		KeyValue<Class<? extends Page>, PageParameters> result = super.getBlogEntryLink(blogEntry);
		result.setKey(cfg.getBlogEntryNoDatePage());
		result.getValue().set(BlogPageConstants.BLOG_USERNAME, blogEntry.getOwner().getUsername());
		return result;
	}

}
