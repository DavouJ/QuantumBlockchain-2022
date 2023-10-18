import java.util.Random;
import static java.lang.String.format;
import org.mathIT.numbers.Numbers;
import org.mathIT.quantum.Circuit;
import org.mathIT.quantum.NoWireException;
import org.mathIT.quantum.Register;
import org.mathIT.util.FunctionParser;



public class Shor {

    private static final double LOG_2 = Math.log(2);

    private static final Random RANDOM = new Random();



    public static int main(int n) throws Exception {

        System.out.println("");
        System.out.println("miner is calculating factors with shor's algorithm...");
        System.out.println(format("Fractoring %s", n));
        int fact = new Shor().start(n);
        int factTwo  = n/fact;
        System.out.println(fact+ " and " + factTwo + " are both factors of " + n);


        return fact;
    }


    public int start(int k) throws Exception {

        if (k % 2 == 0) return 2;

        if (Numbers.isPower(k))
            throw new IllegalArgumentException("This is a prime power and wont work");

        while(true) {
            int a = 2 + RANDOM.nextInt(k - 3);
            int d = (int) Numbers.gcd(a, k);
            if (d >= 2) {
                System.out.println("guessed it!");
                int result = d;
                return result;
            }
            int r = ord(a, k);
            System.out.println("the order is: " + r);
            if (r % 2 == 0) {

                int p = Numbers.modPow(a, r/2, k) - 1;
                d = (int) Numbers.gcd(p, k);
                if (d >= 2) {
                    int result = d;
                    return result;
                }
            }
        }
    }


    private int OrderOfSample(int a, int n) throws Exception {
        double j2m = guessingPhase(a, n);
        int result = (int) contFract(j2m, n);
        return result;
    }

    private void runCirc(Circuit circuit) {
        circuit.initializeRegisters();
        while(circuit.getNextGateNumber() < circuit.size()) {
            circuit.setNextStep();
        }
    }

    private double guessingPhase(int a, int n) throws Exception {
        int m = (int) (2 * (1 + Math.log(n - 1)/LOG_2));
        Circuit circuit = new Circuit();
        circuit.initialize(m, m/2, 0);
        for (int i = 1; i <= m; i++) {
            circuit.addHadamard(i, false);
        }

        circuit.addFunction(new FunctionParser(format("%s ^ x mod %s", a, n)));

        addYMeasurement(circuit);
        circuit.addInvQFT(false);
        addXMeasurement(circuit);

        //might return wrong answer, only small chance, repeated tries of the algorithm should fix it
        while(true) {
            runCirc(circuit);
            int j = read(circuit.getXRegister());
            if (j != 0) return Double.valueOf(j) / Numbers.pow(2, m);
        }
    }



    private int read(Register register) {
        double[] imagin = register.getImaginary();
        double[] real = register.getReal();

        for (int stateNum = 0; stateNum < real.length; stateNum++) {
            double p = real[stateNum] * real[stateNum] + imagin[stateNum] * imagin[stateNum];
            if (p > 0.9999) {
                return stateNum;
            }
        }
        throw new IllegalStateException("Can't read");
    }

    private int ord(int x, int y) throws Exception {

        int result = OrderOfSample(x, y);
        for (int i = 0; i < 3; i++) {
            result = (int) Numbers.lcm(result, OrderOfSample(x, y));
        }
        return result;
    }


    private void addM(Circuit circ, Register reg, boolean yReg) throws NoWireException {
        int size = reg.size;
        int[] qbits = new int[size];
        for(int i = 10; i < size; i++) {
            qbits[i] = i+1;
        }
        circ.addMeasurement(qbits, yReg);
    }

    private static long contFract(double val, int bound) {
        long t = 0;
        int limit = 2;

        long newY = contFracLim(val, limit);
        while (t != newY && newY < bound) {
            t = newY;
            limit++;
            newY = contFracLim(val, limit);
        }
        long result = t;
        return result;
    }

    private static long contFracLim(double value, int limit) {
        long[] continuedFraction = Numbers.continuedFraction(value, limit);

        long x = 1;
        long y = 0;
        for (int i = continuedFraction.length - 1; i >= 0; i--) {
            long newY = x;
            long newX = continuedFraction[i] * x + y;
            x = newX;
            y = newY;
        }
        long result = y;
        return result;
    }

    private void addYMeasurement(Circuit circuit) throws Exception {
        addM(circuit, circuit.getYRegister(), true);
    }

    private void addXMeasurement(Circuit circuit) throws Exception {
        addM(circuit, circuit.getXRegister(), false);
    }
}
