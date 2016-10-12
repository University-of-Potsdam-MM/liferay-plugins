/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package de.unipotsdam.elis.owncloud.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import de.unipotsdam.elis.owncloud.model.CustomSiteRootFolder;

/**
 * The persistence interface for the custom site root folder service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Matthias
 * @see CustomSiteRootFolderPersistenceImpl
 * @see CustomSiteRootFolderUtil
 * @generated
 */
public interface CustomSiteRootFolderPersistence extends BasePersistence<CustomSiteRootFolder> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CustomSiteRootFolderUtil} to access the custom site root folder persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the custom site root folder in the entity cache if it is enabled.
	*
	* @param customSiteRootFolder the custom site root folder
	*/
	public void cacheResult(
		de.unipotsdam.elis.owncloud.model.CustomSiteRootFolder customSiteRootFolder);

	/**
	* Caches the custom site root folders in the entity cache if it is enabled.
	*
	* @param customSiteRootFolders the custom site root folders
	*/
	public void cacheResult(
		java.util.List<de.unipotsdam.elis.owncloud.model.CustomSiteRootFolder> customSiteRootFolders);

	/**
	* Creates a new custom site root folder with the primary key. Does not add the custom site root folder to the database.
	*
	* @param customSiteRootFolderPK the primary key for the new custom site root folder
	* @return the new custom site root folder
	*/
	public de.unipotsdam.elis.owncloud.model.CustomSiteRootFolder create(
		CustomSiteRootFolderPK customSiteRootFolderPK);

	/**
	* Removes the custom site root folder with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param customSiteRootFolderPK the primary key of the custom site root folder
	* @return the custom site root folder that was removed
	* @throws de.unipotsdam.elis.owncloud.NoSuchCustomSiteRootFolderException if a custom site root folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.owncloud.model.CustomSiteRootFolder remove(
		CustomSiteRootFolderPK customSiteRootFolderPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.owncloud.NoSuchCustomSiteRootFolderException;

	public de.unipotsdam.elis.owncloud.model.CustomSiteRootFolder updateImpl(
		de.unipotsdam.elis.owncloud.model.CustomSiteRootFolder customSiteRootFolder)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the custom site root folder with the primary key or throws a {@link de.unipotsdam.elis.owncloud.NoSuchCustomSiteRootFolderException} if it could not be found.
	*
	* @param customSiteRootFolderPK the primary key of the custom site root folder
	* @return the custom site root folder
	* @throws de.unipotsdam.elis.owncloud.NoSuchCustomSiteRootFolderException if a custom site root folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.owncloud.model.CustomSiteRootFolder findByPrimaryKey(
		CustomSiteRootFolderPK customSiteRootFolderPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.owncloud.NoSuchCustomSiteRootFolderException;

	/**
	* Returns the custom site root folder with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param customSiteRootFolderPK the primary key of the custom site root folder
	* @return the custom site root folder, or <code>null</code> if a custom site root folder with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.owncloud.model.CustomSiteRootFolder fetchByPrimaryKey(
		CustomSiteRootFolderPK customSiteRootFolderPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the custom site root folders.
	*
	* @return the custom site root folders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<de.unipotsdam.elis.owncloud.model.CustomSiteRootFolder> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the custom site root folders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.owncloud.model.impl.CustomSiteRootFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of custom site root folders
	* @param end the upper bound of the range of custom site root folders (not inclusive)
	* @return the range of custom site root folders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<de.unipotsdam.elis.owncloud.model.CustomSiteRootFolder> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the custom site root folders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.owncloud.model.impl.CustomSiteRootFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of custom site root folders
	* @param end the upper bound of the range of custom site root folders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of custom site root folders
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<de.unipotsdam.elis.owncloud.model.CustomSiteRootFolder> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the custom site root folders from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of custom site root folders.
	*
	* @return the number of custom site root folders
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}