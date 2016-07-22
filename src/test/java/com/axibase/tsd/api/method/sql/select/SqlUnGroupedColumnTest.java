package com.axibase.tsd.api.method.sql.select;

import com.axibase.tsd.api.method.sql.SqlTest;
import com.axibase.tsd.api.model.series.Sample;
import com.axibase.tsd.api.model.series.Series;
import com.axibase.tsd.api.model.sql.StringTable;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.ProcessingException;

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

    @Test(expected = ProcessingException.class)
    public void testErrorRaisingSelectUngroupedColumnWithGroupClause() {
        String sqlQuery =
                "SELECT entity, datetime, avg(value)\n" +
                        "FROM '" + TEST_METRIC_NAME + "'\n" +
                        "WHERE datetime = '2016-06-29T08:00:00.000Z'\n" +
                        "GROUP BY entity";

        StringTable resultTable = executeQuery(sqlQuery).readEntity(StringTable.class);
    }

    @Test(expected = ProcessingException.class)
    public void testErrorRaisingSelectUngroupedColumnWithoutGroupClause() {
        String sqlQuery =
                "SELECT entity, datetime, avg(value)\n" +
                        "FROM '" + TEST_METRIC_NAME + "'\n" +
                        "WHERE datetime = '2016-06-29T08:00:00.000Z'";

        StringTable resultTable = executeQuery(sqlQuery).readEntity(StringTable.class);
    }
}
