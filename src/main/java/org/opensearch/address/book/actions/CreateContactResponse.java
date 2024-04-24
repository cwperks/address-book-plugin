package org.opensearch.address.book.actions;

import org.opensearch.core.action.ActionResponse;
import org.opensearch.core.common.Strings;
import org.opensearch.core.common.io.stream.StreamInput;
import org.opensearch.core.common.io.stream.StreamOutput;
import org.opensearch.core.xcontent.MediaTypeRegistry;
import org.opensearch.core.xcontent.ToXContent;
import org.opensearch.core.xcontent.XContentBuilder;

import java.io.IOException;

public class CreateContactResponse extends ActionResponse implements ToXContent {

    private final boolean success;

    public CreateContactResponse(boolean success) {
        this.success = success;
    }

    public CreateContactResponse() {
        super();
        this.success = false;
    }

    public CreateContactResponse(StreamInput in) throws IOException {
        super(in);
        success = in.readBoolean();
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        out.writeBoolean(success);
    }

    public boolean getSuccess() {
        return success;
    }

    @Override
    public XContentBuilder toXContent(XContentBuilder builder, Params params) throws IOException {

        builder.startObject("createcontact");
        builder.field("success", success);
        builder.endObject();

        return builder;
    }

    @Override
    public String toString() {
        return Strings.toString(MediaTypeRegistry.JSON, this, true, true);
    }
}
