package server.config;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.methods.MethodsClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class SlackAppConfig {

  @Bean
  public App slackApp() {
    AppConfig appConfig = AppConfig.builder().singleTeamBotToken("xoxb-6394234122982-6386489480343-xpYoVph94Ui4MIhH2wHyOfZ6")
        .signingSecret("fbc1682d44647ced056f88e3fc25b986").build();
    App app = new App(appConfig);
    app.command("/hello", (req, ctx) -> ctx.ack("Hi there!"));

    return app;
  }

  @Bean
  public MethodsClient slackClient(App slackApp) {
    return slackApp.client();
  }
}
