package com.lirik.listener;

import com.lirik.entity.Revision;
import org.hibernate.envers.RevisionListener;

public class MyRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        ((Revision) revisionEntity).setUserName("Lirik");
    }
}
