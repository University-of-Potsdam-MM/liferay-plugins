AUI().ready(
	'aui-base',
	'aui-io-request',
	'aui-modal',
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
		
		$(window).scroll(function() {
		    if ($(window).scrollTop() > 200) {
		        $('body').addClass('smallhead');
		    } else {
		        $('body').removeClass('smallhead');
		    }
		});
		
		var body = A.one('body');
		
		/*if(body)
		{
			A.on('scroll', function(e) {
				if(window.scrollY > 500)
				{
					body.addClass("smallhead");
				}
				else
				{
					body.removeClass("smallhead");
				}
		      });
		}*/
		
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
		
		var mobilemenu = A.all('a.mobile-menu');
		
		if(mobilemenu){
			mobilemenu.on('click', function(event){
				event.preventDefault();
				event.stopPropagation();

				var target = event.currentTarget;
				var href = target.getAttribute('href');
				var elem = A.one(href);

				var s = '.submenu:not(' + href +')';
				var submenu = A.all(s)
				console.log(submenu);
				console.log(s);
				submenu.addClass('hidden');
				
				if(elem)
					elem.toggleClass('hidden');
				
				return false;
			})
		}

		var siteNavigationNavbar = A.one('#_145_navSiteNavigationNavbarCollapse ul');

		var navigation = A.one('#navigation ul');

		if (siteNavigationNavbar && navigation) {
			siteNavigationNavbar.setHTML(navigation.getHTML());
		}
		
		var _5_WAR_soportlet_previous = function()
		{}
		var _5_WAR_soportlet_next = function()
		{}
	}
);