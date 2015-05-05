syncHelper = {
	syncUser : function(){
		var username = $("#js_sync_user_username").val();
		if(!username || username.length == 0){
			alert("请输入用户名");
		}else{
			$.ajax({
				type: "POST",
				url : ctx + "/sync/user",
				data : {
					username : username
				},
				success: function(data){
					alert("同步结束");
				},
				failure : function(){
					alert("同步异常");
				}
			});
		}
	},
	
	syncProject : function(){
		var project = $("#js_sync_user_project").val();
		if(!project || project.length == 0){
			alert("请输入用户名");
		}else{
			$.ajax({
				type: "POST",
				url : ctx + "/sync/project",
				data : {
					project : project
				},
				success: function(data){
					alert("同步结束");
				},
				failure : function(){
					alert("同步异常");
				}
			});
		}
	}
};
$(function() {
	
});