package com.dubby.base.model.abstracts;

import com.dubby.base.model.Dictionary;
import com.dubby.base.model.IdGenerator;
import com.dubby.base.model.NullEmptyChecker;
import com.dubby.base.model.PersistenceManager;
import com.dubby.base.model.entity.Sequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public abstract class AIdGenerator implements IdGenerator {

    @Autowired
    private Dictionary baseDictionary;

    @Autowired
    private PersistenceManager persistenceManagerImpl;

    protected abstract Class getEntityClass();

    protected abstract String getCounterType();

    protected Dictionary getDictionary() {
        return baseDictionary;
    }

    protected PersistenceManager getPersistenceManager() {
        return persistenceManagerImpl;
    }

    protected String getPrefix(String... args) {
        return "";
    }

    protected String getSeparator() {
        return "";
    }

    protected String getSuffix(String... args) {
        return "";
    }

    protected abstract Integer getPadLength();

    protected String constructSequenceKey(String prefix, String suffix) {

        return String.format("%s;%s;%s", getCounterType(), prefix, suffix);
    }

    @Override
    public Long getNextSequence(String sequenceKey) throws Exception {

        if (sequenceKey.length() > 25) {
            throw new IllegalArgumentException(getDictionary().constructString("base.error.sequence.id.to.long"));
        }

        Sequence sequence = getPersistenceManager().findWithLock(Sequence.class, sequenceKey);

        Long newSequence = 1L;

        if (sequence == null) {
            sequence = new Sequence();

            sequence.setId(sequenceKey);
            sequence.setSequence(newSequence);

            Sequence testSequence = getPersistenceManager().find(Sequence.class, sequenceKey);

            if (testSequence != null) {
                newSequence = getNextSequence(sequenceKey);
            } else {
                getPersistenceManager().create(sequence);
            }
        } else {
            newSequence = sequence.getSequence() + 1;

            sequence.setSequence(newSequence);

            getPersistenceManager().update(sequence);
        }

        return newSequence;
    }

    @Override
    @Transactional
    public String generateId(String[] prefixParameter, String[] suffixParameter) throws Exception {

        String prefix = getPrefix(prefixParameter);
        String suffix = getSuffix(suffixParameter);

        Long newSequence = getNextSequence(constructSequenceKey(prefix, suffix));

        String newId = String.format("%0" + getPadLength().toString() + "d", newSequence);

        if (NullEmptyChecker.isNotNullOrEmpty(prefix)) {
            newId = String.format("%s%s%s", prefix, getSeparator(), newId);
        }

        if (NullEmptyChecker.isNotNullOrEmpty(suffix)) {
            newId = String.format("%s%s%s", newId, getSeparator(), suffix);
        }

        Object check = getPersistenceManager().find(getEntityClass(), newId);

        if (check != null) {
            throw new Exception(getDictionary().constructString("base.error.entity.id.already.used", newId));
        }

        return newId;
    }
}
