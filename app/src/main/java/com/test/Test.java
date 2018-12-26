package com.test;

import java.util.ArrayList;
import java.util.Arrays;

public class Test {

    private static E[] sEs;
    private static E sE1;
    private static E sE2;
    private static E sE3;
    private static E sE4;
    private static E sE5;
    private static ArrayList<E> sAll;

    /**
     * 1 有五栋五种颜色的房子
     * 2 每一位房子的主人国籍都不同
     * 3 这五个人每人只喝一种饮料，只抽一种牌子的香烟，只养一种宠物
     * 4 没有人有相同的宠物，抽相同牌子的香烟，喝相同的饮料
     *
     * 提示：
     * 1 英国人住在红房子里
     * 2 瑞典人养了一条狗
     * 3 丹麦人喝茶
     * 9 挪威人住第一间房子
     * 13 德国人抽 PRINCE 烟
     * 7 黄房子主人抽 DUNHILL 烟
     *
     * 14 挪威人住在蓝房子旁边
     * 11 养马人住在 DUNHILL 烟的人旁边
     *
     * 4 绿房子在白房子左边
     * 5 绿房子主人喝咖啡
     *
     *
     * 6 抽 PALL MALL 烟的人养了一只鸟
     * 8 住在中间那间房子的人喝牛奶
     * 10 抽混合烟的人住在养猫人的旁边
     * 12 抽 BLUE MASTER 烟的人喝啤酒
     * 15 抽混合烟的人的邻居喝矿泉水
     * 问题: 谁养鱼?
     */
    public static void main(String args[]){
        sEs = new E[5];
        sE1 = new E(Home.黄房子, People.挪威人,null, null, Smoke.dunhill烟);
        sE2 = new E(Home.红房子, People.英国人,null, null, null);
        sE3 = new E(null, People.德国人,null, null, Smoke.prince烟);
        sE4 = new E(null, People.瑞典人,Animal.DOG, null, null);
        sE5 = new E(null, People.丹麦人,null, Water.茶, null);

        sAll = new ArrayList<>(5);
        sAll.add(sE1);
        sAll.add(sE2);
        sAll.add(sE3);
        sAll.add(sE4);
        sAll.add(sE5);

        initEs(sE1);

        /*
        E{mHome=黄房子, mPeople=挪威人, mAnimal=猫, mWater=矿泉水, mSmoke=dunhill烟}
        E{mHome=蓝房子, mPeople=丹麦人, mAnimal=马, mWater=茶, mSmoke=混合烟}
        E{mHome=红房子, mPeople=英国人, mAnimal=鸟, mWater=牛奶, mSmoke=pallmall烟}
        E{mHome=绿房子, mPeople=德国人, mAnimal=null, mWater=咖啡, mSmoke=prince烟}
        E{mHome=白房子, mPeople=瑞典人, mAnimal=DOG, mWater=啤酒, mSmoke=bluemaster烟}
*/
    }

    private static boolean tryE(E e,Try try_){
        count++;
        if(try_ == null || e == null)
            return false;
        try_.prepareTry(e);
        boolean success = try_.try_(e);
        if(!success)
            try_.tryOver(e);
        return success;
    }

    private static void initEs(E e1) {
        sEs[0] = e1;
        for (E e : assertBlueHome()) {
            boolean success = tryE(e, new Try() {
                @Override
                public void prepareTry(E e) {
                    sEs[1] = e;
                    e.mHome = Home.蓝房子;
                    e.mAnimal = Animal.马;
                }

                @Override
                public boolean try_(E e) {
                    return tryBlueHome();
                }

                @Override
                public void tryOver(E e) {
                    e.mHome = null;
                    e.mAnimal = null;
                    sEs[1] = null;
                }
            });
            if(success){
                System.out.println("-------------------------- start -----------------------------");
                System.out.println(Arrays.toString(sEs));
                System.out.println("--------------------------- end ------------------------------");
                System.out.println(count);
            }
        }
    }

    private static boolean tryBlueHome() {
        for (E assertE3 : assertE3Home()) {
            boolean success = tryE(assertE3, new Try() {
                @Override
                public void prepareTry(E e) {
                    sEs[2] = e;
                    e.mWater = Water.牛奶;
                }

                @Override
                public boolean try_(E e) {
                    return tryGreenHome();
                }

                @Override
                public void tryOver(E e) {
                    if (e != null)
                        e.mWater = null;
                    sEs[2] = null;
                }
            });
            if(success)
                return true;
        }
        return false;
    }

