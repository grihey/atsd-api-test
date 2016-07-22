package com.axibase.tsd.api.method.metric;

import com.axibase.tsd.api.model.metric.Metric;
import org.json.JSONException;
import org.junit.Test;

import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.OK;
import java.io.IOException;
import java.text.ParseException;

/**
 * Created by grihey on 22.07.16.
 */
public class MetricCommandTest extends MetricMethod {

    /* #3088 */
    @Test
    public void testMetricCommand() throws Exception, IOException, JSONException, InterruptedException, ParseException {
        String name = "javatestmetriccomm-1";
        final Metric metric = new Metric(name);

        StringBuffer sb = new StringBuffer("metric");
        sb.append(" m:").append(metric.getName());
        sb.append(" p:").append("float");
        sb.append(" v:").append("true");
        sb.append(" t:").append("java=tag");


        tcpSender.send(sb.toString(), 1000);

        Thread.sleep(1000);
        Response response = queryMetric(name);

        if (response.getStatus() != OK.getStatusCode()) {
            response.close();
            throw new IOException("Fail to execute metric command.");
        }

        String actual = response.readEntity(String.class);
        String expected = "{\"name\":\"javatestmetriccomm-1\",\"dataType\":\"FLOAT\"," +
                        "\"versioned\":true,\"tags\":{\"java\":\"tag\"}}";

        if(!compareJsonString(expected, actual, false)) {
            throw new IOException("Test is not passed.");
        }
    }

}
