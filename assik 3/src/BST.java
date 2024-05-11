import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class BST<K extends Comparable<K>, V> implements Iterable<K> {
    private Node root;
    private int size;

    private class Node {
        private K key;
        private V value;
        private Node left, right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public void put(K key, V value) {
        root = put(root, key, value);
    }

    // Recursive helper method to add key-value pair to the BST
    private Node put(Node node, K key, V value) {
        if (node == null) {
            size++;
            return new Node(key, value);
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = put(node.left, key, value);
        } else if (cmp > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.value = value;
        }
        return node;
    }

    public V get(K key) {
        Node node = find(root, key);
        return node == null ? null : node.value;
    }

    // Helper method to find the node with the given key in the BST
    private Node find(Node node, K key) {
        while (node != null) {
            int cmp = key.compareTo(node.key);
            if (cmp < 0) {
                node = node.left;
            } else if (cmp > 0) {
                node = node.right;
            } else {
                return node;
            }
        }
        return null;
    }

    public void delete(K key) {
        root = delete(root, key);
    }

    // Helper method to delete a key-value pair from the BST
    private Node delete(Node node, K key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = delete(node.left, key);
        } else if (cmp > 0) {
            node.right = delete(node.right, key);
        } else {
            if (node.right == null) return node.left;
            if (node.left == null) return node.right;
            Node temp = node;
            node = min(temp.right);
            node.right = deleteMin(temp.right);
            node.left = temp.left;
        }
        return node;
    }

    private Node deleteMin(Node node) {
        if (node.left == null) return node.right;
        node.left = deleteMin(node.left);
        return node;
    }

    private Node min(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTIterator();
    }

    // Inner class for BST iterator
    private class BSTIterator implements Iterator<K> {
        private ArrayList<K> keys = new ArrayList<>();
        private int index = 0;

        private BSTIterator() {
            inorderTraversal(root);
        }

        private void inorderTraversal(Node node) {
            if (node == null) return;
            inorderTraversal(node.left);
            keys.add(node.key);
            inorderTraversal(node.right);
        }

        @Override
        public boolean hasNext() {
            return index < keys.size();
        }

        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in the tree.");
            }
            return keys.get(index++);
        }
    }
}
