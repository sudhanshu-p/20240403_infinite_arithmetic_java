import java.util.ArrayList;
import java.util.Arrays;

public class ArrayNumsTest {
    public static void main(String[] args) {
        ArrayList<Integer> firstNumber = new ArrayList<>(Arrays.asList(8, 9, 0));
        ArrayList<Integer> secondNumber = new ArrayList<>(Arrays.asList(0, 0, 0, 1));

        ArrayList<Integer> result = ArrayNums.addition(firstNumber, secondNumber);

        System.out.println("First Number: " + firstNumber);
        System.out.println("Second Number: " + secondNumber);
        System.out.println("Result: " + result);

        ArrayList<Integer> subFirstNumber = new ArrayList<>(Arrays.asList(5, 2, 0));
        ArrayList<Integer> subSecondNumber = new ArrayList<>(Arrays.asList(2, 1, 2));

        ArrayList<Integer> subtractResult = ArrayNums.subtract(subFirstNumber, subSecondNumber);

        System.out.println("First Number: " + subFirstNumber);
        System.out.println("Second Number: " + subSecondNumber);
        System.out.println("Result: " + subtractResult);

        ArrayList<Integer> mulFirstNumber = new ArrayList<>(Arrays.asList(9, 1));
        ArrayList<Integer> mulSecondNumber = new ArrayList<>(Arrays.asList(8, 1));

        ArrayList<Integer> mulResult = ArrayNums.multiply(mulFirstNumber, mulSecondNumber);

        System.out.println("First Number: " + mulFirstNumber);
        System.out.println("Second Number: " + mulSecondNumber);
        System.out.println("Result: " + mulResult);

        ArrayList<Integer> divident = new ArrayList<>(Arrays.asList(8, 1));
        ArrayList<Integer> divisor = new ArrayList<>(Arrays.asList(9));

        DivisionResult divResult = ArrayNums.divide(divident, divisor);

        System.out.println("Divident: " + divident);
        System.out.println("Divisor: " + divisor);
        System.out.println("Quotient: " + divResult.quotient);
        System.out.println("Remainder: " + divResult.remainder);
    }
}

class ArrayNums {
    private static void verifyInputData(ArrayList<Integer> testingNumber) {
        // Check if input is ArrayList
        if (testingNumber == null) {
            System.out.println("The inputs should be ArrayLists!");
        }

        // Check if ArrayList is empty
        if (testingNumber.isEmpty()) {
            System.out.println("Input must not be empty!");
        }

        // Check if all inputs are integers and in range
        for (int num : testingNumber) {
            if (num < 0 || num > 9) {
                System.out.println("The values in the ArrayList should be 0 - 9");
            }
        }
    }

    public static ArrayList<Integer> addition(ArrayList<Integer> firstNumber, ArrayList<Integer> secondNumber) {
        int carry = 0;
        int index = 0;
        ArrayList<Integer> outputArray = new ArrayList<>();

        while (index < firstNumber.size() && index < secondNumber.size()) {
            int currentSum = firstNumber.get(firstNumber.size() - index - 1) +
                    secondNumber.get(secondNumber.size() - index - 1) + carry;

            carry = 0;

            if (currentSum >= 10) {
                carry = 1;
                currentSum -= 10;
            }

            outputArray.add(0, currentSum);
            index++;
        }

        while (index < firstNumber.size()) {
            int currentSum = firstNumber.get(firstNumber.size() - index - 1) + carry;
            carry = 0;
            if (currentSum >= 10) {
                carry = 1;
                currentSum -= 10;
            }
            outputArray.add(0, currentSum);
            index++;
        }

        while (index < secondNumber.size()) {
            int currentSum = secondNumber.get(secondNumber.size() - index - 1) + carry;
            carry = 0;
            if (currentSum >= 10) {
                carry = 1;
                currentSum -= 10;
            }
            outputArray.add(0, currentSum);
            index++;
        }

        if (carry > 0) {
            outputArray.add(0, carry);
        }

        return outputArray;
    }

