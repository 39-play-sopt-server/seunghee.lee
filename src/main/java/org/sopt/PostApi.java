package org.sopt;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Posts", description = "게시글 API")
public interface PostApi {
    @Operation(summary = "게시글 작성")
    @PostMapping
    ResponseEntity<ApiResponse<PostResponse>> create(@RequestBody PostRequest request);

    @Operation(summary = "게시글 목록 조회", description = "category를 전달하면 해당 카테고리만 조회합니다.")
    @GetMapping
    ResponseEntity<ApiResponse<List<PostResponse>>> findAll(
            @RequestParam(required = false) Category category
    );

    @Operation(summary = "카테고리 목록 조회")
    @GetMapping("/categories")
    ResponseEntity<ApiResponse<List<Category>>> findCategories();

    @Operation(summary = "게시글 상세 조회", description = "상세 조회 시 조회수가 증가합니다.")
    @GetMapping("/{id}")
    ResponseEntity<ApiResponse<PostResponse>> findById(@PathVariable Long id);

    @Operation(summary = "게시글 전체 수정")
    @PutMapping("/{id}")
    ResponseEntity<ApiResponse<PostResponse>> update(
            @PathVariable Long id,
            @RequestBody PostRequest request
    );

    @Operation(summary = "게시글 카테고리 수정")
    @PatchMapping("/{id}/category")
    ResponseEntity<ApiResponse<PostResponse>> updateCategory(
            @PathVariable Long id,
            @RequestParam Category category
    );

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id);
}
