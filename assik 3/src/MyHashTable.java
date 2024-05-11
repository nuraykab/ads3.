public class MyHashTable<K, V> {
    public HashNode<TestingClass.MyTestingClass, TestingClass.Student>[] getChainArray() {
        return new HashNode[0];
    }

    // Inner static class to represent each node in the hash table
    public static class HashNode<K, V> {
        private K key;
        private V value;
        public HashNode<K, V> next;

        public HashNode(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "{" + key + "=" + value + "}";
        }
    }

    private HashNode<K, V>[] table;
    private int size;
    private int capacity;

    public MyHashTable() {
        this(11);
    }

    public MyHashTable(int capacity) {
        this.capacity = capacity;
        this.table = new HashNode[capacity];
    }

    // Hash function to compute the index of a key
    private int hash(K key) {
        return Math.abs(key.hashCode() % capacity);
    }

    // Method to add a key-value pair to the hash table
    public void put(K key, V value) {
        int index = hash(key);
        HashNode<K, V> newNode = new HashNode<>(key, value);
        if (table[index] == null) {
            table[index] = newNode;
        } else {
            HashNode<K, V> current = table[index];
            while (current.next != null) {
                if (current.key.equals(key)) {
                    current.value = value; // Update value if key already exists
                    return;
                }
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    // Method to retrieve the value associated with a key from the hash table
    public V get(K key) {
        int index = hash(key);
        HashNode<K, V> current = table[index];
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    // Method to remove a key-value pair from the hash table
    public V remove(K key) {
        int index = hash(key);
        HashNode<K, V> prev = null;
        HashNode<K, V> current = table[index];
        while (current != null) {
            if (current.key.equals(key)) {
                if (prev == null) {
                    table[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return current.value;
            }
            prev = current;
            current = current.next;
        }
        return null;
    }

    // Method to check if the hash table contains a particular value
    public boolean contains(V value) {
        for (HashNode<K, V> node : table) {
            HashNode<K, V> current = node;
            while (current != null) {
                if (current.value.equals(value)) {
                    return true;
                }
                current = current.next;
            }
        }
        return false;
    }

    // Method to retrieve the key associated with a particular value
    public K getKey(V value) {
        for (HashNode<K, V> node : table) {
            HashNode<K, V> current = node;
            while (current != null) {
                if (current.value.equals(value)) {
                    return current.key;
                }
                current = current.next;
            }
        }
        return null;
    }

    public HashNode<K, V>[] getTable() {
        return table;
    }
}
