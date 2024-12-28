package com.fastcampus.kafkahandson.ugc;

import com.fastcampus.kafkahandson.ugc.inspectedpost.AutoInspectionResult;
import com.fastcampus.kafkahandson.ugc.inspectedpost.InspectedPost;
import com.fastcampus.kafkahandson.ugc.post.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostInspectService implements PostInspectUsecase {

    private final MetadataPort metadataPort;
    private final PostAutoInspectPort postAutoInspectPort;

    @Override
    public Optional<InspectedPost> inspect(Post post) {
        String categoryName = metadataPort.getCategoryNameByCategoryId(post.getCategoryId());
        AutoInspectionResult result = postAutoInspectPort.inspect(post, categoryName);

        return Optional.of(result)
                .filter(AutoInspectionResult::isGood)
                .map(goodResult -> InspectedPost.generate(
                        post,
                        categoryName,
                        goodResult.tags()
                ));
    }
}
