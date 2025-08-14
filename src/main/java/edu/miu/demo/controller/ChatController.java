package edu.miu.demo.controller;

import edu.miu.demo.dto.ChatRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
public class ChatController {
    private final ChatClient chatClient;
    @Autowired
    VectorStore vectorStore;
    @Autowired
    private CallAdvisor myLoggingAdvisor;

    public ChatController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/chat")
    public String search(@RequestBody ChatRequest message){
        Advisor retrievalAugmentationAdvisor = RetrievalAugmentationAdvisor.builder()
                .documentRetriever(VectorStoreDocumentRetriever.builder()
                        .similarityThreshold(0.5)
                        .vectorStore(vectorStore)
                        .build())
                .queryAugmenter(ContextualQueryAugmenter.builder()
                        .allowEmptyContext(true)
                        .build())
                .build();

        return chatClient.prompt()
                .advisors(retrievalAugmentationAdvisor,myLoggingAdvisor)
//                .advisors(retrievalAugmentationAdvisor)
                .user(message.getQuestion())
                .call()
                .content();

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(path = "/chat/stream", produces = MediaType.TEXT_PLAIN_VALUE)
    public Flux<String> streamChat(@RequestBody ChatRequest chatRequest) {
//        VectorStoreDocumentRetriever retriever = VectorStoreDocumentRetriever.builder()
//                .vectorStore(vectorStore)
////                .similarityThreshold(0.5)
//                .topK(5)
//                .build();
//
//// ✅ Correct query object
//        Query query = new Query("what are the benefits and scope of Global Accidental Death & Dismemberment?");
//
//        List<Document> docs = retriever.retrieve(query);

        Advisor retrievalAugmentationAdvisor = RetrievalAugmentationAdvisor.builder()
                .documentRetriever(VectorStoreDocumentRetriever.builder()
                        .similarityThreshold(0.5)
                        .vectorStore(vectorStore)
                        .build())
                .queryAugmenter(ContextualQueryAugmenter.builder()
                        .allowEmptyContext(false)
                        .build())
                .build();

        return chatClient.prompt()
                .advisors(retrievalAugmentationAdvisor, myLoggingAdvisor)
                .user(chatRequest.getQuestion())
                .stream()
                .content();
    }
}
