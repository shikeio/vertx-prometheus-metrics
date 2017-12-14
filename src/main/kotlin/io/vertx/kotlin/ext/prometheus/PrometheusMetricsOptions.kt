package io.vertx.kotlin.ext.prometheus

import io.vertx.ext.prometheus.PrometheusMetricsOptions
import io.vertx.core.http.HttpServerOptions
import io.vertx.ext.dropwizard.Match

/**
 * A function providing a DSL for building [io.vertx.ext.prometheus.PrometheusMetricsOptions] objects.
 *
 *
 * @param baseName 
 * @param configPath 
 * @param enabled 
 * @param jmxDomain 
 * @param jmxEnabled 
 * @param monitoredEventBusHandlers 
 * @param monitoredHttpClientEndpoints 
 * @param monitoredHttpClientUris 
 * @param monitoredHttpServerUris 
 * @param registryName 
 * @param serverOptions 
 *
 * <p/>
 * NOTE: This function has been automatically generated from the [io.vertx.ext.prometheus.PrometheusMetricsOptions original] using Vert.x codegen.
 */
fun PrometheusMetricsOptions(
  baseName: String? = null,
  configPath: String? = null,
  enabled: Boolean? = null,
  jmxDomain: String? = null,
  jmxEnabled: Boolean? = null,
  monitoredEventBusHandlers: Iterable<io.vertx.ext.dropwizard.Match>? = null,
  monitoredHttpClientEndpoints: Iterable<io.vertx.ext.dropwizard.Match>? = null,
  monitoredHttpClientUris: Iterable<io.vertx.ext.dropwizard.Match>? = null,
  monitoredHttpServerUris: Iterable<io.vertx.ext.dropwizard.Match>? = null,
  registryName: String? = null,
  serverOptions: io.vertx.core.http.HttpServerOptions? = null): PrometheusMetricsOptions = io.vertx.ext.prometheus.PrometheusMetricsOptions().apply {

  if (baseName != null) {
    this.setBaseName(baseName)
  }
  if (configPath != null) {
    this.setConfigPath(configPath)
  }
  if (enabled != null) {
    this.setEnabled(enabled)
  }
  if (jmxDomain != null) {
    this.setJmxDomain(jmxDomain)
  }
  if (jmxEnabled != null) {
    this.setJmxEnabled(jmxEnabled)
  }
  if (monitoredEventBusHandlers != null) {
    for (item in monitoredEventBusHandlers) {
      this.addMonitoredEventBusHandler(item)
    }
  }
  if (monitoredHttpClientEndpoints != null) {
    for (item in monitoredHttpClientEndpoints) {
      this.addMonitoredHttpClientEndpoint(item)
    }
  }
  if (monitoredHttpClientUris != null) {
    for (item in monitoredHttpClientUris) {
      this.addMonitoredHttpClientUri(item)
    }
  }
  if (monitoredHttpServerUris != null) {
    for (item in monitoredHttpServerUris) {
      this.addMonitoredHttpServerUri(item)
    }
  }
  if (registryName != null) {
    this.setRegistryName(registryName)
  }
  if (serverOptions != null) {
    this.setServerOptions(serverOptions)
  }
}

