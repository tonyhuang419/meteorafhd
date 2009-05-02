nullXN.APP.share = {
	dialog:null,
	action:'add',
	//url:各业务弹出分享的action的路径,data:参数,json格式
	showDialog:function(url,data){
		if(!this.dialog)this.createDialog();
		var s = this;
		var d = this.dialog;
		
//		var data = {id:id,owner:owner,host:host};
		//var url;
		//if(data.id && data.owner){
		//	//url = '/share/ajax.do';
		//	data = 'post=' + encodeURIComponent(XN.JSON.build(data));
		//}else if(data.type && data.link){
		//	//url = '/share/popup.do';
		//	if($('summary') && !data.summary)data.summary = $('summary').innerHTML;
		//	data = 'post=' + encodeURIComponent(XN.JSON.build(data));
		//}else 
		if(data.tid){
			//url = '/getshare.do';
			data = "tid="+ tid + "&tribeId=" + tribeId;
		}else{
			data = 'post=' + encodeURIComponent(XN.JSON.build(data));
		}
		
		this.friendSelector.reset();
		
		this.tabView.showTab('shareAddTab');
		
		$('sharer_popup_message').value = '';
		$('popShareSendMessage').value = '';
		$('popShareContainer').hide();
		$('popShareLoading').show();
		d.header.hide();
		d.footer.hide();

		new XN.NET.xmlhttp({
			'url':url,
			'data':data,
			onSuccess:function(r){
				if(XN.STRING.isBlank(r.responseText)){
					$('popShareLoading').innerHTML = '<p>该分享不存在或者已被删除</p>';
					setTimeout(function(){
						s.dialog.hide();
						$('popShareLoading').innerHTML = '<p>载入中...</p>';
					},1500);
					return;
				}
				$('shareAjaxResult').innerHTML = r.responseText;
				$('popShareSubjectInput').value = $('popShareTitle').value;
				$('popShareContainer').show();
				$('popShareLoading').hide();
				d.header.show();
				d.footer.show();
				$('sharer_popup_message').focus();
                d.refresh();
			},
			onError:function(){
				XN.DO.showError('获取分享失败,请稍候重试');
				d.hide();
			}
		});
		d.setY(XN.EVENT.scrollTop() + 200);
		d.show();
	},
	sendRequest:function(){
		var data = {},str,s = this;

		if(this.action == 'add'){
			data = XN.FORM.serialize('popShareParams','hash');
			data['action'] = 'add';
			data['auth'] = $('popShareAuth').value;
			data['body'] = $('sharer_popup_message').value;
			if(data.body.length > 500){
				XN.DO.showError('评论字数不能超过500');
				return;
			}
			if($('summary'))data['summary'] = $('summary').innerHTML;
			str = 'post=' + encodeURIComponent(XN.JSON.build(data)) + '&tsc=' + $('tsc_popShare').value;
		}else if(this.action == 'send'){
			data['action'] = 'sharetofriend';
			data['ids'] = this.friendSelector.getIds();
			if(data.ids.length == 0){
				return;
			}
			if($('popShareSendMessage').value.length > 500){
				XN.DO.showError('评论字数不能超过500');
				return;
			}
			data['form'] = XN.FORM.serialize('popShareParams','hash');
			if($('summary'))data['form']['summary'] = $('summary').innerHTML;
			data['body'] = $('popShareSendMessage').value;
			data['subject'] = $('popShareSubjectInput').value;
			str = 'post=' + encodeURIComponent(XN.JSON.build(data)) + '&tsc=' + $('tsc_popShare').value;
		}

        this.dialog.hide();
		XN.DO.alert({
			msg:'<div class="large">正在发送请求...</div>',
			noFooter:true
		});
		new XN.NET.xmlhttp({
			url:'/share/submit.do',
			data:str,
			onSuccess:function(r){
				var msg = XN.JSON.parse(r.responseText);
				s.onPostSuccess(msg);
			},
			onError:function(){
				XN.DO.showError('分享失败,请重试');
			}
		});
	},
	onPostSuccess:function(r){
		XN.DO.alert({
			msg:'<div class="large">' + r.msg + '</div>',
			autoHide:3,
			noFooter:true,
			type:(r.status == 1 ? 'error' : null)
		});
	},
	createDialog:function(){
		var tmp,p,s = this;
		p = this.dialog = new XN.UI.panel({
            addIframe : true
        });
		p.setIndex(600);
		p.setHeader('分享');
		tmp = '<div id="popShareContainer"><div class="share_header">' +
'		<div class="tabs clearfix">' +
'				<ul class="toggle_tabs clearfix">' +
'					<li class="first">'+
'						<a id="shareSendTab" onfocus="this.blur()" href="javascript:void(0)">分享给我的好友</a>' +
'					</li>' +
'					<li class="last">' +
'						<a id="shareAddTab" onfocus="this.blur()" class="selected" href="javascript:void(0)">添加到我的分享</a>' +
'					</li>' +
'				</ul>' +
'			</div>' +
'		</div>' +
'		<div id="shareSendContent" class="share_send">' +
'			<div class="share_fields">' +
'				<dl class="composer_fields clearfix">' +
'					<dt id="dt_to_field">' +
'						<label for="to_field">分享给: </label>' +
'					</dt>' +
'					<dd class="field" id="dd_to_field">' +
'						<div id="shareSelectFriends" class="composer"></div>' +
'					</dd>' +
'					<dt id="dt_subject_field">' +
'						<label for="subject_field">标题: </label>' +
'					</dt>' +
'					<dd class="field" id="dd_subject_field">' +
'						<input id="popShareSubjectInput" value="" class="inputtext" type="text">' +
'					</dd>' +
'					<dt id="dt_message_field">' +
'						<label for="message_field">添加评论: </label>' +
'					</dt>' +
'					<dd class="field" id="dd_message_field">' +
'						<textarea name="send_message" id="popShareSendMessage"></textarea>' +
'					</dd>' +
'				</dl>' +
'			</div>' +
'		</div>' +
'		<div id="shareAddContent" class="share_post">' +
'			<div class="share_fields">' +
'				<dl class="composer_fields clearfix">' +
'					<dt id="dt_message_field">' +
'						<label for="message_field">添加评论: </label>' +
'					</dt>' +
'					<dd class="field" id="dd_message_field">' +
'						<textarea name="message" id="sharer_popup_message"></textarea>' +
'					</dd>' +
'					<dt id="dt_privacy_field">' +
'						<label for="dt_privacy_field">浏览权限: </label>' +
'					</dt>' +
'					<dd class="field" id="dd_privacy_field">' +
'						<select id="popShareAuth" class="select">' +
'							<option value="99">所有人</option>' +
'							<option value="0">所有好友</option>' +
'							<option value="-1">自己收藏</option>' +
'						</select>' +
'					</dd>' +
'				</dl>' +
'			</div>' +
'		</div>' +
'		<div id="shareAjaxResult"></div>' +
'		</div></div><div class="loading" id="popShareLoading"><p>载入中...</p></div>' ;
		p.setBody(tmp);
		p.body.addClass('share_popup');
		p.setWidth(465);
		this.friendSelector = new XN.UI.multiFriendSelector();
		$X('shareSelectFriends').addChild(this.friendSelector);
		var yes = new XN.UI.button({text:'分享'});
		yes.onclick = function(){
//			alert('yes');
			s.sendRequest();
		};
		var no = new XN.UI.button({text:'取消',className:'gray'});
		no.onclick = function(){
			p.hide();
		};
		p.setFooter(yes);
		p.setFooter(no);
		p.hide();
		var t = this.tabView = new XN.UI.tabView({
			selectedClass:'selected'
		});
		t.addTab({
			label:'shareSendTab',
			content:'shareSendContent',
			onActive:function(){
				$('shareSendContent').style.display = 'block';
				s.action = 'send';
//				yes.focus();
			}
		});
		t.addTab({
			label:'shareAddTab',
			content:'shareAddContent',
			active:true,
			onActive:function(){
				s.action = 'add';
//				yes.focus();
			}
		});
	},
	del:function(id,type,curpage){
		XN.DO.confirm({
			msg:'您确定要删除此分享吗',
			callBack:function(r){
				if(r){
					var url = '/share/EditShare.do?action=del&sid=' + id ;
					if(type!=''){
						url+='&type=' + type;
					}
					if(curpage!=0){
						url+='&curpage=' + curpage;
					}
//					if($('share_' + id)){
//						$('share_' + id).remove();
//						new XN.NET.xmlhttp(url);
//					}else{
						window.location.href = url;
//					}
				}
			}
		})
	}
};
//XN.DEBUG.On();

	pop_share_new =  function(url,pars){
		XN.APP.share.showDialog(url,pars);
	};

	create_share_div = function(id,owner,host){
		pop_share_new('/share/ajax.do',{id:id,owner:owner,host:host});
	};
	
	create_thread_share_div = function(tid,tribeId){
		pop_share_new('/getshare.do',{tid:tid,tribeId:tribeId});
	};
	
	pop_share = function(){
		try{
			var post = XN.FORM.serialize('popShareParams','hash');
		}catch(e){
			var post = {};
			post.link=ge('link').value;post.type=ge('type').value;post.title=ge('title').value;post.pic=ge('pic').value;post.fromno=ge('fromno').value;post.fromname=ge('fromname').value;post.fromuniv=ge('fromuniv').value;post.albumid=ge('albumid').value;
			post.largeurl=$('largeurl').value;
		}	
		post.summary=$('summary').innerHTML;
		pop_share_new('/share/popup.do',post);
	};
	
	pop_share_for_list = function(index){
		var post = XN.FORM.serialize('popShareParams_' + index,'hash');
		post.summary = $('summary_' + index).innerHTML;
		pop_share_new('/share/popup.do',post);
	};
	
	delete_share = function(id,type,curpage){
		XN.APP.share.del(id,type,curpage);
	};
	play = function(el,t,file){
		el = $(el + '');
		if(t == 'mp3'){
			el.innerHTML = XN.Template.flashPlayer({filename:file});
		}else{
			el.innerHTML = XN.Template.mediaPlayer({filename:file});
		}
	};
	
	playswf = function(el,file,scale){
		var w,h;
		el = $(el + '');
		w = el.parentNode.offsetWidth - 24;
		if(w > 500){
			w = 500;
		}
		h = parseInt(w/scale);
//			el.setStyle('background-image:url(http://xnimg.cn/img/upload_progress.gif);float:none;margin-bottom:10px;');
		el.innerHTML = XN.Template.flash({width:w,height:h,filename:file});
		el.onclick = null;
	};	



  
  
