import java.io.*;
import java.util.StringTokenizer;

class Node {
    Node parent, left, right;
    int value, size = 1, answer;
    int leftLength, rightLength;
    boolean reverse, full;

    Node (int value) {
        leftLength = rightLength = answer = this.value = value;
        full = value == 1;
    }

    public void update () {
        size = 1;

        leftLength = rightLength = answer = value;
        full = value == 1;

        if (left!=null) left.propagate();
        if (right!=null) right.propagate();

        if (left == null && right == null) return;

        if (left == null) {
            size += right.size;
            answer = Math.max(answer, right.answer);
            if (value == 1) answer = Math.max(answer, 1 + right.leftLength);

            full &= right.full;

            if (value == 0) {
                rightLength = right.rightLength;
            } else {
                leftLength = 1 + right.leftLength;
                if (right.full) {
                    rightLength = right.rightLength + 1;
                } else {
                    rightLength = right.rightLength;
                }
            }

        } else if (right == null) {
            size += left.size;
            answer = Math.max(answer, left.answer);
            if (value == 1) answer = Math.max(answer, left.rightLength + 1);

            full &= left.full;
            if (value == 0) {
                leftLength = left.leftLength;
            } else {
                rightLength = 1 + left.rightLength;

                if (left.full) {
                    leftLength = left.leftLength + 1;
                } else {
                    leftLength = left.leftLength;
                }
            }
        } else {
            size += left.size + right.size;
            answer = Math.max(left.answer, right.answer);

            if (value == 0) {
                rightLength = right.rightLength;
                leftLength = left.leftLength;
            } else {
                answer = Math.max(answer, left.rightLength + right.leftLength + 1);
                full = left.full && right.full;

                if (right.full) {
                    rightLength = left.rightLength + right.size + 1;
                } else {
                    rightLength = right.rightLength;
                }

                if (left.full) {
                    leftLength = left.size + right.leftLength + 1;
                } else {
                    leftLength = left.leftLength;
                }
            }
        }

    }

    public void propagate () {
        if (reverse) {
            Node temp = left;
            left = right;
            right = temp;

            int poo = rightLength;
            rightLength = leftLength;
            leftLength = poo;

            if (left != null) left.reverse ^= true;
            if (right != null) right.reverse ^= true;
        }
        reverse = false;
    }
}

class Splay {
    public Node root;
    private Node[] nodes = new Node[100001];
    private int[] arr;
    Splay (int[] arr) {
        this.arr = arr;

        for (int i=0;i<arr.length;i++) {
            nodes[i] = new Node(arr[i]);
        }

        for (int i=arr.length-1;i>0;i--) {
            nodes[i-1].right = nodes[i];
            nodes[i].parent = nodes[i-1];

            nodes[i-1].update();
        }

        root = nodes[0];
    }

    public void propagate(Node a) {
        if (a.reverse) {
            Node temp = a.left;
            a.left = a.right;
            a.right = temp;

            if (a.left != null) {
                a.left.reverse ^= true;
            }

            if (a.right != null) {
                a.right.reverse ^= true;
            }

            int poo = a.leftLength;
            a.leftLength = a.rightLength;
            a.rightLength = poo;
        }

        a.reverse = false;
    }

    public void rotate (Node a) {
        Node p = a.parent, temp = null;

        propagate(p);
        propagate(a);

        if (p.left == a) {
            temp = p.left = a.right;
            a.right = p;
        } else {
            temp = p.right = a.left;
            a.left = p;
        }

        a.parent = p.parent;
        p.parent = a;

        if (temp != null) {
            temp.parent = p;
        }

        if (a.parent != null) {
            if (a.parent.left == p) {
                a.parent.left = a;
            } else {
                a.parent.right = a;
            }
        } else {
            root = a;
        }

        p.update();
        a.update();
    }

    public void splay (Node a) {
        while(a.parent != null) {
            if(a.parent.parent != null) rotate(((a.parent.parent.left == a.parent) && (a.parent.left == a)) ? a.parent : a);
            rotate(a);
        }
    }

    public void reverseRange (int left, int right) {
        if (left == right) return;

        if (left == 1 && right == arr.length) {
            root.reverse ^= true;
            root.propagate();

        } else if (left == 1) {
            findKth(right + 1, root);

            root.propagate();
            root.left.reverse ^= true;
            root.left.propagate();

            root.update();
        } else if (right == arr.length) {
            findKth(left - 1, root);

            root.propagate();
            root.right.reverse ^= true;
            root.right.propagate();

            root.update();
        } else {
            findKth(left-1, root);
            Node origin = root;
            root.propagate();

            root = origin.right;
            root.parent = null;

            findKth(right-left + 2, root);

            origin.right = root;
            Node answer = root.left;
            answer.reverse ^= true;

            propagate(answer);
            root.update();
            origin.update();

            root.parent = origin;
            root = origin;
        }
    }

    public void findKth (int order, Node now) {
        propagate(now);

        if (now.left == null && now.right == null) {
            splay(now);
            return;
        }

        if (now.left == null) {
            if (order == 1) {
                splay(now);
                return;
            }

            findKth(order-1, now.right);
        } else if (now.right == null) {
            if (now.size == order) {
                splay(now);
            } else {
                findKth(order, now.left);
            }
        } else {
            if (now.left.size + 1 == order) {
                splay(now);
                return;
            }

            if (now.left.size < order) {
                findKth(order - now.left.size - 1, now.right);
            } else {
                findKth(order, now.left);
            }
        }
    }

    public int query (int left, int right) {
        if (left == 1 && right == arr.length) {
            return root.answer;
        } else if (left == 1) {
            findKth(right+1, root);
            return root.left.answer;
        } else if (right == arr.length) {
            findKth(left-1, root);
            return root.right.answer;
        } else {
            findKth(left-1, root);
            Node origin = root;

            root = origin.right;
            root.parent = null;

            findKth(right-left + 2, root);

            Node answer = root.left;
            origin.right = root;
            root.parent = origin;

            root = origin;

            return answer.answer;
        }
    }

    public void inOrder (Node node) {
        if (node.left != null) inOrder(node.left);
        System.out.print(node.value+" ");
        if (node.right != null) inOrder(node.right);
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] arr= new int[N];

        for (int i = 0;i<arr.length;i++) arr[i] = Integer.parseInt(st.nextToken());

        Splay tree = new Splay(arr);
        int M = Integer.parseInt(br.readLine());

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        while(M-->0) {
            st = new StringTokenizer(br.readLine());
            int m = Integer.parseInt(st.nextToken());
            int left = Integer.parseInt(st.nextToken()), right = Integer.parseInt(st.nextToken());

            if (m == 1) {
                tree.reverseRange(left, right);
            } else {
                bw.write(tree.query(left, right)+"\n");
            }

        }
        bw.flush();

    }
}