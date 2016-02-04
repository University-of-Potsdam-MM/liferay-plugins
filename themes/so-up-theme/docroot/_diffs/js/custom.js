/* toggle worskpaces navigation dropdown */

$('document').ready(clickdropdown); 
$('document').ready(showSubnavi); 


		
function clickdropdown() {		
	
			
	
			$('.click-nav ul.mainmenu').toggleClass('no-js js');
			$('.scope-mobile').toggleClass('no-js js');
			$('.click-nav .js').hide();
			$('.scope-mobile .js').hide();
			$('.clicker').click(function(e) {
				
				
				
				$('.click-nav .js').slideToggle(200);
				$('.scope-mobile .js').slideToggle(200);
				$('.clicker').toggleClass('active');
				
				
				
				
				e.stopPropagation();
			});
			$(document).click(function() {
				if ($('.click-nav .js ul.mainmenu').is(':visible')) {
					$('.click-nav .js ul.mainmenu', this).slideUp();
					$('.scope-mobile .js', this).slideUp();
					
					$('.clicker').removeClass('active');
				}
			});
			
			
			var TriggerClick = 0;
			var winwidth=$(window).width();
			
			$( ".clicker" ).click(function() {
				
				var count_elements = $('#subnav-mobile ul li').length;
				var count_elements2 = $('#subnav-mobile ul li ul li').length;
				var navitems = +count_elements - +count_elements2;
				var margin_height= +navitems * +43;
				
			
				
					if(TriggerClick==0){
						TriggerClick=1;
						$( ".wrapper-portlet-area" ).animate({ marginTop: +150 + +margin_height,}, 200 );
					}else{
						TriggerClick=0;
						$( ".wrapper-portlet-area" ).animate({ marginTop: 150,}, 200 );
					};
					
			
					
					
				
			
			
			}); // end click function
			
			$(window).resize(function() {
				var winwidth=$(window).width();
				if (winwidth > 979 ) {
				$( ".wrapper-portlet-area" ).css( "margin-top", 20);
				} else { $( ".wrapper-portlet-area" ).css( "margin-top", 150);}
			});
			
		
			
			/* hide subnavigation when mainmeu is open */

			
			var zaehler =0;
			$( "#main-menu li" ).click(function() {
				
				
				
				if (zaehler == 0) {
				
				$('span.clicker span').hide();
				zaehler ++;
				} else {
					
					$('span.clicker span').show();
					zaehler =0;
				}
				
			});
				
				
}

/* unternavigationspunkte klick auf pfeil */ 

function showSubnavi() {		

	
	
	
	$('li.has-children span').click(function(e) {		
		$('ul',e.target.parentElement).slideToggle(200);
	
		$(e.target).toggleClass('active');
		
		e.stopPropagation();
	});
	
}

$('document').ready(function($) {

    function sticky()
    {
        var window_top=$(window).scrollTop();
        var top_position=$('body').offset().top;
        var element_to_stick=$('.container-fluid');
        var winwidth=$(window).width();
        
        if (window_top > top_position && winwidth>978) {
            element_to_stick.addClass('smallhead');
           
        } else {
            element_to_stick.removeClass('smallhead');
        }
    }
    sticky();
    
    $(window).scroll(function() {
        sticky();
    });
    
    
  
    
}); 




/* höhe wikitext berechnen */

$(document).ready(function($){
   
	function get_height ()
	{
		var comp_height= $(".portlet-wiki .wiki-body").height();

		$(".portlet-wiki .page-actions.top-actions").css("height", comp_height+'px');
	}
	
	get_height();
	
	$(window).resize(function() {
		get_height();
	});
});

/*breite spalte berechnen, klasse anhaengen */
$(document).ready(function($){
	   
	function get_columnWidth ()
	{
		var comp_colWidthFirst= $(".portlet-column-first").width();
		var firstElement_to_stick=$('.portlet-column-first');
		
		var comp_colWidthLast= $(".portlet-column-last").width();
		var lastElement_to_stick=$('.portlet-column-last');
		
		
		
		/*alert (comp_colWidthLast);
		alert (comp_colWidthFirst);*/

		 if (comp_colWidthFirst < 492 ) {
			 firstElement_to_stick.addClass('smallcol');
	          
	        } else {
	            firstElement_to_stick.removeClass('smallcol');
	        }
		 
		 if (comp_colWidthLast < 500 ) {
			 lastElement_to_stick.addClass('smallcol');
	          
	        } else {
	            lastElement_to_stick.removeClass('smallcol');
	        }
	 
	}
	
	get_columnWidth();
	
	$(window).resize(function() {
		get_columnWidth();
	});
});


/* change zindex */