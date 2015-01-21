$(function() {
	//rapid view选中事件
	$("#rapid_view_select").change(function(){
		$.ajax({
			url : '/report/loadRapidViewSprint',
			type : 'post',
			async : true,
			data : {
				rapidViewId : $("#rapid_view_select").find("option:selected").val()
			},
			success : function(result){				
				var comp = $("#sprint_select");
				comp.empty();
				var dataList = eval(result);
				if(dataList){
					for(i in dataList){
						var record = dataList[i];
						comp.append("<option value='" + record.id + "'>" + record.name + "</option>");
					}
				}
			},
			failure : function(){
				alert("获取列表失败！");
			}
		});
	});
});