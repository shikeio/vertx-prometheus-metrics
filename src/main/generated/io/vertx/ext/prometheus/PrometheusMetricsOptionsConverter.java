/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.vertx.ext.prometheus;

import io.vertx.core.json.JsonObject;

/**
 * Converter for {@link io.vertx.ext.prometheus.PrometheusMetricsOptions}.
 *
 * NOTE: This class has been automatically generated from the {@link io.vertx.ext.prometheus.PrometheusMetricsOptions} original class using Vert.x codegen.
 */
public class PrometheusMetricsOptionsConverter {

  public static void fromJson(JsonObject json, PrometheusMetricsOptions obj) {
    if (json.getValue("serverOptions") instanceof JsonObject) {
      obj.setServerOptions(new io.vertx.core.http.HttpServerOptions((JsonObject) json.getValue("serverOptions")));
    }
  }

  public static void toJson(PrometheusMetricsOptions obj, JsonObject json) {
    if (obj.getServerOptions() != null) {
      json.put("serverOptions", obj.getServerOptions().toJson());
    }
  }
}
