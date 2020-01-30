/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package logger;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class LogTest {
    @Test
    public void EventLogMethod() {

        EventLog eLog = new EventLog("event");

        eLog.writeToEventLog(EventLog.Severity.ERROR,
                "Lorem ipsum dolor sit amet, liber dicant regione qui at, ea voluptatum inciderint quo, aliquando assentior philosophia mel eu. Mazim noster ne pro, tale facilisi qui et. Cum te omnis tantas alienum. Mel ad viris tempor virtute, nostro persius mea te. Cu his propriae invenire. His officiis intellegam dissentiunt id, eu tota ceteros vel.");
        eLog.writeToEventLog(EventLog.Severity.INFO,
                "No natum persequeris ullamcorper eos, ex ius euismod lucilius suscipiantur. Audiam numquam imperdiet mei et, ipsum velit insolens ut vel. Ea dicta verear veritus est. Sed affert facilis sententiae ne, partiendo scripserit ex has. Impedit tractatos pri ad, sit deleniti sensibus et, qui ea dico oblique admodum.");
        eLog.writeToEventLog(EventLog.Severity.WARN,
                "Dictas intellegat nec no, ius minimum consequuntur ne, eu his veri quaerendum repudiandae. Simul offendit scaevola ad mei. Eos amet omnes consectetuer ea, eu albucius luptatum signiferumque sit. Vocent suscipit referrentur ex vel.");

    }

    @Test
    public void TimeseriesLogMethod() {

        TimeseriesLog tsLog = new TimeseriesLog("tslog");
        List<Double> metrics = new ArrayList<Double>();

        metrics.add(Math.random());
        metrics.add(Math.random());
        metrics.add(Math.random());
        tsLog.writeListMetricsToTimeseriesLog(metrics);
        metrics.clear();

    }
}
