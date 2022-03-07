class ToDoListItem {
    private String task;
    private boolean done;

    public ToDoListItem(String todo) {
        this.task = todo;
        this.done = false;
    }

    public String getTask() {
        return this.task;
    }

    public boolean isDone() {
        return this.done;
    }

    public void markDone() {
        this.done = true;
    }

    public void markNotDone() {
        this.done = false;
    }

    public String toString() {
        String stringRep = this.task;
        if (this.done) {
            stringRep = stringRep + " [done]";
        } else {
            stringRep = stringRep + " [not done]";
        }

        return stringRep;
    }
}