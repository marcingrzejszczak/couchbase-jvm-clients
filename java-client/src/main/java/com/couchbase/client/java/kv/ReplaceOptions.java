/*
 * Copyright (c) 2018 Couchbase, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.couchbase.client.java.kv;

import com.couchbase.client.java.CommonOptions;
import com.couchbase.client.java.codec.DefaultEncoder;
import com.couchbase.client.java.codec.Encoder;

import java.time.Duration;

import static com.couchbase.client.core.util.Validators.notNull;

public class ReplaceOptions extends CommonOptions<ReplaceOptions> {

  public static ReplaceOptions DEFAULT = new ReplaceOptions();

  private Duration timeout;
  private Duration expiry;
  private PersistTo persistTo;
  private ReplicateTo replicateTo;
  private Encoder encoder = DefaultEncoder.INSTANCE;
  private long cas;

  private ReplaceOptions() { }

  public static ReplaceOptions replaceOptions() {
    return new ReplaceOptions();
  }

  public Duration expiry() {
    return expiry;
  }

  public ReplaceOptions expiry(final Duration expiry) {
    this.expiry = expiry;
    return this;
  }

  public PersistTo persistTo() {
    return persistTo;
  }

  public ReplaceOptions persistTo(final PersistTo persistTo) {
    this.persistTo = persistTo;
    return this;
  }

  public ReplicateTo replicateTo() {
    return replicateTo;
  }

  public ReplaceOptions replicateTo(final ReplicateTo replicateTo) {
    this.replicateTo = replicateTo;
    return this;
  }

  public Encoder encoder() {
    return encoder;
  }

  public ReplaceOptions encoder(final Encoder encoder) {
    notNull(encoder, "Encoder");

    this.encoder = encoder;
    return this;
  }

  public long cas() {
    return cas;
  }

  public ReplaceOptions cas(long cas) {
    this.cas = cas;
    return this;
  }
}
