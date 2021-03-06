/*******************************************************************************
 * Copyright (c) 2010, 2011 Ed Anuff and Usergrid, all rights reserved.
 * http://www.usergrid.com
 * 
 * This file is part of Usergrid Core.
 * 
 * Usergrid Core is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * Usergrid Core is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * Usergrid Core. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package org.usergrid.persistence;

import java.util.Map;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * The interface class that specifies the operations that can be performed on
 * the Usergrid Datastore. This interface is designed to be implemented by
 * different backends. Although these operations are meant to take advantage of
 * the capabilities of Cassandra, they should be implementable using other
 * relational databases such as MySql or NoSQL databases such as GAE or MongoDB.
 */
public interface EntityManagerFactory {

	/**
	 * A string description provided by the implementing class.
	 * 
	 * @return description text
	 * @throws Exception
	 *             the exception
	 */
	public abstract String getImpementationDescription() throws Exception;

	/**
	 * Gets the entity manager.
	 * 
	 * @param applicationId
	 *            the application id
	 * @return EntityDao for the specfied parameters
	 */
	public abstract EntityManager getEntityManager(UUID applicationId);

	/**
	 * Creates a new application.
	 * 
	 * @param name
	 *            a unique application name.
	 * @return the newly created application id.
	 * @throws Exception
	 *             the exception
	 */
	public abstract UUID createApplication(String name) throws Exception;

	/**
	 * Creates a Application entity. All entities except for applications must be
	 * attached to a Application.
	 * 
	 * @param name
	 *            the name of the application to create.
	 * @param properties
	 *            property values to create in the new entity or null.
	 * @return the newly created application id.
	 * @throws Exception
	 *             the exception
	 */
	public abstract UUID createApplication(String name,
			Map<String, Object> properties) throws Exception;

	public abstract UUID importApplication(UUID applicationId, String name,
			Map<String, Object> properties) throws Exception;

	/**
	 * Returns the application id for the application name.
	 * 
	 * @param name
	 *            a unique application name.
	 * @return the Application id or null.
	 * @throws Exception
	 *             the exception
	 */
	public abstract UUID lookupApplication(String name) throws Exception;

	/**
	 * Returns all the applications in the system.
	 * 
	 * @return all the applications.
	 * @throws Exception
	 *             the exception
	 */
	public abstract Map<String, UUID> getApplications() throws Exception;

	public abstract void setup() throws Exception;

	public abstract Map<String, String> getServiceProperties();

	public abstract boolean updateServiceProperties(Map<String, String> properties);

	public abstract boolean setServiceProperty(String name, String value);

	public abstract boolean deleteServiceProperty(String name);
}
