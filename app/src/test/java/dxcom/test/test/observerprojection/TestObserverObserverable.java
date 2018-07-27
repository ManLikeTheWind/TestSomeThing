package dxcom.test.test.observerprojection;


import org.junit.Test;

/**
 * @serial
 */
public class TestObserverObserverable {
    @Test
    public  void testObserv_er_able(){
        ObserverableAnimal observerableAnimal=new ObserverableAnimal();

        ObserverCat observerCat=new ObserverCat(observerableAnimal);
        ObserverDog observerDog=new ObserverDog(observerableAnimal);

        observerableAnimal.setData("");


    }

}
