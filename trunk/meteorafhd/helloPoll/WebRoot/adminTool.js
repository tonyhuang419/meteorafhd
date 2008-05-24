function addModifyBtn(){
	var mjInfoTable = document.getElementById("mjInfo");
	for(var i =1; i < mjInfoTable.rows.length; i++){
		var tdX = document.createElement("td"); 
		tdX.innerHTML = "<input type='button' value='修改' onClick='modifyRow("+i+")' />" ;
		//tdX.setAttribute('uid','btn'+i);
		var trX = mjInfoTable.rows[i];
		trX.appendChild(tdX);
	}
	convertTextField();
}

function addDelBtn(){
	var mjInfoTable = document.getElementById("mjInfo");
	for(var i =1; i < mjInfoTable.rows.length; i++){
		var tdX = document.createElement("td"); 
		tdX.innerHTML = "<input type='button' value='删除' onClick='delRow("+i+",5)' />" ;
		var trX = mjInfoTable.rows[i];
		trX.appendChild(tdX);
	}
}

function convertTextField(){		
	var mjInfoTable = document.getElementById("mjInfo");
	var rowLength = mjInfoTable.rows.length;
	var rowX = mjInfoTable.rows;
	for(var i=1;i<rowLength;i++){
		var name = rowX[i].cells[1].innerHTML;
		rowX[i].cells[1].innerHTML="<input type='text'"+"id='name"+i+"' value="+name+"size='20' maxlength='20' />";
		var price = rowX[i].cells[2].innerHTML;
		rowX[i].cells[2].innerHTML="<input type='text'"+"id='price"+i+"' value="+price+"size='6' maxlength='6' />";
		var deal = rowX[i].cells[3].innerHTML;
		rowX[i].cells[3].innerHTML="<input type='text'"+"id='deal"+i+"' value="+deal+"size='5' maxlength='5' />";
	}
}

function modifyRow(rowNum){
	var mjInfoTable = document.getElementById("mjInfo");
	var rowX = mjInfoTable.rows[rowNum];

	var mjid = rowX.cells[0].innerHTML;

	var nameID = 'name'+rowNum;
	var priceID = 'price'+rowNum;
	var dealID = 'deal'+rowNum;

	var mjname = document.getElementById(nameID).value;
	var mjprice = document.getElementById(priceID).value;
	var mjdeal = document.getElementById(dealID).value;
	
	if(checkNum(mjprice)==false){    //此函数在addMJ.js
			showMessage("价格请勿以火星文表示，谢谢合作");
	}
	else{
		ModifyPoll.modify(parseInt(mjid),mjname,mjprice,mjdeal,callback);
	}	
}

function delRow(rowNum){
 	if(confirm("删除确认？")){
		var mjInfoTable = document.getElementById("mjInfo");
		var rowX = mjInfoTable.rows[rowNum];
		var mjid = rowX.cells[0].innerHTML;
		ModifyPoll.del(parseInt(mjid),callback);
		//mjInfoTable.deleteRow(rowNum);
	}
	else{  }	
}

function callback(data){
	//alert(data);
	//location.reload();
}