reportHelper = {
	generateProjectReport : function(){
		var projectJiraId = $("#js_rpt_project").val();
		var versionJiraId = $("#js_rpt_project_version").val();
		if(projectJiraId.length <= 0 || versionJiraId.length <= 0){
			alert("请选择项目与版本！");
			return;
		}
		window.location.href = ctx + "/report/showProjectReport?projectJiraId=" + projectJiraId + "&versionJiraId=" + versionJiraId;
	}
};
$(function() {
	$("#js_rpt_project").change(function(){
		var projectJiraId = $(this).val();
		$.ajax({
			type: "POST",
			url : ctx + "/report/projectReport/version",
			data : {
				projectJiraId : projectJiraId
			},
			success: function(data){
				var comp = $("#js_rpt_project_version");
				comp.empty();
				if(!data || data == "[]"){
					comp.attr("disabled","disabled");
            	}else{
            		var versions = eval(data);
                	for(var i =0; i < versions.length; i++){
                		var version = versions[i];
                		comp.append("<option value='" + version.jiraId + "'>" + version.name + "</option>");
                	}
                	comp.removeAttr("disabled");
            	}	
			},
			failure : function(){
				alert("同步异常");
			}
		});
	});
});