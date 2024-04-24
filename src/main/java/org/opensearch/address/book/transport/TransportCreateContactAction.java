package org.opensearch.address.book.transport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opensearch.OpenSearchException;
import org.opensearch.action.index.IndexRequest;
import org.opensearch.action.index.IndexResponse;
import org.opensearch.action.support.ActionFilters;
import org.opensearch.action.support.HandledTransportAction;
import org.opensearch.action.support.WriteRequest;
import org.opensearch.action.support.master.AcknowledgedResponse;
import org.opensearch.address.book.actions.CreateContactAction;
import org.opensearch.address.book.actions.CreateContactRequest;
import org.opensearch.client.Client;
import org.opensearch.cluster.service.ClusterService;
import org.opensearch.common.inject.Inject;
import org.opensearch.core.action.ActionListener;
import org.opensearch.index.mapper.MapperService;
import org.opensearch.tasks.Task;
import org.opensearch.threadpool.ThreadPool;
import org.opensearch.transport.TransportService;

import java.util.Map;

import static org.opensearch.address.book.Constants.ADDRESS_BOOK_SYSTEM_INDEX;

public class TransportCreateContactAction extends HandledTransportAction<CreateContactRequest, AcknowledgedResponse> {
    private static final Logger log = LogManager.getLogger(TransportCreateContactAction.class);

    private final ThreadPool threadPool;

    private final Client client;


    @Inject
    public TransportCreateContactAction(
            TransportService transportService,
            ActionFilters actionFilters,
            ThreadPool threadPool,
            Client client
    ) {
        super(CreateContactAction.NAME, transportService, actionFilters, CreateContactRequest::new);
        this.client = client;
        this.threadPool = threadPool;
    }

    @Override
    protected void doExecute(Task task, CreateContactRequest request, ActionListener<AcknowledgedResponse> actionListener) {
        this.threadPool.getThreadContext().stashContext();

        IndexRequest indexRequest = new IndexRequest(ADDRESS_BOOK_SYSTEM_INDEX)
                .setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE)
                .source(Map.of("firstName", request.getFirstName()))
                .timeout("60s");
        client.index(indexRequest, new ActionListener<>() {
            @Override
            public void onResponse(IndexResponse response) {
                log.debug("contact index success.");
                actionListener.onResponse(new AcknowledgedResponse(true));
            }

            @Override
            public void onFailure(Exception e) {
                log.warn("contact index failed.");
                actionListener.onFailure(new OpenSearchException("contact index failed."));
            }
        });
    }
}
