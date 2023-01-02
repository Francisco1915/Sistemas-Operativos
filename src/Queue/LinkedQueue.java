package Queue;

public class LinkedQueue<T>{

    private LinearNode<T> front;
    private LinearNode<T> rear;
    private int count;

    public LinkedQueue() {
        this.front = null;
        this.rear = null;
        this.count = 0;
    }

    public void enqueue(T element) {
        
        LinearNode<T> newNode = new LinearNode(element);
        
        if (this.count == 0) {
            
            this.front = newNode;
            this.rear = newNode;
            count++;
        } else {
            
            this.rear.setNext(newNode);
            this.rear = newNode;
            count++;
        }
    }

    public T dequeue() throws EmptyCollectionException {
        
        if (this.isEmpty()) {
            throw new EmptyCollectionException("Queue is empty");
        }
        
        LinearNode<T> removeNode = this.front;
        
        if (this.count == 1) {
            this.front = null;
            this.rear = null;
            this.count--;
            return removeNode.getElement();
        } 
        
        this.front = removeNode.getNext();
        this.count--;
        return removeNode.getElement();
    }

    public T first() throws EmptyCollectionException {    
        if (this.isEmpty()) {
            throw new EmptyCollectionException("Queue is empty");
        }
        return this.front.getElement();
    }

    public boolean isEmpty() {  
        return count == 0;
    }

    public int size() {      
        return count;
    }   

    public String toString() {
        String result = "";
        try {
            result = "A Queue tem " + this.size() + " elementos sendo o primeiro: " + this.first() + ".";
        } catch (EmptyCollectionException ex) {
            System.out.println(ex);
        }
        return result;
    }
}
