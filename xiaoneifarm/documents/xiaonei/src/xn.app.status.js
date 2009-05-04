null//XN.config.enableReplyAllStatus = false;
//XN.config.enableMediaStatus = true;
XN.namespace( 'config.status' );
XN.config.status.enableMedia = false;
XN.config.status.enableReplyAll = false;
XN.config.status.params = '';

//XN.DEBUG.On();
if ( !XN.APP.status )
{
    XN.APP.status = {};
}

(function( ns )
{
	var EMstaticRoot = XN.ENV.staticRoot;

    ns._errors = {
        1 : '请不要从站外提交',
        2 : '该状态不存在',
        6 : null,
        3 : '内容不能为空',
        4 : '请不要发布政治敏感内容、色情内容、商业广告或其他不恰当内容',
        5 : '你短时间内发表了太多相同的内容',
        9 : '你还不是TA的好友，不能使用\“回复所有人\”'
    };

    ns.getError = function( code )
    {
        return this._errors[ code ] || false;
    };

})( XN.APP.status );

(function( ns )
{
	var STRING = XN.STRING;
	
	var DEBUG = false;
	
	function log( str )
	{
		if ( DEBUG ) XN.log( str );
	}

	ns.updateAction = function( param )
	{
		$extend( this , param );
	};
	
	ns.updateAction.prototype = {
		//params
		maxLength : 140,
		reqeustURI : '/doing/update.do',
        parseMediaURI : '/share/GetUrlInfo.do',
		//getTscURI : '/doing/doing.do',
        enableMedia : false,
		
		//private
		_tscCode : null,
		_postRequest : null,
        _getMediaRequest : null,
		
		//public
		abort : function()
		{
			try
			{
				this._postRequest.abort();
			}catch( e ){}
            try
            {
                this._getMediaRequest.abort();
            }catch( e ){}
		},
		//更新状态
		update : function( sstr )
		{
			var This = this;
			//if ( !this._tscCode ) return;
			sstr = STRING.trim( sstr );
		    
            if ( sstr === '' )
            {
				this.fireEvent( 'postError' , '您不能发布空状态' , sstr );
                return;
            }

			if ( sstr.length > this.maxLength )
			{
				this.fireEvent( 'postError' , '您最多能够输入' + this.maxLength + '个字符' , sstr );
				return;
			}
			
			this.fireEvent( 'beforePost' );
			if ( this.enableMedia && XN.config.status.enableMedia )
            {
                this._parseMedia( sstr );
            }
            else
            {
                this._updateStatus( sstr );
            }

		},

        _parseMedia : function( sstr )
        {
            var This = this;
            var links = /http:\/\/[A-Za-z0-9\%\-\:\+\#\.\?=&_~\/]+[^\:\(\s\u0391-\uFFE5]/i.exec( sstr );
            
            if ( !links )
            {
                this._updateStatus( sstr );
                return;
            }

            var link = links[ 0 ] , media;
            
            var ubbCode;
            
            if ( /(mp3|wma)$/i.test( link ) )
            {
                media = {type:2,link:link};
                ubbCode = sstr.replace( link , '[audio]' );
                this._updateStatus( sstr , ubbCode , media );
                return;
            }

            new XN.NET.xmlhttp({
                url : This.parseMediaURI + '?link=' + encodeURIComponent( link ),
                method : 'get',
                onSuccess : function( r )
                {
                    try
                    {
                        var m = XN.JSON.parse( r.responseText );
                    }
                    catch( e )
                    {
                        This._updateStatus( sstr );
                        return;
                    }


                    switch( m.type )
                    {
                        case 10 :
                            media = {type:3,link:m.url};
                            ubbCode = sstr.replace( link , '[video]' );
                        break;
                        case 6 :
                            media = {type:1,link:link};
                            ubbCode = sstr.replace( link , '[link]' );
                        break;
                    }
                    This._updateStatus( sstr , ubbCode , media );
                },
                onError : function()
                {
                    This._updateStatus( sstr );
                }
            });
        },

        _updateStatus : function( sstr , ubbCode ,  media )
        {
            XN.log( ubbCode );
            var This =  this;
			this._postRequest = new XN.NET.xmlhttp( {
				
				url : this.reqeustURI,
				
				data : 'c=' + encodeURIComponent( ( ubbCode || sstr ) ) + ( media ? '&media=' + encodeURIComponent( XN.JSON.build( media ) ) : '' ) + '&raw=' + encodeURIComponent( sstr ) + '&' + XN.config.status.params,
				
				onComplete : function()
				{
					This.fireEvent( 'postComplete' );
				},
				
				onSuccess : function( r )
				{
					try
                    {
                        r = XN.JSON.parse( r.responseText );
                        if ( r.code == 0 )
                        {
                            if ( XN.STRING.isBlank( r.msg ) )
                            {
                                r.msg = '你可以更新状态，让朋友们知道你在做什么...';
                            }
                            This.fireEvent( 'postSuccess' , r.msg , sstr );
                        }
                        else
                            This.fireEvent( 'postError' , XN.APP.status.getError( r.code ) );
                    }
                    catch( e )
                    {
                        This.fireEvent( 'postError' );
                    }
                },
				
				onError : function()
				{
					This.fireEvent( 'postError' );
				}
			} );
        }
	};
	XN.EVENT.enableCustomEvent( ns.updateAction.prototype );
})( XN.APP.status );


/*
 * new status editor
 */


(function( ns )
{
	var STRING = XN.STRING;
	var addEvent = XN.EVENT.addEvent;
	var currentStatusEditor;	
	var DEBUG = true;

	function log( s )
	{
		if ( DEBUG ) XN.log( s );
	}

	ns.editor = function( params )
	{
		$extend( this , params );
		this.init();
	};

	ns.editor.prototype = {
		
		IDsubmit : 'publisher_statusSubmit',
		IDinput : 'publisher_statusInput',
		IDcounter : 'statusCount',
		IDinputContent : 'statusContent',
		IDcurrentStatus : 'currentStatus',
		IDoriginalStatus : 'currentStatus_bak',
		IDupdateTime : 'statusUpdateTime',
        IDemotion : 'status_emotion',
        IDspecial : 'commendStatus',

		TIPinputDefault : '你可以更新状态，让好友们知道你在做什么...',
		TIPonPostError : '状态更新失败,请重试',
		TIPupdateTime : '刚刚更新',
		TIPnewUser : '你可以更新状态，让朋友们知道你在做什么...',

		CFGshowError : true,
		CFGmaxLength : 140,
        CFGspCookieName : 'sta1', 

		action : null,
        _lastStatus : null,


        _uiType : 'home',

		getConfig : function( key )
		{
			return this[ 'CFG' + key ];
		},
		
		getEl : function( id )
		{
			return $( this[ 'ID' + id ] );
		},
		
		getTip : function( key )
		{
			return this[ 'TIP' + key ];
		},

		init : function()
		{
			var This = this,
			ac;
			
			this._patchForNewUser();

			this.action = ac = new XN.APP.status.updateAction( {
				maxLength : this.getConfig( 'maxLength' ),
                enableMedia : true 
			} );

			ac.addEvent( 'beforePost' , function()
			{
				This._beforePost();
				This.fireEvent( 'beforeUpdate' );
			} );

			ac.addEvent( 'postSuccess' , function( r )
			{
				This._onPostSuccess( r );
				This.fireEvent( 'updateSuccess' );
			} );
			
			ac.addEvent( 'postError' , function( r )
			{
				This._onPostError( r );
				This.fireEvent( 'updateError' , r );
			} );
			
			//attach element event
			var input = this.getEl( 'input' );

			input.addEvent( 'focus' , function( e )
			{
				This._onInputFocus( e );
			} , false );
			
            input.addEvent( 'blur' , function( e )
            {
                This._onBlur();
            } , false );


			this._inputHelper = new XN.FORM.inputHelper( this.getEl( 'input' ) )
			.countSize( this.getEl( 'counter' ) , this.getConfig( 'maxLength' ) )
			.setDefaultValue( this.getTip( 'inputDefault' ) );
			
            XN.EVENT.addEvent( this.getEl( 'input' ) , 'keydown' , function( e )
            {
                if ( e.keyCode == 13 )
                {
				    This.update( This.getEl( 'input' ).value );
                }
            } );

			this._enableSubmit();

            this.getEl( 'emotion' ).addEvent( 'click' , function( e )
            {
                e = e || window.event;
                XN.EVENT.stop( e );
                This._parseEmotionEvent( e );
            } ).addEvent( 'mouseover' , function( e )
            {
                This._overEmotion = true;
            }).addEvent( 'mouseleave' , function()
            {
                This._overEmotion = false;
            }).addEvent( 'mousedown' , function( e )
            {
                This.getInputPos();
                XN.event.stop( e || window.event );
            });

            if ( this.getEl( 'special' ) )
            {
                this.getEl( 'special' ).addEvent( 'mousedown' , function()
                {
                    This.getInputPos();
                });
            }

            if ( $( 'status_emotion_legend' ) )
            {
                this._uiType = 'other';
                
                this.showEmotion = function()
                {
                    $( 'status_emotions' ).show();
                    $( 'status_emotion_legend' ).hide();
                };

                this.hideEmotion = function()
                {
                    $( 'status_emotions' ).hide();
                    $( 'status_emotion_legend' ).show(); 
                };

                $( 'status_emotion_legend' ).onmousedown = function( e )
                {
                    XN.event.stop( e || window.event );
                    This.showEmotion();
                };
                
                XN.event.addEvent( document , 'mousedown' , function( e )
                {
                    This.hideEmotion();
                });

            }
            else
            {
                this.showEmotion = function()
                {
                    this.getEl( 'emotion' ).show();
                };

                this.hideEmotion = function()
                {
                    this.getEl( 'emotion' ).hide();
                };
            }
		},
        
        getInputPos : function()
        {
             this._currentInputPos = $CursorPosition( this.getEl( 'input' ) );
        },

        showEmotion : XN.func.empty,
        hideEmotion : XN.func.empty,

		_patchForNewUser : function()
		{
			if ( STRING.isBlank( this.getEl( 'currentStatus' ).innerHTML ) )
			{
				this.getEl( 'currentStatus' ).innerHTML = this.getTip( 'newUser' );
				this.getEl( 'updateTime' ).innerHTML = '';
			}
		},
        
        _parseEmotionEvent : function( e )
        {
            var el = XN.EVENT.element( e );
            if ( el.tagName.toLowerCase() == 'a' ) el = el.getElementsByTagName( 'img' )[ 0 ];
            if ( el.tagName.toLowerCase() == 'img' && el.getAttribute( 'emotion' ) ) this.addEmotion( el.getAttribute( 'emotion' ) );
        },

        _forSpecial : false,

        addEmotion : function( code , sp )
        {

            if ( sp )
            {
                XN.Cookie.set( this.getConfig( 'spCookieName' ) , '1' , 10000 , '/' , '.xiaonei.com' );
                this._forSpecial = true;
            }

            var This = this;
            
            if ( this.forSpecial )
            {
                code = this.forSpecial( code );   
            }

            var input = this.getEl( 'input' );

            if ( this.getTip( 'inputDefault' ) == input.value )
            {
                input.value = '';
            }
            
            var pos = this._currentInputPos;
            input.value = input.value.slice( 0, pos.start ) + code + input.value.slice( pos.end );
            
            input.blur();
            
            setTimeout( function()
            {
                This._inputHelper.focus( pos.start + code.length );
            } , 10 );
        },
        
		update : function( status )
		{
			if ( this.getTip( 'inputDefault' ) == status ) return;
            this._lastStatus = XN.STRING.trim( this.getEl( 'currentStatus' ).innerHTML );
            this.action.update( status );
		},

		_disableSubmit : function()
		{
			this.getEl( 'submit' ).onclick = null;
			this.getEl( 'input' ).disalbe = true;
		},
		
		_enableSubmit : function()
		{
			var This = this;

			var submit = this.getEl( 'submit' )
			submit.onclick = function( e )
			{
				XN.EVENT.stop( e || window.event );
				This.update( This.getEl( 'input' ).value );
			};
			submit.delClass( 'gray' );

			this.getEl( 'input' ).disabled = false;
		},

		_resetInput : function()
		{
			var input = this.getEl( 'input' );
			input.value = this.getTip( 'inputDefault' );
			input.style.color = '#888';
			input.blur();
		},
		
		advancedMode : function()
		{
            if ( this._modeTimer )
            {
                clearTimeout( this._modeTimer );
                this._modeTimer = null;
            }
            
            if ( this._uiType == 'home' )
            {
			    this.getEl( 'inputContent' ).addClass( 'inputactve' );
			    this.getEl( 'submit' ).show();
			    this.getEl( 'counter' ).show();
                //this.getEl( 'emotion' ).show();
                if ( this.getEl( 'special' ) )
                {
                    this.getEl( 'special' ).hide();
                }
			
            }
            this.showEmotion();

			this.getEl( 'input' ).style.color = '#333';
			//this._action.getTscCode();
			$("statusEdit").style.backgroundPosition="0 0";   
            
            this.fireEvent( 'advancedMode' );
            this._patchForIE();

		},

		simpleMode : function()
		{
            var This = this;
			if ( this._uiType == 'home' )
            {
                this.getEl( 'inputContent' ).delClass( 'inputactve' );
			    this.getEl( 'counter' ).hide();
                //this.getEl( 'emotion' ).hide();
			    
                if ( this.getEl( 'special' ) && (!this._forSpecial) )
                {
                    this.getEl( 'special' ).show();
                }

                //fix bug for ie
                if ( this._modeTimer )
                {
                    clearTimeout( this._modeTimer );
                    this._modeTimer = null;
                }
            
                this._modeTimer = setTimeout( function()
                {
                    This.getEl( 'submit' ).hide();
                    //This._patchForIE();
                } , 20 );
            }

			$("statusEdit").style.backgroundPosition="0 -58px";        
            this.hideEmotion();
            
            this.fireEvent( 'simpleMode' );
		},
		
		_resetInputCounter : function( focre )
		{
			var counter = this.getEl( 'counter' );
            var v = this.getEl( 'input' ).value;
            counter.innerHTML = ( focre ? 0 : v.length ) + '/' + this.getConfig( 'maxLength' );
			counter.delClass( 'full' );
			if ( this._uiType == 'home' )
            {
                counter.hide();
		    }
            this.fireEvent( 'resetCounter' );
            XN.log( focre );
		},
		
		_onBlur : function()
		{
            var This = this;
            var input = this.getEl( 'input' );
            var v = input.value;
			if ( v !== '' && v != this.getTip( 'inputDefault' ) ) return;
            if ( this._overEmotion ) return;
            This.simpleMode();
		},
        
        _patchForIE : function()
        {
            if ( XN.BROWSER.IE7 )
            {
                document.body.style.zoom = 1.1;
                document.body.style.zoom = '';
            }        
        },

		_onInputFocus : function()
		{
            var input = this.getEl( 'input' );
            if ( input.value == this.getTip( 'inputDefault' ) )
            {
                input.value = '';
            }
            this._resetInputCounter();
			this.advancedMode();
			this.fireEvent( 'inputFocus' );
		},

		_beforePost : function()
		{
			this._disableSubmit();
			this.getEl( 'currentStatus' ).innerHTML = '<img class="loading-img" src="' + XN.ENV.staticRoot + 'img/upload_progress.gif"/>更新中，请稍候..';
		},

		_onPostSuccess : function( r )
		{

            if ( this._specialCode && r.indexOf( this._specialCode ) !== -1 )
            {
                XN.COOKIE.set( 'sta1' , '1' , 10000 );
            }

			this._enableSubmit();
			this._resetInput();
			this._resetInputCounter( true );
			this.simpleMode();

			this.getEl( 'updateTime' ).innerHTML = this.getTip( 'updateTime' );

			var currentStatus = this.getEl( 'currentStatus' );
			currentStatus.innerHTML = r;
			currentStatus.style.backgroundColor = 'rgb(255,255,150)';
			setTimeout( function(){
				XN.Effect.gradient( currentStatus , 255 , 255 , 150 , function()
                {
                    currentStatus.style.backgroundColor = 'transparent';
                });
			} , 50 );
		},

		_onPostError : function( r )
		{
			this._enableSubmit();
			this._resetInputCounter();
			this._resetInput();
			this.simpleMode();
            this.getEl( 'currentStatus' ).innerHTML = this._lastStatus;
            this.getEl( 'updateTime' ).innerHTML = '';
			if ( this.getConfig( 'showError' ) ) XN.DO.showError( r || this.getTip( 'onPostError' ) );
		}
	};

	XN.EVENT.enableCustomEvent( ns.editor.prototype );

})( XN.APP.status );


XN.DOM.readyDo( function()
{
	//if ( 'home' !== document.body.id ) return;
    if ( !$( 'statusEdit' ) || !$( 'publisher_statusInput' ) ) return;
	
    var statusEditor = new XN.APP.status.editor();

    statusEditor.forSpecial = function( code )
    {
        return code;
    }
    
    /*if ( $( 'status-guide-notice' ) )
    {
        statusEditor.getEl( 'input' ).focus();
        statusEditor.advancedMode();
    }*/

    if ( /home|pageStatus/.test( document.body.id ) )
    {
        var input = statusEditor.getEl( 'input' );
        var icon = $( 'status_emotion_legend' );
        var emotion =  icon ? $( 'status_emotions' ) : statusEditor.getEl( 'emotion' );
        function showEm()
        {
            emotion.show();
            if ( icon )
            {
               icon.hide();
            }
            delE();
        }

        function delE()
        {
            input.delEvent( 'keydown' , showEm );
            input.delEvent( 'blur' , delE );
        }
        
        setTimeout( function()
        {
            input.focus();
            input.addEvent( 'blur' , delE );
            input.addEvent( 'keydown' , showEm );
            //hideEm();
            if ( XN.Browser.IE )
            {
                setTimeout( function()
                {
                    hideEm();
                } , 100 );
            }
            else
            {
                hideEm();
            }
        },0);

        function hideEm()
        {
            
            emotion.hide();
            
            if ( icon )
            {
                icon.show();
            }
        }
        

    }

    window.statusEditor = statusEditor;
});

/*
XN.dom.ready(function()
{
    if ( $( 'status_emotion_legend' ) )
    {
        $( 'status_emotions' ).hide();
        $( 'status_emotion' ).show();
    }
});
*/

/*
XN.DOM.readyDo( function()
{
	if ( 'profile' !== document.body.id ) return;
	var statusEditor = new XN.APP.status.oldEditor();
});
*/

/*
 * 状态回复
 */

(function( ns ){

    var ecd = function( str )
    {
        return encodeURIComponent( str );
    };

    var dcd = function( str )
    {
        return decodeURIComponent( str );
    };

    var $ = xn_getEl;

    var editors = {};
    
    getReplyEditor = function( idx , delFlag )
    {
        return editors[ delFlag + idx ];
    };

    delReplyEditor = function( idx , delFlag )
    {
        delete editors[ delFlag + idx ];
    };

    ns.replyEditor = function( params )
    {
        this.config = this.config || {};

        $extend( this.config ,
        {
            loadReplyURI : '/doing/getReply.do',
            sendReplyURI : '/doing/reply.do',
            delReplyURI : '/doing/deleteReply.do',
            maxlength : 140,
            showMore : true
        });

        $extend( this.config , params );
        //this._action = new XN.APP.status.replyAction();
        editors[ this.getConfig( 'delFlag' ) + this.getConfig( 'idx' ) ] = this;
    };

    ns.replyEditor.prototype =
    {
        _tips : {
            loadError : '加载回复失败',
            replyError : '状态回复失败',
            deleteConfirm : '确定要删除这条回复?',
            deleteError : '删除回复失败',
            inputTip : '添加回复',
            sending : '正在发送...'
        },


        _tscCode : null,

        _replyData : null,

        _replyRequest : null,

        _replyCount : null,

        _showMore : false,

        _hasLoadAll : false,
        
        isProfile : function()
        {
            return 'profile' == document.body.id;
        },


        abortRequest : function()
        {
            try
            {
                this._replyRequest.abort();
            }catch( e ){}
        },

        getTip : function( key )
        {
            return this._tips[ key ];
        },

        getConfig : function( key )
        {
            if ( key == 'idx' ) return this.config[ 'doingId' ];
            return this.config[ String( key ) ];
        },

        getEl : function( id )
        {
            if ( id == 'feedbody' ) return $( this.getID( 'feedbody' ) ) || $( this.getID( 'replyfordoing' ) );
            return $( this.getID( id ) );
        },
        
        getID : function( id )
        {
            if ( this.getConfig( 'delFlag' ) == 'p' )
                return id + '_p_' + this.getConfig( 'idx' );

            return id + this.getConfig( 'idx' );
        },
        
        canDel : function()
        {
            return this._canDel;
            return this.getConfig( 'delFlag' ) == 'd';
        },

        isHostId : function( id )
        {
            return this.getConfig( 'hostId' ) === String( id );
        },
        
        replyMode : 'none',

        replyTo : function( sid , uid , uname )
        {
            uname = dcd( uname );
            this.clearReply();
            this.replyMode = 'one';

            this._replyData = {
                sid : sid,
                uid : uid,
                uname : uname
            };

            var input = this.getEl( 'input' );
            
            input.value = '回复' + uname + ': ' + input.value;

            //this.getEl( 'input' ).focus();
            this._inputHelper.focus();
        },

        replyToAll : function()
        {
            var input = this.getEl( 'input' );
            
            this.replyMode = 'all';

            this.stripReply();

            input.value = '回复大家: ' + input.value;

            this._inputHelper.focus();

            this._replyData = {
                toAll:true
            };
        },
        
        stripReply : function()
        {
            var input = this.getEl( 'input' );
            var v = input.value.replace( new RegExp( '^回复.*:( ?)' ) , '' );
            input.value = v.replace( new RegExp( '^添加回复' ) , '' );        
        },


        clearReply : function()
        {
            this.replyMode = 'none';
            this.stripReply();
            this.getEl( 'replyall' ).checked = false;
            this._replyData = null;
        },
        
        updateReplyCounter : function()
        {
            var c1 = this.getEl( 'counter_m' );
            if ( c1 )
                c1.innerHTML = this._replyCount;
            
            var c2 = this.getEl( 'replyCount' );
            if ( c2 )
                c2.innerHTML = this._replyCount;
        },

        sendReply : function()
        {
            var This = this;

                
            reply = this.getEl( 'input' ).value;
            
            if ( reply == this.getTip( 'inputTip' ) )
                return;

            if ( XN.STRING.isBlank( reply ) )
            {
                XN.DO.showError( '输入不能为空' );
                return;
            }
            
            if ( reply.length > this.getConfig( 'maxlength' ) )
            {
                XN.DO.showError( '最多只能输入' + this.getConfig( 'maxlength' ) + '个字符' );
                return;
            }


            var params = {};

            params[ 'doingId' ] = this.getConfig( 'doingId' );
            params[ 'owner' ] = this.getConfig( 'ownerId' );
            //params[ 'tsc' ] = this._tscCode;
            
            if ( this._replyData )
            {
                if ( this._replyData.toAll )
                {
                    params[ 'replayAllUser' ] = 1;
                    if ( !new RegExp( '^回复大家:' ).test( reply ) )
                    {
                        if ( reply.length + 6 <= this.getConfig( 'maxlength' ) )
                        {
                            reply = '回复大家: ' + reply;
                        }
                    }
                }
                else
                {
                    params[ 'rpLayer' ] = '1';
                    params[ 'sToId' ] = this._replyData[ 'uid' ];
                    params[ 'sToName' ] = this._replyData[ 'uname' ];
                    params[ 'secondaryReplyId' ] = this._replyData[ 'sid' ];
                }
            }
            else
            {
                params[ 'rpLayer' ] = '0';
            }
            
            params[ 'c' ] = reply;
            
            this.loadingMode();

            this.fireEvent( 'beforePost' , this._replyData , this );
            
            this._replyRequest = new XN.NET.xmlhttp(
            {
                url : this.getConfig( 'sendReplyURI' ),
                data : XN.ARRAY.toQueryString( params ),
                onSuccess : function( r )
                {
                    var rt = XN.JSON.parse( r.responseText );
                    if ( rt.code == 0 )
                        This._onReplySuccess( rt );
                    else
                    {
                        This._onReplyError( XN.APP.status.getError( rt.code ) ); 
                        //This._tscCode = rt.msg;
                    }
                },
                onError : function()
                {
                     This._onReplyError( This.getTip( 'replyError' ) ); 
                }
            });
        },

        disableSubmit : function()
        {
            var el = this.getEl( 'submit' );
            el.addClass( 'gray' );
            el.disabled = true;
        },

        enableSubmit : function()
        {
            var el = this.getEl( 'submit' );
            el.delClass( 'gray' );
            el.disabled = false;
        },

        _onReplyError : function( msg )
        {
            this.resetInput();
            this.simpleMode();
            XN.DO.showError( msg );
        },

        _onReplySuccess : function( v )
        {
            var This = this;
            
            //this._tscCode = v.tsc;
            
            //this._replyData = null;
            this.clearReply();

            this._replyCount ++;
            this.updateReplyCounter();

            this.resetInput();

            this.simpleMode();
            this.getEl( 'input' ).blur();

            this.getEl( 'input' ).value = this.getTip( 'inputTip' );
            this.getEl( 'input' ).style.color = '#888';
            
            /*
             }
            else
                this.getEl( 'input' ).blur();
            */
            
            var div = $element( 'div' );
            div.className = 'statuscmtitem';
            div.id = 'status_reply_' + v.id;

            var html = [];

            //html.push( '<div id="' + v.id + '" class="statuscmtitem">' );
			html.push( '<span class="share-n-hide float-right"><a class="x-to-hide" href="#nogo" onclick="getReplyEditor(\'' + This.getConfig( 'idx' ) + '\',\'' + this.getConfig( 'delFlag' ) + '\').del(\'' + v.replyerId + '\',\'' + v.id + '\');"> </a></span>' );
            html.push( '<a class="minfriendpic" style="background-image: url(' + v.replyerHead + ')" href="http://' + XN.ENV.domain + '/profile.do?id=' + v.replyerId + '"></a>' );
            html.push( '<p class="replybody">' );
            html.push( '<a class="replyername" href="http://' + XN.ENV.domain + '/profile.do?id=' + v.replyerId + '">' + v.replyerName + '</a><span class="time">' + v.replyTime + '</span><br/>' );
           

            html.push( '<span class="replycontent">' + v.replyContent + '</span>' );

            html.push( '</p>' );
           // html.push( '</div>' );

            div.innerHTML = html.join( '' );

            this.getEl( 'replyList' ).show();
            this.getEl( 'replyList' ).appendChild( div );

            this.showMore();

        },
        
        del : function( uid , sid )
        {
            var This = this;

            function request()
            {
                var p = {};
                p[ 'replyId' ] = sid;
                p[ 'doingId' ] = This.getConfig( 'doingId' );
                p[ 'owner' ] = This.getConfig( 'ownerId' );

                new XN.NET.xmlhttp(
                {
                    url : This.getConfig( 'delReplyURI' ),
                    data : XN.ARRAY.toQueryString( p ),
                    onSuccess : function()
                    {
                        This._onDeleteSuccess( sid );
                    },
                    onError : function()
                    {
                        XN.DO.showError( This.getTip( 'deleteError' ) );
                    }
                });
            }
            
            //alert( 1 );
            XN.DO.confirm(
            {
                message : this.getTip( 'deleteConfirm' ),
                callBack : function( r )
                {
                    if ( r ) request();
                }
            });
        },
        
        _onDeleteSuccess : function( id )
        {
            this._replyCount --;
            this.updateReplyCounter();
            
            $( 'status_reply_' + id ).remove();
            
            if ( !this._showMore ) 
                this.showMore();
        },

        load : function( more )
        {
            var This = this;
            new XN.NET.xmlhttp(
            {
                data : 'doingId=' + this.getConfig( 'doingId' ) + '&owner=' + this.getConfig( 'ownerId' ),
                url : this.getConfig( 'loadReplyURI' ),
                onSuccess : function( r )
                {
                    try
                    {
                        var rt = XN.JSON.parse( r.responseText );
                        if ( rt.code !== 0 )
                        {
                            XN.DO.showError( XN.APP.status.getError( rt.code ) || This.getTip( 'loadError' ) );
                            return;
                        }
                    }
                    catch( e )
                    {
                        XN.DO.showError( This.getTip( 'loadError' ) );
                        return;
                    }

                    //This._tscCode = rt.tsc;
                    This._replyCount = rt.replyList.length;
                    //This.config.ownerId = rt.ownerid;
                    This._canDel = This.isHostId( rt.ownerid )
                    if ( more )
                    {
                        This._hasLoadAll = true;
                        This.renderReplys( rt.replyList );
                        This.showMore();
                    }
                    else
                        This._updateUIonLoadReply( rt.replyList );
                },
                onError : function()
                {
                    XN.DO.showError( This.getTip( 'loadError' ) );
                }
            });
        },

        loadMore : function()
        {
            this.load( true );
        },
        
        loadJSON : function( json )
        {
            this.loadFromJSON = true;
            this._replyCount = json.length;
            this._canDel = this.isHostId( json.ownerid );
            this.config[ 'ownerId' ] = json.ownerid;
            this._updateUIonLoadReply( json.replyList );
            //this.showMore();
        },

        showMore : function()
        {
            this._showMore = true;
            if ( !this.getEl( 'show_more_link' ) ) return;
            this.getEl( 'show_more_link' ).hide();
            
            if ( this.loadFromJSON && !this._hasLoadAll )
                this.loadMore();
            this.getEl( 'replyList' ).delClass( 'nomore' );
            this.getEl( 'replyList' ).addClass( 'blockmore' );
        },
        
        hideMore : function()
        {
            this._showMore = false;
            if ( !this.getEl( 'show_more_link' ) ) return;
            this.getEl( 'show_more_link' ).show();
            this.getEl( 'replyList' ).addClass( 'nomore' );
            this.getEl( 'replyList' ).delClass( 'blockmore' );            
        },

        _updateUIonLoadReply : function( obj )
        {
            var This = this;
            var html = [];

            html.push( '<div class="min-cmtbox statustab">' );
            html.push( '<div class="mincmt-body">' );
            
            //回复列表开始

            html.push( '<div class="statuscmtlist nomore">' );
            
            html.push( '<div style="display:none;" id="' + this.getID( 'replyList' ) + '">' );
            html.push( '</div>' );

            
            //回复编辑框开始


            html.push( '<div id="' + this.getID( 'reply_editor' ) + '" class="statuscmtitem reply-adding">' );

            html.push( '<div>' );
            
            if ( !this.isProfile() )
            {
                html.push( '<span id="' + this.getID( 'user_head' ) + '" style="display:none;background-image: url(' + XN.user.tinyPic + ');" class="minfriendpic"></span>' );
            }

            html.push( '<textarea id="' + this.getID( 'input' ) + '" class="input-text archive-inp" type="text" value="" style="height:16px;" cols="30" rows="1"></textarea>' );

            html.push( '</div>' );
            

           html.push( [
                '<div class="reply-nav clearfix" style="display:none;" id="' + this.getID( 'buttons' ) + '">',
                    '<span class="replyAll clearfix">',
                    '<span style="display:none;" id="' + this.getID( 'word_counter' ) + '" class="mincmtcount float-right">0/70</span>',
                    ' <input style="display:none;" class="input-button" id="' + this.getID( 'submit' ) + '" type="submit" value="回复" />&nbsp;',
                    '<span style="display:none;" id="' + this.getID( 'replyallc' ) + '">',
                    '<input type="checkbox" name="' + this.getID( 'replyall' ) + '" id="' + this.getID( 'replyall' ) + '"/>',
                    '<label for="' + this.getID( 'replyall' ) + '">回复所有人</label>',
                    '</span>',
                    '</span>',
                '</div>'
            ].join( '' ) );
            
            //html.push( '<a name="' + this.getID( 'goto_reply_editor' ) + '"></a>' );
            html.push( '</div>' );

            //回复编辑框结束

            
            html.push( '</div>' );
            
            //回复列表结束

            html.push( '</div>' );
            html.push( '</div>' );

            this.getEl( 'feedbody' ).innerHTML = html.join( '' );
            
            this.renderReplys( obj , true );

            this.attachEvent();

            if ( this.getConfig( 'showMore' ) && !this.loadFromJSON )
                this.showMore();
            else
                this.hideMore();

            this.show( this.getConfig( 'showMore' ) ? 'advance' : 'simple' );

        },

        renderReplys : function( obj , init )
        {
            var This = this;
            var length = obj.length;
            
            var html = [];

            function addSpeClass( i )
            {
                if ( i > 0 && i < length - 1 ) return 'more';
                return '';
            }

            XN.ARRAY.each( obj , function( i , v )
            {
				
                html.push( '<div id="status_reply_' + v.id + '" class="statuscmtitem ' + addSpeClass( i ) + '">' );
				if ( This.isHostId( v.ubid ) || This.canDel() )
                {
                    html.push( '<span class="share-n-hide float-right"><a class="x-to-hide" href="#nogo" onclick="getReplyEditor(\'' + This.getConfig( 'idx' ) + '\',\'' + This.getConfig( 'delFlag' ) + '\').del(\'' + v.ubid + '\',\'' + v.id + '\');"> </a></span>' );
                }

                html.push( '<a class="minfriendpic" style="background-image: url(' + v.replyer_tinyurl + ')" href="http://' + XN.ENV.domain + '/profile.do?id=' + v.ubid + '"></a>' );
                html.push( '<p class="replybody">' );
                html.push( '<a class="replyername" href="http://' + XN.ENV.domain + '/profile.do?id=' + v.ubid + '">' + v.ubname + '</a><span class="time">' + v.replyTime + '</span><br/>' );
                
                html.push( '<span class="replycontent">' + v.replyContent + '</span>' );
                
                if ( !This.isHostId( v.ubid ) )
                {
                    html.push( '<a href="#nogo" onclick="getReplyEditor(\'' + This.getConfig( 'idx' ) + '\',\'' + This.getConfig( 'delFlag' ) + '\').replyTo( \'' + v.id + '\',\'' + v.ubid + '\',\'' + ecd( v.ubname ) + '\');">回复</a>' );
                }
                
                /*if ( This.canDel() )
                {
                    html.push( '<a href="#nogo" onclick="getReplyEditor(\'' + This.getConfig( 'idx' ) + '\',\'' + This.getConfig( 'delFlag' ) + '\').del(\'' + v.fid + '\',\'' + v.id + '\');">删除</a>' );
                }
                else 
                */
                
                

                html.push( '</p>' );
                html.push( '</div>' );
                

                if ( i == 0 &&  This._replyCount > 2  )
                {
                    var moreTip = '显示全部';
                    if ( This._replyCount >= 100 )
                    {
                        moreTip = '显示最新';
                    }
                    html.push( '<div id="' + This.getID( 'show_more_link' ) + '" class="statuscmtitem showmorereply">' );
                    html.push( '<a href="#nogo" onclick="getReplyEditor(\'' + This.getConfig( 'idx' ) + '\',\'' + This.getConfig( 'delFlag' ) + '\').showMore();">' + moreTip + '<span id="' + This.getID( 'counter_m' ) + '">' + Math.min( This._replyCount , 100 ) + '</span>条</a>' );
                    html.push( '</div>' );
                }

            });

            if ( obj.length ) this.getEl( 'replyList' ).show();
            this.getEl( 'replyList' ).innerHTML =  html.join( '' );

            if ( XN.browser.IE && document.body.id == 'profile' )
            {
                XN.ui.refreshAll();
            }
        },
        
        /*keepEditMode : function()
        {
            this._keepEditMode = 
        };
        */
        attachEvent : function()
        {
            var This = this;

            this.getEl( 'input' ).addEvent( 'focus' , function()
            {
				
                if ( This.getEl( 'input' ).value == This.getTip( 'inputTip' ) )
                {
                    This.resetInput();
                }
                This.editMode();
                //This.getEl( 'emotions' ).show();
            } );

            this.getEl( 'input' ).addEvent( 'blur' , function()
            {
				
                var v = This.getEl( 'input' ).value;
                if (  v !== '' && v != This.getTip( 'inputTip' ) ) return;
                This.simpleMode();
                //This.getEl( 'emotions' ).hide();
            } );

            //this.getEl( 'emotions' ).show();
            
            this.getEl( 'submit' ).addEvent( 'click' , function()
            {
                var v =  This.getEl( 'input' ).value;
                if ( v === '' || v == This.getTip( 'inputTip' ) ) return;
                This.sendReply();
            } );

            this._inputHelper = new XN.FORM.inputHelper( this.getEl( 'input' ) )
            .onEsc( function()
            {
                This.hide();
            } )
            .countSize( this.getID( 'word_counter' ) , this.getConfig( 'maxlength' ) )
            .setDefaultValue( this.getTip( 'inputTip' ) );
            
            XN.EVENT.addEvent( this.getEl( 'input' ) , 'keydown' , function( e )
            {
                e = e || window.event;
                if ( e.keyCode == 13 )
                {
                  This.sendReply();  
                }
            } );

            /*XN.event.addEvent( this.getEl( 'replyall' ) , 'mousedown' , function( e )
            {
                XN.event.stop( e || window.event );
            });*/

            XN.event.addEvent( this.getEl( 'replyall' ) , 'click' , function( e )
            {
                //XN.event.stop( e || window.event );
                    if ( This.getEl( 'replyall' ).checked )
                    {
                        This.replyToAll();
                    }
                    else
                    {
                        This.clearReply();
                    }
            });

            /*
            this.getEl( 'emotions' ).addEvent( 'mousedown' , function( e )
            {
                e = e || window.event;
                XN.EVENT.stop( e );
                This._parseEmotion( e );
            } );
            */
        },

        resetInput : function()
        {
            this.getEl( 'input' ).disabled = false;
            this.getEl( 'input' ).value = '';
            this.resetInputCounter();
        },

        resetInputCounter : function()
        {
            var counter = this.getEl( 'word_counter' )
			counter.innerHTML = this.getEl( 'input' ).value.length + '/' + this.getConfig( 'maxlength' );
			counter.delClass( 'full' );
        },

        _modeTimer : null,
        
        _firstFocus : true,

        editMode : function()
        {
            if ( this._modeTimer )
            {
                clearTimeout( this._modeTimer );
                this._modeTimer = null;
            }
            if ( !this.isProfile() )
            {
                this.getEl( 'reply_editor' ).addClass( 'actived' );
                this.getEl( 'user_head' ).show();
            }
            this.getEl( 'input' ).disabled = false;
            this.getEl( 'input' ).style.color = '#333';
            this.getEl( 'input' ).style.height = '29px';
            this.getEl( 'input' ).style.border="1px solid #5D74A2";
            this.enableSubmit();
            this.getEl( 'submit' ).value = '回复';
            //this.getEl( 'emotions' ).show();
            this.getEl( 'submit' ).show();
            this.getEl( 'word_counter' ).show();
            if ( this._replyCount && !this.isProfile() && XN.config.status.enableReplyAll ) 
            {
                this.getEl( 'replyallc' ).show(); 
            }
            this.resetInputCounter();
            this.getEl( 'buttons' ).show();
        },

        simpleMode : function()
        {
            XN.log( 'simpleMode' );

            var This = this;

            //防止blur时造成页面高度变化,从而使用户点不到要点的link

            //XN.log( 'yes' );
            if ( this._modeTimer )
            {
                clearTimeout( this._modeTimer );
                this._modeTimer = null;
            }

            this._modeTimer = setTimeout(function()
            {
                //防止在新鲜事中元素被销毁从而找不到input

                if ( !This.getEl( 'input' ) ) return;
                This.getEl( 'buttons' ).hide();
                if ( !This.isProfile() )
                {
                    This.getEl( 'reply_editor' ).delClass( 'actived' );
                    This.getEl( 'user_head' ).hide();
                }
                This.getEl( 'input' ).disabled = false;
                This.getEl( 'input' ).style.height = '16px';
                //This.getEl( 'emotions' ).hide();
                This.getEl( 'submit' ).hide();
                This.getEl( 'word_counter' ).hide();
                This.getEl( 'input' ).style.border="1px solid #BDC7D8";
                if ( XN.config.status.enableReplyAll )
                {
                    This.getEl( 'replyallc' ).hide();
                }
            } , 200 );
        },

        loadingMode : function()
        {
            this.disableSubmit();
            this.getEl( 'submit' ).value = this.getTip( 'sending' );
            this.getEl( 'input' ).disabled = true;
            //this.getEl( 'emotions' ).hide();
        },

        _parseEmotion : function( e )
        {
            var el = XN.EVENT.element( e );
            if ( el.tagName.toLowerCase() == 'img' ) el = el.parentNode;
            if ( !el.getAttribute( 'emotion' ) ) return;
            this.addEmotion( el.getAttribute( 'emotion' ) );
        },
        
        addEmotion : function( ubb )
        {

            var input = this.getEl( 'input' );

            if ( this.getTip( 'inputTip' ) == input.value )
            {
                input.value = '';
            }
            
            input.value += ubb;
            
            input.blur();
            input.focus();
            input.style.color = '#333';
        },

        changeMode : function()
        {
            if ( this.view == 'reply' )
                this.hide();
            else
                this.show();
        },

        show : function( mode )
        {
            mode = mode || 'advance';
            
            this.view = 'reply';
            this._replyData = null;
            this.getEl( 'feedbody' ).show();
            this.getEl( 'replyKey' ).innerHTML = '收起回复';
            
            if ( mode == 'advance' )
            {
                this.getEl( 'input' ).focus();
                this.getEl( 'input' ).style.color = '#333';
                //window.location.href = '#' + this.getID( 'goto_reply_editor' );
                //window.scrollTop += 20;
                this.showMore();
            }
            //this.showMore();
        },

        hide : function()
        {
            var count;
            if ( this._replyCount == 0 )
            {
                count = '';
            }
            else if ( this._replyCount >= 100 )
            {
                count = '100+条';
            }
            else
            {
                count = this._replyCount + '条';
            }

            this.view = 'close';
            this.resetInput();
            this.getEl( 'feedbody' ).hide();
            this.getEl( 'replyKey' ).innerHTML = '<span id="' + this.getID( 'replyCount' ) + '">' + count + '</span>回复';
            this.clearReply();
        }
    };

    XN.EVENT.enableCustomEvent( ns.replyEditor.prototype )

})( XN.APP.status );


//XN.DEBUG.On();

getReplyOfDoingFromJSON = function( json , doingId , hostId , length )
{
    //json = XN.JSON.parse( json );
    //json.length = length;
    json.length = parseInt( length );
    new XN.APP.status.replyEditor(
    {
        doingId : doingId,
        hostId : hostId,
        delFlag : 'f',
        showMore : false
    }).loadJSON( json );    
};

getReplyOfTheDoing = function( doingId , ownerId , hostId , delFlag , auto )
{

    try
    {
        XN.app.status.notify.del( doingId );
    }
    catch(e){}

    var ed = getReplyEditor( doingId , delFlag );
    
    if ( ed )
    {
        try
        {
            //在新鲜事中html被销毁后重新构造
            var ce = ed.getEl( 'input' ).value;
            ed.changeMode();
            return;
        }catch(e){}
    }

    var showMore = !( auto || false );

    new XN.APP.status.replyEditor(
    {
        doingId : doingId,
        hostId : hostId,
        delFlag : delFlag,
        showMore : showMore,
        ownerId : ownerId
    }).load();

};

function replyDelete()
{
    var url,
    p = {},
    tip;

    var ars = arguments;
    if ( ars[ 2 ] )
    {
        url = '/doing/deleteReply.do';
        p[ 'replyId' ] = ars[ 2 ];
        p[ 'doingId' ] = ars[ 1 ];
        tip = '确定要删除这条回复吗?'
    }
    else
    {
        url = '/doing/deleteDoing.do';
        p[ 'id' ] = ars[ 1 ];
        if ( ars[ 3 ] ) p[ 'ownerid' ] = ars[ 3 ];
        tip = '确定要删除这条状态吗?'
    }
    
    function onSuccess()
    {
        window.location.reload(); 
    }

    function del()
    {
        new XN.NET.xmlhttp(
        {
            url : url,
            data : XN.ARRAY.toQueryString( p ),
            onComplete : onSuccess
        });
    }

    XN.DO.confirm(
    {
        message : tip,
        callBack : function( r )
        {
            if ( r )
                del();
        }
    });
}

function delMyDoing( obj , doingId )
{
    replyDelete( obj , doingId ); 
}

function sudoDelDoing( obj , doingId , ownerId )
{
    replyDelete( obj , doingId , null , ownerId ); 
}

delMyRpDoing = function( el , doingId , rid )
{
    replyDelete( el , doingId , rid );
}

XN.APP.status.getVideoScale = function( url )
{
    if ( /tudou/i.test( url ) )
    {
        return [400,300];
    }
    else if ( /youtube/i.test( url ) )
    {
        return [425,355];
    }
    else if ( /youku/i.test( url ) )
    {
        return [480,400];
    }
    else if ( /sina/i.test( url ) )
    {
        return [480,370];
    }
    else if ( /qq/i.test( url ) )
    {
        return [456,362];
    }
    else if ( /mofile/i.test( url ) )
    {
        return [480,395];
    }
    else if ( /ku6/i.test( url ) )
    {
        return [460,390];
    }
    else if ( /openv/i.test( url ) )
    {
        return [500,460];
    }
}

function playStatusVideo( sid , url , el )
{
    url = decodeURIComponent( url );
    var scale = XN.APP.status.getVideoScale( url );
    var html = XN.Template.flash({width:scale[0],height:scale[1],filename:url});
    
    var p = el.parentNode;

    if ( !$( 'media' + sid ) || ( p.id && p.id == 'currentStatus' ) || ( p && /currentStatus/.test( p.className ) ) )
    {
        XN.DO.alert(
        {
            title : '状态',
            message : '<center style="padding:10px">' + html + '</center>',
            width : scale[ 0 ] + 80,
            button : '关闭',
            callBack : function()
            {
                this.body.setContent('');
            },
            noHeader : true
        });
    }
    else
    {
        if ( /^\S*$/.test( $( 'media' + sid ).innerHTML ) )
        {
            $( 'media' + sid ).innerHTML = '<div class="feedmediabox">' + html + '</div>';
            if ( el ) $( el ).addClass( 'expand' );
        }
        else
        {
            $( 'media' + sid ).innerHTML = '';
            if ( el ) el.delClass( 'expand' );
        }
    }
}

function playStatusAudio( sid , url , el )
{
    var html;
    
    if( /mp3$/i.test( url ) )
    {
		html = XN.Template.flashPlayer({filename:url});
	}
    else
    {
		html = XN.Template.mediaPlayer({filename:url});
	}
    
    var p = el.parentNode;

    if ( !$( 'media' + sid ) || ( p.id && p.id == 'currentStatus' ) || ( p && /currentStatus/.test( p.className ) ) )
    {
        XN.DO.alert(
        {
            title : '状态',
            message : '<center style="padding:10px">' + html + '</center>',
            width : 500,
            button : '关闭',
            callBack : function()
            {
                this.body.setContent('');
            },
            noHeader : true
        });
    }
    else
    {
        if ( /^\S*$/.test( $( 'media' + sid ).innerHTML ) )
        {
            $( 'media' + sid ).innerHTML = '<div class="feedmediabox">' + html + '</div>';
            if ( el ) $( el ).addClass( 'expand' );
        }
        else
        {
            $( 'media' + sid ).innerHTML = '';
            if ( el ) el.delClass( 'expand' );
        }
    }
}

function $CursorPosition( textBox )
{       
        var start = 0, end = 0;
        if ( typeof( textBox.selectionStart ) == 'number' )
        {
            start = textBox.selectionStart;
            end = textBox.selectionEnd;
        }
        else if ( document.selection )
        {
            var range = document.selection.createRange();
            if ( range.parentElement() == textBox )
            {
                var range_all = document.body.createTextRange();
                range_all.moveToElementText( textBox );
                for ( start=0; range_all.compareEndPoints( 'StartToStart' , range ) < 0; start++ )
                    range_all.moveStart( 'character', 1 );
                for ( var i = 0; i <= start; i ++ )
                {
                    if ( textBox.value.charAt(i) == '\n' )
                        start++;
                }
                
                var range_all = document.body.createTextRange();
                range_all.moveToElementText(textBox);
                
                for ( end = 0; range_all.compareEndPoints('StartToEnd', range) < 0; end ++ )
                    range_all.moveStart('character', 1);
                
                for ( var i = 0; i <= end; i ++ )
                {
                    if (textBox.value.charAt(i) == '\n')
                        end ++;
                }
            }
        }
        
        return {"start": start, "end": end, "item": [start, end]};
}


XN.DOM.readyDo(function()
{
    if( !$( 'pageStatus' ) || !XN.BROWSER.IE ) return;
    document.body.style.zoom = 1.1;
    document.body.style.zoom = '';
});

