$(function() {
	$("#flex").jqGrid({
		datatype: "local",
		height: 250,
		colNames:['Inv No','Date', 'Client', 'Amount','Tax','Total','Notes'],
		 colModel:[
		           {name:'id',index:'id', width:60, sorttype:"int"},
		           {name:'invdate',index:'invdate', width:90, sorttype:"date" , editable:true, edittype:'text'},
		           {name:'name',index:'name', width:100 , editable:true, edittype:'text'},
		           {name:'amount',index:'amount', width:80, align:"right",sorttype:"float" , editable:true, edittype:'text'},
		           {name:'tax',index:'tax', width:80, align:"right",sorttype:"float" , editable:true, edittype:'text'},
		           {name:'total',index:'total', width:80,align:"right",sorttype:"float" , editable:true, edittype:'text'},
		           {name:'note',index:'note', width:150, sortable:false , editable:true, edittype:'text'}
		           ],
		           multiselect: true,
		           caption: "Manipulating Array Data",
		           //cellEdit:true
				           ondblClickRow : function(id) {
				        	   var rowData = $("#flex").jqGrid("getRowData", id);
								
								$('#flex').jqGrid('editRow', id, {
									keys : true,
									url : 'clientArray',
//									mtype : "POST",
									restoreAfterError : true,
									extraparam : {
										id : rowData.id
									},
									oneditfunc : function(rowid) {
										console.log(rowid);
									},
									succesfunc : function(response) {
										alert("save success");
										return true;
									},
									errorfunc : function(rowid, res) {
										console.log(rowid);
										console.log(res);
									},
									aftersavefunc : function(rowid, res){
										console.log("saved");
									}
								});

				}
	});
	var mydata = [
{id:"1",invdate:"2007-10-01",name:"test",note:"note",amount:"200.00",tax:"10.00",total:"210.00"},
{id:"2",invdate:"2007-10-02",name:"test2",note:"note2",amount:"300.00",tax:"20.00",total:"320.00"},
{id:"3",invdate:"2007-09-01",name:"test3",note:"note3",amount:"400.00",tax:"30.00",total:"430.00"},
{id:"4",invdate:"2007-10-04",name:"test",note:"note",amount:"200.00",tax:"10.00",total:"210.00"},
{id:"5",invdate:"2007-10-05",name:"test2",note:"note2",amount:"300.00",tax:"20.00",total:"320.00"},
{id:"6",invdate:"2007-09-06",name:"test3",note:"note3",amount:"400.00",tax:"30.00",total:"430.00"},
{id:"7",invdate:"2007-10-04",name:"test",note:"note",amount:"200.00",tax:"10.00",total:"210.00"},
{id:"8",invdate:"2007-10-03",name:"test2",note:"note2",amount:"300.00",tax:"20.00",total:"320.00"},
{id:"9",invdate:"2007-09-01",name:"test3",note:"note3",amount:"400.00",tax:"30.00",total:"430.00"}
];

	for(var i=0;i<=mydata.length;i++){
		jQuery("#flex").jqGrid('addRowData',i+2,mydata[i]);
	}

	$("#add_btn").off("click").on("click", function (){
		jQuery("#flex").jqGrid('addRow',{
			rowID : "new_row",
			initdata : {},
			position :"last", 
			 useDefValues : true,  
	            useFormatter : true,  
	            addRowParams : {extraparam:{  
	            }}  
		});
		//当前新增id进入可编辑状态  
        $('#flex').jqGrid('editRow','new_row',{  
            keys : true,        //这里按[enter]保存  
            url: 'clientArray',  
            //mtype : "POST",  
            restoreAfterError: true,  
            extraparam: {  
            },  
            oneditfunc: function(rowid){  
                console.log(rowid);  
            },  
            succesfunc: function(response){  
                alert("save success");  
                return true;  
            },  
            errorfunc: function(rowid, res){  
                console.log(rowid);  
                console.log(res);  
            }  
        });  
	});
});