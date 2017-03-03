package fr.robot.twitterClient.service.common;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class BusinessUtil {
	public static final String HASH = "#";

	public static String isMatchFound(String text, List<String> tags){
		String matchedTag = null;
		
		String patternString = "\\b(" + StringUtils.join(tags, "|") + ")\\b";
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(text);

		while (matcher.find()) {
			matchedTag = matcher.group(1);
		}
		return matchedTag;
	}
}
