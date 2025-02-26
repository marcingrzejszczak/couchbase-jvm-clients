/*
 * Copyright 2023 Couchbase, Inc.
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

package com.couchbase.client.java.manager.collection;

import com.couchbase.client.core.annotation.Stability;
import com.couchbase.client.core.manager.collection.CoreCreateOrUpdateCollectionSettings;

import java.time.Duration;

/**
 * Allows to customize the behavior of the update collection operation.
 */
public class UpdateCollectionSettings {

  public Duration maxExpiry;
  public Boolean history;

  protected UpdateCollectionSettings() {
  }

  /**
   * Creates a new instance with default values.
   *
   * @return the instantiated default options.
   */
  public static UpdateCollectionSettings updateCollectionSettings() {
    return new UpdateCollectionSettings();
  }

  public UpdateCollectionSettings maxExpiry(Duration maxExpiry) {
    this.maxExpiry = maxExpiry;
    return this;
  }

  public UpdateCollectionSettings history(Boolean history) {
    this.history = history;
    return this;
  }

  @Stability.Internal
  public Built build() {
    return new Built();
  }

  @Stability.Internal
  public CoreCreateOrUpdateCollectionSettings toCore() {
    return new CoreCreateOrUpdateCollectionSettings() {
      @Override
      public Duration maxExpiry() {
        return build().maxExpiry();
      }

      @Override
      public Boolean history() {
        return build().history();
      }
    };
  }

  @Stability.Internal
  public class Built implements CoreCreateOrUpdateCollectionSettings {
    Built() {
    }

    @Override
    public Duration maxExpiry() {
      return maxExpiry;
    }

    @Override
    public Boolean history() {
      return history;
    }
  }
}
