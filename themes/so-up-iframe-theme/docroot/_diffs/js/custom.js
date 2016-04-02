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

