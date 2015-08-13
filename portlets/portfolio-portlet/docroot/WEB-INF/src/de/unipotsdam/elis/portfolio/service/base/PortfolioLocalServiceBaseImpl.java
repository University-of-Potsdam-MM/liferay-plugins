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

package de.unipotsdam.elis.portfolio.service.base;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.service.BaseLocalServiceImpl;
import com.liferay.portal.service.PersistedModelLocalServiceRegistryUtil;
import com.liferay.portal.service.persistence.UserPersistence;

import de.unipotsdam.elis.portfolio.model.Portfolio;
import de.unipotsdam.elis.portfolio.service.PortfolioLocalService;
import de.unipotsdam.elis.portfolio.service.persistence.PortfolioFeedbackFinder;
import de.unipotsdam.elis.portfolio.service.persistence.PortfolioFeedbackPersistence;
import de.unipotsdam.elis.portfolio.service.persistence.PortfolioFinder;
import de.unipotsdam.elis.portfolio.service.persistence.PortfolioPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the portfolio local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link de.unipotsdam.elis.portfolio.service.impl.PortfolioLocalServiceImpl}.
 * </p>
 *
 * @author Matthias
 * @see de.unipotsdam.elis.portfolio.service.impl.PortfolioLocalServiceImpl
 * @see de.unipotsdam.elis.portfolio.service.PortfolioLocalServiceUtil
 * @generated
 */
