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

package com.couchbase.client.java.manager.view;

import com.couchbase.client.core.error.CouchbaseException;
import com.couchbase.client.core.error.DesignDocumentNotFoundException;
import com.couchbase.client.core.error.TimeoutException;
import com.couchbase.client.java.view.DesignDocumentNamespace;

import java.util.List;

import static com.couchbase.client.java.AsyncUtils.block;
import static java.util.Objects.requireNonNull;

public class ViewIndexManager {
  private final AsyncViewIndexManager async;

  public ViewIndexManager(AsyncViewIndexManager async) {
    this.async = requireNonNull(async);
  }

  public AsyncViewIndexManager async() {
    return async;
  }

  /**
   * Returns the design document from the cluster if present.
   *
   * @param name name of the design document to retrieve.
   * @param namespace namespace to look in.
   * @throws DesignDocumentNotFoundException if there is no design document with the given name present.
   * @throws TimeoutException if the operation times out before getting a result.
   * @throws CouchbaseException for all other error reasons (acts as a base type and catch-all).
   */
  public DesignDocument getDesignDocument(String name, DesignDocumentNamespace namespace) {
    return block(async.getDesignDocument(name, namespace));
  }

  /**
   * Returns the design document from the cluster if present with custom options.
   *
   * @param name name of the design document to retrieve
   * @param namespace namespace to look in
   * @param options additional optional arguments (timeout, retry, etc.)
   * @throws DesignDocumentNotFoundException if there is no design document with the given name present.
   * @throws TimeoutException if the operation times out before getting a result.
   * @throws CouchbaseException for all other error reasons (acts as a base type and catch-all).
   */
  public DesignDocument getDesignDocument(String name, DesignDocumentNamespace namespace, GetDesignDocumentOptions options) {
    return block(async.getDesignDocument(name, namespace, options));
  }

  /**
   * Stores the design document on the server under the specified namespace, replacing any existing document
   * with the same name.
   *
   * @param designDocument document to store
   * @param namespace namespace to store it in
   * @throws TimeoutException if the operation times out before getting a result.
   * @throws CouchbaseException for all other error reasons (acts as a base type and catch-all).
   */
  public void upsertDesignDocument(DesignDocument designDocument, DesignDocumentNamespace namespace) {
    block(async.upsertDesignDocument(designDocument, namespace));
  }

  /**
   * Stores the design document on the server under the specified namespace, replacing any existing document
   * with the same name.
   *
   * @param designDocument document to store
   * @param namespace namespace to store it in
   * @param options additional optional arguments (timeout, retry, etc.)
   * @throws TimeoutException if the operation times out before getting a result.
   * @throws CouchbaseException for all other error reasons (acts as a base type and catch-all).
   */
  public void upsertDesignDocument(DesignDocument designDocument, DesignDocumentNamespace namespace, UpsertDesignDocumentOptions options) {
    block(async.upsertDesignDocument(designDocument, namespace, options));
  }

  /**
   * Convenience method that gets a the document from the development namespace
   * and upserts it to the production namespace.
   *
   * @param name name of the development design document
   * @throws DesignDocumentNotFoundException if the development namespace does not contain a document with the given name
   * @throws TimeoutException if the operation times out before getting a result.
   * @throws CouchbaseException for all other error reasons (acts as a base type and catch-all).
   */
  public void publishDesignDocument(String name) {
    block(async.publishDesignDocument(name));
  }

  /**
   * Convenience method that gets a the document from the development namespace
   * and upserts it to the production namespace.
   *
   * @param name name of the development design document
   * @param options additional optional arguments (timeout, retry, etc.)
   * @throws DesignDocumentNotFoundException if the development namespace does not contain a document with the given name
   * @throws TimeoutException if the operation times out before getting a result.
   * @throws CouchbaseException for all other error reasons (acts as a base type and catch-all).
   */
  public void publishDesignDocument(String name, PublishDesignDocumentOptions options) {
    block(async.publishDesignDocument(name, options));
  }

  /**
   * Removes a design document from the server.
   *
   * @param name name of the document to remove
   * @param namespace namespace to remove it from
   * @throws DesignDocumentNotFoundException if the namespace does not contain a document with the given name
   * @throws TimeoutException if the operation times out before getting a result.
   * @throws CouchbaseException for all other error reasons (acts as a base type and catch-all).
   */
  public void dropDesignDocument(String name, DesignDocumentNamespace namespace) {
    block(async.dropDesignDocument(name, namespace));
  }

  /**
   * Removes a design document from the server.
   *
   * @param name name of the document to remove
   * @param namespace namespace to remove it from
   * @param options additional optional arguments (timeout, retry, etc.)
   * @throws DesignDocumentNotFoundException if the namespace does not contain a document with the given name
   * @throws TimeoutException if the operation times out before getting a result.
   * @throws CouchbaseException for all other error reasons (acts as a base type and catch-all).
   */
  public void dropDesignDocument(String name, DesignDocumentNamespace namespace, DropDesignDocumentOptions options) {
    block(async.dropDesignDocument(name, namespace, options));
  }

  /**
   * Returns all of the design documents in the specified namespace.
   *
   * @param namespace namespace to query
   * @throws TimeoutException if the operation times out before getting a result.
   * @throws CouchbaseException for all other error reasons (acts as a base type and catch-all).
   */
  public List<DesignDocument> getAllDesignDocuments(DesignDocumentNamespace namespace) {
    return block(async.getAllDesignDocuments(namespace));
  }

  /**
   * Returns all of the design documents in the specified namespace.
   *
   * @param namespace namespace to query
   * @param options additional optional arguments (timeout, retry, etc.)
   * @throws TimeoutException if the operation times out before getting a result.
   * @throws CouchbaseException for all other error reasons (acts as a base type and catch-all).
   */
  public List<DesignDocument> getAllDesignDocuments(DesignDocumentNamespace namespace, GetAllDesignDocumentsOptions options) {
    return block(async.getAllDesignDocuments(namespace, options));
  }
}
