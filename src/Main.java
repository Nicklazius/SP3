public class Main {

    public static void main(String[] args) {
        StreamingService s = new StreamingService("StreamLy");

        s.startSession();
        s.endSession();
    }
}
