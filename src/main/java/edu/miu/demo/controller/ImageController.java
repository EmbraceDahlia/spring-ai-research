package edu.miu.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.miu.demo.dto.ChatRequest;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.ai.document.Document;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImageOptionsBuilder;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.slf4j.Logger;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/images")
public class ImageController {
    private final static Logger LOG = LoggerFactory.getLogger(ImageController.class);

    private final ChatClient chatClient;
    //    private final ImageModel imageModel;
    private List<Media> images;
    private List<Media> dynamicImages = new ArrayList<>();

    public ImageController(ChatClient.Builder chatClientBuilder){
        this.chatClient = chatClientBuilder.defaultAdvisors(new SimpleLoggerAdvisor()).build();
        this.images = List.of(
                Media.builder().id("fruits").mimeType(MimeTypeUtils.IMAGE_PNG).data(new ClassPathResource("images/fruits.png")).build(),
                Media.builder().id("fruits-2").mimeType(MimeTypeUtils.IMAGE_PNG).data(new ClassPathResource("images/fruits-2.png")).build(),
                Media.builder().id("fruits-3").mimeType(MimeTypeUtils.IMAGE_PNG).data(new ClassPathResource("images/fruits-3.png")).build(),
                Media.builder().id("fruits-4").mimeType(MimeTypeUtils.IMAGE_PNG).data(new ClassPathResource("images/fruits-4.png")).build(),
                Media.builder().id("fruits-5").mimeType(MimeTypeUtils.IMAGE_PNG).data(new ClassPathResource("images/fruits-5.png")).build(),
                Media.builder().id("animals").mimeType(MimeTypeUtils.IMAGE_PNG).data(new ClassPathResource("images/animals.png")).build(),
                Media.builder().id("animals-2").mimeType(MimeTypeUtils.IMAGE_PNG).data(new ClassPathResource("images/animals-2.png")).build(),
                Media.builder().id("animals-3").mimeType(MimeTypeUtils.IMAGE_PNG).data(new ClassPathResource("images/animals-3.png")).build(),
                Media.builder().id("animals-4").mimeType(MimeTypeUtils.IMAGE_PNG).data(new ClassPathResource("images/animals-4.png")).build(),
                Media.builder().id("animals-5").mimeType(MimeTypeUtils.IMAGE_PNG).data(new ClassPathResource("images/animals-5.png")).build()
        );
    }

    @GetMapping(value = "/find/{object}", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody byte[] analyze(@PathVariable String object){
        String msg = """
                Which picture contains %s.
                Return only a single picture.
                Return only the number that indicates its position in the media list.
                """.formatted(object);

//        String msg = """
//            Which picture contains %s.
//            Return only the position number as an integer (e.g., 1, 2, 3).
//            Do not return words, brackets, or any other text.
//            """.formatted(object);

        LOG.info(msg);

        UserMessage um = UserMessage.builder().text(msg).media(images).build();

        String content = this.chatClient.prompt(new Prompt(um))
                .call()
                .content();
        LOG.info("The result is ==============>>>>>>>>>> "+content);
        assert content != null;
        return images.get(Integer.parseInt(content)-1).getDataAsByteArray();
    }

    @GetMapping("/describe")
    String[] describe(){
        UserMessage um = UserMessage.builder().text("Explain what do you see on each image.")
                .media(List.copyOf(Stream.concat(images.stream(),dynamicImages.stream()).toList())).build();
        return this.chatClient.prompt(new Prompt(um))
                .call()
                .entity(String[].class);
    }
    @PostMapping("/question")
    public String[] question(@RequestBody ChatRequest request) throws IOException {
        List<Media> mediaList = new ArrayList<>();

        if (request.getImage() != null) {
            Path imagePath = Paths.get("uploads", request.getImage());
            byte[] imageBytes = Files.readAllBytes(imagePath);
            String mimeTypeStr = Files.probeContentType(imagePath);
            if (mimeTypeStr == null) {
                mimeTypeStr = "application/octet-stream"; // or "image/png" as fallback
            }

            Media imageMedia = Media.builder()
                    .id(UUID.randomUUID().toString())
                    .mimeType(MimeType.valueOf(mimeTypeStr))
                    .data(new UrlResource(imagePath.toUri()))
                    .name(request.getImage())
                    .build();

            mediaList.add(imageMedia);
        }

        UserMessage userMessage = UserMessage.builder()
                .text(request.getQuestion())
                .media(mediaList)
                .build();

        return this.chatClient.prompt(new Prompt(userMessage))
                .call()
                .entity(String[].class);
    }

}
