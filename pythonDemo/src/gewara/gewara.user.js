// ==UserScript==  
// @name         gewara get ticket
// @versionv     0.1
// @author       fhdone
// @namespace    http://www.fhdone.com
// @description  gewara get ticket
// @include      *://m.gewara.com/home/movie/showOrder.xhtml?tradeNo=*
// @require
// ==/UserScript== 


function getTicket(){
    var radios = document.getElementsByName("distype");
    for(var i=0; i<radios.length; i++){
		if( checkProcess(radios[i])){
			document.forms[0].submit();
			nofity( 'OK' , 3000 );
			break;
		}
		window.location.reload(); 
    }
}


function checkProcess( radioObj ){
	innerText = radioObj.parentNode.innerText
	if ( innerText.indexOf('10元') != -1){
		if( radioObj.disabled == false ){
			radioObj.checked = true
			return true;
		}
	}
	if ( innerText.indexOf('不使用优惠') != -1){
		if( radioObj.disabled == false ){
			radioObj.checked = true
			return true;
		}
	}
}

document.onclick=function(e){
	if( window.webkitNotifications && window.webkitNotifications.checkPermission() != 0 ) {
		window.webkitNotifications.requestPermission();
	}
}

function nofity(str , timeout ){
	if( window.webkitNotifications && window.webkitNotifications.checkPermission() == 0 ) {
		var notification = webkitNotifications.createNotification(
		"http://m.gewara.com/favicon.ico",
		'买票', str );
		notification.show();
		// if ( timeout ) {
		// 	setTimeout(function() {
		// 		notification.cancel();
		// 	}, timeout);
		// }
	} 
}

var now = new Date();
var day = now.getDay();
var hour = now.getHours();
if (  ( day==3 || day==4 || day==5 ) &&
	  ( hour==11 ) ){
	setTimeout(getTicket, 1000 )
}

