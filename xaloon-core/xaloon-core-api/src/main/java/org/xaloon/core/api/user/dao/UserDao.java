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
package org.xaloon.core.api.user.dao;

import java.io.Serializable;

import org.xaloon.core.api.user.model.User;

/**
 * @author vytautas r.
 */
public interface UserDao extends Serializable {
	/**
	 * @param user
	 */
	<T extends User> void save(T user);

	/**
	 * @param username
	 * @return user instance found in database
	 */
	<T extends User> T getUserByUsername(String username);
	
	/**
	 * @param email
	 * @return user instance found in database
	 */
	<T extends User> T getUserByEmail(String email);

	/**
	 * @return new instance of user
	 */
	<T extends User> T newUser();

	/**
	 * Creates new anonymous user entity
	 * 
	 * @return new instance of anonymous user
	 */
	<T extends User> T newAnonymousUser();

	/**
	 * Creates new anonymous user entity from provided details
	 * 
	 * @param currentUser
	 * @return new instance of anonymous user
	 */
	<T extends User> T newAnonymousUser(T currentUser);
}