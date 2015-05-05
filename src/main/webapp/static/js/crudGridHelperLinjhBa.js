crudGridHelperLinjhBa = {

};

$(function() {
	var gloabel_assignees = eval("(" + assignees + ")");
	//jqGrid
	$("#jira_table_linjh").jqGrid({
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
			$('#jira_table_linjh').jqGrid('editRow', id, {
				keys : true,
				url : 'clientArray',
				restoreAfterError : true
			});
		}
	});
	
	//提交按钮
	$("#create_btn").off("click").on("click", function (){
		var rowCount = $('#jira_table_linjh').getGridParam('records');
		if(rowCount){
			var project = $("#project_select").val();
			var issueType = $("#issue_type_select").val();
			var version = $("#version_select").val();
			var templateCode = $("#template_select").val();
			
			var vos = new Array();
			
			for(var i = 1; i <= rowCount; i++){
				var rowData = $("#jira_table_linjh").jqGrid('getRowData', i);
				var issueKey = rowData['issueKey'];
				var basVal = rowData['bas'].split(",");
				var productsVal = rowData['products'].split(",");

				var bas = new Array();
				for(j in basVal){
					if(basVal[j]){
						var ba = {
								username : basVal[j]
						};
						bas.push(ba);
					}
				}
				
				var products = new Array();
				for(j in productsVal){
					if(productsVal[j]){
						var product = {
								username : productsVal[j]
						};
						products.push(product);
					}
				}
				
				var vo = {
						issueKey : issueKey,
						bas : bas,
						products : products
				};
				vos.push(vo);
			}
			
			var crudVos = {
					crudVos : vos
			};
			
			
			$.ajax({
				url : '/jiraCrud/createJiraTasks',
				type : 'post',
				async : true,
				data : {
					project : project,
					issueType : issueType,
					version : version,
					crudVos : JSON.stringify(crudVos),
					templateCode : templateCode
				},
				success : function(result){
					window.location.href = "/jiraCrud/showCreateResult?issueKeys= " + crudHelper.formatSliptStr(result,",");
				},
				failure : function(){
					alert("保存失败！");
				}
			});
		}
	});
	
	//添加按钮
	$("#add_btn").off("click").on("click", function (){
		var rowCount = $('#jira_table_linjh').getGridParam('records');
		jQuery("#jira_table_linjh").jqGrid('addRow',{
			rowID : rowCount + 1,
			initdata : {},
			position :"last", 
			useDefValues : true,  
	         useFormatter : true
		});
		//当前新增id进入可编辑状态  
        $('#jira_table_linjh').jqGrid('editRow',rowCount + 1,{  
            keys : true,
            url: 'clientArray',  
            restoreAfterError: true 
        });  
	});
	
	//删除按钮
	$("#del_btn").off("click").on("click", function (){
		var rowCount = $('#jira_table_linjh').getGridParam('records');
		for(var i = 1; i <= rowCount; i++){
			if($("#jqg_jira_table_linjh_" + i).is(":checked")){
				$('#jira_table_linjh').jqGrid('delRowData',i);
			}
		}
	});
});