package edu.miu.demo.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class VectorStoreRepository {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public void cleanUpStore(){
        Set<String> keys = redisTemplate.keys("redis*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
            System.out.println("Deleted " + keys.size() + " vector keys from Redis.");
        } else {
            System.out.println("No vector keys found to delete.");
        }
    }
//    @Autowired
//    VectorStore vectorStore;
//
//    public void add(List<String> products) {
//        List<Document> documents = IntStream.range(0, products.size())
//                .mapToObj(i -> new Document("house-" + i, products.get(i), Map.of()))
//                .toList();
//        vectorStore.add(documents);
//    }
//    public List<String> findClosestMatches(String query,int numberOfMatches) {
//
//        SearchRequest request = SearchRequest.builder()
//                .query(query)
//                .topK(numberOfMatches)
//                .build();
//        List<Document> results = vectorStore.similaritySearch(request);
//        return results.stream()
//                .map((Document doc) -> doc.getText())
//                .toList();
//    }
//
//    public String findClosestMatch(String query,int number) {
//        List<String> matches = findClosestMatches(query, number);
//        return String.join("\n", matches);
//    }
}
