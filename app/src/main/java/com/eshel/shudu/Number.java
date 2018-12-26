package com.eshel.shudu;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Number {

    List<Integer> maybe = new LinkedList<>();

    public void initMaybe(){
        maybe.clear();
        for (int i = 1; i <= 9; i++) {
            maybe.add(i);
        }
        Set<Integer> known = new HashSet<>();
        setKnownByH(known);
        setKnownByV(known);
        setKnownByAround(known);
        for (Integer integer : known) {
            maybe.remove(integer);
        }
    }

    public void updateMaybe(){
        initMaybe();
    }
    int number;

    int x;
    int y;

    public Number(int number, int x, int y) {
        this.number = number;
        this.x = x;
        this.y = y;
    }

    public Number next_H;//横向
    public Number last_H;

    public void setKnownByH(Set<Integer> known){
        Number next = next_H;
        while (next != null){
            if(next.number != -1)
                known.add(next.number);
            next = next.next_H;
        }

        Number last = last_H;
        while (last != null){
            if(last.number != -1)
                known.add(last.number);
            last = last.last_H;
        }
    }

    public void setKnownByV(Set<Integer> known){
        Number next = next_V;
        while (next != null){
            if(next.number != -1)
                known.add(next.number);
            next = next.next_V;
        }

        Number last = last_V;
        while (last != null){
            if(last.number != -1)
                known.add(last.number);
            last = last.last_V;
        }
    }

    public Number next_V;//纵向
    public Number last_V;

    public Number top;//九宫
    public Number bottom;
    public Number left;
    public Number right;

    public void setKnownByAround(Set<Integer> known){
        Number next = right;
        while (next != null){
            if(next.number != -1)
                known.add(next.number);

            {
                Number next2 = next.bottom;
                while (next2 != null){
                    if(next2.number != -1)
                        known.add(next2.number);
                    next2 = next2.bottom;
                }

                Number last2 = next.top;
                while (last2 != null){
                    if(last2.number != -1)
                        known.add(last2.number);
                    last2 = last2.top;
                }
            }

            next = next.right;
        }

        Number last = left;
        while (last != null){
            if(last.number != -1)
                known.add(last.number);

            {
                Number next2 = last.bottom;
                while (next2 != null){
                    if(next2.number != -1)
                        known.add(next2.number);
                    next2 = next2.bottom;
                }

                Number last2 = last.top;
                while (last2 != null){
                    if(last2.number != -1)
                        known.add(last2.number);
                    last2 = last2.top;
                }
            }

            last = last.left;
        }

        Number self = this;
        if(self.number != -1)
            known.add(self.number);

        {
            Number next2 = self.bottom;
            while (next2 != null){
                if(next2.number != -1)
                    known.add(next2.number);
                next2 = next2.bottom;
            }

            Number last2 = self.top;
            while (last2 != null){
                if(last2.number != -1)
                    known.add(last2.number);
                last2 = last2.top;
            }
        }

    }

    @Override
    public String toString() {
        return number+"";
    }
}
