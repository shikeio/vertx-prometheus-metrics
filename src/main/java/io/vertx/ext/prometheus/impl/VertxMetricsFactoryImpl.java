package io.vertx.ext.prometheus.impl;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.impl.FileResolver;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.metrics.MetricsOptions;
import io.vertx.core.spi.VertxMetricsFactory;
import io.vertx.core.spi.metrics.VertxMetrics;
import io.vertx.ext.prometheus.PrometheusMetricsOptions;

/**
 * @author Ranger Tsao(https://github.com/boliza)
 */
public class VertxMetricsFactoryImpl extends io.vertx.ext.dropwizard.impl.VertxMetricsFactoryImpl implements VertxMetricsFactory {

  private Logger logger = LoggerFactory.getLogger(VertxMetricsFactoryImpl.class);

  @Override
  public VertxMetrics metrics(Vertx vertx, VertxOptions options) {
    //prometheus options
    MetricsOptions baseOptions = options.getMetricsOptions();
    PrometheusMetricsOptions metricsOptions;
    if (baseOptions instanceof PrometheusMetricsOptions) {
      metricsOptions = (PrometheusMetricsOptions) baseOptions;
    } else {
      metricsOptions = new PrometheusMetricsOptions(baseOptions.toJson());
    }

    if (metricsOptions.getConfigPath() != null && !metricsOptions.getConfigPath().isEmpty()) {
      JsonObject loadedFromFile = loadOptionsFile(metricsOptions.getConfigPath(), new FileResolver(vertx));
      if (!loadedFromFile.isEmpty()) {
        metricsOptions = new PrometheusMetricsOptions(loadedFromFile);
      }
    }
    VertxMetrics vertxMetrics = super.metrics(vertx, options.setMetricsOptions(metricsOptions));
    return new VertxMetricsImpl(vertx, vertxMetrics, metricsOptions);
  }

  @Override
  public MetricsOptions newOptions() {
    return new PrometheusMetricsOptions();
  }

  private JsonObject loadOptionsFile(String configPath, FileResolver fileResolver) {
    File file = fileResolver.resolveFile(configPath);
    try (Scanner scanner = new Scanner(file)) {
      scanner.useDelimiter("\\A");
      String metricsConfigString = scanner.next();

      return new JsonObject(metricsConfigString);
    } catch (IOException ioe) {
      logger.error("Error while reading metrics config file", ioe);
    } catch (DecodeException de) {
      logger.error("Error while decoding metrics config file into JSON", de);
    }

    return new JsonObject();
  }

}
