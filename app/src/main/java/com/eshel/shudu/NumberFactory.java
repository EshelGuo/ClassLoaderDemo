package com.eshel.shudu;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NumberFactory {

    public static NumberFactory newInstance() {
        return new NumberFactory();
    }

    List<Number> mNumbers = new ArrayList<>(81);
    Number[][] numberArrays;
    public void initNumbers(int[][] numbers){
        numberArrays = new Number[numbers.length][numbers[0].length];
        for (int i = 0; i < numbers.length; i++) {
            int[] line = numbers[i];
            for (int j = 0; j < line.length; j++) {
                if(line[j] == 0)
                    line[j] = -1;
                Number currentNum = numberArrays[i][j] = new Number(line[j], j, i);
                if(currentNum.number == -1)
                    mNumbers.add(numberArrays[i][j]);

                int x;
                int y;

                if(j - 1 >= 0){
                    x = j-1;
                    y = i;

                    currentNum.last_H = numberArrays[y][x];
                    currentNum.last_H.next_H = currentNum;

                    int currentX = currentNum.x;
                    int lastX = currentNum.last_H.x;
                    if(currentX / 3 == lastX / 3){
                        currentNum.left = currentNum.last_H;
                        currentNum.left.right = currentNum;
                    }
                }

                if(i - 1 >= 0){
                    x = j;
                    y = i-1;

                    currentNum.last_V = numberArrays[y][x];
                    currentNum.last_V.next_V = currentNum;

                    int currentY = currentNum.y;
                    int lastY = currentNum.last_V.y;
                    if(currentY / 3 == lastY / 3){
                        currentNum.top = currentNum.last_V;
                        currentNum.top.bottom = currentNum;
                    }
                }
            }
        }
    }

    public Number getNumberByXY(int x, int y){
        for (Number number : mNumbers) {
            if(number.x == x && number.y == y){
                return number;
            }
        }
        return null;
    }

    public void calculate(){
        for (Number number : mNumbers) {
            number.initMaybe();
        }
        boolean isSuccess = try_(0);
        System.out.println(isSuccess);
        print();
    }

    private void print(){
        for (Number[] numberArray : numberArrays) {
            System.out.println(Arrays.toString(numberArray));
        }
        System.out.println("\n");
    }

    private boolean try_(final int index) {
        final Number number = mNumbers.get(index);
        if(number.x == 0 && number.y == 3){
            System.out.println();
        }
        number.updateMaybe();
        if(index == mNumbers.size() - 1 ){
            if(number.maybe.size() == 1) {
                number.number = number.maybe.get(0);
                return true;
            }
        }
        for (final Integer integer : number.maybe) {
            boolean isSuccess = invokeTry(new Try() {
                @Override
                public void prepareTry() {
                    number.number = integer;
                }

                @Override
                public boolean try_() {
                    int index_ = index+1;
                    if(index_ < mNumbers.size()){
                        boolean isSuccess = NumberFactory.this.try_(index_);
                        return isSuccess;
                    }
                    return false;
                }

                @Override
                public void tryOver() {
                    number.number = -1;
                }
            });
            if(isSuccess)
                return true;
        }
        return false;
    }

    private boolean invokeTry(Try try_){
        try_.prepareTry();
        boolean isSuccess = try_.try_();
        if(isSuccess)
            return true;
        try_.tryOver();
        return false;
    }
}
interface Try{
    void prepareTry();
    boolean try_();
    void tryOver();
}
