Ker={}

/*cookie方法*/
Ker.setCookie=function(name,value){
	var days = 20;
    var exp = new Date();
    exp.setTime(exp.getTime() + days*24*60*60*1000);
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString(); 
}

/*cookie方法*/
Ker.getCookie=function(name){
    var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
    if(arr != null) return unescape(arr[2]); return null;

}

/*cookie方法*/
Ker.removeCookie=function(name){
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval=Ker.getCookie(name);
    if(cval!=null) document.cookie= name + "="+cval+";expires="+exp.toGMTString();
}
