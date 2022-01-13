/*
 * Copyright 2019 Couchbase, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.couchbase.client.java.manager.analytics;

import com.couchbase.client.core.annotation.Stability;
import com.couchbase.client.java.json.JsonObject;

import static com.couchbase.client.core.logging.RedactableArgument.redactMeta;
import static java.util.Objects.requireNonNull;

/**
 * Represents a dataset (or collection) in analytics.
 */
public class AnalyticsDataset {

  private final String name;
  private final String dataverseName;
  private final JsonObject json;

  /**
   * Creates a new dataset from a raw JSON object.
   *
   * @param json the decoded JSON object.
   */
  @Stability.Internal
  public AnalyticsDataset(final JsonObject json) {
    this.json = requireNonNull(json);
    this.name = requireNonNull(json.getString("DatasetName"));
    this.dataverseName = requireNonNull(json.getString("DataverseName"));
  }

  /**
   * Returns the name of the dataset (or collection).
   *
   * @return the name of the dataset (or collection).
   */
  public String name() {
    return name;
  }

  /**
   * Returns the name of the dataverse in which this dataset is stored.
   *
   * @return the name of the dataverse.
   */
  public String dataverseName() {
    return dataverseName;
  }

  /**
   * Returns the "raw" JSON returned from the analytics service.
   *
   * @return the "raw" JSON returned from the analytics service.
   */
  public JsonObject json() {
    return json;
  }

  @Override
  public String toString() {
    return "AnalyticsDataset{" +
        "name='" + redactMeta(name) + '\'' +
        ", dataverseName='" + redactMeta(dataverseName) + '\'' +
        ", json=" + redactMeta(json) +
        '}';
  }

}
