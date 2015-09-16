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
		
		/*var body = A.one('body');
		
		if(body)
		{
			A.on('scroll', function(e) {
				if(window.scrollY > 50)
				{
					body.addClass("smallhead");
				}
				else
				{
					body.removeClass("smallhead");
				}
		      });
		} */
		
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
		
		var allsites = A.one('#sidebar-all-sites');
		
		if(allsites)
		{
			// Liste aller oeffentlichen Seiten laden
		    var d = A.io.request('?p_p_id=5_WAR_soportlet&p_p_lifecycle=2&p_p_state=normal&p_p_mode=view&p_p_resource_id=getSites&p_p_cacheability=cacheLevelPage', {
		    	method: 'POST',
		    	dataType: 'json',
		        data: {
		          _5_WAR_soportlet_searchTab: 'all-sites',
		          _5_WAR_soportlet_directory: 0,	
		          _5_WAR_soportlet_end	: 100,
		          _5_WAR_soportlet_start:0
		        },
		        on: {
		        	success: function() {
		        		var l = this.get('responseData').sites;
			            for(var i = 0; i < l.length; i++)
			            {
			            	allsites.append('<li><a href="' + l[i].publicLayoutsURL + '">' + l[i].name + '</a></li>')
			            }
		        	}
		       }
		     });
		}
		
		
		var filldiv = A.one('.windowdiv');
		var modal = null;
		if(filldiv)
		{
		    var loadaddform = A.io.request('?p_p_id=5_WAR_soportlet&p_p_lifecycle=0&p_p_state=exclusive&p_p_mode=view&_5_WAR_soportlet_mvcPath=%2Fsites%2Fedit_site.jsp',{
		    	method:'GET',
		    	dataType: 'html',
		    	on:{
		    		success: function(){
		    			var l = this.get('responseData');
		    			
					    modal = new A.Modal(
					      {
					        bodyContent: l,
					        centered: true,
					        headerContent: '<h1>Workspace hinzufügen</h1>',
					        render: '.windowdiv',
					        height: 500,
					        width: 700,
					        cssClass: 'so-portlet-sites-dialog'
					      }
					    ).render();
					    modal.hide();
					    
					    var next = A.one('.windowdiv .so-portlet-sites-dialog #next').set('onclick',''); 
					    var prev = A.one('.windowdiv .so-portlet-sites-dialog #previous'); 
					    var save = A.one('.windowdiv .so-portlet-sites-dialog #save')
					    next
					    	.on('click', function(event){
					    		event.preventDefault();
						    	this.addClass('disabled');
						    	this.setAttribute('disabled', '');
						    	prev.removeClass('disabled');
						    	prev.removeAttribute('disabled');
						    	A.all('.windowdiv .so-portlet-sites-dialog .section').toggleClass('hide');
						    })
						prev
					    	.on('click', function(event){
					    		event.preventDefault();
						    	this.addClass('disabled');
						    	this.setAttribute('disabled', '');
						    	next.removeClass('disabled');
						    	next.removeAttribute('disabled');
						    	A.all('.windowdiv .so-portlet-sites-dialog .section').toggleClass('hide');
						    })
						    
					   save. on('click', function(event){
				    		event.preventDefault();
				    		var form = A.one('.windowdiv .so-portlet-sites-dialog form');
				    		form.submit();
					   
					   })
				  }
		    	}
		    })
		}
		
		var addbtn = A.one('#sidebar .add');
		if(addbtn)
		{
			addbtn.on('click', function(event){
				console.log(modal);
				modal.show();
			})
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