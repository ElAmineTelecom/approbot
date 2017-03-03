package fr.robot.twitterClient.config;

public class GlobalSettings {

	// == #database connection
	public static final  String DRIVER_CLASS_NAME="org.postgresql.Driver";
	public static final  String URL="jdbc:postgresql://localhost:5432/robot";
	public static final  String	USERNAME = "postgres";
	public static final  String	PASSWORD = "postgres";
	
	public static final String ENTITY_PACKAGES_TO_SCAN = "fr.robot.twitterClient.dataaccess.entity";
}
