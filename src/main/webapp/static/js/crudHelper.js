crudHelper = {
		formatSliptStr : function(str, char){
			if(char==null||char==""||str.length==0||char.length>str.length){
				return str;
			}
			if(str.substring(str.length-char.length)==char){
				return str.substring(0, str.length-char.length);
			}
			return str;
		}
};

$(function() {
	//加载默认jsp
	var selectedValue = $("#template_select").find("option:selected").val();
	$("#load_grid").load("/jiraCrud/loadGridJsp?templateCode=" + selectedValue);

	//项目select选中事件
	$("#project_select").change(function(){
		$.ajax({
			url : '/jiraCrud/getProjectVersions',
			type : 'post',
			async : true,
			data : {
				project : $("#project_select").find("option:selected").val()
			},
			success : function(result){				
				var comp = $("#version_select");
				comp.empty();
				var dataList = eval(result);
				if(dataList){
					for(i in dataList){
						var record = dataList[i];
						comp.append("<option value='" + record.name + "'>" + record.name + "</option>");
					}
				}
			},
			failure : function(){
				alert("获取列表失败！");
			}
		});
	});
	
	//template select 选中事件
	$("#template_select").change(function(){
		var selectedValue = $("#template_select").find("option:selected").val();
		$("#load_grid").load("/jiraCrud/loadGridJsp?templateCode=" + selectedValue);
	});
});