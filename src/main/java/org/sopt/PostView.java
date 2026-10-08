package org.sopt;

import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

public class PostView {
    private final Scanner scanner = new Scanner(System.in);

    // View 출력
    public void printMenu() {
        System.out.println("\n=== 게시판 ===");
        System.out.println("1. 게시글 작성");
        System.out.println("2. 게시글 목록 조회");
        System.out.println("3. 게시글 단건 조회");
        System.out.println("4. 게시글 수정");
        System.out.println("5. 게시글 삭제");
        System.out.println("6. 종료");
    }

    public int readCommand() {
        System.out.print("선택: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public String readTitle() {
        System.out.print("제목: ");
        return scanner.nextLine();
    }

    public String readContent() {
        System.out.print("내용: ");
        return scanner.nextLine();
    }

    public String readAuthor() {
        System.out.print("작성자: ");
        return scanner.nextLine();
    }

    public Category readCategory() {
        System.out.println("카테고리: 1. 공지  2. 자유  3. 질문  4. 정보");
        System.out.print("선택: ");
        return switch (Integer.parseInt(scanner.nextLine())) {
            case 1 -> Category.NOTICE;
            case 2 -> Category.FREE;
            case 3 -> Category.QUESTION;
            case 4 -> Category.INFORMATION;
            default -> throw new InvalidPostException("올바른 카테고리를 선택해주세요.");
        };
    }

    public Long readPostId(String message) {
        System.out.print(message);
        return Long.parseLong(scanner.nextLine());
    }

    public void printPost(Post post) {
        System.out.println("\n=== 게시글 ===");
        System.out.println("제목: " + post.getTitle());
        System.out.println("내용: " + post.getContent());
        System.out.println("작성자: " + post.getAuthor());
        System.out.println("카테고리: " + post.getCategory().getDisplayName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.println("작성일: " + post.getCreatedAt().format(formatter));
        System.out.println("수정일: " + post.getUpdatedAt().format(formatter));
    }

    public void printPosts(List<Post> posts) {
        System.out.println("\n=== 게시글 목록 ===");
        if (posts.isEmpty()) {
            System.out.println("게시글이 없습니다.");
            return;
        }
        for (int i = 0; i < posts.size(); i++) {
            Post post = posts.get(i);
            System.out.println(post.getId() + ". [" + post.getCategory().getDisplayName() + "] " + post.getTitle());
        }
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
