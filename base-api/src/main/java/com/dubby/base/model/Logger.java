/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dubby.base.model;

import java.io.Serializable;
import java.util.List;


public interface Logger extends Serializable {

    public org.slf4j.Logger getLogger(String loggerName);

    public List retrieveAuditTrail() throws Exception;

    public void logToAuditTrail(String logActivityName, String module, String referenceId,
                                String additionalInfo, String performedBy) throws Exception;

    public void logToAuditTrail(String logActivityName, String module, String referenceId,
                                String additionalInfo, Class itemClass, Object previousValue,
                                String performedBy) throws Exception;

    public void trace(String string, Object... args);

    public void debug(String string, Object... args);

    public void logInfo(String errorCode, Object... args);

    public void logWarn(String errorCode, Object... args);

    public void logError(Throwable e, String errorCode, Object... args);

}
