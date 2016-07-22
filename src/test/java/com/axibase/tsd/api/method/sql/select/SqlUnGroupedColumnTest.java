package com.axibase.tsd.api.method.sql.select;

import com.axibase.tsd.api.method.sql.SqlTest;
import com.axibase.tsd.api.model.series.Sample;
import com.axibase.tsd.api.model.series.Series;
import com.axibase.tsd.api.model.sql.StringTable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static javax.ws.rs.core.Response.Status;


/**
 * @author Igor Shmagrinskiy
 */
public class SqlUnGroupedColumnTest extends SqlTest {
    private static final String TEST_PREFIX = "sql-select-un-grouped-column-";
    private static final String TEST_METRIC_NAME = TEST_PREFIX + "metric";
    private static final String TEST_ENTITY_NAME = TEST_PREFIX + "entity";

    @BeforeClass
    public static void prepareDate() {
        Series series = new Series(TEST_ENTITY_NAME, TEST_METRIC_NAME);
        sendSamplesToSeries(series,
                new Sample("2016-06-29T08:00:00.000Z", "0")
        );
    }

    @Test
    public void testErrorRaisingSelectUngroupedColumnWithGroupClause() {
        String sqlQuery =
                "SELECT entity, datetime, avg(value)\n" +
                        "FROM '" + TEST_METRIC_NAME + "'\n" +
                        "WHERE datetime = '2016-06-29T08:00:00.000Z'\n" +
                        "GROUP BY entity";

        Response response = executeQuery(sqlQuery);

        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals(getErrorMessageFromResponse(response), "Include column \"datetime\" in the GROUP BY clause.");
    }

    @Test
    public void testErrorRaisingSelectUngroupedColumnWithoutGroupClause() {
        String sqlQuery =
                "SELECT entity, datetime, avg(value)\n" +
                        "FROM '" + TEST_METRIC_NAME + "'\n" +
                        "WHERE datetime = '2016-06-29T08:00:00.000Z'";

        Response response = executeQuery(sqlQuery);

        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals(getErrorMessageFromResponse(response), "SELECT expression cannot include both an aggregation function and a column not referenced in GROUP BY clause.");
    }

    private String getErrorMessageFromResponse(Response response) {
        String jsonText = response.readEntity(String.class);
        try {
            JSONObject json = new JSONObject(jsonText);
            return json.getJSONArray("errors")
                    .getJSONObject(0)
                    .getString("message");
        } catch (JSONException e) {
            return null;
        }

    }
}
