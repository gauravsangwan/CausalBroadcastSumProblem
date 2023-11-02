public class CustomMessage {
    private int counter;
    private int sum;
    private String type;
    // 3 type read write and ack

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getSum() {
        return sum;
    }
    public void setSum(int sum){
        this.sum = sum;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCounter() {
        return counter;
    }

    public String getType() {
        return type;
    }
}
