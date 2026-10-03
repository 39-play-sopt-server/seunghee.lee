package org.sopt;

public class Main {
    public static void main(String[] args) {
        // Main은 각 계층을 생성하고 연결하는 진입점 역할만 담당
        PostView view = new PostView();
        PostController controller = new PostController(view);
        controller.run();
    }
}
