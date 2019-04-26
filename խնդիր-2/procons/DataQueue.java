package procons;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by abadalian on 3/31/16.
 *
 * Based on:
 * https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/locks/Condition.html
 */
class DataQueue {
    private Lock lock = new ReentrantLock();
    private Condition allowPut  = lock.newCondition();
    private Condition allowTake = lock.newCondition();

    private int[] items = new int[100];
    private int putinx = 0, takeinx = 0, count = 0;

    //
    public int getCount()
    {
        lock.lock();
        try {
            return count;
        }
        finally {
            lock.unlock();
        }
    }

    //
    public void put(int x) throws InterruptedException
    {
        lock.lock();
        try {
            while( count == items.length )
                allowPut.await();

            items[putinx++] = x;
            if( putinx == items.length )
                putinx = 0;
            ++count;

            allowTake.signalAll();
        }
        finally {
            lock.unlock();
        }
    }

    //
    public int take() throws InterruptedException
    {
        lock.lock();
        try {
            while( count == 0 )
                allowTake.await();

            int x = items[takeinx++];
            if( takeinx == items.length )
                takeinx = 0;
            --count;

            if( count < 80 )
                allowPut.signalAll();
                
            return x;
        }
        finally {
            lock.unlock();
        }
    }
}
