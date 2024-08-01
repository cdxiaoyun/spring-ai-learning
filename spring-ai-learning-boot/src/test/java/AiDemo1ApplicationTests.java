import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.StreamingChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.image.ImageClient;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

@Slf4j
@SpringBootTest
class AiDemo1ApplicationTests {

    @Resource
    ChatClient chatClient;

    @Test
    void chatTest() {
        String message = "你好，介绍一下你自己";
        Prompt prompt = new Prompt(new UserMessage(message));
        String content = chatClient.call(prompt).getResult().getOutput().getContent();
        log.info(content);
    }

    @Resource
    StreamingChatClient streamingChatClient;

    @Test
    void chatStreamTest() {
        String message = "你好，介绍一下你自己";
        Prompt prompt = new Prompt(new UserMessage(message));

        streamingChatClient.stream(prompt)
                .flatMap(chatResponse -> Flux.fromIterable(chatResponse.getResults()))
                .map(content -> content.getOutput().getContent())
                .doOnNext(System.out::println)
                .last()
                .block();
    }

    @Resource
    ImageClient imageClient;

    @Test
    void testToImageTest() {
        String message = "生成一幅夕阳下程序员看海的图片";
        ImagePrompt prompt = new ImagePrompt(message);
        String url = imageClient.call(prompt).getResult().getOutput().getUrl();
        log.info(url);
    }
}
