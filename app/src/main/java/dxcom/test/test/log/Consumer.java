package dxcom.test.test.log;


/** 消费者 */
class Consumer implements Runnable{
    private Resourcer resourcer;
    public Consumer(Resourcer resourcer){
        this.resourcer=resourcer;
    }

    @Override
    public void run() {

        while(true){
            resourcer.destroy();
        }
    }



}