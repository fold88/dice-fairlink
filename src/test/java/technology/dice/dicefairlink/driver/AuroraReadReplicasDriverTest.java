/*
 * Copyright (C) 2018 - present by Dice Technology Ltd.
 *
 * Please see distribution for license.
 */
package technology.dice.dicefairlink.driver;

import static org.assertj.core.api.Assertions.assertThat;
import com.amazonaws.services.rds.model.DBClusterNotFoundException;
import org.junit.Test;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class AuroraReadReplicasDriverTest {

  private static final String VALID_JDBC_URL = "jdbc:auroraro:mysql://aa:123/db?param1=123&param2=true&param3=abc";

  @Test(expected = SQLException.class)
  public void throwsOnAcceptsURL_nullString() throws Exception {
    AuroraReadReplicasDriver auroraReadReplicasDriver = new AuroraReadReplicasDriver(new ScheduledThreadPoolExecutor(1));
    auroraReadReplicasDriver.acceptsURL(null);
  }

  @Test
  public void canAcceptsURL_emptyString() throws Exception {
    AuroraReadReplicasDriver auroraReadReplicasDriver = new AuroraReadReplicasDriver(new ScheduledThreadPoolExecutor(1));
    boolean retunedValue = auroraReadReplicasDriver.acceptsURL("");
    assertThat(retunedValue).isEqualTo(false);
  }

  @Test
  public void canAcceptsURL_validString() throws Exception {
    AuroraReadReplicasDriver auroraReadReplicasDriver = new AuroraReadReplicasDriver(new ScheduledThreadPoolExecutor(1));
    boolean retunedValue = auroraReadReplicasDriver.acceptsURL(VALID_JDBC_URL);
    assertThat(retunedValue).isEqualTo(true);
  }

  @Test(expected = NullPointerException.class)
  public void failToConnectToValidUrl_nullProperties() throws Exception {
    AuroraReadReplicasDriver auroraReadReplicasDriver = new AuroraReadReplicasDriver(new ScheduledThreadPoolExecutor(1));
    auroraReadReplicasDriver.connect(VALID_JDBC_URL, null); // last call must throw
  }

  @Test(expected = DBClusterNotFoundException.class)
  public void failToConnectToValidUrl_emptyProperties() throws Exception {
    AuroraReadReplicasDriver auroraReadReplicasDriver = new AuroraReadReplicasDriver(new ScheduledThreadPoolExecutor(1));
    final Properties emptyProperties = new Properties();
    auroraReadReplicasDriver.connect(VALID_JDBC_URL, emptyProperties); // last call must throw
  }

}
