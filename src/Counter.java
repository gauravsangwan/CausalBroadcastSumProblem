import io.jbotsim.core.Message;
import io.jbotsim.core.Color;
import io.jbotsim.core.Node;

import java.util.*;

import static java.lang.Math.max;

public class Counter extends Node {
    private enum State {
        IDLE,
        PENDING,
        PROCESSING,
        DONE
    }
    private State state;
    private int counter;
    private List<Integer> LocalArray;
    private Queue<Message> pendingQueue = new LinkedList<>();
    private int SharedSum;
    private int localsum;
    private int NodeID;
    @Override
    public void onStart(){
        counter = 0;
        NodeID = getID();
        state = State.IDLE;
        setColor(Color.blue);
        SharedSum = 0;
        Random random = new Random();
        int arraysize = random.nextInt(10) + 1;
        List<Integer> Numbers = new ArrayList<>();
        for (int i = 0; i < arraysize; i++) {
            int temp = random.nextInt(100) + 1;
            Numbers.add(temp);
        }
        LocalArray = Numbers;
        int sum = 0;
        for (int number : Numbers) {
            sum += number;
        }
        localsum = sum;
        SharedSum = localsum;
//        System.out.println(localsum);
//        System.out.println(NodeID);
    }

    @Override
    public void onClock(){
        counter++;

        while(!pendingQueue.isEmpty()){
            Message message = pendingQueue.peek();
            Node sender = message.getSender();
            CustomMessage messaagereceived= processMessage(message);
            String messageType = messaagereceived.getType();
            int receivedcounter = messaagereceived.getCounter();
            int receivedsum = messaagereceived.getSum();
            counter = 1 + max(counter,receivedcounter);
            if(messageType.equals("Write")) {
                if (validTime(receivedcounter)) {
                    processWrite(receivedsum, sender);
                    pendingQueue.poll();
                } else {
                    break; //causal order not there yet.
                }
            } else if(messageType.equals("Read")){
                if(validTime(receivedcounter)){
                    processRead(sender);
                    pendingQueue.poll();
                } else {
                    break; //causal order not there yet.
                }
            }

        }


    }

    private void processRead(Node sender) {
        send(sender, new Message(createMessage("Ack")));
    }

    @Override
    public void onSelection() {
        sendAll(createMessage("Write"));
//        sendAll(new Message("Test"));
        state = State.PROCESSING;
        setColor(Color.RED);
    }

    @Override
    public void onMessage(Message message){
        state = State.PROCESSING;
        setColor(Color.RED);
        List<Node> Neighbors = getNeighbors();
        int numNeighbors = Neighbors.size();
        int ackcount = 0;
//        CustomMessage received = (CustomMessage) message.getContent();
        int senderID = message.getSender().getID();
        CustomMessage messaagereceived= processMessage(message);
        String messageType = messaagereceived.getType();
        int receivedcounter = messaagereceived.getCounter();
        int receivedsum = messaagereceived.getSum();
        counter = 1 + max(counter,receivedcounter);
        if(messageType.equals("Ack")){
            while(!(ackcount==numNeighbors)){
                ackcount++;
                if(receivedsum>SharedSum){
                    SharedSum = receivedsum;
                }
            }
        } else {
            pendingQueue.add(message);
            for(Node node: getNeighbors()){
                if(node != message.getSender()){
                    send(node,createMessage("Write"));
                }
            }
        }




    }

    private void processWrite(int receivedsum, Node sender) {
        SharedSum = receivedsum + SharedSum;
        send(sender, new Message(createMessage("Ack")));
    }

    private boolean validTime(int receivedcounter) {
        //Messages are causally ordered
        return receivedcounter == counter + 1;
    }

    private Message createMessage(String Type){
        CustomMessage customMessage = new CustomMessage();
        customMessage.setCounter(counter);
        customMessage.setSum(SharedSum);
        customMessage.setType(Type);
        return new Message(customMessage);
    }

    private CustomMessage processMessage(Message message)
    {
        return (CustomMessage) message.getContent();
    }
}
