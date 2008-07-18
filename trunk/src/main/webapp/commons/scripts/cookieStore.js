/*************************************************************************
* 
* 依赖mootools-1.2-core-jm.js
* 将字符串存进cookies
* var tokenStore = new TokenStore(cookieName);
* tokenStore.addToken("xxx");
* /////////////
* tokenStore.retrieve();
* storeItem.tokens;//取储存的tokens
*
**************************************************************************/
function TokenStore(cookieName){
	this.splitChar = '$';
	this.cookieName = cookieName;
	this.tokens = new Array();
}
/**
* 存储节点id，不存储重复的id
*/
TokenStore.prototype.addToken=function(token){
	var strToken = token+"";
	if(!this.tokens.contains(strToken)){
		if(strToken.indexOf(this.splitChar)!=-1){
			alert("can't add char '"+this.splitChar+"'");
			return;
		}
		this.tokens.push(strToken);
		this.store();
	}
};
/**
* 移除节点
*/
TokenStore.prototype.removeToken=function(token){
	var strToken = token+"";
	if(this.tokens.contains(strToken)){
		this.tokens.erase(strToken);
		this.store();
	}
};
/**
* 移除所有节点
*/
TokenStore.prototype.clearToken=function(){
	this.tokens = new Array();
	this.store();
};
/**
* 存储到cookie
*/
TokenStore.prototype.store=function(){
	var ck = "";
	for(var i=0;i<this.tokens.length;i++){
		if(i==0){
			ck = ck + this.tokens[i];
		}else{
			ck = ck + this.splitChar + this.tokens[i];
		}
	}
	Cookie.write(this.cookieName, ck);
};
/**
* 从cookie读取
*/
TokenStore.prototype.retrieve=function(){
	var ck = Cookie.read(this.cookieName);
	if(ck != null){
		this.tokens = ck.split(this.splitChar);
	}
};
/***************************************************************************/