    private static boolean tryGreenHome() {
        E[] greenHomes = assertGreenHome();
        for (E greenHome : greenHomes) {
            boolean success = tryE(greenHome, new Try() {
                @Override
                public void prepareTry(E e) {
                    e.mHome = Home.绿房子;
                    e.mWater = Water.咖啡;
                    sEs[3] = e;
                }

                @Override
                public boolean try_(E e) {
                    return tryWhiteHome(e);
                }

                @Override
                public void tryOver(E e) {
                    e.mHome = null;
                    e.mWater = null;
                    sEs[3] = null;
                }
            });
            if(success)
                return true;
        }
        return false;
    }

    private static boolean tryWhiteHome(final E greenHome) {
        final E[] whites = assertWhiteHome();
        if (whites == null || whites.length == 0)
            return false;
        for (E white : whites) {
            boolean success = tryE(white, new Try() {
                @Override
                public void prepareTry(E e) {
                    sEs[getIndexByE(greenHome) + 1] = e;
                    e.mHome = Home.白房子;
                }

                @Override
                public boolean try_(E e) {
                    return tryPallmall();
                }

                @Override
                public void tryOver(E e) {
                    sEs[getIndexByE(greenHome) + 1] = null;
                    e.mHome = null;
                }
            });
            if(success)
                return true;
        }
        return false;
    }

    private static boolean tryPallmall() {
        E[] pallmallEs = assertPallmall();
        for (E pallmallE : pallmallEs) {
            boolean success = tryE(pallmallE, new Try() {
                @Override
                public void prepareTry(E pallmallE) {
                    pallmallE.mSmoke = Smoke.pallmall烟;
                    pallmallE.mAnimal = Animal.鸟;
                }

                @Override
                public boolean try_(E e) {
                    return tryBluemaster();
                }

                @Override
                public void tryOver(E pallmallE) {
                    pallmallE.mSmoke = null;
                    pallmallE.mAnimal = null;
                }
            });
            if(success)
                return true;
        }
        return false;
    }

    private static boolean tryBluemaster() {
        E[] bluemaster = assertBluemaster();
        if(arrayIsEmpty(bluemaster))
            return false;
        for (E e : bluemaster) {
            boolean success = tryE(e, new Try() {
                @Override
                public void prepareTry(E e) {
                    e.mSmoke = Smoke.bluemaster烟;
                    e.mWater = Water.啤酒;
                }

                @Override
                public boolean try_(E e) {
                    return tryBlend();
                }

                @Override
                public void tryOver(E e) {
                    e.mSmoke = null;
                    e.mWater = null;
                }
            });
            if(success)
                return true;
        }
        return false;
    }

    static int count;
    private static boolean tryBlend() {
        E[] blends = assertBlend();
        if(arrayIsEmpty(blends))
            return false;
        for (final E blend : blends) {
            boolean success = tryE(blend, new Try() {
                @Override
                public void prepareTry(E e) {
                    e.mSmoke = Smoke.混合烟;
                }

                @Override
                public boolean try_(E e) {
                    //混合烟人的脚标
                    int blendIndex = getIndexByE(e);
                    E last = getLast(blendIndex);
                    E next = getNext(blendIndex);
                    return checkTheEndResult(last, next);
                }

                private boolean checkTheEndResult(E last, E next) {
                    //10．抽混合烟的人住在养猫人的旁边
                    //15．抽混合烟的人的邻居喝矿泉水
                    boolean canCattery = false;//养猫
                    boolean canDrinkWater = false;//喝矿泉水
                    if(last != null){
                        canCattery = checkCattery(last);
                        canDrinkWater = checkDrinkWater(last);
                    }

                    if(next != null){
                        if(!canCattery)
                            canCattery = checkCattery(next);
                        if(!canDrinkWater)
                            canDrinkWater = checkDrinkWater(next);
                    }
                    if(canCattery && canDrinkWater)
                        return true;

                    //reset
                    if(last != null){
                        if(last.mWater == Water.矿泉水)
                            last.mWater = null;
                        if(last.mAnimal == Animal.猫)
                            last.mAnimal = null;
                    }

                    if(next != null){
                        if(next.mWater == Water.矿泉水)
                            next.mWater = null;
                        if(next.mAnimal == Animal.猫)
                            next.mAnimal = null;
                    }
                    return false;
                }

                private boolean checkDrinkWater(E e) {
                    if(e.mWater == null || e.mWater == Water.矿泉水){
                        e.mWater = Water.矿泉水;
                        return true;
                    }
                    return false;
                }

                private boolean checkCattery(E e) {
                    if(e.mAnimal == null || e.mAnimal == Animal.猫){
                        e.mAnimal = Animal.猫;
                        return true;
                    }
                    return false;
                }

                private E getNext(int blendIndex) {
                    int index = blendIndex + 1;
                    if(index >= 0 && index < sEs.length)
                        return sEs[index];
                    return null;
                }

                private E getLast(int blendIndex) {
                    int index = blendIndex - 1;
                    if(index >= 0 && index < sEs.length)
                        return sEs[index];
                    return null;
                }

                @Override
                public void tryOver(E e) {
                    e.mSmoke = null;
                }
            });
            if(success)
                return true;
        }
        return false;
    }

