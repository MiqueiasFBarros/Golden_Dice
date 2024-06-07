package com.mb.dev.goldendice;

public class Result {
    private int result;
    private String diceType;

    public Result(int result, String diceType) {
        this.result = result;
        this.diceType = diceType;
    }

    public int getResult() {
        return result;
    }

    public String getDiceType() {
        return diceType;
    }

    private String center(String text, int len){
        if (text.length() >= len) return text.substring(0, len);
        int before = (len - text.length()) / 2;
        int after = len - text.length() - before;
        return String.format("%" + before + "s%s%" + after + "s", "", text, "");
    }

    @Override
    public String toString() {
        String format = "|%s|%s|\n";
        String line = String.format(format, "Dado - " + diceType, "Resultado - " + result);
        return "┌─────────────────────────┐\n" +
                line +
                "└─────────────────────────┘\n";
    }
}