/**
 *  share comment
 */

function show_or_hide(action,share_id,share_owner){
//		var link=ge('show_link_'+share_id);
//		if (!link) {
//			return;
//		}
		if(action=='show'){
			ge('share_comment_'+share_id).style.display='block';
			ge('share_footer_'+share_id).style.display='none';
			//link.innerHTML='隐藏评论';
			//link.onclick=function(){return share_hide_comments(share_id,share_owner);};
		}
		else if(action=='hide'){
			ge('share_comment_'+share_id).style.display='none';
			ge('share_footer_'+share_id).style.display='block';
			window.location='#share_'+share_id;
			//link.innerHTML='发表评论';
			//link.onclick=function(){return share_show_comments(share_id,share_owner);};
		}
	}
	
	function share_hide_comments(share_id,share_owner){
		ge('share_comment_'+share_id).style.display='none';
		show_or_hide('hide',share_id,share_owner);
	}

	function share_show_comments(share_id,share_owner){
		var comment_list=ge('commentList_'+share_id);
		if(!comment_list){
			return;
		}
		var inner=comment_list.innerHTML;
		var regex=/\s/ig;
		inner=inner.replace(regex,'');
		if(inner==''){
			comment_list=$X('commentList_'+share_id);
			var post={share_id:share_id,share_owner:share_owner};
			comment_list.setContent(new XN.NET.xmlhttp('/share/showcomment.do','post='+ XN.JSON.build(post)));
		}
		show_or_hide('show',share_id,share_owner);
		ge('comment_text_'+share_id).focus();
	}
	function share_add_comment_submit(obj,share_id,share_owner){
		var comment_text=ge('comment_text_'+share_id);
		if(XN.STRING.isBlank(comment_text.value)){
			XN.DO.showError('评论内容不能为空');
			return false;
		}
		if(comment_text.value.length>500){
			XN.DO.showError('评论内容不能超过500字');
			return false;
		}
		var error=ge('ajax_msgerror');
		if(error){
			error.parentNode.removeChild(error);
		}
		var post={share_id:share_id,share_owner:share_owner,comment:comment_text.value,ownername:ge('share_ownername_'+share_id).value,title:ge('share_title_'+share_id).value};
		//alert(post.comment);return;
		var comment_body=$xElement('div');
		$('commentList_'+share_id).appendChild(comment_body);
		comment_body.setContent(new XN.NET.xmlhttp('/share/addcomment.do','post='+ encodeURIComponent(XN.JSON.build(post)),function(r){
			var text=r.responseText;
			if(!text.match('ajax_msgerror')) comment_text.value='';
		}));
	}


	function share_delete_comment(obj,share_id,share_owner,comment_id){

		XN.DO.confirm({
			title:'删除该评论',
			msg:'确定要删除吗?',
			callBack:function(r){
				if(r){
					var post={share_id:share_id,share_owner:share_owner,comment_id:comment_id};
					var comment_body=$X('comment_'+comment_id);
					comment_body.setContent(new XN.NET.xmlhttp('/share/deletecomment.do','post='+encodeURIComponent(XN.JSON.build(post)),function(){
							comment_body.parentNode.removeChild(comment_body);
						},{
							onError:function(){
								alert('删除错误');
							}
						}));			
				}
			}
		});
	}

	function share_show_add_comment(obj,share_id){
		var node=obj.parentNode;
		remove_node(node);
		var add_comment=ge('add_comment_input'+share_id);
		show(add_comment);
		var submit_button=ge('add_comment_button'+share_id);
		submit_button.disabled=false;
		var textarea_id='comment'+share_id;
		$(textarea_id).focus();
		return false;
	}
	
	
	function shareonkeydown(e){
		var k = (e.which)?e.which:window.event.keyCode; 
	   if(k==13){
		   sharelink1();return;
	   }
	}
	
	function sharelink1(){
		var sharelink=document.getElementById("sharelink").weblink.value;	
		var regex=/\s/ig;
		sharelink=sharelink.replace(regex,'');
     	if((sharelink=="")||(sharelink=="http://")||(sharelink=="分享网址、音乐、视频")){		
			alert("请输入一个网址");
			return false ;
		}
		if(sharelink.substr(0,7)!="http://"&&sharelink.substr(0,6)!="ftp://"){
			sharelink = "http://"+sharelink;
	        document.getElementById("isUrl").value=sharelink;
		}
		if(/^http:\/\/([A-Za-z0-9\.]*)xiaonei\.com/i.test(sharelink)){
			alert('站内内容可以直接分享，此处请分享站外内容');return false;
		}
		var sharecolumn=document.getElementById("share-pre");
		sharecolumn.style.display="none";
		document.sharelink.submit();
		var shareload=document.getElementById("share-pre-hidden");
		shareload.style.display = 'block';
		shareload.src = 'http://xnimg.cn/img/upload_progress.gif';
	}
