package com.odangsa.hashtag.service;

import com.clarifai.channel.ClarifaiChannel;
import com.clarifai.credentials.ClarifaiCallCredentials;
import com.clarifai.grpc.api.*;
import com.clarifai.grpc.api.status.StatusCode;
import com.google.protobuf.ByteString;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class RecommendService {

    private final Map<String,String> categoryMap;
    private final GptService gptService;

    static final String PAT = "0042a41f1b11481589a11b8a5790c6a1";
    static final String USER_ID = "clarifai";
    static final String APP_ID = "main";
    static final String MODEL_ID = "general-image-recognition-vit";
    static final String MODEL_VERSION_ID = "1bf8b41a7c154eaca6203643ff6a75b6";

    public List<String> recommendCategory(MultipartFile picture, String place){
        List<String> categories = new ArrayList<>();
        try {
            categories.addAll(picture2category(picture));
            return categories;
        }catch (Exception e){
            log.error("error : "+e.getMessage());
            return null;
        }
    }

    private List<String> picture2category(MultipartFile picture) throws Exception {
        File file = new File("image.jpg");
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(picture.getBytes());
        fos.close();
        return keyword2category(picture2keyword(file));
    }

    private Map<String, Float> picture2keyword(File file) throws IOException{
        V2Grpc.V2BlockingStub stub = V2Grpc.newBlockingStub(ClarifaiChannel.INSTANCE.getGrpcChannel())
                .withCallCredentials(new ClarifaiCallCredentials(PAT));

        MultiOutputResponse postModelOutputsResponse = stub.postModelOutputs(
                PostModelOutputsRequest.newBuilder()
                        .setUserAppId(UserAppIDSet.newBuilder().setUserId(USER_ID).setAppId(APP_ID))
                        .setModelId(MODEL_ID)
                        .setVersionId(MODEL_VERSION_ID)  // This is optional. Defaults to the latest model version.
                        .addInputs(
                                Input.newBuilder().setData(
                                        Data.newBuilder().setImage(
                                                Image.newBuilder()
                                                        .setBase64(ByteString.copyFrom(Files.readAllBytes(
                                                                file.toPath()
                                                        )))
                                        )
                                )
                        )
                        .build()
        );

        if (postModelOutputsResponse.getStatus().getCode() != StatusCode.SUCCESS) {
              throw new RuntimeException("Post model outputs failed, status: " + postModelOutputsResponse.getStatus());
        }

        // Since we have one input, one output will exist here.
        Output output = postModelOutputsResponse.getOutputs(0);

        Map<String, Float> results = new HashMap<>();
        for (Concept concept : output.getData().getConceptsList()) {
            results.put(concept.getName(), concept.getValue());
        }

        return results;
    }

    private List<String> keyword2category(Map<String, Float> results) throws Exception{
        List<String> keywords = results.keySet().stream().map(h->categoryMap.get(h)).collect(Collectors.toList());

        List<String> categories = gptService.chat(keywords);

        return categories;
    }
}
