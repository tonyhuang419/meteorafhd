function modifyDeal(){
	var mjInfoTable = document.getElementById("mjInfo");
	var rowLength = mjInfoTable.rows.length;
	var rowX = mjInfoTable.rows;
	for(var i=1;i<rowLength;i++){
		var deal = rowX[i].cells[3].innerHTML;
		if(deal.indexOf("true")!=-1){
			rowX[i].cells[3].innerHTML="�������ң�";
			rowX[i].cells[3].className="buy";
		}
		else{
			rowX[i].cells[3].innerHTML="�˺Ų���";
		}
	}
}

function addBuyBtn(){
	var mjInfoTable = document.getElementById("mjInfo");
	for(var i =1; i < mjInfoTable.rows.length; i++){
		var tdX = document.createElement("td");
		if(mjInfoTable.rows[i].cells[3].innerHTML.indexOf("�˺Ų���")!=-1){
			tdX.innerHTML = "<input type='button' value='�˺Ų���' disabled='disabled'/>";
		}
		else{
			tdX.innerHTML = "<input type='button' value='��Ҫ��' onClick='addBidRecord("+i+")'/>";	
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
		tdX.innerHTML = "<input type='button' value='�鿴��¼' onClick='showBidRecord("+mjid+")'/>" ;
		var trX = mjInfoTable.rows[i];
		trX.appendChild(tdX);
	}
}

function addBidRecord(rowNum){
	var mjInfoTable = document.getElementById("mjInfo");		
	var bidPrice = window.prompt("�����٣�");
	var mjid =	mjInfoTable.rows[rowNum].cells[0].innerHTML;		
	var whobuy = document.getElementById("username").innerHTML;
	var now = new Date();
	var nowX = new Date(now.getYear(),now.getMonth(),now.getDate(),now.getHours(),now.getMinutes(),now.getSeconds());
	if(checkNum(bidPrice)==false){    //�˺�����addMJ.js
			showMessage("�۸������Ի����ı�ʾ��лл����");
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

//ȥ��ո�; 
function ltrim(s){ 
	return s.replace( /^\s*/, ""); 
} 
//ȥ�ҿո�; 
function rtrim(s){ 
	return s.replace( /\s*$/, ""); 
} 
//ȥ���ҿո�; 
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