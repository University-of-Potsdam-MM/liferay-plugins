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
        var element_to_stick=$('body');
        var winwidth=$(window).width();
        
        if (window_top > top_position && winwidth>978) {
            element_to_stick.addClass('smallhead');
          
        } else {
            element_to_stick.removeClass('smallhead');
        }
    }
    $(window).scroll(sticky);
    sticky();
    
});