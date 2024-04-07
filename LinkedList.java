public class LinkedList<T> {
    private Node<T> head;
    private Node<T> tail; // New reference for the tail of the list

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev; // New previous reference for bidirectional navigation

        Node(T item) {
            this.item = item;
            this.next = null;
            this.prev = null;
        }
    }

    // Removes the node following the given node
    public void removeAfter(Node<T> node) {
        if (node == null || node.next == null) {
            return;
        }
        Node<T> toRemove = node.next;
        node.next = toRemove.next;
        if (toRemove.next != null) {
            toRemove.next.prev = node;
        } else { // Adjusting tail if needed
            tail = node;
        }
    }

    // Creates a copy of the list
    public LinkedList<T> copy() {
        LinkedList<T> copyList = new LinkedList<>();
        Node<T> current = this.head;
        while (current != null) {
            copyList.addLast(current.item);
            current = current.next;
        }
        return copyList;
    }

    // Removes all nodes with the item equal to key
    public void removeDuplicate(T key) {
        Node<T> current = this.head;
        while (current != null) {
            Node<T> nextNode = current.next;
            if (current.item.equals(key)) {
                if (current.prev != null) {
                    current.prev.next = current.next;
                } else {
                    head = current.next;
                }
                if (current.next != null) {
                    current.next.prev = current.prev;
                } else {
                    tail = current.prev;
                }
            }
            current = nextNode;
        }
    }

    // Returns the maximum item, assumes Comparable
    public T max() {
        if (head == null) return null; // Adjusted to return null for empty list
        T maxVal = head.item;
        Node<T> current = head;
        while (current != null) {
            if (((Comparable<T>)current.item).compareTo(maxVal) > 0) {
                maxVal = current.item;
            }
            current = current.next;
        }
        return maxVal;
    }

    // Utility method to add elements to the tail of the list
    public void addLast(T item) {
        Node<T> newNode = new Node<>(item);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }
    
}
