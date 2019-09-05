package com.Testing;

public class Plot {

    public static <E> void plotBar(Integer [] items, double max_width, E [] labels){
        if(items.length != labels.length){
            System.err.println("labels/items cardinality mismatch");
            return;
        }
        int max = items[0];
        int [] normalized = new int[items.length];
        for(int e:items){
            if(e > max) max = e;
        }
        for(int i = 0; i < items.length; i++){
            normalized[i] = (int)max_width*items[i]/max;
        }

        for(int i = 0 ; i < items.length; i++){
            StringBuilder sb = new StringBuilder();
            for(int j = 0; j < items[i]; j++){
                sb.append("|");
            }
            sb.append(" ").append(labels[i]);

            System.out.println(sb.toString());
        }
    }
}
