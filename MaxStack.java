import java.util.LinkedList;
import java.util.List;

public class MaxStack {
    public List<String> storage = new LinkedList<>();
    public int index = 0;
    public int largest = 0;

    @Override
    public void push(int element) {
        if (largest == 0) {
            largest = element;
        } else if (element > largest){
            largest = element;
        }
        index++;
        storage.add(element + "," + largest);
    }

    @Override
    public int pop() {
        var value =  storage.remove(index - 1);
        index--;
        return Integer.parseInt(value.split(",")[0]);
    }


    @Override
    public int getMax() {
        var value = storage.get(index - 1);
        return Integer.parseInt(value.split(",")[1]);
    }
}
