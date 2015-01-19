crudGridHelperYanghuiGaphone = {

};

$(function() {
	var gloabel_assignees = eval("(" + assignees + ")");
	//jqGrid
	$("#jira_table").jqGrid({
		datatype: "local",
		height: 500,
		weight: 900,
		colNames: ['Jira#','开发', '测试'],
		colModel : [
								{
									name : 'issueKey',
									index : 'issueKey',
									width : 100,
									editable : true,
									edittype : 'text'
								},
								{
									name : 'developers',
									index : 'developers',
									width : 400,
									editable : true,
									edittype : 'custom',
									editoptions : {
										custom_element : function(value,
												options) {
											var devComp = "";
											var developers = gloabel_assignees.developers;
											for(i in developers){
												var developer = developers[i];
												devComp = devComp + "<input type=\"checkbox\" name=\"user\" value=\""+ developer.username +"\"/>" + developer.name;
											}
											
											var comp = "<div id=\""
													+ options.id
													+ "\" style=\"white-space:normal;\" >"
													+ devComp
													+ "</div>";
											return comp;
										},
										custom_value : function(elem,
												operation, value) {
											if (operation === 'get') {
												var id = elem.attr("id");
												var users = "";
												$("#" + id + " input").each(function() {
													if($(this).is(":checked")){
														users = users + $(this).val()+ ",";
													}
												});
												users = crudHelper.formatSliptStr(users,",");
												elem.val(users);
												return elem.val();
											} else if (operation === 'set') {
												var id = elem.attr("id");
												var users = "";
												$("#" + id + " input").each(function() {
													if($(this).is(":checked")){
														users = users + $(this).val()+ ",";
													}
												});
												users = crudHelper.formatSliptStr(users,",");
												elem.val(users);
											}

										}
									}
								},
								{
									name : 'qas',
									index : 'qas',
									width : 400,
									editable : true,
									edittype : 'custom',
									editoptions : {
										custom_element : function(value,
												options) {
											var qaComp = "";
											var qas = gloabel_assignees.qas;
											for(i in qas){
												var qa = qas[i];
												qaComp = qaComp + "<input type=\"checkbox\" name=\"user\" value=\""+ qa.username +"\"/>" + qa.name;
											}
											
											var comp = "<div id=\""
													+ options.id
													+ "\" style=\"white-space:normal;\" >"
													+ qaComp
													+ "</div>";
											return comp;
										},
										custom_value : function(elem,
												operation, value) {
											if (operation === 'get') {
												var id = elem.attr("id");
												var users = "";
												$("#" + id + " input").each(function() {
													if($(this).is(":checked")){
														users = users + $(this).val()+ ",";
													}
												});
												users = crudHelper.formatSliptStr(users,",");
												elem.val(users);
												return elem.val();
											} else if (operation === 'set') {
												var id = elem.attr("id");
												var users = "";
												$("#" + id + " input").each(function() {
													if($(this).is(":checked")){
														users = users + $(this).val()+ ",";
													}
												});
												users = crudHelper.formatSliptStr(users,",");
												elem.val(users);
											}

										}
									}
								}
		           ],
		multiselect: true,
		caption: "创建Jira任务",
		ondblClickRow: function(id){
			$('#jira_table').jqGrid('editRow', id, {
				keys : true,
				url : 'clientArray',
				restoreAfterError : true
			});
		}
	});
});