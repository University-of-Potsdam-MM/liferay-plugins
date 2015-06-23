<nav class="${nav_css_class}" id="navigation" role="navigation">
	<ul aria-label="#language ("site-pages")" role="menubar">
		<#list nav_items as nav_item >
			<#assign nav_item_attr_has_popup="">
			<#assign nav_item_attr_selected="">
			<#assign nav_item_css_class = "">

			<#if nav_item.isSelected()>
				<#assign nav_item_attr_selected="aria-selected='true'">
				<#assign nav_item_css_class = "selected" >
			</#if>
	
			<#if nav_item.hasChildren()>
				<#assign nav_item_attr_has_popup="aria-haspopup='true'">
			</#if>
			
			<#-- Custom Logic to seperate portfolio sites in users private site -->
			<#assign portfoliopage = nav_item.getLayout().getExpandoBridge().getAttribute("Portfolio") />
			<#assign currentportfoliopage = layout.getExpandoBridge().getAttribute("Portfolio") />
			<#if (currentportfoliopage?string("true", "false") = "false" && portfoliopage?string("true", "false") = "false") || (currentportfoliopage?string("true", "false") = "true" && portfoliopage?string("true", "false") = "true")>
			
			<li class="${nav_item_css_class}" id="layout_${nav_item.getLayoutId()}" ${nav_item_attr_selected} role="presentation">
				<a aria-labelledby="layout_${nav_item.getLayoutId()}" href="${nav_item.getURL()}" ${nav_item_attr_has_popup} ${nav_item.getTarget()} role="menuitem"><span>${nav_item.icon()} ${nav_item.getName()}</span></a>
			
			<#if nav_item.hasChildren()>
			<ul class="child-menu" role="menu">
				<#list nav_item.getChildren() as nav_child>
							<#assign nav_child_attr_selected="">
							<#assign nav_child_css_class = "false">

							<#if nav_child.isSelected()>
								<#assign nav_child_attr_selected="aria-selected='true'">
								<#assign nav_child_css_class = "selected">
							</#if>

							<li class="${nav_child_css_class}" id="layout_${nav_child.getLayoutId()}" ${nav_child_attr_selected} role="presentation">
								<a aria-labelledby="layout_${nav_child.getLayoutId()}" href="${nav_child.getURL()}" ${nav_child.getTarget()} role="menuitem">${nav_child.getName()}</a>
							</li>
						</#list>
				
				
				
				
				
				</ul>
				</#if>
			</li>
										</#if>
		</#list>
	</ul>
</nav>