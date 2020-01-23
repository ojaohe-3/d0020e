package Data;

public enum Credits {
	SEVEN,
	FIFTHEEN,
	THIRTY,
	ERROR;
	
	public static Credits getByString(String str) {
		if(str == "SEVEN") {
			return Credits.SEVEN;
		} else if(str == "FIFTHEEN") {
			return Credits.FIFTHEEN;
		} else if(str == "THIRTY") {
			return Credits.THIRTY;
		} 
		return Credits.ERROR;
		
	}
}
