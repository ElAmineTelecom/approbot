package fr.robot.twitterClient.config;

public class GlobalSettings {

	// == #database connection
	public static final  String DRIVER_CLASS_NAME="org.postgresql.Driver";
	public static final  String URL="jdbc:postgres://ec2-54-247-177-33.eu-west-1.compute.amazonaws.com:5432/d7s5p3gjm7vnkg";
	public static final  String	USERNAME = "mqdnqmorglmhkb";
	public static final  String	PASSWORD = "3c8643ea02e01a14c4fd3217957880e05eacba67e7e7131880ddc8abc77f3e63";

	public static final String ENTITY_PACKAGES_TO_SCAN = "fr.robot.twitterClient.dataaccess.entity";
}
