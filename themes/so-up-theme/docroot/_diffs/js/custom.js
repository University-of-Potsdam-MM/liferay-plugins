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

/*
$(document).ready(myfunction);
$(window).on('resize',myfunction);

function myfunction() {
    // do whatever
} */