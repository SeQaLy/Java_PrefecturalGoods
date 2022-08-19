package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Useful {
	public static int zeroDelete( String str ) {
		int result = 0;
		// 先頭の0の次に来る数字をグループ化
		Pattern pattern = Pattern.compile( "^0+([0-9]+.*)" );
		Matcher matcher = pattern.matcher( str );
		if ( matcher.matches() ) {
			// 先頭が0の場合はグループを取り出す 例) 01,02,09
			result = Integer.parseInt( matcher.group( 1 ) );
		} else {
			// 先頭が0以外の場合はそのまま返す 例) 10,11,20
			result = Integer.parseInt( str );
		}
		return result;
	}

	public static String typeToAuth( int type ) {
		String authority = "";
		switch( type ){
			case 1:
				authority = "管理者";
				break;
			case 2:
				authority = "一般";
				break;
			case 3:
				authority = "ビューワー";
				break;
		}
		return authority;
	}
}
