import java.lang.reflect.Array;
import java.util.Arrays;

public class Selection{

    public static void main(String[] args) {
        int[] MyArray = new int[] {66, 11, 43, 5, 34, 6, 16};
        Selection myObj = new Selection();
        myObj.SelectionSort(MyArray);
        System.out.println(Arrays.toString(MyArray));
    }

    // Select sort an array a with ascending order.
    void SelectionSort(int[] a){
        int n = a.length;
        int self, temp;
        // Outer loop
        for (int i = 0; i < n-1; i++){ //outer loop: how many iteration

            for(int j = i+1; j< n; j++){ //Inner loop: finds the global min in the rest of the element.
                if (a[j] < a[i]){
                    temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }
    }
}











