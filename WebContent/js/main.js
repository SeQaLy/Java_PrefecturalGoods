$( function() {
	$( "area" ).on( "click", function() {
		let prefecturalName = $( this ).attr( "name" );
		$.ajax( {
			type : "post",
			url : "SpecialGoods",
			data : {
				prefecturalName : prefecturalName
			}

		} ).done( function( result ) {
			// モーダル表示( 非表示状態解除 )
			$( ".food-list" ).empty();
			$( ".modal-container" ).removeClass( "deactive" );
			$( ".modal-title" ).text( prefecturalName + "の特産品" );
			for ( var i = 0; i < result["list"].length; i++ ) {
				$( ".food-list" ).append( "<li class='food-list-chil'>" + result["list"][i]["specialFoodName"] + "</li>" );
			}

		} ).fail( function() {
			alert( "failed" );
		} )
	} );

	// 子画面閉じるボタン処理
	$( ".modal-close" ).on( "click", function() {
		$( ".modal-container" ).addClass( "deactive" )
	} );

	// 子画面領域外の判定
	$( document ).on( "click", function( e ) {
		if( !$( e.target ).closest( ".modal-content" ).length ) {
			console.log( "add class" );
			$( ".modal-container" ).addClass( "deactive" );
		}
	} );
} )