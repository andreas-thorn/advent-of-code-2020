public class Day25 {

    private static long transform(long subjectNumber, long loopSize) {
        long result = 1;
        for(int loop = 0; loop < loopSize; loop++)
            result = (result * subjectNumber) % 20201227;

        return result;
    }

    private static long getLoopSize(long publicKey) {
        long transformResult = 1;
        for(int loopSize = 0; ; loopSize++) {
            if(transformResult == publicKey)
                return  loopSize;
            transformResult = (transformResult * 7) % 20201227;
        }
    }

    private static long firstTask(long publicKeyDoor, long publicKeyCard) {
        var loopSize = getLoopSize(publicKeyCard);
        return transform(publicKeyDoor, loopSize);
    }

    public static void main(String[] args) {
        long publicKeyDoor = 6269621;
        long publicKeyCard = 8252394;

        System.out.printf("First task: %d%n", firstTask(publicKeyDoor, publicKeyCard));
    }
}
