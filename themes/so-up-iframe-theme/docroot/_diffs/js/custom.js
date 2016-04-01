/* toggle worskpaces navigation dropdown */

$('document').ready(clickdropdown); 
$('document').ready(showSubnavi); 

function clickdropdown() {		
	
	$('.click-nav ul.mainmenu').toggleClass('no-js js');
	$('.scope-mobile').toggleClass('no-js js');
	$('.click-nav .js').hide();
	$('.scope-mobile .js').hide();
	
	$('.clicker').click(function(e){
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
				// fehlt abfrage, wie gross fenster //
					if(TriggerClick==0){
						TriggerClick=1;
						$( ".wrapper-portlet-area" ).animate({ marginTop: +120 + +margin_height,}, 200 );
					}else{
						TriggerClick=0;
						$( ".wrapper-portlet-area" ).animate({ marginTop: 120,}, 200 );
					};
					

			}); // end click function
			
			$(window).resize(function() {
				/*	var winwidth=$(window).width();
				if (winwidth > 979 ) {
				$( ".wrapper-portlet-area" ).css( "margin-top", 20);
				} else { $( ".wrapper-portlet-area" ).css( "margin-top", 150);} */
				
				TriggerClick=0;
				
				if (winwidth > 979 ) {
					$( ".wrapper-portlet-area" ).css( "margin-top", 20);
					} else { $( ".wrapper-portlet-area" ).css( "margin-top", 120);} 
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

$('document').ready(function($){

    function sticky(){
        
    	var window_top=$(window).scrollTop();
        var top_position=$('body').offset().top;
        var element_to_stick=$('.container-fluid');
        var winwidth=$(window).width();
        
       
        
        
        	if (window_top > 165 && winwidth>978){
        		
        		
        	
        			element_to_stick.addClass('smallhead');
        		
        	}else{
        		element_to_stick.removeClass('smallhead');
        	}
       
    
    $(window).scroll(function(){
        sticky();
    });  
    
} 


}); 

/*breite der spalte berechnen, klasse anhaengen */
$(document).ready(function($){
	   
	function get_columnWidth (){
		var comp_colWidthFirst= $(".portlet-column-first").width();
		var firstElement_to_stick=$('.portlet-column-first');
		
		var comp_colWidthLast= $(".portlet-column-last").width();
		var lastElement_to_stick=$('.portlet-column-last');
		
		 if (comp_colWidthFirst < 492 ){
			 firstElement_to_stick.addClass('smallcol');
		 }else{
			 firstElement_to_stick.removeClass('smallcol');
		 }
		 
		 if (comp_colWidthLast < 500 ){
			 lastElement_to_stick.addClass('smallcol');
		 }else{
			 lastElement_to_stick.removeClass('smallcol');
		 }
	}
	
	get_columnWidth();
	
	$(window).resize(function() {
		get_columnWidth();
	});
});

/* calculate heights for blog portlet */
$(document).ready(function($){ 
	
	function get_height(){
			
		$.each($('.portlet-blogs .entry.approved'), function() {
		   
			
			var height = $(this).find('.entry-body').height();
			var height2 = $(this).find('.entry-content').height();
		   
		    
		    $(".edit-actions", this).css("height", height);
		    $(".edit-actions", this).css("margin-top", height2 +40);
		    
		   
		});

	}
	
	get_height();
	
	$(window).resize(function() {
		get_height();
	});
	
	
	 /* detect drag and drop */
	    
		Liferay.on('initLayout', function(event) {
			  Liferay.once(function() {
			    Liferay.Layout.on([ 'drag:end', 'drag:start' ], function(event) {
			    
			    	get_height();
			    });
			  }, Liferay.Layout, 'bindDragDropListeners');
			});
	
	
});

/* calculate height and margins for wiki portlet */

$(document).ready(function($){
   
	function get_height(){
		alert('height');
		var comp_height= $(".portlet-wiki .wiki-body").height();
		var comp_margin= $(".portlet-wiki .taglib-header").outerHeight();
		var comp_top= $(".portlet-wiki .portlet-body").find(".navbar").outerHeight(); 
		var ausgleich = 85;
		$(".portlet-wiki .page-actions.top-actions").css("marginTop", (comp_margin + comp_top) - ausgleich);
		$(".portlet-wiki .page-actions.top-actions").css("height", comp_height);
		
		
		
		
	
	
	}
	
	/* detect window resizing */
	
	get_height();
	
	$(window).resize(function() {
		get_height();
	});
	

    /* detect drag and drop */
    
	Liferay.on('initLayout', function(event) {
		  Liferay.once(function() {
		    Liferay.Layout.on([ 'drag:end', 'drag:start' ], function(event) {
		    
		    	get_height();
		    });
		  }, Liferay.Layout, 'bindDragDropListeners');
		});
	
});






/* ***bootstrap klassen anhängen*** **/
/* 1. tabbed navigation iin portlets */
$(document).ready(function($){ 
	
	//var div_class_to_add = $(".portlet-wiki .nav-collpase");
	//var ul_class_to_add= $(".portlet-wiki .nav-collpase .nav");
	
	
	$(".portlet-wiki .nav-collapse").addClass('tabbable');
	$(".portlet-wiki .nav-collapse").addClass('responsive');
	$(".portlet-wiki .nav-collapse .nav").addClass('nav-tabs');
	

});

