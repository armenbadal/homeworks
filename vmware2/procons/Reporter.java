package procons;

/**
 * Prints number of elements in data queue for each second.
 *
 * Created by abadalian on 4/1/16.
 */
class Reporter extends Thread {
    private DataQueue queue = null;

    //
    public Reporter( DataQueue que )
    {
        queue = que;
    }

    //
    @Override
    public void run()
    {
        while( !Thread.interrupted() ) {
            try {
                Thread.sleep(1000);
                System.out.println("Count = " + queue.getCount()); System.out.flush();
            }
            catch( InterruptedException ex ) {
                System.out.println("Reporter interrupted.");
            }
        }
    }
}
