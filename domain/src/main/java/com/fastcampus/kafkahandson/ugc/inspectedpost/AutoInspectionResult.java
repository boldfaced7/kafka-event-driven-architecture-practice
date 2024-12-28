package com.fastcampus.kafkahandson.ugc.inspectedpost;

public record AutoInspectionResult(
        String status,
        String[] tags
) {
   public boolean isGood() {
       return status.equals("GOOD");
   }
}
