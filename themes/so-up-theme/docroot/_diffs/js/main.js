AUI().ready(
	'aui-base',
	'aui-io-request',
	'event',
	function(A) {
		var memberButton = A.one('#memberButton');

		if (memberButton) {
			memberButton.on(
				'click',
				function(event) {
					event.preventDefault();

					A.io.request(
						event.currentTarget.attr('href'),
						{
							on: {
								success: function(event, id, obj) {
									window.location.reload();
								}
							}
						}
					);
				}
			);
		}
		
		var body = A.one('body');
		
		if(body)
		{
			A.on('scroll', function(e) {
				if(window.scrollY > 150)
				{
					body.addClass("smallhead");
				}
				else
				{
					body.removeClass("smallhead");
				}
		      });
		}
		
		var sidebar = A.one('#sidebar');
		
		if(sidebar) {
			var title = sidebar.one('.title');
			
			if(title){
				title.on('click', function(event){
					event.preventDefault();
					sidebar.toggleClass('show-sidebar');
					title.one('.arrow').toggleClass('down');
				});
			}

		}
		
		var filterBox = A.one('#grouproom-filter');
		
		if(filterBox){
			filterBox.on('keyup', function(event){
				event.preventDefault();
				var val = filterBox.val().toLowerCase();
				
				var list = A.one('#sidebar-my-sites');
				list.all('li').each(function(){
					if(this.text().toLowerCase().contains(val))
						this.removeClass("hidden")
					else
						this.addClass("hidden");
				})
			})
		}	
		
		var workspacePageSelectOwn = A.one('.switch #own');
		var workspacePageSelectPublic = A.one('.switch #public');
		
		if(workspacePageSelectOwn)
		{
			workspacePageSelectOwn.on('change', function(event){
				A.one('#sidebar-my-sites').removeClass('hidden');
				A.one('#sidebar-all-sites').addClass('hidden');
			});
		}
		if(workspacePageSelectPublic)
		{
			workspacePageSelectPublic.on('change', function(event){
				A.one('#sidebar-my-sites').addClass('hidden');
				A.one('#sidebar-all-sites').removeClass('hidden');
			});
		}		
		
		var up_services = A.one('#up-general .up_services');
		
		if(up_services)
		{
			up_services.on('click', function(event){
				event.preventDefault();
				up_services.siblings('ul').toggleClass('hidden');
			})
		}
		
		var search = A.all('#up-general .search-img');
		var searchfield = A.one('#up-general #searchfield')
		if(search && searchfield)
		{
			search.on('click', function(event){
				event.preventDefault();
				searchfield.toggleClass('hidden');
			})
		}
		
	
		var mainMenu = A.one('#main-menu.enabled');
		
		if(mainMenu){
			mainMenu.on('click', function(event){
				mainMenu.toggleClass("open");
				mainMenu.all('li ul, span.close').each(function(){
					this.toggleClass("hidden");
				})
			})
		}

		var messageBoard = A.one('.portlet-message-boards');

		if (messageBoard) {
			messageBoard.delegate(
				['mouseenter', 'mouseleave'],
				function(event) {
					var target = event.currentTarget;

					target.toggleClass('controls-visible', event.type == 'mouseenter');
				},
				'.message-container'
			);
		}

		var toggleDockbar = A.one('#toggleDockbar');

		if (toggleDockbar) {
			toggleDockbar.on(
				'click',
				function(event) {
					event.preventDefault();

					var body = A.one('body');

					body.toggleClass('show-dockbar');
				}
			);
		}

		var siteNavigationNavbar = A.one('#_145_navSiteNavigationNavbarCollapse ul');

		var navigation = A.one('#navigation ul');

		if (siteNavigationNavbar && navigation) {
			siteNavigationNavbar.setHTML(navigation.getHTML());
		}
	}
);