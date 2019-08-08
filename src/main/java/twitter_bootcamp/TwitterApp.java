package twitter_bootcamp;


import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter_bootcamp.config.AppConfiguration;


public class TwitterApp extends Application<AppConfiguration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterApp.class);

    public static void main(final String[] args) throws Exception {
        if (args.length != 2) {
            LOGGER.error("Incorrect number of arguments. The first argument must be 'server'. The second argument" +
                    " needs to be the name of the config yaml file, eg: 'app_config.yml'");
        }
        else {
            new TwitterApp().run(args);
        }
    }

    @Override
    public void run(final AppConfiguration configuration, final Environment environment) {
        ServiceComponent component = DaggerServiceComponent.builder()
                .resourceModule(new ResourceModule())
                .twitterModule(new TwitterModule(configuration))
                .build();

        LOGGER.info("Registering REST resources..");
        environment.jersey().register(component.getTwitterResources());
    }
}