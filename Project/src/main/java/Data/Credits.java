package Data;

public enum Credits {

	SEVEN,

	SEVENHALF,
	FIFTHEEN,
	THIRTY,
	ONEHUNDREDEIGHTY,

	TWOHUNDREDTEN,
	THREEHUNDRED,
	ERROR;
	
	
	public static Credits getByString(String str) {
		if(str.equals("SEVEN")) {
			return Credits.SEVENHALF;
		} else if(str.equals("FIFTHEEN")) {
			return Credits.FIFTHEEN;
		} else if(str.equals("THIRTY")) {
			return Credits.THIRTY;
		} else if(str.equals("ONEHUNDREDEIGHTY")) {
			return Credits.ONEHUNDREDEIGHTY;
		} else if(str.contentEquals("TWOHUNDREDTEN")) {
			return Credits.TWOHUNDREDTEN;
		} else if(str.equals("THREEHUNDRED")) {
			return Credits.THREEHUNDRED;
		}
		return Credits.ERROR;
		
	}

	
	public static Credits getByInt(String number) {
		if(number.equals("7,5")) {
			return Credits.SEVENHALF;
		} else if(number.equals("15")) {
			return Credits.FIFTHEEN;
		} else if(number.equals("30")) {
			return Credits.THIRTY;
		} else if(number.equals("180")) {
			return Credits.ONEHUNDREDEIGHTY;
		} else if(number.contentEquals("210")) {
			return Credits.TWOHUNDREDTEN;
		} else if(number.equals("300")) {
			return Credits.THREEHUNDRED;
		}
		return Credits.ERROR;
		
	}
}

