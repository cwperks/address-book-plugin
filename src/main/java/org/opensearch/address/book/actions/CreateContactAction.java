package org.opensearch.address.book.actions;

import org.opensearch.action.ActionType;
import org.opensearch.action.support.master.AcknowledgedResponse;

public class CreateContactAction extends ActionType<AcknowledgedResponse> {

    public static final CreateContactAction INSTANCE = new CreateContactAction();
    public static final String NAME = "contact:admin/create";

    protected CreateContactAction() {
        super(NAME, AcknowledgedResponse::new);
    }
}