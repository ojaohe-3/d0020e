package Data;

public enum Credits {
	SEVEN,
	FIFTHEEN,
	THIRTY,
	ERROR;
	
	public static Credits getByString(String str) {
		if(str.equals("SEVEN")) {
			return Credits.SEVEN;
		} else if(str.equals("FIFTHEEN")) {
			return Credits.FIFTHEEN;
		} else if(str.equals("THIRTY")) {
			return Credits.THIRTY;
		} 
		return Credits.ERROR;
		
	}
}
