{
	$( function() {
		// input初期化
		const clearInput = function() {
			$("input").each(function(index, element) {
				element.value = "";
			});
		};
		// フォームを初期化
		const initForm = function() {
			clearInput();
			errorRemove();
			submitDisable = false;
			$( "#submit" ).addClass( "disabled" )
			$( "#submit" ).prop( "disabled", true )
		};


		$(".toggleMenu div").on( "click", function() {
			// 新規登録メニュー押下
			if ($(this).hasClass("toggleRegister") && !$(this).hasClass("check")) {
				var element = "<input type='text' placeholder='名前' name='name' limit=10>";
				$(".input-name-container").append( element );
				$(".toggleLogin").removeClass("check");
				$(this).addClass("check");
				$("#submit").text("新規登録");
				$("#submit").attr("name", "register");

				// input初期化
				initForm();
				// ログインメニュー押下
			} else if ($(this).hasClass("toggleLogin")
					&& !$(this).hasClass("check")) {
				$(".input-name-container").empty();
				$(this).addClass("check");
				$(".toggleRegister").removeClass("check");
				$("#submit").text("ログイン");
				$("#submit").attr("name", "login");
				initForm();
			}
		});

		// 新規登録処理
		$( document ).on( "click", "button[name='register']", function() {
			$.ajax( {
				type : "post",
				url : "UserRegister",
				data : {
					name : $( "input[name='name']" ).val(),
					id: $( "input[name='id']" ).val(),
					password: $( "input[name='password']" ).val(),
				}
			} ).done( function( result ) {
				// エラーがある場合
				if ( result["errorMessage"] != null ) {
					console.log( "th" );
					$( "input[name='id']" ).css( {
						"border-color" : "red"
					} );
					errorRemove();
					$( ".input-name-container" ).prepend( "<p class='error' name='registerError'>" + result["errorMessage"] + "</p>" )
				// 登録完了
				} else {
					errorRemove();
					alert( "登録が完了しました。" );
					// toggleをログインに切り替え
					$(".input-name-container").empty();
					$( ".toggleRegister" ).removeClass( "check" );
					$( "#submit" ).text( "ログイン" );
					$( "#submit" ).attr( "name", "login" );
					$( ".toggleLogin" ).addClass( "check" );
					initForm();
				}
			} )
		} )

		// ログイン処理
		$( document ).on("click", "button[name='login']", function() {
			$.ajax({
				type : "post",
				url : "UserLogin",
				data : {
					id : $("input[name='id']").val(),
					password : $("input[name='password']").val()
				}
			}).done( function( result ) {
				if ( result["isLogin"] ) {
					location.href = "top.jsp";
				} else {
					errorRemove();
					$( ".input-name-container" ).prepend( "<p class='error' name='loginError'>" + result["errorMessage"] + "</p>" )
				}
			});
		});

		// ログイン子画面表示
		$( ".btn-login,.btn-start" ).on("click", function() {

			$.ajax( {
				type : "post",
				url : "Lag"
			} ).done( function() {
				$( ".modal-container" ).removeClass("deactive");
			} );
		});

		// **は１０文字以下で...の**の部分を変換して返す
		const
		parseName = function(name) {
			switch (name) {
			case "name":
				return "名前";
				break;
			case "id":
				return "ID";
				break;
			case "password":
				return "パスワード";
				break;
			}
		};

		const errorRemove = function() {
			$( ".error" ).remove();
			$( "input[name='id'], input[name='password']" ).css( { "border-color" : "black" } );
		};

		const selectErrorRemove = function( errorType ) {
			$( "[name='" +  errorType + "']" ).remove();
			$( "input[name='id'], input[name='password']" ).css( { "border-color" : "black" } );
		};

		const checkLimitOver = function( str, limit ) {
			if ( str.length > limit ) {
				return false;
			}
			return true;
		};

		const checkAllInputted = function() {
			isAllInput = true;
			$( "input" ).each( function( index, element ) {
				if ( $( element ).val().length < 1 ) {
					isAllInput = false;
				}
			} );
			return isAllInput;
		};

		// 全ての要素が超過してない場合 true
		const checkAllInputLimitOver = function() {
			var isOver = true;
			$( "input" ).each( function( index, element ) {
				str = $( element ).val();
				limit = $( element ).attr( "limit" );
				columnName = parseName( $( element).attr( "name" ) );

				if ( !checkLimitOver( str, limit ) ) {
					$( element ).parent().prepend( "<p class='error' name='inputError'>" + columnName + "は" + limit + "文字以内で入力してください。</p>" )
					isOver = false;
				}
			} );

			if( !checkAllInputted() ) {
				isOver = false;
			}
			return isOver;
		}

		// 入力制限チェック
		// 学び appendで追加したものにはイベントが設定されていないので
		// 下記のような書き方をする
		$( document ).on( "input", "input", function() {

			selectErrorRemove( "loginError" );
			selectErrorRemove( "registerError" );
			errorRemove();

			if ( checkAllInputLimitOver() ) {
				$( "#submit" ).prop( "disabled", false );
				$( "#submit" ).removeClass( "disabled" );
			} else {
				$( "#submit" ).prop( "disabled", true );
				$( "#submit" ).addClass( "disabled" );
			}
		});

		// 子画面閉じるボタン処理
		$(".modal-close").on("click", function() {
			$(".modal-container").addClass("deactive");
		});

		// 子画面領域外の判定
		$(document).on("click", function(e) {
			if (!$(e.target).closest(".modal-content").length) {
				$(".modal-container").addClass("deactive");
			}
		});
	});
}