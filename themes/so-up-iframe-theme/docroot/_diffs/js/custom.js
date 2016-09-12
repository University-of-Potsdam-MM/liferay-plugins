/* toggle worskpaces navigation dropdown */

/* add class if window width > 987 */
$('document').ready(function($){

	 $(window).resize(function(){
	        sticky();
	    });
	

	
	
	function sticky(){
	    
		var element_to_stick=$('.container-fluid');
        var winwidth=$(window).width();
        
        if (element_to_stick.hasClass('smallhead') && winwidth < 960 )
        	{
           		
        		element_to_stick.removeClass('smallhead');
        	}
   
        if (!element_to_stick.hasClass('smallhead') && winwidth > 960)
        	{
        	
        		element_to_stick.addClass('smallhead');
        	}
        
		} 
    
}); 


/* scroll sidebar */

$(document).ready(function(){
	
	get_height();
	$(".site-list-container").niceScroll({touchbehavior:true,cursorcolor:"#014260",cursorwidth:5,autohidemode:true}); 
	$('.nicescroll-rails').css("position","fixed");
	$('.nicescroll-rails').css("top","100px");
	function get_height(){
	
			var list_height=$(window).height() - 150;
  			$('.site-list-container').height(list_height);
  			
		
  
	};
 
	$(window).resize(function() {
		get_height();
	});		

}); 

