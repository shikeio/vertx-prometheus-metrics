package examples;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.docgen.Source;
import io.vertx.ext.prometheus.PrometheusMetricsOptions;

/**
 * @author Ranger Tsao(https://github.com/boliza)
 */
@Source
public class MetricsExample {

  public void setup() {
    Vertx vertx = Vertx.vertx(new VertxOptions().setMetricsOptions(
      new PrometheusMetricsOptions().setEnabled(true)
    ));
  }

}
