import io.jbotsim.core.Message;
import io.jbotsim.core.Color;
import io.jbotsim.core.Node;

import java.util.*;

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
//        System.out.println(localsum);
//        System.out.println(NodeID);
    }

    @Override
    public void onClock(){
        counter++;

        while(!pendingQueue.isEmpty()){
            Message message = pendingQueue.peek();
            Node sender = message.getSender();
            List<Object> messageContent = processMessage(message);
            String messageType = (String) messageContent.get(0);
            int receivedcounter;
            int receivedsum;
            if(messageType.equals("write")){
                receivedcounter = (Integer) messageContent.get(1);
                receivedsum = (Integer) messageContent.get(2);
                if(validTime(receivedcounter)){
                    processWrite(receivedsum,sender);
                    pendingQueue.poll();
                } else {
                    break; //causal order not there yet.
                }
            } else if (messageType.equals("read")) {
                receivedcounter = (Integer) messageContent.get(2);

                if(validTime(receivedcounter)){
                    proocessRead(sender);
                    pendingQueue.poll();
                } else {
                    break; //causal order not there yet.
                }
            }

        }


    }

    private void proocessRead(Node sender) {

    }

    @Override
    public void onSelection() {
        CustomMessage customMessage = new CustomMessage();
        customMessage.setCounter(counter);
        customMessage.setSum(localsum);
        customMessage.setType("Write");
        sendAll(new Message(customMessage));
//        sendAll(new Message("Test"));
    }

    @Override
    public void onMessage(Message message){
        CustomMessage received = (CustomMessage) message.getContent();
        System.out.println("Message Received id ");
        System.out.print(message.getSender().getID());
        System.out.println("Message counter id ");
        System.out.print(received.getCounter());

    }

    private void processWrite(int receivedsum, Node sender) {

    }

    private boolean validTime(int receivedcounter) {
        //Messages are causally ordered
        return receivedcounter == counter + 1;
    }

    private Message createMessage(String Type){
            String result =counter + " " + localsum;
        return new Message(result, Type);
    }

    private List<Object> processMessage(Message message){
        String flag = message.getFlag();
        int receivedcounter = 0;
        int receivedsum = -1;
        if(flag.equals("write")) {
            String content = (String) message.getContent();
            String[] parts = content.split(" ");

            if (parts.length == 2) {
                try {
                    receivedcounter = Integer.parseInt(parts[0]);
                    receivedsum = Integer.parseInt(parts[1]);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid Message. Could not parse integers.");
                }
            } else {
                System.err.println("Corrupted Message");
            }

            List<Object> result = new ArrayList<>();
            result.add(flag);
            result.add(receivedcounter);
            result.add(receivedsum);
            return result;
        } else if (flag.equals("read")) {
            receivedcounter = (Integer) message.getContent();
            List<Object> result = new ArrayList<>();
            result.add(flag);
            result.add(receivedcounter);
            return result;
        }
        return null;
    }

}
