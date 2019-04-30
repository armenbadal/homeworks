package procons;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
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
        return count;
    }

    /**
     * Երբ արտադրողի հոսքը հերթական թիվն ավելացնում է տվյալների
     * հերթում, այն ստուգում է հերթի չափը և, եթե չափը մեծ է կամ
     * հավասար 100֊ից, հոսքի աշխատանքը բլոկավորվում է մինչև թվերի
     * քանակը դառնա փոքր կամ հավասար 80֊ի։
     */
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

    /**
     * Երբ սպառողն ուզում է հերթից հանել հերթական թիվը և տեսնում
     * է, որ այն դատարկ է, ապա սպառողի հոսքը բլոկավորվում է մինչև
     * հերթում արտադրողի կողմից նոր տարրի ավելանալը։
     */
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
