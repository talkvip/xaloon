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
package org.xaloon.core.api.plugin.comment;

import java.io.Serializable;
import java.util.List;

/**
 * @author vytautas r.
 */
public interface CommentDao extends Serializable {
	/**
	 * Property for counter to identify that comment count should be incremented
	 */
	String COMMENT_COUNT = "comment_count";

	/**
	 * Save comment to storage
	 * 
	 * @param comment
	 */
	void save(Comment comment);

	/**
	 * Retrieve comment list for selected commentable object
	 * 
	 * @param commentable
	 * @param first
	 * @param count
	 * @return list of comments
	 */
	List<Comment> getComments(Commentable commentable, int first, int count);

	/**
	 * Delete all comments. NOT RECCOMENDED!
	 */
	void deleteAll();

	/**
	 * Retrieve count of selected commentable object
	 * 
	 * @param commentable
	 * @return how many comments there are by commentable object
	 */
	Long count(Commentable commentable);

	/**
	 * Load concrete comment by id
	 * 
	 * @param id
	 * @return comment instance by id
	 */
	Comment load(Long id);

	/**
	 * Retrieve all comments, waiting for approval of administrator
	 * 
	 * @return list of waiting comments
	 */
	List<Comment> getWaitingCommentsForApproval();

	/**
	 * Delete concrete comment
	 * 
	 * @param comment
	 */
	void delete(Comment comment);

	/**
	 * Modify comment and save
	 * 
	 * @param comment
	 * @param enabled
	 */
	void enable(Comment comment, boolean enabled);

	/**
	 * Delete all comments, which are still waiting for approval. Should be a spam.
	 */
	void deleteWaitingCommentsForApproval();

	/**
	 * @return create new instance of Comment persistable object
	 */
	Comment newComment();
}
