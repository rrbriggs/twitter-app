package twitter_bootcamp;


import org.hibernate.validator.constraints.NotEmpty;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.TwitterResponse;

@Path("/api/1.0/twitter")
@Produces(MediaType.APPLICATION_JSON)
public class TwitterResource {
    private final int TWEET_LENGTH = 280;

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterApp.class);

    @GET
    @Path("/timeline")
    public Response getTimeline() {
        try {
            LOGGER.info("Getting Timeline.. ");

            Twitter twitterInstance = getTwitterResponse();

            return Response
                    .status(Response.Status.OK)
                    .entity(twitterInstance.getHomeTimeline())
                    .build();
        }
        catch (IOException | TwitterException e) {
            e.printStackTrace();
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("There was an error getting your timeline. Please try again in a few minutes.")
                    .build();
        }
    }

    public Twitter getTwitterResponse() throws IOException {
        GetTwitterInstance getTwitterInstance = new GetTwitterInstance();
        Twitter twitterInstance = getTwitterInstance.getTwitter();
        return twitterInstance;
    }

    @POST
    @Path("/tweet")
    public Response postTweet(@FormParam("message") @NotEmpty String message) {

        if (message.length() <= TWEET_LENGTH) {
            try {
                Status status = sendTweet(message);

                LOGGER.info("Tweeting: {}", status.getText());

                return Response
                        .status(Response.Status.CREATED)
                        .entity(status)
                        .build();
            }
            catch (IOException | TwitterException e) {
                e.printStackTrace();

                return Response
                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("The problem here is with us, not you. If the problem persists, " +
                                "submit an issue (https://github.com/rrbriggs/BrigBot/issues)")
                        .build();
            }
        }
        else {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Tweet was too long. Limit tweet to " + TWEET_LENGTH + " characters.")
                    .build();
        }
    }

    public Status sendTweet(String message) throws IOException, TwitterException {
        GetTwitterInstance twitterInstance = new GetTwitterInstance();
        Status status = twitterInstance.getTwitter().updateStatus(message);
        return status;
    }
}