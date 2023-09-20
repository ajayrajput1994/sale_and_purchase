const toggleSidebar = () => {
	if($(".sidebar").is(":visible")){
		$(".sidebar").css("display","none");
		$(".base_content").css("margin-left","0%");
	}else{ 
		$(".sidebar").css("display","block");
		$(".base_content").css("margin-left","19%");
	}
};