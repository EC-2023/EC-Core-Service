package src.config.utils;

import lombok.Data;

public class Test {
    public static void main(String[] args) {


    }


    @Data
    class a {
        public int c;
        public int d;

        public a(int i, int i1) {
            c = i;
            d = i1;
        }
    }
}
