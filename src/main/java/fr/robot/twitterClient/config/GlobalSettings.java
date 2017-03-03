package fr.robot.twitterClient.config;

public class GlobalSettings {

	// == #database connection
	public static final  String DRIVER_CLASS_NAME="org.postgresql.Driver";
	public static final  String URL="jdbc:postgres://ec2-54-235-181-120.compute-1.amazonaws.com:5432/dc8bd7p629f7j4";
	public static final  String	USERNAME = "yhiumcckxaezco";
	public static final  String	PASSWORD = "a970a0c72b9261170f1d8393b728cd572648a3877a03781bd9b683a5503cd706";

	public static final String ENTITY_PACKAGES_TO_SCAN = "fr.robot.twitterClient.dataaccess.entity";
}
