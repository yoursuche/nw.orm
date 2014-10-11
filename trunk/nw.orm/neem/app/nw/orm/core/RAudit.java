package nw.orm.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import nw.orm.core.enums.RAuditLevel;

@Entity
@Table(name="AUDIT")

public class RAudit extends REntity {

    private static final long serialVersionUID = -6137992922034567873L;
    
    /**
     * Unique Identifier for current user performing the action to be audited
     */
    @Column(nullable = false, name = "USER_ID", updatable = false, insertable = true)
    private String userId;
    
    /**
     * Target entity
     */
    @Column(nullable = false, name = "ENTITY", updatable = false, insertable = true)
    private String entity;
    
    /**
     * Audit Level, CREATE, UPDATE, DELETE
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "AUDIT_LEVEL", updatable = false, insertable = true)
    private RAuditLevel auditLevel;
    
    @Column(nullable = true, name = "SOURCE_MACHINE", updatable = false, insertable = true)
    private String sourceMachine;
    
    @Column(nullable = true, name = "SOURCE_MACHINE_PUBLIC", updatable = false, insertable = true)
    private String sourceMachinePublic;
    
    /**
     * XML representing changes made on the target record
     */
    @Column(nullable = false, name = "AUDIT_DATA", updatable = false, insertable = true, columnDefinition="TEXT")
    private String auditData;
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getEntity() {
        return entity;
    }
    
    public void setEntity(String entity) {
        this.entity = entity;
    }
    
    public RAuditLevel getAuditLevel() {
        return auditLevel;
    }
    
    public void setAuditLevel(RAuditLevel auditLevel) {
        this.auditLevel = auditLevel;
    }
    
    public String getAuditData() {
        return auditData;
    }
    
    public void setAuditData(String auditData) {
        this.auditData = auditData;
    }

    public String getSourceMachine() {
	return sourceMachine;
    }

    public void setSourceMachine(String sourceMachine) {
	this.sourceMachine = sourceMachine;
    }

    public String getSourceMachinePublic() {
	return sourceMachinePublic;
    }

    public void setSourceMachinePublic(String sourceMachinePublic) {
	this.sourceMachinePublic = sourceMachinePublic;
    }
}
