
/**
 * # Խնդիր #2? (C++/Java)
 *
 * Գրել N (N=1…10) արտադրողներ, M (M=1…10) սպառողներ և մեկ
 * տվյալների հերթ ունեցող կոնսոլային ծրագիր։ Յուրաքանչյուր 
 * արտադրող և սպառող աշխատում են առանձին հոսքերով, և բոլոր 
 * հոսքերը աշխատում են միաժամանակ։ Արտադրողի հոսքը քանում է 
 * պատահական 0…100 միլիվայրկյան, ապա արթնանում է և գեներացնում 
 * է 1֊ից 100 միջակայքի պատահական ամբողջ թիվ, հետո այդ թիվն 
 * ավելացնում է տվյալների հերթում։ Սպառողի հոսքը քնում է 
 * պատահական 0…100 միլիվայրկյան, ապա արթնանում է, տվյալների 
 * հերթից հանում է մի թիվ և այն գրում  է ‘data.txt’ տեքստային 
 * ֆայլում։ Բոլոր թվերը կցվում են ֆայլի վերջից և իրարից 
 * բաժանված են ստորակետերով (օրինակ, 4,67,99,23,…)։ Երբ 
 * արտադրողի հոսքը հերթական թիվն ավելացնում է տվյալների հերթում, 
 * այն ստուգում է հերթի չափը և, եթե չափը մեծ է կամ հավասար 
 * 100֊ից, հոսքի աշխատանքը բլոկավորվում է մինչև թվերի քանակը 
 * դառնա փոքր կամ հավասար 80֊ի։ Երբ սպառողն ուզում է հերթից 
 * հանել հերթական թիվը և տեսնում է, որ այն դատարկ է, ապա սպառողի 
 * հոսքը բլոկավորվում է մինչև հերթում արտադրողի կողմից նոր տարրի
 * ավելանալը։ Ծրագիրը գործարկելիս պետք է ներմուծել արտադրողների 
 * N թիվը և սպառողների M թիվը, որից հետո ծրագիրը գործարկում է 
 * բոլոր հոսքերը։  Ծրագիրը պետք է ամեն մի վայկյան տպի հերթում 
 * եղած թվերի քանակը։ Երբ դադարեցնում ենք ծրագրի աշխատանքը, 
 * ապա այն մետք է նախ՝ կանգնեցնի բոլոր արտադրողներին, հետո սպասի
 * այնքան, որ սպառողները դատարկեն հերթը, և վերջում կանգնեցնի 
 * սպառողների աշխատանքը։
 */

package procons;

import java.io.Console;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by abadalian on 3/31/16.
 */
public class Main {
    //
    public static void main( String[] args )
    {
        // read number of producers and consumers
        // from command line
        Console syscons = System.console();

		System.out.print("Number of producers (1..10):\n? ");
        String en = syscons.readLine();
        int N = Integer.parseInt(en);
		if( 0 > N || N > 11 ) {
			System.err.printf("%d is not in [1..10]\n", N);
			System.exit(1);
		}

		System.out.print("Number of consumers (1..10):\n? ");
        String em = syscons.readLine();
        int M = Integer.parseInt(em);
		if( 0 > M || M > 11 ) {
			System.err.printf("%d is not in [1..10]\n", M);
			System.exit(2);
		}

		System.out.printf("%d producers and %d consumers.\n", N, M);


        // file for output
        PrintWriter output = null;
        try {
            output = new PrintWriter("data.txt");
        }
        catch( FileNotFoundException ex ) {
            System.exit(1);
        }

        // data buffer
        DataQueue buffer = new DataQueue();

		// status reporter
        Reporter reporter = new Reporter(buffer);
        reporter.start();

		// producers
        ExecutorService prods = Executors.newFixedThreadPool(N);
        for( int i = 0; i < N; ++i )
            prods.execute(new Producer(buffer));

		// consumers
        ExecutorService conss = Executors.newFixedThreadPool(M);
        for( int i = 0; i < M; ++i )
            conss.execute(new Consumer(buffer, output));


        // shutdown handler
        final PrintWriter eOutput = output;
        Thread sdh = new Thread() {
            @Override
            public void run()
            {
                prods.shutdownNow();

                while( buffer.getCount() != 0 ) {
                    try {
                        Thread.sleep(100);
                    }
                    catch( InterruptedException e ) {
                        e.printStackTrace();
                    }
                }
                conss.shutdownNow();

                reporter.interrupt();
                eOutput.close();
            }
        };
        Runtime.getRuntime().addShutdownHook(sdh);
    }
}
