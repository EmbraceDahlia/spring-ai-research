package edu.miu.demo;

import edu.miu.demo.repo.VectorStoreRepository;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.rag.Query;
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
	@Autowired
	VectorStoreRepository vectorStoreRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		vectorStoreRepository.cleanUpStore();
		TikaDocumentReader documentReader = new TikaDocumentReader(documentResource);
		TextSplitter textSplitter = new TokenTextSplitter();
		vectorStore.add(
				textSplitter.apply(
						documentReader.get()));
	}
}