    private static int getBiggerOfTwoNumArrays(ArrayList<Integer> firstNumber, ArrayList<Integer> secondNumber) {
        // Only intended to be used after verification is complete.

        // Case of firstNumber being greater in length
        if (firstNumber.size() - secondNumber.size() > 0) {
            return 1;
        }

        // Case of secondNumber being greater in length
        if (firstNumber.size() - secondNumber.size() < 0) {
            return -1;
        }

        // Case of equal length, need to check digit by digit which is bigger
        for (int i = 0; i < firstNumber.size(); i++) {
            // Case that firstNumber's digit is bigger
            if (firstNumber.get(i) > secondNumber.get(i)) {
                return 1;
            }
            // Case that secondNumber's digit is bigger
            if (firstNumber.get(i) < secondNumber.get(i)) {
                return -1;
            }
        }

        // If the loop above ends, both the numbers are equal.
        return 0;
    }

    private static ArrayList<Integer> simpleSubtract(ArrayList<Integer> biggerNumber,
            ArrayList<Integer> smallerNumber) {
        int borrow = 0;
        int index = 0;
        ArrayList<Integer> outputArray = new ArrayList<>(biggerNumber.size());

        // Subtracts digit by digit until smaller number wears out
        while (index < biggerNumber.size() && index < smallerNumber.size()) {
            int currentNum = biggerNumber.get(biggerNumber.size() - index - 1) - borrow
                    - smallerNumber.get(smallerNumber.size() - index - 1);
            borrow = 0;

            // If the digit is negative, make it positive and switch borrow
            if (currentNum < 0) {
                currentNum += 10;
                borrow = 1;
            }
            outputArray.add(0, currentNum);
            index++;
        }

        // Only bigger number can have remaining digits
        while (index < biggerNumber.size()) {
            int currentNum = biggerNumber.get(biggerNumber.size() - index - 1) - borrow;
            borrow = 0;

            if (currentNum < 0) {
                currentNum += 10;
                borrow = 1;
            }

            outputArray.add(0, currentNum);
            index++;
        }

        // Remove leading zeros, except for the case of a single zero
        return trimLeadingZeros(outputArray);
    }

    public static ArrayList<Integer> subtract(ArrayList<Integer> firstNumber, ArrayList<Integer> secondNumber) {
        verifyInputData(firstNumber);
        verifyInputData(secondNumber);

        switch (getBiggerOfTwoNumArrays(firstNumber, secondNumber)) {
            // secondNumber > firstNumber
            case -1:
                ArrayList<Integer> outputArray = simpleSubtract(secondNumber, firstNumber);
                outputArray.add(0, -1);
                return outputArray;
            // The numbers are equal
            case 0:
                return new ArrayList<>(Arrays.asList(0));
            // firstNumber > secondNumber
            case 1:
                return simpleSubtract(firstNumber, secondNumber);
        }
        return null;
    }

    private static ArrayList<Integer> trimLeadingZeros(ArrayList<Integer> list) {
        int startIndex = 0;
        while (startIndex < list.size() - 1 && list.get(startIndex) == 0) {
            startIndex++;
        }
        if (startIndex > 0) {
            return new ArrayList<>(list.subList(startIndex, list.size()));
        }
        return list;
    }

    private static ArrayList<Integer> oneDigitMultiply(ArrayList<Integer> multiplicant, int multiplier) {
        // Handling case of multiplier being 0
        if (multiplier == 0) {
            return new ArrayList<>(Arrays.asList(0));
        }

        int carry = 0;
        ArrayList<Integer> currentResult = new ArrayList<>(multiplicant.size() + 1);

        // Loops over the multiplicant and accesses it in reverse order
        for (int multiplicantIndex = 0; multiplicantIndex < multiplicant.size(); multiplicantIndex++) {
            int currentDigit = multiplier * multiplicant.get(multiplicant.size() - multiplicantIndex - 1) + carry;
            carry = 0;

            // Setting carry if there's need
            if (currentDigit > 9) {
                carry = currentDigit / 10;
                currentDigit %= 10;
            }
            currentResult.add(0, currentDigit);
        }

        if (carry > 0) {
            currentResult.add(0, carry);
        }

        return currentResult;
    }

