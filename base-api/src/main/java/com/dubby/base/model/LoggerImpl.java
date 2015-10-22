package com.dubby.base.model;

import com.dubby.base.model.entity.AuditTrail;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component
public class LoggerImpl implements Logger {

	private static final long serialVersionUID = 1L;

    @Autowired
    private PersistenceManager persistenceManagerImpl;

    @Autowired
    private Dictionary baseDictionary;

    @Autowired
    private ObjectStringConverter objectStringConverterImpl;

    protected PersistenceManager getPersistenceManager() {
        return persistenceManagerImpl;
    }

    protected Dictionary getDictionary() {
        return baseDictionary;
    }

    protected ObjectStringConverter getObjectStringConverter() {
        return objectStringConverterImpl;
    }

    private org.slf4j.Logger getLogger() {
        final Throwable t = new Throwable();
        t.fillInStackTrace();
        return LoggerFactory.getLogger(t.getStackTrace()[1].getClassName());
    }

    @Override
    public org.slf4j.Logger getLogger(String loggerName) {

        return LoggerFactory.getLogger(loggerName);
    }

    @Override
    @Transactional(readOnly = true)
    public List retrieveAuditTrail() throws Exception {
        return getPersistenceManager().find(AuditTrail.class);
    }

    @Override
    @Transactional
    public void logToAuditTrail(String logActivityName, String module, String referenceId,
                                String additionalInfo, String performedBy) throws Exception {

        logToAuditTrail(logActivityName, module, referenceId, additionalInfo, null, null, performedBy);
    }

    @Override
    @Transactional
    public void logToAuditTrail(String logActivityName, String module, String referenceId,
                                String additionalInfo, Class itemClass, Object previousValue,
                                String performedBy) throws Exception {

        AuditTrail auditTrail = new AuditTrail();
        
        auditTrail.setActivityName(logActivityName);
        auditTrail.setModule(module);
        auditTrail.setReferenceId(referenceId);
        auditTrail.setAdditionalInfo(additionalInfo);

        if (NullEmptyChecker.isNotNullOrEmpty(previousValue)) {
            auditTrail.setPreviousValue(getObjectStringConverter().convertToString(itemClass, previousValue));
        }

        auditTrail.setActivityDate(new Date());
        auditTrail.setPerformedBy(performedBy);

        getPersistenceManager().create(auditTrail, auditTrail.getPerformedBy());
    }

    @Override
    public void trace(String string, Object ... args) {
        getLogger().trace(string, args);
    }

    @Override
    public void debug(String string, Object ... args) {
        getLogger().debug(string, args);
    }

    @Override
    public void logInfo(String infoCode, Object ... args) {
        getLogger().info(getDictionary().constructString(infoCode, args));
    }

    @Override
    public void logWarn(String warnCode, Object ... args) {
        getLogger().warn(getDictionary().constructString(warnCode, args));
    }

    @Override
    public void logError(Throwable e, String errorCode, Object ... args) {

        StringBuilder errorMessage = new StringBuilder();

        if (!errorCode.isEmpty()) {
            errorMessage.append(getDictionary().constructString(errorCode, args));
        }

        if(NullEmptyChecker.isNotNullOrEmpty(e.getMessage())){
        	errorMessage.append(" : ");
            errorMessage.append(e.getMessage());
        }
        
        getLogger().error(errorMessage.toString(), e);

    }

}
