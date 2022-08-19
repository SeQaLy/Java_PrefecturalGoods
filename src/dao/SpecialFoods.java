package dao;

public class SpecialFoods {
	private int specialFoodNumber;
	private String specialFoodName;
	private String prefecturalID;
	private String prefecturalName;
	private String timestamp;
	private String updateUser;
	/**
	 * コンストラクタ
	 * @param specialFoodNumber 特産品番号
	 * @param specialFoodName　特産品名
	 * @param prefecturalID 都道府県ID
	 */
	public SpecialFoods( String specialFoodName, String prefecturalID ) {
		this.specialFoodName = specialFoodName;
		this.prefecturalID = prefecturalID;
	}

	public SpecialFoods( int specialFoodNumber, String specialFoodName, String prefecturalID ) {
		this.specialFoodNumber = specialFoodNumber;
		this.specialFoodName = specialFoodName;
		this.prefecturalID = prefecturalID;
	}

	public SpecialFoods( int specialFoodNumber, String specialFoodName, String prefecturalID, String prefecturalName ) {
		this.specialFoodNumber = specialFoodNumber;
		this.specialFoodName = specialFoodName;
		this.prefecturalID = prefecturalID;
		this.prefecturalName = prefecturalName;
	}

	public SpecialFoods( int specialFoodNumber, String specialFoodName, String prefecturalID, String prefecturalName, String timestamp ) {
		this.specialFoodNumber = specialFoodNumber;
		this.specialFoodName = specialFoodName;
		this.prefecturalID = prefecturalID;
		this.prefecturalName = prefecturalName;
		this.timestamp = timestamp;
	}

	public int getSpecialFoodNumber() {
		return specialFoodNumber;
	}

	public void setSpecialFoodNumber(int specialFoodNumber) {
		this.specialFoodNumber = specialFoodNumber;
	}

	public String getSpecialFoodName() {
		return specialFoodName;
	}

	public void setSpecialFoodName(String specialFoodName) {
		this.specialFoodName = specialFoodName;
	}

	public String getPrefecturalID() {
		return prefecturalID;
	}

	public void setPrefecturalID(String prefecturalID) {
		this.prefecturalID = prefecturalID;
	}

	public String getPrefecturalName() {
		return prefecturalName;
	}

	public void setPrefecturalName( String prefecturalName) {
		this.prefecturalName = prefecturalName;
	}

	public String getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp( String timestamp ) {
		this.timestamp = timestamp;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser( String updateUser ) {
		this.updateUser = updateUser;
	}


	public void showInfo() {
		System.out.println( this.specialFoodNumber );
		System.out.println( this.prefecturalID );
		System.out.println( this.specialFoodName );
	}



}