    private static ArrayList<Integer> addAll(ArrayList<ArrayList<Integer>> arrayOfArrays) {
        // Checking for lengths
        if (arrayOfArrays.isEmpty()) {
            return new ArrayList<>(Arrays.asList(0));
        }
        if (arrayOfArrays.size() == 1) {
            return arrayOfArrays.get(0);
        }

        ArrayList<Integer> outputArray = addition(arrayOfArrays.get(0), arrayOfArrays.get(1));

        for (int index = 2; index < arrayOfArrays.size(); index++) {
            outputArray = addition(outputArray, arrayOfArrays.get(index));
        }

        return outputArray;
    }

    public static ArrayList<Integer> multiply(ArrayList<Integer> firstNumber, ArrayList<Integer> secondNumber) {
        verifyInputData(firstNumber);
        verifyInputData(secondNumber);

        int secondNumIndex = 0;
        ArrayList<ArrayList<Integer>> outputArray = new ArrayList<>(secondNumber.size());

        while (secondNumIndex < secondNumber.size()) {
            int currentMultiplier = secondNumber.get(secondNumber.size() - secondNumIndex - 1);

            if (currentMultiplier == 0) {
                secondNumIndex++;
                continue;
            }
            ArrayList<Integer> currentResult = oneDigitMultiply(firstNumber, currentMultiplier);

            // Add the trailing 0s
            for (int trailing = 0; trailing < secondNumIndex; trailing++) {
                currentResult.add(0);
            }

            outputArray.add(currentResult);
            secondNumIndex++;
        }

        return addAll(outputArray);
    }

    private static Object[] checkMaxMultiple(ArrayList<Integer> divident, ArrayList<Integer> divisor) {
        // Data here would already be verified, so we need not do it again

        int maxSafeTimes = 1;

        // Run until the value reaches 10
        while (maxSafeTimes < 11) {
            ArrayList<Integer> result = subtract(divident, oneDigitMultiply(divisor, maxSafeTimes));

            // This indicates overkill has occurred, so return with last maximum value
            if (result.get(0) == -1) {
                ArrayList<Integer> remainder = subtract(divident, oneDigitMultiply(divisor, maxSafeTimes - 1));
                return new Object[] { maxSafeTimes - 1, remainder };
            }
            maxSafeTimes++;
        }

        // We are dividing the number in such a way that maxSafeTimes cannot
        // be a 2 digit number. If it is, we throw an error.
        throw new Error("Unknown error occurred. Please try again");
    }

    @SuppressWarnings("unchecked")
    public static DivisionResult divide(ArrayList<Integer> divident, ArrayList<Integer> divisor) {
        verifyInputData(divident);
        verifyInputData(divisor);

        // Division by 0 case
        if (divisor.get(0) == 0) {
            throw new Error("Cannot divide by 0");
        }

        // We want divident > divisor, otherwise the divident is remainder
        if (getBiggerOfTwoNumArrays(divident, divisor) == -1) {
            return new DivisionResult(new ArrayList<>(Arrays.asList(0)), divident);
        }

        // In case they are equal, return quotient as 1 and remainder 0
        if (getBiggerOfTwoNumArrays(divident, divisor) == 0) {
            return new DivisionResult(new ArrayList<>(Arrays.asList(1)), new ArrayList<>());
        }

        ArrayList<Integer> currentDivident = new ArrayList<>(divident);
        ArrayList<Integer> quotientArray = new ArrayList<>();
        ArrayList<Integer> remainder = new ArrayList<>();

        while (!currentDivident.isEmpty() || getBiggerOfTwoNumArrays(remainder, divisor) != -1) {
            Object[] tempArray = checkMaxMultiple(currentDivident, divisor);
            int quotient = (int) tempArray[0];
            remainder = (ArrayList<Integer>) tempArray[1];
            quotientArray.add(quotient);

            int remainderSize = remainder.size();
            currentDivident = new ArrayList<>(remainder);
            if (remainderSize < divisor.size()) {
                break;
            }
        }

        return new DivisionResult(quotientArray, remainder);
    }
}

class DivisionResult {
    ArrayList<Integer> quotient;
    ArrayList<Integer> remainder;

    DivisionResult(ArrayList<Integer> quotient, ArrayList<Integer> remainder) {
        this.quotient = quotient;
        this.remainder = remainder;
    }
}