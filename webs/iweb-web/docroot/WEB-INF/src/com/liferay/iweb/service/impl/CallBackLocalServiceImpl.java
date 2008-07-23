/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2008 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.iweb.service.impl;

import java.util.Set;

import com.liferay.iweb.IWebException;
import com.liferay.iweb.model.InterestGroup;
import com.liferay.iweb.model.PostEntry;
import com.liferay.iweb.service.base.CallBackLocalServiceBaseImpl;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

/**
 * <a href="CallBackLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Prakash Radhakrishnan
 *
 */
public class CallBackLocalServiceImpl extends CallBackLocalServiceBaseImpl {

	public Set<InterestGroup> getInterestGroupDetails(
			Set<InterestGroup> interestGroups)
		throws IWebException {

		for (InterestGroup ig : interestGroups) {
			try {
				long id = ig.getCid();
				Group group = groupLocalService.getGroup(id);
				ig.setName(group.getName());
				ig.setOpen(group.getType() == 1);
				ig.setUri(group.getFriendlyURL());
			}
			catch (SystemException ex) {
				throw new IWebException(ex);
			}
			catch (PortalException ex) {
				throw new IWebException(ex);
			}
		}
		return interestGroups;
	}

	public int getInterestGroupType(long interestGroupId) throws IWebException {
		try {
			return GroupLocalServiceUtil.getGroup(interestGroupId).getType();
		}
		catch (PortalException e) {
			throw new IWebException(e);
		}
		catch (SystemException e) {
			throw new IWebException(e);
		}
	}

	public Set<PostEntry> getPostEntryDetails(Set<PostEntry> postEntries)
		throws IWebException {

		for (PostEntry postEntry : postEntries) {
			try {
				long id = postEntry.getPid();
				String type = postEntry.getType();
				if (type.equals("blog")) {
					BlogsEntry entry =
						BlogsEntryLocalServiceUtil.getBlogsEntry(id);

					postEntry.setTitle(entry.getTitle());
					postEntry.setContent(entry.getContent());
					postEntry.setInterestGroupId(entry.getGroupId());
					postEntry.setAuthor(entry.getUserName());
				}
				else if (type.equalsIgnoreCase("wiki")) {
					WikiPage wikientry =
						WikiPageLocalServiceUtil.getWikiPage(id);

					postEntry.setTitle(wikientry.getTitle());
					postEntry.setContent(wikientry.getContent());
					postEntry.setInterestGroupId(wikientry.getPageId());
					postEntry.setAuthor(wikientry.getUserName());
				}
			}
			catch (SystemException ex) {
				throw new IWebException(ex);
			}
			catch (PortalException ex) {
				throw new IWebException(ex);
			}
		}
		return postEntries;
	}

}