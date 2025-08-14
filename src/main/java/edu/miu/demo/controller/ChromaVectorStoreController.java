package edu.miu.demo.controller;

//import org.springframework.ai.chroma.vectorstore.ChromaApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vectors")
public class ChromaVectorStoreController {

//    @Autowired
//    private ChromaApi chromaApi;
//
//    // For example, configure tenant and database as constants or from application.yml
//    private static final String TENANT = "SpringAiTenant";
//    private static final String DATABASE = "SpringAiDatabase";
//
//    @GetMapping("/collection/{name}")
//    public ResponseEntity<?> getCollection(@PathVariable String name) {
//        try {
//            ChromaApi.Collection collection = chromaApi.getCollection(TENANT, DATABASE, name);
//            if (collection == null) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                        .body("Collection '" + name + "' not found.");
//            }
//            return ResponseEntity.ok(collection);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error fetching collection: " + e.getMessage());
//        }
//    }
//    @DeleteMapping("/collection/{name}")
//    public ResponseEntity<?> deleteCollection(@PathVariable String name) {
//        try {
//            chromaApi.deleteCollection(TENANT, DATABASE, name);
//            return ResponseEntity.ok("Collection '" + name + "' deleted successfully.");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error deleting collection: " + e.getMessage());
//        }
//    }


}
