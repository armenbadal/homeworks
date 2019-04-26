package procons;

import java.util.Random;

/**
 * Created by abadalian on 3/31/16.
 */
class Producer implements Runnable {
    private DataQueue queue = null;

    final private Random rand = new Random();

    //
    public Producer( DataQueue que )
    {
        queue = que;
    }

    @Override
    public void run()
    {
        while( !Thread.interrupted() ) {
            try {
                final int msecs = rand.nextInt(100);
                Thread.sleep(msecs);

                final int value = rand.nextInt(100);
                queue.put(value);
            }
            catch( InterruptedException ex ) {
                System.out.println("Producer interrupted");
                return;
            }
        }
    }
}
