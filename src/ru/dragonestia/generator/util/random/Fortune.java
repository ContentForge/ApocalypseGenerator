package ru.dragonestia.generator.util.random;

import ru.dragonestia.generator.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Fortune<T> {

    private final Random random;
    public final List<Pair<T, Integer>> items = new ArrayList<>();

    public Fortune(Random random){
        this.random = random;
    }

    public int getAllWeight(){
        int all = 0;
        for (Pair<T, Integer> item: items) all += item.second;
        return all;
    }

    public T roll(){
        return roll(random.nextInt(getAllWeight()));
    }

    public T roll(int weight){
        T element;
        int i = 0;
        do {
            element = items.get(i).first;
            weight -= items.get(i).second;
            if(++i >= items.size()) i = 0;
        } while (weight > 0);
        return element;
    }

}
