package io.vertx.ext.prometheus;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.dropwizard.DropwizardMetricsOptions;

/**
 * @author Ranger Tsao(https://github.com/boliza)
 */
@DataObject(generateConverter = true)
public class PrometheusMetricsOptions extends DropwizardMetricsOptions {

  /**
   * The default Prometheus server port = 9090.
   */
  public static final int DEFAULT_PORT = 9090;

  private HttpServerOptions serverOptions;

  public PrometheusMetricsOptions() {
    super();
    serverOptions = new HttpServerOptions().setPort(DEFAULT_PORT);
  }

  public PrometheusMetricsOptions(DropwizardMetricsOptions other) {
    super(other);
    serverOptions = new HttpServerOptions().setPort(DEFAULT_PORT);
  }

  public PrometheusMetricsOptions(PrometheusMetricsOptions other) {
    super(other);
    serverOptions = other.serverOptions;
  }

  public PrometheusMetricsOptions(JsonObject json) {
    super(json);
    serverOptions = new HttpServerOptions(json.getJsonObject("serverOptions", new JsonObject().put("port", DEFAULT_PORT)));
  }

  public HttpServerOptions getServerOptions() {
    return serverOptions;
  }

  public PrometheusMetricsOptions setServerOptions(HttpServerOptions serverOptions) {
    this.serverOptions = serverOptions;
    return this;
  }

  @Override
  public JsonObject toJson() {
    JsonObject result = new JsonObject();
    PrometheusMetricsOptionsConverter.toJson(this, result);
    return result;
  }
}
