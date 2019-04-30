
## Խնդիր #2 (C++/Java)

Գրել N (N=1…10) արտադրողներ, M (M=1…10) սպառողներ և մեկ տվյալների հերթ
ունեցող կոնսոլային ծրագիր։ Յուրաքանչյուր արտադրող և սպառող աշխատում են
առանձին հոսքերով, և բոլոր հոսքերը աշխատում են միաժամանակ։ Արտադրողի հոսքը
քնում է պատահական 0…100 միլիվայրկյան, ապա արթնանում է և գեներացնում է 
1֊ից 100 միջակայքի պատահական ամբողջ թիվ, հետո այդ թիվն ավելացնում է 
տվյալների հերթում։ Սպառողի հոսքը քնում է պատահական 0…100 միլիվայրկյան,
ապա արթնանում է, տվյալների հերթից հանում է մի թիվ և այն գրում է ‘data.txt’
տեքստային ֆայլում։ Բոլոր թվերը կցվում են ֆայլի վերջից և իրարից բաժանված են
ստորակետերով (օրինակ, 4,67,99,23,…)։ Երբ արտադրողի հոսքը հերթական թիվն
ավելացնում է տվյալների հերթում, այն ստուգում է հերթի չափը և, եթե չափը մեծ
է կամ հավասար 100֊ից, հոսքի աշխատանքը բլոկավորվում է մինչև թվերի քանակը 
դառնա փոքր կամ հավասար 80֊ի։ Երբ սպառողն ուզում է հերթից հանել հերթական
թիվը և տեսնում է, որ այն դատարկ է, ապա սպառողի հոսքը բլոկավորվում է մինչև
հերթում արտադրողի կողմից նոր տարրի ավելանալը։

Ծրագիրը գործարկելիս պետք է ներմուծել արտադրողների N թիվը և սպառողների M
թիվը, որից հետո ծրագիրը գործարկում է բոլոր հոսքերը։ Ծրագիրը պետք է ամեն
մի վայրկյան տպի հերթում եղած թվերի քանակը։ Երբ դադարեցնում ենք ծրագրի
աշխատանքը, ապա այն մետք է նախ՝ կանգնեցնի բոլոր արտադրողներին, հետո սպասի
այնքան, որ սպառողները դատարկեն հերթը, և վերջում կանգնեցնի սպառողների
աշխատանքը։



-----

## Task #2​ (C++/Java)

Write a console application which has N producers (N=1…10), M consumers
(M=1…10) and one data queue. Each producer and consumer is a separate 
thread and all threads are working concurrently. Producer thread sleeps 
0…100 milliseconds randomly then it wakes up and generates a random number 
between 1 and 100 and then puts this number into the data queue. Consumer 
thread sleeps 0…100 milliseconds randomly and then wakes up and takes the 
number from the queue and saves it to the output ‘data.txt’ file. All 
numbers are appended in the file and all they are comma delimited (for 
example 4,67,99,23,…). When a producer thread puts the next number to the 
data queue, it checks the size of the data queue, and if it is >=100 the 
producer thread is blocked until the number of elements gets <= 80. When 
a consumer thread wants to take the next number from the data queue and 
there are no elements in it, the consumer thread is blocked until a new 
element is added to the data queue by a producer.

When we start the application we need to insert the N (number of producers) 
and the M (number of consumers) after which the program starts all threads. 
It should print the current number of elements of the data queue every second. 
When we stop the program, it should interrupt all producers and wait for all 
consumers to save all queued data then program exits.
