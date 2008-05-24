function modifyDeal(){
	var mjInfoTable = document.getElementById("mjInfo");
	var rowLength = mjInfoTable.rows.length;
	var rowX = mjInfoTable.rows;
	for(var i=1;i<rowLength;i++){
		var deal = rowX[i].cells[3].innerHTML;
		if(deal.indexOf("true")!=-1){
			rowX[i].cells[3].innerHTML="快来买我！";
			rowX[i].cells[3].className="buy";
		}
		else{
			rowX[i].cells[3].innerHTML="此号不卖";
		}
	}
}

function addBuyBtn(){
	var mjInfoTable = document.getElementById("mjInfo");
	for(var i =1; i < mjInfoTable.rows.length; i++){
		var tdX = document.createElement("td");
		if(mjInfoTable.rows[i].cells[3].innerHTML.indexOf("此号不卖")!=-1){
			tdX.innerHTML = "<input type='button' value='此号不卖' disabled='disabled'/>";
		}
		else{
			tdX.innerHTML = "<input type='button' value='我要买！' onClick='addBidRecord("+i+")'/>";	
		}
		var trX = mjInfoTable.rows[i];
		trX.appendChild(tdX);
	}
}

function addSeeRecordBtn(){
	var mjInfoTable = document.getElementById("mjInfo");
	for(var i =1; i < mjInfoTable.rows.length; i++){
		var tdX = document.createElement("td"); 
		var mjid = mjInfoTable.rows[i].cells[0].innerHTML;
		tdX.innerHTML = "<input type='button' value='查看记录' onClick='showBidRecord("+mjid+")'/>" ;
		var trX = mjInfoTable.rows[i];
		trX.appendChild(tdX);
	}
}

function addBidRecord(rowNum){
	var mjInfoTable = document.getElementById("mjInfo");		
	var bidPrice = window.prompt("给多少？");
	var mjid =	mjInfoTable.rows[rowNum].cells[0].innerHTML;		
	var whobuy = document.getElementById("username").innerHTML;
	var now = new Date();
	var nowX = new Date(now.getYear(),now.getMonth(),now.getDate(),now.getHours(),now.getMinutes(),now.getSeconds());
	if(checkNum(bidPrice)==false){    //此函数在addMJ.js
			showMessage("价格请勿以火星文表示，谢谢合作");
	}
	else{
		ToolBidReocrd.create(parseInt(mjid),parseInt(bidPrice),nowX,whobuy,callback);
	}
}


function addMJHref(){
	var mjInfoTable = document.getElementById("mjInfo");
	var rowLength = mjInfoTable.rows.length;
	var rowX = mjInfoTable.rows;
	for(var i=1;i<rowLength;i++){
		var MJname = rowX[i].cells[1].innerHTML;
		rowX[i].cells[1].innerHTML = toHref(MJname)		
	}
}

var baseURL="http://e.taisha.org/space-username-";
var t =  " target='_blank'";
function toHref(MJname){
	url = baseURL + trim(MJname)+".html";
	info = "<a href="+url+t+">"+MJname+"</a>";
	return info;
}

//去左空格; 
function ltrim(s){ 
	return s.replace( /^\s*/, ""); 
} 
//去右空格; 
function rtrim(s){ 
	return s.replace( /\s*$/, ""); 
} 
//去左右空格; 
function trim(s){ 
	return rtrim(ltrim(s)); 
}

function showBidRecord(mjid){
	ToolBidReocrd.showBidRecord(mjid,callbackx);
}

function callbackx(data){
	var bidMessage ="<table id='bidShow' border='1' align='CENTER'><tr align='CENTER'><th width='50'>MJName</th><th width='100'>WhoBuy</th><th width='100'>BidPrice</th><th width='100'>BidTime</th></tr>";
	var bidmessageDiv = document.getElementById("bidMessage");
	bidmessageDiv.innerHTML = "";	
	bidMessage += data;
	bidmessageDiv.innerHTML = bidMessage + "</table>";	
}

function callback(data){
	showMessage(data);
}

function showMessage(message){
	var messageDiv = document.getElementById("message");
	messageDiv.innerHTML="";
	messageDiv.innerHTML = message;
}