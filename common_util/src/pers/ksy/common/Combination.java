package pers.ksy.common;

import java.util.ArrayList;
import java.util.List;

public class Combination<T> {
    private List<List<T>> retList = new ArrayList<>();

    public List<List<T>> combiantion(T chs[]) {
        if (chs == null || chs.length == 0) {
            return null;
        }
        List<T> list = new ArrayList<T>();
        for (int i = 1; i <= chs.length; i++) {
            combine(chs, 0, i, list);
        }
        return retList;
    }

    public void combine(T[] cs, int begin, int number, List<T> list) {
        if (number == 0) {
            retList.add(new ArrayList<T>(list));
            return;
        }
        if (begin == cs.length) {
            return;
        }
        list.add(cs[begin]);
        combine(cs, begin + 1, number - 1, list);
        list.remove(cs[begin]);
        combine(cs, begin + 1, number, list);
    }
    
    

    /**
     * @return the retList
     */
    public List<List<T>> getRetList() {
        return retList;
    }

    public static void main(String[] args) {
        String[] sa = new String[] { "a", "b", "c", "d" };
        Integer[] ia = new Integer[] { 1, 3, 4, 5, 6 };
        Combination<Integer> c = new Combination<>();
        c.combiantion(ia);
        for (List<Integer> list : c.retList) {
            for (Integer s : list) {
                System.out.print(s + " ");
            }
            System.out.println();
        }
    }
}
