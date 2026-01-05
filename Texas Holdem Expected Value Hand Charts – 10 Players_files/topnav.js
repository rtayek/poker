	
	$(document).ready(function(){
		$("ul.new-topnav_menu_new > li").mouseenter(function(){
			$(this).addClass("activeTab");
			
		});
		
		//out
		$("ul.new-topnav_menu_new > li").mouseleave(function(){
			$(this).removeClass("activeTab");
			
		});		

		
	});
