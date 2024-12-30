package com.fastcampus.kafkahandson.ugc;

import com.fastcampus.kafkahandson.ugc.inspectedpost.InspectedPost;

public interface InspectedPostMessageProducePort {
    InspectedPost sendCreateMessage(InspectedPost inspectedPost);
    InspectedPost sendUpdateMessage(InspectedPost inspectedPost);
    InspectedPost sendDeleteMessage(InspectedPost inspectedPost);
}
