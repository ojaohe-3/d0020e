package Data;

/**
 * This represents all possible study periods during a year.
 * @author <b>UNKNOWN</b>
 *
 */
public enum LP {

	ONE,
	TWO,
	THREE,
	FOUR,
	ERROR;
	
	public static LP getByString(String str) {
		if(str.equals("1")) {
			return LP.ONE;
		} else if(str.equals("2")) {
			return LP.TWO;
		} else if(str.equals("3")) {
			return LP.THREE;
		} else if(str.equals("4")) {
			return LP.FOUR;
		}
		return LP.ERROR;
		
	}
}
