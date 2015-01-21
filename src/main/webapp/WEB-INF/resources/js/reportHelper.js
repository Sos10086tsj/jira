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
	
	//生成报告
	$("#rpt_btn").off("click").on("click", function (){
		var rapidViewId = $("#rapid_view_select").find("option:selected").val();
		var sprintId = $("#sprint_select").find("option:selected").val();
		$("#report").load("/report/generateSprintReport?rapidViewId=" + rapidViewId + "&sprintId=" + sprintId);
	});
	
	//伸缩
	$('tr.parent').click(function(){   // 获取所谓的父行  

        $(this).toggleClass("selected")   // 添加/删除高亮  
        .siblings('.child_'+this.id).toggle();  // 隐藏/显示所谓的子行  
//		$(this).toggleClass("selected").siblings('.sub_row_'+this.id).toggle();
//		$(this).toggleClass("selected").siblings('.include_row_'+this.id).toggle();
	}).click();  
});