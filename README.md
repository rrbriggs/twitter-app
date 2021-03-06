# Twitter

This application has the basic functionality of:
1: Sending a tweet
2: Viewing your Timeline
3: Filter your Timeline

## Building and running with Gradle:

Clone the repo with `git clone https://github.com/rrbriggs/twitter-app.git`

*This application requires an app_config.yml file that contains your Twitter tokens and keys.
There an example file called (app_config-example.yml) that lives in the config folder of this project.
Once you have populated this file with your information, make sure to rename the file to `app_config.yml`.
Note: for this file, you must add your key or token after the ":" for example:*

`consumerKey: "YourConsumerKey"`

Open the directory in terminal or navigate to it, then to build the app enter:

`./gradlew build`

To run the app, then enter:
`./gradlew run`

### To send a Tweet:
POST plain text / string to the endpoint /api/1.0/twitter/tweet/message
For example, from terminal: `curl -d "message=Hello World" -X POST http://localhost:8080/api/1.0/twitter/tweet`

### To get your Home Timeline:
GET from the endpoint /api/1.0/twitter/timeline
For example, from terminal: `curl  http://localhost:8080/api/1.0/twitter/timeline`

### To get your User Timeline:
GET from the endpoint(with included param): /api/1.0/twitter/timeline?timeline?userTimeline=user

### To filter from your Timeline:
GET from the endpoint /api/1.0/twitter/timeline/filter with the query param of filterKey
For example, from terminal: `curl -X GET http://localhost:8080/api/1.0/twitter/timeline/filter?filterKey=YourFilterKeyword`

## Running unit tests:
In terminal, at the project root directory run:
`./gradlew clean test`

## Running test coverage:
In terminal at the project root directory run:
`./gradlew clean build jacocoTestReport`



## Note:

Requires the [Twitter4j library](http://twitter4j.org/en/http://twitter4j.org/en/)
