package dxcom.test.test.log;

/**生产者*/
class Producer implements Runnable{
    private Resourcer resourcer;
    public Producer(Resourcer resourcer){
        this.resourcer=resourcer;
    }

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
            resourcer.create("sdf");
        }
    }
}