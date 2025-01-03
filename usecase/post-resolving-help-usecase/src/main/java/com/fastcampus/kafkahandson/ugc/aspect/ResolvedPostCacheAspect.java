package com.fastcampus.kafkahandson.ugc.aspect;

import com.fastcampus.kafkahandson.ugc.ResolvedPostCachePort;
import com.fastcampus.kafkahandson.ugc.post.model.ResolvedPost;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Aspect
@Component
@RequiredArgsConstructor
public class ResolvedPostCacheAspect {

    private final ResolvedPostCachePort resolvedPostCachePort;

    @Around("@annotation(cacheableResolvedPost)")
    public Object handleCacheableResolvedPost(
            ProceedingJoinPoint joinPoint,
            CacheableResolvedPost cacheableResolvedPost
    ) {
        Long postId = Long.parseLong(cacheableResolvedPost.key());

        return resolvedPostCachePort.get(postId)
                .orElseGet(() -> proceed(joinPoint));
    }

    @Around("@annotation(cacheableResolvedPostList)")
    public Object handleCacheableResolvedPostList(
            ProceedingJoinPoint joinPoint,
            CacheableResolvedPostList cacheableResolvedPostList
    ) {
        String keys = cacheableResolvedPostList.keys();
        List<Long> postIds = getPostIds(joinPoint, keys);

        Map<Long, Object> cached = resolvedPostCachePort.getAll(postIds)
                .stream()
                .collect(Collectors.toMap(
                        ResolvedPost::getId,
                        Function.identity())
                );

        List<Long> missingIds = postIds.stream()
                .filter(Objects::nonNull)
                .filter(id -> !cached.containsKey(id))
                .toList();

        List<?> proceeded = proceed(joinPoint, missingIds);

        return postIds.stream()
                .map(id -> cached.getOrDefault(
                        id, proceeded.get(missingIds.indexOf(id))))
                .toList();
    }


    @SuppressWarnings("unchecked")
    private List<Long> getPostIds(ProceedingJoinPoint joinPoint, String targetName) {
        String[] parameterNames = ((MethodSignature) joinPoint.getSignature())
                .getParameterNames();
        Object[] args = joinPoint.getArgs();

        int index = Arrays.asList(parameterNames).indexOf(targetName);

        return Optional.of(index)
                .filter(i -> !i.equals(-1))
                .map(i -> (List<Long>) args[i])
                .orElseGet(List::of);
    }

    private static ResolvedPost proceed(ProceedingJoinPoint joinPoint) {
        try {
            return (ResolvedPost) joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
    private static List<?> proceed(ProceedingJoinPoint joinPoint, List<Long> missingIds) {
        try {
            return (List<?>) joinPoint.proceed(new Object[]{missingIds});
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
