package org.opensearch.address.book.actions;

import org.opensearch.action.ActionRequestValidationException;
import org.opensearch.action.support.nodes.BaseNodesRequest;
import org.opensearch.core.common.io.stream.StreamInput;
import org.opensearch.core.common.io.stream.StreamOutput;

import java.io.IOException;

public class CreateContactRequest extends BaseNodesRequest<CreateContactRequest> {

    private String firstName;

    public CreateContactRequest(StreamInput in) throws IOException {
        super(in);
        this.firstName = in.readString();
    }

    public CreateContactRequest() {
        super(new String[0]);
    }

    public CreateContactRequest(String firstName) {
        this();
        setFirstName(firstName);
    }

    @Override
    public void writeTo(final StreamOutput out) throws IOException {
        super.writeTo(out);
        out.writeString(firstName);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    @Override
    public ActionRequestValidationException validate() {
        if (firstName == null || firstName.isEmpty()) {
            return new ActionRequestValidationException();
        }
        return null;
    }
}
