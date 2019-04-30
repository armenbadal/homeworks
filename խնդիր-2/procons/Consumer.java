package procons;

import java.io.PrintWriter;
import java.util.Random;

/**
 * Սպառողի հոսքը քնում է պատահական 0…100 միլիվայրկյան, ապա
 * արթնանում է, տվյալների հերթից հանում է մի թիվ և այն գրում է
 * ‘data.txt’ տեքստային ֆայլում։ Բոլոր թվերը կցվում են ֆայլի 
 * վերջից և իրարից բաժանված են ստորակետերով (օրինակ, 4,67,99,23,…)։
 */
class Consumer implements Runnable {
    private DataQueue queue = null;
    private PrintWriter writer = null;

    final private Random rand = new Random();

    //
    public Consumer( DataQueue que, PrintWriter wri )
    {
        queue = que;
        writer = wri;
    }

    @Override
    public void run()
    {
        while( !Thread.interrupted() ) {
            try {
                final int msecs = rand.nextInt(100);
                Thread.sleep(msecs);

                Integer value = queue.take();
                writer.print(value);
				writer.print(",");
                writer.flush();
            }
            catch( InterruptedException ex ) {
                System.out.println("Consumer interrupted");
                return;
            }
        }
    }
}
