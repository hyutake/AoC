package AoC.day8;

public class Node {
        private String id;
        private String left;
        private String right;

        public Node(String id, String left, String right) {
            this.id = id;
            this.left = left;
            this.right = right;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLeft() {
            return left;
        }

        public void setLeft(String left) {
            this.left = left;
        }

        public String getRight() {
            return right;
        }

        public void setRight(String right) {
            this.right = right;
        }

        @Override
        public String toString() {
            return "{\n  id: " + id + "\n  left: " + left + "\n  right: " + right + "\n}";
        }

    }