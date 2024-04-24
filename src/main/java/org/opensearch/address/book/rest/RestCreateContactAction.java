package org.opensearch.address.book.rest;

import org.opensearch.address.book.actions.CreateContactAction;
import org.opensearch.address.book.actions.CreateContactRequest;
import org.opensearch.client.node.NodeClient;
import org.opensearch.common.settings.Settings;
import org.opensearch.common.util.concurrent.ThreadContext;
import org.opensearch.rest.BaseRestHandler;
import org.opensearch.rest.RestController;
import org.opensearch.rest.RestRequest;
import org.opensearch.rest.action.RestToXContentListener;
import org.opensearch.threadpool.ThreadPool;

import java.io.IOException;
import java.util.List;

import static org.opensearch.rest.RestRequest.Method.PUT;

public class RestCreateContactAction extends BaseRestHandler  {
    private static final List<Route> routes = List.of(new Route(PUT, "/contact"));

    private final ThreadContext threadContext;
    private final Settings settings;

    public RestCreateContactAction(
            final Settings settings,
            final ThreadPool threadPool
    ) {
        super();
        this.threadContext = threadPool.getThreadContext();
        this.settings = settings;
    }

    @Override
    public List<Route> routes() {
        return routes;
    }

    @Override
    public String getName() {
        return "Address Book - Create contact";
    }

    @Override
    protected RestChannelConsumer prepareRequest(RestRequest restRequest, NodeClient nodeClient) throws IOException {
        String firstName = restRequest.param("firstName");

        CreateContactRequest createContactRequest = new CreateContactRequest(firstName);
        return channel -> {
            nodeClient.execute(CreateContactAction.INSTANCE, createContactRequest, new RestToXContentListener<>(channel));
        };
    }
}
