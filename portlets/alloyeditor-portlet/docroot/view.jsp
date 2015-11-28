<%
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
%>

<link href="<%=request.getContextPath()%>/alloy-editor/assets/alloy-editor-ocean-min.css" rel="stylesheet">

<script src="<%=request.getContextPath()%>/alloy-editor/alloy-editor-all.js">
</script>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>

<div id="alloyeditor">

This is the <b>Alloyeditor</b> portlet.</div>
 
<portlet:defineObjects />

<script type="text/javascript">
AlloyEditor.editable('alloyeditor');
</script>
