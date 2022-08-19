package dao;

public class User {
	private String loginId;
	private String password;
	private String name;
	private int type;
	private String loginTime;
	private String typeStr;

	public User( String loginId, String password, String name, int type, String loginTime ) {
		this.loginId = loginId;
		this.password = password;
		this.name = name;
		this.type = type;
		this.loginTime = loginTime;
	}

	/**
	 * パスワード無しコンストラクタ
	 * @param loginId　ログインID
	 * @param name 名前
	 * @param type 権限 1.管理者 2.一般 3.ビューワー
	 * @param loginTime
	 */
	public User( String loginId, String name, int type, String loginTime ) {
		this.loginId = loginId;
		this.name = name;
		this.type = type;
		this.loginTime = loginTime;
	}

	public User( String loginId, String name, int type, String loginTime, String strType ) {
		this.loginId = loginId;
		this.name = name;
		this.type = type;
		this.loginTime = loginTime;
		this.typeStr = strType;
	}

	/**
	 * loginIdを取得します。
	 * @return loginId
	 */
	public String getLoginId() {
	    return loginId;
	}
	/**
	 * loginIdを設定します。
	 * @param loginId loginId
	 */
	public void setLoginId(String loginId) {
	    this.loginId = loginId;
	}
	/**
	 * passwordを取得します。
	 * @return password
	 */
	public String getPassword() {
	    return password;
	}
	/**
	 * passwordを設定します。
	 * @param password password
	 */
	public void setPassword(String password) {
	    this.password = password;
	}
	/**
	 * nameを取得します。
	 * @return name
	 */
	public String getName() {
	    return name;
	}
	/**
	 * nameを設定します。
	 * @param name name
	 */
	public void setName(String name) {
	    this.name = name;
	}
	/**
	 * typeを取得します。
	 * @return type
	 */
	public int getType() {
	    return type;
	}
	/**
	 * typeを設定します。
	 * @param type type
	 */
	public void setType(int type) {
	    this.type = type;
	}
	/**
	 * loginTimeを取得します。
	 * @return loginTime
	 */
	public String getLoginTime() {
	    return loginTime;
	}
	/**
	 * loginTimeを設定します。
	 * @param loginTime loginTime
	 */
	public void setLoginTime(String loginTime) {
	    this.loginTime = loginTime;
	}
	public void setStrType( String typeStr ) {
		this.typeStr = typeStr;
	}

	public String getStrType() {
		return this.typeStr;
	}
}
