package fr.robot.twitterClient.twitter4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import twitter4j.DirectMessage;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserStreamListener;
import twitter4j.conf.ConfigurationBuilder;

@Configuration
@ConditionalOnClass({ TwitterFactory.class, Twitter.class })
@EnableConfigurationProperties(Twitter4jProperties.class)
public class Twitter4jAutoConfiguration {

    @Autowired private Twitter4jProperties properties;
    
    @Bean
    @ConditionalOnMissingBean
    public TwitterFactory twitterFactory(){
        if (this.properties.getOauth().getConsumerKey() == null
            || this.properties.getOauth().getConsumerSecret() == null
            || this.properties.getOauth().getAccessToken() == null
            || this.properties.getOauth().getAccessTokenSecret() == null)

        {
            String msg = "Twitter4j properties not configured properly." +
                " Please check twitter4j.* properties settings in configuration file.";
            throw new RuntimeException(msg);

        }
        
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(properties.getDebug())
            .setOAuthConsumerKey(properties.getOauth().getConsumerKey())
            .setOAuthConsumerSecret(properties.getOauth().getConsumerSecret())
            .setOAuthAccessToken(properties.getOauth().getAccessToken())
            .setOAuthAccessTokenSecret(properties.getOauth().getAccessTokenSecret());

        TwitterFactory tf = new TwitterFactory(cb.build());
        return tf;

    }
    

    @Bean
    @ConditionalOnMissingBean
    public Twitter twitter(TwitterFactory twitterFactory){
        return twitterFactory.getInstance();

    }
    
    
    @Bean
    @ConditionalOnMissingBean
    public TwitterStreamFactory twitterStreamFactory(){
        if (this.properties.getOauth().getConsumerKey() == null
            || this.properties.getOauth().getConsumerSecret() == null
            || this.properties.getOauth().getAccessToken() == null
            || this.properties.getOauth().getAccessTokenSecret() == null)

        {
            String msg = "Twitter4j properties not configured properly." +
                " Please check twitter4j.* properties settings in configuration file.";
            throw new RuntimeException(msg);

        }
        
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(properties.getDebug())
            .setOAuthConsumerKey(properties.getOauth().getConsumerKey())
            .setOAuthConsumerSecret(properties.getOauth().getConsumerSecret())
            .setOAuthAccessToken(properties.getOauth().getAccessToken())
            .setOAuthAccessTokenSecret(properties.getOauth().getAccessTokenSecret());

        TwitterStreamFactory tsf = new TwitterStreamFactory(cb.build());
        return tsf;

    }
     
    @Bean
    @ConditionalOnMissingBean
    public TwitterStream twitterStream(TwitterStreamFactory twitterStreamFactory){
        return twitterStreamFactory.getInstance();
    }
}
