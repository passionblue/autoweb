package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class CleanerCustomerNotificationForm extends ActionForm {

	private String _CustomerId;             
	private String _ReasonforId;             
	private String _ReasonforTarget;             
	private String _NotificationType;             
	private String _SourceType;             
	private String _TriggerType;             
	private String _IsRetransmit;             
	private String _MethodType;             
	private String _TemplateType;             
	private String _Content;             
	private String _Destination;             
	private String _Reference;             
	private String _TimeScheduled;             
	private String _TimeCreated;             
	private String _TimeSent;             

    public String getCustomerId() {
        return _CustomerId;
    }

    public void setCustomerId(String CustomerId) {
        this._CustomerId = CustomerId;
    }    

    public String getReasonforId() {
        return _ReasonforId;
    }

    public void setReasonforId(String ReasonforId) {
        this._ReasonforId = ReasonforId;
    }    

    public String getReasonforTarget() {
        return _ReasonforTarget;
    }

    public void setReasonforTarget(String ReasonforTarget) {
        this._ReasonforTarget = ReasonforTarget;
    }    

    public String getNotificationType() {
        return _NotificationType;
    }

    public void setNotificationType(String NotificationType) {
        this._NotificationType = NotificationType;
    }    

    public String getSourceType() {
        return _SourceType;
    }

    public void setSourceType(String SourceType) {
        this._SourceType = SourceType;
    }    

    public String getTriggerType() {
        return _TriggerType;
    }

    public void setTriggerType(String TriggerType) {
        this._TriggerType = TriggerType;
    }    

    public String getIsRetransmit() {
        return _IsRetransmit;
    }

    public void setIsRetransmit(String IsRetransmit) {
        this._IsRetransmit = IsRetransmit;
    }    

    public String getMethodType() {
        return _MethodType;
    }

    public void setMethodType(String MethodType) {
        this._MethodType = MethodType;
    }    

    public String getTemplateType() {
        return _TemplateType;
    }

    public void setTemplateType(String TemplateType) {
        this._TemplateType = TemplateType;
    }    

    public String getContent() {
        return _Content;
    }

    public void setContent(String Content) {
        this._Content = Content;
    }    

    public String getDestination() {
        return _Destination;
    }

    public void setDestination(String Destination) {
        this._Destination = Destination;
    }    

    public String getReference() {
        return _Reference;
    }

    public void setReference(String Reference) {
        this._Reference = Reference;
    }    

    public String getTimeScheduled() {
        return _TimeScheduled;
    }

    public void setTimeScheduled(String TimeScheduled) {
        this._TimeScheduled = TimeScheduled;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

    public String getTimeSent() {
        return _TimeSent;
    }

    public void setTimeSent(String TimeSent) {
        this._TimeSent = TimeSent;
    }    

}