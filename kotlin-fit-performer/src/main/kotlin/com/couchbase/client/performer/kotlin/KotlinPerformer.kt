/*
 * Copyright (c) 2022 Couchbase, Inc.
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
package com.couchbase.client.performer.kotlin

import com.couchbase.client.performer.core.CorePerformer
import com.couchbase.client.performer.core.commands.SdkCommandExecutor
import com.couchbase.client.performer.core.commands.TransactionCommandExecutor
import com.couchbase.client.performer.core.perf.Counters
import com.couchbase.client.performer.kotlin.util.ClusterConnection
import com.couchbase.client.protocol.performer.PerformerCapsFetchResponse
import com.couchbase.client.protocol.run.Workloads
import com.couchbase.client.protocol.shared.API
import com.couchbase.client.protocol.shared.ClusterConnectionCloseRequest
import com.couchbase.client.protocol.shared.ClusterConnectionCloseResponse
import com.couchbase.client.protocol.shared.ClusterConnectionCreateRequest
import com.couchbase.client.protocol.shared.ClusterConnectionCreateResponse
import com.couchbase.client.protocol.shared.DisconnectConnectionsRequest
import com.couchbase.client.protocol.shared.DisconnectConnectionsResponse
import io.grpc.ServerBuilder
import io.grpc.Status
import io.grpc.stub.StreamObserver
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory

class KotlinPerformer : CorePerformer() {
    private val clusterConnections: MutableMap<String, ClusterConnection> = mutableMapOf()
    private val logger = LoggerFactory.getLogger(KotlinPerformer::class.java)

    override fun customisePerformerCaps(response: PerformerCapsFetchResponse.Builder?) {
        response!!.setPerformerUserAgent("kotlin")
            .addSdkImplementationCaps(com.couchbase.client.protocol.sdk.Caps.SDK_PRESERVE_EXPIRY)
    }

    override fun clusterConnectionCreate(request: ClusterConnectionCreateRequest, responseObserver: StreamObserver<ClusterConnectionCreateResponse>) {
        try {
            val connection = ClusterConnection(request)
            clusterConnections[request.clusterConnectionId] = connection
            logger.info("Established connection to cluster at IP: {} with user {} and id {}", request.clusterHostname, request.clusterUsername, request.clusterConnectionId)
            responseObserver.onNext(ClusterConnectionCreateResponse.newBuilder()
                .setClusterConnectionCount(clusterConnections.size)
                .build())
            responseObserver.onCompleted()
        } catch (err: Exception) {
            logger.error("Operation failed during clusterConnectionCreate due to {}", err.message)
            responseObserver.onError(Status.ABORTED.withDescription(err.toString()).asException())
        }
    }

    override fun clusterConnectionClose(
        request: ClusterConnectionCloseRequest,
        responseObserver: StreamObserver<ClusterConnectionCloseResponse>
    ) {
        try {
            runBlocking {
                clusterConnections.get(request.clusterConnectionId)!!.cluster.disconnect()
            }
            clusterConnections.remove(request.clusterConnectionId)
            responseObserver.onNext(ClusterConnectionCloseResponse.newBuilder()
                .setClusterConnectionCount(clusterConnections.size)
                .build())
            responseObserver.onCompleted()
        } catch (err: Exception) {
            responseObserver.onError(Status.ABORTED.withDescription(err.toString()).asException())
        }
    }

    override fun disconnectConnections(
        request: DisconnectConnectionsRequest?,
        responseObserver: StreamObserver<DisconnectConnectionsResponse?>
    ) {
        runBlocking {
            clusterConnections.forEach { t, u ->
                runBlocking {
                    u.cluster.disconnect()
                }
            }
        }
        clusterConnections.clear()
        responseObserver.onNext(DisconnectConnectionsResponse.newBuilder().build())
        responseObserver.onCompleted()
    }

    override fun executor(workloads: com.couchbase.client.protocol.run.Workloads, counters: Counters, api: API): SdkCommandExecutor {
        // The test driver freaks out if we say we don't support the async API.
        // As a workaround, execute the tests redundantly (same way in both API modes).
        // Uncomment this when driver is fixed.
//        if (api != API.DEFAULT) {
//            throw UnsupportedOperationException("This performer supports only the default API.")
//        }

        return KotlinSdkCommandExecutor(clusterConnections.get(workloads.clusterConnectionId)!!, counters)
    }

    override fun transactionsExecutor(workloads: Workloads?, counters: Counters?): TransactionCommandExecutor? {
        return null
    }
}


fun main(args: Array<String>) {
    val logger = LoggerFactory.getLogger(KotlinPerformer::class.java)
    val port = 8060

    // ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.PARANOID);
    val server = ServerBuilder.forPort(port)
        .addService(KotlinPerformer())
        .build()
    server.start()
    logger.info("Server Started at {}", server.port)
    server.awaitTermination()
}
