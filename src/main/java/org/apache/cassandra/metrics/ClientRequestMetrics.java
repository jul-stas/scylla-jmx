/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

/*
 * Copyright 2015 Cloudius Systems
 *
 * Modified by Cloudius Systems
 */

package org.apache.cassandra.metrics;

import java.util.concurrent.TimeUnit;

import com.scylladb.jmx.metrics.APIMetrics;
import com.scylladb.jmx.metrics.DefaultNameFactory;
import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.Counter;
import com.yammer.metrics.core.Meter;

public class ClientRequestMetrics extends LatencyMetrics {
    @Deprecated
    public static final Counter readTimeouts = Metrics
            .newCounter(DefaultNameFactory.createMetricName(
                    "ClientRequestMetrics", "ReadTimeouts", null));
    @Deprecated
    public static final Counter writeTimeouts = Metrics
            .newCounter(DefaultNameFactory.createMetricName(
                    "ClientRequestMetrics", "WriteTimeouts", null));
    @Deprecated
    public static final Counter readUnavailables = Metrics
            .newCounter(DefaultNameFactory.createMetricName(
                    "ClientRequestMetrics", "ReadUnavailables", null));
    @Deprecated
    public static final Counter writeUnavailables = Metrics
            .newCounter(DefaultNameFactory.createMetricName(
                    "ClientRequestMetrics", "WriteUnavailables", null));

    public final Meter timeouts;
    public final Meter unavailables;

    public ClientRequestMetrics(String url, String scope) {
        super(url, "ClientRequest", scope);

        timeouts = APIMetrics.newMeter(url + "/timeouts",
                factory.createMetricName("Timeouts"), "timeouts",
                TimeUnit.SECONDS);
        unavailables = APIMetrics.newMeter(url + "/unavailables",
                factory.createMetricName("Unavailables"), "unavailables",
                TimeUnit.SECONDS);
    }

    public void release() {
        super.release();
        APIMetrics.defaultRegistry().removeMetric(
                factory.createMetricName("Timeouts"));
        APIMetrics.defaultRegistry().removeMetric(
                factory.createMetricName("Unavailables"));
    }
}
