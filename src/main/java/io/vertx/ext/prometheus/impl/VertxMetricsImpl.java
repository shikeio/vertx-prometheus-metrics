package io.vertx.ext.prometheus.impl;

import org.joor.Reflect;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.dropwizard.DropwizardExports;
import io.prometheus.client.vertx.MetricsHandler;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.datagram.DatagramSocket;
import io.vertx.core.datagram.DatagramSocketOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.net.SocketAddress;
import io.vertx.core.spi.metrics.DatagramSocketMetrics;
import io.vertx.core.spi.metrics.EventBusMetrics;
import io.vertx.core.spi.metrics.HttpClientMetrics;
import io.vertx.core.spi.metrics.HttpServerMetrics;
import io.vertx.core.spi.metrics.PoolMetrics;
import io.vertx.core.spi.metrics.TCPMetrics;
import io.vertx.core.spi.metrics.VertxMetrics;
import io.vertx.ext.prometheus.PrometheusMetricsOptions;
import io.vertx.ext.web.Router;

/**
 * @author Ranger Tsao(https://github.com/boliza)
 */
public class VertxMetricsImpl implements VertxMetrics {

  private Logger logger = LoggerFactory.getLogger(VertxMetricsImpl.class);

  private Vertx vertx;
  private VertxMetrics delegate;
  private PrometheusMetricsOptions metricsOptions;

  VertxMetricsImpl(Vertx vertx, VertxMetrics delegate, PrometheusMetricsOptions metricsOptions) {
    this.vertx = vertx;
    this.delegate = delegate;
    this.metricsOptions = metricsOptions;
  }

  @Override
  public void verticleDeployed(Verticle verticle) {
    delegate.verticleDeployed(verticle);
  }

  @Override
  public void verticleUndeployed(Verticle verticle) {
    delegate.verticleUndeployed(verticle);
  }

  @Override
  public void timerCreated(long id) {
    delegate.timerCreated(id);
  }

  @Override
  public void timerEnded(long id, boolean cancelled) {
    delegate.timerEnded(id, cancelled);
  }

  @Override
  public EventBusMetrics createMetrics(EventBus eventBus) {
    return delegate.createMetrics(eventBus);
  }

  @Override
  public HttpServerMetrics<?, ?, ?> createMetrics(HttpServer server, SocketAddress localAddress, HttpServerOptions options) {
    return delegate.createMetrics(server, localAddress, options);
  }

  @Override
  public HttpClientMetrics<?, ?, ?, ?, ?> createMetrics(HttpClient client, HttpClientOptions options) {
    return delegate.createMetrics(client, options);
  }

  @Override
  public TCPMetrics<?> createMetrics(SocketAddress localAddress, NetServerOptions options) {
    return delegate.createMetrics(localAddress, options);
  }

  @Override
  public TCPMetrics<?> createMetrics(NetClientOptions options) {
    return delegate.createMetrics(options);
  }

  @Override
  public DatagramSocketMetrics createMetrics(DatagramSocket socket, DatagramSocketOptions options) {
    return delegate.createMetrics(socket, options);
  }

  @Override
  public void eventBusInitialized(EventBus bus) {
    //do things
    if (metricsOptions.isEnabled()) {
      //using reflect to get registry then start a prometheus server
      CollectorRegistry.defaultRegistry.register(new DropwizardExports(Reflect.on(delegate).call("registry").get()));
      Router router = Router.router(vertx);
      router.get("/metrics").handler(new MetricsHandler());

      vertx.createHttpServer(metricsOptions.getServerOptions())
           .requestHandler(router::accept)
           .listen(fut -> {
             if (fut.succeeded()) {
               logger.info("Start prometheus server using options:" + metricsOptions.getServerOptions().toJson());
             } else {
               logger.error("Failed start prometheus server", fut.cause());
             }
           });
    }
  }

  @Override
  public <P> PoolMetrics<?> createMetrics(P pool, String poolType, String poolName, int maxPoolSize) {
    return delegate.createMetrics(pool, poolType, poolName, maxPoolSize);
  }

  @Override
  public boolean isMetricsEnabled() {
    return delegate.isMetricsEnabled();
  }

  @Override
  public boolean isEnabled() {
    return delegate.isEnabled();
  }

  @Override
  public void close() {
    delegate.close();
    //close server
  }
}
