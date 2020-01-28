package Data;

/**
 * This class contains a simple topic with a title, nothing more.
 * @author Robin
 *
 */
public class Topic {
	private String title;
	
	public Topic(String title) {
		this.title = title;
	}
	
	/**
	 * @return The actual string representation of this topic.
	 */
	@Override
	public String toString() {
		return this.title;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o.getClass() != this.getClass()) {
			return false;
		}
		
		return ((Topic)o).title == this.title;
	}
	
	/**
	 * Labels used for the topic class. These include the title and the topic label itself.
	 */
	public static enum TopicLabels {
		TOPIC("Topic"), TITLE("title");
		private String name;
		private TopicLabels(String name) {
			this.name = name;
		}
		@Override
		public String toString() {
			return this.name;
		}
	}
}
