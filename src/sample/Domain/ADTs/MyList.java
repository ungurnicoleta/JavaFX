package sample.Domain.ADTs;

import sample.Exception.MyException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyList<T1> implements IList<T1>, Iterable<T1> {
    private List<T1> myLista;

    public MyList(){
        this.myLista = new ArrayList<T1>();
    }

    @Override
    public void add(T1 elem){
        this.myLista.add(elem);
    }

    @Override
    public void delete(T1 elem) throws MyException {
        if(this.myLista.isEmpty())
            throw new MyException("The list is empty!");
            this.myLista.remove(elem);
    }

    @Override
    public int size() {
        return this.myLista.size();
    }

    @Override
    public T1 get(int index) {
        return this.myLista.get(index);
    }

    @Override
    public String toString() {
        String printList = "";
        for (T1 element : this.myLista){
            printList = printList + "Element: " + element.toString() + "\n";
        }
        return printList;
    }
    @Override
    public ArrayList<T1> toArray(){
        ArrayList<T1> toArray = new ArrayList<>();
        for (T1 elem:myLista) {
            toArray.add(elem);
        }
        return toArray;
    }

    @Override
    public Iterator<T1> iterator() {
        return this.myLista.iterator();
    }
}
