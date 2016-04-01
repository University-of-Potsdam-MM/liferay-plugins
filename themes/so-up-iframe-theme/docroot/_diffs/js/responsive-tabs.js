$(document).ready;(function ( $, window, document, undefined ) {
	////alert("fzunction");
  
    // undefined is used here as the undefined global 
    // variable in ECMAScript 3 and is mutable (i.e. it can 
    // be changed by someone else). undefined isn't really 
    // being passed in so we can ensure that its value is 
    // truly undefined. In ES5, undefined can no longer be 
    // modified.

    // window and document are passed through as local 
    // variables rather than as globals, because this (slightly) 
    // quickens the resolution process and can be more 
    // efficiently minified (especially when both are 
    // regularly referenced in your plugin).


    // From http://ejohn.org/blog/ecmascript-5-strict-mode-json-and-more/
    //
    // Strict Mode is a new feature in ECMAScript 5 that allows you to
    // place a program, or a function, in a "strict" operating context.
    // This strict context prevents certain actions from being taken
    // and throws more exceptions (generally providing the user with
    // more information and a tapered-down coding experience).
    //
    // Since ECMAScript 5 is backwards-compatible with ECMAScript 3,
    // all of the "features" that were in ECMAScript 3 that were
    // "deprecated" are just disabled (or throw errors) in strict
    // mode, instead.
    //
    // Strict mode helps out in a couple ways:
    //
    //  *  It catches some common coding bloopers, throwing exceptions.
    //  *  It prevents, or throws errors, when relatively "unsafe"
    //     actions are taken (such as gaining access to the global object).
    //  *  It disables features that are confusing or poorly thought out.
    
    "use strict";

    var pluginName = 'responsiveTabs';

    // The plug-in itself is implemented as an object. Here's the
    // constructor function

    function Plugin(element, options) {
		
        // Members
        var el = element,      // DOM version of element
           $el = $(element),   // jQuery version of element
           windowSize;         // last measured window size

        // Extend default options with those supplied by user.
        options = $.extend({}, $.fn[pluginName].defaults, options);
        
  
     
        
        // Set the small screen (responsive) style for tabbable naviation.
        function setSmallStyle() {
   
   		 
   		 // cosole.log('blaaa');
            $(".nav-tabs > li",$el).css("text-align", "center");
            $(".nav-tabs > li:not(.active)",$el).hide();
            var next_link = $(".nav-tabs li.active").next().children(":first");
            next_link.addClass("next");
         
      
			var pre_link = $(".nav-tabs li.active").prev().children(":first");
			pre_link.addClass("prev");
			
			$(next_link).appendTo($(".nav-tabs li:not(:last-child)",$el))
			//$("<a class='right tab-control' >&rsaquo;</a>").appendTo($(".nav-tabs li:not(:last-child)",$el))
			
			
              .each(function(i){
                var thisLi  = $(this).parents("ul").first().children("li:nth-child("+(i+1)+")"),
                    thisTab = $(thisLi).children("a[href]"),
                    nextLi  = $(this).parents("ul").first().children("li:nth-child("+(i+2)+")"),
                    nextTab = $(nextLi).children("a[href]");
                $(this).click(function() {
                    slideTab(thisLi, "out", "left" );
                    slideTab(nextLi, "in",  "right");
                    $(nextTab).tab('show');
                });
            });
           // $("<a class='left tab-control'>&lsaquo;</a>").prependTo($(".nav-tabs li:not(:first-child)",$el))
			$(pre_link).prependTo($(".nav-tabs li:not(:first-child)",$el))
              .each(function(i){
                var thisLi = $(this).parents("ul").first().children("li:nth-child("+(i+2)+")"),
                    thisTab = $(thisLi).children("a[href]"),
                    prevLi = $(this).parents("ul").first().children("li:nth-child("+(i+1)+")"),
                    prevTab = $(prevLi).children("a[href]");
                $(this).click(function() {
                    slideTab(thisLi, "out", "right");
                    slideTab(prevLi, "in", "left");
                    $(prevTab).tab('show');
                });
            });
          //  $(".nav-tabs li:first-child",$el).prepend("<span class='left tab-control-spacer'> </span>");
          //  $(".nav-tabs li:last-child",$el).append("<span class='right tab-control-spacer'> </span>");
        }

        // Set the large screen version of tabbable navigation;
        // this is just the bootstrap default, so all we need to do is
        // to undo any potential changes we made for a small screen
        // style.
                
        function setLargeStyle() {
        	//alert("largestyle");
          
            $(".nav-tabs > li",$el).css("text-align", "left");
            $(".nav-tabs > li:not(.active)",$el).show();
            $(".tab-control",$el).remove();
            $(".tab-control-spacer",$el).remove();
            $("li.active a.prev").remove();
            $("li.active a.next").remove();
            
            $.each($('li.active a.prev'), function() {
            	
            	this.remove();
            	
            });
            
            	$.each($('li.active a.next'), function() {
            	
            	this.remove();
            	
            });

        }
 
        function windowResized() {
		 
        	//alert ("win-re-sized");
            // Although this isn't strictly necessary, let's monitor the
            // window size so we can detect when it crosses the threshold
            // that triggers re-styling. Not likely a big deal for actual
            // users, but we include the functionality for the geeks that
            // like to look at responsive web sites and mess around with
            // browser window widths.
            //
            // We're not bothering with debouncing the window resize 
            // event since we only care when a breakpoint is crossed.
            // Ignoring the other resizes effectively serves as a
            // debouncer.
                        
        
        	   if ($('.nav-tabs').parents('.smallcol').length) {
              	 setSmallStyle();
              }
            
            else  {

              setLargeStyle();
                    
            }
        
        	
        	
        
        }

        // Initialize plugin.
        function init() {
        
	
            // keep track of the window size so we can tell when it crosses a breakpoint
            windowSize = $('body').width();
            
            // default is large window styling; adjust if appropriate
            // $.contains(param1,param2); param1 ist parent
            // $(".nav-tabs").parents('.smallcoll').length == 1
           /* if ( $.contains('.smallcol','.nav-tabs') )  {
                setSmallStyle();
            }
            */
            if ($('.nav-tabs').parents('.smallcol').length) {
            	 setSmallStyle();
            }
            
            // track window size changes to look for breakpoints
            $(window).on('resize', windowResized);
            
            hook('onInit');
            
           

        }
 
        // Get/set a plugin option.
        // Get usage: $('#el').demoplugin('option', 'key');
        // Set usage: $('#el').demoplugin('option', 'key', value);
    
        function option (key, val) {
		   //alert('option');
            if (val) {
                options[key] = val;
            } else {
                return options[key];
            }
        }
 
        // Destroy plugin.
        // Usage: $('#el').demoplugin('destroy');

        function destroy() {
        
		   //alert("destroy");
            // Clean up by removing the event handlers we've added
            $(window).off('resize', windowResized);
            
            // restore styles and DOM
            setLargeStyle();
                
            // Iterate over each matching element.
            $el.each(function() {
                var el = this,
                   $el = $(this);
     
                hook('onDestroy');
                
                // Remove Plugin instance from the element.
                $el.removeData('plugin_' + pluginName);
            });
        }
 
        // Callback hooks.
        // Usage: In the defaults object specify a callback function:
        // hookName: function() {}
        // Then somewhere in the plugin trigger the callback:
        // hook('hookName');
    
        function hook(hookName) {
		 //alert("hook");
            if (options[hookName] !== undefined) {
                // Call the user defined function.
                // Scope is set to the jQuery element we are operating on.
                options[hookName].call(el);
            }
        }
 
        // Initialize the plugin instance.
        init();
 
        // Expose methods of Plugin we wish to be public.
        return {
            option: option,
            destroy: destroy
        };
   
    
    
        /* detect drag and drop */
        
        var isDragging = false;
        $(".portlet-boundary").mousedown(function() {
            isDragging = false;
        }).mousemove(function() {
            isDragging = true;
         }).mouseup(function() {
            var wasDragging = isDragging;
            isDragging = false;
            if (wasDragging) {
            	init(); 
            }
        }); 
    
    
    
    
    }
 
    // Build the plugin here 

    $.fn[pluginName] = function ( options ) {


        // If the first parameter is a string, treat this as a call to
        // a public method. The first parameter is the method name and
        // following parameters are arguments for the method.
  
        if (typeof arguments[0] === 'string') {
            var methodName = arguments[0];
            var args = Array.prototype.slice.call(arguments, 1);
            var returnVal;
            this.each(function() {
                // Check that the element has a plugin instance, and that
                // the requested public method exists.
                if ( $.data(this, 'plugin_' + pluginName) && 
                     typeof $.data(this, 'plugin_' + pluginName)[methodName] === 'function' ) {
                    // Call the method of the Plugin instance, and Pass it
                    // the supplied arguments.
                    returnVal = $.data(this, 'plugin_' + pluginName)[methodName].apply(this, args);
                } else {
                    throw new Error('Method ' +  methodName + ' does not exist on jQuery.' + pluginName);
                }
            });
            if (returnVal !== undefined){
                // If the method returned a value, return the value.
                return returnVal;
            } else {
                // Otherwise, returning 'this' preserves chainability.
                return this;
            }

        // If the first parameter is an object (options), or was omitted,
        // instantiate a new instance of the plugin.
  
        } else if (typeof options === "object" || !options) {

            return this.each(function() {
                // Only allow the plugin to be instantiated once.
                if (!$.data(this, 'plugin_' + pluginName)) {
                    // Pass options to Plugin constructor, and store Plugin
                    // instance in the elements jQuery data object.
                    $.data(this, 'plugin_' + pluginName, new Plugin(this, options));
                }
            });
        }
    };
    
    // Default plugin options.
    // Options can be overwritten when initializing plugin, by
    // passing an object literal, or after initialization:
    // $('#el').responsiveTabs('option', 'key', value);
    $.fn[pluginName].defaults = {
        maxSmallWidth: 5000,   // biggest screen size for which we use "small" configuration
        slideTime: 500,       // milliseconds to slide from one tab to another
        onInit: function() {},
        onDestroy: function() {}
    };
    
  

})( jQuery, window, document );


$(document).ready(function() {
	
    $(".tabbable.responsive").responsiveTabs(); 
});




