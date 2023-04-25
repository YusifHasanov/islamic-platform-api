package com.msys.esm.Core.DTO.Response;

import java.util.List;

public record PaginitionVideosResponse(List<VideoResponse> videoResponses, int resultForCurrentPage, int totalVideo, int currentPage, int totalPageCount) {
}
