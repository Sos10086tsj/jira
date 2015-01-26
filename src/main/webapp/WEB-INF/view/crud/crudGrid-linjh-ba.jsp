<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"
	src="../../resources/plugins/jquery/jquery-2.1.1.js"></script>
	<link rel="stylesheet" type="text/css"
	href="../../resources/css/common.css">
<link rel="stylesheet" type="text/css"
	href="../../resources/plugins/jpGrid/css/ui.jqgrid.css">
<script type="text/javascript"
	src="../../resources/plugins/jpGrid/js/minified/jquery.jqGrid.min.js"></script>
<script type="text/javascript"
	src="../../resources/plugins/jpGrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="../../resources/js/crudGridHelperLinjhBa.js"></script>
</head>
<body>

	<div class="float_left">
		<table id="jira_table_linjh" cellspacing="0" cellpadding="0" border="0"></table>
	</div>
	<div class="clear"></div>

</body>

<script>
	var assignees = '${assignees}';
</script>

</html>