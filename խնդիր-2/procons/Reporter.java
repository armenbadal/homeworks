package procons;

/**
 * Ծրագիրը պետք է ամեն մի վայրկյան տպի հերթում եղած թվերի քանակը։
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
                System.out.println("Count = " + queue.getCount());
                System.out.flush();
            }
            catch( InterruptedException ex ) {
                System.out.println("Reporter interrupted.");
            }
        }
    }
}
