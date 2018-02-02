public class LinkedListDeque<T> {
    private class HelperNode{
        public HelperNode next;
        public T item;
        public HelperNode previous;

        public HelperNode(HelperNode p, T i, HelperNode n){
            previous = p;
            item = i;
            next = n;
        }
    }

    private static void main(String[] args) {
        LinkedListDeque Links = new LinkedListDeque();
        Links.addFirst(1);
        Links.addFirst(2);
        Links.addFirst(3);

        Links.addLast(4);
        Links.removeFirst();
    }

    private HelperNode sentinel;
    private int size;

    public LinkedListDeque(){
        sentinel = new HelperNode(sentinel, null, sentinel);
        size = 0;
    }

    public void addFirst(T item){
    /*Adds an item of type T to the front of the deque.*/
        if (this.isEmpty()){
            size += 1;

            HelperNode newnode = new HelperNode(sentinel, item, sentinel);
            sentinel.next = newnode;
            sentinel.previous = newnode;
        }
        else {
            size += 1;

            HelperNode newnode = new HelperNode(sentinel, item, sentinel.next);
            sentinel.next = newnode;
            newnode.next.previous = newnode;
        }
    }

    public void addLast(T item){
    /*Adds an item of type T to the back of the deque. */
        if (this.isEmpty()){
            size += 1;

            HelperNode newnode = new HelperNode(sentinel, item, sentinel);
            sentinel.next = newnode;
            sentinel.previous = newnode;
        } else {
            size += 1;

            HelperNode newnode = new HelperNode(sentinel.previous, item, sentinel);
            sentinel.next = sentinel.next;
            sentinel.previous = newnode;
            }
        }

    public boolean isEmpty(){
    /*Returns true if deque is empty, false otherwise.*/
        if (size == 0){
            return true;
        } return false;
    }

    public int size(){
    /* Returns the number of items in the deque.*/
        return size;
    }

    public void printDeque(){
    /*Prints the items in the deque from first to last, separated by a space.*/
        if (size == 0){
            return;
        } else {
            HelperNode dummynode = new HelperNode(sentinel.next, null, sentinel.next);
            for (int i = 0; i <= size; i++){
                System.out.print(sentinel.next.item + " ");
                sentinel.next = sentinel.next.next;
            }
            sentinel.next = dummynode.previous;
        }
    }

    public T removeFirst(){
    /*Removes and returns the item at the front of the deque. If no such item exists, returns null.*/
        if (size == 0){
            return null;
        } else {
            T removed = sentinel.next.item;
            sentinel.next = sentinel.next.next;
            sentinel.next.previous = sentinel;
            size -= 1;

            return removed;
        }
    }

    public T removeLast(){
    /*Removes and returns the item at the back of the deque. If no such item exists, returns null.*/
        if (size == 0){
            return null;
        }
        else {
            T removed = sentinel.previous.item;
            sentinel.previous = sentinel.previous.previous;
            size -= 1;

            return removed;

        }
    }

    public T get(int index){
    /*Gets the item at the given index, where 0 is the
     front, 1 is the next item, and so forth. If no such item
     exists, returns null. Must not alter the deque!*/
        if (size == 0){
            return null;
        }
        else {
            HelperNode dummynode = new HelperNode(sentinel.next, null, sentinel.next);
            for (int i = 0; i <= index; i++) {
                sentinel.next = sentinel.next.next;
            }
            T returned = sentinel.next.item;
            sentinel.next = dummynode.previous;

            return returned;
        }
    }

    public T getRecursive(int index){
        if (index == 0){
            return sentinel.next.item;
        }
        else {
            sentinel.next = sentinel.next.next;
            return getRecursive(index - 1);
        }
    }
}
