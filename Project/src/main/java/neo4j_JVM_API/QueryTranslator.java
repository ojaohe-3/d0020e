package neo4j_JVM_API;

/**
 * Simple class for generating a query on-the-go.
 * Dunno if we need this or not, but I'm too lazy to write specific query code.
 * @author Rogin
 *
 */
class QueryTranslator {

	
	static String getCreateStatement(String variable, String label, String[] options, String[] values) {
		if (options.length != values.length) {
			throw new IllegalArgumentException("The options and values have different length");
		}
		String query =  "CREATE(" + variable +":" + label + "{";
		for (int i = 0; i < options.length-1; i++) {
			query = query.concat(options[i] + ": \"" + values[i] + "\",");
		}
		query = query.concat(options[options.length-1] + ": \"" + values[options.length-1] + "\" })");
		return query;
	}

	@Deprecated
	String MATCH() {
		return "";
	}
}
