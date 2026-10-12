package stage.ifm.ebankbot.agents;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;
@Service
public class EbankAIAgent {
    private ChatClient chatClient;

    public EbankAIAgent(ChatClient.Builder chatClient,
                        ChatMemory chatMemory,
                        ToolCallbackProvider toolCallbackProvider) {
        this.chatClient =
                chatClient
                        .defaultSystem("""
                        Vous êtes un assistant qui se charge de répondre aux questions
                        de l'utilisateur à propos des clients et des comptes bancaires, en fonction du contexte
                        Si aucun contexte n'est fourni, répond avec JE NE SAIS PAS
                        """)
                        .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                        .defaultTools(toolCallbackProvider)
                        .build();
    }
    public String chatGroq(String query){
        return chatClient.prompt(query)
                .options(OpenAiChatOptions.builder().extraBody(Map.of("include_reasoning", false)))
                .advisors(advisor -> advisor.param(ChatMemory.CONVERSATION_ID, "7"))
                .call().content();
    }
    public String chatOpenai(String query){
        return chatClient.prompt(query)
                .advisors(advisor -> advisor.param(
                        ChatMemory.CONVERSATION_ID, "conversation-1"
                ))
                .call().content();
    }
    public Flux<String> chatStreamQroq(String query){
        return chatClient.prompt(query)
                .options(OpenAiChatOptions.builder().extraBody(Map.of("include_reasoning", false)))
                .advisors(advisor -> advisor.param(ChatMemory.CONVERSATION_ID, "7"))
                .stream().content();
    }
    public Flux<String> chatStreamOpenai(String query){
        return chatClient.prompt(query)
                .stream().content();
    }

}
