crudGridHelperLinjhBa = {

};

$(function() {
	var gloabel_assignees = eval("(" + assignees + ")");
	//jqGrid
	$("#jira_table").jqGrid({
		datatype: "local",
		height: 500,
		weight: 900,
		colNames: ['Jira#','需求', '产品'],
		colModel : [
								{
									name : 'issueKey',
									index : 'issueKey',
									width : 100,
									editable : true,
									edittype : 'text'
								},
								{
									name : 'bas',
									index : 'bas',
									width : 400,
									editable : true,
									edittype : 'custom',
									editoptions : {
										custom_element : function(value,
												options) {
											var baComp = "";
											var bas = gloabel_assignees.bas;
											for(i in bas){
												var ba = bas[i];
												baComp = baComp + "<input type=\"checkbox\" name=\"user\" value=\""+ ba.username +"\"/>" + ba.name;
											}
											var comp = "<div id=\""
													+ options.id
													+ "\" style=\"white-space:normal;\" >"
													+ baComp
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
									name : 'products',
									index : 'products',
									width : 400,
									editable : true,
									edittype : 'custom',
									editoptions : {
										custom_element : function(value,
												options) {
											var productComp = "";
											var products = gloabel_assignees.products;
											for(i in products){
												var product = products[i];
												productComp = productComp + "<input type=\"checkbox\" name=\"user\" value=\""+ product.username +"\"/>" + product.name;
											}
											var comp = "<div id=\""
													+ options.id
													+ "\" style=\"white-space:normal;\" >"
													+ productComp +"</div>";
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