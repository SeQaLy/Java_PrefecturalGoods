$( function() {
	$( "select" ).on( "change", function() {
		const newPermission = $( this ).children( "option:checked" ).val();
		const userId = $( this ).parent().parent().children( ".table-loginid" ).text()

		$.ajax( {
			type : "post",
			url : "UserChangePermission",
			data : {
				userId : userId,
				newPermission : newPermission
			}
		} )
	} )
} )