    private static E[] assertBlend() {
        ArrayList<E> blend = new ArrayList<>();
        for (E e : sAll) {
            if(e.mSmoke != null)
                continue;
            blend.add(e);
        }
        return blend.toArray(new E[0]);
    }

    private static E[] assertBluemaster() {
        //12．抽bluemaster烟的人喝啤酒
        ArrayList<E> bluemasters = new ArrayList<>();
        for (E e : sAll) {
            if(e.mSmoke != null)
                continue;
            if(e.mWater != null)
                continue;
            bluemasters.add(e);
        }
        return bluemasters.toArray(new E[0]);
    }

    private static E[] assertPallmall() {
        ArrayList<E> result = new ArrayList<>(5);
        for (E e : sAll) {
            if(e.mSmoke != null)
                continue;
            if(e.mAnimal != null)
                continue;
            result.add(e);
        }
        return result.toArray(new E[0]);
    }

    private static int getIndexByE(E e){
        for (int i = 0; i < sEs.length; i++) {
            E e1 = sEs[i];
            if(e == e1)
                return i;
        }
        return -1;
    }

    private static E[] assertE3Home() {
        ArrayList<E> e3 = new ArrayList<>(4);
        for (int i = 1; i < sAll.size() - 1; i++) {
            E e = sAll.get(i);
            if(e.mHome == Home.蓝房子)
                continue;
            e3.add(e);
        }
        return e3.toArray(new E[0]);
        //e3 != e5
        //e3 != e1
        //e3 != assertBlueHome();
        //e3 == 绿房子 || e3 == 白房子 || e3 == 红房子
        //e3 == [e2 e3 e4]
    }

    /**
     * 谁住蓝房子?
     */
    private static E[] assertBlueHome() {
        // != e2
        //蓝房子养马
        return new E[]{sE3, sE5};
    }

    private static E[] assertGreenHome(){
        ArrayList<E> greenE = new ArrayList<>(3);
        for (int i = 1; i < sAll.size(); i++) {
            E e = sAll.get(i);
            if(e.mHome != null)
                continue;
            if(e.mWater != null)
                continue;
            greenE.add(e);
        }
        return greenE.toArray(new E[0]);
    }

    private static E[] assertWhiteHome(){
        ArrayList<E> white = new ArrayList<>(2);
        for (int i = 1; i < sAll.size(); i++) {
            E e = sAll.get(i);
            if(e.mHome != null)
                continue;
            white.add(e);
        }
        return white.toArray(new E[0]);
    }

    private static boolean arrayIsEmpty(Object[] arrays){
        return arrays == null || arrays.length == 0;
    }
}

class E{
    Home mHome;
    People mPeople;
    Animal mAnimal;
    Water mWater;
    Smoke mSmoke;

    public E(Home home, People people, Animal animal, Water water, Smoke smoke) {
        mHome = home;
        mPeople = people;
        mAnimal = animal;
        mWater = water;
        mSmoke = smoke;
    }

    @Override
    public String toString() {
        return "E{" +
                "mHome=" + mHome +
                ", mPeople=" + mPeople +
                ", mAnimal=" + mAnimal +
                ", mWater=" + mWater +
                ", mSmoke=" + mSmoke +
                '}';
    }
}

enum Home{
    红房子, 绿房子, 白房子, 黄房子, 蓝房子
}

enum People{
    瑞典人, 丹麦人, 挪威人, 德国人, 英国人
}

enum Animal{
    DOG, 鸟, 猫, 马, 鱼
}

enum Water{
    咖啡, 牛奶, 啤酒, 矿泉水, 茶
}

enum Smoke{
    pallmall烟, dunhill烟, 混合烟, bluemaster烟, prince烟
}

interface Try{
    void prepareTry(E e);
    boolean try_(E e);
    void tryOver(E e);
}
