
package procons;

import java.io.Console;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**/
public class Main {
    /**
     * Գրել N (N=1…10) արտադրողներ, M (M=1…10) սպառողներ և մեկ տվյալների
     * հերթ ունեցող կոնսոլային ծրագիր։ Յուրաքանչյուր արտադրող և սպառող
     * աշխատում են առանձին հոսքերով, և բոլոր հոսքերը աշխատում են միաժամանակ։
     */
    public static void main( String[] args )
    {
        // Ծրագիրը գործարկելիս պետք է ներմուծել արտադրողների N թիվը և
        // սպառողների M թիվը, որից հետո ծրագիրը գործարկում է բոլոր հոսքերը։

        Console syscons = System.console();

        // տերմինալից հարդալ producer-ների քանակը
		System.out.print("Number of producers (1..10):\n? ");
        String en = syscons.readLine();
        int N = Integer.parseInt(en);
		if( 0 > N || N > 11 ) {
			System.err.printf("%d is not in [1..10]\n", N);
			System.exit(1);
		}

        // տերմինալից հարդալ consumer-ների քանակը
		System.out.print("Number of consumers (1..10):\n? ");
        String em = syscons.readLine();
        int M = Integer.parseInt(em);
		if( 0 > M || M > 11 ) {
			System.err.printf("%d is not in [1..10]\n", M);
			System.exit(2);
		}

		System.out.printf("%d producers and %d consumers.\n", N, M);


        // թվերի արտածման ֆայլը
        PrintWriter output = null;
        try {
            output = new PrintWriter("data.txt");
        }
        catch( FileNotFoundException ex ) {
            System.exit(1);
        }

        // թվերի հերթը
        DataQueue buffer = new DataQueue();

		// թվերի հերթի վիճակն արտածողը
        Reporter reporter = new Reporter(buffer);
        reporter.start();

		// producer-ների հոսքերի ստեղծումը
        ExecutorService prods = Executors.newFixedThreadPool(N);
        for( int i = 0; i < N; ++i )
            prods.execute(new Producer(buffer));

		// consumer-ների հոսքերի ստեղծումը
        ExecutorService conss = Executors.newFixedThreadPool(M);
        for( int i = 0; i < M; ++i )
            conss.execute(new Consumer(buffer, output));


        // Երբ դադարեցնում ենք ծրագրի աշխատանքը, ապա այն պետք է
        // նախ՝ կանգնեցնի բոլոր արտադրողներին, հետո սպասի այնքան,
        // որ սպառողները դատարկեն հերթը, և վերջում կանգնեցնի
        // սպառողների աշխատանքը։

        // producer-ների և consumer-ների աշխատանքը դադարեցնող հոսքը
        Thread sdh = new Thread() {
            @Override
            public void run()
            {
                // կանգնեցնել բոլոր producer-ներին
                prods.shutdownNow();

                // սպասել մինչև consumer-ները դատարկեն տվյալների հերթը
                while( buffer.getCount() != 0 ) {
                    try {
                        Thread.sleep(100);
                    }
                    catch( InterruptedException e ) {
                        e.printStackTrace();
                    }
                }
                // կանգնեցնել բոլոր consumer-ներին
                conss.shutdownNow();

                // կանգնեցել հերթի վիճակին հետևող հոսքը
                reporter.interrupt();
                // փակել արտածման ֆայլը
                output.close();
            }
        };
        Runtime.getRuntime().addShutdownHook(sdh);
    }
}