public abstract class PortfolioLocalServiceBaseImpl extends BaseLocalServiceImpl
	implements PortfolioLocalService, IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link de.unipotsdam.elis.portfolio.service.PortfolioLocalServiceUtil} to access the portfolio local service.
	 */

	/**
	 * Adds the portfolio to the database. Also notifies the appropriate model listeners.
	 *
	 * @param portfolio the portfolio
	 * @return the portfolio that was added
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public Portfolio addPortfolio(Portfolio portfolio)
		throws SystemException {
		portfolio.setNew(true);

		return portfolioPersistence.update(portfolio);
	}

	/**
	 * Creates a new portfolio with the primary key. Does not add the portfolio to the database.
	 *
	 * @param plid the primary key for the new portfolio
	 * @return the new portfolio
	 */
	@Override
	public Portfolio createPortfolio(long plid) {
		return portfolioPersistence.create(plid);
	}

	/**
	 * Deletes the portfolio with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param plid the primary key of the portfolio
	 * @return the portfolio that was removed
	 * @throws PortalException if a portfolio with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public Portfolio deletePortfolio(long plid)
		throws PortalException, SystemException {
		return portfolioPersistence.remove(plid);
	}

	/**
	 * Deletes the portfolio from the database. Also notifies the appropriate model listeners.
	 *
	 * @param portfolio the portfolio
	 * @return the portfolio that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public Portfolio deletePortfolio(Portfolio portfolio)
		throws SystemException {
		return portfolioPersistence.remove(portfolio);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(Portfolio.class,
			clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return portfolioPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.portfolio.model.impl.PortfolioModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return portfolioPersistence.findWithDynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.portfolio.model.impl.PortfolioModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return portfolioPersistence.findWithDynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	 * Returns the number of rows that match the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows that match the dynamic query
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery)
		throws SystemException {
		return portfolioPersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows that match the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows that match the dynamic query
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection) throws SystemException {
		return portfolioPersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public Portfolio fetchPortfolio(long plid) throws SystemException {
		return portfolioPersistence.fetchByPrimaryKey(plid);
	}

	/**
	 * Returns the portfolio with the primary key.
	 *
	 * @param plid the primary key of the portfolio
	 * @return the portfolio
	 * @throws PortalException if a portfolio with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Portfolio getPortfolio(long plid)
		throws PortalException, SystemException {
		return portfolioPersistence.findByPrimaryKey(plid);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException, SystemException {
		return portfolioPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the portfolios.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.portfolio.model.impl.PortfolioModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of portfolios
	 * @param end the upper bound of the range of portfolios (not inclusive)
	 * @return the range of portfolios
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Portfolio> getPortfolios(int start, int end)
		throws SystemException {
		return portfolioPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of portfolios.
	 *
	 * @return the number of portfolios
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getPortfoliosCount() throws SystemException {
		return portfolioPersistence.countAll();
	}

	/**
	 * Updates the portfolio in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param portfolio the portfolio
	 * @return the portfolio that was updated
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public Portfolio updatePortfolio(Portfolio portfolio)
		throws SystemException {
		return portfolioPersistence.update(portfolio);
	}

	/**
	 * Returns the portfolio local service.
	 *
	 * @return the portfolio local service
	 */
	public de.unipotsdam.elis.portfolio.service.PortfolioLocalService getPortfolioLocalService() {
		return portfolioLocalService;
	}

	/**
	 * Sets the portfolio local service.
	 *
	 * @param portfolioLocalService the portfolio local service
	 */
	public void setPortfolioLocalService(
		de.unipotsdam.elis.portfolio.service.PortfolioLocalService portfolioLocalService) {
		this.portfolioLocalService = portfolioLocalService;
	}

	/**
	 * Returns the portfolio remote service.
	 *
	 * @return the portfolio remote service
	 */
	public de.unipotsdam.elis.portfolio.service.PortfolioService getPortfolioService() {
		return portfolioService;
	}

	/**
	 * Sets the portfolio remote service.
	 *
	 * @param portfolioService the portfolio remote service
	 */
	public void setPortfolioService(
		de.unipotsdam.elis.portfolio.service.PortfolioService portfolioService) {
		this.portfolioService = portfolioService;
	}

	/**
	 * Returns the portfolio persistence.
	 *
	 * @return the portfolio persistence
	 */
	public PortfolioPersistence getPortfolioPersistence() {
		return portfolioPersistence;
	}

	/**
	 * Sets the portfolio persistence.
	 *
	 * @param portfolioPersistence the portfolio persistence
	 */
	public void setPortfolioPersistence(
		PortfolioPersistence portfolioPersistence) {
		this.portfolioPersistence = portfolioPersistence;
	}

	/**
	 * Returns the portfolio finder.
	 *
	 * @return the portfolio finder
	 */
	public PortfolioFinder getPortfolioFinder() {
		return portfolioFinder;
	}

	/**
	 * Sets the portfolio finder.
	 *
	 * @param portfolioFinder the portfolio finder
	 */
	public void setPortfolioFinder(PortfolioFinder portfolioFinder) {
		this.portfolioFinder = portfolioFinder;
	}

	/**
	 * Returns the portfolio feedback local service.
	 *
	 * @return the portfolio feedback local service
	 */
	public de.unipotsdam.elis.portfolio.service.PortfolioFeedbackLocalService getPortfolioFeedbackLocalService() {
		return portfolioFeedbackLocalService;
	}

	/**
	 * Sets the portfolio feedback local service.
	 *
	 * @param portfolioFeedbackLocalService the portfolio feedback local service
	 */
	public void setPortfolioFeedbackLocalService(
		de.unipotsdam.elis.portfolio.service.PortfolioFeedbackLocalService portfolioFeedbackLocalService) {
		this.portfolioFeedbackLocalService = portfolioFeedbackLocalService;
	}

	/**
	 * Returns the portfolio feedback remote service.
	 *
	 * @return the portfolio feedback remote service
	 */
	public de.unipotsdam.elis.portfolio.service.PortfolioFeedbackService getPortfolioFeedbackService() {
		return portfolioFeedbackService;
	}

	/**
	 * Sets the portfolio feedback remote service.
	 *
	 * @param portfolioFeedbackService the portfolio feedback remote service
	 */
	public void setPortfolioFeedbackService(
		de.unipotsdam.elis.portfolio.service.PortfolioFeedbackService portfolioFeedbackService) {
		this.portfolioFeedbackService = portfolioFeedbackService;
	}

	/**
	 * Returns the portfolio feedback persistence.
	 *
	 * @return the portfolio feedback persistence
	 */
	public PortfolioFeedbackPersistence getPortfolioFeedbackPersistence() {
		return portfolioFeedbackPersistence;
	}

	/**
	 * Sets the portfolio feedback persistence.
	 *
	 * @param portfolioFeedbackPersistence the portfolio feedback persistence
	 */
	public void setPortfolioFeedbackPersistence(
		PortfolioFeedbackPersistence portfolioFeedbackPersistence) {
		this.portfolioFeedbackPersistence = portfolioFeedbackPersistence;
	}

	/**
	 * Returns the portfolio feedback finder.
	 *
	 * @return the portfolio feedback finder
	 */
	public PortfolioFeedbackFinder getPortfolioFeedbackFinder() {
		return portfolioFeedbackFinder;
	}

	/**
	 * Sets the portfolio feedback finder.
	 *
	 * @param portfolioFeedbackFinder the portfolio feedback finder
	 */
	public void setPortfolioFeedbackFinder(
		PortfolioFeedbackFinder portfolioFeedbackFinder) {
		this.portfolioFeedbackFinder = portfolioFeedbackFinder;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.service.ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.service.ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user remote service.
	 *
	 * @return the user remote service
	 */
	public com.liferay.portal.service.UserService getUserService() {
		return userService;
	}

	/**
	 * Sets the user remote service.
	 *
	 * @param userService the user remote service
	 */
	public void setUserService(
		com.liferay.portal.service.UserService userService) {
		this.userService = userService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public void afterPropertiesSet() {
		Class<?> clazz = getClass();

		_classLoader = clazz.getClassLoader();

		PersistedModelLocalServiceRegistryUtil.register("de.unipotsdam.elis.portfolio.model.Portfolio",
			portfolioLocalService);
	}

	public void destroy() {
		PersistedModelLocalServiceRegistryUtil.unregister(
			"de.unipotsdam.elis.portfolio.model.Portfolio");
	}

	/**
	 * Returns the Spring bean ID for this bean.
	 *
	 * @return the Spring bean ID for this bean
	 */
	@Override
	public String getBeanIdentifier() {
		return _beanIdentifier;
	}

	/**
	 * Sets the Spring bean ID for this bean.
	 *
	 * @param beanIdentifier the Spring bean ID for this bean
	 */
	@Override
	public void setBeanIdentifier(String beanIdentifier) {
		_beanIdentifier = beanIdentifier;
	}

	@Override
	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		if (contextClassLoader != _classLoader) {
			currentThread.setContextClassLoader(_classLoader);
		}

		try {
			return _clpInvoker.invokeMethod(name, parameterTypes, arguments);
		}
		finally {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	protected Class<?> getModelClass() {
		return Portfolio.class;
	}

	protected String getModelClassName() {
		return Portfolio.class.getName();
	}

	/**
	 * Performs an SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) throws SystemException {
		try {
			DataSource dataSource = portfolioPersistence.getDataSource();

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql, new int[0]);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = de.unipotsdam.elis.portfolio.service.PortfolioLocalService.class)
	protected de.unipotsdam.elis.portfolio.service.PortfolioLocalService portfolioLocalService;
	@BeanReference(type = de.unipotsdam.elis.portfolio.service.PortfolioService.class)
	protected de.unipotsdam.elis.portfolio.service.PortfolioService portfolioService;
	@BeanReference(type = PortfolioPersistence.class)
	protected PortfolioPersistence portfolioPersistence;
	@BeanReference(type = PortfolioFinder.class)
	protected PortfolioFinder portfolioFinder;
	@BeanReference(type = de.unipotsdam.elis.portfolio.service.PortfolioFeedbackLocalService.class)
	protected de.unipotsdam.elis.portfolio.service.PortfolioFeedbackLocalService portfolioFeedbackLocalService;
	@BeanReference(type = de.unipotsdam.elis.portfolio.service.PortfolioFeedbackService.class)
	protected de.unipotsdam.elis.portfolio.service.PortfolioFeedbackService portfolioFeedbackService;
	@BeanReference(type = PortfolioFeedbackPersistence.class)
	protected PortfolioFeedbackPersistence portfolioFeedbackPersistence;
	@BeanReference(type = PortfolioFeedbackFinder.class)
	protected PortfolioFeedbackFinder portfolioFeedbackFinder;
	@BeanReference(type = com.liferay.counter.service.CounterLocalService.class)
	protected com.liferay.counter.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.portal.service.ResourceLocalService.class)
	protected com.liferay.portal.service.ResourceLocalService resourceLocalService;
	@BeanReference(type = com.liferay.portal.service.UserLocalService.class)
	protected com.liferay.portal.service.UserLocalService userLocalService;
	@BeanReference(type = com.liferay.portal.service.UserService.class)
	protected com.liferay.portal.service.UserService userService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private String _beanIdentifier;
	private ClassLoader _classLoader;
	private PortfolioLocalServiceClpInvoker _clpInvoker = new PortfolioLocalServiceClpInvoker();
}