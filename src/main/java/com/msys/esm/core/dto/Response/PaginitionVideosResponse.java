package com.msys.esm.core.dto.Response;

import java.util.List;

public record PaginitionVideosResponse(List<VideoResponse> videoResponses, int resultForCurrentPage, int totalVideo, int currentPage, int totalPageCount) {
}
