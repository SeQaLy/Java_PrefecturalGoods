$( function() {

	// ###### ソート ######
	// thの最初以外の要素クリック時にソートを実行
	$( "th:not(:first-child)" ).on( "click", function() {
		var sortColumn = $( this ).attr( "name" );
		// 次のソート方法を取得
		var sortType = $( "input[name='nextSortType']" ).val();
		var url = ""
		// 別項目をソートする際に、次のソート順が残っている問題への対処
		// 問題例 idを昇順にした後、特産品をソートすると、降順で特産品がソートされてしまう。
		// 対処 前回ソートした列と違う列をソートした場合は、昇順で表示するように設定
		if ( $( "input[name='currentSortColumn']" ).val() == sortColumn ) {
			url = "SpecialFoodSort?sortMethod=" + sortType + "&sortColumn=" + sortColumn;
		} else {
			url = "SpecialFoodSort?sortMethod=asc" + "&sortColumn=" + sortColumn;
		}
		location.href = url;
	} );

	// ###### ボタンアニメーション #######
	$( ".pages a,.crud-container div, #submit, input[type='submit'], .showall-content" ).on( "mouseover", function() {
		if ( !$( this ).hasClass( "active" ) ) {
			$( this ).stop( true ).animate( {
				"background-color" : "#fffffe",
				"color" : "#3da9fc"
			}, 150 )
		}
	} ).on( "mouseout", function() {
		if ( !$( this ).hasClass( "active" ) ) {
			$( this ).stop( true ).animate( {
				"background-color" : "#3da9fc",
				"color" : "#fffffe"
			}, 150 )
		}
	} );

	//  ###### 追加　編集処理関連 ######
	var identifier = "";
	var originTimestamp = "";
	$( ".crud-container > div" ).on( "click", function() {
		$( ".prefectural-menu" ).children( "option" ).remove(); // 押下する度、都道府県名が増えるのを防止
		// 押下されたボタンのクラス
		var modalType = $( this ).attr( "class" );
		$.ajax( {
			type : "post",
			url : "PrefecturalNameList",
		} ).done( function( result ) {

			$( ".modal-container" ).removeClass( "deactive" );
			// 追加ボタン押下時
			if ( modalType == "add-content" ) {
				// 都道府県選択を解除し、特産品名を空に
				$( "option:selected" ).prop( "selected", false );
				$( "input[name='food-name']" ).val( "" );

				identifier = "add";
				$( ".modal-title" ).text( "新規追加" );
				// 都道府県名プルダウンメニューの選択候補追加
				result["prefecturalList"].forEach( function( prefectural ) {
					var element = "<option value='" + prefectural + "'>" + prefectural + "</option>";
					$( ".prefectural-menu" ).append( element );

				} );
			// 編集ボタン押下時
			} else if ( modalType == "update-content" ) {
				// 1件だけ編集可能
				if ( $( "input:checked" ).length == 1 ) {
					identifier = "update";
					// 編集前の都道府県名
					var originPrefectural = $( "input:checked" ).parent().parent().children( ".table-prefectural" ).text();
					// 編集前の特産品名
					var originFoodName = $( "input:checked" ).parent().parent().children( ".table-food" ).text();
					// 編集対象の特産品番号
					var foodNumber = $( "input:checked" ).parent().parent().children( ".table-id" ).text();
					// タイムスタンプ取得
					$.ajax( {
						type : "post",
						url : "SpecialFoodExclusive",
						data : {
							foodNumber : foodNumber
						}
					} ).done( function( timestamp ) {
						originTimestamp = timestamp["timestamp"];
					} )

					$( ".modal-title" ).text( "編集" );
					$( ".foodnumber-content" ).text( foodNumber );
					result["prefecturalList"].forEach( function( prefectural ) {
						var element = "";
						if ( originPrefectural == prefectural ) {
							element = "<option value='" + prefectural + "' selected>" + prefectural + "</option>";
						} else {
							element = "<option value='" + prefectural + "'>" + prefectural + "</option>";
						}
						$( ".prefectural-menu" ).append( element );
					} );
					$( "input[name='food-name']" ).val( originFoodName );
				// 何も選択されていない場合
				} else if ( $( "input:checked" ).length < 1 ){
					alert( "編集するデータを選択してください。" );
					$( ".modal-container" ).addClass( "deactive" );
				// 複数選択されている場合
				} else {
					alert( "１件だけ選択してください。" )
					$( ".modal-container" ).addClass( "deactive" );
				}
			// 削除ボタン押下時
			} else if ( ".delete-content" ) {
				// モーダルウィンドウは非表示
				$( ".modal-container" ).addClass( "deactive" );
			}
		} );
	} );

	// 検索
	$( ".search-btn" ).on( "click", function() {
		$.ajax( {
			type : "post",
			url : "SpecialFoodsList",
			async : false,
			data : {
				// 検索ボックスの中身を送信
				searchTerm : $( "input[name='search-box']" ).val()
			}
		} ).done( function() {
			// jspで表示処理をしているため、ブラウザ更新処理
			location.reload();
		} );
	} );

	// 削除ボタン
	$( ".delete-content" ).on( "click", function() {
		var deleteNumbers = [];
		// チェックが入っている全ての要素に対して
		$.each( $( "input:checked" ), function( index, val ) {
			// 削除するIDを配列に追加
			deleteNumbers.push( $( val ).attr( "name" ) ); // nameにはidが入っている
		} );
		// １件以上選択されている、かつ、ダイアログでOKが押された場合
		if ( deleteNumbers.length > 0 && confirm( deleteNumbers.length + "件のデータを削除しますか?" ) ) {
			$.ajax( {
				type : "post",
				url : "SpecialFoodDelete",
				data : {
					// JSON形式(実際は文字列)
					deleteIndex : JSON.stringify( deleteNumbers )
				}
			} ).done( function( result ) {
				if ( result["errorMessages"] == null ) {
					alert( deleteNumbers.length + "件のデータを削除しました。" );
					location.reload();
				} else {
					alert( result["errorMessages"][0] );
					location.reload();
				}
			} )
		} else {
			alert( "削除するデータを選択してください。" )
		}
	} );

	// 確定ボタン押下時
	$( "#submit" ).on( "click", function() {
		// 未入力チェック　空白削除
		if ( $( "[name='food-name']" ).val().replace(/\s+/g, "") == "" ) {
			alert( "特産品名を入力してください。" );
		} else {
			// 20文字以下制限チェック
			if ( $( "[name='food-name']" ).val().length <= 20 ) {
				if ( confirm( "この内容でよろしいですか？" ) ) {
					// 識別子は追加の場合 add,編集の場合 update が入る
					if ( identifier == "add" ) {
						$.ajax( {
							type : "post",
							url : "SpecialFoodAdd",
							data : {
								prefecturalName : $( "[name='prefectural']" ).val(),
								foodName : $( "[name='food-name']" ).val(),
								updateUser : $( "[name='userId']" ).val()
							}
						} ).done( function( result ) {
							// エラーがなければアラートを出し、リロード
							if ( result["errorMessages"] == null ) {
								alert( "追加しました。" );
								location.reload();
							} else {
								// サーバーから帰ってきたエラー
								alert( result["errorMessages"][0] );
							}
						} );
					} else if ( identifier == "update" ) { // 更新処理
						console.log( $( "[name='userId']" ).val() );
						$.ajax( {
							type : "post",
							url : "SpecialFoodUpdate",

							data : {
								prefecturalName : $( "[name='prefectural']" ).val(),
								foodName : $( "[name='food-name']" ).val(),
								foodNumber : $( "[name='food-number']" ).text(),
								originTimestamp : originTimestamp,
								updateUser : $( "[name='userId']" ).val()
							}
						} ).done( function( result ) {
							// エラーがなければアラートを出し、リロード
							if ( result["errorMessages"] == null ) {
								alert( "編集しました。" );
								location.reload();
							} else {
								alert( result["errorMessages"][0] );
								location.reload();
							}
						} )
					}
				}
			} else { // 入力文字数が20を超えた場合
				alert( "特産品名は20文字以内で入力してください。" );
			}
		}
	} );



	// 子画面閉じるボタン処理
	$( ".modal-close" ).on( "click", function() {
		$( ".modal-container" ).addClass( "deactive" );
		// 閉じたときに識別子初期化
		identifier = "";
	} );

	// 子画面領域外の判定
	$( document ).on( "click", function( e ) {
		if( !$( e.target ).closest( ".modal-content" ).length ) {
			$( ".modal-container" ).addClass( "deactive" );
			// 閉じたときに識別子初期化
			identifier = "";
			}
	} );

	// 行を押下するとその行を選択済みに
	$( "tr" ).on( "click", function() {
		// 既にチェックされている場合は、外す
		if ( $( this ).children( ".table-checkbox" ).children( "input" ).prop( "checked" ) ) {
			$( this ).children( ".table-checkbox" ).children( "input" ).prop( "checked", false );
		} else {
			$( this ).children( ".table-checkbox" ).children( "input" ).prop( "checked", true );
		}
	} );

	// チェックボックスの挙動修正
	$( "input[type='checkbox']" ).on( "click", function() {
		if( $( this ).prop( "checked" ) ) {
			$( this ).prop( "checked", false );
		} else {
			$( this ).prop( "checked", true );
		}
	} );
});

