package org.sopt;

public class PostController {
    private final PostService service;
    private final PostView view;

    public PostController(PostService service, PostView view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        while (true) {
            try {
                view.printMenu();
                int command = view.readCommand();
                switch (command) {
                    case 1 -> createPost();
                    case 2 -> view.printPosts(service.findAll());
                    case 3 -> readPost();
                    case 4 -> updatePost();
                    case 5 -> deletePost();
                    case 6 -> { view.printMessage("프로그램을 종료합니다."); return; }
                    default -> view.printMessage("잘못된 입력입니다.");
                }
            } catch (NumberFormatException exception) {
                view.printMessage("숫자 형식의 입력을 사용해주세요.");
            } catch (PostNotFoundException | InvalidPostException exception) {
                view.printMessage(exception.getMessage());
            }
        }
    }

    private void createPost() {
        service.create(view.readTitle(), view.readContent(), view.readAuthor(), view.readCategory());
        view.printMessage("게시글이 작성되었습니다.");
    }

    private void readPost() {
        Long id = view.readPostId("조회할 게시글 번호: ");
        view.printPost(service.findById(id));
    }

    private void updatePost() {
        Long id = view.readPostId("수정할 게시글 번호: ");
        service.update(id, view.readTitle(), view.readContent(), view.readCategory());
        view.printMessage("게시글이 수정되었습니다.");
    }

    private void deletePost() {
        Long id = view.readPostId("삭제할 게시글 번호: ");
        service.delete(id);
        view.printMessage("게시글이 삭제되었습니다.");
    }
}
