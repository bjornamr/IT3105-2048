package Game;

import java.util.Comparator;

/**
 * Created by Joakim on 15.10.2015.
 */
public class CompareTiles{
    public final Comparator<Integer> LEFT = new Comparator<Integer>(){
        @Override
        public int compare(Integer a, Integer b){
            if(a == 0 && b != 0){
                return 1;
            }else if(a != 0 && b == 0){
                return -1;
            }else{
                return 0;
            }
        }
    };
    public  final Comparator<Integer> RIGHT = new Comparator<Integer>(){
        @Override
        public int compare(Integer a, Integer b){
            if(a == 0 && b != 0){
                return -1;
            }else if(a != 0 && b == 0){
                return 1;
            }else{
                return 0;
            }
        }
    };
    public  final Comparator<Integer> UP = new Comparator<Integer>(){
        @Override
        public int compare(Integer a, Integer b){
            if(a == 0 && b != 0){
                return 1;
            }else if(a != 0 && b == 0){
                return -1;
            }else{
                return 0;
            }
        }
    };
    public  final Comparator<Integer> DOWN = new Comparator<Integer>(){
        @Override
        public int compare(Integer a, Integer b){
            if(a == 0 && b != 0){
                return -1;
            }else if(a != 0 && b == 0){
                return 1;
            }else{
                return 0;
            }
        }
    };

}