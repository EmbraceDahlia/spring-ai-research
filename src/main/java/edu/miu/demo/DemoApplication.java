package edu.miu.demo;

import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
	@Value("classpath:/MIU Insurance Summary of Benefits 25-26.pdf")
	private Resource documentResource;
	@Autowired
	VectorStore vectorStore;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

//		List <Document> documents = List.of(
//				new Document("1","Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!", Map.of("meta1", "meta1")),
//				new Document("2","The World is Big and Salvation Lurks Around the Corner",Map.of("meta2", "meta2")),
//				new Document("3","You walk forward facing the past and you turn back toward the future.", Map.of("meta3", "meta3")));
//
//		vectorStore.add(documents);
//		TikaDocumentReader documentReader = new TikaDocumentReader(documentResource);
//		TextSplitter textSplitter = new TokenTextSplitter();
//		vectorStore.add(
//				textSplitter.apply(
//						documentReader.get()));

//		List<Document> results = this.vectorStore.similaritySearch(SearchRequest.builder().query("Spring").topK(5).build());
//        if(results!=null)
//        	results.forEach(System.out::println);
	}
}
