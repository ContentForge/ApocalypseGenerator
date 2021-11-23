package ru.dragonestia.apocalypse.chat;

import ru.dragonestia.apocalypse.Apocalypse;

public class Interference {

    private final transient static char[] BAD_CHARS = {'@', '#', '$', '^', '*', '~', 'Ω', 'Σ', 'ζ', 'λ', 'ψ', '∀', '∂', 'ℶ'};

    private Interference(){

    }

    public static String distort(String text, float lost){
        if(lost > 0.75f) return null;
        if(lost <= 0){
            return text;
        }

        char[] chars = text.toCharArray();
        for(int i = 0; i < chars.length; i++){
            if(chars[i] == ' ') continue;
            if(Apocalypse.random.nextFloat() < lost) chars[i] = BAD_CHARS[Apocalypse.random.nextInt(BAD_CHARS.length)];
        }

        return new String(chars);
    }

}
