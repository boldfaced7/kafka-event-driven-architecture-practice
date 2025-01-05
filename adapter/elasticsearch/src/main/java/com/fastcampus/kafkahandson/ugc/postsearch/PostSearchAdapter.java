package com.fastcampus.kafkahandson.ugc.postsearch;

import com.fastcampus.kafkahandson.ugc.PostSearchPort;
import com.fastcampus.kafkahandson.ugc.inspectedpost.InspectedPost;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PostSearchAdapter implements PostSearchPort {

    private final PostSearchRepository postSearchRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public void index(InspectedPost post) {
        postSearchRepository.save(PostDocumentConverter.toDocument(post));
    }

    @Override
    public void delete(Long postId) {
        postSearchRepository.deleteById(postId);
    }

    @Override
    public List<Long> searchPostIdsByKeyword(String keyword, int pageNumber, int pageSize) {
        return buildQuery(keyword, pageNumber, pageSize)
                .map(this::executeSearch)
                .map(this::extractPostIds)
                .orElseGet(List::of);
    }

    private Optional<Query> buildQuery(String keyword, int pageNumber, int pageSize) {
        if (isInvalidInput(keyword, pageNumber, pageSize)) {
            return Optional.empty();
        }
        Criteria criteria = new Criteria("title").matches(keyword)
                .or(new Criteria("content")).matches(keyword)
                .or(new Criteria("categoryName")).is(keyword)
                .or(new Criteria("tags")).is(keyword);

        Query query = new CriteriaQuery(criteria)
                .setPageable(PageRequest.of(pageNumber, pageSize));

        return Optional.of(query);
    }

    private boolean isInvalidInput(String keyword, int pageNumber, int pageSize) {
        return keyword == null || pageNumber < 0 || pageSize <= 0;
    }

    private SearchHits<PostDocument> executeSearch(Query query) {
        return elasticsearchOperations.search(query, PostDocument.class);
    }

    private List<Long> extractPostIds(SearchHits<PostDocument> searchHits) {
        return searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .map(PostDocument::getId)
                .toList();
    }
}
