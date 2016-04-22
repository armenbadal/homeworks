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
    private final Lock lock = new ReentrantLock();
    private final Condition notFull  = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();
    private final Condition lessThan80 = lock.newCondition();

    private final Integer[] items = new Integer[100];
    private int putinx = 0, takeinx = 0, count = 0;

    //
    public int getCount()
    {
        final Lock lock = this.lock;
        lock.lock();
        try {
            return count;
        }
        finally {
            lock.unlock();
        }
    }

    //
    public void put(Integer x) throws InterruptedException
    {
        lock.lock();
        try {
            while( count == items.length ) {
                notFull.await();
                lessThan80.await();
            }

            items[putinx] = x;
            ++putinx;
            if( putinx == items.length )
                putinx = 0;
            ++count;
            notEmpty.signal();
        }
        finally {
            lock.unlock();
        }
    }

    //
    public Integer take() throws InterruptedException
    {
        lock.lock();
        try {
            while( count == 0 )
                notEmpty.await();

            Integer x = items[takeinx];
            ++takeinx;
            if( takeinx == items.length )
                takeinx = 0;
            --count;
            notFull.signal();
            if( count < 80 )
                lessThan80.signalAll();
            return x;
        }
        finally {
            lock.unlock();
        }
    }
}
