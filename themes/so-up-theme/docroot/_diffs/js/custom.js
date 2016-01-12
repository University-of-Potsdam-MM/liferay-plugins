/* toggle worskpaces navigation dropdown */

$('document').ready(clickdropdown); 


		
function clickdropdown() {			
	
			$('.click-nav ul').toggleClass('no-js js');
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
				if ($('.click-nav .js ul').is(':visible')) {
					$('.click-nav .js ul', this).slideUp();
					$('.scope-mobile .js', this).slideUp();
					$('.clicker').removeClass('active');
				}
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
