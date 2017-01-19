import java.util.ArrayList;
import java.util.List;

public class RunAVLTest {
    public static void main(String[] args)
    {
        AVLTree tree = new AVLTree();

        tree.put(62);
        tree.put(44);
        tree.put(78);
        tree.put(17);
        tree.put(50);
        tree.put(88);
        tree.put(48);
        tree.put(54);

        tree.put(52);
        tree.remove(62);
        tree.put(49);
        tree.remove(88);

        print(tree);
    }

    // Methods for printing the tree and debugging

    /** Prints textual representation of tree structure (for debug purpose only). */
    public static void print(AVLTree tree) {
        System.out.println("AVL Tree: Nodes are printed as (key, height)");
        dumpRecurse(tree.root(), 0);
    }

    private static void dumpRecurse(AVLNode p, int depth) {
        String indent = (depth == 0 ? "" : String.format("%" + (6*depth) + "s", ""));
        if (p.isExternal())
        {
            if (!p.sibling().isExternal())
                System.out.println(indent + "(-, 0)");
        }
        else {
            System.out.println(indent + "(" + p.getElement() +"," + p.getHeight()+")");
            dumpRecurse(p.getLeft(), depth+1);
            dumpRecurse(p.getRight(), depth+1);
        }
    }

    /** Ensure that current tree structure is valid AVL. */
    public static boolean sanityCheck(AVLTree tree) {
        for (AVLNode p : positions(tree)) {
            if (p.isInternal()) {
                if (p.getElement() == -1)
                    System.out.println("VIOLATION: Internal node has null entry");
                else if (p.getHeight() != 1 + Math.max(p.getLeft().getHeight(), p.getRight().getHeight())) {
                    System.out.println("VIOLATION: AVL unbalanced node with key " + p.getElement());
                    print(tree);
                    return false;
                }
            }
        }
        return true;
    }

    /** Returns an iterable collection of the positions of the tree. */
    private static Iterable<AVLNode> positions(AVLTree tree) { return preorder(tree); }

    /** Returns an iterable collection of positions of the tree, reported in preorder. */
    private static Iterable<AVLNode> preorder(AVLTree tree) {
        List<AVLNode> snapshot = new ArrayList<>();
        if (!tree.isEmpty())
            preorderSubtree(tree.root(), snapshot);   // fill the snapshot recursively
        return snapshot;
    }

    /**
     * Adds positions of the subtree rooted at Position p to the given
     * snapshot using a preorder traversal
     */
    private static void preorderSubtree(AVLNode p, List<AVLNode> snapshot) {
        snapshot.add(p);                  // for preorder, we add position p before exploring subtrees
        for (AVLNode c : children(p))
            preorderSubtree(c, snapshot);
    }

    /** Returns an iterable collection of the Positions representing p's children. */
    private static Iterable<AVLNode> children(AVLNode p) {
        List<AVLNode> snapshot = new ArrayList<>(2);    // max capacity of 2
        if (p.getLeft() != null)
            snapshot.add(p.getLeft());
        if (p.getRight() != null)
            snapshot.add(p.getRight());
        return snapshot;
    }
}
