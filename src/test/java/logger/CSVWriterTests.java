/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package logger;

import org.junit.Test;

import events.EventsHandler;
import events.EventsHandler.Severity;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;

import metrics.TimeseriesHandler;

public class CSVWriterTests {
    @Test
    public void EventLogMethod() {

        EventsHandler eventHandler = new EventsHandler("events", Severity.ERROR);

        eventHandler.writeToEventLog(EventsHandler.Severity.ERROR,
                "Lorem ipsum dolor sit amet, liber dicant regione qui at, ea voluptatum inciderint quo, aliquando assentior philosophia mel eu. Mazim noster ne pro, tale facilisi qui et. Cum te omnis tantas alienum. Mel ad viris tempor virtute, nostro persius mea te. Cu his propriae invenire. His officiis intellegam dissentiunt id, eu tota ceteros vel.");
        eventHandler.writeToEventLog(EventsHandler.Severity.INFO,
                "No natum persequeris ullamcorper eos, ex ius euismod lucilius suscipiantur. Audiam numquam imperdiet mei et, ipsum velit insolens ut vel. Ea dicta verear veritus est. Sed affert facilis sententiae ne, partiendo scripserit ex has. Impedit tractatos pri ad, sit deleniti sensibus et, qui ea dico oblique admodum.");
        eventHandler.writeToEventLog(EventsHandler.Severity.WARN,
                "Dictas intellegat nec no, ius minimum consequuntur ne, eu his veri quaerendum repudiandae. Simul offendit scaevola ad mei. Eos amet omnes consectetuer ea, eu albucius luptatum signiferumque sit. Vocent suscipit referrentur ex vel.");

    }

    @Test
    public void TimeseriesLogMethod() {
        TimeseriesHandler timeseriesHandler = new TimeseriesHandler("timeseries");

        DoubleSupplier doubleSupplier = () -> Math.random();
        BooleanSupplier booleanSupplier = () -> true;
        LongSupplier longSupplier = () -> System.currentTimeMillis();
        IntSupplier integerSupplier = () -> Integer.MAX_VALUE;

        timeseriesHandler.registerMetric("Bool", booleanSupplier);
        timeseriesHandler.registerMetric("Int", integerSupplier);
        timeseriesHandler.registerMetric("Long", longSupplier);
        timeseriesHandler.registerMetric("Double", doubleSupplier);

        for (int i = 0; i < 500; i++) {
            timeseriesHandler.writeMetricsToTimeseriesLog();
        }
    }
}
