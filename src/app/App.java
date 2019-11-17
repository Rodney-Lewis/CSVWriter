package app;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) throws Exception {

        TimeseriesLog tsLog = new TimeseriesLog("tslog");
        EventLog eLog = EventLog.getInstance();

        List<Double> metrics = new ArrayList<Double>();
        
        /*
            Run logging in two sepeate timed scheduled threads.
        */
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
            exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                metrics.add(Math.random());
                metrics.add(Math.random());
                metrics.add(Math.random());
                metrics.add(Math.random());
                metrics.add(Math.random()); 
                metrics.add(Math.random());
                metrics.add(Math.random());
                metrics.add(Math.random());
                metrics.add(Math.random());
                metrics.add(Math.random()); 
                tsLog.writeMetricsToTimeseriesLog(metrics);
                metrics.clear();
            }
        }, 0, 1, TimeUnit.SECONDS);

        ScheduledExecutorService execs = Executors.newSingleThreadScheduledExecutor();
            execs.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                eLog.writeToEventLog(EventLog.Severity.ERROR, "Lorem ipsum dolor sit amet, liber dicant regione qui at, ea voluptatum inciderint quo, aliquando assentior philosophia mel eu. Mazim noster ne pro, tale facilisi qui et. Cum te omnis tantas alienum. Mel ad viris tempor virtute, nostro persius mea te. Cu his propriae invenire. His officiis intellegam dissentiunt id, eu tota ceteros vel.");
                eLog.writeToEventLog(EventLog.Severity.INFO, "No natum persequeris ullamcorper eos, ex ius euismod lucilius suscipiantur. Audiam numquam imperdiet mei et, ipsum velit insolens ut vel. Ea dicta verear veritus est. Sed affert facilis sententiae ne, partiendo scripserit ex has. Impedit tractatos pri ad, sit deleniti sensibus et, qui ea dico oblique admodum.");
                eLog.writeToEventLog(EventLog.Severity.WARN, "Dictas intellegat nec no, ius minimum consequuntur ne, eu his veri quaerendum repudiandae. Simul offendit scaevola ad mei. Eos amet omnes consectetuer ea, eu albucius luptatum signiferumque sit. Vocent suscipit referrentur ex vel.");
                eLog.writeToEventLog(EventLog.Severity.ERROR, "Ut mei eius iuvaret. Pri wisi movet utroque ea, ex modo epicurei ocurreret vix, ei nam denique accusamus assueverit. Eum viderer discere argumentum ne, nostrum mandamus per an. No dictas detraxit cum, sea ne illum clita veritus, vis officiis phaedrum in.");
                eLog.writeToEventLog(EventLog.Severity.INFO, "Perpetua suavitate voluptatibus ut vel, mea nisl putent everti ne. Vero debet in eum, elit libris vituperatoribus ut sit. Usu noster inermis dissentiunt eu. Vix te case ipsum, no omnium bonorum equidem eos. Munere corrumpit instructior ne vix, ius aliquid impedit ut.");   
            }
        }, 0, 3, TimeUnit.SECONDS);
    }
}